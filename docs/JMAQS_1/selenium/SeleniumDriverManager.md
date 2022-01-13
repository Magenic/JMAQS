# <img src="resources/jmaqslogo.jpg" height="32" width="32"> SeleniumDriverManager

## Overview
Manages the Selenium Driver.

## Available Methods
[Close](#Close)  
[GetWebDriver](#GetWebDriver)  
[LogVerbose](#LogVerbose)   
[LoggingStartUp](#LoggingStartUp)  


## Close
Closes the WebDriver
#### Example
```java
this.close();
```

## GetWebDriver
Get the web driver.
```java
WebDriver driver = this.getWebDriver();
```

## LogVerbose
Log a verbose message and include the automation specific call stack data.
```java
seleniumDriverManager.logVerbose("Logging verbose messaging");
```

## LoggingStartUp
Logging startup.
```java
this.loggingStartup(tempDriver);
```
