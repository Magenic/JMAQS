# <img src="resources/jmaqslogo.jpg" height="32" width="32"> Database FAQ

## How do use a provider besides SQL, SQLite and PostgreSql
There are multiple ways to use a custom provider.

[Override the GetDataBaseConnection method](../database/DatabaseBaseTest.md)  
[Create your own DatabaseDriver](../database/DatabaseDriver.md)  
[Implement the IProvider Class](../database/Providers.md)  

## Why doesn't JMAQS directly support Oracle SQL anymore
The Oracle SQL library has a non-standard license, we are not confident it adheres to most user licensing standards.

## How Can to Connect to an Oracle SQL Database

### Adding the Oracle Database Connector
// TODO
### Setting Up the Connection Inline
```java
/**
* Check that we get back the state table.
*/
@Test
public void addInTest() {
    // Adding the connection inline
    this.getTestObject().overrideDatabaseConnection(() -> new OracleConnection(DatabaseConfig.getConnectionString()));

    var states = this.getDatabaseDriver().query("SELECT * FROM States").toArray();
    Assert.assertEquals(50, states.Count, "Expected 50 states.");
}
```

### Or Setting Up the Connection for All Tests In A Class
```java
public class DatabaseCustomUnitTests extends BaseDatabaseTest {
    /// <summary>
    /// Override the database connection
    /// </summary>
    /// <returns>The database connection</returns>
//    protected override IDbConnection GetDataBaseConnection()
//    {
//        return new OracleConnection(DatabaseConfig.GetConnectionString());
//    }
}
```