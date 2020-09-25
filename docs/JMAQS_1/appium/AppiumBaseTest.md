# <img src="resources/jmaqslogo.jpg" height="32" width="32"> Base Appium Test

## Overview
The BaseAppiumTest has methods that sets up the webdriver, gets the mobile driver, tears down the appium driver, and creates a new test object. 

# Available calls
[GetAppiumDriver](#GetAppiumDriver)  
[BeforeLoggingTeardown](#BeforeLoggingTeardown)  
[CreateNewTestObject](#CreateNewTestObject)  

## GetAppiumDriver
This method gets the default Appium/Mobile driver. 
```java
public AppiumDriver<WebElement> getAppiumDriver() {
    return this.getTestObject().getAppiumDriver();
  }
```

## BeforeLoggingTeardown
Takes a screen shot if needed and tear down the appium driver. It is called during teardown.
```java
this.beforeLoggingTeardown(resultType);
```

## CreateNewTestObject
This method creates a new Appium test object based on the mobile device.
```java
 protected void createNewTestObject() {
     this.setTestObject(new AppiumTestObject(this::getMobileDriver, this.createLogger(),
         this.getFullyQualifiedTestClassName()));
   }

this.CreateNewTestObject();
```