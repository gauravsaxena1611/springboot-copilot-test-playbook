package com.example.ecommerce.regression;

import com.example.ecommerce.dto.OrderRequest;
import com.example.ecommerce.dto.ProductRequest;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.OrderStatus;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.service.OrderService;
import com.example.ecommerce.service.ProductService;
import com.example.ecommerce.test.BaseIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

@Tag("regression")
@Sql({"/data/users.sql", "/data/products.sql"})
class OrderWorkflowRegressionTest extends BaseIntegrationTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    private Product testProduct;
    private OrderRequest orderRequest;

    @BeforeEach
    void setUp() {
        // Create a test product with stock
        ProductRequest productRequest = new ProductRequest();
        productRequest.setName("Test Product");
        productRequest.setPrice(new BigDecimal("99.99"));
        productRequest.setStockQuantity(10);
        testProduct = productService.createProduct(Product.fromRequest(productRequest));

        // Prepare order request
        orderRequest = new OrderRequest();
        orderRequest.setUserId(1L); // Assuming user exists from users.sql
        OrderRequest.OrderItemRequest item = new OrderRequest.OrderItemRequest();
        item.setProductId(testProduct.getId());
        item.setQuantity(2);
        orderRequest.getItems().add(item);
    }

    @Test
    @DisplayName("Complete Order Workflow - Happy Path")
    void completeOrderWorkflow_HappyPath_ShouldSucceed() {
        // Step 1: Create Order
        Order order = orderService.createOrder(orderRequest);
        assertNotNull(order.getId());
        assertEquals(OrderStatus.CREATED, order.getStatus());

        // Step 2: Verify Stock Updated
        Product updatedProduct = productService.getProduct(testProduct.getId()).orElseThrow();
        assertEquals(8, updatedProduct.getStockQuantity());

        // Step 3: Process Payment
        Order paidOrder = orderService.processPayment(order.getId(), "CREDIT_CARD");
        assertEquals(OrderStatus.PAID, paidOrder.getStatus());

        // Step 4: Confirm Delivery
        Order deliveredOrder = orderService.confirmDelivery(order.getId());
        assertEquals(OrderStatus.DELIVERED, deliveredOrder.getStatus());

        // Step 5: Complete Order
        Order completedOrder = orderService.updateOrderStatus(order.getId(), OrderStatus.COMPLETED);
        assertEquals(OrderStatus.COMPLETED, completedOrder.getStatus());
        assertNotNull(completedOrder.getCompletedAt());
    }

    @Test
    @DisplayName("Order Cancellation Workflow")
    void orderCancellationWorkflow_ShouldRestoreStock() {
        // Step 1: Create Order
        Order order = orderService.createOrder(orderRequest);
        int initialStock = testProduct.getStockQuantity();

        // Step 2: Cancel Order
        boolean cancelled = orderService.cancelOrder(order.getId());
        assertTrue(cancelled);

        // Step 3: Verify Stock Restored
        await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
            Product updatedProduct = productService.getProduct(testProduct.getId()).orElseThrow();
            assertEquals(initialStock, updatedProduct.getStockQuantity());
        });

        // Step 4: Verify Order Status
        Order cancelledOrder = orderService.getOrder(order.getId()).orElseThrow();
        assertEquals(OrderStatus.CANCELLED, cancelledOrder.getStatus());
    }

    @Test
    @DisplayName("Order Return Workflow")
    void orderReturnWorkflow_ShouldHandleRefundAndStock() {
        // Step 1: Create and Complete Order
        Order order = orderService.createOrder(orderRequest);
        order = orderService.processPayment(order.getId(), "CREDIT_CARD");
        order = orderService.confirmDelivery(order.getId());

        // Step 2: Initiate Return
        Order returnedOrder = orderService.initiateReturn(order.getId(), "Defective product");
        assertEquals(OrderStatus.RETURNED, returnedOrder.getStatus());

        // Step 3: Verify Stock Updated
        await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
            Product updatedProduct = productService.getProduct(testProduct.getId()).orElseThrow();
            assertEquals(10, updatedProduct.getStockQuantity());
        });
    }

    @Test
    @DisplayName("Multiple Order Processing Workflow")
    void multipleOrderProcessing_ShouldMaintainConsistency() {
        // Step 1: Create Multiple Orders
        Order order1 = orderService.createOrder(orderRequest);
        Order order2 = orderService.createOrder(orderRequest);

        // Step 2: Process Orders in Different States
        orderService.processPayment(order1.getId(), "CREDIT_CARD");
        orderService.cancelOrder(order2.getId());

        // Step 3: Verify Final States
        Order finalOrder1 = orderService.getOrder(order1.getId()).orElseThrow();
        Order finalOrder2 = orderService.getOrder(order2.getId()).orElseThrow();

        assertEquals(OrderStatus.PAID, finalOrder1.getStatus());
        assertEquals(OrderStatus.CANCELLED, finalOrder2.getStatus());

        // Step 4: Verify Stock Consistency
        Product finalProduct = productService.getProduct(testProduct.getId()).orElseThrow();
        assertEquals(8, finalProduct.getStockQuantity());
    }
}
