# Exception Module - Overview

## Purpose

Provides a standardized exception hierarchy for distinguishing between business logic violations (client fault) and technical/infrastructure errors (system fault).

## Design

- Abstract `BaseException` provides common error code functionality
- `BusinessException` for client-caused errors (invalid input, business rule violations)
- `TechnicalException` for system-caused errors (database failures, external service errors)
- All exceptions carry an error code for consistent error handling

## Key Classes

| Class | Responsibility |
|-------|----------------|
| `BaseException` | Abstract base class with error code support |
| `BusinessException` | Business logic violations (client fault) |
| `TechnicalException` | Technical/infrastructure errors (system fault) |

## Dependencies

- None (standalone module)
