/**
 * === API TEST GENERATION TEMPLATE ===
 *
 * TARGET CONTROLLER: [ControllerClassName, e.g., com.example.app.controller.UserController]
 * TARGET ENDPOINTS: [List of endpoints to test, e.g., POST /api/users, GET /api/users/{id}, PUT /api/users/{id}, DELETE /api/users/{id}]
 * TEST TYPE: API/Web Layer Test (@WebMvcTest)
 *
 * === CONTROLLER CONTEXT ===
 * [Paste complete controller class here, including package, imports, all fields, constructor(s), and annotations.
 * Include relevant JavaDoc for the class and target methods.]
 *
 * === ENDPOINT SPECIFICATIONS ===
 *
 * Endpoint 1: POST /api/users
 * - Purpose: [What this endpoint does, e.g., Creates a new user]
 * - Request Body: [JSON structure or parameters, e.g., `{"email": "test@example.com", "password": "password123"}`]
 * - Response Body: [Expected JSON structure, e.g., `{"id": 1, "email": "test@example.com", "status": "ACTIVE"}`]
 * - Status Codes: [201 (Created), 400 (Bad Request), 409 (Conflict)]
 * - Security: [Authentication/authorization requirements, e.g., 'Requires no authentication for registration']
 *
 * Endpoint 2: GET /api/users/{id}
 * - Purpose: [What this endpoint does, e.g., Retrieves a user by ID]
 * - Path Variable: [id: Long]
 * - Response Body: [Expected JSON structure, e.g., `{"id": 1, "email": "test@example.com"}`]
 * - Status Codes: [200 (OK), 404 (Not Found)]
 * - Security: [Authentication/authorization requirements, e.g., 'Requires AUTHENTICATED user with ROLE_USER or ROLE_ADMIN']
 *
 * === SERVICE LAYER MOCKING ===
 * Services to Mock:
 * - [ServiceName, e.g., UserService]: [What methods need mocking, e.g., `createUser(UserDto)`, `findById(Long)`, `updateUser(Long, UserDto)`, `deleteUser(Long)`]
 *
 * Mock Setup Examples:
 * ```java
 * @MockBean // Use @MockBean for mocking service layer dependencies in @WebMvcTest
 * private UserService userService;
 *
 * @BeforeEach
 * void setupMocks() {
 * // Example mock behavior for userService
 * when(userService.findById(1L)).thenReturn(new UserDto(1L, "existing@example.com"));
 * when(userService.findById(99L)).thenThrow(new UserNotFoundException("User not found"));
 * when(userService.createUser(any(UserDto.class))).thenAnswer(invocation -> {
 * UserDto createdUser = invocation.getArgument(0);
 * createdUser.setId(UUID.randomUUID().getLeastSignificantBits()); // Simulate ID generation
 * return createdUser;
 * });
 * }
 * ```
 *
 * === REQUEST/RESPONSE SCENARIOS ===
 *
 * SUCCESS SCENARIOS:
 * 1. Valid POST Request - Happy Path (Create User)
 * - Request: `{"email": "newuser@example.com", "password": "securePassword"}`
 * - Expected Status: 201 (Created)
 * - Expected Response: `{"id": 123, "email": "newuser@example.com", "status": "ACTIVE"}`
 * - Service Behavior: `userService.createUser()` should return a UserDto.
 *
 * 2. Valid GET Request - Existing Resource (Get User by ID)
 * - Request: GET `/api/users/1`
 * - Expected Status: 200 (OK)
 * - Expected Response: `{"id": 1, "email": "existing@example.com"}`
 * - Service Behavior: `userService.findById(1L)` should return a UserDto.
 *
 * ERROR SCENARIOS:
 * 1. Invalid POST Request Data (Missing required field)
 * - Request: `{"password": "test"}` (missing email)
 * - Expected Status: 400 (Bad Request)
 * - Expected Response: `{"timestamp": "...", "status": 400, "error": "Bad Request", "message": "Email cannot be null"}` (or similar validation error structure)
 * - Validation: Spring's `@Valid` annotation should catch this, resulting in MethodArgumentNotValidException.
 *
 * 2. Resource Not Found (GET request for non-existent ID)
 * - Request: GET `/api/users/99`
 * - Expected Status: 404 (Not Found)
 * - Expected Response: `{"timestamp": "...", "status": 404, "error": "Not Found", "message": "User not found"}`
 * - Service Behavior: `userService.findById(99L)` throws `UserNotFoundException`.
 *
 * 3. Internal Server Error (Service layer exception)
 * - Request: Valid POST `{"email": "error@example.com", "password": "password"}`
 * - Expected Status: 500 (Internal Server Error)
 * - Expected Response: `{"timestamp": "...", "status": 500, "error": "Internal Server Error", "message": "An unexpected error occurred"}`
 * - Service Behavior: `userService.createUser()` throws a `RuntimeException`.
 *
 * === SECURITY TESTING (if applicable) ===
 * Authentication Tests:
 * - Unauthenticated requests (e.g., `POST /api/users` without token): Expected 401 (Unauthorized).
 * - Invalid token/credentials: Expected 401.
 * - Valid authentication (e.g., Bearer token): Expected normal processing.
 *
 * Authorization Tests (for role-based access):
 * - Insufficient permissions (e.g., User with `ROLE_USER` tries to access admin endpoint `GET /api/admin/users`): Expected 403 (Forbidden).
 * - Correct role/permissions (e.g., User with `ROLE_ADMIN` accesses admin endpoint): Expected normal processing.
 *
 * === VALIDATION TESTING ===
 * Request Validation (for POST/PUT requests):
 * - Required field missing (`@NotNull`, `@NotEmpty`)
 * - Invalid field format (`@Email`, `@Pattern`)
 * - Field length violations (`@Size`, `@Length`)
 * - Invalid enum values
 * - Custom business rule violations (e.g., custom `@UniqueEmail` annotation)
 *
 * === CONTENT TYPE TESTING ===
 * - JSON request/response (default: `application/json`)
 * - Form data (`application/x-www-form-urlencoded`) - if supported
 * - File upload (`multipart/form-data`) - if supported for specific endpoints
 * - XML if supported (`application/xml`) - less common for Spring REST
 *
 * === GENERATION REQUIREMENTS ===
 * - Use `@WebMvcTest` annotation targeting the specific controller.
 * - Inject `MockMvc` for performing HTTP requests.
 * - Mock all service/repository dependencies using `@MockBean`.
 * - Use `mockMvc.perform(get/post/put/delete(...))` for request execution.
 * - Use `andExpect(status().isOk())`, `andExpect(jsonPath("$.field").value("value"))` for response verification.
 * - Test all relevant HTTP status codes (2xx, 4xx, 5xx).
 * - Verify request/response headers if relevant.
 * - Include comprehensive security testing scenarios (401, 403) if applicable.
 * - Test request validation thoroughly for invalid inputs.
 * - Ensure proper JSON serialization/deserialization is handled.
 * - Include error response format verification (timestamp, status, error, message).
 * - Consider using a `ObjectMapper` to convert DTOs to JSON strings for request bodies.
 *
 * === SAMPLE TEST STRUCTURE (for reference) ===
 * ```java
 * @WebMvcTest(UserController.class)
 * @AutoConfigureMockMvc(addFilters = false) // Disable security filters for basic API testing if not testing security
 * class UserControllerTest {
 *
 * @Autowired
 * private MockMvc mockMvc;
 *
 * @MockBean
 * private UserService userService; // Mock the service layer
 *
 * // Example test for GET /api/users/{id}
 * @Test
 * void shouldReturnUserWhenValidIdProvided() throws Exception {
 * // Given
 * Long userId = 1L;
 * UserDto mockUserDto = new UserDto(userId, "test@example.com", "Active");
 * when(userService.findById(userId)).thenReturn(mockUserDto);
 *
 * // When & Then
 * mockMvc.perform(get("/api/users/{id}", userId)
 * .accept(MediaType.APPLICATION_JSON))
 * .andExpect(status().isOk())
 * .andExpect(jsonPath("$.id").value(userId))
 * .andExpect(jsonPath("$.email").value("test@example.com"));
 * }
 *
 * // Example test for POST /api/users
 * @Test
 * void shouldCreateUserSuccessfullyWhenValidRequest() throws Exception {
 * // Given
 * UserCreateRequest request = new UserCreateRequest("new@example.com", "password123");
 * UserDto createdUserDto = new UserDto(2L, "new@example.com", "Active");
 * when(userService.createUser(any(UserDto.class))).thenReturn(createdUserDto);
 *
 * // When & Then
 * mockMvc.perform(post("/api/users")
 * .contentType(MediaType.APPLICATION_JSON)
 * .content(new ObjectMapper().writeValueAsString(request)))
 * .andExpect(status().isCreated())
 * .andExpect(jsonPath("$.id").value(2L))
 * .andExpect(jsonPath("$.email").value("new@example.com"));
 * }
 * }
 * ```
 *
 * Generate comprehensive API tests following this specification.
 */