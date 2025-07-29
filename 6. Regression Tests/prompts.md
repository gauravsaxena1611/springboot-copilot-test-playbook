# Copilot / LLM Prompts for Regression Test Suite Construction

---

## 1. Identify Regression Scenarios

> Based on the functional workflows in `FunctionalWorkflowTemplate.md` and recent defect logs, list critical regression test scenarios including impacted modules, priorities, and historical failures.

**Maps to:** `RegressionTestScenariosTemplate.md`

---

## 2. Select Regression Test Cases

> From existing unit, integration, and API test coverage data (`unit-test-coverage.md`, `integration-test-coverage.md`, `api-test-coverage.md`), select relevant test cases and suites to include in the regression testing cycle. Exclude flaky or obsolete tests and note reasons.

**Maps to:** `RegressionTestSelectionTemplate.md`

---

## 3. Document CI/CD Regression Test Execution Configuration

> Generate a markdown document detailing CI/CD platform, test trigger schedules, types of tests run, and environment setup for regression testing.

**Maps to:** `RegressionExecutionTemplate.md`

---

## 4. Analyze and Summarize Regression Test Metrics

> Using test execution logs and coverage reports, generate a markdown summary of regression test metrics including execution duration, defect detection rates, flaky test count, and coverage percentage. Suggest action items for improvement.

**Maps to:** `RegressionMetricsTemplate.md`

---

## 5. Generate New or Update Existing Regression Tests (Optional / Advanced)

> For scenarios with test coverage gaps or recent bugs, generate or enhance regression test methods targeted to those cases, providing test skeletons and validation prompts.

---

## Reference Table: Mapping Prompts to Templates & Previous Steps

| Prompt No. | Description                               | Maps To Template                      | References from Previous Steps           |
|------------|-------------------------------------------|-------------------------------------|------------------------------------------|
| 1          | Identify regression test scenarios         | `RegressionTestScenariosTemplate.md`| `FunctionalWorkflowTemplate.md`, defect logs |
| 2          | Select regression test cases                | `RegressionTestSelectionTemplate.md`| `unit-test-coverage.md`, `integration-test-coverage.md`, `api-test-coverage.md` |
| 3          | Document CI/CD execution configuration      | `RegressionExecutionTemplate.md`    | `environment-setup.md`                    |
| 4          | Analyze regression test metrics              | `RegressionMetricsTemplate.md`      | Test execution reports                    |
| 5          | Generate/update regression test code (optional) | N/A (code generation)              | `RegressionTestScenariosTemplate.md`     |

---

## Notes for Usage

- Replace placeholders and fill templates with project-specific data informed by Steps 1–5.
- Use iterative prompting with AI tools, validating results with historical data and business priorities.
- Regularly update regression suite documentation to keep it in sync with project changes.

