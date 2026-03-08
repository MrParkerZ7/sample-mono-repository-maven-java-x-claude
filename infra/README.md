# Infrastructure Module

AWS CDK infrastructure-as-code for the application stack.

## Quick Start

```bash
# Synthesize CloudFormation template
cd infra
cdk synth

# Deploy stack
cdk deploy

# Destroy stack
cdk destroy
```

## Stacks

| Stack | Description |
|-------|-------------|
| `MainStack` | Complete application infrastructure |

## Resources

- VPC with public/private subnets
- S3 bucket for storage
- SQS queue for messaging
- DynamoDB table for data
- API Gateway (REST and SOAP)

## Dependency

```xml
<dependency>
    <groupId>com.example</groupId>
    <artifactId>infra</artifactId>
</dependency>
```
