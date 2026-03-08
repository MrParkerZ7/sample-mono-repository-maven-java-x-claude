# Env Module

Environment and configuration management utilities.

## Quick Start

```java
// Environment variables and system properties
String dbUrl = EnvironmentManager.getEnv("DATABASE_URL");
String profile = EnvironmentManager.getActiveProfile();
boolean isProd = EnvironmentManager.isProduction();

// Hierarchical configuration provider
ConfigurationProvider config = new ConfigurationProvider();
config.set("app.name", "MyApp");
String name = config.get("app.name");
int port = config.getInt("server.port", 8080);
boolean debug = config.getBoolean("debug.enabled", false);
```

## Dependency

```xml
<dependency>
    <groupId>com.example</groupId>
    <artifactId>env</artifactId>
</dependency>
```
