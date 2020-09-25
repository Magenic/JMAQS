# <img src="resources/jmaqslogo.jpg" height="32" width="32"> Database Config

## Overview
Manages the connections to access the databases.

## GetConnectionString
Gets the connection string.
```java
 String connection = getConnectionString();
```

## GetProviderTypeString
Gets provider type string.
```java
String providerType = getProviderTypeString()
```

## GetEntityDirectoryString
Gets the entity directory string
```java
String entity = DatabaseCofig.
```

## GetProviderType
Method returns the providerType of this DatabaseConfig object.
```java
DataProviderType type = DatabaseCofig.getProviderType();

DataProviderType type = getProviderType(Config.getValueForSection(DATABASE_SECTION, "DatabaseProviderType"););
```

## GetProvider
Method returns the provider of the DatabaseConfig object.
```java
final IDataSourceProvider provider = DatabaseConfig.getProvider();

IDataSourceProvider provider = getProvider(getProviderType());
```

## GetDatabaseCapabilitiesAsObjects
Method returns the databaseCapabilitiesAsObjects of this DatabaseConfig object.
```java
Map<String, Object> databaseObject = DatabaseConfig.getDatabaseCapabilitiesAsObjects();
```

## GetEntityPackageString
Method returns the entityPackageString of this DatabaseConfig object.
```java
String entity = DatabaseConfig.getEntityPackageString();
```

## GetDatabaseName
Method returns the name of this DatabaseConfig object.
```java
String name = DatabaseConfig.getDatabaseName();
```

## GetDatabaseUser
Method returns the databaseUser of this DatabaseConfig object.
```java
String user = DatabaseConfig.getDatabaseuser();
```

## GetDatabasePassword
Method returns the databasePassword of this DatabaseConfig object.
```java
String password = DatabaseConfig.getDatabasePassword();
```