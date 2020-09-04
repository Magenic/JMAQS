# <img src="resources/jmaqslogo.jpg" height="32" width="32"> UIWaitFactory

## Overview
Factory class that is used for creating and maintaining a thread-safe collection of wait drivers.

## Available Methods
[GetWaitDriver](#GetWaitDriver)  
[SetWaitDriver](#SetWaitDriver)  
[RemoveWaitDriver](#RemoveWaitDriver)  
[GetLowLevelDriver](#GetLowLevelDriver)  

## GetWaitDriver
Gets the UIWait object from the wait collection.
<br> If none exists, one is created and cached using the driver provided.
```java
WebElement element = UIWaitFactory.getWaitDriver(webDriver).waitForClickableElement(by);
```

## SetWaitDriver
Adds the waitDriver to the wait collection.
```java
UIWait waitDriver = new UIWait(this.getWebDriver());
UIWaitFactory.setWaitDriver(this.getWebDriver(), waitDriver);
```

## RemoveWaitDriver
Removes the waitDriver from the collection.
```java
UIWaitFactory.removeWaitDriver(this.getWebDriver());
```

## GetLowLevelDriver
Gets the low level driver.
```java
 WebDriver baseDriver = getLowLevelDriver(searchContext);
```