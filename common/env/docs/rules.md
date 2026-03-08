# Env Module - Rules

## Error Handling

- `getEnv()` and `getProperty()` return `null` for missing values
- `getInt()` returns default value if parsing fails
- `getBoolean()` returns default value if parsing fails
- No exceptions thrown for missing or invalid configuration

## Usage Rules

1. Use `getEnvOrDefault()` or `getOrDefault()` for optional configuration
2. Use `getEnvOptional()` when you need to distinguish between missing and empty values
3. Use `ConfigurationProvider` for application-specific configuration
4. Use `EnvironmentManager` for environment and system property access

## Profile Conventions

- `development` - Local development environment
- `test` - Test/CI environment
- `production` - Production environment
- `default` - Fallback when no profile is set

## Thread Safety

- `EnvironmentManager` is thread-safe (reads from system environment)
- `ConfigurationProvider` uses `ConcurrentHashMap` internally
- All methods are safe for concurrent access
