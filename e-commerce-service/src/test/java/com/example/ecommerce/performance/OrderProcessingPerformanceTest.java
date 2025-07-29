package com.example.ecommerce.performance;

import com.example.ecommerce.dto.OrderRequest;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.service.OrderService;
import com.example.ecommerce.service.ProductService;
import com.example.ecommerce.test.BaseIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@Tag("performance")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql({"/data/users.sql", "/data/products.sql"})
class OrderProcessingPerformanceTest extends BaseIntegrationTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    private Product testProduct;
    private static final int CONCURRENT_USERS = 50;
    private static final int REQUESTS_PER_USER = 10;
    private static final Duration EXPECTED_RESPONSE_TIME = Duration.ofMillis(500);

    @BeforeEach
    void setUp() {
        // Create a test product with sufficient stock
        testProduct = new Product();
        testProduct.setName("Performance Test Product");
        testProduct.setPrice(new BigDecimal("99.99"));
        testProduct.setStockQuantity(CONCURRENT_USERS * REQUESTS_PER_USER * 2);
        testProduct = productService.createProduct(testProduct);
    }

    @Test
    void orderCreation_UnderLoad_ShouldMaintainResponseTime() throws InterruptedException {
        // Arrange
        ExecutorService executorService = Executors.newFixedThreadPool(CONCURRENT_USERS);
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(CONCURRENT_USERS);
        List<Duration> responseTimes = new CopyOnWriteArrayList<>();
        List<Future<List<Order>>> futures = new ArrayList<>();

        // Act
        for (int i = 0; i < CONCURRENT_USERS; i++) {
            futures.add(executorService.submit(() -> {
                List<Order> orders = new ArrayList<>();
                startLatch.await(); // Wait for all threads to be ready

                for (int j = 0; j < REQUESTS_PER_USER; j++) {
                    Instant start = Instant.now();

                    try {
                        OrderRequest request = createOrderRequest();
                        Order order = orderService.createOrder(request);
                        orders.add(order);
                    } finally {
                        Duration responseTime = Duration.between(start, Instant.now());
                        responseTimes.add(responseTime);
                    }

                    // Add random delay between requests
                    Thread.sleep(ThreadLocalRandom.current().nextLong(50, 150));
                }

                endLatch.countDown();
                return orders;
            }));
        }

        // Start all threads simultaneously
        startLatch.countDown();

        // Wait for all threads to complete
        boolean completed = endLatch.await(5, TimeUnit.MINUTES);
        executorService.shutdown();

        // Assert
        assertTrue(completed, "All requests should complete within timeout");

        // Calculate metrics
        double averageResponseTime = responseTimes.stream()
                .mapToLong(Duration::toMillis)
                .average()
                .orElse(0.0);

        long slowResponses = responseTimes.stream()
                .filter(duration -> duration.compareTo(EXPECTED_RESPONSE_TIME) > 0)
                .count();

        // Verify results
        assertTrue(averageResponseTime <= EXPECTED_RESPONSE_TIME.toMillis(),
                "Average response time (" + averageResponseTime + "ms) exceeds expected " + EXPECTED_RESPONSE_TIME.toMillis() + "ms");

        assertTrue(slowResponses <= responseTimes.size() * 0.05,
                "More than 5% of responses exceeded expected response time");

        // Verify all orders were created successfully
        long totalOrders = futures.stream()
                .map(future -> {
                    try {
                        return future.get().size();
                    } catch (Exception e) {
                        return 0;
                    }
                })
                .mapToLong(Integer::longValue)
                .sum();

        assertEquals(CONCURRENT_USERS * REQUESTS_PER_USER, totalOrders,
                "All orders should be created successfully");
    }

    @Test
    void productSearch_UnderLoad_ShouldMaintainPerformance() throws InterruptedException {
        // Arrange
        int numSearches = 1000;
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        List<Duration> responseTimes = new CopyOnWriteArrayList<>();
        CountDownLatch endLatch = new CountDownLatch(numSearches);

        // Act
        IntStream.range(0, numSearches).forEach(i ->
            executorService.submit(() -> {
                try {
                    Instant start = Instant.now();
                    productService.searchProducts("Test", null);
                    responseTimes.add(Duration.between(start, Instant.now()));
                } finally {
                    endLatch.countDown();
                }
            })
        );

        // Wait for completion
        boolean completed = endLatch.await(2, TimeUnit.MINUTES);
        executorService.shutdown();

        // Assert
        assertTrue(completed, "All searches should complete within timeout");

        double averageResponseTime = responseTimes.stream()
                .mapToLong(Duration::toMillis)
                .average()
                .orElse(0.0);

        assertTrue(averageResponseTime <= 100.0,
                "Average search response time should be under 100ms");
    }

    private OrderRequest createOrderRequest() {
        OrderRequest request = new OrderRequest();
        request.setUserId(1L); // From users.sql

        OrderRequest.OrderItemRequest item = new OrderRequest.OrderItemRequest();
        item.setProductId(testProduct.getId());
        item.setQuantity(2);

        request.getItems().add(item);
        return request;
    }
}
