# Project Analysis: Sample Mono Repository (Maven Java)

## Project Overview

| Attribute | Value |
|-----------|-------|
| **Project Name** | sample-mono-repository |
| **Project Type** | Maven Multi-Module Mono-repository |
| **Primary Language** | Java 21 (LTS) |
| **Framework** | Spring Boot 3.4.3 |
| **Database** | HSQLDB (Batch), DynamoDB (via CDK) |
| **Build Tool** | Apache Maven 3.9+ |
| **Architecture** | Modular Monolith with Multi-Service |

---

## Technology Stack

### Core Technologies

| Category | Technology | Version | Purpose |
|----------|------------|---------|---------|
| Language | Java | 21 (LTS) | Primary runtime |
| Framework | Spring Boot | 3.4.3 | Application framework |
| Framework | Spring Batch | (BOM) | Batch processing |
| Framework | Spring WS | (BOM) | SOAP services |
| Cloud SDK | AWS SDK v2 | 2.25.70 | AWS service integration |
| IaC | AWS CDK | 2.130.0 | Infrastructure as Code |
| Build | Maven | 3.9+ | Build and dependency management |

### Key Libraries/Dependencies

| Library | Version | Purpose |
|---------|---------|---------|
| Jackson | (BOM) | JSON serialization |
| HSQLDB | (BOM) | In-memory database (Batch) |
| WSDL4J | (BOM) | WSDL support (SOAP) |
| JUnit 5 | 5.10.2 | Testing framework |
| Mockito | 5.11.0 | Mocking framework |
| JaCoCo | 0.8.12 | Code coverage (100% required) |
| Spotless | 2.43.0 | Google Java Format |

---

## Framework Patterns Demonstrated

| Pattern | Implementation | Purpose |
|---------|----------------|---------|
| Modular Monolith | Maven multi-module | Code organization |
| REST API | Spring MVC @RestController | HTTP endpoints |
| SOAP Service | Spring WS @Endpoint | SOAP web services |
| Batch Processing | Spring Batch Job/Tasklet | Background jobs |
| AWS Wrappers | SDK v2 client wrappers | Simplified AWS access |
| Infrastructure as Code | AWS CDK constructs | Cloud provisioning |
| Exception Hierarchy | Base/Business/Technical | Error handling |
| Centralized Config | Parent POM BOM | Version management |

---

## Business Target

| Aspect | Description |
|--------|-------------|
| **Purpose** | Demonstrate enterprise Java patterns with Spring Boot and AWS |
| **Domain** | Sample/Demo application |
| **Use Case** | REST APIs, Batch jobs, SOAP services with AWS infrastructure |
| **Complexity** | Medium (multi-service with cloud infrastructure) |

---

## Module/Package Summary

| Module | Location | Type | Description |
|--------|----------|------|-------------|
| common-exception | `/common/common-exception` | Library | Exception hierarchy (Base, Business, Technical) |
| common-utils | `/common/common-utils` | Library | JSON, Date, String utilities |
| common-env | `/common/common-env` | Library | Environment and config management |
| common-aws | `/common/common-aws` | Library | AWS SDK wrappers (S3, SQS, DynamoDB) |
| service-rest | `/service/service-rest` | Application | REST API service (port 8080) |
| service-batch | `/service/service-batch` | Application | Batch processing service |
| service-soap | `/service/service-soap` | Application | SOAP web service (port 8081) |
| infra | `/infra` | CDK App | AWS infrastructure definitions |

---

## Entry Points

| Service | Class | Port | Protocol |
|---------|-------|------|----------|
| REST | `com.example.service.rest.Application` | 8080 | HTTP/REST |
| Batch | `com.example.service.batch.BatchApplication` | - | Job |
| SOAP | `com.example.service.soap.SoapApplication` | 8081 | HTTP/SOAP |
| CDK | `com.example.infra.InfraApp` | - | CDK |

---

## API Endpoints

### REST API (port 8080)

| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/health` | Health check |
| GET | `/api/samples/{id}` | Get sample by ID |

### SOAP API (port 8081)

| Path | Description |
|------|-------------|
| POST `/ws` | SOAP endpoint |
| GET `/ws/sample.wsdl` | WSDL definition |

---

## AWS Resources (CDK)

| Resource | Type | Purpose |
|----------|------|---------|
| VPC | Networking | 10.0.0.0/16, 2 AZs |
| S3 Bucket | Storage | Object storage |
| SQS Queue | Messaging | Message queue |
| DynamoDB Table | Database | NoSQL storage |
| API Gateway | Integration | REST/SOAP exposure |

---

## Analysis Documents

| Document | Path | Description |
|----------|------|-------------|
| Architecture Analysis | [architecture-analysis.md](./architecture-analysis.md) | System architecture, layers, patterns |
| Project Structure | [project-structure-analysis.md](./project-structure-analysis.md) | Directory layout, module organization |
| Data Flow Analysis | [data-flow-analysis.md](./data-flow-analysis.md) | Request flows, AWS integrations |
| Sequence Analysis | [sequence-analysis.md](./sequence-analysis.md) | Component interaction sequences |
| Infrastructure Analysis | [infrastructure-analysis.md](./infrastructure-analysis.md) | AWS CDK resources, networking |
| C4 Model Analysis | [c4-model-analysis.md](./c4-model-analysis.md) | Context, Container, Component views |
| API Contract Analysis | [api-contract-analysis.md](./api-contract-analysis.md) | REST/SOAP endpoint specifications |
| Dependency Analysis | [dependency-analysis.md](./dependency-analysis.md) | Module dependencies, external libraries |

---

## Diagram Artifacts

| Diagram | Path | Description |
|---------|------|-------------|
| Architecture Overview | [../diagrams/architecture-overview.drawio](../diagrams/architecture-overview.drawio) | Main system architecture |
| C4 Context | [../diagrams/c4-1-context.drawio](../diagrams/c4-1-context.drawio) | System context view |
| C4 Container | [../diagrams/c4-2-container.drawio](../diagrams/c4-2-container.drawio) | Container view |
| Infrastructure | [../diagrams/infrastructure.drawio](../diagrams/infrastructure.drawio) | AWS infrastructure |
| Module Dependencies | [../diagrams/module-dependencies.drawio](../diagrams/module-dependencies.drawio) | Dependency graph |

---

## Quick Start

### Build All Modules

```bash
mvn clean install
```

### Run Services

```bash
# REST Service
mvn spring-boot:run -pl service/service-rest

# Batch Service
mvn spring-boot:run -pl service/service-batch

# SOAP Service
mvn spring-boot:run -pl service/service-soap
```

### Deploy Infrastructure

```bash
cd infra
cdk synth
cdk deploy
```

---

## Quality Standards

| Aspect | Tool | Requirement |
|--------|------|-------------|
| Code Coverage | JaCoCo | 100% line coverage |
| Formatting | Spotless | Google Java Format |
| Testing | JUnit 5 + Mockito | All modules tested |

---

## License

MIT License
