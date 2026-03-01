# Sample Mono Repository - Maven Java

A Java Maven multi-module monorepo demonstrating best practices for enterprise application development with Spring Boot, AWS SDK, and AWS CDK.

## Technology Stack

| Technology | Version | Purpose |
|------------|---------|---------|
| Java | 21 (LTS) | Runtime |
| Spring Boot | 3.4.3 | Application framework |
| AWS SDK | 2.25.x | AWS service integration |
| AWS CDK | 2.130.x | Infrastructure as Code |
| Maven | 3.9+ | Build tool |
| JaCoCo | 0.8.12 | Code coverage (100% enforced) |
| Spotless | 2.43.x | Code formatting (Google Java Format) |

## Project Structure

```
sample-mono-repository-maven-java-x-claude/
├── pom.xml                              # Parent POM (BOM + plugin management)
├── common/                              # Shared libraries
│   ├── common-aws/                      # AWS service wrappers (S3, SQS, DynamoDB)
│   ├── common-exception/                # Exception hierarchy
│   ├── common-utils/                    # Utilities (JSON, Date, String)
│   └── common-env/                      # Environment & configuration
├── service/                             # Application services
│   ├── service-rest/                    # REST API (Spring Web)
│   ├── service-batch/                   # Batch jobs (Spring Batch)
│   └── service-soap/                    # SOAP service (Spring WS)
└── infra/                               # Infrastructure as Code
    └── construct/                       # CDK constructs (VPC, API Gateway, etc.)
```

## Modules

### Common Modules

| Module | Description |
|--------|-------------|
| `common-exception` | Base exception classes (`BusinessException`, `TechnicalException`) |
| `common-utils` | Utility classes for JSON, Date, and String operations |
| `common-env` | Environment management and configuration providers |
| `common-aws` | AWS SDK v2 wrappers for S3, SQS, and DynamoDB |

### Service Modules

| Module | Port | Description |
|--------|------|-------------|
| `service-rest` | 8080 | REST API with health and sample endpoints |
| `service-batch` | - | Spring Batch job processing |
| `service-soap` | 8081 | SOAP web service with WSDL support |

### Infrastructure Module

| Construct | Resources |
|-----------|-----------|
| `NetworkConstruct` | VPC with public, private, and isolated subnets |
| `StorageConstruct` | S3 bucket, SQS queue, DynamoDB table |
| `RestApiConstruct` | API Gateway for REST service |
| `SoapApiConstruct` | API Gateway for SOAP service |

## Getting Started

### Prerequisites

- Java 21+
- Maven 3.9+
- AWS CLI (for CDK deployment)

### Build

```bash
# Compile all modules
mvn clean compile

# Run tests with coverage
mvn clean verify

# Install to local repository
mvn clean install
```

### Code Quality

```bash
# Check code formatting
mvn spotless:check

# Auto-fix formatting issues
mvn spotless:apply
```

### Run Services

```bash
# REST Service
cd service/service-rest
mvn spring-boot:run

# SOAP Service
cd service/service-soap
mvn spring-boot:run

# Batch Job
cd service/service-batch
mvn spring-boot:run
```

### Deploy Infrastructure

```bash
cd infra

# Synthesize CloudFormation template
cdk synth

# Deploy to AWS
cdk deploy
```

## Architecture

### Dependency Management

The parent POM uses `<dependencyManagement>` to define versions centrally. Child modules declare only the dependencies they need **without versions**:

```xml
<!-- Parent POM defines version -->
<dependencyManagement>
    <dependency>
        <groupId>software.amazon.awssdk</groupId>
        <artifactId>s3</artifactId>
        <version>2.25.70</version>
    </dependency>
</dependencyManagement>

<!-- Child POM uses without version -->
<dependencies>
    <dependency>
        <groupId>software.amazon.awssdk</groupId>
        <artifactId>s3</artifactId>
    </dependency>
</dependencies>
```

This ensures:
- Consistent versions across modules
- No duplicate dependency declarations
- Modules only include what they explicitly need

### Quality Enforcement

- **100% Line Coverage**: JaCoCo fails the build if coverage drops below 100%
- **Google Java Format**: Spotless enforces consistent code style during `validate` phase

## API Endpoints

### REST Service (port 8080)

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/health` | Health check |
| GET | `/api/samples/{id}` | Get sample by ID |

### SOAP Service (port 8081)

| Endpoint | Description |
|----------|-------------|
| POST `/ws` | SOAP request endpoint |
| GET `/ws/sample.wsdl` | WSDL definition |

## License

MIT
