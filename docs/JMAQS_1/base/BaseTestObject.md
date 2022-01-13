# <img src="resources/jmaqslogo.jpg" height="32" width="32"> Base Test Object

## Overview
Takes care of the base test context data.

[GetClosed](#GetClosed)  
[Log](#Log)  
[PerfTimerCollection](#PerfTimerCollection)  
[Values](#Values)   
[Objects](#GetObjects)  
[SetManagerStore](#SetManagerStore)  
[AddAssociatedFile](#AddAssociatedFile)  
[SetValue](#SetValue)  
[SetObject](#SetObject) 
[AddDriverManager](#AddDriverManager)  
[RemoveAssociatedFile](#RemoveAssociatedFile)  
[GetArrayOfAssociatedFiles](#GetArrayOfAssociatedFiles)  
[ContainsAssociatedFile](#ContainsAssociatedFile)  
[OverrideDriverManager](#OverrideDriverManager)  
[Close](#Close)

## GetClosed
Checks if the object has been closed
```java
boolean isClosed = this.getClosed();
```

## Log
### GetLog
Gets the logger
```java
Logger log = this.getLog();
```

### SetLog
Sets the logger
```java
this.getTestObject.setLog(logger);
this.log = logger;
```

## PerfTimerCollection
### GetPerfTimerCollection
Gets the performance timer collection
```java
PerfTimerCollection collection = testObject.getPerfTimerCollection();
```

### SetPerfTimerCollection
Sets the performance timer collection
```java
BaseTestObject testObject = this.getBaseTestObject();
testObject.setPerfTimerCollection(perfTimerCollection);
```

## GetFullyQualifiedTestName
Gets the test name
```java
testObject.getFullyQualifiedTestName();
```

## Values
### GetValues
Gets the Concurrent Hash Map of string key value pairs
```java
BaseTestObject testObject = this.getTestObject();
ConcurrentMap<String, String> values = testObject.getValues();
```

### SetValues
Sets the Concurrent Hash Map of string key and object value pairs
```java
BaseTestObject testObject = this.getTestObject();
testObject.SetValues(hash map)
this.values = values;
```

## Objects
### GetObjects
Gets the Concurrent Hash Map of string key and object value pairs
```java
BaseTestObject testObject = this.getTestObject();
ConcurrentMap<String, String> values = testObject.getObjects();
```

### SetObjects
Sets the Concurrent Hash Map of string key and object value pairs
```java
BaseTestObject testObject = this.getTestObject();
testObject.SetValues(hash map)
this.values = values;
```

## Manager
### GetManagerStore
Gets the Concurrent Hash Map of string key and driver value pairs
```java
public ManagerDictionary getManagerStore() {
    return this.managerStore;
  }
```

### SetManagerStore
Sets the Concurrent Hash Map of string key and driver value pairs
```java
 protected void setManagerStore(ManagerDictionary managerStore) {
    this.managerStore = managerStore;
  }
```

## SetValue
Sets a string value, will replace if the key already exists
```java
 public void setValue(String key, String value) {
    if (this.values.containsKey(key)) {
      this.values.replace(key, value);
    } else {
      this.values.put(key, value);
    }
  }
```

## SetObject
Sets an object value, will replace if the key already exists
```java
public void setObject(String key, Object value) {
    if (this.objects.containsKey(key)) {
      this.objects.replace(key, value);
    } else {
      this.objects.put(key, value);
    }
  }
```

## AddDriverManager
Add driver manager
```java
public <T extends DriverManager> void addDriverManager(T driverManager) {
    this.addDriverManager(driverManager, false);
  }

 public <T extends DriverManager> void addDriverManager(T driverManager,
      boolean overrideIfExists) {
    if (overrideIfExists) {
      this.overrideDriverManager(driverManager.getClass().getTypeName(), driverManager);
    } else {
      this.addDriverManager(driverManager.getClass().getTypeName(), driverManager);
    }
  }

 public void addDriverManager(String key, DriverManager driverManager) {
    this.managerStore.put(key, driverManager);
  }
```

## OverrideDriverManager
Override a specific driver
```java
this.overrideDriverManager(driverManager.getClass().getTypeName(), driverManager);

 public void overrideDriverManager(String key, DriverManager driverManager) {
    if (this.managerStore.containsKey(key)) {
      this.managerStore.putOrOverride(key, driverManager);
    } else {
      this.managerStore.put(key, driverManager);
    }
  }
```

## AssociatedFile
### AddAssociatedFile
Checks if the file exists and if so attempts to add it to the associated files set
```java
testObject.addAssociatedFile(path);

public boolean addAssociatedFile(String path) {
    if (new File(path).exists()) {
      return this.associatedFiles.add(path);
    }

    return false;
  }
```

### RemoveAssociatedFile
Removes the file path from the associated file set
```java
boolean removed = testObject.removeAssociatedFile(logPath);
```

## GetArrayOfAssociatedFiles
Get array of associated files string [ ]
```java
String[] assocFiles = testObject.getArrayOfAssociatedFiles();
```

## ContainsAssociatedFile
Contains associated file boolean
```java
boolean contains = testObject.containsAssociatedFile(filePath);
```

## Close
Dispose the driver store
```java
this.driver.close();
```