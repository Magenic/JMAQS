# <img src="resources/jmaqslogo.jpg" height="32" width="32"> Base Appium Test

## Overview
The Base Appium Test has methods that sets up the web driver, gets the mobile driver, tears down the appium driver, and creates a new test object. 

# Available calls
[GetAppiumDriver](#GetAppiumDriver)  
[SetAppiumDriver](#SetAppiumDriver)  
[GetMobileDriver](#GetMobileDriver)  
[BeforeLoggingTeardown](#BeforeLoggingTeardown)  
[CreateNewTestObject](#CreateNewTestObject)  

## GetAppiumDriver
This method gets the default Appium driver
```java
public AppiumDriver<WebElement> getAppiumDriver() {
    return this.getTestObject().getAppiumDriver();
  }
```

## SetAppiumDriver
This method sets the default Appium driver
```java
public void setAppiumDriver(AppiumDriver<WebElement> mobileDriver) {
    this.getTestObject().setAppiumDriver(mobileDriver);
  }
```

## GetMobileDriver
Gets new mobile driver
```java
protected AppiumDriver<WebElement> getMobileDriver() {
    return AppiumDriverFactory.getDefaultMobileDriver();
  }
```

## BeforeLoggingTeardown
Takes a screenshot if needed and tear down the appium driver. It is called during teardown.
```java
this.beforeLoggingTeardown(resultType);
```

## CreateNewTestObject
This method creates a new Appium test object based on the mobile device.
```java
 protected override void createNewTestObject(){
    this.TestObject = new AppiumTestObject(() => this.getMobileDevice(), this.createLogger(), this.getFullyQualifiedTestClassName());
}

this.createNewTestObject();
```