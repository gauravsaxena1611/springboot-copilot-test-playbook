# 🧠 Prompt Strategy and Guidelines for GitHub Copilot in Spring Boot Test Generation

This document defines a **standardized, repeatable prompt strategy** for using GitHub Copilot to generate test code in Java Spring Boot projects. By following these guidelines, teams can maximize the quality, correctness, and usability of AI-generated tests, while reducing hallucinations, brittleness, and rework.

This guide is meant to be used across **unit, integration, API, and regression test generation workflows** documented in this playbook.

---

## 📋 Table of Contents

1. [Overview](#overview)
2. [What to Include for Best Copilot Performance](#what-to-include-for-best-copilot-performance)
    - [A. Method and Class Context](#a-method-and-class-context)
    - [B. Dependencies and Setup](#b-dependencies-and-setup)
    - [C. Expected Behavior and Test Intent](#c-expected-behavior-and-test-intent)
    - [D. Test Type and Framework Hints](#d-test-type-and-framework-hints)
3. [How to Recognize and Fix Hallucinations and Brittle Tests](#how-to-recognize-and-fix-hallucinations-and-brittle-tests)
    - [A. Common Hallucinations](#a-common-hallucinations)
    - [B. Brittle or Unreliable Test Patterns](#b-brittle-or-unreliable-test-patterns)
    - [C. Missing or Invalid Test Context](#c-missing-or-invalid-test-context)
4. [Prompt Refinement Patterns](#prompt-refinement-patterns)
    - [A. Improve Clarity with Comments](#a-improve-clarity-with-comments)
    - [B. Be Explicit About Test Type](#b-be-explicit-about-test-type)
    - [C. Supply Input/Output Examples](#c-supply-inputoutput-examples)
    - [D. Break Complex Prompts into Smaller Units](#d-break-complex-prompts-into-smaller-units)
5. [Reusable Prompt Template](#reusable-prompt-template)
6. [Summary: Prompting Do’s and Don’ts](#summary-prompting-dos-and-donts)

---

## 🔍 Overview

GitHub Copilot can rapidly accelerate the creation of test cases, but the quality of its output is highly dependent on the structure, context, and clarity of your prompts. A consistent strategy helps:

- Guide Copilot toward producing meaningful, compilable tests.
- Reduce manual cleanup of broken or inaccurate code.
- Surface edge cases and error paths in your system more effectively.

This guide ensures every team member—regardless of AI prompting experience—can reliably generate high-quality tests from both new and legacy codebases.

---

## 💡 What to Include for Best Copilot Performance

### A. Method and Class Context

Provide Copilot with as much **source code context** as possible. Include:

- Full class declaration (not just a single method).
- JavaDoc or inline comments explaining method behavior.
- Relevant annotations (`@Service`, `@RestController`, `@Transactional`, etc.).

**Example Prompt Context:**

```java
/**
 * Finds users who registered in the last 30 days.
 * Returns a list of basic user DTOs.
 */
public List<UserDto> findRecentUsers() {
    List<User> users = userRepository.findRegisteredAfter(LocalDate.now().minusDays(30));
    return users.stream().map(UserMapper::toDto).collect(Collectors.toList());
}
```

### B. Dependencies and Setup
Copilot generates better tests when it knows what collaborators or beans are involved.

- Constructor injection (preferred)
- Mockable collaborators (UserRepository, EmailService, etc.).
- Configuration hints for integration tests

```java
public class UserService {
    private final UserRepository userRepository;
    private final EmailService emailService;

    public UserService(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }
}
```

### C. Expected Behavior and Test Intent
Tell Copilot what the method should do and what the test should verify. Use natural language, comments, or JavaDoc.

Define at least one success and one edge case for every method you prompt.

- Success cases
- Edge cases
- Error scenarios

```java
// Should return an empty list when no recent users are found
// Should throw exception if input date is null
```

### D. Test Type and Framework Hints

Always be explicit about what kind of test you want:

| Test Type        | Prompt Hint Example                            |
|------------------|------------------------------------------------|
| Unit Test        | "Write a JUnit 5 test using Mockito"           |
| API Test         | "Generate a Spring Boot @WebMvcTest"           |
| Integration Test | "Create a @SpringBootTest"                     |
| Regression Test  | "Create regression tests for failing scenario" |



## 🚨 How to Recognize and Fix Hallucinations and Brittle Tests

Copilot can occasionally produce invalid or low-quality test code. Use this checklist to detect and correct problems.

### Common Hallucinations
These are invented code elements that don't exist in your project.
- Non-existent methods - Calling non-existent methods (createDummyUser(), UserUtils.buildMock()).
- Unknown classes - Referring to classes that don’t exist (e.g., UserDTOFactory).
- Missing frameworks/libraries - Assuming external frameworks or libraries are imported when they are not.

Fix: Remove or rewrite. Ask Copilot to use only the existing code or stub needed pieces.

### Brittle or Unreliable Test Patterns
Brittle tests fail when implementation details change, even if behavior doesn't.

Watch for:

- Hard-coded values - Hard-coded values that break easily.
- Weak assertions - assertTrue(true) or other ineffective assertions.
- Improper mocking - Unmocked or improperly mocked collaborators.

Fix: Use clear and meaningful assertions. Replace fragile mocks with structured setups.

### MMissing or Invalid Test Context
Copilot might skip setup code, annotations, or fail to properly wire Spring context.

Checklist:
- is `@ExtendWith(MockitoExtension.class)` included for unit tests?
- Proper bean annotations (`@Mock`, `@MockBean`, `@Autowired`)
- Is the test class annotated with @SpringBootTest, @WebMvcTest, or equivalent?

## 🔁 Prompt Refinement Patterns

When the first suggestion is incomplete or inaccurate, iterate. Here's how:

### A. Use Clear Comments

Bad:
```java
@Test
void testGetUser() {

```

Good:
```java
// Should return user by email if it exists in the repository
@Test
void shouldReturnUserByEmailIfExists() {
```

### B. Specify Test Type Explicitly
 - "Write a unit test using JUnit 5 and Mockito for this service."
 - "Create a Spring Boot test for this REST controller using @WebMvcTest."

### C. Provide Examples
```java
// Input: email = "user@example.com"
// Expected Output: User object with id = 1L, name = "Jane"
```

### D. Break Complex Prompts into Smaller Units

Instead of:

"Generate all tests for this class."

Do:

1. Generate test class structure - Generate the test class and setup.
2. Add individual test methods - Generate one @Test method per behavior.
3. Refine incrementally - Add refinements one by one with focused prompts.

## 🧰 Reusable Prompt Template

Use this template for every Copilot test generation step:

```java
// === Copilot Test Prompt ===
//
// Purpose: [Brief summary of what the method does]
// Test Type: [Unit | Integration | API | Regression]
// Framework: [JUnit 5, Mockito, @SpringBootTest, etc.]
//
// Expected Behavior:
//   - [Case 1: Expected result]
//   - [Case 2: Edge or error case]
//
// Setup Requirements:
//   - [Mocked dependencies]
//   - [Test annotations]
//
// Target Method/Class: [Paste the full method or class code here]
```

## ✅ Summary: Prompting Do's and Don'ts

| Do's                              | Don'ts                                          |
|-----------------------------------|-------------------------------------------------|
| Include the full class and method | Give only method name                           |
| Use comments to describe behavior | Leave Copilot to guess purpose                  |
| Specify test type clearly         | Use vague/generic "test this" phrases/prompts   | 
| Check output for hallucinations   | Trust results without checking & verification   |
| Refactor brittle code             | Accept vague or fragile results                 |
| Prompt in small, iterative steps  | Ask for too much in one go                      |


When in doubt, prompt small, describe clearly, and review critically for consistent quality and maintainability