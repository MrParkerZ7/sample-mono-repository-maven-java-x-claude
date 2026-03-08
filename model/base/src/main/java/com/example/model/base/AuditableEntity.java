package com.example.model.base;

import java.time.Instant;

/** Abstract entity with identifier and audit timestamps. */
public abstract class AuditableEntity extends BaseEntity implements Auditable {

  private Instant createdAt;
  private Instant updatedAt;

  /** Default constructor. */
  protected AuditableEntity() {}

  /** Constructor with id and timestamps. */
  protected AuditableEntity(String id, Instant createdAt, Instant updatedAt) {
    super(id);
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  @Override
  public Instant getCreatedAt() {
    return createdAt;
  }

  /** Sets the creation timestamp. */
  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }

  @Override
  public Instant getUpdatedAt() {
    return updatedAt;
  }

  /** Sets the last modification timestamp. */
  public void setUpdatedAt(Instant updatedAt) {
    this.updatedAt = updatedAt;
  }
}
