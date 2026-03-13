# Fat JAR Dependency Duplication

## Problem Statement

In a modular monorepo with transitive dependencies, a shared library can appear
multiple times across the dependency tree:

```
common/
  some-util       (imports a.jar)
  some-common     (imports some-util + a.jar)
application/
  some-app        (imports some-util + some-common + a.jar)
```

If `a.jar` is 5MB, does `some-app` end up with 15MB from `a.jar` alone (3 copies)?

## Resolution in Maven

**No duplication occurs.** Maven's dependency resolver deduplicates automatically.

### During compilation (`mvn clean install`)

- Each module's thin JAR contains only its own compiled classes.
- Dependencies are resolved from the local `.m2` repository at build/runtime.
- Maven's dependency tree flattens transitive dependencies and includes each
  artifact exactly once.

Verify with:

```bash
mvn dependency:tree -pl <module>
```

Duplicates appear as `(omitted for duplicate)` or `(omitted for conflict)`.

### During fat JAR packaging (`spring-boot-maven-plugin repackage`)

Spring Boot flattens all dependencies into `BOOT-INF/lib/` with one copy each:

```
some-app-1.0.0.jar
  BOOT-INF/lib/
    a.jar              <- single copy
    some-util.jar
    some-common.jar
```

### Where duplication DOES occur

When deploying **multiple fat JARs** side by side, each is self-contained:

```
target/
  some-app-1.0.0.jar        <- contains a.jar (5MB)
  another-app-1.0.0.jar     <- also contains a.jar (5MB)
```

This is by design for independent deployability.

## Mitigation Strategies (cross-app duplication)

| Approach | Description | Trade-off |
|----------|-------------|-----------|
| Thin launcher | `spring-boot-thin-launcher` downloads deps at startup | Smaller artifact, needs network at runtime |
| Layered JARs | Spring Boot layers mode for Docker image caching | Shared layers cached, only app code changes per deploy |
| Shared lib folder | Thin JARs + shared `lib/` directory | Smallest disk, harder dependency management |
| Docker multi-stage | Base image contains shared dependencies | Standard production approach |

## Status

- Maven/Java: Resolved by build tool (no action needed).
- Node/Webpack: To be investigated (Webpack tree-shaking and code splitting may
  handle this differently).
