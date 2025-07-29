# Step 3: Unit Test Generation

## Purpose

This step guides you in generating, refining, and documenting high-quality unit tests for your Java Spring Boot application’s business logic and critical classes.  
You’ll use manual best practices and AI/Copilot-driven suggestions to maximize test coverage, reliability, and maintainability.

---

## Why Is This Step Important?

- **Unit tests are the bedrock of regression protection.**
- **Early bug catching:** Problems are found at the smallest, cheapest possible scope.
- **AI tools like Copilot work best when focused on granular logic for test generation.**

---

## Inputs

- List of service/utility classes and their responsibilities (from prior steps).
- Any known business rules, workflows, and requirements needing direct assertion.
- Test dependency setup from `/environment-setup/`.

---

## Prerequisite Checklist

- [ ] You have access to code for all business logic and utility classes.
- [ ] Test libraries (JUnit, Mockito, AssertJ, etc.) are installed and working.
- [ ] The project builds and existing tests (if any) pass.

---

## Step-by-Step Actions

### 1. Identify Units Under Test

- **Manual:**  
  Review your services, major utilities, and any class with key business logic. Document a prioritized list in `/unit-tests/unit-test-targets.md`.
- **Copilot-assisted:**  
  Use prompts to extract all classes that lack a corresponding test file or test methods.

---

### 2. Generate Test Skeletons

- **Manual:**  
  For each class, create a `ClassNameTest.java` (or `TestClassName.java`) file if none exists.
- **Copilot/AI:**  
  Use prompts to generate test class skeletons and method stubs for all public methods.

---

### 3. Write/Generate Test Cases

- **Manual:**  
  For high-risk or complex methods, write initial test cases by hand.
- **Copilot/AI:**  
  Use targeted prompts to generate:
    - Happy path test methods
    - Negative/edge case tests (nulls, exceptions, boundary conditions)
    - Parameterized test variations

---

### 4. Use Mocking Where Needed

- **Manual:**  
  Identify dependencies to mock/stub (e.g., repositories, external APIs).
- **Copilot/AI:**  
  Prompt for `@Mock` and `@InjectMocks` usage, Mockito setup, and clear documentation/comments in tests.

---

### 5. Review & Refine

- **Manual:**  
  Review AI-generated tests for correctness, relevance, redundancy, and readability.
- **Copilot-assisted:**  
  Use Copilot to suggest improvements/refactorings for clarity.

---

### 6. Document and Organize

- Store all final tests in `/src/test/java/`.
- Capture a summary of added tests, patterns used, and open coverage gaps in `/unit-tests/unit-test-coverage.md`.

---

## Outputs

- Fully implemented test classes in `src/test/java/`.
- `/unit-tests/unit-test-targets.md` – List of units/classes and current test status.
- `/unit-tests/unit-test-coverage.md` – Markdown summary of what’s covered, example gaps, and improvement points.
- (Optional) Screenshots/logs of Copilot/LLM prompt usage/suggestions.

---

## Prompts Library
(_See `/unit-tests/prompts.md` for detailed, ready-to-use prompts for each activity!_)

---

## Output Coverage Table

| Activity                         | Produces/Edits                         | Supported by Prompts  |
|----------------------------------|----------------------------------------|-----------------------|
| Class/test target identification | `unit-test-targets.md`                 | Yes                   |
| Test class creation/generation   | `src/test/java/` (actual test classes) | Yes                   |
| Quality and coverage review      | coverage summary, `coverage.md`        | Yes                   |
| Mocking/edge case handling       | `src/test/java/`, notes, templates     | Yes                   |

---

## Checklist

- [ ] All key business logic classes identified as units-under-test
- [ ] Test class skeletons exist for each target class
- [ ] Meaningful tests cover happy path and edge/negative scenarios
- [ ] External dependencies are mocked where needed
- [ ] Test results are reviewed and refined (not accepted blindly from AI)
- [ ] Coverage summary updated and gaps documented

