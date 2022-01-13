# <img src="resources/jmaqslogo.jpg" height="32" width="32"> Base Web Service Test

## Overview
The Base Web Service Test class provides access to the WebService TestObject and Web Service Driver.

# Available calls
[GetWebServiceDriver](#GetWebServiceDriver)  
[SetWebServiceDriver](#SetWebServiceDriver)  
[GetWebServiceClient](#GetWebServiceClient)  
[CreateNewTestObject](#CreateNewTestObject)  

## GetWebServiceDriver
Gets the web service driver
```java
public WebServiceDriver getWebServiceDriver() {
    return this.getTestObject().getWebServiceDriver();
  }
```

## SetWebServiceDriver
Sets the web service driver
```java
public void setWebServiceDriver(WebServiceDriver webServiceDriver) {
    this.getTestObject().setWebServiceDriver(webServiceDriver);
  }
```

## GetWebServiceClient
Gets the Web Service client
```java
protected WebServiceDriver getWebServiceClient() throws URISyntaxException {
    return new WebServiceDriver(WebServiceConfig.getWebServiceUri());
  }
```

## CreateNewTestObject
Create a web service test object
```java
 protected void createNewTestObject() {
    Logger logger = this.createLogger();
    try {

      WebServiceTestObject webServiceTestObject = new WebServiceTestObject(
          this.getWebServiceClient(), logger, this.getFullyQualifiedTestClassName());
      this.setTestObject(webServiceTestObject);
    } catch (URISyntaxException e) {
      getLogger().logMessage(
          StringProcessor.safeFormatter("Test Object could not be created: %s", e.getMessage()));
    }
  }
```