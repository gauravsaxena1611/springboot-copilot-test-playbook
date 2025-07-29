# Example Metrics Dashboard (July 2025)

| Metric                     | Current Value  | Goal/Threshold | Trend (Last 4 Sprints) | Comments / Actions                                   |
|----------------------------|----------------|----------------|------------------------|------------------------------------------------------|
| Unit Test Coverage         | 83%            | 80%+           | 78, 80, 82, 83         | Stable improvement. Target 85% next.                 |
| Integration Test Coverage  | 72%            | 75%+           | 65, 67, 71, 72         | Add tests for OrderService ASAP.                     |
| API Test Coverage          | 85%            | 85%+           | 78, 80, 83, 85         | All endpoints covered, focus on negative tests.      |
| Regression Suite Pass Rate | 97%            | 99%+           | 95, 96, 98, 97         | Few intermittent failures, flakiness action planned. |
| Avg CI Pipeline Duration   | 42 min         | ≤45 min        | 45, 43, 41, 42         | Acceptable; investigate further optimization.        |
| Flaky Test Count           | 4              | ≤2             | 6, 5, 4, 4             | Quarantine or refactor.                              |
| MTTR                       | 7 hrs          | ≤24 hrs        | 10, 10, 9, 7           | Meets goal; maintain.                                |
