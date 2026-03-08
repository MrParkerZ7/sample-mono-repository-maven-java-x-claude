# Model Base - Rules

## Equality

- Entities are equal when they have the same `id` and the same concrete class
- Different subclasses with the same id are NOT equal (uses `getClass()` comparison)
- Null ids follow standard `Objects.equals` behavior

## Inheritance

- All domain entities MUST extend either `BaseEntity` or `AuditableEntity`
- Entities with `createdAt`/`updatedAt` fields SHOULD extend `AuditableEntity`
- Entities with only an `id` field SHOULD extend `BaseEntity`
- Subclasses MUST NOT override `equals()` or `hashCode()` unless they need composite keys
