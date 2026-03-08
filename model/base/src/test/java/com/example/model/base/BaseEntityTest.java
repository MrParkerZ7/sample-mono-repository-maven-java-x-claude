package com.example.model.base;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BaseEntityTest {

  @Test
  void testDefaultConstructor() {
    ConcreteEntity entity = new ConcreteEntity();
    assertNull(entity.getId());
  }

  @Test
  void testConstructorWithId() {
    ConcreteEntity entity = new ConcreteEntity("entity-123");
    assertEquals("entity-123", entity.getId());
  }

  @Test
  void testSetId() {
    ConcreteEntity entity = new ConcreteEntity();
    entity.setId("entity-456");
    assertEquals("entity-456", entity.getId());
  }

  @Test
  void testEqualsAndHashCode() {
    ConcreteEntity entity1 = new ConcreteEntity("same-id");
    ConcreteEntity entity2 = new ConcreteEntity("same-id");
    ConcreteEntity entity3 = new ConcreteEntity("different-id");

    assertEquals(entity1, entity2);
    assertEquals(entity1.hashCode(), entity2.hashCode());
    assertNotEquals(entity1, entity3);
    assertNotEquals(entity1, null);
    assertNotEquals(entity1, new Object());
    assertEquals(entity1, entity1);
  }

  @Test
  void testEqualsDifferentSubclass() {
    ConcreteEntity entity = new ConcreteEntity("id-1");
    AnotherEntity other = new AnotherEntity("id-1");
    assertNotEquals(entity, other);
  }

  @Test
  void testEqualsWithNullIds() {
    ConcreteEntity entity1 = new ConcreteEntity();
    ConcreteEntity entity2 = new ConcreteEntity();
    assertEquals(entity1, entity2);
    assertEquals(entity1.hashCode(), entity2.hashCode());
  }

  private static class ConcreteEntity extends BaseEntity {
    ConcreteEntity() {}

    ConcreteEntity(String id) {
      super(id);
    }
  }

  private static class AnotherEntity extends BaseEntity {
    AnotherEntity(String id) {
      super(id);
    }
  }
}
