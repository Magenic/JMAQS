/*
 * Copyright 2020 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.selenium;

import com.magenic.jmaqs.utilities.logging.Logger;
import com.magenic.jmaqs.utilities.logging.MessageType;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;

/**
 * Event Handler Class implementing WebDriverEventListener.
 */
public class EventHandler implements WebDriverEventListener {
  /**
   * The Event Handler Logger.
   */
  private Logger logger;

  /**
   * Initializes a new instance of the EventHandler class.
   *
   * @param logger The Logger
   */
  public EventHandler(Logger logger) {
    this.logger = logger;
  }

  /**
   * Log message before clicking element.
   *
   * @param element The Web Element
   * @param driver  The Web Driver
   */
  @Override
  public void beforeClickOn(WebElement element, WebDriver driver) {
    try {
      this.logger.logMessage(MessageType.INFORMATION,
          "Before clicking element: {0} Text:{1} Location: X:{2} Y:{3}", element.toString(),
          element.getText(), element.getLocation().x, element.getLocation().y);
    } catch (Exception exc) {
      this.logger.logMessage(MessageType.INFORMATION, "Before clicking element");
    }
  }

  /**
   * Log message after clicking element.
   *
   * @param element The Web Element
   * @param driver  The Web Driver
   */
  @Override
  public void afterClickOn(WebElement element, WebDriver driver) {
    try {
      this.logger.logMessage(MessageType.INFORMATION,
          "Element clicked: {0} Text:{1} Location: X:{2} Y:{3}", element.toString(),
          element.getText(), element.getLocation().x, element.getLocation().y);
    } catch (Exception exc) {
      this.logger.logMessage(MessageType.INFORMATION, "Element clicked");
    }
  }

  /**
   * Log message before changing an element's value.
   *
   * @param element    The Web Element
   * @param driver     The Web Driver
   * @param keysToSend The keys to send
   */
  @Override
  public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
    String value = element.getAttribute("value");
    this.logger.logMessage(MessageType.INFORMATION, "Element value before change: {0}", value);
  }

  /**
   * Log message after changing an element's value.
   *
   * @param element    The Web Element
   * @param driver     The Web Driver
   * @param keysToSend The keys to send
   */
  @Override
  public void afterChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
    String value = element.getAttribute("value");
    this.logger.logMessage(MessageType.INFORMATION, "Element value changed to: {0}", value);
  }

  /**
   * Log message before finding an element.
   *
   * @param by      The By Element to find
   * @param element The Web Element
   * @param driver  The Web Driver
   */
  @Override
  public void beforeFindBy(By by, WebElement element, WebDriver driver) {
    this.logger
        .logMessage(MessageType.INFORMATION, "Before finding element By: {0}", by.toString());
  }

  /**
   * Log message after finding an element.
   *
   * @param by      The By element to find
   * @param element The Web Element
   * @param driver  The Web Driver
   */
  @Override
  public void afterFindBy(By by, WebElement element, WebDriver driver) {
    this.logger.logMessage(MessageType.INFORMATION, "Found element By: " + by.toString());
  }

  /**
   * Log message before navigating back to a page.
   *
   * @param driver The Web Driver
   */
  @Override
  public void beforeNavigateBack(WebDriver driver) {
    this.logger.logMessage(MessageType.INFORMATION, "Before navigating back to previous page");
  }

  /**
   * Log message after navigating back to a page.
   *
   * @param driver The Web Driver
   */
  @Override
  public void afterNavigateBack(WebDriver driver) {
    this.logger.logMessage(MessageType.INFORMATION, "Navigated back to previous page: {0}",
        driver.getCurrentUrl());
  }

  /**
   * Log message before navigating forward to a page.
   *
   * @param driver The Web Driver
   */
  @Override
  public void beforeNavigateForward(WebDriver driver) {
    this.logger.logMessage(MessageType.INFORMATION, "Before navigating forward to next page");
  }

  /**
   * Log message after navigating forward to a page.
   *
   * @param driver The Web Driver
   */
  @Override
  public void afterNavigateForward(WebDriver driver) {
    this.logger.logMessage(MessageType.INFORMATION, "Navigated forward to next page: {0}",
        driver.getCurrentUrl());
  }

  /**
   * Log message before refreshing the page.
   *
   * @param driver The Web Driver
   */
  @Override
  public void beforeNavigateRefresh(WebDriver driver) {
    this.logger.logMessage(MessageType.INFORMATION, "Before Refreshing the page");
  }

  /**
   * Log message after refreshing the page.
   *
   * @param driver The Web Driver
   */
  @Override
  public void afterNavigateRefresh(WebDriver driver) {
    this.logger.logMessage(MessageType.INFORMATION, "Page refreshed");
  }

  /**
   * Log message before navigating to a page.
   *
   * @param url    The URL
   * @param driver The Web Driver
   */
  @Override
  public void beforeNavigateTo(String url, WebDriver driver) {
    this.logger.logMessage(MessageType.INFORMATION, "Before navigating to page: {0}", url);
  }

  /**
   * Log message after navigating to a page.
   *
   * @param url    The URL
   * @param driver The Web Driver
   */
  @Override
  public void afterNavigateTo(String url, WebDriver driver) {
    this.logger.logMessage(MessageType.INFORMATION, "After navigating to page: {0}", url);
  }

  /**
   * Log message before executing a script.
   *
   * @param script The script
   * @param driver The Web Driver
   */
  @Override
  public void beforeScript(String script, WebDriver driver) {
    this.logger.logMessage(MessageType.INFORMATION, "Before executing script: {0}", script);
  }

  /**
   * Log message after executing a script.
   *
   * @param script The script
   * @param driver The Web Driver
   */
  @Override
  public void afterScript(String script, WebDriver driver) {
    this.logger.logMessage(MessageType.INFORMATION, "Script executed: {0}", script);
  }

  /**
   * Log message before switching to a window.
   *
   * @param windowName The name of the window
   * @param driver     The Web Driver
   */
  @Override
  public void beforeSwitchToWindow(String windowName, WebDriver driver) {
    this.logger.logMessage(MessageType.INFORMATION, "Before switching to window: {0}", windowName);
  }

  /**
   * Log message after switching to a window.
   *
   * @param windowName The name of the window
   * @param driver     The Web Driver
   */
  @Override
  public void afterSwitchToWindow(String windowName, WebDriver driver) {
    this.logger.logMessage(MessageType.INFORMATION, "Switched to window: {0}", windowName);
  }

  /**
   * Log message before accepting an alert.
   *
   * @param driver The Web Driver
   */
  @Override
  public void beforeAlertAccept(WebDriver driver) {
    this.logger.logMessage(MessageType.INFORMATION, "Before accepting the alert");
  }

  /**
   * Log message after accepting an alert.
   *
   * @param driver The Web Driver
   */
  @Override
  public void afterAlertAccept(WebDriver driver) {
    this.logger.logMessage(MessageType.INFORMATION, "Alert accepted");
  }

  /**
   * Log message after dismissing an alert.
   *
   * @param driver The Web Driver
   */
  @Override
  public void afterAlertDismiss(WebDriver driver) {
    this.logger.logMessage(MessageType.INFORMATION, "Before dismissing the alert");
  }

  /**
   * Log Message before dismissing an alert.
   *
   * @param driver The Web Driver
   */
  @Override
  public void beforeAlertDismiss(WebDriver driver) {
    this.logger.logMessage(MessageType.INFORMATION, "Alert dismissed");
  }

  /**
   * Log Message when there is an exception.
   *
   * @param e      The exception
   * @param driver The Web Driver
   */
  @Override
  public void onException(Throwable e, WebDriver driver) {
    // First chance handler catches these when it is a real error - These are typically retry loops
    this.logger.logMessage(MessageType.VERBOSE, "Exception occurred at {0}", e.getMessage());
  }

  /**
   * Log message before getting a screenshot.
   *
   * @param target The Output Type target
   * @param <X>    The specified Output Type
   */
  @Override
  public <X> void beforeGetScreenshotAs(OutputType<X> target) {
    this.logger.logMessage(MessageType.INFORMATION, "Before screenshot capture");
  }

  /**
   * Log message after getting a screenshot.
   *
   * @param target     The Output Type target
   * @param screenshot The screenshot
   * @param <X>        The specified Output Type
   */
  @Override
  public <X> void afterGetScreenshotAs(OutputType<X> target, X screenshot) {
    this.logger.logMessage(MessageType.INFORMATION, "After screenshot capture");
  }

  /**
   * Log message before getting text from an element.
   *
   * @param element The element
   * @param driver  The Web Driver
   */
  @Override
  public void beforeGetText(WebElement element, WebDriver driver) {
    this.logger.logMessage(MessageType.INFORMATION, "Before getting text from element");
  }

  /**
   * Log message after getting text from an element.
   *
   * @param element The element
   * @param driver  The Web Driver
   * @param text    The text from the element
   */
  @Override
  public void afterGetText(WebElement element, WebDriver driver, String text) {
    this.logger.logMessage(MessageType.INFORMATION, "Got element text: {0}", text);
  }
}