# API Contract Analysis: Sample Mono Repository (Maven Java)

## 1. API Overview

| Attribute | Value |
|-----------|-------|
| **API Types** | REST, SOAP |
| **REST Base URL** | `http://localhost:8080/api` |
| **SOAP Base URL** | `http://localhost:8081/ws` |
| **Version Strategy** | None (sample project) |
| **Documentation** | WSDL (SOAP), OpenAPI (REST - not implemented) |

---

## 2. REST API Contract

### 2.1 Overview

| Attribute | Value |
|-----------|-------|
| **Service** | service-rest |
| **Port** | 8080 |
| **Base Path** | `/api` |
| **Content-Type** | `application/json` |

### 2.2 Endpoints

#### GET /api/health

| Aspect | Details |
|--------|---------|
| **Summary** | Health check endpoint |
| **Auth** | None |
| **Request Body** | None |
| **Success Response** | 200 OK |

**Response Schema:**

```json
{
  "status": "string"
}
```

**Example Response:**

```json
{
  "status": "UP"
}
```

**Response Codes:**

| Code | Description |
|------|-------------|
| 200 | Service is healthy |
| 500 | Internal server error |

---

#### GET /api/samples/{id}

| Aspect | Details |
|--------|---------|
| **Summary** | Get sample by ID |
| **Auth** | None |
| **Path Parameters** | `id` (String, required) |
| **Request Body** | None |
| **Success Response** | 200 OK |

**Path Parameters:**

| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| `id` | String | Yes | Sample identifier |

**Response Schema:**

```json
{
  "id": "string",
  "name": "string",
  "active": "boolean"
}
```

**Example Response:**

```json
{
  "id": "123",
  "name": "Sample 123",
  "active": true
}
```

**Response Codes:**

| Code | Description |
|------|-------------|
| 200 | Sample found |
| 404 | Sample not found |
| 500 | Internal server error |

---

### 2.3 REST API Summary Table

| Method | Path | Summary | Auth | Request | Response |
|--------|------|---------|------|---------|----------|
| GET | /api/health | Health check | None | - | `{"status":"UP"}` |
| GET | /api/samples/{id} | Get sample | None | Path: id | Sample object |

---

## 3. SOAP API Contract

### 3.1 Overview

| Attribute | Value |
|-----------|-------|
| **Service** | service-soap |
| **Port** | 8081 |
| **Endpoint** | `/ws` |
| **WSDL** | `/ws/sample.wsdl` |
| **Namespace** | `http://example.com/sample` |
| **Content-Type** | `text/xml` |

### 3.2 Operations

#### getSampleRequest

| Aspect | Details |
|--------|---------|
| **Summary** | Get sample by ID |
| **Namespace** | `http://example.com/sample` |
| **Local Part** | `getSampleRequest` |
| **Request Element** | `GetSampleRequest` |
| **Response Element** | `GetSampleResponse` |

**Request Schema:**

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `id` | string | Yes | Sample identifier |

**Request Example:**

```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:sam="http://example.com/sample">
   <soapenv:Header/>
   <soapenv:Body>
      <sam:getSampleRequest>
         <sam:id>123</sam:id>
      </sam:getSampleRequest>
   </soapenv:Body>
</soapenv:Envelope>
```

**Response Schema:**

| Field | Type | Description |
|-------|------|-------------|
| `id` | string | Sample identifier |
| `name` | string | Sample name |
| `active` | boolean | Active status |

**Response Example:**

```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/">
   <soapenv:Header/>
   <soapenv:Body>
      <sam:getSampleResponse xmlns:sam="http://example.com/sample">
         <sam:id>123</sam:id>
         <sam:name>Sample 123</sam:name>
         <sam:active>true</sam:active>
      </sam:getSampleResponse>
   </soapenv:Body>
</soapenv:Envelope>
```

---

### 3.3 SOAP Models

#### GetSampleRequest

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `id` | String | Yes | Sample identifier to retrieve |

**Java Class:** `com.example.service.soap.model.GetSampleRequest`

```java
public class GetSampleRequest {
    private String id;
    // getters and setters
}
```

#### GetSampleResponse

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `id` | String | Yes | Sample identifier |
| `name` | String | Yes | Sample name |
| `active` | boolean | Yes | Active status |

**Java Class:** `com.example.service.soap.model.GetSampleResponse`

```java
public class GetSampleResponse {
    private String id;
    private String name;
    private boolean active;
    // getters and setters
}
```

---

### 3.4 WSDL Location

| Type | URL |
|------|-----|
| **WSDL** | `http://localhost:8081/ws/sample.wsdl` |
| **Schema** | Embedded in WSDL |

---

## 4. Common Data Models

### 4.1 Sample Object

Used by both REST and SOAP services:

| Field | JSON Type | XML Type | Description |
|-------|-----------|----------|-------------|
| `id` | string | xs:string | Unique identifier |
| `name` | string | xs:string | Display name |
| `active` | boolean | xs:boolean | Active status |

### 4.2 Health Status

REST-only model:

| Field | JSON Type | Description |
|-------|-----------|-------------|
| `status` | string | Health status ("UP" or "DOWN") |

---

## 5. Error Handling

### 5.1 REST Error Responses

| HTTP Code | Scenario | Response Body |
|-----------|----------|---------------|
| 400 | Bad Request | `{"error": "Bad Request"}` |
| 404 | Not Found | `{"error": "Not Found"}` |
| 500 | Server Error | `{"error": "Internal Server Error"}` |

### 5.2 SOAP Fault

```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/">
   <soapenv:Body>
      <soapenv:Fault>
         <faultcode>soapenv:Server</faultcode>
         <faultstring>Error message</faultstring>
      </soapenv:Fault>
   </soapenv:Body>
</soapenv:Envelope>
```

---

## 6. API Client Configuration

### 6.1 REST Client (cURL)

```bash
# Health check
curl -X GET http://localhost:8080/api/health

# Get sample
curl -X GET http://localhost:8080/api/samples/123
```

### 6.2 SOAP Client (cURL)

```bash
curl -X POST http://localhost:8081/ws \
  -H "Content-Type: text/xml" \
  -d '<?xml version="1.0"?>
      <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                        xmlns:sam="http://example.com/sample">
         <soapenv:Header/>
         <soapenv:Body>
            <sam:getSampleRequest>
               <sam:id>123</sam:id>
            </sam:getSampleRequest>
         </soapenv:Body>
      </soapenv:Envelope>'
```

---

## 7. API Summary

| Service | Type | Port | Endpoints | Authentication |
|---------|------|------|-----------|----------------|
| service-rest | REST | 8080 | 2 | None |
| service-soap | SOAP | 8081 | 1 + WSDL | None |

### Endpoint Count by Service

| Service | GET | POST | PUT | DELETE | SOAP |
|---------|-----|------|-----|--------|------|
| service-rest | 2 | 0 | 0 | 0 | - |
| service-soap | 1 (WSDL) | - | - | - | 1 |
