# Utils Module - API

## JsonUtils

JSON serialization and deserialization using Jackson.

### Static Fields

```java
public static final ObjectMapper OBJECT_MAPPER
```

### Methods

| Method | Parameters | Returns | Description |
|--------|------------|---------|-------------|
| `toJson` | Object object | String | Serializes object to JSON string |
| `fromJson` | String json, Class&lt;T&gt; clazz | T | Deserializes JSON to object |
| `fromJsonSafe` | String json, Class&lt;T&gt; clazz | Optional&lt;T&gt; | Safe deserialization returning Optional |

### Error Codes

| Code | When |
|------|------|
| `JsonSerializationException` | Serialization or deserialization fails |

## DateUtils

Date and time operations using UTC timezone.

### Static Fields

```java
public static final ZoneId UTC
public static final DateTimeFormatter ISO_DATE_TIME
public static final DateTimeFormatter ISO_DATE
```

### Methods

| Method | Parameters | Returns | Description |
|--------|------------|---------|-------------|
| `now` | none | Instant | Returns current instant |
| `today` | none | LocalDate | Returns today's date in UTC |
| `nowLocalDateTime` | none | LocalDateTime | Returns current local date-time in UTC |
| `nowZoned` | none | ZonedDateTime | Returns current zoned date-time in UTC |
| `formatDateTime` | LocalDateTime dateTime | String | Formats to ISO date-time string |
| `formatDate` | LocalDate date | String | Formats to ISO date string |
| `parseDateTime` | String dateTimeString | LocalDateTime | Parses ISO date-time string |
| `parseDate` | String dateString | LocalDate | Parses ISO date string |
| `toInstant` | LocalDateTime localDateTime | Instant | Converts LocalDateTime to Instant |
| `toLocalDateTime` | Instant instant | LocalDateTime | Converts Instant to LocalDateTime |

## StringUtils

Null-safe string operations.

### Methods

| Method | Parameters | Returns | Description |
|--------|------------|---------|-------------|
| `isBlank` | String str | boolean | Checks if null or whitespace only |
| `isNotBlank` | String str | boolean | Inverse of isBlank |
| `defaultIfBlank` | String str, String defaultValue | String | Returns default if blank |
| `truncate` | String str, int maxLength | String | Truncates to max length |
| `generateUuid` | none | String | Generates random UUID string |
| `toUpperCase` | String str | String | Null-safe uppercase conversion |
| `toLowerCase` | String str | String | Null-safe lowercase conversion |
| `trim` | String str | String | Null-safe trim |
