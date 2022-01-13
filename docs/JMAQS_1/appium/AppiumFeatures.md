# <img src="resources/jmaqslogo.jpg" height="32" width="32"> Appium Features

## Overview
JMAQS provides support for testing mobile applications.  	

## BaseTest
Base Appium Test is an abstract test class you can extend.  
Extending the class allows you to automatically use JMAQS' web service testing capabilities.
```java
public class MyAppiumTests extends BaseAppiumTest {}
```

## Driver
The AppiumDriver is an object that allows you to interact with appium services.
This driver wraps common web service interactions, making appium testing relatively easy.
The driver is also thread safe, which means you can run multiple appium tests in parallel.
*Information, such as the OS version is pulled from the JMAQS configuration.
```java
AppiumDriver<WebElement> driver = AppiumDriverFactory.getDefaultMobileDriver();
```

## DriverManager
Driver that manages the driver manager.

## Log
There is also logger (also thread safe) that can be used to add log message to your log.
```java
this.getLogger().logMessage("I am testing with JMAQS");
```

## TestObject
The TestObject can be thought of as your test context.
It holds all the JMAQS test execution related data.
This includes the web service driver, logger, soft asserts, performance timers, plus more.

```java
this.getWebDriver().navigate().to("http://magenicautomation.azurewebsites.net/");
this.getLogger().logMessage("I am testing with JMAQS");
```
*Notes:*  
* *Most of the test object objects are already accessible on the test level. For example **this.getLog()** and **this.getTestObject.getLog()** both access the same logger.*
* *You seldom use the test object directly. It is usually only used when you want to share your test JMAQS context with another piece of code*

## Utilities
Stores the functions for Capturing screenshots, saving page sources, waiting with the web driver, and killing the driver.

## Config
Stores methods for interacting with the config.xml

## Sample code
```java
package com.magenic.jmaqs.appium;

import com.magenic.jmaqs.utilities.helper.TestCategories;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * The type Base appium test.
 */
public class BaseAppiumTestUnitTest extends BaseAppiumTest {
/**
   * Test get appium driver.
   */
  @Test(groups = TestCategories.APPIUM)
  public void MobileDeviceTest() {
      PageModel page = new PageModel(this.getTestObject());
      page.OpenPage();
    }   
}
```