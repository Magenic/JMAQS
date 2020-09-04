# <img src="resources/jmaqslogo.jpg" height="32" width="32"> Database Providers

## Overview
We support several providers with Database testing, the providers support getting the data source and the Dialect.

## Supported Providers
H2Providers  
MySQLProvider  
SQLiteProvider  
SQLProvider  

## GetDataSource
This method for all Providers returns the data source for the Provider object.
```java
Properties properties = new Properties();
properties.put(HIBERNATE_CONNECTION_DATASOURCE, DatabaseConfig.getProvider().getDataSource());
```
## GetDialect
This method returns the dialect of the provider object.
 ```java
 Properties properties = new Properties();
 properties.put(HIBERNATE_DIALECT, DatabaseConfig.getProvider().getDialect());
 ```