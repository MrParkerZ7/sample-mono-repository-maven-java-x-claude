# Exception Module - Rules

## Error Handling

- Use `BusinessException` for errors caused by invalid client input or business rule violations
- Use `TechnicalException` for errors caused by system failures (database, network, external services)
- Always provide a meaningful error code for categorization and logging

## Usage Rules

1. Never catch and swallow exceptions without logging
2. Preserve the original cause when wrapping exceptions
3. Use specific error codes for different error scenarios
4. Keep error messages concise but informative

## Error Code Conventions

- Prefix with domain: `ERR_VALIDATION`, `ERR_DATABASE`, `ERR_AUTH`
- Use uppercase with underscores
- Be specific: `ERR_USER_NOT_FOUND` instead of `ERR_NOT_FOUND`

## Thread Safety

- All exception classes are immutable after construction
- Exception instances are thread-safe
