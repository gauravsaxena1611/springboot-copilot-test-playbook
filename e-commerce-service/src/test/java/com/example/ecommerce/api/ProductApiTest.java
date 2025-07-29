package com.example.ecommerce.api;

import com.example.ecommerce.dto.ProductRequest;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.test.BaseIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Tag("api")
class ProductApiTest extends BaseIntegrationTest {

    @Test
    @DisplayName("GET /api/v1/products - Should return paginated products")
    void getProducts_ShouldReturnPaginatedResults() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/v1/products")
                .param("page", "0")
                .param("size", "10")
                .param("sort", "name,asc"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", isA(Array.class)))
                .andExpect(jsonPath("$.totalElements", greaterThanOrEqualTo(0)))
                .andExpect(jsonPath("$.pageable.pageSize", is(10)));
    }

    @ParameterizedTest
    @CsvSource({
        "name,asc,$.content[0].name",
        "price,desc,$.content[0].price",
        "createdAt,desc,$.content[0].createdAt"
    })
    @DisplayName("GET /api/v1/products - Should support different sort options")
    void getProducts_ShouldSupportSorting(String sortField, String sortDir, String jsonPath) throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/v1/products")
                .param("sort", String.format("%s,%s", sortField, sortDir)))
                .andExpect(status().isOk())
                .andExpect(jsonPath(jsonPath).exists());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("POST /api/v1/products - Should create new product")
    void createProduct_WithValidData_ShouldSucceed() throws Exception {
        // Arrange
        ProductRequest request = new ProductRequest();
        request.setName("Test Product");
        request.setDescription("Test Description");
        request.setPrice(new BigDecimal("99.99"));
        request.setStockQuantity(100);

        // Act
        ResultActions result = mockMvc.perform(post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(request)));

        // Assert
        result.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name", is(request.getName())))
                .andExpect(jsonPath("$.price", is(99.99)));
    }

    @Test
    @DisplayName("GET /api/v1/products/search - Should return filtered results")
    void searchProducts_ShouldReturnFilteredResults() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/v1/products/search")
                .param("query", "test")
                .param("minPrice", "10.00")
                .param("maxPrice", "100.00")
                .param("category", "electronics"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[*]").exists())
                .andExpect(jsonPath("$.content[*].price", everyItem(greaterThanOrEqualTo(10.00))))
                .andExpect(jsonPath("$.content[*].price", everyItem(lessThanOrEqualTo(100.00))));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("PUT /api/v1/products/{id} - Should update existing product")
    void updateProduct_WithValidData_ShouldSucceed() throws Exception {
        // Arrange
        Long productId = 1L;
        ProductRequest request = new ProductRequest();
        request.setName("Updated Product");
        request.setPrice(new BigDecimal("199.99"));

        // Act & Assert
        mockMvc.perform(put("/api/v1/products/{id}", productId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(request.getName())))
                .andExpect(jsonPath("$.price", is(199.99)));
    }

    @Test
    @DisplayName("GET /api/v1/products/{id} - Should return 404 for non-existent product")
    void getProduct_WithNonExistentId_ShouldReturn404() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/v1/products/{id}", 999999))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("POST /api/v1/products - Should validate request data")
    void createProduct_WithInvalidData_ShouldReturn400() throws Exception {
        // Arrange
        ProductRequest request = new ProductRequest();
        // Missing required fields

        // Act & Assert
        mockMvc.perform(post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").exists())
                .andExpect(jsonPath("$.errors[*].field").value(hasItems("name", "price")));
    }

    @Test
    @DisplayName("GET /api/v1/products - Should handle server errors gracefully")
    void getProducts_WithServerError_ShouldReturn500() throws Exception {
        // This test requires setting up a condition that would trigger a server error
        // Usually done through mock configuration or test-specific configuration

        // Act & Assert
        mockMvc.perform(get("/api/v1/products")
                .header("X-Trigger-Error", "true")) // Custom header to trigger error in test
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.timestamp").exists());
    }
}
