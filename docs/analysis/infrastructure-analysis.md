# Infrastructure Analysis: Sample Mono Repository (Maven Java)

## 1. Cloud Provider

| Attribute | Value |
|-----------|-------|
| **Primary Cloud** | Amazon Web Services (AWS) |
| **IaC Tool** | AWS CDK (Java) |
| **CDK Version** | 2.130.0 |
| **Constructs Version** | 10.3.0 |
| **Deployment Model** | CloudFormation stacks |

---

## 2. Compute Resources

### 2.1 Application Services

The services are designed as Spring Boot applications packaged as executable JARs:

| Service | Type | Port | Deployment Target |
|---------|------|------|-------------------|
| service-rest | Spring Boot JAR | 8080 | Container/EC2/Lambda |
| service-batch | Spring Boot JAR | - | Container/EC2/Lambda |
| service-soap | Spring Boot JAR | 8081 | Container/EC2/Lambda |

### 2.2 CDK Compute Constructs

| Construct | Purpose | Configuration |
|-----------|---------|---------------|
| RestApiConstruct | API Gateway for REST | Exposes port 8080 |
| SoapApiConstruct | API Gateway for SOAP | Exposes port 8081 |

---

## 3. Networking

### 3.1 VPC Configuration (NetworkConstruct)

| Attribute | Value |
|-----------|-------|
| **CIDR Block** | 10.0.0.0/16 |
| **Max AZs** | 2 |
| **NAT Gateways** | 1 (default) |

### 3.2 Subnet Configuration

| Subnet Type | CIDR Mask | Purpose | Egress |
|-------------|-----------|---------|--------|
| **Public** | /24 | Internet-facing resources | Full |
| **Private with Egress** | /24 | Application workloads | NAT Gateway |
| **Isolated** | /24 | Databases, sensitive | None |

### 3.3 Network Diagram

```
┌─────────────────────────────────────────────────────────────┐
│                      VPC (10.0.0.0/16)                      │
│  ┌─────────────────────┐    ┌─────────────────────┐        │
│  │   Availability Zone 1│    │   Availability Zone 2│        │
│  │                      │    │                      │        │
│  │  ┌────────────────┐ │    │  ┌────────────────┐ │        │
│  │  │ Public Subnet  │ │    │  │ Public Subnet  │ │        │
│  │  │   10.0.x.0/24  │ │    │  │   10.0.x.0/24  │ │        │
│  │  │  ┌──────────┐  │ │    │  │  ┌──────────┐  │ │        │
│  │  │  │   NAT    │  │ │    │  │  │   IGW    │  │ │        │
│  │  │  └──────────┘  │ │    │  │  └──────────┘  │ │        │
│  │  └────────────────┘ │    │  └────────────────┘ │        │
│  │                      │    │                      │        │
│  │  ┌────────────────┐ │    │  ┌────────────────┐ │        │
│  │  │ Private Subnet │ │    │  │ Private Subnet │ │        │
│  │  │   10.0.x.0/24  │ │    │  │   10.0.x.0/24  │ │        │
│  │  │  (with Egress) │ │    │  │  (with Egress) │ │        │
│  │  └────────────────┘ │    │  └────────────────┘ │        │
│  │                      │    │                      │        │
│  │  ┌────────────────┐ │    │  ┌────────────────┐ │        │
│  │  │Isolated Subnet │ │    │  │Isolated Subnet │ │        │
│  │  │   10.0.x.0/24  │ │    │  │   10.0.x.0/24  │ │        │
│  │  │  (no egress)   │ │    │  │  (no egress)   │ │        │
│  │  └────────────────┘ │    │  └────────────────┘ │        │
│  │                      │    │                      │        │
│  └─────────────────────┘    └─────────────────────┘        │
└─────────────────────────────────────────────────────────────┘
```

---

## 4. Data Storage

### 4.1 Storage Resources (StorageConstruct)

| Resource | Type | Configuration |
|----------|------|---------------|
| **S3 Bucket** | Object Storage | S3-managed encryption, private |
| **SQS Queue** | Message Queue | Standard queue |
| **DynamoDB Table** | NoSQL Database | PAY_PER_REQUEST billing |

### 4.2 S3 Bucket Configuration

| Attribute | Value |
|-----------|-------|
| **Name** | SampleBucket |
| **Encryption** | S3_MANAGED |
| **Block Public Access** | All blocked |
| **Auto Delete Objects** | true (dev) |
| **Removal Policy** | DESTROY |

### 4.3 SQS Queue Configuration

| Attribute | Value |
|-----------|-------|
| **Name** | SampleQueue |
| **Type** | Standard |
| **Visibility Timeout** | 30 seconds (default) |
| **Retention** | 4 days (default) |

### 4.4 DynamoDB Table Configuration

| Attribute | Value |
|-----------|-------|
| **Name** | SampleTable |
| **Partition Key** | id (STRING) |
| **Billing Mode** | PAY_PER_REQUEST |
| **Removal Policy** | DESTROY |

---

## 5. Security

### 5.1 S3 Security

| Control | Setting |
|---------|---------|
| Block Public ACLs | true |
| Block Public Policy | true |
| Ignore Public ACLs | true |
| Restrict Public Buckets | true |
| Encryption | S3-managed |

### 5.2 VPC Security

| Component | Configuration |
|-----------|---------------|
| Public Subnet | Internet Gateway access |
| Private Subnet | NAT Gateway egress only |
| Isolated Subnet | No external access |

### 5.3 IAM (Implicit)

CDK automatically creates IAM roles for:
- Lambda functions (if used)
- API Gateway execution
- Service-to-service communication

---

## 6. Integration Services

### 6.1 API Gateway

| Gateway | Type | Target | Port |
|---------|------|--------|------|
| RestApiConstruct | REST | service-rest | 8080 |
| SoapApiConstruct | REST | service-soap | 8081 |

### 6.2 Message Queue

| Service | Type | Purpose |
|---------|------|---------|
| SQS | Standard Queue | Async message processing |

---

## 7. CDK Stack Structure

### 7.1 Stack Hierarchy

```
┌─────────────────────────────────────────────────────────────┐
│                        InfraApp                             │
│                    (CDK Application)                        │
└─────────────────────────┬───────────────────────────────────┘
                          │
                          ▼
┌─────────────────────────────────────────────────────────────┐
│                       MainStack                             │
│                 (CloudFormation Stack)                      │
│  ┌────────────────┐  ┌────────────────┐  ┌───────────────┐ │
│  │NetworkConstruct│  │StorageConstruct│  │RestApiConstruct│ │
│  │                │  │                │  │               │ │
│  │ - VPC          │  │ - S3 Bucket    │  │ - API Gateway │ │
│  │ - Subnets      │  │ - SQS Queue    │  │   (REST)      │ │
│  │ - NAT Gateway  │  │ - DynamoDB     │  │               │ │
│  └────────────────┘  └────────────────┘  └───────────────┘ │
│                                          ┌───────────────┐ │
│                                          │SoapApiConstruct│ │
│                                          │               │ │
│                                          │ - API Gateway │ │
│                                          │   (SOAP)      │ │
│                                          └───────────────┘ │
└─────────────────────────────────────────────────────────────┘
```

### 7.2 Construct Classes

| Class | Package | Purpose |
|-------|---------|---------|
| `InfraApp` | `com.example.infra` | CDK app entry point |
| `MainStack` | `com.example.infra` | Stack composition |
| `NetworkConstruct` | `com.example.infra.construct` | VPC and networking |
| `StorageConstruct` | `com.example.infra.construct` | S3, SQS, DynamoDB |
| `RestApiConstruct` | `com.example.infra.construct` | REST API Gateway |
| `SoapApiConstruct` | `com.example.infra.construct` | SOAP API Gateway |

### 7.3 CDK Commands

| Command | Description |
|---------|-------------|
| `cdk synth` | Generate CloudFormation template |
| `cdk deploy` | Deploy stack to AWS |
| `cdk diff` | Show changes vs deployed |
| `cdk destroy` | Remove stack |

---

## 8. CDK Configuration

### 8.1 cdk.json

```json
{
  "app": "mvn -e -q compile exec:java",
  "context": {
    "@aws-cdk/aws-apigateway:usagePlanKeyOrderInsensitiveId": true,
    "@aws-cdk/core:stackRelativeExports": true,
    "@aws-cdk/aws-rds:lowercaseDbIdentifier": true,
    "@aws-cdk/core:newStyleStackSynthesis": true
  }
}
```

### 8.2 Feature Flags

| Flag | Value | Purpose |
|------|-------|---------|
| `usagePlanKeyOrderInsensitiveId` | true | API Gateway stability |
| `stackRelativeExports` | true | Cross-stack references |
| `lowercaseDbIdentifier` | true | RDS naming convention |
| `newStyleStackSynthesis` | true | Modern synthesis |

---

## 9. Deployment Architecture

### 9.1 Infrastructure Diagram

```
                          ┌──────────────────┐
                          │    Internet      │
                          └────────┬─────────┘
                                   │
                          ┌────────▼─────────┐
                          │  API Gateway     │
                          │  (REST & SOAP)   │
                          └────────┬─────────┘
                                   │
┌──────────────────────────────────┼──────────────────────────────────┐
│                              VPC │                                   │
│  ┌───────────────────────────────┼───────────────────────────────┐  │
│  │               Public Subnet   │                               │  │
│  │                    ┌──────────▼──────────┐                    │  │
│  │                    │    NAT Gateway      │                    │  │
│  │                    └──────────┬──────────┘                    │  │
│  └───────────────────────────────┼───────────────────────────────┘  │
│                                  │                                   │
│  ┌───────────────────────────────┼───────────────────────────────┐  │
│  │              Private Subnet   │                               │  │
│  │   ┌──────────────┐  ┌────────▼────────┐  ┌──────────────┐    │  │
│  │   │ service-rest │  │  service-batch  │  │ service-soap │    │  │
│  │   │    :8080     │  │    (jobs)       │  │    :8081     │    │  │
│  │   └──────┬───────┘  └────────┬────────┘  └──────┬───────┘    │  │
│  │          │                   │                   │            │  │
│  └──────────┼───────────────────┼───────────────────┼────────────┘  │
│             │                   │                   │                │
│  ┌──────────┼───────────────────┼───────────────────┼────────────┐  │
│  │          ▼                   ▼                   ▼            │  │
│  │   ┌────────────┐      ┌────────────┐      ┌────────────┐     │  │
│  │   │     S3     │      │    SQS     │      │  DynamoDB  │     │  │
│  │   └────────────┘      └────────────┘      └────────────┘     │  │
│  │                    Isolated Subnet                            │  │
│  └───────────────────────────────────────────────────────────────┘  │
│                                                                      │
└──────────────────────────────────────────────────────────────────────┘
```

---

## 10. Stack Outputs

| Output | Description | Source |
|--------|-------------|--------|
| Vpc | VPC reference | NetworkConstruct |
| Bucket | S3 bucket reference | StorageConstruct |
| Queue | SQS queue reference | StorageConstruct |
| Table | DynamoDB table reference | StorageConstruct |
| RestApi | REST API Gateway | RestApiConstruct |
| SoapApi | SOAP API Gateway | SoapApiConstruct |
