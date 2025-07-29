# Test Coverage Validation Template

## Coverage Report Summary

- Reporting Tool: [JaCoCo, SonarQube, etc.]
- Date: [YYYY-MM-DD]
- Overall Coverage: [XX]%

## Coverage by Module/Class

| Module/Class             | Coverage % | Missing Coverage Areas           | Notes                      |
|-------------------------|------------|---------------------------------|----------------------------|
| UserService             | 78%        | Exception handling tests missing| Prioritized for next sprint|
| OrderProcessingService   | 55%        | Boundary cases, null inputs      | Needs additional testing   |
| PaymentGatewayClient     | 90%        | External integration mocks limited | Stable, review in 2 weeks |

## Validation Actions

- Review test cases for critical classes with <70% coverage.
- Augment tests for missing edge cases indicated.
- Use prompts to generate potential test cases for gaps.
