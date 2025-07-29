# Copilot / LLM Prompts for Integration Test Generation with Step 1–3 References

---

## 1. Identify Integration Test Targets from Architecture and Workflows

> Using the component boundaries and workflows documented in `ArchitectureDiagramTemplate.md` and `FunctionalWorkflowTemplate.md`, list key integration points including service-to-repository and inter-service interactions. Map these to expected integration test classes.

**Maps to:** `IntegrationTestTargetsTemplate.md`

---

## 2. Scaffold Integration Test Classes for Service-Repository Integration

> Generate a Spring Boot `@SpringBootTest` class skeleton for the service `[ServiceName]` integrating with repository `[RepositoryName]`, including transactional management and testcontainer setup if applicable.

---

## 3. Write Integration Tests for Combined Component Behavior

> For `[TestClassName]`, write integration test cases that verify workflow success, transactional boundaries, error handling, and side effects in accordance with scenarios from `FunctionalWorkflowTemplate.md`.

---

## 4. Generate Integration Test for Service-to-External API Interaction

> Scaffold an integration test that mocks or uses a stub for external API `[ExternalServiceName]` as defined in `ExternalDependencyTemplate.md` and captures failure and success responses properly.

---

## 5. Document Test Scenarios as Readable Markdown

> Create detailed test scenario descriptions based on requirements for `[TestClassName]` using the `TestScenarioTemplate.md` format, explaining purpose, preconditions, steps, and expected outcomes.

---

## 6. Review and Suggest Improvements for Integration Tests

> Review existing integration tests for `[ServiceName]`, suggest improvements or missing critical cases based on coverage goals in `TeamGoalsTemplate.md`.

---

## 7. Summarize Integration Test Coverage

> From test run results and coverage tools, generate a markdown summary aligned to `[IntegrationTestTargetsTemplate.md]` and `TeamGoalsTemplate.md`, highlighting gaps and next priorities.

**Maps to:** `IntegrationTestCoverageTemplate.md`

---

# Prompt Reference Table for Integration Test Step

| Prompt No. | Description                                    | Reference Step & Artifact                                    |
|------------|------------------------------------------------|------------------------------------------------------------|
| 1          | Identify integration test targets              | Step 1: `ArchitectureDiagramTemplate.md`, `FunctionalWorkflowTemplate.md` |
| 2          | Scaffold service-repository integration test   | Step 1: `ServiceUtilityClassInventoryTemplate.md`, `ArchitectureDiagramTemplate.md` |
| 3          | Write combined component integration tests      | Step 1: `FunctionalWorkflowTemplate.md`                      |
| 4          | External API integration test scaffolding        | Step 1: `ExternalDependencyTemplate.md`                      |
| 5          | Document detailed test scenarios                 | Step 1: `FunctionalWorkflowTemplate.md`                      |
| 6          | Review and improve integration tests             | Step 1: `TeamGoalsTemplate.md`                               |
| 7          | Summarize integration test coverage              | Step 1: `TeamGoalsTemplate.md`, `IntegrationTestTargetsTemplate.md` |

---

## Usage Notes

- Replace placeholders such as `[ServiceName]`, `[RepositoryName]`, `[ExternalServiceName]`, `[TestClassName]` with actual names from Step 1 and 2 documents.
- Utilize iterative prompting with Copilot/LLM to refine each test.
- Always cross-validate generated tests with your Step 1 workflows and team goals.

---

# End of Integration Tests Prompts Library
