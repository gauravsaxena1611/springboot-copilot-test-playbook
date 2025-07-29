# Copilot/LLM Prompts for Unit Test Generation with Step 1 & 2 References

---

## 1. Identify Unit Test Targets (Reference Step 1 & 2 Inputs)

> Using the class and responsibility inventory in `ServiceUtilityClassInventoryTemplate.md`, list all service and utility classes with no or insufficient test coverage, considering test priorities noted in `TeamGoalsTemplate.md`.  Include their main responsibilities and associated workflows from `FunctionalWorkflowTemplate.md`.

**Maps to:** `UnitTestTargetsTemplate.md`

---

## 2. Generate Test Class Skeletons with Dependency Mocks (Based on Step 1 Dependencies)

> For the service class `[ClassName]` listed in `UnitTestTargetsTemplate.md`, generate a JUnit 5 test class skeleton using Mockito. Mock any external dependencies listed in `ExternalDependencyTemplate.md` and services noted as dependencies in `ServiceUtilityClassInventoryTemplate.md`.

**Maps to:** Test class skeletons in `/src/test/java/`

---

## 3. Create Happy Path Unit Test Methods Aligned with Workflows

> Generate JUnit 5 test methods for `[MethodName]` in `[ClassName]`, focusing on the key success scenarios described in the workflow `[WorkflowName]` found in `FunctionalWorkflowTemplate.md`. Include clear Arrange-Act-Assert comments.

**Maps to:** Individual test methods

---

## 4. Generate Negative and Edge Case Tests Leveraging Data Model

> Given the entity definitions and relationships from `DataModelTemplate.md`, generate unit tests for `[ClassName]` that check for null inputs, invalid data, exceptions, incorrect states, and boundary conditions relevant to `[EntityName]`.

---

## 5. Parameterized Test Generation

> Create parameterized JUnit 5 tests for `[ClassName].[MethodName]` focusing on varied valid and invalid inputs suggested by the entity fields and business logic in `DataModelTemplate.md` and use cases in `FunctionalWorkflowTemplate.md`.

--- 

## 6. Mock Setup

> Add `@Mock` and `@InjectMocks` to the test for `[ClassName]`, setting up a mock `[UserRepository]` and sample stub responses.

---

## 7. Review and Refine AI-Generated Tests

> Review the generated tests for `[ClassName]`. Suggest improvements for any missing edge cases or assertions, referencing coverage goals from `TeamGoalsTemplate.md`.

> Review all generated unit tests for `[ClassName]` and suggest improvements or additions, cross-referencing coverage priorities in `TeamGoalsTemplate.md`.

---

## 8. Mock External Calls According to Dependencies List

> Add Mockito mocks for all external dependencies to `[ClassName]`'s test, as listed in `ExternalDependencyTemplate.md`, ensuring isolated unit test execution.

> Add Mockito mocks and stub responses for all integrations listed in `ExternalDependencyTemplate.md`, ensuring tests isolate business logic from external dependencies.
---

## 9. Summarize Test Coverage Status

> Using your JaCoCo report, generate a markdown coverage summary that highlights coverage completeness for all classes referenced in `UnitTestTargetsTemplate.md` and set priorities for further testing consistent with `TeamGoalsTemplate.md`.

> Using your latest JaCoCo coverage report, generate a markdown summary highlighting coverage status for classes in `UnitTestTargetsTemplate.md` and aligning next steps with goals in `TeamGoalsTemplate.md`.
 
**Maps to:** `UnitTestCoverageTemplate.md`

---

## 10. Refinement and Review
> Review generated test methods and suggest any missing cases or improvements for readability and assertion clarity.

**Maps to:** UnitTestCoverageTemplate.md

---

# Reference Tables: Links to Previous Steps’ Prompts

| Prompt No. | Description                             | Source Step & Prompt                                                               |
|------------|-----------------------------------------|------------------------------------------------------------------------------------|
| 1          | Identify classes needing tests          | Step 1: `ServiceUtilityClassInventoryTemplate.md` & `TeamGoalsTemplate.md`         |
| 2          | Generate test skeletons with mocks      | Step 1: `ExternalDependencyTemplate.md`, `ServiceUtilityClassInventoryTemplate.md` |
| 3          | Happy path tests aligned with workflows | Step 1: `FunctionalWorkflowTemplate.md`                                            |
| 4          | Negative/edge tests from data model     | Step 1: `DataModelTemplate.md`                                                     |
| 5          | Parameterized tests from entity fields  | Step 1: `DataModelTemplate.md`                                                     |
| 6          | Review and test completeness suggestion | Step 1: `TeamGoalsTemplate.md`                                                     |
| 7          | Mock external calls                     | Step 1: `ExternalDependencyTemplate.md`                                            |
| 8          | Summarize coverage with goals           | Step 1: `TeamGoalsTemplate.md`                                                     |

---

# How to Use These Prompts

- Replace placeholder names (e.g., `[ClassName]`, `[MethodName]`, `[EntityName]`, `[WorkflowName]`) with values extracted from your Step 1 filled templates.
- Use these prompts iteratively together with your IDE Copilot or chat AI to generate and refine tests.
- Always review outputs manually and cross-check with Step 1 documentation for context accuracy.
- Validate outputs with human review referencing coverage and goals documents.

