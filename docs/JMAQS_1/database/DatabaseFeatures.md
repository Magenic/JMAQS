# <img src="resources/jmaqslogo.jpg" height="32" width="32"> Database Features

## Overview
JMAQS provides support for testing database applications.  	

## BaseTest
BaseDatabaseTest is an abstract test class you can extend.  
Extending the class allows you to automatically use JMAQS's database testing capabilities.
```java
public class MyDatabaseTests extends BaseDatabaseTest
```

## Driver
The DatabaseDriver is an object that allows you to interact with database services.  
This driver wraps common database interactions, making testing relatively easy.  
The driver is also thread safe, which means you can run multiple database tests in parallel.  
*Information, such as the OS version is pulled from the JMAQS configuration.

## DriverManager
Driver that manages the driver manager.

## Log
There is also logger (also thread safe) the can be used to add log message to your log.
```java
this.getLog().logMessage("I am testing with JMAQS");
```

## TestObject
The TestObject can be thought of as your test context, It holds all the JMAQS test execution related data.  
This includes the database driver, logger, soft asserts, performance timers, plus more.

*Notes:*  
* *Most of the test object objects are already accessible on the test level. For example **this.getLog()** and **this.getTestObject().getLog()** both access the same logger.*
* *You seldom use the test object directly. It is usually only used when you want to share your test JMAQS context with another piece of code*

## Utilities
Stores functions for Capturing screenshots, saving page sources, waiting with the database driver, and killing the driver.

## Config
Stores methods for interacting with the App.config

## EventFiringDriver
Wrap basic firing database interactions

## Providers
Manages the provider connections.

## ConnectionFactory
Gets a database connection based on configuration values

## Sample code
```java
import com.magenic.jmaqs.base.BaseGenericTest;
import com.magenic.jmaqs.database.entities.StatesEntity;
import com.magenic.jmaqs.utilities.helper.TestCategories;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DatabaseDriverUnitTest extends BaseGenericTest {
{
    @Test(groups = TestCategories.DATABASE)
      public void testQuery() {
        DatabaseDriver openConnection = ConnectionFactory.getOpenConnection();
        List results = openConnection.query("SELECT * FROM information_schema.tables");
        Assert.assertTrue(results.stream().anyMatch(n -> ((Object[]) n)[2].equals("States")));
      }
}
```