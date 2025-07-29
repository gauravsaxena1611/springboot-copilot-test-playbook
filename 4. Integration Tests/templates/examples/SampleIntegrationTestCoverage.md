# Sample Integration Test Coverage Summary (Example)

## Overall Coverage

Integration test coverage: 65%

| Test Class / Module           | Covered? | Missing Coverage / Cases             | Notes                        | Linked Step 1 Artifacts           |
|-----------------------------|----------|------------------------------------|------------------------------|---------------------------------|
| UserServiceIntegrationTest    | ✔        | Edge cases around transaction rollbacks | Requires negative test cases | `FunctionalWorkflowTemplate.md`  |
| OrderServiceIntegrationTest   | ✗        | External API failure handling       | Needs retries tests          | `ExternalDependencyTemplate.md`  |
