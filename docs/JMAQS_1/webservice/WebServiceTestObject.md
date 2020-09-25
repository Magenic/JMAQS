# <img src="resources/jmaqslogo.jpg" height="32" width="32"> Web Service Test Object

## Overview
Takes care of the base test context data.

[GetWebServiceDriver](#GetWebServiceDriver)  
[SetWebServiceDriver](#SetWebServiceDriver)  
[WebServiceManager](#WebServiceManager)  

## GetWebServiceDriver
Gets the web service driver
```java
this.getTestObject().getWebServiceDriver();
```

## SetWebServiceDriver
Sets the web service driver
```java
testObject.setWebServiceDriver(serviceDriver);

testObject.setWebServiceDriver(CloseableHttpClient client);

testObject.setWebServiceDriver(getCloseableHttpClientSupplier());
```

## WebServiceManager
Gets the web service driver manager
```java
this.getTestObject().getWebServiceDriverManager()
```
