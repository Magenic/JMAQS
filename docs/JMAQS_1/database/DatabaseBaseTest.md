# <img src="resources/jmaqslogo.jpg" height="32" width="32"> BaseDatabaseTest

## Overview
JMAQS provides support for testing databases.

## BaseDatabaseTest
BaseDatabaseTest is an abstract test class you can extend.  Extending the class allows you to automatically use MAQS's database testing capabilities.
```java
@Test
public class DatabaseTest extends BaseDatabaseTest {}
```

## DatabaseDriver
The DatabaseDriver is an object that allows you to interact with databases.  
This driver wraps common database interactions, making database testing relatively easy.  
The driver is also thread safe, which means you can run multiple database tests in parallel.  
*Information, such as connection strings are pulled from the JMAQS configuration.
```java
List<T> table = this.getDatabaseDriver().query("SELECT * FROM information_schema.tables").ToList();
```
## Log
There is also logger (also thread safe) that can be used to add log message to your log.
```java
this.getLogger().logMessage("I am testing with MAQS");
```
## TestObject
The TestObject can be thought of as your test context.  It holds all the JMAQS test execution related data.  
This includes the database driver, logger, soft asserts, performance timers, plus more.
```java
List<T> table = this.getDatabaseDriver().query("SELECT * FROM information_schema.tables").toArray();
this.getTestObject().getLogger().logMessage("I am testing with MAQS");
```
*Notes:*
* *Most of the test object objects are already accessible on the test lever. For example **this.Log** and **this.TestObject.Log** both access the same logger.*
* *You seldom what you use the test object directly.  It is usually only used when you want to share your test MAQS context with another piece of code*

## Sample code
```java
import com.magenic.jmaqs.database.BaseDatabaseTest;
import com.magenic.jmaqs.utilites.helper.logger;
import com.magenic.jmaqs.utilities.helper.TestCategories;
import org.testng.Assert;
import org.testng.annotations.Test;

    /**
    * DatabaseTest test class
    **/
    public class DatabaseTest extends BaseDatabaseTest {
        /**
        * Check that we get back the state table
        **/
        @Test(groups = TestCategories.DATABASE)
        public void VerifyStateTableExists() {
            this.getTestObject().getLogger().logMessage(MessageType.INFORMATION, "Before query");
            List<T> table = this.getDatabaseDriver().query("SELECT * FROM information_schema.tables").toArray();
            this.getLogger().logMessage(MessageType.INFORMATION, "After query");
            
            Assert.IsTrue(table.contains("States"));
        }
    }
```