# Env Module - Overview

## Purpose

Provides unified access to environment variables, system properties, and application configuration with hierarchical fallback support.

## Design

- `EnvironmentManager` provides static access to environment and system properties
- `ConfigurationProvider` supports hierarchical lookup: local config -> environment -> system properties
- Spring profile detection via `SPRING_PROFILES_ACTIVE` or `spring.profiles.active`

## Key Classes

| Class | Responsibility |
|-------|----------------|
| `EnvironmentManager` | Static access to environment variables and system properties |
| `ConfigurationProvider` | Hierarchical configuration with type conversion |

## Dependencies

- None (standalone module)
