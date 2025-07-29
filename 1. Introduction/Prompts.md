# Copilot \& LLM Prompt Library for Step 1: Project Context \& Templates

_Use these prompts to auto-generate the template files or complete their core sections. For each, see the "Maps to Template" line to understand what artifact the prompt is for. Where generation must be manual, a note is included._

- **Directly generate the templates** you provided in `/introduction/templates/` (using AI, Copilot, or an LLM).
- **Map each prompt** to the corresponding template file.
- Note where a template is not directly generatable (e.g., diagrams requiring manual design).
- **Align every prompt with the step1.md workflow**, ensuring all Step 1 documentation and artifacts have matching, actionable prompts for your users.

## 1. Project Information

**Prompt:**
> Create a project information markdown including sections for Project Name, Overview, Purpose, Background, Technology Stack, Key Modules, and Additional Notes. Fill out placeholders based on available info or leave as TODO.

**Maps to Template**: `ProjectInfoTemplate.md`

## 2. Functional Workflows

**Prompt:**
> Scan the codebase and generate a table of major user workflows and scheduled jobs, with Workflow/Job Name, Description, Main Classes/Endpoints. Also, add a placeholder section titled "Detailed Workflow Descriptions" with subheadings for each workflow.

**Maps to Template**: `FunctionalWorkflowTemplate.md`

## 3. Architecture Overview / Diagrams

**Prompt:**
> Generate a markdown section with a Mermaid flowchart (or PlantUML if preferred) depicting common Spring Boot architecture: UI, REST API Controllers, Service Layer, Repository, Database, External Service. List each component, draw arrows for typical request flow, and add placeholders for integration and security notes.

**Maps to Template**: `ArchitectureDiagramTemplate.md`

**Note:** The prompt will produce a starting flowchart in Mermaid, which users should refine for project-specific architectures.

## 4. Data Model (Entities \& Relationships)

**Prompt:**
> Identify all JPA @Entity classes in the codebase. Generate a Mermaid ER diagram showing entities and their relationships, listing attributes for each entity. Then produce a markdown table with columns: Entity, Description, Main Relationships.

**Maps to Template**: `DataModelTemplate.md`

**Note:** Mermaid diagram may require user review/editing for accuracy or project complexity.

## 5. Service/Utility Class Inventory

**Prompt:**
> List all classes in the service and util/helper packages. Generate a markdown table: Class/Package, Type (Service/Util/etc), Short Description, Key Dependencies. Add a section for Notable Patterns or Anti-patterns with bullet points.

**Maps to Template**: `ServiceUtilityClassInventoryTemplate.md`

## 6. External Dependencies \& Integrations

**Prompt:**
> Extract from pom.xml and code/config any libraries, databases, APIs, or external services used by the app. Generate a markdown table: Dependency/System, Type (API/DB/Lib), Purpose/Usage, Auth/Config Reference. Add a section for Known Reliability or Integration Notes.

**Maps to Template**: `ExternalDependencyTemplate.md`

## 7. Team / Business Goals \& Milestones

**Prompt:**
> Create a markdown table for current team/business goals and milestones, with Goal/Milestone, Target Date, Description/Acceptance Criteria. Add a section for Additional Stakeholder Notes (as bullets).

**Maps to Template**: `TeamGoalsTemplate.md`

## 8. README Generation and Summarization (Step 1 Main Output)

**Prompt:**
> Using all previous template sections and project metadata, generate a draft README.md suitable for a Java Spring Boot app. Include each major heading from the above templates and insert links or references to filled templates as needed.

**Maps to Template**: Project-specific README.md as described in Step 1 outputs (not a template file but a required deliverable per step1.md).

## 9. Coverage, Gaps, or Orphan Areas (for Manual Completion)

**Prompt:**
_These require human review/knowledge:_

- Diagrams that are team- or architect-specific (plantuml, draw.io, or hand-drawn nonstandard diagrams).
- Organizational/cultural notes and unstructured requirements.

**Maps to Template**: Part of `ProjectInfoTemplate.md`, `TeamGoalsTemplate.md`, and in some cases manual editing of the other templates.

# Coverage Mapping Table

| Prompt Purpose            | Template Mapped To                      | AI-Generatable?   | Notes                                               |
|:--------------------------|:----------------------------------------|:-----------------:|:----------------------------------------------------|
| Project Info              | ProjectInfoTemplate.md                  |        YES        |                                                     |
| Functional Workflows      | FunctionalWorkflowTemplate.md           |        YES        |                                                     |
| Architecture Diagram      | ArchitectureDiagramTemplate.md          |      PARTIAL      | Mermaid only; complex diagrams may need manual work |
| Data Model/Entities       | DataModelTemplate.md                    |      PARTIAL      | Mermaid/table: human review recommended             |
| Service/Utility Inventory | ServiceUtilityClassInventoryTemplate.md |        YES        |                                                     |
| External Dependencies     | ExternalDependencyTemplate.md           |        YES        |                                                     |
| Team/Business Goals       | TeamGoalsTemplate.md                    |        YES        |                                                     |
| README Sweeping Summary   | README.md (project-specific)            |        YES        | Collates content; links to templates recommended    |
| Cultural/Manual Notes     | Various (esp. ProjectInfo/TeamGoals)    |      MANUAL       | Human curation needed                               |

**Instructions for Use in Step 1:**

- For each section in Step 1, copy the prompt for the relevant template to Copilot chat or your chosen LLM/AI copilot.
- Paste the generated output into the corresponding template file (overwrite placeholder text as you go).
- Where diagrams or domain details are subtle, review and update as needed.
- When all templates are filled, consolidate the links in your project’s README.md as per the output requirements in your step1.md.
