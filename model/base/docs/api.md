# Model Base - API

## Interfaces

### Identifiable
```java
public interface Identifiable {
    String getId();
}
```

### Auditable
```java
public interface Auditable {
    Instant getCreatedAt();
    Instant getUpdatedAt();
}
```

## Abstract Classes

### BaseEntity
```java
public abstract class BaseEntity implements Identifiable {
    // Fields: id
    // Methods: getId(), setId(), equals(), hashCode()
}
```

### AuditableEntity
```java
public abstract class AuditableEntity extends BaseEntity implements Auditable {
    // Fields: createdAt, updatedAt
    // Methods: getCreatedAt(), setCreatedAt(), getUpdatedAt(), setUpdatedAt()
}
```
