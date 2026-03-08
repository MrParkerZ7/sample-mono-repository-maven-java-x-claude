# Env Module - API

## EnvironmentManager

Static utility for environment variables and system properties.

### Methods

| Method | Parameters | Returns | Description |
|--------|------------|---------|-------------|
| `getEnv` | String name | String | Retrieves environment variable |
| `getEnvOptional` | String name | Optional&lt;String&gt; | Retrieves as Optional |
| `getEnvOrDefault` | String name, String defaultValue | String | Retrieves with default fallback |
| `getProperty` | String name | String | Retrieves system property |
| `getPropertyOptional` | String name | Optional&lt;String&gt; | Retrieves as Optional |
| `getPropertyOrDefault` | String name, String defaultValue | String | Retrieves with default fallback |
| `getActiveProfile` | none | String | Returns active Spring profile |
| `isDevelopment` | none | boolean | Checks if profile is "development" |
| `isProduction` | none | boolean | Checks if profile is "production" |
| `isTest` | none | boolean | Checks if profile is "test" |

### Profile Detection

Checks in order:
1. Environment variable `SPRING_PROFILES_ACTIVE`
2. System property `spring.profiles.active`
3. Default: "default"

## ConfigurationProvider

Hierarchical configuration provider with type conversion.

### Constructor

```java
public ConfigurationProvider()
public ConfigurationProvider(Map<String, String> initialConfig)
```

### Methods

| Method | Parameters | Returns | Description |
|--------|------------|---------|-------------|
| `set` | String key, String value | void | Sets configuration value |
| `get` | String key | String | Retrieves value with fallback |
| `getOptional` | String key | Optional&lt;String&gt; | Retrieves as Optional |
| `getOrDefault` | String key, String defaultValue | String | Retrieves with default |
| `getInt` | String key, int defaultValue | int | Parses as integer |
| `getBoolean` | String key, boolean defaultValue | boolean | Parses as boolean |
| `containsKey` | String key | boolean | Checks if key exists |
| `clear` | none | void | Clears all local configurations |

### Lookup Hierarchy

1. Local configuration (set via `set()`)
2. Environment variables
3. System properties
