# Step 2: Environment & Tool Setup

## Purpose

This step ensures your project is ready for efficient, automated test generation and code understanding.  
**You’ll set up your IDE, GitHub Copilot (or other AI), Java/Spring Boot project scaffold, and all required test libraries and tools.**  
Document your environment so future contributors and AI tools can reproduce and understand your setup.

---

## Why Is This Step Important?

- **Consistent developer experience**: Everyone’s environment works the same way, avoiding “it works on my machine” issues.
- **Copilot (or other AI) performs best when tools, folders, and dependencies are clear and standardized.**
- **Helps new team members onboard.**

---

## Inputs

- Project requirements from Step 1.
- Preferred IDE (IntelliJ IDEA, VS Code, Eclipse, etc.).
- GitHub Copilot subscription or other LLM tooling access.
- Chosen JDK version and build tool (Maven/Gradle).

---

## Prerequisite Checklist

- [ ] You have access to the existing codebase or are ready to create a new Spring Boot scaffold.
- [ ] Your machine meets base hardware/software requirements (RAM, disk, OS compatibility).
- [ ] You can install plugins/extensions for your IDE.

---

## Step-by-Step Actions

### 1. IDE Installation & Setup

**Manual Steps:**
- Install your chosen IDE.
    - IntelliJ IDEA (recommended)
    - Visual Studio Code with Java extensions
    - Eclipse with Copilot plugin
- Configure the IDE:
    - Set up Java SDK (ensure JDK 11 or higher is installed).
    - Configure build tools (Maven/Gradle).
- Install necessary plugins/extensions:
    - GitHub Copilot (or other LLM tools)
    - Lombok (if using)
    - Spring Boot support
    - JUnit/TestNG support
- Set up version control (Git) integration.
- Configure with Java support (ensure correct JDK is installed), and basic project settings.

**Copilot/AI Assistance:**
- Use Copilot to generate an “environment-setup.md” listing all IDE/OS/JDK settings.

---

### 2. GitHub Copilot (or Other LLM) Installation

**Manual:**
- Follow official docs to install Copilot (or other LLM tools) for your IDE.
- Log in and test Copilot with an example Java file.

**Copilot/AI Assistance:**
- Prompt Copilot to summarize Copilot configuration files or settings for later documentation.

---

### 3. Spring Boot Project Scaffold

**Manual:**
- For new projects: Use start.spring.io or your IDE’s project generator.
- Choose Java version, group/artifact, and **add dependencies**:
    - Spring Web
    - Spring Data JPA
    - H2/MySQL/Postgres/Any DB
    - Lombok (optional)
    - Spring Boot Test (JUnit 5, Mockito, etc.)

**Outputs:**
- Document the full folder structure in `/environment-setup/folder-structure.md`.
- Save sample pom.xml or build.gradle.

**Copilot Assistance:**
- Ask Copilot to generate a markdown doc describing the scaffold (see prompts).

---

### 4. Testing & Documentation Tools

**Manual:**
- Add dependencies for JUnit, Mockito, Spring Test, JaCoCo (coverage), AssertJ, etc.
- For legacy projects, ensure compatible library versions.

**Outputs:**
- Document all job/test dependencies in `/environment-setup/test-dependencies.md`.

**Copilot/AI:**
- Prompt Copilot to summarize all test-related dependencies and plugins from your pom.xml/gradle file.

---

### 5. Folder & Repo Structure Documentation

**Manual/Copilot:**
- Generate and maintain a detailed folder/repo structure markdown (docs, src, test, ci, resources, etc.).
- Document where key templates, configs, and artifacts reside.

---

## Outputs

- setup/setup.md (IDE, OS, JDK, plugin info)
- setup/folder-structure.md (description or diagram)
- setup/test-dependencies.md (all libraries, versions)
- Sample generated config files, e.g. `pom.xml`, `.gitignore`
- Screenshots or logs of Copilot/LLM installation verification (optional)
- Documented manual steps and any troubleshooting

---

## Prompts Library
(_See /setup/prompts.md for detailed, ready-to-use prompts for each setup step._)

---

## Output Flow Table

| Activity                            | Produces / Edits                      | Supported by Prompts  |
|-------------------------------------|---------------------------------------|-----------------------|
| IDE/JDK/AI Plugin Setup             | environment-setup.md                  | Yes                   |
| Project Scaffold + Folder Structure | folder-structure.md                   | Yes                   |
| Testing Tools Config                | test-dependencies.md                  | Yes                   |
| All-steps Documentation             | onboarding checklist, troubleshooting | Yes (partial/manual)  |

---

## Checklist

- [ ] IDE installed and configured (list version)
- [ ] Copilot/LLM plugin installed/logged in
- [ ] Java project scaffolded (folder structure saved)
- [ ] Required test dependencies added and documented
- [ ] Folder/repo structure documented
- [ ] All setup steps reproducible by a teammate
