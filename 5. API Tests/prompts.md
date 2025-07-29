# Copilot / LLM Prompts for API Test Generation with Step 1–4 References

---

## 1. Identify API Endpoints for Testing from Controllers & Workflows

> Using the REST controllers and functional workflows documented in `ArchitectureDiagramTemplate.md` and `FunctionalWorkflowTemplate.md`, generate a comprehensive list of all API endpoints with HTTP methods, describing their business purposes.

**Maps to:** `APITestTargetsTemplate.md`

---

## 2. Generate API Test Class Skeletons Using MockMvc or TestRestTemplate

> Generate a Spring Boot test class skeleton for the controller `[ControllerName]`, setting up MockMvc (or TestRestTemplate) for simulating HTTP requests and responses.

---

## 3. Create API Test Cases for Valid Requests

> For the `[HTTP METHOD]` endpoint `[URL]` in `[ControllerName]`, generate detailed test methods validating successful use cases, including expected status codes, response payloads, and headers. Include Arrange-Act-Assert comments.

---

## 4. Create API Test Cases for Validation Errors and Negative Inputs

> Generate test methods for `[ControllerName]` endpoint `[URL]` that send invalid, missing, or malformed data, verifying proper validation errors and response codes.

---

## 5. Generate Authentication and Authorization Test Cases

> Write test methods that assert correct behavior of `[ControllerName]` endpoints `[URL]` under different authentication and role-based scenarios, verifying access control enforcement.

---

## 6. Mock or Stub External Dependencies for API Tests

> For API tests involving external systems as per `ExternalDependencyTemplate.md`, generate unit test mocks or stubs allowing isolated endpoint testing without real calls.

---

## 7. Document API Test Scenarios

> Using `[APITestScenarioTemplate.md]`, generate detailed test scenario documentation for the endpoint `[URL]`, including preconditions, requests, mocks, expected responses, and side effects.

---

## 8. Summarize API Test Coverage with Reference to Team Goals

> Generate a coverage summary highlighting tested endpoints and missing scenarios, prioritizing based on goals defined in `TeamGoalsTemplate.md` and coverage reports from integration and unit tests.

**Maps to:** `APITestCoverageTemplate.md`

---

## Prompt Reference Table

| Prompt No.  | Description                               | Reference Step & Artifact                                                 |
|-------------|-------------------------------------------|---------------------------------------------------------------------------|
| 1           | Identify API endpoints                    | Step 1: `ArchitectureDiagramTemplate.md`, `FunctionalWorkflowTemplate.md` |
| 2           | Scaffold API test classes                 | Step 1: `ArchitectureDiagramTemplate.md`, Step 2 environment context      |
| 3           | Generate success case API tests           | Step 1: `FunctionalWorkflowTemplate.md`                                   |
| 4           | Generate validation error API tests       | Step 1: validation rules as per controller/service                        |
| 5           | Generate authz/authn API tests            | Step 1: `ArchitectureDiagramTemplate.md`, security context                |
| 6           | Mock external dependencies                | Step 1: `ExternalDependencyTemplate.md`                                   |
| 7           | Document API scenarios                    | From `APITestScenarioTemplate.md`                                         |
| 8           | Summarize coverage & gaps                 | Step 1: `TeamGoalsTemplate.md`, `APITestCoverageTemplate.md`              |

---

### Usage Notes

- Replace placeholders such as `[ControllerName]`, `[URL]`, `[HTTP METHOD]` with actual API data derived from Step 1 inputs.
- Leverage iterative ChatGPT/Copilot interactions to generate and refine tests.
- Always manually review and cross-check against API contracts and workflows.

---
