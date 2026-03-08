# Exception Module - API

## BaseException

Abstract base class for all application exceptions.

### Constructor

```java
protected BaseException(String errorCode, String message)
protected BaseException(String errorCode, String message, Throwable cause)
```

### Methods

| Method | Parameters | Returns | Description |
|--------|------------|---------|-------------|
| `getErrorCode` | none | String | Returns the error classification code |

## BusinessException

Exception for business logic violations (client fault).

### Constructor

```java
public BusinessException(String message)
public BusinessException(String errorCode, String message)
public BusinessException(String errorCode, String message, Throwable cause)
```

### Default Error Code

| Code | When |
|------|------|
| `ERR_BUSINESS` | Default code when using single-argument constructor |

## TechnicalException

Exception for technical/infrastructure errors (system fault).

### Constructor

```java
public TechnicalException(String message)
public TechnicalException(String errorCode, String message)
public TechnicalException(String errorCode, String message, Throwable cause)
```

### Default Error Code

| Code | When |
|------|------|
| `ERR_TECHNICAL` | Default code when using single-argument constructor |
