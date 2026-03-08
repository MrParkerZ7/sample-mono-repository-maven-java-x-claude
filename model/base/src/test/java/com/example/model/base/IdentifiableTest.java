package com.example.model.base;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class IdentifiableTest {

  @Test
  void testIdentifiableContract() {
    Identifiable identifiable = () -> "test-id";
    assertEquals("test-id", identifiable.getId());
  }

  @Test
  void testIdentifiableNullId() {
    Identifiable identifiable = () -> null;
    assertNull(identifiable.getId());
  }
}
