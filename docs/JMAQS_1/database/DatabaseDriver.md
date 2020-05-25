# <img src="resources/jmaqslogo.jpg" height="32" width="32"> Database Driver

## Overview
Driver to manage the database manager, factory, and querying the database.

## GetEntityManager
Gets the Entity Manager
```java
DatabaseDriver openConnection = ConnectionFactory.getOpenConnection();
final int hashCode = openConnection.getEntityManager().hashCode();
```

## GetEntityManagerFactory
Gets the entity manager factory
```java
DatabaseDriver openConnection = ConnectionFactory.getOpenConnection();
final int hashCode = openConnection.getEntityManagerFactory().hashCode();
```

## SetEntityManager
Sets the entity manager.
```java
DatabaseDriver openConnection = ConnectionFactory.getOpenConnection();
openConnection.setEntityManager(ConnectionFactory.getEntityManagerFactory().createEntityManager());
```

## SetEntityManagerFactory
Sets the entity manager factory.
```java
DatabaseDriver openConnection = ConnectionFactory.getOpenConnection();
openConnection.setEntityManagerFactory(ConnectionFactory.getEntityManagerFactory());
```

## Query
Queries the database. 
```java
DatabaseDriver openConnection = ConnectionFactory.getOpenConnection();
List results = openConnection.query(INFORMATION_SCHEMAS_QUERY);

DatabaseDriver openConnection = ConnectionFactory.getOpenConnection();
final List<StatesEntity> queryResults = openConnection.query(STATES_SELECT_QUERY, StatesEntity.class);
```

## Close
Closes the Entity Manager and the Entity manager factory.
```java
DatabaseDriver openConnection = ConnectionFactory.getOpenConnection();
openConnection.close();
```

## IsOpen
Check if EntityManager and EntityManagerFactory are open.
```java
DatabaseDriver openConnection = ConnectionFactory.getOpenConnection();
openConnection.close();
boolean connection = openConnection.isOpen());
```