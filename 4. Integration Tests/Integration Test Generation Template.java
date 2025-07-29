/**
 * === INTEGRATION TEST GENERATION TEMPLATE ===
 *
 * TARGET COMPONENT: [Component/Service Name, e.g., com.example.app.service.OrderService]
 * TARGET FUNCTIONALITY: [High-level functionality being tested, e.g., End-to-end order placement and processing]
 * TEST TYPE: Integration Test (Spring Boot Test)
 *
 * === INTEGRATION SCOPE ===
 * Components Included:
 * - [Component 1, e.g., OrderService]: [Role in integration, e.g., orchestrates order flow]
 * - [Component 2, e.g., OrderRepository]: [Role in integration, e.g., persists order data to DB]
 * - [Component 3, e.g., InventoryService]: [Role in integration, e.g., checks and updates product stock (may be real or mocked)]
 *
 * External Systems (how they are handled):
 * - [External System 1, e.g., Payment Gateway]: [Mock/Real/TestContainer, e.g., Mocked using @MockBean]
 * - [External System 2, e.g., Email Service]: [Mock/Real/TestContainer, e.g., Mocked using @MockBean]
 *
 * === SPRING TEST CONFIGURATION ===
 * Test Slice: [Choose one: @SpringBootTest | @WebMvcTest | @DataJpaTest | @RestClientTest | Custom, e.g., @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)]
 * Profile: [Optional: test | integration | custom-profile, e.g., "test"]
 * Database: [Choose one: H2 (in-memory) | TestContainers | Embedded MySQL/PostgreSQL, e.g., H2]
 *
 * Required Test Properties:
 * ```properties
 * # Add any specific test properties here that override application.properties for this test
 * spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;MODE=PostgreSQL
 * spring.mail.host=localhost
 * spring.mail.port=2525 # Mock SMTP server for testing emails
 * app.inventory.check-enabled=true
 * ```
 *
 * === DATA SETUP STRATEGY ===
 * Database Initialization:
 * - [SQL scripts]: E.g., @Sql({"classpath:db/test-schema.sql", "classpath:db/test-data.sql"})
 * - [Entity creation]: E.g., Using @TestEntityManager or directly saving entities via repositories within @BeforeEach.
 * - [Test data builders]: E.g., Using custom `OrderBuilder` or `ProductFactory` classes for complex objects.
 *
 * Test Data Isolation:
 * - [@DirtiesContext]: [When and why to use, e.g., for tests that modify the application context or singletons. Specify ClassMode or MethodMode.]
 * - [@Transactional]: [Transaction management strategy, e.g., ensures each test runs in a transaction and rolls back.]
 * - [Custom cleanup]: [Manual cleanup requirements, e.g., deleting files, clearing queues if not handled by @Transactional.]
 *
 * === INTEGRATION SCENARIOS ===
 * 1. End-to-End Happy Path (Successful order placement)
 * - Description: [Full workflow description, e.g., User places an order, inventory is updated, payment processed, confirmation email sent.]
 * - Data Setup: [Required initial state, e.g., 'Database contains valid product with sufficient stock.']
 * - Actions: [Step-by-step actions, e.g., 'Call OrderService.placeOrder(orderRequest).']
 * - Verification: [What to verify across components, e.g., 'Order saved to DB with correct status, inventory reduced, paymentGateway.process() called, emailService.sendConfirmation() called.']
 *
 * 2. Cross-Component Error Handling (Payment failure)
 * - Description: [Error propagation scenario, e.g., Order initiated, but payment fails; system should rollback inventory.]
 * - Error Trigger: [How to simulate the error, e.g., 'Mock paymentGateway.process() to throw PaymentFailedException.']
 * - Expected Behavior: [How system should respond, e.g., 'Order status is FAILED, inventory is NOT reduced or is restored.']
 * - Verification: [Error handling verification, e.g., 'Order in DB has FAILED status, inventory count remains unchanged, PaymentFailedException is thrown to caller.']
 *
 * 3. Transaction Behavior (Partial failure rollback)
 * - Description: [Transaction boundary testing, e.g., Data is updated in one service, but a subsequent operation fails; previous updates should rollback.]
 * - Setup: [Transaction scenario setup, e.g., 'OrderService updates order, then calls InventoryService which throws an exception.']
 * - Expected: [Commit/rollback behavior, e.g., 'Order status should NOT be updated in DB, inventory should NOT be reduced.']
 * - Verification: [Data consistency checks, e.g., 'Verify order and inventory data in DB remain in their initial state.']
 *
 * === EXTERNAL SYSTEM MOCKING ===
 * Mock Configurations:
 * ```java
 * @MockBean // Use @MockBean for Spring managed mocks
 * private PaymentGatewayService paymentGatewayService;
 * @MockBean
 * private EmailService emailService;
 *
 * @BeforeEach
 * void setupMocks() {
 * // Mock setup for external dependencies
 * when(paymentGatewayService.processPayment(any())).thenReturn(new PaymentResponse("SUCCESS"));
 * doNothing().when(emailService).sendConfirmation(any());
 * }
 * ```
 *
 * === PERFORMANCE CONSIDERATIONS ===
 * - Expected execution time: [Time threshold, e.g., '< 5 seconds'. Integration tests are slower than unit tests.]
 * - Resource usage limits: [Memory, connections, e.g., 'Should not exhaust DB connections.']
 * - Concurrent access testing: [If applicable, e.g., 'Test concurrent order placements for race conditions.']
 *
 * === GENERATION REQUIREMENTS ===
 * - Use appropriate Spring Boot test annotations (@SpringBootTest, etc.).
 * - Include proper test configuration (properties, profiles).
 * - Set up test data in @BeforeEach, using @Sql, or via repositories.
 * - Clean up resources in @AfterEach or via @Transactional/@DirtiesContext if needed.
 * - Use TestContainers for external dependencies (database, message queue) if truly needed, otherwise @MockBean.
 * - Include proper assertions for integrated behavior, verifying data consistency across components.
 * - Test both success and failure scenarios, ensuring error propagation and rollback.
 * - Verify data persistence and retrieval using repository calls or direct DB queries.
 * - Ensure mocks are set up for external systems that are not part of the integration scope.
 *
 * Generate comprehensive integration tests following this specification.
 */