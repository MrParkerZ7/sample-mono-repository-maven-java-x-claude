# Architecture Analysis: Sample Mono Repository (Maven Java)

## 1. Application Overview

| Attribute | Value |
|-----------|-------|
| **Project Name** | sample-mono-repository |
| **Description** | Multi-module Maven mono-repository demonstrating enterprise Java patterns with Spring Boot services and AWS CDK infrastructure |
| **Primary Language** | Java 21 (LTS) |
| **Build Tool** | Apache Maven 3.9+ |
| **Framework** | Spring Boot 3.4.3 |
| **Application Type** | Multi-service Mono-repository (REST API, Batch Processing, SOAP Service) |

### Major Libraries

| Library | Version | Purpose |
|---------|---------|---------|
| Spring Boot | 3.4.3 | Application framework |
| Spring Web | (BOM) | REST API support |
| Spring Batch | (BOM) | Batch job processing |
| Spring Web Services | (BOM) | SOAP service support |
| AWS SDK v2 | 2.25.70 | AWS service integration |
| AWS CDK | 2.130.0 | Infrastructure as Code |
| Jackson | (BOM) | JSON serialization |
| JUnit 5 | 5.10.2 | Testing framework |
| Mockito | 5.11.0 | Mocking framework |
| JaCoCo | 0.8.12 | Code coverage |
| Spotless | 2.43.0 | Code formatting |

---

## 2. Architecture Pattern

### Pattern: Modular Monolith with Multi-Service Architecture

This project follows a **Modular Monolith** pattern organized as a Maven multi-module build:

| Aspect | Description |
|--------|-------------|
| **Structure** | Parent POM with aggregator modules containing leaf modules |
| **Coupling** | Loose coupling via shared library modules |
| **Deployment** | Each service deployable as independent Spring Boot JAR |
| **Dependency Management** | Centralized via parent POM BOM imports |

### Layer Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                    Service Layer                            │
│  ┌───────────────┐  ┌───────────────┐  ┌───────────────┐   │
│  │  service-rest │  │ service-batch │  │  service-soap │   │
│  │    (REST)     │  │   (Batch)     │  │    (SOAP)     │   │
│  └───────┬───────┘  └───────┬───────┘  └───────┬───────┘   │
└──────────┼──────────────────┼──────────────────┼───────────┘
           │                  │                  │
           ▼                  ▼                  ▼
┌─────────────────────────────────────────────────────────────┐
│                    Common Layer                             │
│  ┌──────────────┐ ┌─────────────┐ ┌──────────────────────┐ │
│  │common-except │ │common-utils │ │   common-env         │ │
│  └──────────────┘ └─────────────┘ └──────────────────────┘ │
│                         │                                   │
│                         ▼                                   │
│              ┌─────────────────┐                           │
│              │   common-aws    │                           │
│              └─────────────────┘                           │
└─────────────────────────────────────────────────────────────┘
           │
           ▼
┌─────────────────────────────────────────────────────────────┐
│                 Infrastructure Layer                        │
│  ┌─────────────────────────────────────────────────────┐   │
│  │   infra (AWS CDK)                                    │   │
│  │   - NetworkConstruct (VPC, Subnets)                 │   │
│  │   - StorageConstruct (S3, SQS, DynamoDB)            │   │
│  │   - RestApiConstruct (API Gateway)                  │   │
│  │   - SoapApiConstruct (API Gateway)                  │   │
│  └─────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
```

### Dependency Direction

- Services depend on Common modules (downward)
- Common modules have no cyclic dependencies
- Infrastructure module is independent (deployment only)

---

## 3. Entry Points

### Application Services

| Service | Main Class | Port | Protocol |
|---------|-----------|------|----------|
| REST API | `com.example.service.rest.Application` | 8080 | HTTP/REST |
| Batch | `com.example.service.batch.BatchApplication` | - | Job Execution |
| SOAP | `com.example.service.soap.SoapApplication` | 8081 | HTTP/SOAP |

### API Endpoints

| Service | Method | Path | Description |
|---------|--------|------|-------------|
| REST | GET | `/api/health` | Health check |
| REST | GET | `/api/samples/{id}` | Get sample by ID |
| SOAP | POST | `/ws` | SOAP endpoint |
| SOAP | GET | `/ws/sample.wsdl` | WSDL definition |

### Background Jobs

| Job | Trigger | Description |
|-----|---------|-------------|
| sampleJob | Manual/Scheduled | Sample batch processing tasklet |

### Infrastructure Entry Point

| Component | Main Class | Description |
|-----------|-----------|-------------|
| CDK App | `com.example.infra.InfraApp` | AWS infrastructure deployment |

---

## 4. Components & Modules

### Service Modules

#### service-rest
| Aspect | Details |
|--------|---------|
| **Purpose** | REST API service for HTTP endpoints |
| **Package** | `com.example.service.rest` |
| **Dependencies** | common-exception, common-env, Spring Web, Spring Actuator |
| **Key Classes** | `HealthController`, `SampleController` |

#### service-batch
| Aspect | Details |
|--------|---------|
| **Purpose** | Batch job processing service |
| **Package** | `com.example.service.batch` |
| **Dependencies** | common-exception, common-utils, common-env, Spring Batch, HSQLDB |
| **Key Classes** | `SampleJobConfig`, `SampleTasklet` |

#### service-soap
| Aspect | Details |
|--------|---------|
| **Purpose** | SOAP web service endpoint |
| **Package** | `com.example.service.soap` |
| **Dependencies** | common-exception, common-utils, common-env, Spring WS, WSDL4J |
| **Key Classes** | `SampleEndpoint`, `WebServiceConfig`, `GetSampleRequest`, `GetSampleResponse` |

### Common Modules

#### common-exception
| Aspect | Details |
|--------|---------|
| **Purpose** | Centralized exception hierarchy |
| **Package** | `com.example.common.exception` |
| **Dependencies** | None (leaf module) |
| **Key Classes** | `BaseException`, `BusinessException`, `TechnicalException` |
| **Dependents** | All service modules, common-aws |

#### common-utils
| Aspect | Details |
|--------|---------|
| **Purpose** | General utility functions |
| **Package** | `com.example.common.utils` |
| **Dependencies** | Jackson (DataBind, JSR310) |
| **Key Classes** | `JsonUtils`, `DateUtils`, `StringUtils` |
| **Dependents** | service-batch, service-soap |

#### common-env
| Aspect | Details |
|--------|---------|
| **Purpose** | Environment and configuration management |
| **Package** | `com.example.common.env` |
| **Dependencies** | None (leaf module) |
| **Key Classes** | `EnvironmentManager`, `ConfigurationProvider` |
| **Dependents** | All service modules |

#### common-aws
| Aspect | Details |
|--------|---------|
| **Purpose** | AWS SDK wrappers for S3, SQS, DynamoDB |
| **Package** | `com.example.common.aws` |
| **Dependencies** | common-exception, AWS SDK (S3, SQS, DynamoDB) |
| **Key Classes** | `S3ClientWrapper`, `SqsClientWrapper`, `DynamoDbClientWrapper` |
| **Dependents** | Available for all services |

### Infrastructure Module

#### infra
| Aspect | Details |
|--------|---------|
| **Purpose** | AWS CDK infrastructure definitions |
| **Package** | `com.example.infra` |
| **Dependencies** | AWS CDK, AWS Constructs |
| **Key Classes** | `InfraApp`, `MainStack`, `NetworkConstruct`, `StorageConstruct`, `RestApiConstruct`, `SoapApiConstruct` |

---

## 5. External Integrations

### AWS Services

| Service | Purpose | Wrapper Class |
|---------|---------|---------------|
| **S3** | Object storage | `S3ClientWrapper` |
| **SQS** | Message queuing | `SqsClientWrapper` |
| **DynamoDB** | NoSQL database | `DynamoDbClientWrapper` |
| **API Gateway** | REST/SOAP exposure | CDK constructs |
| **VPC** | Network isolation | `NetworkConstruct` |

### Cloud Infrastructure (via CDK)

| Resource | Configuration |
|----------|--------------|
| VPC | CIDR: 10.0.0.0/16, 2 AZs |
| Public Subnet | CIDR /24, NAT Gateway |
| Private Subnet | CIDR /24, Egress allowed |
| Isolated Subnet | CIDR /24, No external access |
| S3 Bucket | S3-managed encryption, private |
| SQS Queue | Standard queue |
| DynamoDB Table | PAY_PER_REQUEST, PK: "id" |

---

## 6. Cross-Cutting Concerns

### Exception Handling

| Exception Type | Base Class | Error Code | Usage |
|---------------|------------|------------|-------|
| Business errors | `BusinessException` | `ERR_BUSINESS` | Business rule violations |
| Technical errors | `TechnicalException` | `ERR_TECHNICAL` | Infrastructure failures |
| AWS errors | `TechnicalException` | `S3_*`, `DYNAMODB_*`, etc. | AWS SDK failures |

### Logging Strategy

| Service | Logger | Configuration |
|---------|--------|--------------|
| All services | SLF4J/Logback | Spring Boot defaults |
| Test | Logback | Console appender |

### Configuration Management

| Method | Implementation |
|--------|---------------|
| Properties | `application.yml` per service |
| Environment | `EnvironmentManager.getEnv()` |
| System Properties | `EnvironmentManager.getProperty()` |
| Profiles | `isDevelopment()`, `isProduction()`, `isTest()` |

### Code Quality

| Aspect | Tool | Configuration |
|--------|------|---------------|
| Coverage | JaCoCo | 100% line coverage required |
| Formatting | Spotless | Google Java Format 1.19.2 |
| Testing | JUnit 5 + Mockito | Unit and integration tests |

### Build Lifecycle

| Phase | Action |
|-------|--------|
| validate | Spotless format check |
| test | JUnit execution + JaCoCo instrumentation |
| verify | JaCoCo coverage check (100% required) |
| package | Spring Boot executable JAR |
