# <img src="resources/jmaqslogo.jpg" height="32" width="32"> Web Service Driver Manager

## Overview
The WebService Driver Manager has overreach of the Base Driver Manager.

[GetWebServiceDriver](#GetWebServiceDriver) 
[OverrideDriver](#OverrideDriver)  
[Close](#Close)  

## GetWebServiceDriver
Get the http driver
 ```java
WebServiceDriver = getWebServiceDriver();
 ```

## OverrideDriver
Overrides the Web Service Driver
```java
WebServiceDriver webServiceDriver = driverManager.overrideDriver(null);
```

## Close
Close Method sets Base Driver to Null.
```java
this.setBaseDriver(null)
```
