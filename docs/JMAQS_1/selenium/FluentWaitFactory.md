# <img src="resources/jmaqslogo.jpg" height="32" width="32"> FluentWaitFactory
This factory is a handler of fluent wait objects.

## GetNewElementFluentWait
This gets a new element as a fluent wait object by passing in a WebElement, a timeout (int), and a sleep time (int).
```java
FluentWait<WebElement> getNewElementFluentWait(WebElement element, int timeOutInMillis, int sleepInMillis)
```
#### Example
```java
 FluentWait<WebElement> fluentWait = FluentWaitFactory.getNewElementFluentWait(elementDriver, timeout, polling);
```