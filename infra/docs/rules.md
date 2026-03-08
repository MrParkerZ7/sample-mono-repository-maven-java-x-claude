# Infrastructure Module - Rules

## Error Handling

- CDK synthesis fails fast on configuration errors
- CloudFormation rollback on deployment failures
- Stack outputs provide resource identifiers

## Usage Rules

1. Run `cdk synth` before deploying to validate templates
2. Review changes with `cdk diff` before deployment
3. Use `DESTROY` removal policy only for non-production
4. Tag all resources appropriately

## Security Defaults

- S3 buckets block public access
- S3 encryption enabled (S3 managed)
- Private subnets for backend resources
- API Gateway with regional endpoints

## Resource Naming

- Stack ID: `MainStack`
- Construct IDs should be descriptive and unique
- Let CDK generate physical names for CloudFormation

## Environment Configuration

- Override stack props for different environments
- Use context values for environment-specific config
- Separate stacks for production vs development

## Thread Safety

- CDK constructs are not thread-safe during synthesis
- Run synthesis in single-threaded context
- Synthesized templates are immutable
