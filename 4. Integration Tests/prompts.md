# Copilot/LLM Prompts for Integration & API Test Generation with Step 1–3 References

---

## 1. Identify Integration Test Targets Using Architecture and Workflow Templates

> From the component boundaries and service interactions documented in `ArchitectureDiagramTemplate.md` and workflows in `FunctionalWorkflowTemplate.md`, list critical integration points that require tests. Include service-to-repository and service-to-external API interactions.

**Maps to:** `IntegrationTestTargetsTemplate.md`

---

## 2. Scaffold Integration Test Classes for Service-Repositories

> For integration between service `[ServiceName]` and repository `[RepositoryName]` identified in `ServiceUtilityClassInventoryTemplate.md` and `ArchitectureDiagramTemplate.md`, generate a Spring Boot integration test class skeleton using `@SpringBootTest`, appropriate transactional annotations, and testcontainers if DB is external.

---

## 3. Generate Integration Test Cases Covering Success and Failure Scenarios

> For integration test class `[TestClassName]`, write tests that check correct combined behavior, including database CRUD operations, transaction management, and error handling as dictated by workflows from `FunctionalWorkflowTemplate.md`.

---

## 4. Generate API Test Class Skeleton Using MockMvc or TestRestTemplate

> Generate an API test class for controller `[ControllerName]` targeting REST endpoints documented in `FunctionalWorkflowTemplate.md` and/or OpenAPI specs. Include tests for happy paths and validations.

---

## 5. Write API Endpoint Tests for Status Codes, Validation, and Security

> For API endpoint `[Endpoint]`, generate JUnit tests covering:
> - HTTP success responses
> - Input validation errors
> - Authentication and authorization scenarios as specified in `ArchitectureDiagramTemplate.md`

---

## 6. Mock External Services per ExternalDependencyTemplate

> Mock external calls detailed in `ExternalDependencyTemplate.md` for tests in `[TestClassName]` to isolate logic while maintaining coverage.

---

## 7. Review & Refine Integration and API Tests

> Review test classes generated; provide suggestions to improve coverage and align tests with scenarios prioritized in `TeamGoalsTemplate.md`.

---

## 8. Summarize Integration and API Test Coverage with References

> Generate a coverage summary markdown based on reports and target lists from `IntegrationTestTargetsTemplate.md` and aligned with team goals in `TeamGoalsTemplate.md`.

**Maps to:** `IntegrationTestCoverageTemplate.md`

---

# Prompts Reference Table (Previous Steps)

| Prompt No. | Description                                    | Reference Step & Artifact                                    |
|------------|------------------------------------------------|------------------------------------------------------------|
| 1          | Identify integration test targets              | Step 1: `ArchitectureDiagramTemplate.md`, `FunctionalWorkflowTemplate.md` |
| 2          | Scaffold integration test classes               | Step 1: `ServiceUtilityClassInventoryTemplate.md`, `ArchitectureDiagramTemplate.md` |
| 3          | Generate integration test cases                  | Step 1: `FunctionalWorkflowTemplate.md`                      |
| 4          | API test skeleton using MockMvc/TestRestTemplate | Step 1: `FunctionalWorkflowTemplate.md`, API specs          |
| 5          | API endpoint validation & security tests          | Step 1: `ArchitectureDiagramTemplate.md`                     |
| 6          | Mocking external services                        | Step 1: `ExternalDependencyTemplate.md`                      |
| 7          | Review & refinement prompts                       | Step 1: `TeamGoalsTemplate.md`                               |
| 8          | Coverage summary                                  | Step 1: `TeamGoalsTemplate.md`, `IntegrationTestTargetsTemplate.md` |

---

# Usage Note

- Replace all placeholders `[ServiceName]`, `[RepositoryName]`, `[TestClassName]`, `[Endpoint]` with actual names from Step 1–3 documentation.
- Run iterative AI interactions with prompt refinements.
- Manually validate tests against documented workflows and goals.

---

# End of Prompts Library
