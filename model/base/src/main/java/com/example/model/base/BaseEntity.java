package com.example.model.base;

import java.util.Objects;

/** Abstract base entity with identifier and equality based on id. */
public abstract class BaseEntity implements Identifiable {

  private String id;

  /** Default constructor. */
  protected BaseEntity() {}

  /** Constructor with id. */
  protected BaseEntity(String id) {
    this.id = id;
  }

  @Override
  public String getId() {
    return id;
  }

  /** Sets the unique identifier. */
  public void setId(String id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    BaseEntity that = (BaseEntity) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
