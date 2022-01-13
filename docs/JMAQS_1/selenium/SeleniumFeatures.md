# <img src="resources/jmaqslogo.jpg" height="32" width="32"> Selenium Features

## Overview
JMAQS provides support for testing web applications.  

## BaseSeleniumTest
BaseSeleniumTest is an abstract test class you can extend.  Extending the class allows you to automatically use JMAQS' web application testing capabilities.
```java
public class MySeleniumTest extends BaseSeleniumTest {}
```

## WebDriver
The WebDriver is an object that allows you to interact with web pages. JMAQS extends the Selenium WebDriver, but does wrap it. This means all native Selenium functionality is available via the WebDriver. Also note that the driver is thread safe, which means you can run multiple web tests in parallel.   
*Notes:*
* Some browsers, such as IE and Edge, cannot be run in parallel on the same machine.  
* When logging is enabled JMAQS automatically creates EventHandlers instead of a standard WebDrivers. So be warned that your WebDriver may actually be EventHandlers.
* For more info on the Selenium driver you can visit the Selenium GitHub page: https://github.com/SeleniumHQ/selenium/tree/master/java

## Configuration 
Information, such as the type of browser and website base url are pulled from the SeleniumMaqs section your configuration.
```java
 this.getWebDriver().navigate().to(SeleniumConfig.GetWebSiteBase());
```
## Log
There is also logger (also thread safe) that can be used to add log message to your log.
```java
this.getLog().logMessage("I am testing with JMAQS");
```
## TestObject
The TestObject can be thought of as your test context.  
It holds all the JMAQS test execution related data.  
This includes the Selenium driver, logger, soft asserts, performance timers, plus more.

```java
this.getTestObject().getWebDriver().navigate().to("http://magenicautomation.azurewebsites.net/");
this.getTestObject().getLog().logMessage("I am testing with JMAQS");
```
*Notes:*  
* *Most of the test object objects are already accessible on the test lever. For example **this.Log** and **this.TestObject.Log** both access the same logger.*
* *You seldom what you use the test object directly.  It is usually only used when you want to share your test JMAQS context with another piece of code*

## Sample code
```java
package com.magenic.jmaqs.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
* SeleniumTest test class
*/
public class SeleniumTest extends BaseSeleniumTest {
    /**
    * Asset the home page title.
    */
    @Test
    public void HomePageTitle() {
        this.getWebDriver().navigate().to(SeleniumConfig.GetWebSiteBase());
        this.getLog().logMessage("I am testing with JMAQS");
        Assert.assertEquals("HOME", this.getWebDriver.getTitle());
    }
}
```