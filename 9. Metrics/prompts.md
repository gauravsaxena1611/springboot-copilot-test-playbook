# Metrics & Continuous Improvement Copilot Prompts

## 1. Coverage & Trend Summaries

> Parse JaCoCo and Allure coverage reports and generate a metrics dashboard table showing current, previous, and target coverage rates for unit, integration, and API tests.

---

## 2. Code & Test Quality Insights

> Given a SonarQube/Checkstyle report and current CI logs, summarize top recurring issues and suggest concrete improvement actions referenced against project goals in `TeamGoalsTemplate.md`.

---

## 3. Defect & Flakiness Tracking

> Review the last 10 CI regression runs. How many failures were caused by flaky tests? Suggest approaches to quarantine or stabilize those.

---

## 4. Metrics Trend Visualization

> Given four weeks of coverage and pass rate data, generate a summary or graph of trends, annotate any regressions or spikes and recommend root cause investigations.

---

## 5. Requirement/Traceability Mapping

> Create a report mapping each workflow in `FunctionalWorkflowTemplate.md` to its current test coverage, highlighting any unmapped or untested flows.

---

## 6. DORA/Delivery Metrics Extraction

> Using build logs and deployment records, extract deployment frequency, average lead time, mean time to repair (MTTR), and change failure rate; compare to industry DORA benchmarks.

---

## 7. Metrics Reference Table

| Prompt No.  | Description                              | Related Step/Artifact(s)                                  |
|-------------|------------------------------------------|-----------------------------------------------------------|
| 1           | Coverage & Dashboard Generation          | Steps 3–6 coverage templates, build pipeline logs         |
| 2           | Code/Test Quality Improvement            | Static analysis, `TeamGoalsTemplate.md`, CI/CD logs       |
| 3           | Flaky Test Analysis                      | Regression test execution logs, `regression-metrics.md`   |
| 4           | Trend Visualization                      | Coverage dashboards, build logs                           |
| 5           | Requirement Mapping                      | `FunctionalWorkflowTemplate.md`, test tracking templates  |
| 6           | DORA Metrics                             | Build/deploy logs, CI/CD config                           |

---

## Usage Notes

- Copy and adapt prompts to Copilot Chat or any LLM interface.
- Always provide context (logs, reports, template excerpts).
- Review Copilot outputs for accuracy, add visualization where possible, and archive in `/metrics/`.

