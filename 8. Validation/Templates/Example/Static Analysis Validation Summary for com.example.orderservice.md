# Static Analysis Validation Example

## Source Code Module

- Module/Package: com.example.orderservice
- Scan Date: 2025-07-20

## Tools Used

- SonarQube 10.3.3 with custom Spring Boot rules
- Config at `/environment-setup/sonar-project.properties`

## Findings Summary

| Severity   | Issue Type               | Count | Action Status | Comments                         |
|------------|-------------------------|-------|---------------|---------------------------------|
| Critical   | SQL Injection Risk      | 1     | Pending       | Fix query param concatenation   |
| Major      | Code Smells             | 5     | 3 Fixed       | Duplicated code in DiscountCalc |
| Minor      | Style Violations         | 7     | In Progress   | Naming conventions inconsistent |

## Reviewer Notes

- Prioritize SQL injection fix ASAP.
- Schedule refactor for duplicated logic.
- Encourage adherence to naming standards.
