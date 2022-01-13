# <img src="resources/jmaqslogo.jpg" height="32" width="32"> HTML Reporter

## Overview
The HTML Reporter class is a utility class for creating an HTML report for accessibility scans.

# Available methods
[CreateAxeHtmlReport](#CreateAxeHtmlReport)  
[CreateAxeHtmlReportFile](#CreateAxeHtmlReportFile)  

## CreateAxeHtmlReport
Creates a detailed html report for accessibility improvements.

Here is the basic method that takes in a web driver and destination:
```java
createAxeHtmlReport(WebDriver webDriver, String destination)
```

Here is how it is implemented:
```java
HtmlReporter.createAxeHtmlReport(this.getWebDriver(), path);
```

For the HTML report, you can also specify which Report Types should be included.  
This is done by using EnumSet.of().  
All report types are included as default.  
```java
createAxeHtmlReport(WebDriver webDriver, String destination, Set<ResultType> requestedResults)
```

Example of using this method:
```java
HtmlReporter.createAxeHtmlReport(this.getWebDriver(), path, EnumSet.of(ResultType.Violations));
HtmlReporter.createAxeHtmlReport(this.getWebDriver(), path, EnumSet.of(ResultType.Passes, ResultType.Inapplicable, ResultType.Violations));
```

There is also functionality to scan only a web element.
```java
createAxeHtmlReport(WebDriver webDriver, WebElement element, String destination)
```

Example of using this method:
```java
HtmlReporter.createAxeHtmlReport(this.getWebDriver(), this.getWebDriver().findElement(By.cssSelector("main")), path);
```

## CreateAxeHtmlReportFile
The main method to create an HTML report file.  
This is a private method that is called in all createAxeHtmlReport methods.

Example of this method in use:
```java
createAxeHtmlReportFile(webDriver, results, destination, requestedResults);
```