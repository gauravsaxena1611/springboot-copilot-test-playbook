package com.example.ecommerce.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.containers.RedisContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration
public class TestContainersConfig {

    @Bean
    public PostgreSQLContainer<?> postgreSQLContainer() {
        PostgreSQLContainer<?> container = new PostgreSQLContainer<>(DockerImageName.parse("postgres:14-alpine"))
                .withDatabaseName("testdb")
                .withUsername("test")
                .withPassword("test");
        container.start();
        System.setProperty("spring.datasource.url", container.getJdbcUrl());
        System.setProperty("spring.datasource.username", container.getUsername());
        System.setProperty("spring.datasource.password", container.getPassword());
        return container;
    }

    @Bean
    public RedisContainer redisContainer() {
        RedisContainer container = new RedisContainer(DockerImageName.parse("redis:6-alpine"));
        container.start();
        System.setProperty("spring.redis.host", container.getHost());
        System.setProperty("spring.redis.port", container.getFirstMappedPort().toString());
        return container;
    }

    @Bean
    public RabbitMQContainer rabbitMQContainer() {
        RabbitMQContainer container = new RabbitMQContainer(DockerImageName.parse("rabbitmq:3-management-alpine"))
                .withQueue("orders")
                .withQueue("notifications");
        container.start();
        System.setProperty("spring.rabbitmq.host", container.getHost());
        System.setProperty("spring.rabbitmq.port", container.getAmqpPort().toString());
        return container;
    }
}
