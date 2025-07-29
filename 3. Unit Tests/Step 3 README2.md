# Step 3: Unit Test Generation

## Purpose

This step guides you through generating, refining, and documenting high-quality unit tests for your Java Spring Boot application's core business logic and utilities.  

We leverage a hybrid approach combining manual expertise with AI-driven assistance (GitHub Copilot or other LLMs), and crucially

**Crucially, all activities in this step rely on the comprehensive project understanding documents and configurations generated in Steps 1 and 2.**

---

## Why Is This Step Important?

- **Unit tests ensure early bug detection and protect core business logic ensuring regression safety**
- **Guided test generation ensures coverage aligns with real project  workflows and components (from Step 1) improve relevance.**
- **Proper environment and dependencies setup (Step 2) ensure tests run reliably.**
- **Using Step 1 project context guarantees tests are relevant and maintainable.**
- **AI assistance (Copilot or other LLMs) accelerate generation when prompted with correct project context.**

---

## How Step 1 & 2 Templates & Prompts Are Used Here

The detailed project knowledge you documented in Step 1 is the backbone of effective unit test generation here:

| Referenced Artifact                                            | Role in Unit Test Generation                                                                      |
|----------------------------------------------------------------|---------------------------------------------------------------------------------------------------|
| `/introduction/inputs/ProjectInfoTemplate.md`                  | Identifies key modules/classes to prioritize testing focus and prioritization.                    |
| `/introduction/inputs/FunctionalWorkflowTemplate.md`           | Maps business workflows to service classes, ensuring tests reflect real scenarios.                |
| `/introduction/inputs/ArchitectureDiagramTemplate.md`          | Defines service layer and boundaries for  scope unit tests and mocking strategies.                |
| `/introduction/inputs/DataModelTemplate.md`                    | Provides entities and relationships to craft meaningful test inputs and mock dependencies.        |
| `/introduction/inputs/ServiceUtilityClassInventoryTemplate.md` | List of all relevant classes and dependencies to target for unit tests and their mock needs.      |
| `/introduction/inputs/ExternalDependencyTemplate.md`           | List of third-party systems and APIs that should be mocked during unit testing                    |
| `/introduction/inputs/TeamGoalsTemplate.md`                    | Sets prioritization and coverage goals for which classes to cover first per business milestones.  |
| `/setup/setup.md`                      | Confirms environment readiness for test execution                                                 |
| `/setup/test-dependencies.md`                      | Details test libraries/dependencies required                                                      |

**Using these templates, your unit tests will be focused, effective, and aligned with business needs.**

---

## Inputs (Must Have Before Starting)

- All Step 1 input templates listed below (preferably finalized and stored in `/introduction/inputs/`):
  - `/introduction/inputs/ProjectInfoTemplate.md`
  - `/introduction/inputs/FunctionalWorkflowTemplate.md`
  - `/introduction/inputs/ArchitectureDiagramTemplate.md`
  - `/introduction/inputs/DataModelTemplate.md`
  - `/introduction/inputs/ServiceUtilityClassInventoryTemplate.md`
  - `/introduction/inputs/ExternalDependencyTemplate.md`
  - `/introduction/inputs/TeamGoalsTemplate.md`

- Test dependencies configured in Step 2.
- Environment & tooling setup completed as per Step 2.
- A project build that compiles cleanly and optionally existing test coverage reports.

---

## Prerequisite Checklist

- [ ] Verified access to all Step 1 input templates.
- [ ] Environment established and documented per Step 2.
- [ ] Test dependencies installed and test framework working.
- [ ] Project builds without errors, existing tests pass if present.

---

## Step-by-Step Actions

### 1. Identify Unit Test Targets

- **Manual:**  
  Consult `/introduction/inputs/ServiceUtilityClassInventoryTemplate.md` to list all classes and methods.
   
  Prioritize classes based on business importance from `/introduction/inputs/FunctionalWorkflowTemplate.md` and `/introduction/inputs/TeamGoalsTemplate.md`.

  Review `ServiceUtilityClassInventoryTemplate.md` to identify classes and methods that require unit tests. Prioritize according to business workflows in `FunctionalWorkflowTemplate.md` and team goals from `TeamGoalsTemplate.md`.

- **Copilot/AI Assisted:**  
  Use monitoring prompts (see `/unit-tests/prompts.md`) to find test coverage gaps and detect classes lacking tests or with minimal coverage by referencing the inventory and workflows.

### 2. Generate Test Class Skeletons

- Use Copilot to generate test skeletons for each prioritized class.
- Mock dependencies described in `/introduction/inputs/ExternalDependencyTemplate.md` and `/introduction/inputs/ServiceUtilityClassInventoryTemplate.md`.
- Maintain naming conventions consistent with your architecture (as per Step 1 documentation).

### 3. Write Test Cases for Each Method

- Start with happy-path scenarios aligned with business workflows. 

  Happy path tests: Refer to workflows in `/introduction/inputs/FunctionalWorkflowTemplate.md`.
- Add negative and edge case tests.

  Negative and edge cases: Use entity details from `/introduction/inputs/DataModelTemplate.md`.

- Include Parameterized tests where applicable using domain data insights from Step 1.

### 4. Mock External Dependencies

- Use the external dependencies list from Step 1 (`ExternalDependencyTemplate.md`) to mock third-party calls i.e. calls to databases, external APIs, or third-party services. This ensures unit tests remain isolated.
- Use service dependencies from `ServiceUtilityClassInventoryTemplate.md` to mock collaborators.

### 5. Review and Refine

- Validate code and test accuracy.
- Compare test outputs to goals in `/introduction/inputs/TeamGoalsTemplate.md`.
- Refine Copilot-generated tests for clarity, coverage, and maintainability.
- Use Copilot for suggestions on test improvement, always followed by manual review.

### 6. Document Test Coverage and Gaps

- Update `/unit-tests/unit-test-coverage.md` summarizing progress aligned with `TeamGoalsTemplate.md`.
- Align coverage reports with prioritization documents from Step 1 and Step 2.

---

## Outputs

- `/src/test/java/` — Test classes for all services/utilities.
- `/unit-tests/unit-test-targets.md`: List of all unit test implementations, target classes and current status.
- `/unit-tests/unit-test-coverage.md`: Summary of coverage, gaps, and planned extensions aligned with Step 1 goals.
- Optional: Logs/screenshots of AI prompt results. Logs or screenshots of Copilot prompting (optional but helpful for audit).

---

## Checklist

- [ ] All service and utility classes listed in Step 1 are targeted by tests.
- [ ] Test skeletons generated for each target class.
- [ ] Happy path and edge case tests written as per workflows and entity models.
- [ ] External/internal dependencies mocked per Step 1 doc lists.
- [ ] Test coverage summarized and aligned with business/testing goals.
- [ ] AI-generated tests reviewed manually for quality.

---


## References Table (Previous Steps)

| File/Artifact                                                  | Where Used                                        |
|----------------------------------------------------------------|---------------------------------------------------|
| `/introduction/inputs/ProjectInfoTemplate.md`                  | Test target prioritization                        |
| `/introduction/inputs/FunctionalWorkflowTemplate.md`           | Test coverage scope definition                    |
| `/introduction/inputs/ArchitectureDiagramTemplate.md`          | Layer boundaries for unit test isolation          |
| `/introduction/inputs/DataModelTemplate.md`                    | Test input data shape and edge case definition    |
| `/introduction/inputs/ServiceUtilityClassInventoryTemplate.md` | Target classes inventory and dependencies mocking |
| `/introduction/inputs/ExternalDependencyTemplate.md`           | Mock configuration                                |
| `/introduction/inputs/TeamGoalsTemplate.md`                    | Prioritization and coverage goals tracking        |
| `/setup/setup.md`                                              | Environmental readiness confirmation              |
| `/setup/test-dependencies.md`                                  | Test dependencies verification                    |

---

## Prompts Library

Please refer to `/unit-tests/prompts.md` — all prompts **explicitly reference** Step 1 and Step 2 artifacts for contextual AI generation.

---

**Next Step:** Proceed to Step 4 for integration and API test generation once unit tests meet your quality and coverage criteria.
