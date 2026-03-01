# Project Structure Analysis: Sample Mono Repository (Maven Java)

## 1. Overview

| Attribute | Value |
|-----------|-------|
| **Project Type** | Maven Multi-Module Mono-repository |
| **Primary Language** | Java 21 |
| **Build Tool** | Apache Maven 3.9+ |
| **IaC Tool** | AWS CDK (Java) |
| **Total Modules** | 8 (4 common + 3 service + 1 infra) |
| **Total Java Files** | ~52 (19 main + 33 test) |

---

## 2. Root Directory Structure

```
sample-mono-repository-maven-java-x-claude/
├── pom.xml                    # Parent POM (BOM + Plugin Management)
├── README.md                  # Project documentation
├── CLAUDE.md                  # Claude Code instructions
├── .gitignore                 # Git ignore rules
├── .claude/                   # Claude Code configuration
├── docs/                      # Documentation output
│   ├── analysis/             # Analysis markdown files
│   └── diagrams/             # DrawIO diagram files
├── common/                    # Shared library modules
│   └── pom.xml               # Aggregator POM
├── service/                   # Application service modules
│   └── pom.xml               # Aggregator POM
└── infra/                     # Infrastructure as Code
    └── pom.xml               # CDK module POM
```

### Key Root Files

| File | Purpose |
|------|---------|
| `pom.xml` | Parent POM with BOM imports and plugin management |
| `README.md` | Project overview and getting started guide |
| `CLAUDE.md` | Claude Code AI assistant configuration |
| `.gitignore` | Standard Java/Maven ignore patterns |

---

## 3. Module/Package Structure

### 3.1 Common Modules (`common/`)

```
common/
├── pom.xml                           # Aggregator POM
├── common-exception/
│   ├── pom.xml
│   └── src/
│       ├── main/java/com/example/common/exception/
│       │   ├── BaseException.java
│       │   ├── BusinessException.java
│       │   └── TechnicalException.java
│       └── test/java/com/example/common/exception/
│           ├── BaseExceptionTest.java
│           ├── BusinessExceptionTest.java
│           └── TechnicalExceptionTest.java
│
├── common-utils/
│   ├── pom.xml
│   └── src/
│       ├── main/java/com/example/common/utils/
│       │   ├── JsonUtils.java
│       │   ├── DateUtils.java
│       │   └── StringUtils.java
│       └── test/java/com/example/common/utils/
│           ├── JsonUtilsTest.java
│           ├── DateUtilsTest.java
│           └── StringUtilsTest.java
│
├── common-env/
│   ├── pom.xml
│   └── src/
│       ├── main/java/com/example/common/env/
│       │   ├── EnvironmentManager.java
│       │   └── ConfigurationProvider.java
│       └── test/java/com/example/common/env/
│           ├── EnvironmentManagerTest.java
│           └── ConfigurationProviderTest.java
│
└── common-aws/
    ├── pom.xml
    └── src/
        ├── main/java/com/example/common/aws/
        │   ├── S3ClientWrapper.java
        │   ├── SqsClientWrapper.java
        │   └── DynamoDbClientWrapper.java
        └── test/java/com/example/common/aws/
            ├── S3ClientWrapperTest.java
            ├── SqsClientWrapperTest.java
            └── DynamoDbClientWrapperTest.java
```

#### Module Details

| Module | Purpose | Key Classes |
|--------|---------|-------------|
| `common-exception` | Exception hierarchy | `BaseException`, `BusinessException`, `TechnicalException` |
| `common-utils` | Utility functions | `JsonUtils`, `DateUtils`, `StringUtils` |
| `common-env` | Environment management | `EnvironmentManager`, `ConfigurationProvider` |
| `common-aws` | AWS SDK wrappers | `S3ClientWrapper`, `SqsClientWrapper`, `DynamoDbClientWrapper` |

### 3.2 Service Modules (`service/`)

```
service/
├── pom.xml                           # Aggregator POM
├── service-rest/
│   ├── pom.xml
│   └── src/
│       ├── main/
│       │   ├── java/com/example/service/rest/
│       │   │   ├── Application.java
│       │   │   └── controller/
│       │   │       ├── HealthController.java
│       │   │       └── SampleController.java
│       │   └── resources/
│       │       └── application.yml
│       └── test/java/com/example/service/rest/
│           ├── ApplicationTest.java
│           └── controller/
│               ├── HealthControllerTest.java
│               └── SampleControllerTest.java
│
├── service-batch/
│   ├── pom.xml
│   └── src/
│       ├── main/
│       │   ├── java/com/example/service/batch/
│       │   │   ├── BatchApplication.java
│       │   │   ├── config/
│       │   │   │   └── SampleJobConfig.java
│       │   │   └── job/
│       │   │       └── SampleTasklet.java
│       │   └── resources/
│       │       └── application.yml
│       └── test/java/com/example/service/batch/
│           ├── BatchApplicationTest.java
│           ├── config/
│           │   └── SampleJobConfigTest.java
│           └── job/
│               └── SampleTaskletTest.java
│
└── service-soap/
    ├── pom.xml
    └── src/
        ├── main/
        │   ├── java/com/example/service/soap/
        │   │   ├── SoapApplication.java
        │   │   ├── config/
        │   │   │   └── WebServiceConfig.java
        │   │   ├── endpoint/
        │   │   │   └── SampleEndpoint.java
        │   │   └── model/
        │   │       ├── GetSampleRequest.java
        │   │       └── GetSampleResponse.java
        │   └── resources/
        │       └── application.yml
        └── test/java/com/example/service/soap/
            ├── SoapApplicationTest.java
            ├── config/
            │   └── WebServiceConfigTest.java
            └── endpoint/
                └── SampleEndpointTest.java
```

#### Service Details

| Service | Port | Type | Key Components |
|---------|------|------|----------------|
| `service-rest` | 8080 | REST API | Controllers, Health endpoint |
| `service-batch` | - | Batch | Job configuration, Tasklets |
| `service-soap` | 8081 | SOAP WS | Endpoints, Request/Response models |

### 3.3 Infrastructure Module (`infra/`)

```
infra/
├── pom.xml
├── cdk.json                          # CDK configuration
└── src/
    ├── main/java/com/example/infra/
    │   ├── InfraApp.java             # CDK app entry point
    │   ├── MainStack.java            # Main CloudFormation stack
    │   └── construct/
    │       ├── NetworkConstruct.java  # VPC, Subnets
    │       ├── StorageConstruct.java  # S3, SQS, DynamoDB
    │       ├── RestApiConstruct.java  # API Gateway (REST)
    │       └── SoapApiConstruct.java  # API Gateway (SOAP)
    └── test/java/com/example/infra/
        ├── InfraAppTest.java
        ├── MainStackTest.java
        └── construct/
            ├── NetworkConstructTest.java
            ├── StorageConstructTest.java
            ├── RestApiConstructTest.java
            └── SoapApiConstructTest.java
```

---

## 4. Source Code Organization

### Package Structure Convention

```
com.example
├── common
│   ├── exception      # Exception classes
│   ├── utils          # Utility classes
│   ├── env            # Environment/config
│   └── aws            # AWS wrappers
├── service
│   ├── rest
│   │   ├── controller # REST controllers
│   │   └── ...
│   ├── batch
│   │   ├── config     # Job configuration
│   │   ├── job        # Tasklets/steps
│   │   └── ...
│   └── soap
│       ├── config     # WS configuration
│       ├── endpoint   # SOAP endpoints
│       ├── model      # Request/Response
│       └── ...
└── infra
    ├── construct      # CDK constructs
    └── ...
```

### Standard Maven Layout

| Directory | Purpose |
|-----------|---------|
| `src/main/java` | Production source code |
| `src/main/resources` | Configuration files |
| `src/test/java` | Test source code |
| `src/test/resources` | Test configuration |

### Test Coverage

| Module Type | Test Pattern | Coverage |
|-------------|--------------|----------|
| Common | `*Test.java` | 100% required |
| Service | `*Test.java` | 100% required |
| Infra | `*Test.java` | 100% required |

---

## 5. Build & Configuration Files

### Parent POM Structure

```xml
<project>
  <groupId>com.example</groupId>
  <artifactId>sample-mono-repository</artifactId>
  <version>1.0.0-SNAPSHOT</version>
  <packaging>pom</packaging>

  <modules>
    <module>common</module>
    <module>service</module>
    <module>infra</module>
  </modules>

  <dependencyManagement>
    <!-- Spring Boot BOM -->
    <!-- AWS SDK BOM -->
    <!-- Internal modules -->
  </dependencyManagement>

  <build>
    <pluginManagement>
      <!-- Compiler, Surefire, JAR, Spring Boot, JaCoCo, Spotless -->
    </pluginManagement>
  </build>
</project>
```

### Key Configuration Files

| File | Location | Purpose |
|------|----------|---------|
| `pom.xml` (root) | `/` | Parent BOM and plugin management |
| `pom.xml` (common) | `/common/` | Aggregator for common modules |
| `pom.xml` (service) | `/service/` | Aggregator for service modules |
| `application.yml` | `*/src/main/resources/` | Spring Boot configuration |
| `cdk.json` | `/infra/` | AWS CDK app configuration |

### Application Configurations

#### service-rest (`application.yml`)
```yaml
server:
  port: 8080
spring:
  application:
    name: service-rest
management:
  endpoints:
    web:
      exposure:
        include: health,info
```

#### service-batch (`application.yml`)
```yaml
spring:
  application:
    name: service-batch
  batch:
    job:
      enabled: false
  datasource:
    url: jdbc:hsqldb:mem:testdb
    username: sa
    driver-class-name: org.hsqldb.jdbc.JDBCDriver
  jpa:
    hibernate:
      ddl-auto: create-drop
```

#### service-soap (`application.yml`)
```yaml
server:
  port: 8081
spring:
  application:
    name: service-soap
```

---

## 6. Module Dependencies

### Internal Dependency Graph

```
┌─────────────────────────────────────────────────────────────────┐
│                         SERVICES                                │
│  ┌────────────────┐  ┌────────────────┐  ┌────────────────┐    │
│  │  service-rest  │  │ service-batch  │  │  service-soap  │    │
│  └───────┬────────┘  └───────┬────────┘  └───────┬────────┘    │
└──────────┼───────────────────┼───────────────────┼──────────────┘
           │                   │                   │
           │ depends on        │ depends on        │ depends on
           ▼                   ▼                   ▼
┌─────────────────────────────────────────────────────────────────┐
│                          COMMON                                  │
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐ │
│  │common-exception │  │  common-utils   │  │   common-env    │ │
│  └────────┬────────┘  └─────────────────┘  └─────────────────┘ │
│           │                                                      │
│           │ depends on                                           │
│           ▼                                                      │
│  ┌─────────────────────────────────────────────────────────────┐│
│  │                      common-aws                              ││
│  │  (S3ClientWrapper, SqsClientWrapper, DynamoDbClientWrapper) ││
│  └─────────────────────────────────────────────────────────────┘│
└─────────────────────────────────────────────────────────────────┘
```

### Dependency Matrix

| Module | common-exception | common-utils | common-env | common-aws |
|--------|:---------------:|:------------:|:----------:|:----------:|
| service-rest | Y | - | Y | - |
| service-batch | Y | Y | Y | - |
| service-soap | Y | Y | Y | - |
| common-aws | Y | - | - | - |

---

## 7. Naming Conventions

### File Naming

| Type | Pattern | Example |
|------|---------|---------|
| Main class | `Application.java` or `{Name}Application.java` | `BatchApplication.java` |
| Controller | `{Name}Controller.java` | `HealthController.java` |
| Service | `{Name}Service.java` | N/A |
| Configuration | `{Name}Config.java` | `SampleJobConfig.java` |
| Exception | `{Name}Exception.java` | `BusinessException.java` |
| Test | `{Name}Test.java` | `JsonUtilsTest.java` |
| Wrapper | `{Service}ClientWrapper.java` | `S3ClientWrapper.java` |
| Construct | `{Name}Construct.java` | `NetworkConstruct.java` |

### Package Naming

| Level | Pattern | Example |
|-------|---------|---------|
| Group | `com.example` | - |
| Module | `com.example.{module}` | `com.example.common.exception` |
| Feature | `com.example.{module}.{feature}` | `com.example.service.rest.controller` |

### Module Naming

| Category | Pattern | Examples |
|----------|---------|----------|
| Common | `common-{purpose}` | `common-exception`, `common-utils` |
| Service | `service-{type}` | `service-rest`, `service-batch` |
| Infrastructure | `infra` | Single module |

### Maven Artifact Naming

| POM Type | artifactId Pattern |
|----------|-------------------|
| Parent | `sample-mono-repository` |
| Aggregator | Same as directory |
| Leaf | `{directory-name}` |
