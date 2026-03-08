# Model Base - Overview

## Purpose

Provides shared base abstractions for domain entities, eliminating duplication of common fields (id, timestamps) and behavior (equals, hashCode) across service modules.

## Design

### Interfaces

- **Identifiable** - Contract for entities with a unique String identifier
- **Auditable** - Contract for entities that track creation and modification timestamps

### Abstract Classes

- **BaseEntity** - Implements `Identifiable` with id field, equals/hashCode based on id. Used by entities that only need an identifier (e.g., OrderItem, UserActivity)
- **AuditableEntity** - Extends `BaseEntity`, implements `Auditable` with createdAt/updatedAt fields. Used by entities that track timestamps (e.g., UserProfile, Product, Order, Payment)

## Dependencies

None (pure Java, no framework dependencies).
