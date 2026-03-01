# Sequence Diagram Analysis: Sample Mono Repository (Maven Java)

## 1. Overview

This document describes interaction sequences between components for the three services (REST, Batch, SOAP).

---

## 2. REST API Sequences

### 2.1 Health Check Sequence

| Aspect | Details |
|--------|---------|
| **Sequence Name** | REST Health Check |
| **Trigger** | HTTP GET /api/health |
| **Participants** | Client, Spring MVC, HealthController |

**Message Sequence:**

| # | From | To | Message | Return |
|---|------|-----|---------|--------|
| 1 | Client | DispatcherServlet | GET /api/health | - |
| 2 | DispatcherServlet | HandlerMapping | getHandler() | HealthController |
| 3 | DispatcherServlet | HealthController | healthCheck() | Map |
| 4 | HealthController | - | (build response) | `{"status":"UP"}` |
| 5 | DispatcherServlet | Client | HTTP 200 | JSON body |

**Sequence Diagram (ASCII):**

```
Client          DispatcherServlet     HandlerMapping     HealthController
  │                    │                    │                   │
  │ GET /api/health    │                    │                   │
  │───────────────────▶│                    │                   │
  │                    │   getHandler()     │                   │
  │                    │───────────────────▶│                   │
  │                    │   handler          │                   │
  │                    │◀───────────────────│                   │
  │                    │                    │   healthCheck()   │
  │                    │───────────────────────────────────────▶│
  │                    │                    │   {"status":"UP"} │
  │                    │◀───────────────────────────────────────│
  │   HTTP 200 + JSON  │                    │                   │
  │◀───────────────────│                    │                   │
  │                    │                    │                   │
```

### 2.2 Get Sample Sequence

| Aspect | Details |
|--------|---------|
| **Sequence Name** | Get Sample by ID |
| **Trigger** | HTTP GET /api/samples/{id} |
| **Participants** | Client, Spring MVC, SampleController |

**Message Sequence:**

| # | From | To | Message | Return |
|---|------|-----|---------|--------|
| 1 | Client | DispatcherServlet | GET /api/samples/123 | - |
| 2 | DispatcherServlet | HandlerMapping | getHandler() | SampleController |
| 3 | DispatcherServlet | SampleController | getSample("123") | Map |
| 4 | SampleController | - | (build response) | `{id, name, active}` |
| 5 | DispatcherServlet | Jackson | serialize(Map) | JSON string |
| 6 | DispatcherServlet | Client | HTTP 200 | JSON body |

**Sequence Diagram (ASCII):**

```
Client          DispatcherServlet     SampleController       Jackson
  │                    │                    │                   │
  │ GET /api/samples/123                    │                   │
  │───────────────────▶│                    │                   │
  │                    │  getSample("123")  │                   │
  │                    │───────────────────▶│                   │
  │                    │                    │                   │
  │                    │   Map{id,name,active}                  │
  │                    │◀───────────────────│                   │
  │                    │                    │  serialize(Map)   │
  │                    │───────────────────────────────────────▶│
  │                    │                    │  JSON string      │
  │                    │◀───────────────────────────────────────│
  │   HTTP 200 + JSON  │                    │                   │
  │◀───────────────────│                    │                   │
  │                    │                    │                   │
```

---

## 3. SOAP Service Sequences

### 3.1 GetSample SOAP Request Sequence

| Aspect | Details |
|--------|---------|
| **Sequence Name** | SOAP GetSample Request |
| **Trigger** | HTTP POST /ws with SOAP envelope |
| **Participants** | Client, MessageDispatcherServlet, SampleEndpoint |

**Message Sequence:**

| # | From | To | Message | Return |
|---|------|-----|---------|--------|
| 1 | Client | MessageDispatcherServlet | POST /ws (SOAP XML) | - |
| 2 | MessageDispatcherServlet | PayloadRootMapping | resolve(@PayloadRoot) | SampleEndpoint |
| 3 | MessageDispatcherServlet | JAXB Unmarshaller | unmarshal(XML) | GetSampleRequest |
| 4 | MessageDispatcherServlet | SampleEndpoint | getSample(request) | GetSampleResponse |
| 5 | SampleEndpoint | GetSampleResponse | new() | response |
| 6 | SampleEndpoint | response | setId/setName/setActive | - |
| 7 | MessageDispatcherServlet | JAXB Marshaller | marshal(response) | XML |
| 8 | MessageDispatcherServlet | Client | HTTP 200 | SOAP response |

**Sequence Diagram (ASCII):**

```
Client       MessageDispatcher    PayloadRoot    JAXB         SampleEndpoint
  │                 │                │            │                │
  │  POST /ws       │                │            │                │
  │  SOAP Envelope  │                │            │                │
  │────────────────▶│                │            │                │
  │                 │  resolve()     │            │                │
  │                 │───────────────▶│            │                │
  │                 │  endpoint      │            │                │
  │                 │◀───────────────│            │                │
  │                 │                │ unmarshal()│                │
  │                 │───────────────────────────▶│                │
  │                 │                │ Request    │                │
  │                 │◀───────────────────────────│                │
  │                 │                │            │  getSample()   │
  │                 │───────────────────────────────────────────▶│
  │                 │                │            │  Response      │
  │                 │◀───────────────────────────────────────────│
  │                 │                │  marshal() │                │
  │                 │───────────────────────────▶│                │
  │                 │                │  XML       │                │
  │                 │◀───────────────────────────│                │
  │  SOAP Response  │                │            │                │
  │◀────────────────│                │            │                │
```

### 3.2 WSDL Retrieval Sequence

| Aspect | Details |
|--------|---------|
| **Sequence Name** | WSDL Document Request |
| **Trigger** | HTTP GET /ws/sample.wsdl |
| **Participants** | Client, MessageDispatcherServlet, WsdlDefinition |

**Message Sequence:**

| # | From | To | Message | Return |
|---|------|-----|---------|--------|
| 1 | Client | MessageDispatcherServlet | GET /ws/sample.wsdl | - |
| 2 | MessageDispatcherServlet | WsdlDefinition | getSource() | WSDL XML |
| 3 | MessageDispatcherServlet | Client | HTTP 200 | WSDL document |

---

## 4. Batch Service Sequences

### 4.1 Batch Job Execution Sequence

| Aspect | Details |
|--------|---------|
| **Sequence Name** | Sample Batch Job Execution |
| **Trigger** | JobLauncher.run() or scheduled |
| **Participants** | JobLauncher, JobRepository, Job, Step, Tasklet |

**Message Sequence:**

| # | From | To | Message | Return |
|---|------|-----|---------|--------|
| 1 | Trigger | JobLauncher | run(sampleJob, params) | - |
| 2 | JobLauncher | JobRepository | createJobExecution() | JobExecution |
| 3 | JobLauncher | sampleJob | execute() | - |
| 4 | sampleJob | sampleStep | execute() | - |
| 5 | sampleStep | SampleTasklet | execute(contribution, context) | RepeatStatus |
| 6 | SampleTasklet | - | (business logic) | FINISHED |
| 7 | sampleStep | JobRepository | updateStepExecution() | - |
| 8 | sampleJob | JobRepository | updateJobExecution() | - |
| 9 | JobLauncher | Trigger | JobExecution | status |

**Sequence Diagram (ASCII):**

```
Trigger       JobLauncher      JobRepository       sampleJob        sampleStep     SampleTasklet
   │               │                │                  │                │               │
   │  run(job)     │                │                  │                │               │
   │──────────────▶│                │                  │                │               │
   │               │ createExecution│                  │                │               │
   │               │───────────────▶│                  │                │               │
   │               │ JobExecution   │                  │                │               │
   │               │◀───────────────│                  │                │               │
   │               │                │   execute()      │                │               │
   │               │───────────────────────────────────▶                │               │
   │               │                │                  │   execute()    │               │
   │               │                │                  │───────────────▶│               │
   │               │                │                  │                │  execute()    │
   │               │                │                  │                │──────────────▶│
   │               │                │                  │                │  FINISHED     │
   │               │                │                  │                │◀──────────────│
   │               │                │                  │   COMPLETED    │               │
   │               │                │                  │◀───────────────│               │
   │               │                │                  │  update()      │               │
   │               │                │◀──────────────────────────────────│               │
   │               │                │   COMPLETED      │                │               │
   │               │◀───────────────────────────────────                │               │
   │ JobExecution  │                │                  │                │               │
   │◀──────────────│                │                  │                │               │
```

---

## 5. AWS Integration Sequences

### 5.1 S3 PutObject Sequence

| Aspect | Details |
|--------|---------|
| **Sequence Name** | S3 Object Upload |
| **Trigger** | Application code |
| **Participants** | Service, S3ClientWrapper, S3Client, AWS S3 |

**Message Sequence:**

| # | From | To | Message | Return |
|---|------|-----|---------|--------|
| 1 | Service | S3ClientWrapper | putObject(bucket, key, data) | - |
| 2 | S3ClientWrapper | S3Client | putObject(request, body) | PutObjectResponse |
| 3 | S3Client | AWS S3 | HTTP PUT | 200 OK |
| 4 | S3ClientWrapper | Service | void (success) | - |

**Error Sequence:**

| # | From | To | Message | Return |
|---|------|-----|---------|--------|
| 1 | Service | S3ClientWrapper | putObject(bucket, key, data) | - |
| 2 | S3ClientWrapper | S3Client | putObject(request, body) | - |
| 3 | S3Client | - | throw S3Exception | - |
| 4 | S3ClientWrapper | - | catch S3Exception | - |
| 5 | S3ClientWrapper | Service | throw TechnicalException("S3_PUT_ERROR") | - |

### 5.2 DynamoDB GetItem Sequence

| Aspect | Details |
|--------|---------|
| **Sequence Name** | DynamoDB Item Retrieval |
| **Trigger** | Application code |
| **Participants** | Service, DynamoDbClientWrapper, DynamoDbClient, AWS DynamoDB |

**Message Sequence:**

| # | From | To | Message | Return |
|---|------|-----|---------|--------|
| 1 | Service | DynamoDbClientWrapper | getItem(table, keyMap) | Map |
| 2 | DynamoDbClientWrapper | DynamoDbClient | getItem(request) | GetItemResponse |
| 3 | DynamoDbClient | AWS DynamoDB | HTTP POST | JSON response |
| 4 | DynamoDbClientWrapper | - | extract attributes | Map |
| 5 | DynamoDbClientWrapper | Service | attributeMap | Map |

### 5.3 SQS SendMessage Sequence

| Aspect | Details |
|--------|---------|
| **Sequence Name** | SQS Message Send |
| **Trigger** | Application code |
| **Participants** | Service, SqsClientWrapper, SqsClient, AWS SQS |

**Message Sequence:**

| # | From | To | Message | Return |
|---|------|-----|---------|--------|
| 1 | Service | SqsClientWrapper | sendMessage(queueUrl, body) | - |
| 2 | SqsClientWrapper | SqsClient | sendMessage(request) | SendMessageResponse |
| 3 | SqsClient | AWS SQS | HTTP POST | message ID |
| 4 | SqsClientWrapper | Service | messageId | String |

---

## 6. Cross-Service Sequence Patterns

### 6.1 Request Processing Pattern

All services follow a consistent pattern:

```
┌────────────┐     ┌────────────┐     ┌────────────┐     ┌────────────┐
│   Client   │────▶│ Dispatcher │────▶│  Handler   │────▶│  Response  │
└────────────┘     └────────────┘     └────────────┘     └────────────┘
     │                   │                  │                  │
     │     Request       │                  │                  │
     │──────────────────▶│                  │                  │
     │                   │    Route         │                  │
     │                   │─────────────────▶│                  │
     │                   │                  │    Process       │
     │                   │                  │─────────────────▶│
     │                   │                  │    Result        │
     │                   │                  │◀─────────────────│
     │                   │    Result        │                  │
     │                   │◀─────────────────│                  │
     │     Response      │                  │                  │
     │◀──────────────────│                  │                  │
```

### 6.2 Exception Handling Pattern

```
┌────────────┐     ┌────────────┐     ┌────────────┐
│   Caller   │────▶│  Wrapper   │────▶│  AWS SDK   │
└────────────┘     └────────────┘     └────────────┘
     │                   │                  │
     │     invoke()      │                  │
     │──────────────────▶│                  │
     │                   │     call()       │
     │                   │─────────────────▶│
     │                   │  AwsException    │
     │                   │◀─────────────────│
     │                   │                  │
     │                   │ (catch, wrap)    │
     │                   │◀─────────────────│
     │ TechnicalException│                  │
     │◀──────────────────│                  │
```

---

## 7. Sequence Diagram Summary

| Diagram | Service | Key Participants | Main Flow |
|---------|---------|------------------|-----------|
| Health Check | REST | Client, Controller | Synchronous |
| Get Sample | REST | Client, Controller, Jackson | Synchronous |
| SOAP Request | SOAP | Client, Endpoint, JAXB | Synchronous |
| Batch Job | Batch | Launcher, Job, Tasklet | Asynchronous |
| S3 Upload | Common | Wrapper, SDK, AWS | Synchronous |
| DynamoDB Get | Common | Wrapper, SDK, AWS | Synchronous |
| SQS Send | Common | Wrapper, SDK, AWS | Asynchronous |
