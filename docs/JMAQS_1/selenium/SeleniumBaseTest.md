# <img src="resources/jmaqslogo.jpg" height="32" width="32"> Base Selenium Test

## Overview
The BaseSeleniumTest class provides access to the SeleniumTestObject and SeleniumDriver.

# Available Methods
[GetWebDriver](#GetWebDriver)  
[SetWebDriver](#SetWebDriver)  
[GetBrowser](#GetBrowser)  
[BeforeLoggingTeardown](#BeforeLoggingTeardown)  
[CreateNewTestObject](#CreateNewTestObject)  

## GetWebDriver
Gets the web driver
```java
this.getWebDriver();
```

## SetWebDriver
Sets the web driver
```java
this.setWebDriver(this.getBrowser())
```

## GetBrowser
The default get web driver function.
```java
protected WebDriver getBrowser() throws Exception {
    // Returns the web driver
    return WebDriverFactory.getDefaultBrowser();
  }
```

## BeforeLoggingTeardown
Take a screenshot if needed and tear down the web driver.
```java
protected void beforeLoggingTeardown(ITestResult resultType) {
    // Try to take a screenshot
    try {
      if (this.getWebDriver() != null && resultType.getStatus() != ITestResult.SUCCESS
          && this.getLoggingEnabledSetting() != LoggingEnabled.NO) {
        SeleniumUtilities.captureScreenshot(getWebDriver(), getTestObject());
      }
    } catch (Exception e) {
      this.tryToLog(MessageType.WARNING, "Failed to get screen shot because: %s", e.getMessage());
    }
  }
```

## CreateNewTestObject
Create a Selenium test object.
```java
protected void createNewTestObject() {
    try {
      this.setTestObject(new SeleniumTestObject(this.getBrowser(), this.createLogger(),
          this.getFullyQualifiedTestClassName()));
    } catch (Exception e) {
      getLogger().logMessage(
          StringProcessor.safeFormatter("Test Object could not be created: %s", e.getMessage()));
    }
  }
```