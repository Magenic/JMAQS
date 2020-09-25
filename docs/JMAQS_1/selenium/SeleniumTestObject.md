# <img src="resources/jmaqslogo.jpg" height="32" width="32"> Selenium Test Object

## Overview
Selenium test context data

## GetWebDriver
Gets the Selenium web driver
```java
  public WebDriver getWebDriver() {
    return this.getWebManager().getWebDriver();
  }
```

## SetWebDriver
Sets the selenium webDriver
```java
public void setWebDriver(WebDriver driver) {
    this.getManagerStore().put(SeleniumDriverManager.class.getCanonicalName(),
        new SeleniumDriverManager((() -> driver), this));
  }
```
The override
```java
public void setWebDriver(Supplier<WebDriver> webDriverSupplier) {
    this.getManagerStore().put(SeleniumDriverManager.class.getCanonicalName(),
        new SeleniumDriverManager(webDriverSupplier, this));
  }
```

## GetWebManager
Gets the Selenium driver manager
```java
SeleniumDriverManager manager = this.getWebManager.getWebDriver();
```