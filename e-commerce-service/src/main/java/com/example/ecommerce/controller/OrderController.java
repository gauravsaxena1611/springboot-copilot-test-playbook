package com.example.ecommerce.controller;

import com.example.ecommerce.dto.OrderRequest;
import com.example.ecommerce.dto.OrderResponse;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.OrderStatus;
import com.example.ecommerce.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @Operation(summary = "Create a new order", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderRequest orderRequest) {
        Order order = orderService.createOrder(orderRequest);
        return ResponseEntity
            .created(URI.create("/api/v1/orders/" + order.getId()))
            .body(OrderResponse.fromOrder(order));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get order by ID", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable Long id) {
        return orderService.getOrder(id)
            .map(OrderResponse::fromOrder)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Update order status", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OrderResponse> updateOrderStatus(
            @PathVariable Long id,
            @RequestParam OrderStatus status) {
        Order updatedOrder = orderService.updateOrderStatus(id, status);
        return ResponseEntity.ok(OrderResponse.fromOrder(updatedOrder));
    }

    @PostMapping("/{id}/cancel")
    @Operation(summary = "Cancel an order", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long id) {
        boolean cancelled = orderService.cancelOrder(id);
        return cancelled ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get user's orders", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasRole('USER') and @userSecurity.isOwner(#userId)")
    public ResponseEntity<Page<OrderResponse>> getUserOrders(
            @PathVariable Long userId,
            Pageable pageable) {
        Page<Order> orders = orderService.getUserOrders(userId, pageable);
        return ResponseEntity.ok(orders.map(OrderResponse::fromOrder));
    }

    @PostMapping("/{id}/delivery/confirm")
    @Operation(summary = "Confirm order delivery", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasRole('USER') and @orderSecurity.canConfirmDelivery(#id)")
    public ResponseEntity<OrderResponse> confirmDelivery(@PathVariable Long id) {
        Order order = orderService.confirmDelivery(id);
        return ResponseEntity.ok(OrderResponse.fromOrder(order));
    }

    @PostMapping("/{id}/return")
    @Operation(summary = "Initiate order return", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasRole('USER') and @orderSecurity.canInitiateReturn(#id)")
    public ResponseEntity<OrderResponse> initiateReturn(
            @PathVariable Long id,
            @RequestParam String reason) {
        Order order = orderService.initiateReturn(id, reason);
        return ResponseEntity.ok(OrderResponse.fromOrder(order));
    }
}
