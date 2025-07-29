# Sample Integration Test Targets (Example)

| Class/Module Name            | Integration Area / Boundary      | Test Class Name               | Status      | Notes / Linked Workflows         |
|-----------------------------|---------------------------------|------------------------------|-------------|---------------------------------|
| UserService + UserRepository | Service to Repository            | UserServiceIntegrationTest    | Not Started | User Registration (`FunctionalWorkflowTemplate.md`) |
| OrderService + PaymentAPI    | Service to External API          | OrderServiceIntegrationTest   | In Progress | Order Processing (`FunctionalWorkflowTemplate.md`)  |
| UserController API          | Controller to REST API Layer     | UserControllerIntegrationTest | Completed   | API contract OpenAPI.yaml        |
