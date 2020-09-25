# <img src="resources/jmaqslogo.jpg" height="32" width="32"> Base Extendable Test

## Overview
The Base Extendable Test is the base code for classes that work with test objects (web drivers, Database connections).

[GetTestObject](#GetTestObject)  
[SetTestObject](#SetTestObject)  
[Setup](#Setup)

## GetTestObject
This method gets the test object.
```java
public T getTestObject() {
    return (T) super.getTestObject();
  }
```

## SetTestObject
This method sets the test object.
```java
public void setTestObject(BaseTestObject baseTestObject) {
    if (this.baseTestObjects.putIfAbsent(this.getFullyQualifiedTestClassName(), baseTestObject)
        == null) {
      this.baseTestObjects.replace(this.getFullyQualifiedTestClassName(), baseTestObject);
    }
  }
```

## Setup
The setup before a test
```java
public void setup(Method method, ITestContext testContext) {
    super.setup(method, testContext);
  }
```