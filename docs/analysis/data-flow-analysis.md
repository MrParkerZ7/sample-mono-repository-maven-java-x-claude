# Data Flow Analysis: Sample Mono Repository (Maven Java)

## 1. Overview

This document describes data flow patterns across the three services (REST, Batch, SOAP) and their interactions with AWS resources.

---

## 2. User Input Flows

### 2.1 REST API Health Check Flow

| Aspect | Details |
|--------|---------|
| **Flow Name** | Health Check Request |
| **Trigger** | HTTP GET /api/health |
| **Input** | None |
| **Output** | `{"status":"UP"}` |

**Flow Steps:**

```
Client
   │
   │ HTTP GET /api/health
   ▼
┌──────────────────────┐
│   HealthController   │
│  healthCheck()       │
└──────────────────────┘
   │
   │ Return JSON
   ▼
Client receives: {"status":"UP"}
```

### 2.2 REST API Sample Retrieval Flow

| Aspect | Details |
|--------|---------|
| **Flow Name** | Get Sample by ID |
| **Trigger** | HTTP GET /api/samples/{id} |
| **Input** | Path parameter: id (String) |
| **Output** | `{"id":"...","name":"Sample ...","active":true}` |

**Flow Steps:**

```
Client
   │
   │ HTTP GET /api/samples/123
   ▼
┌──────────────────────┐
│   SampleController   │
│   getSample(id)      │
└──────────────────────┘
   │
   │ Build response object
   │
   ▼
┌──────────────────────┐
│  Response DTO        │
│  {id, name, active}  │
└──────────────────────┘
   │
   │ JSON serialization
   ▼
Client receives: {"id":"123","name":"Sample 123","active":true}
```

---

## 3. API Request Flows

### 3.1 REST Service Request Flow

```
┌─────────┐     HTTP/REST      ┌───────────────────┐
│ Client  │ ─────────────────▶ │  Spring Web MVC   │
└─────────┘                    └─────────────────┬─┘
                                                 │
                                                 ▼
                               ┌───────────────────┐
                               │   Controller      │
                               │ @RestController   │
                               └─────────────────┬─┘
                                                 │
                                    ┌────────────┴────────────┐
                                    │                         │
                                    ▼                         ▼
                          ┌─────────────────┐       ┌─────────────────┐
                          │ HealthController│       │ SampleController│
                          │ /api/health     │       │ /api/samples/*  │
                          └─────────────────┘       └─────────────────┘
```

### 3.2 SOAP Service Request Flow

```
┌─────────┐     HTTP/SOAP      ┌───────────────────┐
│ Client  │ ─────────────────▶ │  Spring WS        │
└─────────┘                    └─────────────────┬─┘
                                                 │
                                                 ▼
                               ┌───────────────────┐
                               │  MessageDispatcher │
                               │  Servlet          │
                               └─────────────────┬─┘
                                                 │
                                                 ▼
                               ┌───────────────────┐
                               │  SampleEndpoint   │
                               │  @Endpoint        │
                               └─────────────────┬─┘
                                                 │
                        ┌────────────────────────┴─────────────────────┐
                        │                                              │
                        ▼                                              ▼
              ┌─────────────────────┐                    ┌─────────────────────┐
              │  GetSampleRequest   │                    │  GetSampleResponse  │
              │  (unmarshal XML)    │                    │  (marshal XML)      │
              └─────────────────────┘                    └─────────────────────┘
```

**SOAP Endpoint Details:**

| Aspect | Value |
|--------|-------|
| Namespace | `http://example.com/sample` |
| Local Part | `getSampleRequest` |
| WSDL Location | `/ws/sample.wsdl` |

---

## 4. Background Processing Flows

### 4.1 Batch Job Execution Flow

| Aspect | Details |
|--------|---------|
| **Job Name** | sampleJob |
| **Trigger** | Manual or scheduled |
| **Database** | HSQLDB (in-memory) |
| **Steps** | sampleStep (Tasklet) |

**Batch Flow:**

```
┌───────────────────┐
│   Job Launcher    │
│ (Spring Batch)    │
└─────────┬─────────┘
          │
          │ Launch sampleJob
          ▼
┌───────────────────┐
│   SampleJobConfig │
│   @Configuration  │
└─────────┬─────────┘
          │
          │ Execute sampleStep
          ▼
┌───────────────────┐
│   SampleTasklet   │
│   execute()       │
└─────────┬─────────┘
          │
          │ Business logic
          ▼
┌───────────────────┐
│  HSQLDB Database  │
│  (Job metadata)   │
└───────────────────┘
```

**Spring Batch Components:**

| Component | Purpose |
|-----------|---------|
| `JobLauncher` | Triggers job execution |
| `JobRepository` | Stores job metadata (HSQLDB) |
| `StepBuilderFactory` | Creates step definitions |
| `SampleTasklet` | Executes business logic |

---

## 5. AWS Integration Flows

### 5.1 S3 Operations Flow

```
┌───────────────────┐
│  Application      │
│  (Service Layer)  │
└─────────┬─────────┘
          │
          │ putObject/getObject/deleteObject
          ▼
┌───────────────────┐
│ S3ClientWrapper   │
│ (common-aws)      │
└─────────┬─────────┘
          │
          │ AWS SDK v2
          ▼
┌───────────────────┐      ┌───────────────────┐
│   S3Client        │─────▶│   AWS S3          │
│   (SDK v2)        │      │   (Cloud)         │
└───────────────────┘      └───────────────────┘
```

**S3 Operations:**

| Operation | Method | Description |
|-----------|--------|-------------|
| Put | `putObject(bucket, key, data)` | Upload object |
| Get | `getObject(bucket, key)` | Download object |
| Delete | `deleteObject(bucket, key)` | Remove object |
| Exists | `doesObjectExist(bucket, key)` | Check existence |

### 5.2 SQS Message Flow

```
┌───────────────────┐
│  Producer         │
│  (Application)    │
└─────────┬─────────┘
          │
          │ sendMessage
          ▼
┌───────────────────┐
│ SqsClientWrapper  │
│ (common-aws)      │
└─────────┬─────────┘
          │
          │ AWS SDK v2
          ▼
┌───────────────────┐      ┌───────────────────┐
│   SqsClient       │─────▶│   AWS SQS         │
│   (SDK v2)        │      │   (Queue)         │
└───────────────────┘      └───────────────────┘
          │
          │ receiveMessage
          ▼
┌───────────────────┐
│  Consumer         │
│  (Application)    │
└───────────────────┘
```

### 5.3 DynamoDB Operations Flow

```
┌───────────────────┐
│  Application      │
│  (Service Layer)  │
└─────────┬─────────┘
          │
          │ put/get/delete
          ▼
┌───────────────────────┐
│ DynamoDbClientWrapper │
│ (common-aws)          │
└─────────┬─────────────┘
          │
          │ AWS SDK v2
          ▼
┌───────────────────┐      ┌───────────────────┐
│  DynamoDbClient   │─────▶│   AWS DynamoDB    │
│   (SDK v2)        │      │   (Table)         │
└───────────────────┘      └───────────────────┘
```

**DynamoDB Operations:**

| Operation | Method | Description |
|-----------|--------|-------------|
| Put | `putItem(table, item)` | Create/update item |
| Get | `getItem(table, key)` | Retrieve item |
| Delete | `deleteItem(table, key)` | Remove item |
| Exists | `itemExists(table, key)` | Check existence |

---

## 6. Data Transformation Points

### 6.1 JSON Serialization (common-utils)

```
┌──────────────────┐
│   Java Object    │
└────────┬─────────┘
         │
         │ JsonUtils.toJson()
         ▼
┌──────────────────┐
│   JSON String    │
└──────────────────┘
         │
         │ JsonUtils.fromJson()
         ▼
┌──────────────────┐
│   Java Object    │
└──────────────────┘
```

**Jackson Configuration:**
- Module: `JavaTimeModule` (java.time support)
- Date format: ISO-8601

### 6.2 SOAP XML Marshalling

```
┌──────────────────────┐
│  GetSampleRequest    │
│  (Java POJO)         │
└────────┬─────────────┘
         │
         │ JAXB Unmarshalling
         ▲
┌────────┴─────────────┐
│  SOAP XML Envelope   │
│  (Inbound)           │
└──────────────────────┘

┌──────────────────────┐
│  GetSampleResponse   │
│  (Java POJO)         │
└────────┬─────────────┘
         │
         │ JAXB Marshalling
         ▼
┌──────────────────────┐
│  SOAP XML Envelope   │
│  (Outbound)          │
└──────────────────────┘
```

---

## 7. Error Handling Flow

### Exception Propagation

```
┌───────────────────────┐
│   AWS SDK Error       │
│   (S3Exception, etc.) │
└─────────┬─────────────┘
          │
          │ catch
          ▼
┌───────────────────────┐
│  Wrapper Class        │
│  (S3ClientWrapper)    │
└─────────┬─────────────┘
          │
          │ wrap as TechnicalException
          ▼
┌───────────────────────┐
│  TechnicalException   │
│  errorCode: S3_*      │
└─────────┬─────────────┘
          │
          │ propagate
          ▼
┌───────────────────────┐
│  Service Layer        │
│  (error handling)     │
└───────────────────────┘
```

**Error Code Mapping:**

| Source | Error Code |
|--------|------------|
| S3 Put | `S3_PUT_ERROR` |
| S3 Get | `S3_GET_ERROR` |
| S3 Delete | `S3_DELETE_ERROR` |
| S3 Head | `S3_HEAD_ERROR` |
| DynamoDB Put | `DYNAMODB_PUT_ERROR` |
| DynamoDB Get | `DYNAMODB_GET_ERROR` |
| DynamoDB Delete | `DYNAMODB_DELETE_ERROR` |

---

## 8. Data Flow Summary

### Flow Types by Service

| Service | Input | Processing | Output | Storage |
|---------|-------|------------|--------|---------|
| REST | HTTP Request | Controller | JSON Response | None |
| Batch | Job Launch | Tasklet | Completion Status | HSQLDB |
| SOAP | XML Request | Endpoint | XML Response | None |

### AWS Integration Summary

| Service | S3 | SQS | DynamoDB |
|---------|:--:|:---:|:--------:|
| REST | Available | Available | Available |
| Batch | Available | Available | Available |
| SOAP | Available | Available | Available |

All services can utilize AWS integrations via the `common-aws` module.
