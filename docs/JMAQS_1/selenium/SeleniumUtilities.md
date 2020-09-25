# <img src="resources/jmaqslogo.jpg" height="32" width="32"> Selenium Utilities

## Overview
The SeleniumUtilities class is a utility class for working with Selenium

# Available methods
[CaptureScreenshot](#CaptureScreenshot)  
[CopyFileToPath](#CopyFileToPath)  
[SavePageSource](#SavePageSource)  
[CalculateFileName](#CalculateFileName)  
[ValidateDirectoryStructure](#ValidateDirectoryStructure)  
[WebElementToWebDriver](#WebElementToWebDriver)  
[KillDriver](#KillDriver)  

## CaptureScreenshot
Capture a screenshot.  
*By default screenshots ends up in the log folder, but one of the 'CaptureScreenshot' functions allows you to choose a different directory.*
```java
String username = "NOT";
String password = "Valid";
LoginPageModel page = new LoginPageModel(this.getTestObject());
page.openLoginPage();

SeleniumUtilities.captureScreenshot(webDriver, testObject, directory, fileNameWithoutExtension);
```

## CopyFileToPath
Copies the file to a path
```java
private static void copyFileToPath(File tempFile, String path) throws IOException {
    Files.copy(tempFile.toPath(), new File(path).toPath(), StandardCopyOption.COPY_ATTRIBUTES);
  }
```

##  SavePageSource
Capture the page DOM.  
*By default page source ends up in the log folder, but one of the 'SavePageSource' functions allows you to choose a different directory.*
```java
String username = "NOT";
String password = "Valid";
LoginPageModel page = new LoginPageModel(this.getTestObject());
page.openLoginPage();

String path = SeleniumUtilities.savePageSource(webDriver, testObject, LoggingConfig.getLogDirectory(),
```

## CalculateFileName
Gets the file name
```java
String path = calculateFileName(directory, fileNameWithoutExtension, ".png");
```

## ValidateDirectoryStructure
Checks if the directory structure is valid
```java
validateDirectoryStructure(testObject, directory);
```


##  WebElementToWebDriver
Get the web driver from a web element
```java
LoginPageModel page = new LoginPageModel(this.getTestObject);
page.openLoginPage();

WebElement element = this.WebDriver.findElement(By.CssSelector("#SELECTOR"));

WebDriver driver = SeleniumUtilities.webElementToWebDriver(element);

```

##  KillDriver
Make sure a web driver gets closed
```java
WebDriver tempDriver = WebDriverFactory.getBrowserWithDefaultConfiguration(SeleniumConfig.getBrowserType());

try {
      webDriver.close();
    } finally {
      webDriver.quit();
    }
  }
```