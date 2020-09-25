# <img src="resources/jmaqslogo.jpg" height="32" width="32"> Base Test
The BaseTest class provides access to the TestObject and DriverManager.

## Overview
The Base for tests without a defined system under test

# Available calls
[GetPerfTimerCollection](#GetPerfTimerCollection)  
[SetPerfTimerCollection](#SetPerfTimerCollection)  
[GetLogger](#GetLogger)  
[setLogger](#setLogger)  
[GetLoggingEnabledSetting](#GetLoggingEnabledSetting)  
[SetLoggingEnabledSetting](#SetLoggingEnabledSetting)  
[GetLoggedExceptions](#GetLoggedExceptions)  
[SetLoggedExceptions](#SetLoggedExceptions)  
[GetManagerStore](#GetManagerStore) 
[GetTestContext](#GetTestContext)  
[SetTestContext](#SetTestContext)  
[GetTestObject](#GetTestObject)  
[SetTestObject](#SetTestObject)  
[Setup](#Setup) 
[Teardown](#Teardown) 
[SetTestResult](#SetTestResult)
[BeforeLoggingTeardown](#BeforeLoggingTeardown)  
[CreateLogger](#CreateLogger) 
[GetResultType](#GetResultType)  
[GetResultText](#GetResultText) 
[GetFullyQualifiedTestClassName](#GetFullyQualifiedTestClassName)
[TryToLog](#TryToLog)  
[LogVerbose](#LogVerbose)
[CreateNewTestObject](#CreateNewTestObject)  


## GetPerfTimerCollection
Gets the performance timer collection for a test
```java
 PerfTimerCollection p = this.perfTimerCollection;
```

## SetPerfTimerCollection
Sets the performance timer collection for a test
```java
 PerfTimerCollection p = this.perfTimerCollection;
```

## GetLogger
Gets the testing object logger.
```java
Logger log = this.getTestObject().getLog();
```

## SetLogger
Sets the testing object logger.
```java
this.getTestObject().setLog(log);
```

## GetLoggingEnabledSetting
Gets the logging enable flag
```java
LoggingEnabled logEnabled = LoggingConfig.getLoggingEnabledSetting();
```

## SetLoggingEnabledSetting
Sets the logging enable flag
```java
setLoggingEnabled(setting);
```

## GetLoggedExceptions
Gets the logged exceptions.
```java
List<String> exceptions = this.getLoggedExceptions();
```

## SetLoggedExceptions
Sets the logged exceptions.
```java
setLoggedExceptions(loggedExceptionList);
```

## GetManagerStore
Gets the driver store of the Manager Dictionary.
```java
this.getManagerStore();
```

## GetTestContext
Gets the TestNG Test Context
```java
BaseTest tester = this.getBaseTest();
tester.setTestContext(this.getTestContext());
```

## SetTestContext
Sets the TestNG Test Context
```java
final ITestContext testContext = this.getTestContext();
testContext.setAttribute("testName", "SetTestContext");
this.setTestContext(testContext);
```

## GetTestObject
Gets the test object
```java
Assert.IsNotNull(this.getTestObject());
```

## SetTestObject
Sets the test object
```java
this.setTestObject(testObject);
```

## Setup
Setup before a test
```java
public void setup(Method method, ITestContext testContext) {
    this.testContextInstance = testContext;

    // Get the Fully Qualified Test Class Name and set it in the object
    String testName = method.getDeclaringClass() + "." + method.getName();
    testName = testName.replaceFirst("class ", "");
    this.fullyQualifiedTestClassName.set(testName);

    this.createNewTestObject();
  }
```

## Teardown
Tear down after a test, this is done as clean up after a test
```java
@AfterMethod(alwaysRun = true)
  public void teardown() {}
```

## SetTestResult
Set the test result after each test execution
```java
@AfterMethod(alwaysRun = true)
  public void setTestResult(ITestResult testResult) {
    this.testContextInstance = testResult.getTestContext();
    this.testResult = testResult;
  }
```

## BeforeLoggingTeardown
Steps to do before logging teardown results - If not override nothing is done before logging the results
```java
this.beforeLoggingTeardown(resultType);
```

## CreateLogger
Create a logger
```java
Logger newLogger = this.createLogger();
```

# GetResultType
Get the type of test result
```java
TestResultType resultType = this.getResultType();
```

## GetResultText
Get the test result type as text
```Java
String resultText = this.getResultText()
```

## GetFullyQualifiedTestClassName
Get the fully qualified test name
```java
String className = this.getFullyQualifiedTestClassName(),
```

## TryToLog
Try to log a message - Do not fail if the message is not logged
```java
this.tryToLog(MessageType.WARNING, "Failed to get screen shot because: %s", e.getMessage());
```

## LogVerbose
Log a verbose message and include the automation specific call stack data
```java
this.logVerbose("This is a test to log verbose.");
```

## CreateNewTestObject
Create a Selenium test object
```java
this.createNewTestObject()
```
