# CLAUDE.md

> AI agent instructions for this Java Maven monorepo.

---

## Quick Reference

```
BUILD:    mvn clean verify       # Full build with tests
FORMAT:   mvn spotless:apply     # Fix formatting (REQUIRED)
TEST:     mvn test               # Run tests only
COVERAGE: 100% line coverage     # Enforced by JaCoCo
```

---

## AI-Native Module Pattern

Each module is **self-contained** with code, tests, and documentation:

```
module/
├── src/              # Source code
├── tests/            # Test code
├── docs/
│   ├── overview.md   # What & why (purpose, design)
│   ├── api.md        # Contracts (endpoints, interfaces)
│   └── rules.md      # Business logic (validation, constraints)
└── README.md         # Quick start guide
```

**Key Principle**: Documentation lives with the code it describes.

---

## Project Structure

```
repo/
├── docs/                  # Documentation
│   ├── system.md          # Architecture overview
│   ├── glossary.md        # Terms
│   ├── adr/               # ADRs
│   ├── analysis/          # Analysis docs
│   └── diagrams/          # Draw.io diagrams
│
├── service/               # Service modules (hierarchical)
│   ├── rest/              # REST aggregator
│   │   └── sample/        # Sample REST API (port 8080)
│   ├── batch/             # Batch aggregator
│   │   └── sample/        # Sample batch job
│   └── soap/              # SOAP aggregator
│       └── sample/        # Sample SOAP (port 8081)
│
├── common/                # Shared modules
│   ├── exception/         # Base/Business/Technical
│   ├── utils/             # JSON/Date/String
│   ├── env/               # Configuration
│   └── aws/               # AWS SDK wrappers (modular)
│       ├── aws-s3/        # S3 only
│       ├── aws-sqs/       # SQS only
│       └── aws-dynamodb/  # DynamoDB only
│
└── infra/                 # AWS CDK infrastructure
```

---

## Context Loading Order

When working on a module, load context in this order:

```
1. CLAUDE.md                  # This file (conventions)
2. docs/system.md             # System architecture
3. module/README.md           # Module quick start
4. module/docs/overview.md    # Module design
5. module/docs/api.md         # API contracts
6. module/docs/rules.md       # Business rules
7. Target source file         # Code to modify
8. Target test file           # Expected behavior
```

---

## Build Commands

```bash
# Full build (primary)
mvn clean verify

# Compile only
mvn clean compile

# Build specific module
mvn clean verify -pl service/rest -am

# Format code (REQUIRED before commit)
mvn spotless:apply
```

---

## Key Conventions

### Dependencies
- Versions in parent POM `<dependencyManagement>`
- Child modules: NO version declarations

### Exceptions
```java
// Business errors (client fault)
throw new BusinessException("ERR_CODE", "Message");

// Technical errors (system fault)
throw new TechnicalException("ERR_CODE", "Message", cause);
```

### Testing
- 100% line coverage required
- Pattern: `@ExtendWith(MockitoExtension.class)`
- Naming: `*Test.java`

### Code Style
- Google Java Format (Spotless)
- No emojis
- Javadoc for public members

---

## Module Quick Reference

| Type | Module | Artifact ID | Path | Port |
|------|--------|-------------|------|------|
| REST | Sample | `rest-sample` | `service/rest/sample` | 8080 |
| Batch | Sample | `batch-sample` | `service/batch/sample` | - |
| SOAP | Sample | `soap-sample` | `service/soap/sample` | 8081 |
| Infra | CDK | `infra` | `infra` | - |

**Note**: Each service type (rest, batch, soap) is an aggregator supporting multiple services.

---

## Adding New Module

### New Common Module
```
common/new-module/
├── pom.xml              # relativePath: ../../pom.xml
├── README.md
├── src/
├── tests/
└── docs/
```
Add `<module>new-module</module>` to `common/pom.xml`

### New Service Module
```
service/{type}/new-service/
├── pom.xml              # relativePath: ../../../pom.xml
├── README.md            # artifactId: {type}-new-service
├── src/
├── tests/
└── docs/
```
Add `<module>new-service</module>` to `service/{type}/pom.xml`

Example: `service/batch/notify-daily/` with artifactId `batch-notify-daily`

---

## Troubleshooting

| Issue | Solution |
|-------|----------|
| Format fails | `mvn spotless:apply` |
| Coverage fails | Check `target/site/jacoco/` |
| Module not found | `mvn install -DskipTests` |
