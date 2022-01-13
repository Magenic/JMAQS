# <img src="resources/jmaqslogo.jpg" height="32" width="32"> Logger

## Overview
The Logger captures test execution information.   
By default, log files end up in the "log" folder.

###  Types of Loggers

 - Console Logger
 - File Logger
    - File (.txt)
    - HTML (.html)

###  Console Logger
Writes test execution logging information to the console

###  File Logger
Writes test execution logging information as a .txt file   

#### Examples
```
CompositeUnitTests.Base.CanRunTest - 2018-08-07-02-01-28-3500.txt 
```
###  HTML Logger
Writes test execution logging information as a html file

#### Examples
```
CompositeUnitTests.Base.CanRunTest - 2018-08-07-02-01-28-3500.html 
```

## How to Use Logger

### Logging with MessageTypes
##### Written As

```java
    public void TestWithLogging() {
        this.getlogger().logMessage(MessageType.VERBOSE, "Verbose logging message");
        this.getlogger().logMessage(MessageType.INFORMATION, "Information logging message");
        this.getlogger().logMessage(MessageType.GENERIC, "Generic logging message");
        this.getlogger().logMessage(MessageType.SUCCESS, "Success logging message");
        this.getlogger().logMessage(MessageType.WARNING, "Warning logging message");
        this.getlogger().logMessage(MessageType.ERROR, "Error logging message"); 
    }
```

### Logging without MessageTypes
Messages that don't provide MessageTypes are categorized as generic messages.  
##### Written As

```java
    public void TestWithLogging() {
        this.getlogger().logMessage("Generic massage"); 
    }
```

## Changing Logging Level
Ability to dynamically change logging level at runtime.
##### Written As

```java
    // Change your logging level
    this.getlogger().setLoggingLevel(MessageType.WARNING);
    this.getlogger().logMessage(MessageType.GENERIC, "Will not be logged");
```
## Suspend and Resume Logging
Ability to Suspend and Resume Logging.
##### Written As

```java
    this.getlogger().logMessage(MessageType.ERROR, "Logged"); 
    this.getlogger().suspendLogging();
    this.getlogger().logMessage(MessageType.ERROR, "Not Logged"); 
    this.getlogger().continueLogging(); 
    this.getlogger().logMessage(MessageType.ERROR, "Logged"); 
```