# <img src="resources/jmaqslogo.jpg" height="32" width="32"> UIFindFactory

## Overview
Creates UI Find Objects for searchable elements.

## GetFind
This method uses a search context item type to return the found Item as a UIFind element.
<br>The SearchContext items can be a WebElement or a WebDriver. 
```java
UIFind findWithElement = UIFindFactory.getFind(elementDriver);

UIFind findWithWebDriver = UIFindFactory.getFind(this.getWebDriver());
```