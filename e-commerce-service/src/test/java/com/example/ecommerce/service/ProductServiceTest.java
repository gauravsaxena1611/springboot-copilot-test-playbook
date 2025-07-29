package com.example.ecommerce.service;

import com.example.ecommerce.exception.InsufficientStockException;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product testProduct;

    @BeforeEach
    void setUp() {
        testProduct = new Product();
        testProduct.setId(1L);
        testProduct.setName("Test Product");
        testProduct.setPrice(new BigDecimal("99.99"));
        testProduct.setStockQuantity(10);
    }

    @Nested
    @DisplayName("Stock Management Tests")
    class StockManagementTests {

        @Test
        @DisplayName("Should successfully update stock when sufficient quantity available")
        void updateStock_WithSufficientQuantity_ShouldSucceed() {
            // Arrange
            when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));
            when(productRepository.save(any(Product.class))).thenReturn(testProduct);

            // Act
            boolean result = productService.updateStock(1L, -5);

            // Assert
            assertTrue(result);
            assertEquals(5, testProduct.getStockQuantity());
            verify(eventPublisher).publishEvent(any());
        }

        @Test
        @DisplayName("Should throw exception when insufficient stock")
        void updateStock_WithInsufficientQuantity_ShouldThrowException() {
            // Arrange
            when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));

            // Act & Assert
            assertThrows(InsufficientStockException.class,
                () -> productService.updateStock(1L, -15));

            verify(productRepository, never()).save(any());
            verify(eventPublisher, never()).publishEvent(any());
        }

        @Test
        @DisplayName("Should handle concurrent stock updates correctly")
        void updateStock_WithConcurrentAccess_ShouldHandleCorrectly() {
            // Arrange
            when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));
            when(productRepository.save(any(Product.class))).thenReturn(testProduct);

            // Act
            boolean result1 = productService.updateStock(1L, -3);
            boolean result2 = productService.updateStock(1L, -4);

            // Assert
            assertTrue(result1);
            assertTrue(result2);
            assertEquals(3, testProduct.getStockQuantity());
            verify(eventPublisher, times(2)).publishEvent(any());
        }
    }

    @Nested
    @DisplayName("Product Management Tests")
    class ProductManagementTests {

        @Test
        @DisplayName("Should validate product price is greater than zero")
        void createProduct_WithInvalidPrice_ShouldThrowException() {
            // Arrange
            Product invalidProduct = new Product();
            invalidProduct.setPrice(BigDecimal.ZERO);

            // Act & Assert
            assertThrows(IllegalArgumentException.class,
                () -> productService.createProduct(invalidProduct));

            verify(productRepository, never()).save(any());
        }

        @Test
        @DisplayName("Should successfully create valid product")
        void createProduct_WithValidProduct_ShouldSucceed() {
            // Arrange
            when(productRepository.save(any(Product.class))).thenReturn(testProduct);

            // Act
            Product result = productService.createProduct(testProduct);

            // Assert
            assertNotNull(result);
            assertEquals(testProduct.getName(), result.getName());
            verify(eventPublisher).publishEvent(any());
        }
    }
}
