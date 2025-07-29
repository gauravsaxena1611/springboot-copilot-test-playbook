package com.example.ecommerce.service.impl;

import com.example.ecommerce.event.ProductStockEvent;
import com.example.ecommerce.exception.ProductNotFoundException;
import com.example.ecommerce.exception.StockUpdateException;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final Lock stockUpdateLock = new ReentrantLock();

    @Override
    @Transactional
    public Product createProduct(Product product) {
        validateProduct(product);
        Product savedProduct = productRepository.save(product);
        eventPublisher.publishEvent(new ProductStockEvent(this, savedProduct.getId(), "CREATED", savedProduct.getStockQuantity()));
        return savedProduct;
    }

    @Override
    @Transactional
    @CacheEvict(value = "products", key = "#id")
    public Product updateProduct(Long id, Product product) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));

        updateProductFields(existingProduct, product);
        return productRepository.save(existingProduct);
    }

    @Override
    @Cacheable(value = "products", key = "#id")
    public Optional<Product> getProduct(Long id) {
        return productRepository.findById(id);
    }

    @Override
    @Transactional
    @Retryable(maxAttempts = 3)
    public boolean updateStock(Long productId, int quantity) {
        stockUpdateLock.lock();
        try {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productId));

            if (quantity < 0 && Math.abs(quantity) > product.getStockQuantity()) {
                throw new StockUpdateException("Insufficient stock for product: " + productId);
            }

            product.updateStock(quantity);
            productRepository.save(product);
            eventPublisher.publishEvent(new ProductStockEvent(this, productId, "UPDATED", product.getStockQuantity()));
            return true;
        } finally {
            stockUpdateLock.unlock();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Product> searchProducts(String query, Pageable pageable) {
        return productRepository.findByNameContainingOrDescriptionContainingAllIgnoreCase(query, query, pageable);
    }

    @Override
    @Transactional
    public boolean reserveStock(Long productId, int quantity) {
        stockUpdateLock.lock();
        try {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productId));

            if (product.getStockQuantity() < quantity) {
                return false;
            }

            product.updateStock(-quantity);
            productRepository.save(product);
            eventPublisher.publishEvent(new ProductStockEvent(this, productId, "RESERVED", product.getStockQuantity()));
            return true;
        } finally {
            stockUpdateLock.unlock();
        }
    }

    @Override
    @Transactional
    public void releaseStock(Long productId, int quantity) {
        updateStock(productId, quantity);
        eventPublisher.publishEvent(new ProductStockEvent(this, productId, "RELEASED", quantity));
    }

    private void validateProduct(Product product) {
        if (product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Product price must be greater than zero");
        }
        if (product.getStockQuantity() < 0) {
            throw new IllegalArgumentException("Product stock quantity cannot be negative");
        }
    }

    private void updateProductFields(Product existing, Product updated) {
        existing.setName(updated.getName());
        existing.setDescription(updated.getDescription());
        existing.setPrice(updated.getPrice());
        existing.setCategory(updated.getCategory());
        existing.setFeatured(updated.isFeatured());
        existing.setActive(updated.isActive());
    }

    // Other interface method implementations...
}
