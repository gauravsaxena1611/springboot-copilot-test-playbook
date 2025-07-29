# Project Folder Structure


project-root/
├── src/
│   ├── main/
│   │   ├── java/
│   │   └── resources/
│   └── test/
│       ├── java/
│       └── resources/
├── docs/
├── scripts/
├── .github/
├── .env
├── pom.xml
└── .gitignore

## Description of Key Folders/Files

| Folder/File         | Purpose/Contents                                 |
|---------------------|-------------------------------------------------|
| src/main/java       | Application/business code                        |
| src/test/java       | Test code (unit/integration/regression)          |
| docs/               | Architecture/test/process documentation          |
| scripts/            | Utility, DB migration, or CI/CD scripts          |
| .github/            | GitHub Actions/workflows                         |
| .env                | Environment variables/secrets (if any)           |
| pom.xml             | Maven build file                                 |
| .gitignore          | Version control exclusions                       |
