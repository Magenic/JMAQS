/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.selenium;

import com.magenic.jmaqs.selenium.exceptions.ElementHandlerException;
import com.magenic.jmaqs.selenium.factories.UIWaitFactory;
import com.magenic.jmaqs.utilities.helper.ListProcessor;
import com.magenic.jmaqs.utilities.logging.Logger;
import com.magenic.jmaqs.utilities.logging.MessageType;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 * Contains functions for interacting with WebElement objects.
 */
public class ElementHandler {

  /**
   * Private constructor.
   */
  private ElementHandler() {
  }

  /**
   * Get selected option from dropdown.
   *
   * @param webDriver The web driver
   * @param by        By selector for the element
   * @return Text of the selected option in drop down
   */
  public static String getSelectedOptionFromDropdown(WebDriver webDriver, By by) {
    Select select = new Select(UIWaitFactory.getWaitDriver(webDriver).waitForClickableElement(by));
    return select.getFirstSelectedOption().getText();
  }

  /**
   * Get a ArrayList of the items selected in a dropdown.
   *
   * @param webDriver The web driver
   * @param by        By selector for the element
   * @return String array list of selected items in drop down
   */
  public static List<String> getSelectedOptionsFromDropdown(WebDriver webDriver, By by) {
    ArrayList<WebElement> elements;
    ArrayList<String> selectedItems = new ArrayList<>();
    Select select = new Select(UIWaitFactory.getWaitDriver(webDriver).waitForClickableElement(by));

    // Get a ArrayList of WebElement objects for all selected options from
    // the dropdown
    elements = (ArrayList<WebElement>) select.getAllSelectedOptions();

    // Add the text of each element that is not null to the selectedItems
    // ArrayList
    for (WebElement element : elements) {
      if (element != null) {
        selectedItems.add(element.getText());
      }
    }
    return selectedItems;
  }

  /**
   * Get the value of a specific attribute for an element.
   *
   * @param webDriver The web driver
   * @param by        By selector for the element
   * @return The text in the text box
   */
  public static String getElementAttribute(WebDriver webDriver, By by) {
    return getElementAttribute(webDriver, by, "value");
  }

  /**
   * Get the value of a specific attribute for an element.
   *
   * @param webDriver The web driver
   * @param by        By selector for the element
   * @param attribute The attribute to get the value for
   * @return The text in the text box
   */
  public static String getElementAttribute(WebDriver webDriver, By by, String attribute) {
    return UIWaitFactory.getWaitDriver(webDriver).waitForVisibleElement(by).getAttribute(attribute);
  }

  /**
   * Check or Uncheck a checkbox NOTE: If checkbox is already in desired state, this method takes no
   * action.
   *
   * @param webDriver The web driver
   * @param by        By selector for the element
   * @param check     True to check the checkbox. False to uncheck the checkbox
   */
  public static void checkCheckBox(WebDriver webDriver, By by, boolean check) {
    WebElement element = UIWaitFactory.getWaitDriver(webDriver).waitForClickableElement(by);

    if ((check && !element.isSelected()) || (!check && element.isSelected())) {
      element.click();
    }
  }

  /**
   * Create a comma delimited String from a ArrayList of WebElement objects.
   *
   * @param webDriver The WebDriver
   * @param by        By selector for the elements
   * @param sort      True to create an alphabetically sorted comma delimited String
   * @return Returns a comma delimited String
   */
  public static String createCommaDelimitedString(WebDriver webDriver, By by, boolean sort) {
    ArrayList<String> unsortedList = new ArrayList<>();

    for (WebElement element : webDriver.findElements(by)) {
      unsortedList.add(element.getText().trim());
    }

    return ListProcessor.createCommaDelimitedString(unsortedList, sort);
  }

  /**
   * Create a comma delimited String from a ArrayList of WebElement objects.
   *
   * @param webDriver The WebDriver
   * @param by        By selector for the elements
   * @return Returns a comma delimited String
   */
  public static String createCommaDelimitedString(WebDriver webDriver, By by) {
    return createCommaDelimitedString(webDriver, by, false);
  }

  /**
   * Clicks an element.
   *
   * @param webDriver                The web driver
   * @param by                       By selector for the element
   * @param waitForButtonToDisappear If True, wait for element to disappear. If False, Do not wait
   */
  public static void clickButton(WebDriver webDriver, By by, boolean waitForButtonToDisappear) {
    UIWaitFactory.getWaitDriver(webDriver).waitForClickableElement(by).click();

    if (waitForButtonToDisappear) {
      UIWaitFactory.getWaitDriver(webDriver).waitForAbsentElement(by);
    }
  }

  /**
   * Select multiple items from a List box.
   *
   * @param webDriver            The web driver
   * @param by                   By selector for the element
   * @param elementsTextToSelect ArrayList items as Strings to select from List box
   */
  public static void selectMultipleElementsFromListBox(WebDriver webDriver, By by, List<String> elementsTextToSelect) {
    Select selectItem = new Select(UIWaitFactory.getWaitDriver(webDriver).waitForClickableElement(by));

    // Select all desired items in the list box
    for (String text : elementsTextToSelect) {
      selectItem.selectByVisibleText(text);
    }
  }

  /**
   * Select multiple items from a List box.
   *
   * @param webDriver The web driver
   * @param by        By selector for the element
   * @param values    ArrayList items as Strings to select from List box
   */
  public static void selectMultipleElementsFromListBoxByValue(WebDriver webDriver, By by, List<String> values) {
    Select selectItem = new Select(UIWaitFactory.getWaitDriver(webDriver).waitForClickableElement(by));

    // Select all desired items in the list box
    for (String value : values) {
      selectItem.selectByValue(value);
    }
  }

  /**
   * Select an option from a dropdown using displayed text.
   *
   * @param webDriver The web driver
   * @param by        By selector for the element
   * @param option    The option to select in drop down
   */
  public static void selectDropDownOption(WebDriver webDriver, By by, String option) {
    Select select = new Select(UIWaitFactory.getWaitDriver(webDriver).waitForClickableElement(by));
    select.selectByVisibleText(option);
  }

  /**
   * Select an option from a dropdown using the value attribute.
   *
   * @param webDriver The web driver
   * @param by        By selector for the element
   * @param value     The value attribute for the option to select
   */
  public static void selectDropDownOptionByValue(WebDriver webDriver, By by, String value) {
    Select select = new Select(UIWaitFactory.getWaitDriver(webDriver).waitForClickableElement(by));
    select.selectByValue(value);
  }

  /**
   * Enter text into a text box. NOTE: This function clears the text box before entering text.
   *
   * @param webDriver   The web driver
   * @param by          By selector for the element
   * @param textToEnter Text to enter into the text box
   * @param tabOff      true to press the Tab key after entering text
   */
  public static void setTextBox(WebDriver webDriver, By by, String textToEnter, boolean tabOff) {
    if (!textToEnter.isEmpty()) {
      if (tabOff) {
        textToEnter += Keys.TAB;
      }

      WebElement element = UIWaitFactory.getWaitDriver(webDriver).waitForVisibleElement(by);
      element.clear();
      element.sendKeys(textToEnter);
    } else {
      throw new ElementHandlerException("String is either null or empty");
    }
  }

  /**
   * Enter text into a text box. NOTE: This function clears the text box before entering text.
   *
   * @param webDriver   The web driver
   * @param by          By selector for the element
   * @param textToEnter Text to enter into the text box
   */
  public static void setTextBox(WebDriver webDriver, By by, String textToEnter) {
    setTextBox(webDriver, by, textToEnter, true);
  }

  /**
   * Method to click an element via JavaScript Used for scenarios where normal click can't reach,
   * such as hidden or hover triggered elements.
   *
   * @param webDriver The WebDriver
   * @param by        By selector for the element
   */
  public static void clickElementByJavaScript(WebDriver webDriver, By by) {
    WebElement element = webDriver.findElement(by);
    JavascriptExecutor executor = (JavascriptExecutor) webDriver;
    executor.executeScript("arguments[0].click();", element);
  }

  /**
   * The JavaScript method to scroll an element into the view.
   *
   * @param webDriver The WebDriver
   * @param by        By selector for the element
   */
  public static void scrollIntoView(WebDriver webDriver, By by) {
    WebElement element = webDriver.findElement(by);
    ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", element);
  }

  /**
   * The JavaScript method to scroll an element into the view.
   *
   * @param webElement The parent web element
   * @param by         By element
   */
  public static void scrollIntoView(WebElement webElement, By by) {
    WebElement element = webElement.findElement(by);
    WebDriver webDriver = SeleniumUtilities.webElementToWebDriver(element);

    ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", element);
  }

  /**
   * The JavaScript method to scroll an element into the view.
   *
   * @param webDriver The web driver
   * @param by        By element
   * @param x         Horizontal direction
   * @param y         Vertical direction
   */
  public static void scrollIntoView(WebDriver webDriver, By by, int x, int y) {
    scrollIntoView(webDriver, by);
    executeScrolling(webDriver, x, y);
  }

  /**
   * The JavaScript method to scroll an element into the view.
   *
   * @param webElement The parent web element
   * @param by         By element
   * @param x          Horizontal direction
   * @param y          Vertical direction
   */
  public static void scrollIntoView(WebElement webElement, By by, int x, int y) {
    scrollIntoView(webElement, by);
    executeScrolling(webElement, x, y);
  }

  /**
   * The JavaScript method to scroll an element into the view.
   *
   * @param webDriver The web driver
   * @param x         Horizontal direction
   * @param y         Vertical direction
   */
  public static void executeScrolling(WebDriver webDriver, int x, int y) {
    String scrollCommand = String.format("scroll(%s, %s);", x, y);
    ((JavascriptExecutor) webDriver).executeScript(scrollCommand);
  }

  /**
   * The JavaScript method to scroll an element into the view.
   *
   * @param webElement The web element
   * @param x          Horizontal direction
   * @param y          Vertical direction
   */
  public static void executeScrolling(WebElement webElement, int x, int y) {
    WebDriver webDriver = SeleniumUtilities.webElementToWebDriver(webElement);
    executeScrolling(webDriver, x, y);
  }

  /**
   * Method to slowly type a String Used for scenarios where normal SendKeys types too quickly and
   * causes issues with a website.
   *
   * @param webDriver   The web driver
   * @param by          The By element to use
   * @param textToEnter The String being entered into the text input field
   */
  public static void slowType(WebDriver webDriver, By by, String textToEnter) {
    for (char singleLetter : textToEnter.toCharArray()) {
      UIWaitFactory.getWaitDriver(webDriver).waitForClickableElement(by).sendKeys(Character.toString(singleLetter));

      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        // Wait
        Thread.currentThread().interrupt();
      }
    }
  }

  /**
   * Method used to send secret keys without logging.
   *
   * @param webDriver   The web driver
   * @param by          The By element to send keys
   * @param textToEnter The text to send
   * @param logger      The logging object
   */
  public static void sendSecretKeys(WebDriver webDriver, By by, String textToEnter, Logger logger) {
    try {
      WebElement secretElement = UIWaitFactory.getWaitDriver(webDriver).waitForClickableElement(by);
      logger.suspendLogging();
      secretElement.sendKeys(textToEnter);
      logger.continueLogging();
    } catch (Exception e) {
      logger.continueLogging();

      StringWriter sw = new StringWriter();
      e.printStackTrace(new PrintWriter(sw));
      String stackTrace = sw.toString();

      String message = "Exception during sending secret keys: " + e.getMessage() + System.lineSeparator() + stackTrace;
      logger.logMessage(MessageType.ERROR, message);
      throw new ElementHandlerException(message);
    }
  }
}