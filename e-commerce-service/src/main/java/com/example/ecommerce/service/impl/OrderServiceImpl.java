package com.example.ecommerce.service.impl;

import com.example.ecommerce.dto.OrderRequest;
import com.example.ecommerce.event.OrderEvent;
import com.example.ecommerce.exception.*;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.OrderStatus;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.service.OrderService;
import com.example.ecommerce.service.ProductService;
import com.example.ecommerce.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final NotificationService notificationService;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Order createOrder(OrderRequest orderRequest) {
        log.info("Creating new order for user: {}", orderRequest.getUserId());

        // Validate and reserve stock for all items
        validateAndReserveStock(orderRequest);

        try {
            Order order = buildOrderFromRequest(orderRequest);
            order = orderRepository.save(order);

            // Async notification
            CompletableFuture.runAsync(() -> notificationService.sendOrderCreationNotification(order));

            // Publish event
            eventPublisher.publishEvent(new OrderEvent(this, order.getId(), "ORDER_CREATED"));

            return order;
        } catch (Exception e) {
            // Release reserved stock in case of failure
            rollbackStockReservations(orderRequest);
            throw new OrderCreationException("Failed to create order", e);
        }
    }

    @Override
    @Transactional
    @Retryable(maxAttempts = 3)
    public Order updateOrderStatus(Long id, OrderStatus newStatus) {
        Order order = orderRepository.findById(id)
            .orElseThrow(() -> new OrderNotFoundException("Order not found: " + id));

        try {
            order.updateStatus(newStatus);
            order = orderRepository.save(order);

            // Handle status-specific actions
            handleStatusSpecificActions(order, newStatus);

            // Publish event
            eventPublisher.publishEvent(new OrderEvent(this, order.getId(), "STATUS_UPDATED_" + newStatus));

            return order;
        } catch (IllegalStateException e) {
            throw new InvalidStatusTransitionException("Invalid status transition", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Order> getOrder(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    @Transactional
    public boolean cancelOrder(Long id) {
        Order order = orderRepository.findById(id)
            .orElseThrow(() -> new OrderNotFoundException("Order not found: " + id));

        if (!order.canCancel()) {
            throw new OrderCancellationException("Order cannot be cancelled in current status: " + order.getStatus());
        }

        try {
            // Release reserved stock
            order.getItems().forEach(item ->
                productService.releaseStock(item.getProduct().getId(), item.getQuantity()));

            order.updateStatus(OrderStatus.CANCELLED);
            orderRepository.save(order);

            // Async notification
            CompletableFuture.runAsync(() -> notificationService.sendOrderCancellationNotification(order));

            // Publish event
            eventPublisher.publishEvent(new OrderEvent(this, order.getId(), "ORDER_CANCELLED"));

            return true;
        } catch (Exception e) {
            log.error("Failed to cancel order: {}", id, e);
            throw new OrderCancellationException("Failed to cancel order", e);
        }
    }

    private void validateAndReserveStock(OrderRequest orderRequest) {
        orderRequest.getItems().forEach(item -> {
            if (!productService.reserveStock(item.getProductId(), item.getQuantity())) {
                throw new InsufficientStockException(
                    "Insufficient stock for product: " + item.getProductId());
            }
        });
    }

    private void rollbackStockReservations(OrderRequest orderRequest) {
        orderRequest.getItems().forEach(item -> {
            try {
                productService.releaseStock(item.getProductId(), item.getQuantity());
            } catch (Exception e) {
                log.error("Failed to release stock for product: {}", item.getProductId(), e);
            }
        });
    }

    private void handleStatusSpecificActions(Order order, OrderStatus newStatus) {
        switch (newStatus) {
            case PAID:
                processSuccessfulPayment(order);
                break;
            case SHIPPED:
                initiateShipment(order);
                break;
            case DELIVERED:
                completeDelivery(order);
                break;
            case RETURNED:
                processReturn(order);
                break;
            default:
                // No specific action needed
        }
    }

    private void processSuccessfulPayment(Order order) {
        CompletableFuture.runAsync(() -> {
            notificationService.sendPaymentConfirmation(order);
            // Additional payment processing logic
        });
    }

    private void initiateShipment(Order order) {
        CompletableFuture.runAsync(() -> {
            notificationService.sendShipmentNotification(order);
            // Additional shipment processing logic
        });
    }

    private void completeDelivery(Order order) {
        order.setCompletedAt(LocalDateTime.now());
        CompletableFuture.runAsync(() -> {
            notificationService.sendDeliveryConfirmation(order);
            // Additional delivery completion logic
        });
    }

    private void processReturn(Order order) {
        CompletableFuture.runAsync(() -> {
            notificationService.sendReturnConfirmation(order);
            // Process refund and return logic
        });
    }

    // Additional interface method implementations...
}
