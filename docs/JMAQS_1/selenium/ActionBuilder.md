# <img src="resources/jmaqslogo.jpg" height="32" width="32"> Action Builder
The action builder class contains many advanced methods using interactions with Selenium Actions class.  This includes actions that need to be performed synchronously.

# Available Methods
[HoverOver](#HoverOver)  
[PressModifierKey](#PressModifierKey)  
[SlideElement](#SlideElement)  
[RightClick](#RightClick)  

## HoverOver
Hovers the mouse over an element.
```java
private static By javascriptAlertButton = By.cssSelector(".javaScriptAlertButton");

ActionBuilder.HoverOver(this.getWebDriver(), this.javascriptAlertButton);
```
## PressModifierKey
Press modifier keys synchronously.  Each key will be pressed at the same time.  The Keys class is used to quickly write keys.
```java
// Presses the arrow down, backspace, control, and divide keys all at once
ActionBuilder.pressModifierKey(this.getWebDriver(), Keys.chord(Keys.ARROW_DOWN, Keys.BACK_SPACE, Keys.CONTROL, Keys.DIVIDE))

// Presses the Control key
ActionBuilder.pressModifierKey(this.getWebDriver(), Keys.CONTROL);
```

## SlideElement
Drags and drops an element by an X pixel offset.
```java
// Drags an element left 20 pixels and drops it
ActionBuilder.slideElement(this.getWebDriver(), element, -20);

// Drags an element right 20 pixels and drops it
ActionBuilder.slideElement(this.getWebDriver(), element, 20);
```

## RightClick
Performs a right click on an element
```java
private static By titleImage = By.cssSelector(".title > img");

// Right clicks the title image
ActionBuilder.rightClick(this.getWebDriver(), titleImage);
```