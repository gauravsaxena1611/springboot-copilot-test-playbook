# Copilot Prompts for Validation & Quality Assurance

## 1. Documentation Completeness & Consistency

> Review the following markdown section from `ProjectInfoTemplate.md`. Is anything essential missing? Suggest improvements or additional sections needed for clarity.

---

> Compare references listed in the following templates: `FunctionalWorkflowTemplate.md` and `ServiceUtilityClassInventoryTemplate.md`. Highlight any missing cross-references or inconsistencies.

---

## 2. Code Quality & Static Analysis

> Here's a Checkstyle report summary and a snippet of Java service code. Suggest potential refactorings or identify anti-patterns based on the report and code snippet.

---

> Generate a set of static analysis rules specific to a Spring Boot project that would help enforce code validation standards.

---

## 3. Test Coverage Validation

> Given this JaCoCo coverage report snippet, generate a markdown summary highlighting classes or methods with insufficient coverage and suggest next testing priorities based on `TeamGoalsTemplate.md`.

---

> Review the following unit test methods. Suggest additional edge cases or boundary tests that should be added, considering `DataModelTemplate.md` entity relations.

---

## 4. Cross-Artifact Traceability

> Write a script snippet or pseudocode that verifies every workflow listed in `FunctionalWorkflowTemplate.md` has a corresponding entry in `UnitTestTargetsTemplate.md` and `IntegrationTestTargetsTemplate.md`.

---

> Summarize the relationships between `ExternalDependencyTemplate.md` and the mocks used in test classes documented in `UnitTestTargetsTemplate.md`.

---

## 5. CI/CD Validation

> Generate a YAML snippet for a GitHub Actions workflow that runs all tests and enforces coverage thresholds before merging.

---

> Analyze this failing pipeline log (insert log snippet). Suggest likely causes and remediation steps.

---

## 6. AI-Generated Output Validation

> Review this AI-generated test class against the `UnitTestTargetsTemplate.md` and `FunctionalWorkflowTemplate.md`. Identify missing tests or inconsistencies.

---

> Generate a checklist for reviewing AI-generated prompts and outputs to ensure they align with project context and quality standards.

---

# How to Use These Prompts

- Copy relevant prompt sections to GitHub Copilot Chat in your IDE.
- Paste source template or code snippets when requested.
- Review Copilot output critically; use it to guide manual validation.
- Iterate with clarifying prompts as needed.

---

# Reference Table for Validation Prompts

| Prompt Section                       | Usage Area                        | References                            |
|--------------------------------------|-----------------------------------|---------------------------------------|
| Documentation Completeness           | Documentation review              | Step 1 project and workflow templates |
| Code Quality & Static Analysis       | Code review & refactoring         | Step 2 environment and build config   |
| Test Coverage & Edge Case Suggestion | Test validation                   | Step 3-6 test coverage and templates  |
| Cross-Artifact Traceability          | Consistency checks                | Step 1 all templates & Step 3-5 tests |
| CI/CD Validation                     | Pipeline & automation validation  | Step 2 configs, Step 6 execution docs |
| AI Output Validation                 | Review AI-generated code and docs | Step 1-6 AI generated artifacts       |

