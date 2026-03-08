# Infrastructure Module - Overview

## Purpose

Defines AWS infrastructure using CDK (Cloud Development Kit) with Java, providing infrastructure-as-code for the complete application stack.

## Design

- Single `MainStack` composing all infrastructure
- Modular constructs for network, storage, and API resources
- Regional deployment with multi-AZ support
- Secure defaults (encryption, private subnets, blocked public access)

## Key Classes

| Class | Responsibility |
|-------|----------------|
| `InfraApp` | CDK application entry point |
| `MainStack` | Root stack composing all constructs |
| `NetworkConstruct` | VPC and networking resources |
| `StorageConstruct` | S3, SQS, and DynamoDB resources |
| `RestApiConstruct` | REST API Gateway resources |
| `SoapApiConstruct` | SOAP API Gateway resources |

## Dependencies

- `software.amazon.awscdk:aws-cdk-lib` - AWS CDK library
- `software.constructs:constructs` - CDK constructs base
