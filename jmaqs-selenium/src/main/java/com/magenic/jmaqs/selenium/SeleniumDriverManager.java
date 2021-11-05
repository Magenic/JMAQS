/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.selenium;

import com.magenic.jmaqs.base.BaseTestObject;
import com.magenic.jmaqs.base.DriverManager;
import com.magenic.jmaqs.utilities.helper.StringProcessor;
import com.magenic.jmaqs.utilities.logging.LoggingConfig;
import com.magenic.jmaqs.utilities.logging.LoggingEnabled;
import com.magenic.jmaqs.utilities.logging.MessageType;
import java.util.function.Supplier;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WrapsDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.events.WebDriverListener;

/**
 * The type Selenium driver manager.
 */
public class SeleniumDriverManager extends DriverManager<WebDriver> {

  /**
   * Instantiates a new Selenium driver manager.
   *
   * @param getDriver  the get driver
   * @param testObject the test object
   */
  public SeleniumDriverManager(Supplier<WebDriver> getDriver, BaseTestObject testObject) {
    super(getDriver, testObject);
  }

  /**
   * Instantiates a new Selenium driver manager.
   *
   * @param driver     the Selenium web driver
   * @param testObject the test object
   */
  public SeleniumDriverManager(WebDriver driver, BaseTestObject testObject) {
    super(() -> driver, testObject);
  }

  @Override
  public void close() {
    getLogger().logMessage(MessageType.VERBOSE, "Start dispose driver");

    // If we never created the driver we don't have any cleanup to do
    if (!this.isDriverInitialized()) {
      return;
    }

    try {
      WebDriver driver = this.getWebDriver();
      driver.quit();
    } catch (Exception e) {
      getLogger().logMessage(MessageType.ERROR,
          StringProcessor.safeFormatter("Failed to close web driver because: %s", e.getMessage()));
    }

    this.baseDriver = null;
    getLogger().logMessage(MessageType.VERBOSE, "End dispose driver");
  }

  /**
   * Gets web driver.
   *
   * @return the web driver
   */
  public WebDriver getWebDriver() {
    if (!this.isDriverInitialized() && LoggingConfig.getLoggingEnabledSetting() != LoggingEnabled.NO) {
      WebDriverListener listener = new EventHandler(this.getLogger());
      this.baseDriver = new EventFiringDecorator(listener).decorate(this.getBase());

      // Log the setup
      this.loggingStartup(this.baseDriver);
    }

    return getBase();
  }

  /**
   * Log verbose.
   *
   * @param message the message
   * @param args    the args
   */
  protected void logVerbose(String message, Object... args) {
    StringBuilder messages = new StringBuilder();
    messages.append(StringProcessor.safeFormatter(message, args));
    String fullTestName = getTestObject().getFullyQualifiedTestName();

    Thread thread = Thread.currentThread();
    for (StackTraceElement stackTraceElement : thread.getStackTrace()) {
      String trim = stackTraceElement.toString().trim();
      if (!trim.startsWith(fullTestName)) {
        messages.append(stackTraceElement);
      }
    }
    getLogger().logMessage(MessageType.VERBOSE, messages.toString());
  }

  private void loggingStartup(WebDriver webDriver) {
    try {
      WebDriver driver = ((WrapsDriver) webDriver).getWrappedDriver();

      String browserType = ((RemoteWebDriver) driver).getCapabilities().toString();

      if (SeleniumConfig.getBrowserName().equalsIgnoreCase("Remote")) {
        getLogger().logMessage(MessageType.INFORMATION, StringProcessor.safeFormatter("Remote driver: " + browserType));
      } else {
        getLogger().logMessage(MessageType.INFORMATION, StringProcessor.safeFormatter("Local driver: " + browserType));
      }

    } catch (Exception e) {
      getLogger().logMessage(MessageType.ERROR,
          StringProcessor.safeFormatter("Failed to start driver because: %s", e.getMessage()));
      System.out.println(StringProcessor.safeFormatter("Failed to start driver because: %s", e.getMessage()));
    }
  }
}
