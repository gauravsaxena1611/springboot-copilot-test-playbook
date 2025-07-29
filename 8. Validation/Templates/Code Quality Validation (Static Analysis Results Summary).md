# Static Analysis Validation Template

## Source Code Module

- Module/Package: [e.g., com.example.orderservice]
- Scan Date: [YYYY-MM-DD]

## Tools Used

- [List tools: Checkstyle, SonarQube version]
- Configuration Reference: [Link or path]

## Findings Summary

| Severity   | Issue Type               | Count | Action Status (Fixed/Pending) | Comments                 |
|------------|-------------------------|-------|-------------------------------|--------------------------|
| Critical   | Security Vulnerabilities |       |                               | Must fix before release  |
| Major      | Code Smells             |       |                               | Refactor recommended     |
| Minor      | Style Violations         |       |                               | To fix in next sprint    |

## Reviewer Notes

- Noted high duplication in OrderProcessor class.
- Consider stronger input validation on public APIs.
