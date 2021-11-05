/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.selenium;

import com.magenic.jmaqs.utilities.logging.Logger;
import com.magenic.jmaqs.utilities.logging.MessageType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;

/**
 * Event Handler Class implementing WebDriverEventListener.
 */
public class EventHandler implements WebDriverListener {

  /**
   * String value of value for duplicate instances in this class.
   */
  private static final String VALUE = "value";

  /**
   * String value of before finding element value for duplicate instance in this class.
   */
  private static final String BEFORE_FINDING_STRING = "Before finding element By: %s";

  /**
   * The Event Handler Logger.
   */
  private final Logger logger;

  /**
   * Initializes a new instance of the EventHandler class.
   *
   * @param logger The Logger
   */
  public EventHandler(Logger logger) {
    this.logger = logger;
  }

  /**
   * Log Message when there is a Global exception.
   * @param target the target object
   * @param method the method run
   * @param args the object arguments
   * @param e the exception
   */
  @Override
  public void onError(Object target, Method method, Object[] args, InvocationTargetException e) {
    // First chance handler catches these when it is a real error - These are typically retry loops
    this.logger.logMessage(MessageType.VERBOSE, "Exception occurred at %s", e.getMessage());
  }

  /**
   * Log message before finding an element with the web driver.
   *
   * @param by      The By Element to find
   * @param driver  The Web Driver
   */
  @Override
  public void beforeFindElement(WebDriver driver, By by) {
    this.logger.logMessage(MessageType.INFORMATION, BEFORE_FINDING_STRING, by.toString());
  }

  /**
   * Log message after finding an element with the web driver.
   *
   * @param by      The By element to find
   * @param element The Web Element
   * @param driver  The Web Driver
   */
  @Override
  public void afterFindElement(WebDriver driver, By by, WebElement element) {
    this.logger.logMessage(MessageType.INFORMATION, "Found element By: " + by.toString());
  }

  /**
   * Log message before finding web elements with the web driver.
   *
   * @param by      The By Element to find
   * @param driver  The Web Driver
   */
  @Override
  public void beforeFindElements(WebDriver driver, By by) {
    this.logger.logMessage(MessageType.INFORMATION, BEFORE_FINDING_STRING, by.toString());
  }

  /**
   * Log message before finding web elements with the web driver.
   *
   * @param by      The By Element to find
   * @param element  The Web element
   */
  @Override
  public void beforeFindElements(WebElement element, By by) {
    this.logger.logMessage(MessageType.INFORMATION, BEFORE_FINDING_STRING, by.toString());
  }

  /**
   * Log message after finding web elements with the web driver.
   *
   * @param by      The By element to find
   * @param element The Web Element
   * @param driver  The Web Driver
   */
  @Override
  public void afterFindElements(WebDriver driver, By by, List<WebElement> element) {
    this.logger.logMessage(MessageType.INFORMATION, "Found element By: " + by.toString());
  }

  /**
   * Log message after finding web elements with the web driver.
   * @param by      The By element to find
   * @param element The Web Element
   * @param result  The result
   */
  @Override
  public void afterFindElements(WebElement element, By by, List<WebElement> result) {
    this.logger.logMessage(MessageType.INFORMATION, "Found elements By: " + by.toString());
  }

  /**
   * Log message before executing a script.
   *
   * @param script The script
   * @param driver The Web Driver
   * @param args the arguments of the script
   */
  @Override
  public void beforeExecuteScript(WebDriver driver, String script, Object[] args) {
    this.logger.logMessage(MessageType.INFORMATION, "Before executing script: %s", script);
  }

  /**
   * Log message after executing a script.
   * @param driver The Web Driver
   * @param script The script
   * @param args the arguments of the script
   * @param result the result of the script
   */
  @Override
  public void afterExecuteScript(WebDriver driver, String script, Object[] args, Object result) {
    this.logger.logMessage(MessageType.INFORMATION, "Script executed: %s", script);
  }

  /**
   * Log message before clicking the web element.
   *
   * @param element The Web Element
   */
  @Override
  public void beforeClick(WebElement element) {
    try {
      this.logger.logMessage(MessageType.INFORMATION,
          "Before clicking element: %s Text:%s Location: X:%d Y:%d", element.toString(),
          element.getText(), element.getLocation().x, element.getLocation().y);
    } catch (Exception exc) {
      this.logger.logMessage(MessageType.INFORMATION, "Before clicking element");
    }
  }

  /**
   * Log message after clicking the web element.
   *
   * @param element The Web Element
   */
  @Override
  public void afterClick(WebElement element) {
    try {
      this.logger.logMessage(MessageType.INFORMATION,
          "Element clicked: %s Text:%s Location: X:%d Y:%d", element.toString(),
          element.getText(), element.getLocation().x, element.getLocation().y);
    } catch (Exception exc) {
      this.logger.logMessage(MessageType.INFORMATION, "Element clicked");
    }
  }

  /**
   * Log message before changing a web element's value.
   *
   * @param element    The Web Element
   * @param keysToSend The keys to send
   */
  @Override
  public void beforeSendKeys(WebElement element, CharSequence[] keysToSend) {
    String value = element.getAttribute(VALUE);
    this.logger.logMessage(MessageType.INFORMATION, "Element value before change: %s", value);
  }

  /**
   * Log message after changing a web element's value.
   *
   * @param element    The Web Element
   * @param keysToSend The keys to send
   */
  @Override
  public void afterSendKeys(WebElement element, CharSequence[] keysToSend) {
    String value = element.getAttribute(VALUE);
    this.logger.logMessage(MessageType.INFORMATION, "Element value changed to: %s", value);
  }

  /**
   * Log message before clearing a web element's text value.
   *
   * @param element    The Web Element
   */
  @Override
  public void beforeClear(WebElement element) {
    String value = element.getAttribute(VALUE);
    this.logger.logMessage(MessageType.INFORMATION, "Element value before clear: %s", value);
  }

  /**
   * Log message after clearing a web element's text value.
   *
   * @param element    The Web Element
   */
  @Override
  public void afterClear(WebElement element) {
    String value = element.getAttribute(VALUE);
    this.logger.logMessage(MessageType.INFORMATION, "Element value changed to: %s", value);
  }

  /**
   * Log message before getting text from a web element.
   *
   * @param element The element
   */
  @Override
  public void beforeGetText(WebElement element) {
    this.logger.logMessage(MessageType.INFORMATION, "Before getting text from element");
  }

  /**
   * Log message after getting text from a web element.
   *
   * @param element The element
   * @param text    The text from the element
   */
  @Override
  public void afterGetText(WebElement element, String text) {
    this.logger.logMessage(MessageType.INFORMATION, "Got element text: %s", text);
  }

  /**
   * Log message before navigating to a page.
   *
   * @param url    The URL
   * @param navigation The Web Driver
   */
  @Override
  public void beforeTo(WebDriver.Navigation navigation, String url) {
    this.logger.logMessage(MessageType.INFORMATION, "Before navigating to page: %s", url);
  }

  /**
   * Log message after navigating to a page.
   *
   * @param url    The URL
   * @param navigation The Web Driver
   */
  @Override
  public void afterTo(WebDriver.Navigation navigation, String url) {
    this.logger.logMessage(MessageType.INFORMATION, "After navigating to page: %s", url);
  }

  /**
   * Log message before navigating back to a page.
   *
   * @param navigation The Web Driver navigation
   */
  @Override
  public void beforeBack(WebDriver.Navigation navigation) {
    this.logger.logMessage(MessageType.INFORMATION, "Before navigating back to previous page");
  }

  /**
   * Log message after navigating back to a page.
   *
   * @param navigation The Web Driver
   */
  @Override
  public void afterBack(WebDriver.Navigation navigation) {
    this.logger.logMessage(MessageType.INFORMATION, "Navigated back to previous page");
  }

  /**
   * Log message before navigating forward to a page.
   *
   * @param navigation The Web Driver
   */
  @Override
  public void beforeForward(WebDriver.Navigation navigation) {
    this.logger.logMessage(MessageType.INFORMATION, "Before navigating forward to next page");
  }

  /**
   * Log message after navigating forward to a page.
   *
   * @param navigation The Web Driver
   */
  @Override
  public void afterForward(WebDriver.Navigation navigation) {
    this.logger.logMessage(MessageType.INFORMATION, "Navigated forward to next page");
  }

  /**
   * Log message before refreshing the page.
   *
   * @param navigation The Web Driver
   */
  @Override
  public void beforeRefresh(WebDriver.Navigation navigation) {
    this.logger.logMessage(MessageType.INFORMATION, "Before Refreshing the page");
  }

  /**
   * Log message after refreshing the page.
   *
   * @param navigation The Web Driver
   */
  @Override
  public void afterRefresh(WebDriver.Navigation navigation) {
    this.logger.logMessage(MessageType.INFORMATION, "Page refreshed");
  }

  /**
   * Log message before accepting an alert.
   *
   * @param alert The Web Driver
   */
  @Override
  public void beforeAccept(Alert alert) {
    this.logger.logMessage(MessageType.INFORMATION, "Before accepting the alert");
  }

  /**
   * Log message after accepting an alert.
   *
   * @param alert The Web Driver
   */
  @Override
  public void afterAccept(Alert alert) {
    this.logger.logMessage(MessageType.INFORMATION, "Alert accepted");
  }

  /**
   * Log Message before dismissing an alert.
   *
   * @param alert The Web Driver
   */
  @Override
  public void beforeDismiss(Alert alert) {
    this.logger.logMessage(MessageType.INFORMATION, "Before dismissing the Alert");
  }

  /**
   * Log message after dismissing an alert.
   *
   * @param alert The Web Driver
   */
  @Override
  public void afterDismiss(Alert alert) {
    this.logger.logMessage(MessageType.INFORMATION, "After dismissing the Alert");
  }
}