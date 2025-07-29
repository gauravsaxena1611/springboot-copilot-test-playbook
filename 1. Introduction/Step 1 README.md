# Step 1: Introduction & Project Context

## Purpose

This step prepares you to fully understand and document your Java Spring Boot application before generating any tests.  
**Whether you are working with an existing/legacy project, a modernization effort, or a new/greenfield project, this stage ensures you (and Copilot) have the project context required for meaningful test generation.**

---

## Why Does This Step Matter?

- **Better context = Better Copilot output:** The richer your documentation and functional breakdown, the more precise and relevant Copilot's test suggestions (and code completions) will be.
- **Clarity for all contributors:** Key project understanding avoids duplicated effort, missed workflows, and incorrect assumptions—essential for testing and maintenance alike.
- **Accelerates onboarding, support, and automation.**

**TIP:** Many tasks in this step can be assisted or accelerated by GitHub Copilot and IDE integrations!

---

## Project Scenarios Covered

Identify which of the following describes your situation:
- **Existing Project (Legacy or Ongoing):**  
  Understand and document the current state, architecture, functionalities, and tech stack before any test generation.
- **Modernization/Refactoring:**  
  Start with deep system understanding, documenting what is changing and why. Pay special attention to workflows/modules being refactored.
- **New/Greenfield Development:**  
  Prefer TDD/BDD if possible. Document planned architectures, workflows, and development conventions as you scaffold the project.

---

## Inputs: Gather All Relevant Context

> Collect the following, using the templates in `/introduction/templates/` as needed:

- **Project background:** Purpose, history, key design decisions, and business context.
- **Major functionalities & workflows:** What are the main user journeys, batch jobs, or API workflows the app supports?
- **All service and utility classes:** Document their high-level responsibilities.
- **Architecture diagrams:** System/component/DB diagrams (if available).
- **Data models:** ER diagrams, main entities, and key relationships.
- **Other dependencies:** External APIs, auth systems, libraries, etc.
- **Team goals/milestones:** What is the immediate or long-term goal for testing?
- **Technical stack:**
    - Collect from `pom.xml`:
        - Java version
        - Spring Boot version
        - Database/JPA/ORM libraries
        - Major utility dependencies (e.g., MapStruct, Lombok, Feign, etc.)

**Templates:**
- See `/templates/ProjectInfoTemplate.md`, `/templates/ArchitectureDiagramTemplate.md`, `/templates/FunctionalWorkflowTemplate.md` (complete these for your project).

---

## Prerequisite Checklist

- Access to the codebase and documentation (or initial repo if new).
- Ability to open/view `pom.xml` and main Java packages.
- (For new projects) Ability to scaffold a project (via Spring Initializr, start.spring.io, etc.), and decide on development methodology (TDD, BDD, or other).

---

## Step-by-Step Actions

### For Existing/Legacy or Modernization Projects

1. **Manual:**
    - Review the codebase and all existing documentation.
    - Fill out Project Info, Functional Workflows, and Architecture Diagram using provided templates.
    - Open the `pom.xml`, and list the major framework/library versions in a new section of your project’s README.md.

2. **With Copilot:**
    - Use Copilot in your IDE to:
        - Generate markdown summaries of class responsibilities (see sample prompts in `/introduction/prompts.md`).
        - Summarize all dependencies from your `pom.xml` with the prompt:  
          `"Summarize all major frameworks, Java version, and libraries used in this pom.xml."`
        - Generate class or package-level documentation via "Ask Copilot" on entire folders:
          `"Generate a markdown table listing all service and utility classes with their main purpose."`

### For New/Greenfield Projects

1. **Decide (Manual):**
    - Will you use TDD, BDD, or a mixed/other methodology?
    - Scaffold an initial project structure (can use Spring Initializr with your chosen dependencies).

2. **Document (Manual/Copilot-Aided):**
    - List planned modules, expected main functionalities, and initial workflows using templates.
    - Fill out baseline Project Info and Architecture Diagrams (can prompt Copilot for skeletons).

3. **Document Tech Stack:**
    - After `pom.xml` is generated, document key versions/dependencies in README.md using Copilot or manually.

---

## Outputs

- **[Mandatory for every user/project]**
    - Project-specific `README.md` populated with:
        - Project background, purpose, and business context.
        - Major functionalities/workflows summary.
        - List of all service/util utility classes, with short descriptions.
        - Architecture and data model diagrams (or references/links).
        - Tech stack (with versions), extracted from `pom.xml`.
        - Any goals, milestones, or requirements set by the team.
- **[Optional]**
    - Store filled input templates in `/introduction/inputs/`.
    - Save Copilot-generated documentation/markdowns in `/introduction/copilot-output/`.

---

## How Copilot & IntelliJ Can Help

- **Extract summaries:**  
  Example prompt:
  > "Review the following classes and generate a markdown summary of each class’s main role and any known integration points."
- **Auto-generate documentation tables for packages/services:**
  > "For all classes in the `service/` package, create a markdown table: Class Name, Short Responsibility, Related Entities."
- **Tech stack markdown from `pom.xml`:**
  > "Create a markdown bullet list of all core dependencies, indicating their versions, from this pom.xml."
- **For new projects:**
  > "Generate a simple architecture diagram (in Mermaid syntax) representing controller, service, repository layers."
- **Ask Copilot for class-level doc comments, summaries, or even draft user-stories based on code comments.**

**See prompts in `/introduction/prompts.md` for ready-to-use variants.**

---

## Visual Aids

- Collect or generate visuals like:
    - Simple system architecture diagram.
    - Data model ERD (Entity Relationship Diagram) snippet.
    - Example Copilot output summarizing tech stack.

---

## Why Complete This Step?

- Every subsequent test generation step will use the documentation and structure captured here.
- Copilot's ability to generate useful tests and documentation depends directly on the clarity and completeness of your initial project context.

---

## Checklist

- [ ] Project background, functionalities, and workflows described
- [ ] All relevant diagrams and models collected or referenced
- [ ] Service, utility, helper classes listed with responsibilities
- [ ] Technical stack and libraries/versions clearly summarized
- [ ] Major goals or requirements linked (from team or business)
- [ ] Copilot utilized for README or doc scaffolding where helpful

---

**Once you have filled out the README.md and supporting documents/templates for this step, proceed to Step 2: Environment & Tool Setup, where you will configure your IDE, Copilot, and baseline dependencies.**
