# <img src="resources/jmaqslogo.jpg" height="32" width="32"> Manager Dictionary

## Overview
The Manager Dictionary contains methods to interact with the Driver manager.

[GetDriver](#GetDriver)  
[Put](#Put)  
[AddOrOverride](#AddOrOverride)  
[Remove](#Remove)  
[Clear](#Clear)

## GetDriver
Get the driver for the associated driver manager
```java
T driver = managerDicitionary.getDriver("test")
```

## Put
Add a manager
```java
 public void put(DriverManager driverManager) {
    this.put(driverManager.getClass().getName(), driverManager);
  }
```

## AddOrOverride
Add or replace a manager
```java
ManagerDictionary managerDictionary.putOrOverride(dm1, testManager);

public void putOrOverride(String key, DriverManager driverManager) {
    this.remove(key);
    this.put(key, driverManager);
  }
```

## Remove
Remove a driver manager
```java
boolean removed = this.remove(key);
```

## Clear
Clears the dictionary
```java
 for (Map.Entry<String, DriverManager> entry : this.entrySet()) {
       try {
         entry.getValue().close();
       } catch (Exception e) {
         e.printStackTrace();
       }
     }
```