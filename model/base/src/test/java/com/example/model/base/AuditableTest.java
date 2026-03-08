package com.example.model.base;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import org.junit.jupiter.api.Test;

class AuditableTest {

  @Test
  void testAuditableContract() {
    Instant created = Instant.parse("2024-01-01T00:00:00Z");
    Instant updated = Instant.parse("2024-06-01T00:00:00Z");

    Auditable auditable =
        new Auditable() {
          @Override
          public Instant getCreatedAt() {
            return created;
          }

          @Override
          public Instant getUpdatedAt() {
            return updated;
          }
        };

    assertEquals(created, auditable.getCreatedAt());
    assertEquals(updated, auditable.getUpdatedAt());
  }
}
