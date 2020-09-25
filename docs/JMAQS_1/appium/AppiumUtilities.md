# <img src="resources/jmaqslogo.jpg" height="32" width="32"> Appium Utilities

## Overview
The Appium Utilities class is a utility class for working with Appium

# Available methods
[CaptureScreenshot](#CaptureScreenshot)  
[SavePageSource](#SavePageSource)  

##  CaptureScreenshot
Capture a screenshot.  
*By default screenshots ends up in the log folder, but one of the 'CaptureScreenshot' functions allows you to choose a different directory.*
```java
String username = "NOT";
String password = "Valid";
LoginPageModel page = new LoginPageModel(this.getTestObject());
page.openLoginPage();

boolean captured = AppiumUtilities.captureScreenshot(this.getWebDriver(), this.getTestObject(), "LoginPage");
```
##  SavePageSource
Capture the page DOM.  
*By default page source ends up in the log folder, but one of the 'SavePageSource' functions allows you to choose a different directory.*
```java
String username = "NOT";
String password = "Valid";
LoginPageModel page = new LoginPageModel(this.getTestObject());
page.OpenLoginPage();

boolean savedSource = AppiumUtilities.savePageSource(this.getWebDriver(), this.getTestObject());

String pageSourcePath = AppiumUtilities.savePageSource(this.getAppiumDriver(), this.getTestObject(), "TempTestDirectory", "TestObjAssoc");
```

##  KillDriver
Make sure an appium driver gets closed
```java
AppiumUtilties.killdriver(appiumDriver);