# <img src="resources/jmaqslogo.jpg" height="32" width="32"> Mobile Driver Manager

## Overview
The Mobile Driver Manager gets and closes the appium driver.

## GetMobileDriver
Get the Appium driver
```java
 public AppiumDriver<WebElement> getMobileDriver() {
    return (AppiumDriver<WebElement>) getBase();
  }

```

## Close
Cleanup the Appium driver
```java
 public void close() {
    // If we never created the driver we don't have any cleanup to do  
    if (!this.isDriverInitialized()) {
      return;
    }

    try {
      AppiumDriver<WebElement> driver = this.getMobileDriver();
      driver.close();
      driver.quit();
    } catch (Exception e) {
      this.getLogger().logMessage(MessageType.ERROR, StringProcessor
          .safeFormatter("Failed to close mobile driver because: %s", e.getMessage()));
    }

    this.baseDriver = null;
  }
```