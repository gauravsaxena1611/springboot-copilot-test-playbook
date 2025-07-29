package com.example.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String orderNumber = UUID.randomUUID().toString();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status = OrderStatus.CREATED;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shipping_address_id")
    private Address shippingAddress;

    @Column(nullable = false)
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @Column(name = "shipping_cost")
    private BigDecimal shippingCost = BigDecimal.ZERO;

    @Column(name = "tax_amount")
    private BigDecimal taxAmount = BigDecimal.ZERO;

    @Version
    private Long version;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    public void addItem(OrderItem item) {
        items.add(item);
        item.setOrder(this);
        recalculateTotal();
    }

    public void removeItem(OrderItem item) {
        items.remove(item);
        item.setOrder(null);
        recalculateTotal();
    }

    public void recalculateTotal() {
        this.totalAmount = items.stream()
                .map(OrderItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .add(shippingCost)
                .add(taxAmount);
    }

    public boolean canCancel() {
        return status == OrderStatus.CREATED ||
               status == OrderStatus.CONFIRMED ||
               status == OrderStatus.PAYMENT_PENDING;
    }

    public void updateStatus(OrderStatus newStatus) {
        validateStatusTransition(newStatus);
        this.status = newStatus;
        if (newStatus == OrderStatus.COMPLETED) {
            this.completedAt = LocalDateTime.now();
        }
    }

    private void validateStatusTransition(OrderStatus newStatus) {
        if (!isValidStatusTransition(newStatus)) {
            throw new IllegalStateException(
                "Invalid order status transition from " + this.status + " to " + newStatus);
        }
    }

    private boolean isValidStatusTransition(OrderStatus newStatus) {
        switch (this.status) {
            case CREATED:
                return newStatus == OrderStatus.CONFIRMED ||
                       newStatus == OrderStatus.CANCELLED;
            case CONFIRMED:
                return newStatus == OrderStatus.PAYMENT_PENDING ||
                       newStatus == OrderStatus.CANCELLED;
            case PAYMENT_PENDING:
                return newStatus == OrderStatus.PAID ||
                       newStatus == OrderStatus.PAYMENT_FAILED ||
                       newStatus == OrderStatus.CANCELLED;
            case PAID:
                return newStatus == OrderStatus.PROCESSING ||
                       newStatus == OrderStatus.REFUNDED;
            case PROCESSING:
                return newStatus == OrderStatus.SHIPPED ||
                       newStatus == OrderStatus.CANCELLED;
            case SHIPPED:
                return newStatus == OrderStatus.DELIVERED ||
                       newStatus == OrderStatus.RETURNED;
            case DELIVERED:
                return newStatus == OrderStatus.COMPLETED ||
                       newStatus == OrderStatus.RETURNED;
            default:
                return false;
        }
    }
}

enum OrderStatus {
    CREATED,
    CONFIRMED,
    PAYMENT_PENDING,
    PAYMENT_FAILED,
    PAID,
    PROCESSING,
    SHIPPED,
    DELIVERED,
    COMPLETED,
    CANCELLED,
    REFUNDED,
    RETURNED
}
