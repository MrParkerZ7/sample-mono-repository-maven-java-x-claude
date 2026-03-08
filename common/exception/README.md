# Exception Module

Standard exception hierarchy for business and technical errors.

## Quick Start

```java
// Business error (client fault)
throw new BusinessException("ERR_VALIDATION", "Invalid input");

// Technical error (system fault)
throw new TechnicalException("ERR_DATABASE", "Connection failed", cause);

// Simple constructors with default error codes
throw new BusinessException("User not found");
throw new TechnicalException("Service unavailable");
```

## Dependency

```xml
<dependency>
    <groupId>com.example</groupId>
    <artifactId>exception</artifactId>
</dependency>
```
