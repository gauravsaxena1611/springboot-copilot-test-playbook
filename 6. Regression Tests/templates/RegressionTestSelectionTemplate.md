# Regression Test Selection Template

| Test ID | Test Case / Suite Name                   | Layer (Unit/Integration/API) | Included in Regression? | Notes                  |
|---------|-----------------------------------------|-----------------------------|------------------------|------------------------|
| TC-100  | UserServiceTest.registerUser             | Unit                        | Yes                    | Newly added test case   |
| TC-101  | OrderServiceIntegrationTest.processOrder | Integration                 | Yes                    | Flaky tests quarantined|
| TC-102  | UserControllerApiTest.getUser             | API                         | No                     | Deprecated endpoint    |

---

## Instructions

- Track all tests included or excluded in the regression suite.
- Note reasons for exclusion or quarantine.
- Update as the suite evolves.
