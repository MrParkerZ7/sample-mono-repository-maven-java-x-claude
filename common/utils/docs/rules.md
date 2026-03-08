# Utils Module - Rules

## Error Handling

- `JsonUtils.fromJson()` throws `JsonSerializationException` on parse failure
- `JsonUtils.fromJsonSafe()` returns `Optional.empty()` on parse failure (no exception)
- `DateUtils` parsing methods throw `DateTimeParseException` for invalid formats
- `StringUtils` methods are null-safe and never throw for null input

## Usage Rules

1. Use `fromJsonSafe()` when parsing untrusted input
2. Always use `DateUtils` for date operations to ensure UTC consistency
3. Use `StringUtils.isBlank()` instead of manual null checks
4. Use `truncate()` for user-facing strings with length limits

## Date/Time Conventions

- All dates are processed in UTC timezone
- Use ISO 8601 format for serialization
- `LocalDateTime` assumes UTC context

## Thread Safety

- `JsonUtils.OBJECT_MAPPER` is thread-safe (Jackson ObjectMapper)
- All utility methods are stateless and thread-safe
- No mutable shared state in any utility class
