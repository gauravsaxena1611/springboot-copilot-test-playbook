package com.example.ecommerce.service;

import com.example.ecommerce.dto.OrderRequest;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order createOrder(OrderRequest orderRequest);
    Optional<Order> getOrder(Long id);
    Order updateOrderStatus(Long id, OrderStatus status);
    boolean cancelOrder(Long id);
    Page<Order> getUserOrders(Long userId, Pageable pageable);
    Order processPayment(Long orderId, String paymentMethod);
    Order confirmDelivery(Long orderId);
    Order initiateReturn(Long orderId, String reason);
    List<Order> findOrdersByStatus(OrderStatus status);
    boolean validateOrder(Long orderId);
    void sendOrderConfirmation(Long orderId);
    Order applyDiscount(Long orderId, String couponCode);
    Order recalculateOrderTotal(Long orderId);
    void scheduleDelivery(Long orderId);
}
