# <img src="resources/jmaqslogo.jpg" height="32" width="32"> Accessibility Utilities

## Overview
The Accessibility Utilities class is a utility class for working with the Axe open source framework.

# Available methods
[CreateAxeHtmlReport](#CheckAccessibility)  
[CreateAxeHtmlReportFile](#CheckAccessibilityPasses)  
[CheckAccessibilityInapplicable](#CheckAccessibilityInapplicable)  
[CheckAccessibilityIncomplete](#CheckAccessibilityIncomplete)  
[CheckAccessibilityViolations](#CheckAccessibilityViolations)  
[CreateAccessibilityHtmlReport](#CreateAccessibilityHtmlReport)  
[GetAccessibilityReportPath](#GetAccessibilityReportPath)  

## CheckAccessibility
Runs the axe scan and logs results for all Result types.
```java
void checkAccessibility(SeleniumTestObject testObject, boolean throwOnViolation)
```

An example of calling this method:
```java
AccessibilityUtilities.checkAccessibility(getTestObject(), false);
```

## CheckAccessibilityPasses
Runs the axe scan and logs results for the passes Result type.  
```java
void checkAccessibilityPasses(WebDriver webDriver, Logger logger, MessageType loggingLevel)
```

An example of calling this method:
```java
AccessibilityUtilities.checkAccessibilityPasses(getTestObject().getWebDriver(), fileLogger, MessageType.SUCCESS);
```

## CheckAccessibilityInapplicable
Runs the axe scan and logs results for the inapplicable Result type.
```java
void checkAccessibilityInapplicable(WebDriver webDriver, Logger logger, MessageType loggingLevel, boolean throwOnInapplicable)
```

An example of calling this method:
```java
AccessibilityUtilities.checkAccessibilityInapplicable(getTestObject().getWebDriver(), fileLogger, MessageType.WARNING, false);
```

## CheckAccessibilityIncomplete
Runs the axe scan and logs results for the incomplete Result type.
```java
void checkAccessibilityIncomplete(WebDriver webDriver, Logger logger, MessageType loggingLevel, boolean throwOnIncomplete)
```

An example of calling this method:
```java
AccessibilityUtilities.checkAccessibilityIncomplete(getTestObject().getWebDriver(), fileLogger, MessageType.WARNING, false);
```

## CheckAccessibilityViolations
Runs the axe scan and logs results for the violations Result type.
```java
void checkAccessibilityViolations(WebDriver webDriver, Logger logger, MessageType loggingLevel, boolean throwOnViolation)
```

An example of calling this method:
```java
AccessibilityUtilities.checkAccessibilityViolations(getTestObject().getWebDriver(), fileLogger, MessageType.ERROR, false);
```

## CreateAccessibilityHtmlReport
Runs the axe accessibility scan and creates a detailed html report for accessibility improvements  
```java
void createAccessibilityHtmlReport(SeleniumTestObject testObject, boolean throwOnViolation)
```

An example of calling this method:
```java
AccessibilityUtilities.createAccessibilityHtmlReport(this.getTestObject(), false);
```

The type of Results can be dictated for the HTML report.
```java
void createAccessibilityHtmlReport(SeleniumTestObject testObject, boolean throwOnViolation, Set<ResultType> requestedResult)
```

An example of calling this method:
```java
AccessibilityUtilities.createAccessibilityHtmlReport(this.getTestObject(), false, EnumSet.of(ResultType.Violations));
```

Specific elements can be scanned as well.
```java
void createAccessibilityHtmlReport(SeleniumTestObject testObject, WebElement element, boolean throwOnViolation)
```

An example of calling this method:
```java
AccessibilityUtilities.createAccessibilityHtmlReport(this.getTestObject(), foodTable.getRawExistingElement(), false);
```

## GetAccessibilityReportPath
A method that creates a unique file name that is used for the HTML report name.  
The file logger file path is what is used in this method to create the report path  
This method is private and is used in the createAccessibilityHtmlReport method  
```java
String getAccessibilityReportPath(SeleniumTestObject testObject)
```

How the method is used:
```java
String report = getAccessibilityReportPath(testObject);
```