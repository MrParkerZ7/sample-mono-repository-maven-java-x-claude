package com.example.model.base;

import java.time.Instant;

/** Interface for entities that track creation and modification timestamps. */
public interface Auditable {

  /** Returns the creation timestamp. */
  Instant getCreatedAt();

  /** Returns the last modification timestamp. */
  Instant getUpdatedAt();
}
