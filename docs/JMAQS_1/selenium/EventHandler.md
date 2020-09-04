# <img src="resources/jmaqslogo.jpg" height="32" width="32"> EventHandler

## Overview
This class provides additional helper methods on top of Selenium's base interaction methods for common operations done when interacting with HTML element events.

# Available Methods
[BeforeClickOn](#BeforeClickOn)  
[AfterClickOn](#AfterClickOn)  
[BeforeChangeValueOf](#BeforeChangeValueOf)  
[AfterChangeValueOf](#AfterChangeValueOf)  
[BeforeFindBy](#BeforeFindBy)  
[AfterFindBy](#AfterFindBy)  
[BeforeNavigateBack](#BeforeNavigateBack)  
[AfterNavigateBack](#AfterNavigateBack)  
[BeforeNavigateForward](#BeforeNavigateForward)  
[AfterNavigateForward](#AfterNavigateForward)  
[BeforeNavigateRefresh](#BeforeNavigateRefresh)  
[AfterNavigateRefresh](#AfterNavigateRefresh)  
[BeforeNavigateTo](#BeforeNavigateTo)  
[AfterNavigateTo](#AfterNavigateTo)  
[BeforeScript](#BeforeScript)  
[AfterScript](#AfterScript)  
[BeforeSwitchToWindow](#BeforeSwitchToWindow)  
[AfterSwitchToWindow](#AfterSwitchToWindow)  
[BeforeAlertAccept](#BeforeAlertAccept)  
[AfterAlertAccept](#AfterAlertAccept)  
[BeforeAlertDismiss](#BeforeAlertDismiss)  
[AfterAlertDismiss](#AfterAlertDismiss)  
[OnException](#OnException)  
[BeforeGetScreenshotAs](#BeforeGetScreenshotAs)  
[AfterGetScreenshotAs](#AfterGetScreenshotAs)  
[BeforeGetText](#BeforeGetText)  
[AfterGetText](#AfterGetText)  

## BeforeClickOn
Log message before clicking element.
```java
void beforeClickOn(WebElement element, WebDriver driver)
```

## AfterClickOn
Log message after clicking element.
```java
void afterClickOn(WebElement element, WebDriver driver)
```

## BeforeChangeValueOf
Log message before changing an element's value.
```java
void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) 
```

## AfterChangeValueOf
Log message after changing an element's value.
```java
void afterChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend)
```

## BeforeFindBy
Log message before finding an element.
```java
void beforeFindBy(By by, WebElement element, WebDriver driver)
```

## AfterFindBy
Log message after finding an element.
```java
void afterFindBy(By by, WebElement element, WebDriver driver)
```

## BeforeNavigateBack
Log message before navigating back to a page.
```java
void beforeNavigateBack(WebDriver driver)
```

## AfterNavigateBack
Log message after navigating back to a page.
```java
void afterNavigateBack(WebDriver driver)
```

## BeforeNavigateForward
Log message before navigating forward to a page.
```java
void beforeNavigateForward(WebDriver driver)
```

## AfterNavigateForward
Log message after navigating forward to a page.
```java
void afterNavigateForward(WebDriver driver)
```

## BeforeNavigateRefresh
Log message before refreshing the page.
```java
void beforeNavigateRefresh(WebDriver driver)
```

## AfterNavigateRefresh
Log message after refreshing the page.
```java
void afterNavigateRefresh(WebDriver driver)
```

## BeforeNavigateTo
Log message before navigating to a page.
```java
void beforeNavigateTo(String url, WebDriver driver)
```

## AfterNavigateTo
Log message after navigating to a page.
```java
void afterNavigateTo(String url, WebDriver driver) 
```

## BeforeScript
Log message before executing a script.
```java
void beforeScript(String script, WebDriver driver)
```

## AfterScript
Log message after executing a script.
```java
void afterScript(String script, WebDriver driver)
```

## BeforeSwitchToWindow
Log message before switching to a window.
```java
void beforeSwitchToWindow(String windowName, WebDriver driver)
```

## AfterSwitchToWindow
Log message after switching to a window.
```java
void afterSwitchToWindow(String windowName, WebDriver driver)
```

## BeforeAlertAccept
Log message before accepting an alert.
```java
void beforeAlertAccept(WebDriver driver)
```

## AfterAlertAccept
Log message after accepting an alert.
```java
void afterAlertAccept(WebDriver driver)
```

## BeforeAlertDismiss
Log Message before dismissing an alert.
```java
void beforeAlertDismiss(WebDriver driver)
```

## AfterAlertDismiss
Log message after accepting an alert.
```java
void afterAlertDismiss(WebDriver driver)
```

## OnException
Log Message when there is an exception.
```java
void onException(Throwable e, WebDriver driver)
```

## BeforeGetScreenshotAs
Log message before getting a screenshot.
```java
void beforeGetScreenshotAs(OutputType<X> target)
```

## AfterGetScreenshotAs
Log message after getting a screenshot.
```java
void afterGetScreenshotAs(OutputType<X> target, X screenshot)
```

## BeforeGetText
Log message before getting text from an element.
```java
void beforeGetText(WebElement element, WebDriver driver)
```

## AfterGetText
Log message after getting text from an element.
```java
void afterGetText(WebElement element, WebDriver driver, String text)
```
