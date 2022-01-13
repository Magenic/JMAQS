# <img src="resources/jmaqslogo.jpg" height="32" width="32"> ElementHandler Class

## Overview
This class provides additional helper methods on top of Selenium's base interaction methods for common operations done when interacting with HTML elements.

# Available Methods
[GetSelectedOptionFromDropdown](#GetSelectedOptionFromDropdown)  
[GetSelectedOptionsFromDropdown](#GetSelectedOptionsFromDropdown)  
[GetElementAttribute](#GetElementAttribute)  
[CheckACheckBox](#CheckACheckbox)  
[CreateCommaDelimitedString](#CreateCommaDelimitedString)  
[ClickButton](#ClickButton)  
[SelectMultipleElementsFromListBox](#SelectMultipleElementsFromListBox)  
[SelectMultipleElementsFromListBoxByValue](#SelectMultipleElementsFromListBoxByValue)  
[SelectDropDownOption](#SelectDropDownOption)  
[SelectDropDownOptionByValue](#SelectDropDownOptionByValue)  
[SetTextBox](#SetTextBox)  
[ClickElementByJavaScript](#ClickElementByJavaScript)  
[ScrollIntoView](#ScrollIntoView)  
[ExecuteScrolling](#ExecuteScrolling)  
[SlowType](#SlowType)  
[SendSecretKeys](#SendSecretKeys)  

## GetSelectedOptionFromDropdown
Gets the selected option's displayed text from a select element and returns it.
```javaSelectDropDownOption
// A by selector for a list of names from a dropdown
private static By nameDropdown = By.cssSelector("#namesDropdown");

// Gets the text of the current selected option
String nameSelected = ElementHandler.getSelectedOptionFromDropdown(this.getWebDriver(), nameDropdown);
```

## GetSelectedOptionsFromDropdown
Gets all the selected options' displayed text from a select element and returns them in a list.
```java
// A by selector for a list of names from a dropdown
private static By nameDropdown = By.cssSelector("#namesDropdown");

ArrayList<String> listOfNamesSelected = ElementHandler.getSelectedOptionsFromDropdown(this.getWebDriver(), nameDropdown);
```

## GetElementAttribute
Gets the value of an attribute for an element. By default, it gets the value of the "value" attribute.
```java
// By selector for the page title
private static By pageTitle = By.cssSelector(".title");

// Returns the value of the element's value
String pageTitleValue = ElementHandler.getElementAttribute(this.getWebDriver(), pageTitle);

// By selector for the page title
private static By pageTitle = By.cssSelector(".title");

// Returns the value of the elements href
String pageTitleHrefValue =  ElementHandler.getElementAttribute(this.getWebDriver(), pageTitle, "href");
```

## CheckACheckbox
Checks or un-checks an element. It will check the element if the bool argument is true. It will uncheck the element if the bool argument is false.  If the box is already in state the action wants it to be in upon completing that action, then the action will not do any checking or unchecking.
```java
// By selector for a checkbox
private static By checkBox = By.id("CheckBox");

// Checkbox is initially unchecked, waits for the element to become clickable
UIWaitFactory.getWaitDriver(this.getWebDriver()).waitUntilClickableElement(checkbox);

// If the checkbox is unchecked, then the driver will check the checkbox
ElementHandler.checkCheckBox(this.getWebDriver(), checkbox, true);

// If the checkbox is checked, then the driver will uncheck the checkbox
ElementHandler.checkCheckBox(this.getWebDriver(), checkbox, false);
```
In applications a checkbox will either be checked or unchecked.  Stating "click this checkbox" does not take into consideration the state of that checkbox.  

## CreateCommaDelimitedString
Creates a collection of elements based on a selector, loops through the collection, gathering text, adding it to a list, removes any white space, sorts the list alphabetically, and then returns that list as a string with the values separated by commas.  
By default, it will retrieve the value from the value attribute.
```java
// A by selector for a list of computer parts.
private static By computerParts = By.cssSelector("ul>#options");

// Returns the text of the elements as an ordered string
String computerOptions =  ElementHandler.createCommaDelimitedString(this.getWebDriver(), computerParts, true);

// A by selector for a list of computer parts.
private static By computerParts = By.CssSelector("ul>#options");

// Returns the text of the elements without ordering them
String computerOptions =  ElementHandler.createCommaDelimitedString(this.getWebDriver(), computerParts);
```

## ClickButton
Clicks an element. If the bool argument is true, it will wait until the button disappears after clicking it, else it will immediately return.  If it waits for the button to disappear, it will throw an exception if it does not.  If it does not wait for the button to disappear, it will continue.
```java
// By selector for a checkbox
private static By button = By.id("LargeButton");

ElementHandler.clickButton(this.getWebDriver(), button, true);
```

## SelectMultipleElementsFromListBox
Selects multiple option elements from a list box using a list of strings of the option elements' displayed texts.
```java
// A by selector for a list of computer parts.
private static By computerParts = By.cssSelector("ul>#options");

ArrayList<String> itemsToSelect = new ArrayList<String>();
    itemsToSelect.add("Monitor");
    itemsToSelect.add("Hard Drive");
    itemsToSelect.add("Keyboard");

ElementHandler.selectMultipleElementsFromListBox(this.getWebDriver(), computerParts, itemsToSelect);
```

## SelectMultipleElementsFromListBoxByValue
Selects multiple option elements from a list box using a list of strings of the option elements' value attribute texts.
```java
// A by selector for a list of computer parts.
private static By computerParts = By.cssSelector("ul>#options");

ArrayList<String> itemsToSelect = new ArrayList<String>();
    itemsToSelect.add("1");
    itemsToSelect.add("2");

ElementHandler.selectMultipleElementsFromListBoxByValue(this.getWebDriver(), computerParts, itemsToSelect);
```

## SelectDropDownOption
Selects an option element from a select element using the option's displayed text.
```java
// A by selector for a list of computer parts.
private static By computerParts = By.cssSelector("ul>#options");

// Selects the element inside the computer parts options where the text matches "Motherboard"
ElementHandler.selectDropDownOption(this.getWebDriver(), computerParts, "Motherboard");
```

## SelectDropDownOptionByValue
Selects an option element from a select element using the option's value attribute text.
```java
// A by selector for a list of computer parts.
private static By computerParts = By.cssSelector("ul>#options");

// Selects the element where the value attribute is equal to "1"
ElementHandler.selectDropDownOptionByValue(computerParts , "1");
```

## SetTextBox
Enters text into an element. It also clears the element before entering anything. If the tabOff  is not set or is set to true, then the last key sent will be a tab, else it won't send a tab key at the end of typing the string argument.
```java
// By selector for a textField
private static By textField = By.id("textBox");

// Sends the words "hello, world" to the text box, and then sends tab
ElementHandler.setTextBox(this.getWebDriver(), textField , "hello, world");

// Sends the words "hello, world" to the text box, and does not send tab
ElementHandler.setTextBox(this.getWebDriver(), textField , "hello, world", false);
```

## ClickElementByJavaScript
Clicks an element using JavaScript's [click][1] method where using the normal Selenium Click method may not work correctly, such as hidden or hover triggered elements.
```java
private static By button = By.id("LargeButton");

ElementHandler.clickElementByJavaScript(this.getWebDriver(), button);
```

## ScrollIntoView
Uses the JavaScript [scrollIntoView][2] method to scroll an element into the view.
```java
// By selector for the page title
private static By pageTitle = By.cssSelector(".title");

// Scrolls until the page title is in view
ElementHandler.scrollIntoView(this.getWebDriver(), pageTitle);
```

Scrolls Until Element is in View then Scroll to X and Y Coordinates
Uses the JavaScript [scrollIntoView][2] and [scroll][3] methods to scroll an element into view and then scroll based on an offset from the location of that element using the x and y arguments.
```java
// By selector for the page title
private static By pageTitle = By.cssSelector(".title");

// Scrolls to the page title and then scrolls by the x and y offsets
ElementHandler.scrollIntoView(this.getWebDriver(), pageTitle , -20, 150);
```

## ExecuteScrolling
Executes horizontal and vertical scrolling using the JavaScript [scroll][3] method based on the x and y arguments.
```java
// Scrolls by the x and y offsets
ElementHandler.executeScrolling(this.getWebDriver(), 50, -100);
```

## SlowType
Slowly types a string. Useful in scenarios where the normal Selenium SendKeys method types too quickly and causes issues. It sends key presses every 500 milliseconds.
```java
// By selector for a textField
private static By textField = By.id("textBox");

ElementHandler.slowType(this.getWebDriver(), textField, "hello, world");
```

## SendSecretKeys
Sends the keys to an element without logging. Useful to prevent secret fields (passwords) from being logged.
```java
 private static By passwordField = By.id("passwordField");

 ElementHandler.sendSecretKeys(this.getWebDriver(), passwordField, "superSecretPassword", this.getLogger());
 ```

[1]: https://developer.mozilla.org/en-US/docs/Web/API/HTMLElement/click
[2]: https://developer.mozilla.org/en-US/docs/Web/API/Element/scrollIntoView
[3]: https://developer.mozilla.org/en-US/docs/Web/API/Window/scroll