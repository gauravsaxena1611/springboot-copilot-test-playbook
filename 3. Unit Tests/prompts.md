# Copilot/LLM Prompts for Unit Test Generation

1. **Identify Untested Classes**
   > List all classes in the `service` and `util` packages that have no test class or have <2 test methods.

   **Maps to:** UnitTestTargetsTemplate.md


2. **Generate Test Skeleton**
   > For `UserService`, generate a JUnit 5 test class with class/method-level comments and stubs for each public method.

   **Maps to:** Test class skeletons in `src/test/java/`


3. **Happy Path Test Generation**
   > Write a JUnit 5 test method for `UserService.registerUser(User input)` covering the successful registration scenario, with clear assertions and Arrange-Act-Assert comments.

   **Maps to:** Individual test methods


4. **Negative/Edge Case Test Generation**
   > For `OrderCalculator.applyDiscount(Order o)`, generate parameterized tests for zero, negative price, and null order inputs.

5. **Mock Setup**
   > Add `@Mock` and `@InjectMocks` to the test for `UserService`, setting up a mock `UserRepository` and sample stub responses.

6. **Refinement and Review**
   > Review generated test methods and suggest any missing cases or improvements for readability and assertion clarity.

   **Maps to:** UnitTestCoverageTemplate.md


7. **Coverage Extraction**
   > Using JaCoCo’s or your IDE’s test coverage report, generate a markdown table summarizing which classes/methods lack full coverage.

