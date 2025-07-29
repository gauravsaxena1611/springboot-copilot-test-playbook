# Integration & API Test Coverage Summary

## Overall Coverage

- Integration test coverage: ___%
- API test coverage per endpoint: ___%

| Test Class / Endpoint              | Covered?   | Missing Coverage / Cases                      | Related Step1 Artifacts              |
|------------------------------------|------------|-----------------------------------------------|--------------------------------------|
| UserServiceIntegrationTest         | ✔          | Transaction rollback error cases missing      | `FunctionalWorkflowTemplate.md`      |
| OrderServiceIntegrationTest        | ✗          | External API failure scenario                 | `ExternalDependencyTemplate.md`      |
| UserControllerApiTest              | ✔          | Input validation, security edge cases         | `ArchitectureDiagramTemplate.md`     |
| ...                                |            |                                               |                                      |

## Coverage Gaps & Next Steps

- [ ] Add tests for API validation failure cases.
- [ ] Expand integration failure/retry scenarios.
- [ ] Cross-check coverage with unit tests.

---

## Referencing Step 1 Templates

Coverage analysis guided by:

- Workflow mappings (`FunctionalWorkflowTemplate.md`)
- Data models for validation (`DataModelTemplate.md`)
- External integrations (`ExternalDependencyTemplate.md`)
- Team milestones (`TeamGoalsTemplate.md`)
