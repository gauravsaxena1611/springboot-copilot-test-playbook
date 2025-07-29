# Copilot Prompts for Validation Assistance: How To


### How to Use Copilot for Validation

- Highlight or copy sample template sections, code snippets, or test reports in your IDE.
- Open Copilot Chat or use inline suggestions.
- Input prompts designed to get validation feedback, consistency checks, or summary generation.
- Review all outputs manually and iterate with clarifying prompts.
- Use iterative refinement to improve the quality and completeness of suggested fixes or reports.


### Validation Prompts Examples for Copilot

## 1. Documentation Validation

> Review this markdown content from `FunctionalWorkflowTemplate.md`. Suggest any missing sections, unclear explanations, or inconsistencies with project goals.

---

## 2. Code Quality Review

> Given this Java service class snippet and latest SonarQube report summary, suggest any refactorings or warnings developers should address.

---

## 3. Test Coverage Analysis

> Summarize this JaCoCo coverage report section. Identify low coverage classes and recommend areas where new test cases should be focused, referencing the workflows in `FunctionalWorkflowTemplate.md`.

---

## 4. Cross-Artifact Consistency

> Compare references between the `FunctionalWorkflowTemplate.md` and `UnitTestTargetsTemplate.md`. Indicate any workflows missing corresponding test classes or insufficient coverage.

---

## 5. AI-Generated Code Review

> Here is the AI-generated test class for `OrderProcessor`. Check it for missing test cases and alignment with documented workflows and entity relationships.

---

## 6. Pipeline Configuration Check

> Generate or review a GitHub Actions YAML snippet that runs tests and fails on insufficient coverage. Include notifications for failures.

---

## 7. Manual Review Checklist Generator

> Generate a checklist for human reviewers to validate AI-generated outputs against project documentation including Step 1 templates and test coverage goals.


## 3. Reference Table: Using Copilot for Validation Tasks

| Validation Type                 | Tasks Supported by Copilot                                | Typical Source Artifacts       |
|:--------------------------------|:----------------------------------------------------------|:-------------------------------|
| Documentation Validation        | Missing sections, clarity, consistency checks             | Step 1 templates, README.md    |
| Code Quality \& Static Analysis | Suggest refactorings, interpret static analysis results   | Source code, static reports    |
| Test Coverage Validation        | Summarize gaps, suggest new test cases                    | JaCoCo reports, test templates |
| Cross-Artifact Consistency      | Check reference alignment between docs/tests              | Workflow and test templates    |
| AI-Generated Output Review      | Identify missing tests or inconsistent logic              | AI code outputs, test classes  |
| CI/CD Pipeline Validation       | Create or audit pipeline scripts for mandatory validation | CI configs and logs            |

# Summary

This structured approach with templates, examples, and Copilot prompt guidance empowers your project team to perform thorough, consistent validation efficiently. Copilot accelerates tedious reviews and generates realistic suggestions while your expertise ensures correctness and business alignment.
