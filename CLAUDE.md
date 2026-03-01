# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with this repository.

## Project Overview

Java Maven multi-module monorepo with Spring Boot services, AWS SDK integrations, and AWS CDK infrastructure.

## Build Commands

```bash
# Full build with tests and coverage
mvn clean verify

# Compile only
mvn clean compile

# Install to local repository
mvn clean install

# Build specific module (with dependencies)
mvn clean verify -pl infra -am

# Skip tests
mvn clean install -DskipTests
```

## Code Quality

```bash
# Check formatting (runs automatically during validate phase)
mvn spotless:check

# Auto-fix formatting
mvn spotless:apply
```

**Important**: Always run `mvn spotless:apply` before committing to fix formatting issues.

## Testing

- **Coverage**: 100% line coverage enforced by JaCoCo
- **Framework**: JUnit 5 + Mockito
- All new code must have complete test coverage or the build will fail

```bash
# Run tests only
mvn test

# Run tests with coverage report
mvn verify
# Coverage reports: target/site/jacoco/index.html
```

## Module Structure

| Module | Path | Purpose |
|--------|------|---------|
| Parent POM | `pom.xml` | Dependency & plugin management |
| common-exception | `common/common-exception` | Exception classes |
| common-utils | `common/common-utils` | JSON, Date, String utilities |
| common-env | `common/common-env` | Environment configuration |
| common-aws | `common/common-aws` | AWS SDK wrappers |
| service-rest | `service/service-rest` | REST API (port 8080) |
| service-batch | `service/service-batch` | Spring Batch jobs |
| service-soap | `service/service-soap` | SOAP service (port 8081) |
| infra | `infra` | AWS CDK constructs |

## Key Conventions

### Dependencies
- Versions defined in parent POM `<dependencyManagement>`
- Child modules declare dependencies WITHOUT versions
- Each module only includes dependencies it actually uses

### Code Style
- Google Java Format enforced via Spotless
- No emojis in code or comments
- Javadoc for public classes and methods

### Exceptions
- Extend `BusinessException` for business logic errors
- Extend `TechnicalException` for infrastructure/technical errors

### Testing
- Test class naming: `*Test.java`
- Use `@ExtendWith(MockitoExtension.class)` for mocking
- Spring Boot tests: `@SpringBootTest` or `@WebMvcTest`

## CDK Infrastructure

```bash
cd infra

# Synthesize CloudFormation
cdk synth

# Deploy
cdk deploy

# Diff against deployed stack
cdk diff
```

### Constructs
- `NetworkConstruct` - VPC with 3 subnet types (public, private, isolated)
- `StorageConstruct` - S3, SQS, DynamoDB
- `RestApiConstruct` - API Gateway for REST service
- `SoapApiConstruct` - API Gateway for SOAP service

## Common Tasks

### Adding a new common module
1. Create directory under `common/`
2. Add `pom.xml` inheriting from parent (use `../../pom.xml` relativePath)
3. Add module to `common/pom.xml` modules list
4. Add dependency management entry in parent POM

### Adding a new service
1. Create directory under `service/`
2. Add `pom.xml` with Spring Boot plugin
3. Add module to `service/pom.xml` modules list
4. Include `@SpringBootApplication` main class
5. Add application.yml configuration

### Adding AWS resources
1. Create new construct in `infra/src/main/java/com/example/infra/construct/`
2. Add construct to `MainStack`
3. Add corresponding test class
4. Ensure 100% coverage
