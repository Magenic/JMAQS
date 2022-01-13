# <img src="resources/jmaqslogo.jpg" height="32" width="32"> Wait Methods

## Overview
UI Wait has functions to wait for an element to exist, to not exist, 
<br> to be visible, to equal a desired value, and many others.

# Available methods
[GetWaitDriver](#GetWaitDriver)  
[SetWaitDriver](#SetWaitDriver)  
[ResetWaitDriver](#ResetWaitDriver)  
[WaitForPresentElement](#WaitForPresentElement)  
[WaitForVisibleElement](#WaitForVisibleElement)  
[WaitUntilVisibleElement](#WaitUntilVisibleElement)  
[WaitForEnabledElement](#WaitForEnabledElement)  
[WaitUntilEnabledElement](#WaitUntilEnabledElement)  
[WaitUntilDisabledElement](#WaitUntilDisabledElement)  
[WaitForAbsentElement](#WaitForAbsentElement)  
[WaitUntilAbsentElement](#WaitUntilAbsentElement)  
[WaitForElements](#WaitForElements)  
[WaitForExactText](#WaitForExactText)  
[WaitUntilExactText](#WaitUntilExactText)  
[WaitForContainsText](#WaitForContainsText)  
[WaitUntilContainsText](#WaitUntilContainsText)  
[WaitUntilAttributeTextEquals](#WaitUntilAttributeTextEquals)  
[WaitForAttributeTextEquals](#WaitForAttributeTextEquals)  
[WaitUntilAttributeTextContains](#WaitUntilAttributeTextContains)    
[WaitUntilAttribute](#WaitUntilAttribute)  
[WaitForClickableElementAndScrollIntoView](#WaitForClickableElementAndScrollIntoView)  
[WaitUntilClickableElementAndScrollIntoView](#WaitUntilClickableElementAndScrollIntoView)  
[WaitUntilClickableElement](#WaitUntilClickableElement)  
[WaitForClickableElement](#WaitForClickableElement)  
[WaitForPageLoad](#WaitForPageLoad)  
[WaitUntilPageLoad](#WaitUntilPageLoad)  
[WaitUntilIframeToLoad](#WaitUntilIframeToLoad)  
[WaitForIframeToLoad](#WaitForIframeToLoad)  
[WaitForIframeToLoad](#WaitForIframeToLoad)  
[GetWebDriver](#GetWebDriver)  
[GetNewWaitDriver](#GetNewWaitDriver)  
[ScrollIntoView](#ScrollIntoView)  
[AttributeMatches](#AttributeMatches)  
[DoesTextMatch](#DoesTextMatch)  
[DoesContainsText](#DoesContainsText)  

## GetWaitDriver
Get the WebDriverWait to use outside this instance class.
```java
return this.waitForPresentElement(by, this.getWaitDriver());
```

## SetWaitDriver
Sets the WebDriverWait.
```java
this.setWaitDriver(this.getNewWaitDriver());
```

## ResetWaitDriver
Resets wait default wait driver.
```java
WebDriverWait = this.resetWaitDriver();
```

## WaitForPresentElement
Waits For the element to be present.
```java
return this.waitForPresentElement(by, this.getWaitDriver());
```

## WaitForVisibleElement
Wait for the specified element to be visible on the pages DOM. 
<br>The first element located with the specified By value is returned.
```java
WebElement element = UIWaitFactory.getWaitDriver(webDriver).waitForVisibleElement(by);
```

## WaitUntilVisibleElement
Wait until the element exists and is visible.
```java
return waitUntilVisibleElement(by, this.getWaitDriver());
```

## WaitForEnabledElement
Wait for the specified element to be present and enabled. 
<br>The first element located with the specified By value is returned.
```java
return waitForEnabledElement(by, this.timeout, this.fluentRetryTime);
```

## WaitUntilEnabledElement
Wait for the specified element to be present and enabled.
```java
return waitUntilEnabledElement(by, this.timeout, this.fluentRetryTime);
```

## WaitUntilDisabledElement
Waits until the element is disabled.
```java
return waitUntilDisabledElement(by, this.timeout, this.fluentRetryTime);
```

## WaitForAbsentElement
Wait until the element is not displayed or visible.
```java
UIWaitFactory.getWaitDriver(webDriver).waitForAbsentElement(by);
```

## WaitUntilAbsentElement
Wait until the element is not displayed or visible.
```java
boolean isAbsent = this.waitUntilAbsentElement(by, timeOutInMillis, sleepInMillis);
```

## WaitForElements
Wait for a selector to present, and then return a list 
<br>of all WebElements that are located by that selector.
```java
return this.waitForElements(by, getWaitDriver());
```

## WaitForExactText
Wait for the specified element to have the Exact text.
```java
WebElement exactText = this.getWebDriver()).waitForExactText(employeePageTitle, "Index");
```

## WaitUntilExactText
Wait until the exact text is present in the specified element.
```java
bool exactText = this.getWebDriver().waitUntilExactText(by, text);
```

## WaitForContainsText
Wait for an element to contain a specified text.
```java
WebElement containsText = this.getWebDriver().waitForContainsText(By, "Header Text")
```

##  WaitUntilContainsText
Wait until the element contains the specified text.
```java
return this.waitUntilContainsText(by, text, getWaitDriver());
```

## WaitUntilAttributeTextEquals
Wait until an attribute of the specified selector to be present and equal the desired value.
```java
boolean attributeText = this.waitUntilAttributeTextEquals(By, attribute, text);
```

## WaitForAttributeTextEquals
Wait for an attribute of the specified selector to be present and equal the desired value.
```java
return this.waitForAttributeTextEquals(by, attribute, text, getWaitDriver());
```

## WaitUntilAttributeTextContains
Wait for an attribute of the specified selector to be present, and contain the specified text.
```java
boolean textContains = this.waitUntilAttributeTextContains(by, attribute, text);
```

## WaitUntilAttribute
Wait for an attribute of the specified selector to be present, and contain the specified text.
```java
return this.waitUntilAttribute(by, attribute, text, getWaitDriver(), false);
```

## WaitForClickableElementAndScrollIntoView
Scroll an element into view, and wait for it to be clickable.
```java
return this.waitForClickableElementAndScrollIntoView(by, getWaitDriver());
```

## WaitUntilClickableElementAndScrollIntoView
Scroll an element into view, and wait for it to be clickable.
```java
return this.waitUntilClickableElementAndScrollIntoView(by, getWaitDriver());
```

## WaitUntilClickableElement
Wait for the specified selector to be clickable.
```java
return this.waitUntilClickableElement(by, getWaitDriver());
```

## WaitForClickableElement
Wait for the element specified by the provided selector to be clickable.
```java
WebElement element = UIWaitFactory.getWaitDriver(webDriver)
    .waitForClickableElement(by);
```

## WaitForPageLoad
Wait for the page to load by waiting for the source to stop changing for at least a second.
```java
UIWaitFactory.getWaitDriver(this.getWebDriver()).waitForPageLoad();
```

## WaitUntilPageLoad
Wait for the page to load by waiting for the source to stop changing for at least a second.
```java
boolean loaded = waitUntilPageLoad();
```

## WaitUntilIframeToLoad
Waits for the Frame to be available.
```java
boolean IframeLoaded = return waitUntilIframeToLoad(by, this.getWaitDriver());
```

## WaitForIframeToLoad
Waits for the Frame to be available.
```java
waitForIframeToLoad(by, wait);
```

## GetWebDriver
Returns the webDriver of this SeleniumWait object.
```java
WebDriver newWebDriver = this.getWebDriver();
```

## GetNewWaitDriver
Gets a new WaitDriver using the default timeout.
```java
WebDriverWait webDriver = this.setWaitDriver(this.getNewWaitDriver());
```

## ScrollIntoView
Scroll the web page so the selector is visible.
```java
scrollIntoView(by);
```

## AttributeMatches
Check the text value of an attribute.
```java
static boolean attributeMatches(WebDriver driver, By by,
    String attribute, String text, boolean contains)
```

## DoesTextMatch
Checks if the text of the elements are equal 
```java
boolean doesTextMatch(By by, String text) 
```

## DoesContainsText
Checks if the text of the elements are equal.
```java
boolean doesContainsText(By by, String text)
```
