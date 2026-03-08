# Model Base

Base entity abstractions for shared domain models.

## Quick Start

Add dependency:
```xml
<dependency>
    <groupId>com.example</groupId>
    <artifactId>model-base</artifactId>
</dependency>
```

## Usage

### Entities with audit timestamps
```java
public class MyEntity extends AuditableEntity {
    // inherits: id, createdAt, updatedAt, equals, hashCode
}
```

### Entities with id only
```java
public class MyEntity extends BaseEntity {
    // inherits: id, equals, hashCode
}
```

## Hierarchy

```
Identifiable (interface)      Auditable (interface)
     |                              |
BaseEntity (abstract)               |
     |                              |
AuditableEntity (abstract) --------+
```

## Build

```bash
mvn clean verify -pl model/base -am
```
