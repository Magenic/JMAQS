# <img src="resources/jmaqslogo.jpg" height="32" width="32">Database Info Constants

## Overview
The placeholder for the Data provider types and interactions.

## Find
Finds the provider data type via a string.
```java
DataProviderType providerType = DataProviderType.find("Oracle");
```

## GetName
Gets the Provider Type Name
```java
DataProviderType type = DataProviderType.SQL;
String providerType = type.getName();
```

## GetDialectString
Gets the dialect string
```java
DataProviderType type = DataProviderType.SQL;
String dialectString = type.getDialectString();
```