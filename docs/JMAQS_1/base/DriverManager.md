# <img src="resources/jmaqslogo.jpg" height="32" width="32"> Driver Manager

## Overview
The DriverManager has overreach of the BaseTestObject and the BaseDriver.

[Logger](#Logger)  
[BaseDriver](#BaseDriver)  
[GetDriver](#GetDriver)  
[IsDriverInitialized](#IsDriverInitialized)  
[GetBase](#GetBase)  

## Logger
Gets the testing object
```java
Logger log = this.getLogger;
```

## BaseDriver
### GetBaseDriver
Gets the underlying driver, like the web driver or database connection driver
 ```java
 this.baseDriver = this.getDriverSupplier.get();
 ```

### SetBaseDriver
Sets the underlying driver, like the web driver or database connection driver
 ```java
this.baseDriver = baseDriver;
 ```

## GetDriver
```java
this.BaseDriver = this.getBase();
```

## IsDriverInitialized
Check if the underlying driver has been initialized
```java
boolean initialiized = this.isDriverIntialized();
```

## GetBase
Get the underlying driver
```java
this.baseDriver = this.getDriverSupplier.get();
```