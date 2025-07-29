package com.example.ecommerce.controller;

import com.example.ecommerce.dto.OrderRequest;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.OrderStatus;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.security.JwtTokenProvider;
import com.example.ecommerce.test.BaseIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class OrderControllerIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private User testUser;
    private String userToken;

    @BeforeEach
    void setUp() {
        // Clean up data
        orderRepository.deleteAll();
        userRepository.deleteAll();

        // Create test user
        testUser = createTestUser();
        userToken = jwtTokenProvider.generateToken(testUser.getUsername());
    }

    @Nested
    @DisplayName("Create Order Tests")
    class CreateOrderTests {

        @Test
        @DisplayName("Should successfully create order for authenticated user")
        void createOrder_WithValidRequest_ShouldSucceed() throws Exception {
            // Arrange
            OrderRequest request = createSampleOrderRequest();

            // Act
            ResultActions result = mockMvc.perform(post("/api/v1/orders")
                    .header("Authorization", "Bearer " + userToken)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(request)));

            // Assert
            result.andExpect(status().isCreated())
                    .andExpect(jsonPath("$.status", is(OrderStatus.CREATED.name())))
                    .andExpect(jsonPath("$.userId", is(testUser.getId().intValue())));

            // Verify database state
            List<Order> orders = orderRepository.findByUserId(testUser.getId());
            assertEquals(1, orders.size());
        }

        @Test
        @DisplayName("Should fail to create order without authentication")
        void createOrder_WithoutAuth_ShouldFail() throws Exception {
            // Arrange
            OrderRequest request = createSampleOrderRequest();

            // Act & Assert
            mockMvc.perform(post("/api/v1/orders")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(request)))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        @DisplayName("Should fail to create order with invalid data")
        void createOrder_WithInvalidData_ShouldFail() throws Exception {
            // Arrange
            OrderRequest request = new OrderRequest(); // Empty request

            // Act & Assert
            mockMvc.perform(post("/api/v1/orders")
                    .header("Authorization", "Bearer " + userToken)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(request)))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("Order Status Update Tests")
    class OrderStatusUpdateTests {

        @Test
        @DisplayName("Should successfully update order status")
        void updateOrderStatus_WithValidStatus_ShouldSucceed() throws Exception {
            // Arrange
            Order order = createAndSaveTestOrder();

            // Act
            ResultActions result = mockMvc.perform(put("/api/v1/orders/{id}/status", order.getId())
                    .header("Authorization", "Bearer " + userToken)
                    .param("status", OrderStatus.CONFIRMED.name()));

            // Assert
            result.andExpect(status().isOk())
                    .andExpect(jsonPath("$.status", is(OrderStatus.CONFIRMED.name())));

            // Verify database state
            Order updatedOrder = orderRepository.findById(order.getId()).orElseThrow();
            assertEquals(OrderStatus.CONFIRMED, updatedOrder.getStatus());
        }
    }

    private User createTestUser() {
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword(passwordEncoder.encode("password"));
        user.setEnabled(true);
        return userRepository.save(user);
    }

    private OrderRequest createSampleOrderRequest() {
        OrderRequest request = new OrderRequest();
        request.setUserId(testUser.getId());

        OrderRequest.OrderItemRequest item = new OrderRequest.OrderItemRequest();
        item.setProductId(1L);
        item.setQuantity(2);
        item.setUnitPrice(new BigDecimal("99.99"));

        request.setItems(new ArrayList<>(List.of(item)));
        return request;
    }

    private Order createAndSaveTestOrder() {
        Order order = new Order();
        order.setUser(testUser);
        order.setStatus(OrderStatus.CREATED);
        return orderRepository.save(order);
    }
}
