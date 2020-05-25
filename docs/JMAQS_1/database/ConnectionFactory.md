# <img src="resources/jmaqslogo.jpg" height="32" width="32"> Connection Factory

## Overview
 Manages and sets connections for Database Testing
 
 ## GetOpenConnection
 This returns an open connection of this ConnectionFactory object.
 ```java
final DatabaseDriver openConnection = ConnectionFactory.getOpenConnection();
```

## GetEntityManagerFactory
This returns an entityManagerFactory of this ConnectionFactory object.
```java
final EntityManagerFactory entityManagerFactory = getEntityManagerFactory();
```

## GetProperties
This method returns the properties of the ConnectionFactory object.
```java
Properties properties = getProperties();
```

## GetEntityFiles
This Method returns the entityFiles of this ConnectionFactory object.
```java
File[] files = getEntityFiles();
```

## GetEntityClassNames
This method returns the entityClassNames of this ConnectionFactory object.
```java
List<String> classNames = getEntityClassNames() 
```

## GetPersistenceUnitInfo
This Method returns the persistenceUnitInfo of this ConnectionFactory object.
```java
DatabasePersistenceUnitInfo persistenceUnitInfo = getPersistenceUnitInfo(DatabaseConfig.getDatabaseName());
```