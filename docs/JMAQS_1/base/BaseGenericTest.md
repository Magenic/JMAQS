# <img src="resources/jmaqslogo.jpg" height="32" width="32"> Base Generic Test

## Overview
Works with creating test objects and logging teardown for tests.

[BeforeLoggingTeardown](#BeforeLoggingTeardown)  
[CreateNewTestObject](#CreateNewTestObject)  

## BeforeLoggingTeardown
Override to deal with logging before the tear down
```java
this.beforeLoggingTeardown(testResult);
```

## CreateNewTestObject
Creates a new test object
```java
this.createNewTestObject();
```
