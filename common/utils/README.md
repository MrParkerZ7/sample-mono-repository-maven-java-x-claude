# Utils Module

General utilities for JSON, Date, and String operations.

## Quick Start

```java
// JSON operations
String json = JsonUtils.toJson(myObject);
MyClass obj = JsonUtils.fromJson(json, MyClass.class);
Optional<MyClass> safe = JsonUtils.fromJsonSafe(json, MyClass.class);

// Date operations
Instant now = DateUtils.now();
LocalDate today = DateUtils.today();
String formatted = DateUtils.formatDateTime(localDateTime);
LocalDateTime parsed = DateUtils.parseDateTime("2024-01-15T10:30:00");

// String operations
boolean blank = StringUtils.isBlank(str);
String truncated = StringUtils.truncate(str, 100);
String uuid = StringUtils.generateUuid();
```

## Dependency

```xml
<dependency>
    <groupId>com.example</groupId>
    <artifactId>utils</artifactId>
</dependency>
```
