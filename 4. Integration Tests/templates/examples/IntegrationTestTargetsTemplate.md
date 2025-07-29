# Integration Test Targets

| Class/Module                 | Integration Area              | Test Class                   | Status       | Notes / Linked Workflows          |
|------------------------------|-------------------------------|------------------------------|--------------|-----------------------------------|
| UserService + UserRepository | Service-Repository boundary   | UserServiceIntegrationTest   | Not Started  | User Registration Workflow        |
| OrderService + PaymentAPI    | Service-External API          | OrderServiceIntegrationTest  | In Progress  | Order Processing Workflow         |
| UserController API           | REST API Endpoint             | UserControllerApiTest        | Completed    | API contract `OpenAPI.yaml`       |
| ...                          |                               |                              |              |                                   |
---

## Previous Step References:

- Service boundaries: `ArchitectureDiagramTemplate.md`
- Workflows: `FunctionalWorkflowTemplate.md`
- External systems: `ExternalDependencyTemplate.md`
- Priorities: `TeamGoalsTemplate.md`
