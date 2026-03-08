package com.example.model.base;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import org.junit.jupiter.api.Test;

class AuditableEntityTest {

  @Test
  void testDefaultConstructor() {
    ConcreteAuditableEntity entity = new ConcreteAuditableEntity();
    assertNull(entity.getId());
    assertNull(entity.getCreatedAt());
    assertNull(entity.getUpdatedAt());
  }

  @Test
  void testFullConstructor() {
    Instant now = Instant.now();
    ConcreteAuditableEntity entity = new ConcreteAuditableEntity("id-123", now, now);

    assertEquals("id-123", entity.getId());
    assertEquals(now, entity.getCreatedAt());
    assertEquals(now, entity.getUpdatedAt());
  }

  @Test
  void testSetters() {
    ConcreteAuditableEntity entity = new ConcreteAuditableEntity();
    Instant created = Instant.parse("2024-01-01T00:00:00Z");
    Instant updated = Instant.parse("2024-06-01T00:00:00Z");

    entity.setId("id-456");
    entity.setCreatedAt(created);
    entity.setUpdatedAt(updated);

    assertEquals("id-456", entity.getId());
    assertEquals(created, entity.getCreatedAt());
    assertEquals(updated, entity.getUpdatedAt());
  }

  @Test
  void testImplementsAuditable() {
    ConcreteAuditableEntity entity = new ConcreteAuditableEntity();
    assertInstanceOf(Auditable.class, entity);
    assertInstanceOf(Identifiable.class, entity);
    assertInstanceOf(BaseEntity.class, entity);
  }

  @Test
  void testEqualsInherited() {
    ConcreteAuditableEntity entity1 = new ConcreteAuditableEntity("same-id", null, null);
    ConcreteAuditableEntity entity2 = new ConcreteAuditableEntity("same-id", null, null);
    ConcreteAuditableEntity entity3 = new ConcreteAuditableEntity("different-id", null, null);

    assertEquals(entity1, entity2);
    assertEquals(entity1.hashCode(), entity2.hashCode());
    assertNotEquals(entity1, entity3);
  }

  private static class ConcreteAuditableEntity extends AuditableEntity {
    ConcreteAuditableEntity() {}

    ConcreteAuditableEntity(String id, Instant createdAt, Instant updatedAt) {
      super(id, createdAt, updatedAt);
    }
  }
}
