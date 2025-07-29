/**
 * AI-GENERATED TEST REVIEW CHECKLIST
 *
 * Reviewer: [Name]
 * Date: [Date]
 * Test Class: [ClassName]
 *
 * === COMPILATION AND EXECUTION ===
 * [ ] Tests compile without errors.
 * [ ] All imports are correct and available in the project's dependencies.
 * [ ] Tests execute successfully in the IDE (without security/context issues if not intended).
 * [ ] No unexpected runtime errors or exceptions during test execution (beyond expected failures for error tests).
 *
 * === HALLUCINATION CHECK (Crucial!) ===
 * [ ] No non-existent methods are called (e.g., `user.getInternalId()`).
 * [ ] All referenced classes and enums exist in the project (e.g., `UserTestDataFactory`).
 * [ ] No invented utility classes or methods are used unless explicitly created by the team.
 * [ ] Framework usage is correct, adheres to project conventions, and available (e.g., no JUnit 4 in a JUnit 5 project).
 *
 * === BUSINESS LOGIC ACCURACY ===
 * [ ] Test scenarios accurately reflect actual business requirements and rules.
 * [ ] Error conditions are realistic, relevant, and correctly handled by the SUT.
 * [ ] Success scenarios correctly reflect real usage patterns and desired outcomes.
 * [ ] Edge cases are appropriate for the domain and target method's contract.
 * [ ] Assertions verify the *behavior* and *contract*, not just internal implementation details.
 *
 * === MAINTAINABILITY ===
 * [ ] Tests are not brittle; they should not break on non-behavioral refactoring of the SUT.
 * [ ] Mocking is not over-specific to implementation details (e.g., mocking private methods or exact call sequences).
 * [ ] Test data is flexible and reusable (e.g., using builders or dedicated methods).
 * [ ] Tests are independent and can run in any order without impacting each other's state.
 *
 * === READABILITY AND DOCUMENTATION ===
 * [ ] Test purpose is clear from the test method names and `@DisplayName` annotations.
 * [ ] Complex scenarios, mock setups, or data constructions have explanatory comments.
 * [ ] Test structure follows the Arrange-Act-Assert (AAA) pattern.
 * [ ] Code is clean, well-formatted, and adheres to project coding standards (e.g., indentation, line breaks).
 *
 * === PERFORMANCE AND EFFICIENCY ===
 * [ ] Tests execute quickly (< 1 second for unit tests, < 5 seconds for most integration tests).
 * [ ] No unnecessary setup or computationally complex operations within tests.
 * [ ] Resource cleanup (e.g., database clear, file deletion) is handled appropriately.
 * [ ] No obvious memory leaks or resource leaks that could impact subsequent tests or builds.
 *
 * Review Notes:
 * [Add specific feedback and improvement suggestions here, referencing line numbers or code blocks.]
 * [Example: "Line 45: `userRepository.findByName` is a hallucination; use `userRepository.findByEmail` instead."]
 * [Example: "Consider converting `testCalculateDiscount` to a `@ParameterizedTest` for better coverage."]
 */