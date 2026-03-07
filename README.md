# Sample Mono Repository - Maven Java

> **AI-Native Modular Monorepo** with self-contained modules combining code, tests, and documentation.

---

## AI-Native Module Pattern

Each module is **self-contained** with code, tests, and documentation together:

```
module/
├── src/              # Source code
├── tests/            # Test code
├── docs/
│   ├── overview.md   # What & why (purpose, design, architecture)
│   ├── api.md        # Contracts (endpoints, interfaces, methods)
│   └── rules.md      # Business logic (validation, constraints)
└── README.md         # Quick start guide
```

**Key Principle**: Documentation lives with the code it describes - making each module easy for both humans and AI agents to understand in isolation.

---

## Architecture Overview

```
┌─────────────────────────────────────────────────────────────────────────┐
│                         MONO REPOSITORY                                 │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                         │
│  ┌─────────────────┐                                                    │
│  │      docs/      │  System-level documentation & decisions            │
│  └─────────────────┘                                                    │
│                                                                         │
│  ┌─────────────────────────────────────────────────────────────────┐    │
│  │                      service/ (hierarchical)                    │    │
│  │  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐  │    │
│  │  │     rest/       │  │     batch/      │  │     soap/       │  │    │
│  │  │  ├ sample/      │  │  ├ sample/      │  │  ├ sample/      │  │    │
│  │  │  ├ another/     │  │  ├ notify-daily/│  │  ├ another/     │  │    │
│  │  │  └ ...          │  │  └ ...          │  │  └ ...          │  │    │
│  │  └─────────────────┘  └─────────────────┘  └─────────────────┘  │    │
│  └─────────────────────────────────────────────────────────────────┘    │
│                                                                         │
│  ┌─────────────────────────────────────────────────────────────────┐    │
│  │                      common/ (modules)                          │    │
│  │  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌────────────────┐   │    │
│  │  │exception │  │  utils   │  │   env    │  │      aws/      │   │    │
│  │  │          │  │          │  │          │  │ ┌────────────┐ │   │    │
│  │  │          │  │          │  │          │  │ │aws-s3      │ │   │    │
│  │  │          │  │          │  │          │  │ │aws-sqs     │ │   │    │
│  │  │          │  │          │  │          │  │ │aws-dynamodb│ │   │    │
│  │  │          │  │          │  │          │  │ └────────────┘ │   │    │
│  │  └──────────┘  └──────────┘  └──────────┘  └────────────────┘   │    │
│  └─────────────────────────────────────────────────────────────────┘    │
│                                                                         │
│  ┌─────────────────────────────────────────────────────────────────┐    │
│  │                         infra/                                  │    │
│  │  AWS CDK Infrastructure (self-contained module)                 │    │
│  └─────────────────────────────────────────────────────────────────┘    │
│                                                                         │
└─────────────────────────────────────────────────────────────────────────┘
```

---

## Project Structure

```
sample-mono-repository/
│
├── pom.xml                              # Parent POM (BOM + plugin management)
├── README.md                            # This file
├── CLAUDE.md                            # AI agent instructions
│
├── docs/                                # ══ DOCUMENTATION ══
│   ├── system.md                        # System architecture & diagrams
│   ├── glossary.md                      # Domain terms & abbreviations
│   ├── adr/                             # Architectural Decision Records
│   ├── analysis/                        # Architecture analysis documents
│   └── diagrams/                        # Visual diagrams (Draw.io)
│
├── common/                              # ══ SHARED MODULES ══
│   ├── pom.xml                          # Aggregator POM
│   │
│   ├── exception/                       # Exception handling module
│   │   ├── pom.xml
│   │   ├── src/main/java/.../exception/
│   │   │   ├── BaseException.java
│   │   │   ├── BusinessException.java
│   │   │   └── TechnicalException.java
│   │   └── src/test/java/...
│   │
│   ├── utils/                           # Utility functions module
│   │   ├── pom.xml
│   │   ├── src/main/java/.../utils/
│   │   │   ├── JsonUtils.java
│   │   │   ├── DateUtils.java
│   │   │   └── StringUtils.java
│   │   └── src/test/java/...
│   │
│   ├── env/                             # Environment configuration module
│   │   ├── pom.xml
│   │   ├── src/main/java/.../env/
│   │   │   ├── ConfigurationProvider.java
│   │   │   └── EnvironmentManager.java
│   │   └── src/test/java/...
│   │
│   └── aws/                             # AWS SDK wrappers (modular)
│       ├── pom.xml                      # Aggregator POM
│       ├── README.md
│       │
│       ├── aws-s3/                      # S3 wrapper module
│       │   ├── pom.xml
│       │   ├── README.md
│       │   ├── src/main/java/.../s3/
│       │   │   └── S3ClientWrapper.java
│       │   ├── src/test/java/...
│       │   └── docs/
│       │       ├── overview.md
│       │       ├── api.md
│       │       └── rules.md
│       │
│       ├── aws-sqs/                     # SQS wrapper module
│       │   ├── pom.xml
│       │   ├── README.md
│       │   ├── src/main/java/.../sqs/
│       │   │   └── SqsClientWrapper.java
│       │   ├── src/test/java/...
│       │   └── docs/
│       │       ├── overview.md
│       │       ├── api.md
│       │       └── rules.md
│       │
│       └── aws-dynamodb/                # DynamoDB wrapper module
│           ├── pom.xml
│           ├── README.md
│           ├── src/main/java/.../dynamodb/
│           │   └── DynamoDbClientWrapper.java
│           ├── src/test/java/...
│           └── docs/
│               ├── overview.md
│               ├── api.md
│               └── rules.md
│
├── service/                             # ══ SERVICE MODULES ══
│   ├── pom.xml                          # Aggregator POM
│   │
│   ├── rest/                            # REST API aggregator
│   │   ├── pom.xml                      # Aggregator for REST services
│   │   └── sample/                      # Sample REST service (Port 8080)
│   │       ├── pom.xml
│   │       ├── src/main/java/.../rest/
│   │       │   ├── Application.java
│   │       │   └── controller/
│   │       ├── src/main/resources/
│   │       │   └── application.yml
│   │       └── src/test/java/...
│   │
│   ├── batch/                           # Batch aggregator
│   │   ├── pom.xml                      # Aggregator for batch services
│   │   └── sample/                      # Sample batch job
│   │       ├── pom.xml
│   │       ├── src/main/java/.../batch/
│   │       │   ├── BatchApplication.java
│   │       │   ├── config/
│   │       │   └── job/
│   │       ├── src/main/resources/
│   │       │   └── application.yml
│   │       └── src/test/java/...
│   │
│   └── soap/                            # SOAP aggregator
│       ├── pom.xml                      # Aggregator for SOAP services
│       └── sample/                      # Sample SOAP service (Port 8081)
│           ├── pom.xml
│           ├── src/main/java/.../soap/
│           │   ├── SoapApplication.java
│           │   ├── config/
│           │   ├── endpoint/
│           │   └── model/
│           ├── src/main/resources/
│           │   └── application.yml
│           └── src/test/java/...
│
├── infra/                               # ══ INFRASTRUCTURE MODULE ══
│   ├── pom.xml
│   ├── src/main/java/.../infra/
│   │   ├── InfraApp.java
│   │   ├── MainStack.java
│   │   └── construct/
│   │       ├── NetworkConstruct.java
│   │       ├── StorageConstruct.java
│   │       ├── RestApiConstruct.java
│   │       └── SoapApiConstruct.java
│   └── src/test/java/...
│
└── .gitignore
```

---

## Module Reference

### Common Modules

| Module | Artifact ID | Path | Purpose |
|--------|-------------|------|---------|
| Exception | `exception` | `common/exception` | Base/Business/Technical exceptions |
| Utils | `utils` | `common/utils` | JSON, Date, String utilities |
| Environment | `env` | `common/env` | Configuration management |
| AWS S3 | `aws-s3` | `common/aws/aws-s3` | S3 operations wrapper |
| AWS SQS | `aws-sqs` | `common/aws/aws-sqs` | SQS operations wrapper |
| AWS DynamoDB | `aws-dynamodb` | `common/aws/aws-dynamodb` | DynamoDB operations wrapper |

### Service Modules (Hierarchical)

| Aggregator | Module | Artifact ID | Path | Port |
|------------|--------|-------------|------|------|
| rest | Sample REST | `rest-sample` | `service/rest/sample` | 8080 |
| batch | Sample Batch | `batch-sample` | `service/batch/sample` | - |
| soap | Sample SOAP | `soap-sample` | `service/soap/sample` | 8081 |

**Structure**: Each service type (rest, batch, soap) is an aggregator that can contain multiple services.

### Infrastructure Module

| Module | Artifact ID | Path | Purpose |
|--------|-------------|------|---------|
| Infrastructure | `infra` | `infra` | AWS CDK constructs |

---

## Module Dependency Graph

```
┌─────────────────────────────────────────────────────────────────┐
│                         SERVICES                                │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐              │
│  │    rest     │  │    batch    │  │    soap     │              │
│  └──────┬──────┘  └──────┬──────┘  └──────┬──────┘              │
│         │                │                │                     │
│         └────────────────┼────────────────┘                     │
│                          │                                      │
│                          ▼                                      │
├─────────────────────────────────────────────────────────────────┤
│                         COMMON                                  │
│  ┌────────┐  ┌────────┐  ┌──────────────────────────────┐       │
│  │  env   │  │ utils  │  │           aws/               │       │
│  └────────┘  └────────┘  │ ┌───────┐┌───────┐┌────────┐ │       │
│                          │ │aws-s3 ││aws-sqs││aws-ddb │ │       │
│                          │ └───┬───┘└───┬───┘└───┬────┘ │       │
│                          └─────┼────────┼────────┼──────┘       │
│                                └────────┼────────┘              │
│                                         ▼                       │
│                                  ┌───────────┐                  │
│                                  │ exception │                  │
│                                  └───────────┘                  │
└─────────────────────────────────────────────────────────────────┘
```

---

## Technology Stack

| Category | Technology | Version |
|----------|------------|---------|
| Runtime | Java | 21 (LTS) |
| Framework | Spring Boot | 3.4.3 |
| Build | Apache Maven | 3.9+ |
| Cloud | AWS SDK v2 | 2.25.70 |
| Infrastructure | AWS CDK | 2.130.0 |
| Coverage | JaCoCo | 0.8.12 (100% enforced) |
| Formatting | Spotless | 2.43.0 (Google Java Format) |

---

## Quick Start

### Build Commands

```bash
# Full build with tests and coverage
mvn clean verify

# Format code (REQUIRED before commit)
mvn spotless:apply

# Build specific module
mvn clean verify -pl service/rest -am

# Run tests only
mvn test
```

### Run Services

```bash
# REST API (http://localhost:8080)
cd service/rest/sample && mvn spring-boot:run

# SOAP Service (http://localhost:8081)
cd service/soap/sample && mvn spring-boot:run

# Batch Jobs
cd service/batch/sample && mvn spring-boot:run
```

### Deploy Infrastructure

```bash
cd infra
cdk synth    # Generate CloudFormation
cdk deploy   # Deploy to AWS
```

---

## Dependencies

### Using Common Modules

```xml
<!-- Exception handling -->
<dependency>
    <groupId>com.example</groupId>
    <artifactId>exception</artifactId>
</dependency>

<!-- Utilities -->
<dependency>
    <groupId>com.example</groupId>
    <artifactId>utils</artifactId>
</dependency>

<!-- Environment config -->
<dependency>
    <groupId>com.example</groupId>
    <artifactId>env</artifactId>
</dependency>
```

### Using AWS Modules (only what you need)

```xml
<!-- S3 only -->
<dependency>
    <groupId>com.example</groupId>
    <artifactId>aws-s3</artifactId>
</dependency>

<!-- SQS only -->
<dependency>
    <groupId>com.example</groupId>
    <artifactId>aws-sqs</artifactId>
</dependency>

<!-- DynamoDB only -->
<dependency>
    <groupId>com.example</groupId>
    <artifactId>aws-dynamodb</artifactId>
</dependency>
```

---

## API Endpoints

### REST Service (Port 8080)

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/health` | Health check |
| GET | `/api/samples/{id}` | Get sample by ID |

### SOAP Service (Port 8081)

| Endpoint | Description |
|----------|-------------|
| POST `/ws` | SOAP request handler |
| GET `/ws/sample.wsdl` | WSDL definition |

---

## Key Conventions

### Exception Handling

```java
// Business errors (invalid input, rule violations)
throw new BusinessException("ERR_CODE", "User message");

// Technical errors (infrastructure failures)
throw new TechnicalException("ERR_CODE", "Technical message", cause);
```

### Code Quality

- **Coverage**: 100% line coverage (JaCoCo)
- **Format**: Google Java Format (Spotless)
- **Javadoc**: Required for public classes/methods
- **No emojis**: Never in code or comments

### Testing

```java
@ExtendWith(MockitoExtension.class)
class MyClassTest {
    @Mock private Dependency dep;
    @InjectMocks private MyClass myClass;

    @Test
    void shouldDoSomething() {
        // Arrange, Act, Assert
    }
}
```

---

## Adding New Modules

### New Common Module

```
common/new-module/
├── pom.xml           # Inherit from ../../pom.xml
├── README.md
├── src/
├── tests/
└── docs/
    ├── overview.md
    ├── api.md
    └── rules.md
```

### New Service Module

Services are organized hierarchically by type. To add a new service:

```
service/{type}/new-service/
├── pom.xml           # Inherit from ../../../pom.xml
├── README.md
├── src/
├── tests/
└── docs/
    ├── overview.md
    ├── api.md
    └── rules.md
```

Example: Adding a new batch job `notify-daily`:
1. Create `service/batch/notify-daily/`
2. Add `<module>notify-daily</module>` to `service/batch/pom.xml`
3. Set artifactId to `batch-notify-daily` in new module's pom.xml

---

## AI Agent Guidelines

### Context Loading Order

```
1. CLAUDE.md                  # Build & conventions
2. docs/system.md             # System architecture
3. module/README.md           # Module quick start
4. module/docs/overview.md    # Module design
5. module/docs/api.md         # API contracts
6. module/docs/rules.md       # Business rules
7. Target source file         # Code to modify
8. Target test file           # Expected behavior
```

---

## License

MIT
