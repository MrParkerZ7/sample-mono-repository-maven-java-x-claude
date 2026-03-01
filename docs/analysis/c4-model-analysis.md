# C4 Model Analysis: Sample Mono Repository (Maven Java)

## Overview

This document provides C4 model analysis for the Sample Mono Repository system at three levels:
- **Level 1: System Context** - How the system fits in the world
- **Level 2: Container** - Major deployable units
- **Level 3: Component** - Internal structure of containers

---

## Level 1: System Context

### System Description

| Attribute | Value |
|-----------|-------|
| **System Name** | Sample Mono Repository System |
| **Description** | Multi-service Java application providing REST API, Batch processing, and SOAP web services with AWS infrastructure |
| **Key Capabilities** | RESTful APIs, Batch jobs, SOAP services, AWS integration |

### Actors/Users

| Actor | Type | Description | Interactions |
|-------|------|-------------|--------------|
| **API Consumer** | Person/System | External client consuming REST APIs | GET /api/health, GET /api/samples/{id} |
| **SOAP Client** | Person/System | External client using SOAP services | POST /ws (SOAP requests) |
| **Batch Operator** | Person | Administrator triggering batch jobs | Job execution, monitoring |
| **DevOps Engineer** | Person | Infrastructure operator | CDK deployment, monitoring |

### External Systems

| System | Type | Description | Data Exchange |
|--------|------|-------------|---------------|
| **AWS S3** | External Service | Object storage service | Store/retrieve files |
| **AWS SQS** | External Service | Message queue service | Send/receive messages |
| **AWS DynamoDB** | External Service | NoSQL database service | CRUD operations |
| **AWS API Gateway** | External Service | API management | Route requests |

### Context Diagram Description

```
┌─────────────────────────────────────────────────────────────────────────┐
│                                                                         │
│  ┌────────────┐     ┌────────────┐     ┌────────────┐                  │
│  │    API     │     │   SOAP     │     │   Batch    │                  │
│  │  Consumer  │     │   Client   │     │  Operator  │                  │
│  └─────┬──────┘     └─────┬──────┘     └─────┬──────┘                  │
│        │                  │                  │                          │
│        │ REST API         │ SOAP             │ Trigger                  │
│        │                  │                  │                          │
│        ▼                  ▼                  ▼                          │
│  ┌─────────────────────────────────────────────────────────────┐       │
│  │                                                             │       │
│  │              SAMPLE MONO REPOSITORY SYSTEM                  │       │
│  │                                                             │       │
│  │  Provides REST APIs, Batch processing, and SOAP services   │       │
│  │                                                             │       │
│  └───────────────────────────┬─────────────────────────────────┘       │
│                              │                                          │
│           ┌──────────────────┼──────────────────┐                      │
│           │                  │                  │                      │
│           ▼                  ▼                  ▼                      │
│    ┌────────────┐     ┌────────────┐     ┌────────────┐               │
│    │  AWS S3    │     │  AWS SQS   │     │  DynamoDB  │               │
│    │  (Storage) │     │  (Queue)   │     │  (NoSQL)   │               │
│    └────────────┘     └────────────┘     └────────────┘               │
│                                                                         │
└─────────────────────────────────────────────────────────────────────────┘
```

---

## Level 2: Container Diagram

### Containers (Deployable Units)

| Container | Technology | Description | Responsibilities |
|-----------|------------|-------------|------------------|
| **service-rest** | Spring Boot 3.4.3, Java 21 | REST API Service | Handle HTTP requests, health checks, sample endpoints |
| **service-batch** | Spring Boot 3.4.3, Spring Batch | Batch Processing Service | Execute scheduled/triggered batch jobs |
| **service-soap** | Spring Boot 3.4.3, Spring WS | SOAP Web Service | Handle SOAP requests, WSDL exposure |
| **common-aws** | Java 21, AWS SDK v2 | AWS Integration Library | Wrap S3, SQS, DynamoDB operations |
| **common-utils** | Java 21, Jackson | Utility Library | JSON serialization, date/string utilities |
| **common-env** | Java 21 | Environment Library | Configuration and profile management |
| **common-exception** | Java 21 | Exception Library | Centralized exception hierarchy |
| **AWS API Gateway** | AWS Service | API Gateway | Route external requests to services |
| **AWS S3** | AWS Service | Object Storage | File storage |
| **AWS SQS** | AWS Service | Message Queue | Async messaging |
| **AWS DynamoDB** | AWS Service | NoSQL Database | Data persistence |

### Container Relationships

| From | To | Protocol | Description |
|------|----|----------|-------------|
| API Consumer | AWS API Gateway | HTTPS | External API access |
| AWS API Gateway | service-rest | HTTP | Route REST requests |
| AWS API Gateway | service-soap | HTTP | Route SOAP requests |
| service-rest | common-exception | Java | Exception handling |
| service-rest | common-env | Java | Configuration access |
| service-batch | common-exception | Java | Exception handling |
| service-batch | common-utils | Java | JSON/utilities |
| service-batch | common-env | Java | Configuration access |
| service-soap | common-exception | Java | Exception handling |
| service-soap | common-utils | Java | JSON/utilities |
| service-soap | common-env | Java | Configuration access |
| service-* | common-aws | Java | AWS service access |
| common-aws | AWS S3 | HTTPS | Object operations |
| common-aws | AWS SQS | HTTPS | Queue operations |
| common-aws | AWS DynamoDB | HTTPS | Database operations |

### Container Diagram Description

```
┌─────────────────────────────────────────────────────────────────────────────────┐
│                              System Boundary                                     │
│                                                                                  │
│  ┌─────────────────────────────────────────────────────────────────────────┐   │
│  │                         Service Layer                                    │   │
│  │                                                                          │   │
│  │   ┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐        │   │
│  │   │  service-rest   │  │  service-batch  │  │  service-soap   │        │   │
│  │   │                 │  │                 │  │                 │        │   │
│  │   │ Spring Boot     │  │ Spring Boot     │  │ Spring Boot     │        │   │
│  │   │ REST API        │  │ Spring Batch    │  │ Spring WS       │        │   │
│  │   │ Port: 8080      │  │ (Jobs)          │  │ Port: 8081      │        │   │
│  │   └────────┬────────┘  └────────┬────────┘  └────────┬────────┘        │   │
│  │            │                    │                    │                  │   │
│  └────────────┼────────────────────┼────────────────────┼──────────────────┘   │
│               │                    │                    │                       │
│               ▼                    ▼                    ▼                       │
│  ┌─────────────────────────────────────────────────────────────────────────┐   │
│  │                         Common Layer                                     │   │
│  │                                                                          │   │
│  │  ┌─────────────┐ ┌─────────────┐ ┌─────────────┐ ┌─────────────┐       │   │
│  │  │common-except│ │common-utils │ │ common-env  │ │ common-aws  │       │   │
│  │  │             │ │             │ │             │ │             │       │   │
│  │  │ Exceptions  │ │ JSON/Utils  │ │ Config      │ │ AWS Wrappers│       │   │
│  │  └─────────────┘ └─────────────┘ └─────────────┘ └──────┬──────┘       │   │
│  │                                                          │              │   │
│  └──────────────────────────────────────────────────────────┼──────────────┘   │
│                                                              │                  │
└──────────────────────────────────────────────────────────────┼──────────────────┘
                                                               │
                          ┌────────────────────────────────────┼───────────────┐
                          │                                    │               │
                          ▼                                    ▼               ▼
                   ┌────────────┐                       ┌────────────┐  ┌────────────┐
                   │  AWS S3    │                       │  AWS SQS   │  │  DynamoDB  │
                   │            │                       │            │  │            │
                   └────────────┘                       └────────────┘  └────────────┘
```

---

## Level 3: Component Diagrams

### 3.1 service-rest Components

| Component | Technology | Description | Interfaces |
|-----------|------------|-------------|------------|
| **Application** | Spring Boot | Main entry point | `main()` |
| **HealthController** | Spring MVC @RestController | Health check endpoint | `GET /api/health` |
| **SampleController** | Spring MVC @RestController | Sample CRUD endpoints | `GET /api/samples/{id}` |

**Component Relationships:**

| From | To | Description |
|------|-----|-------------|
| Spring MVC | HealthController | Routes /api/health |
| Spring MVC | SampleController | Routes /api/samples/* |
| Controllers | Jackson | JSON serialization |

**Component Diagram (service-rest):**

```
┌──────────────────────────────────────────────────────────────┐
│                      service-rest                             │
│                                                               │
│  ┌─────────────────────────────────────────────────────────┐ │
│  │                  Spring MVC Framework                    │ │
│  │  ┌───────────────────────┐                              │ │
│  │  │   DispatcherServlet   │                              │ │
│  │  └───────────┬───────────┘                              │ │
│  └──────────────┼──────────────────────────────────────────┘ │
│                 │                                             │
│       ┌─────────┴─────────┐                                  │
│       ▼                   ▼                                  │
│  ┌──────────────┐   ┌──────────────┐                        │
│  │HealthControl│   │SampleControl │                        │
│  │              │   │              │                        │
│  │ /api/health  │   │/api/samples/*│                        │
│  └──────────────┘   └──────────────┘                        │
│                                                               │
└──────────────────────────────────────────────────────────────┘
```

### 3.2 service-batch Components

| Component | Technology | Description | Interfaces |
|-----------|------------|-------------|------------|
| **BatchApplication** | Spring Boot | Main entry point | `main()` |
| **SampleJobConfig** | Spring Batch @Configuration | Job/step definitions | Job beans |
| **SampleTasklet** | Spring Batch Tasklet | Business logic executor | `execute()` |

**Component Relationships:**

| From | To | Description |
|------|-----|-------------|
| JobLauncher | SampleJobConfig | Retrieves job definition |
| sampleJob | sampleStep | Contains step |
| sampleStep | SampleTasklet | Executes tasklet |
| SampleTasklet | HSQLDB | Job metadata storage |

**Component Diagram (service-batch):**

```
┌──────────────────────────────────────────────────────────────┐
│                      service-batch                            │
│                                                               │
│  ┌─────────────────────────────────────────────────────────┐ │
│  │                 Spring Batch Framework                   │ │
│  │  ┌───────────────────────┐                              │ │
│  │  │     JobLauncher       │                              │ │
│  │  └───────────┬───────────┘                              │ │
│  └──────────────┼──────────────────────────────────────────┘ │
│                 │                                             │
│                 ▼                                             │
│  ┌──────────────────────────┐                                │
│  │    SampleJobConfig       │                                │
│  │    @Configuration        │                                │
│  │                          │                                │
│  │  ┌────────────────────┐  │                                │
│  │  │     sampleJob      │  │                                │
│  │  └─────────┬──────────┘  │                                │
│  │            │             │                                │
│  │            ▼             │                                │
│  │  ┌────────────────────┐  │                                │
│  │  │     sampleStep     │  │                                │
│  │  └─────────┬──────────┘  │                                │
│  └────────────┼─────────────┘                                │
│               │                                               │
│               ▼                                               │
│  ┌──────────────────────────┐                                │
│  │     SampleTasklet        │────────▶ HSQLDB               │
│  │     (execute logic)      │                                │
│  └──────────────────────────┘                                │
│                                                               │
└──────────────────────────────────────────────────────────────┘
```

### 3.3 service-soap Components

| Component | Technology | Description | Interfaces |
|-----------|------------|-------------|------------|
| **SoapApplication** | Spring Boot | Main entry point | `main()` |
| **WebServiceConfig** | Spring WS @Configuration | WS configuration | Bean definitions |
| **SampleEndpoint** | Spring WS @Endpoint | SOAP endpoint | `getSample()` |
| **GetSampleRequest** | JAXB POJO | Request model | Properties |
| **GetSampleResponse** | JAXB POJO | Response model | Properties |

**Component Relationships:**

| From | To | Description |
|------|-----|-------------|
| MessageDispatcherServlet | SampleEndpoint | Routes SOAP requests |
| SampleEndpoint | GetSampleRequest | Unmarshals input |
| SampleEndpoint | GetSampleResponse | Creates response |
| WebServiceConfig | WSDL Definition | Exposes WSDL |

**Component Diagram (service-soap):**

```
┌──────────────────────────────────────────────────────────────┐
│                      service-soap                             │
│                                                               │
│  ┌─────────────────────────────────────────────────────────┐ │
│  │              Spring Web Services Framework              │ │
│  │  ┌───────────────────────┐                              │ │
│  │  │MessageDispatcherServlet│                              │ │
│  │  └───────────┬───────────┘                              │ │
│  └──────────────┼──────────────────────────────────────────┘ │
│                 │                                             │
│                 ▼                                             │
│  ┌──────────────────────────┐                                │
│  │     WebServiceConfig     │───────▶ WSDL Definition       │
│  │     @Configuration       │                                │
│  └──────────────────────────┘                                │
│                                                               │
│  ┌──────────────────────────┐                                │
│  │     SampleEndpoint       │                                │
│  │     @Endpoint            │                                │
│  │     @PayloadRoot         │                                │
│  └─────────────┬────────────┘                                │
│                │                                              │
│       ┌────────┴────────┐                                    │
│       ▼                 ▼                                    │
│  ┌──────────────┐ ┌──────────────┐                          │
│  │GetSampleReq  │ │GetSampleResp │                          │
│  │   (POJO)     │ │   (POJO)     │                          │
│  └──────────────┘ └──────────────┘                          │
│                                                               │
└──────────────────────────────────────────────────────────────┘
```

### 3.4 common-aws Components

| Component | Technology | Description | Interfaces |
|-----------|------------|-------------|------------|
| **S3ClientWrapper** | AWS SDK v2 | S3 operations wrapper | `put`, `get`, `delete`, `exists` |
| **SqsClientWrapper** | AWS SDK v2 | SQS operations wrapper | `send`, `receive`, `delete` |
| **DynamoDbClientWrapper** | AWS SDK v2 | DynamoDB wrapper | `put`, `get`, `delete`, `exists` |

**Component Diagram (common-aws):**

```
┌──────────────────────────────────────────────────────────────┐
│                      common-aws                               │
│                                                               │
│  ┌──────────────────┐  ┌──────────────────┐  ┌─────────────┐│
│  │ S3ClientWrapper  │  │ SqsClientWrapper │  │DynamoDbClient││
│  │                  │  │                  │  │  Wrapper    ││
│  │ - putObject()    │  │ - sendMessage()  │  │ - putItem() ││
│  │ - getObject()    │  │ - receiveMessage()│ │ - getItem() ││
│  │ - deleteObject() │  │ - deleteMessage()│  │ - deleteItem()│
│  │ - doesObjectExist│  │                  │  │ - itemExists()│
│  └────────┬─────────┘  └────────┬─────────┘  └──────┬──────┘│
│           │                     │                   │        │
│           │                     │                   │        │
└───────────┼─────────────────────┼───────────────────┼────────┘
            │                     │                   │
            ▼                     ▼                   ▼
     ┌────────────┐        ┌────────────┐      ┌────────────┐
     │  AWS S3    │        │  AWS SQS   │      │  DynamoDB  │
     │  (Cloud)   │        │  (Cloud)   │      │  (Cloud)   │
     └────────────┘        └────────────┘      └────────────┘
```

---

## Summary

| Level | Scope | Key Elements |
|-------|-------|--------------|
| **Level 1** | Context | System, Users, External Systems |
| **Level 2** | Container | 7 internal containers, 4 AWS services |
| **Level 3** | Component | Controllers, Jobs, Endpoints, Wrappers |
