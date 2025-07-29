package com.example.ecommerce.service;

import com.example.ecommerce.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    Product createProduct(Product product);
    Product updateProduct(Long id, Product product);
    Optional<Product> getProduct(Long id);
    Page<Product> searchProducts(String query, Pageable pageable);
    List<Product> getFeaturedProducts();
    void deleteProduct(Long id);
    boolean updateStock(Long productId, int quantity);
    List<Product> getProductsByCategory(Long categoryId);
    List<Product> getProductsByPriceRange(BigDecimal min, BigDecimal max);
    void updateProductRating(Long productId);
    boolean reserveStock(Long productId, int quantity);
    void releaseStock(Long productId, int quantity);
    List<Product> getRelatedProducts(Long productId);
    Page<Product> getProductsByTags(List<String> tags, Pageable pageable);
}
