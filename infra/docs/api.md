# Infrastructure Module - API

## InfraApp

CDK application entry point.

### Main Method

```java
public static void main(String[] args)
```

Creates App instance, instantiates MainStack, and synthesizes CloudFormation.

## MainStack

Root stack composing all infrastructure constructs.

### Constructor

```java
public MainStack(Construct scope, String id, StackProps props)
```

### Getter Methods

| Method | Returns | Description |
|--------|---------|-------------|
| `getVpc` | Vpc | Network VPC |
| `getBucket` | Bucket | S3 bucket |
| `getQueue` | Queue | SQS queue |
| `getTable` | Table | DynamoDB table |
| `getRestApi` | RestApi | REST API Gateway |
| `getSoapApi` | RestApi | SOAP API Gateway |

## NetworkConstruct

VPC and networking resources.

### VPC Configuration

| Property | Value |
|----------|-------|
| CIDR | `10.0.0.0/16` |
| Max AZs | 2 |
| Subnets | Public, Private with egress, Private isolated |

### Getter Methods

| Method | Returns | Description |
|--------|---------|-------------|
| `getVpc` | Vpc | Configured VPC |

## StorageConstruct

Storage resources (S3, SQS, DynamoDB).

### S3 Bucket Configuration

| Property | Value |
|----------|-------|
| Encryption | S3 managed |
| Public Access | Blocked |
| Removal Policy | DESTROY |

### DynamoDB Table Configuration

| Property | Value |
|----------|-------|
| Partition Key | `id` (STRING) |
| Billing Mode | PAY_PER_REQUEST |

### Getter Methods

| Method | Returns | Description |
|--------|---------|-------------|
| `getBucket` | Bucket | S3 bucket |
| `getQueue` | Queue | SQS queue |
| `getTable` | Table | DynamoDB table |

## RestApiConstruct

REST API Gateway resources.

### API Configuration

| Property | Value |
|----------|-------|
| Name | REST Service API |
| Type | REGIONAL |
| Stage | prod |

### Endpoints

| Path | Method | Description |
|------|--------|-------------|
| `/health` | GET | Health check |
| `/samples/{id}` | GET | Get sample |

## SoapApiConstruct

SOAP API Gateway resources.

### API Configuration

| Property | Value |
|----------|-------|
| Name | SOAP Service API |
| Type | REGIONAL |
| Stage | prod |

### Endpoints

| Path | Method | Description |
|------|--------|-------------|
| `/ws` | POST | SOAP endpoint |
| `/ws/sample.wsdl` | GET | WSDL definition |
