# <img src="resources/jmaqslogo.jpg" height="32" width="32"> UIFind

## Overview
This is the tool-class used for finding elements with the provided search item.

# Available methods
[FindElement](#FindElement)  
[FindElements](#FindElements)  
[FindElementWithText](#FindElementWithText)  
[FindIndexOfElementWithText](#FindIndexOfElementWithText)
[GetElementList](#GetElementList)

## FindElement
General Find Element.
```java
return findElement(by);
```

## FindElements
Finds all elements using the by provided.
```java
return findElements(by);
```

## FindElementWithText
Find a specified Web Element by text.
```java
return findElementWithText(by, text);
```

## FindIndexOfElementWithText
Find the Index of the Specified Web Element Collection.
```java
return findIndexOfElementWithText(list, text);
```

## GetElementList
Function to get the Web Collection.
```java
boolean throwException = true;
List<WebElement> elementList = getElementList(by, throwException);
```
