# Dependency Analysis: Sample Mono Repository (Maven Java)

## 1. Overview

| Attribute | Value |
|-----------|-------|
| **Project Type** | Maven Multi-Module |
| **Total Modules** | 8 |
| **Build Tool** | Apache Maven 3.9+ |
| **Dependency Management** | Parent POM BOM imports |
| **Version Strategy** | Centralized in parent POM |

---

## 2. Module Hierarchy

### 2.1 Module Tree

```
sample-mono-repository (parent)
├── common (aggregator)
│   ├── common-exception
│   ├── common-utils
│   ├── common-env
│   └── common-aws
├── service (aggregator)
│   ├── service-rest
│   ├── service-batch
│   └── service-soap
└── infra
```

### 2.2 Module Types

| Module | Type | Packaging |
|--------|------|-----------|
| sample-mono-repository | Parent | pom |
| common | Aggregator | pom |
| service | Aggregator | pom |
| common-exception | Library | jar |
| common-utils | Library | jar |
| common-env | Library | jar |
| common-aws | Library | jar |
| service-rest | Application | jar (Spring Boot) |
| service-batch | Application | jar (Spring Boot) |
| service-soap | Application | jar (Spring Boot) |
| infra | CDK App | jar |

---

## 3. Internal Module Dependencies

### 3.1 Dependency Matrix

| Module | common-exception | common-utils | common-env | common-aws |
|--------|:---------------:|:------------:|:----------:|:----------:|
| common-exception | - | - | - | - |
| common-utils | - | - | - | - |
| common-env | - | - | - | - |
| common-aws | Y | - | - | - |
| service-rest | Y | - | Y | - |
| service-batch | Y | Y | Y | - |
| service-soap | Y | Y | Y | - |
| infra | - | - | - | - |

### 3.2 Dependency Graph

```
┌─────────────────────────────────────────────────────────────────────┐
│                          SERVICES                                    │
│                                                                      │
│  ┌───────────────┐   ┌───────────────┐   ┌───────────────┐         │
│  │  service-rest │   │ service-batch │   │  service-soap │         │
│  └───────┬───────┘   └───────┬───────┘   └───────┬───────┘         │
│          │                   │                   │                  │
│          │                   │                   │                  │
│    ┌─────┴─────┐      ┌──────┼──────┐     ┌──────┼──────┐          │
│    │           │      │      │      │     │      │      │          │
│    ▼           ▼      ▼      ▼      ▼     ▼      ▼      ▼          │
└────┼───────────┼──────┼──────┼──────┼─────┼──────┼──────┼──────────┘
     │           │      │      │      │     │      │      │
     │           │      │      │      │     │      │      │
┌────┼───────────┼──────┼──────┼──────┼─────┼──────┼──────┼──────────┐
│    ▼           │      ▼      │      │     ▼      │      │          │
│ ┌────────────────┐   ┌────────────────┐   ┌────────────────┐       │
│ │common-exception│   │  common-utils  │   │   common-env   │       │
│ └───────┬────────┘   └────────────────┘   └────────────────┘       │
│         │                                                           │
│         │ depends on                                                │
│         ▼                                                           │
│ ┌─────────────────────────────────────────────────────────────┐    │
│ │                       common-aws                             │    │
│ │    (S3ClientWrapper, SqsClientWrapper, DynamoDbClientWrapper)│    │
│ └─────────────────────────────────────────────────────────────┘    │
│                          COMMON                                      │
└─────────────────────────────────────────────────────────────────────┘
```

### 3.3 Service Dependencies

| Service | Required Modules |
|---------|------------------|
| service-rest | common-exception, common-env |
| service-batch | common-exception, common-utils, common-env |
| service-soap | common-exception, common-utils, common-env |

---

## 4. External Dependencies

### 4.1 Parent POM BOMs

| BOM | Version | Purpose |
|-----|---------|---------|
| spring-boot-dependencies | 3.4.3 | Spring Boot dependency versions |
| aws-sdk-bom | 2.25.70 | AWS SDK v2 dependency versions |

### 4.2 Dependencies by Module

#### common-exception

| Dependency | Scope | Version Source |
|------------|-------|----------------|
| junit-jupiter | test | Parent POM |

#### common-utils

| Dependency | Scope | Version Source |
|------------|-------|----------------|
| jackson-databind | compile | Spring Boot BOM |
| jackson-datatype-jsr310 | compile | Spring Boot BOM |
| junit-jupiter | test | Parent POM |

#### common-env

| Dependency | Scope | Version Source |
|------------|-------|----------------|
| junit-jupiter | test | Parent POM |

#### common-aws

| Dependency | Scope | Version Source |
|------------|-------|----------------|
| common-exception | compile | Parent POM |
| s3 (software.amazon.awssdk) | compile | AWS SDK BOM |
| sqs (software.amazon.awssdk) | compile | AWS SDK BOM |
| dynamodb (software.amazon.awssdk) | compile | AWS SDK BOM |
| junit-jupiter | test | Parent POM |
| mockito-core | test | Parent POM |

#### service-rest

| Dependency | Scope | Version Source |
|------------|-------|----------------|
| common-exception | compile | Parent POM |
| common-env | compile | Parent POM |
| spring-boot-starter-web | compile | Spring Boot BOM |
| spring-boot-starter-actuator | compile | Spring Boot BOM |
| spring-boot-starter-test | test | Spring Boot BOM |

#### service-batch

| Dependency | Scope | Version Source |
|------------|-------|----------------|
| common-exception | compile | Parent POM |
| common-utils | compile | Parent POM |
| common-env | compile | Parent POM |
| spring-boot-starter-batch | compile | Spring Boot BOM |
| hsqldb | runtime | Spring Boot BOM |
| spring-boot-starter-test | test | Spring Boot BOM |
| spring-batch-test | test | Spring Boot BOM |

#### service-soap

| Dependency | Scope | Version Source |
|------------|-------|----------------|
| common-exception | compile | Parent POM |
| common-utils | compile | Parent POM |
| common-env | compile | Parent POM |
| spring-boot-starter-web-services | compile | Spring Boot BOM |
| wsdl4j | compile | Spring Boot BOM |
| spring-boot-starter-test | test | Spring Boot BOM |

#### infra

| Dependency | Scope | Version Source |
|------------|-------|----------------|
| aws-cdk-lib | compile | Parent POM |
| constructs | compile | Parent POM |
| junit-jupiter | test | Parent POM |

---

## 5. Dependency Versions (Parent POM)

### 5.1 Framework Versions

| Dependency | Version |
|------------|---------|
| Spring Boot | 3.4.3 |
| AWS SDK v2 | 2.25.70 |
| AWS CDK | 2.130.0 |
| AWS Constructs | 10.3.0 |

### 5.2 Test Dependencies

| Dependency | Version |
|------------|---------|
| JUnit Jupiter | 5.10.2 |
| Mockito | 5.11.0 |

### 5.3 Build Plugins

| Plugin | Version |
|--------|---------|
| maven-compiler-plugin | 3.13.0 |
| maven-surefire-plugin | 3.2.5 |
| maven-jar-plugin | 3.4.1 |
| spring-boot-maven-plugin | 3.4.3 |
| jacoco-maven-plugin | 0.8.12 |
| spotless-maven-plugin | 2.43.0 |

---

## 6. Dependency Analysis

### 6.1 Circular Dependencies

**Status: NONE DETECTED**

The dependency graph is acyclic:
- Common modules have no inter-dependencies (except common-aws → common-exception)
- Service modules only depend on common modules
- Infrastructure module is completely isolated

### 6.2 Transitive Dependencies

| Primary | Brings In |
|---------|-----------|
| spring-boot-starter-web | Spring MVC, Jackson, Tomcat |
| spring-boot-starter-batch | Spring Batch, JPA |
| aws-sdk (s3, sqs, dynamodb) | AWS core, HTTP client |
| jackson-datatype-jsr310 | Jackson core |

### 6.3 Dependency Conflicts

**Status: NONE**

- Spring Boot BOM ensures consistent Spring versions
- AWS SDK BOM ensures consistent AWS SDK versions
- Parent POM pins all other versions

---

## 7. Module Coupling Analysis

### 7.1 Coupling Metrics

| Module | Afferent (used by) | Efferent (uses) | Instability |
|--------|-------------------|-----------------|-------------|
| common-exception | 4 | 0 | 0.00 (stable) |
| common-utils | 2 | 1 | 0.33 |
| common-env | 3 | 0 | 0.00 (stable) |
| common-aws | 0 | 1 | 1.00 (unstable) |
| service-rest | 0 | 2 | 1.00 |
| service-batch | 0 | 3 | 1.00 |
| service-soap | 0 | 3 | 1.00 |
| infra | 0 | 0 | N/A (isolated) |

**Note:** Services are expected to be unstable (high efferent coupling) as they are leaf nodes.

### 7.2 Dependency Depth

| Module | Max Depth |
|--------|-----------|
| common-exception | 0 |
| common-utils | 0 |
| common-env | 0 |
| common-aws | 1 |
| service-rest | 1 |
| service-batch | 1 |
| service-soap | 1 |
| infra | 0 |

---

## 8. Recommendations

### 8.1 Current Architecture Strengths

- Clear separation of concerns
- No circular dependencies
- Centralized version management
- Appropriate use of BOMs

### 8.2 Potential Improvements

| Area | Recommendation |
|------|----------------|
| common-aws usage | Services could leverage common-aws for AWS integration |
| Test dependencies | Consider test containers for integration tests |
| Documentation | Add dependency documentation in CLAUDE.md |

---

## 9. Build Order

Maven builds in this order (respecting dependencies):

```
1. common-exception  (no dependencies)
2. common-utils      (no internal dependencies)
3. common-env        (no internal dependencies)
4. common-aws        (depends on: common-exception)
5. service-rest      (depends on: common-exception, common-env)
6. service-batch     (depends on: common-exception, common-utils, common-env)
7. service-soap      (depends on: common-exception, common-utils, common-env)
8. infra             (no internal dependencies)
```

**Command:** `mvn clean install` builds all modules in dependency order.
