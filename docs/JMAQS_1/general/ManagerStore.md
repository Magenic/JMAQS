# <img src="resources/maqslogo.ico" height="32" width="32"> Manager Dictionary

## Overview
The manager dictionary is a collection of test drivers (such as Selenium WebDriver, Database driver, Web service driver, etc.) that can be used in any JMAQS test.  
The store allows you to add use multiple drivers in the same test.  
This means you can have one test with any number of drive, you can also have multiple drivers of the same time.
Not only does the driver store allow you to multiple drivers, but it also automatically cleans up all the managed drivers when your test is done.  

# Available methods
[Add](#Add)  
[AddOrOverride](#AddOrOverride)  
[Get](#Get)

# See it in practice
[Example](#Example)

##  Add
Add a new web driver to the store.  
*Will throw an exception if a default you try to add a duplicate default or named driver.*

### Add default
```java
ManagerDictionary managerDictionary = new ManagerDictionary();
managerDictionary.put(new SeleniumDriverManager(() -> WebDriverFactory.getBrowserWithDefaultConfiguration(BrowserType.Chrome), this.getTestObject()));
```

##  AddOrOverride
Add or override a new web driver in the store.  
*Will cleanup and dispose of any drivers that are being replaced.*

### Add or override default
```java
this.getManagerStore().putOrOverride(new DatabaseDriverManager(() -> DatabaseConfig.getOpenConnection()));
```

### Add or override named
```java
this.getManagerStore().putOrOverride("NAMEDB", new DatabaseDriverManager(() -> DatabaseConfig.getOpenConnection()));
```

##  Get
Get the driver from the store
### Default driver
```java
// All different ways of getting the same WebDriver
WebDriver alsoDefaultDriver = this.getTestObject().getWebDriver();
WebDriver alsoAlsoDefaultDriver = this.getWebDriver();
``` 
*The this.WebDriver is only available when if you are creating base Selenium tests.*
### Named driver
```java
WebDriver selenNamed = this.getManagerStore().getDriver("NAMESEL");

DatabaseDriver dbNamed = this.getManagerStore().getDriver("NAMEDB");
```

# Example
```java
package com.magenic.jmaqs.selenium.unittestpagemodel;

import com.magenic.jmaqs.database.DatabaseConfig;
import com.magenic.jmaqs.database.DatabaseDriver;
import com.magenic.jmaqs.database.DatabaseDriverManager;
import com.magenic.jmaqs.selenium.BaseSeleniumTest;
import com.magenic.jmaqs.selenium.SeleniumConfig;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import com.magenic.jmaqs.

public class SeleniumTest extends BaseSeleniumTest {
  
  @BeforeTest
  public void setUpStore() {
    // Add database driver
    this.getManagerStore().put(new DatabaseDriverManager(() -> DatabaseConfig.getOpenConnection());

    // Add named WebService driver
    this.getManagerStore().putOrOverride("NAMESEL", new DatabaseDriverManager(() -> DatabaseConfig.getOpenConnection());
  }
  
  @Test
  public void compoundTest() {
    this.getWebDriver().navigate().to(SeleniumConfig.getWebSiteBase());

    // Get the other named driver from the store
    WebDriver namedWebDriver = this.getManagerStore().getDriver("NAMESEL");
    namedWebDriver.navigate().to(SeleniumConfig.getWebSiteBase());

    Assert.assertEquals(this.getWebDriver().getTitle(), namedWebDriver.getTitle(), "Expect page to have the same title");

    // Get the default database driver from the store
    DatabaseDriver databaseDriver = this.getTestObject().getManagerStore().getDatabaseDriver();

    String query = "UPDATE States SET WasRun = 'Yes' WHERE WasRun = 'No'";
    int result = databaseDriver.execute(query);

    Assert.assertEquals(1, result, "Expected 1 update.");
  } 
}
```