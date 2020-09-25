# <img src="resources/jmaqslogo.jpg" height="32" width="32"> Database Persistence Unit Info

## Overview
Manages the database persistence unit info

## GetPersistenceUnitName
Gets the persistence unit name.
```java
public String getPersistenceUnitName() {
    return persistenceUnitName;
  }
```

## GetPersistenceProviderClassName
Gets the Persistence Provider Class Name
```java
public String getPersistenceProviderClassName() {
    return persistenceProviderClassName;
  }
```

## GetTransactionType
Gets the Transaction Type
```java
public PersistenceUnitTransactionType getTransactionType() {
    return transactionType;
  }
```

## GetJtaDataSource
Gets the Jta Data Source
```java
public DataSource getJtaDataSource() {
    return jtaDataSource;
  }
```

## GetNonJtaDataSource
Gets the non Jta Data Source
```java
public DataSource getNonJtaDataSource() {
    return nonJtaDataSource;
  }
```

## GetMappingFileNames
Gets the Mapping File Names
```java
public List<String> getMappingFileNames() {
    return mappingFileNames;
  }
```

## GetJarFileUrls
Gets tje Jar File Urls
```java
public List<URL> getJarFileUrls() {
    return new ArrayList<>();
  }
```

## GetPersistenceUnitRootUrl
Gets the Persistence Unit Root Url
```java
public URL getPersistenceUnitRootUrl() {
    return null;
  }
```

## GetManagedClassNames
Gets the Managed Class Names
```java
public List<String> getManagedClassNames() {
    return managedClassNames;
  }
```

## ExcludeUnlistedClasses
Gets the excluded Unlisted Classes
```java
public boolean excludeUnlistedClasses() {
    return false;
  }
```

## GetSharedCacheMode
Gets the Shared Cache Mode
```java
public SharedCacheMode getSharedCacheMode() {
    return null;
  }
```

## GetValidationMode
Gets the Validation Mode
```java
public ValidationMode getValidationMode() {
    return null;
  }
```

## GetProperties
Gets the properties of the persistence info.
```java
public Properties getProperties() {
    return properties;
  }
```

## GetPersistenceXMLSchemaVersion
Gets the Persistence XML Schema Version
```java
public String getPersistenceXMLSchemaVersion() {
    return null;
  }
```

## GetClassLoader
Gets the Class loader
```java
public ClassLoader getClassLoader() {
    return null;
  }
```

## AddTransformer
Adds a class transformer to transformers. 
```java
public void addTransformer(ClassTransformer transformer) {
    transformers.add(transformer);
  }
```

## GetNewTempClassLoader
Gets the New Temp ClassLoader
```java
public ClassLoader getNewTempClassLoader() {
    return null;
  }
```