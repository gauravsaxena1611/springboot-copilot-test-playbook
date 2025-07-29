package com.example.ecommerce.service;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.test.BaseIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Sql("/data/products.sql")
class ConcurrentOperationsTest extends BaseIntegrationTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    private Product testProduct;
    private static final int THREAD_COUNT = 10;
    private static final int STOCK_DECREASE_PER_THREAD = 2;

    @BeforeEach
    void setUp() {
        testProduct = new Product();
        testProduct.setName("Concurrent Test Product");
        testProduct.setPrice(new BigDecimal("99.99"));
        testProduct.setStockQuantity(THREAD_COUNT * STOCK_DECREASE_PER_THREAD);
        testProduct = productRepository.save(testProduct);
    }

    @Test
    @DisplayName("Should handle concurrent stock updates correctly")
    void concurrentStockUpdates_ShouldMaintainConsistency() throws InterruptedException {
        // Arrange
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(THREAD_COUNT);
        List<Exception> exceptions = new ArrayList<>();

        // Act
        for (int i = 0; i < THREAD_COUNT; i++) {
            executorService.submit(() -> {
                try {
                    startLatch.await(); // Wait for all threads to be ready
                    productService.updateStock(testProduct.getId(), -STOCK_DECREASE_PER_THREAD);
                } catch (Exception e) {
                    exceptions.add(e);
                } finally {
                    endLatch.countDown();
                }
            });
        }

        // Start all threads simultaneously
        startLatch.countDown();

        // Wait for all threads to complete
        boolean completed = endLatch.await(10, TimeUnit.SECONDS);
        executorService.shutdown();

        // Assert
        assertEquals(0, exceptions.size(), "No exceptions should occur during concurrent updates");
        assertEquals(true, completed, "All threads should complete within timeout");

        // Verify final stock quantity
        Product updatedProduct = productRepository.findById(testProduct.getId()).orElseThrow();
        assertEquals(0, updatedProduct.getStockQuantity(),
            "Stock should be zero after all concurrent updates");
    }

    @Test
    @DisplayName("Should handle concurrent order creation correctly")
    void concurrentOrderCreation_WithLimitedStock_ShouldMaintainConsistency() throws InterruptedException {
        // Arrange
        int initialStock = 5; // Limited stock
        testProduct.setStockQuantity(initialStock);
        productRepository.save(testProduct);

        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(THREAD_COUNT);
        List<Boolean> successfulReservations = new ArrayList<>();

        // Act
        for (int i = 0; i < THREAD_COUNT; i++) {
            executorService.submit(() -> {
                try {
                    startLatch.await();
                    boolean reserved = productService.reserveStock(testProduct.getId(), 1);
                    synchronized (successfulReservations) {
                        successfulReservations.add(reserved);
                    }
                } catch (Exception e) {
                    // Log exception
                } finally {
                    endLatch.countDown();
                }
            });
        }

        startLatch.countDown();
        endLatch.await(10, TimeUnit.SECONDS);
        executorService.shutdown();

        // Assert
        long successfulCount = successfulReservations.stream().filter(success -> success).count();
        assertEquals(initialStock, successfulCount,
            "Number of successful reservations should match initial stock");

        Product finalProduct = productRepository.findById(testProduct.getId()).orElseThrow();
        assertEquals(0, finalProduct.getStockQuantity(),
            "Stock should be zero after all successful reservations");
    }

    @Test
    @DisplayName("Should handle concurrent cache updates correctly")
    void concurrentCacheUpdates_ShouldMaintainConsistency() throws InterruptedException {
        // Arrange
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(THREAD_COUNT);
        List<Product> retrievedProducts = new ArrayList<>();

        // Prime the cache
        productService.getProduct(testProduct.getId());

        // Act
        for (int i = 0; i < THREAD_COUNT; i++) {
            final int updateValue = i;
            executorService.submit(() -> {
                try {
                    startLatch.await();
                    // Update product
                    testProduct.setStockQuantity(updateValue);
                    Product updated = productService.updateProduct(testProduct.getId(), testProduct);
                    // Read product (should be from cache)
                    productService.getProduct(testProduct.getId())
                        .ifPresent(retrievedProducts::add);
                } catch (Exception e) {
                    // Log exception
                } finally {
                    endLatch.countDown();
                }
            });
        }

        startLatch.countDown();
        endLatch.await(10, TimeUnit.SECONDS);
        executorService.shutdown();

        // Assert
        assertEquals(THREAD_COUNT, retrievedProducts.size(),
            "All threads should retrieve a product");

        // Verify cache consistency
        Product finalCachedProduct = productService.getProduct(testProduct.getId()).orElseThrow();
        Product finalDbProduct = productRepository.findById(testProduct.getId()).orElseThrow();
        assertEquals(finalDbProduct.getStockQuantity(), finalCachedProduct.getStockQuantity(),
            "Cache and DB should be consistent");
    }
}
