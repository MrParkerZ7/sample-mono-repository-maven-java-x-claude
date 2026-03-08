# Utils Module - Overview

## Purpose

Provides common utility functions for JSON serialization, date/time operations, and string manipulation with consistent behavior across the application.

## Design

- All utility classes are `final` with private constructors (static-only usage)
- `JsonUtils` uses Jackson with ISO date formatting and Java 8 time support
- `DateUtils` operates exclusively in UTC timezone
- `StringUtils` provides null-safe string operations

## Key Classes

| Class | Responsibility |
|-------|----------------|
| `JsonUtils` | JSON serialization/deserialization with Jackson |
| `DateUtils` | Date and time operations in UTC |
| `StringUtils` | Null-safe string operations and validation |

## Dependencies

- `com.fasterxml.jackson.core:jackson-databind` - JSON processing
- `com.fasterxml.jackson.datatype:jackson-datatype-jsr310` - Java 8 date/time support
