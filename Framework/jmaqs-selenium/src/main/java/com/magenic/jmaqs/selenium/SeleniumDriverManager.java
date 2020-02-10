/*
 * Copyright 2019 (C) Magenic, All rights Reserved
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
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

/**
 * The type Selenium driver manager.
 */
public class SeleniumDriverManager extends DriverManager {

  /**
   * Initializes a new instance of the <see cref="SeleniumDriverManager"/> class.
   *
   * @param getDriver  Function for getting an Selenium web driver
   * @param testObject The associated test object
   */
  public <T> SeleniumDriverManager(Supplier<T> getDriver, BaseTestObject testObject) {
    super(getDriver, testObject);
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
   * Get the web driver.
   *
   * @return The web driver
   */
  public WebDriver getWebDriver() {

    if (!this.isDriverInitialized()
        && LoggingConfig.getLoggingEnabledSetting() != LoggingEnabled.NO) {
      WebDriver tempDriver = (WebDriver) this.getBase();
      tempDriver = new EventFiringWebDriver(tempDriver);
      this.baseDriver = tempDriver;

      // Log the setup
      this.loggingStartup(tempDriver);
    }

    return (WebDriver) getBase();
  }

  /**
   * Log a verbose message and include the automation specific call stack data.
   *
   * @param message The message text
   * @param args    String format arguments
   */
  protected void logVerbose(String message, Object... args) {

    StringBuilder messages = new StringBuilder();
    messages.append(StringProcessor.safeFormatter(message, args));

    //FIXME: Need to figure out what the approach is for java in this form of logging
    //Object methodInfo = Object[].class.getEnclosingMethod();
    //String fullName = methodInfo.getClass().getTypeName() + "." + methodInfo.getClass().getName();

    Thread thread = Thread.currentThread();
    for (StackTraceElement stackTraceElement : thread.getStackTrace()) {
      //FIXME: Need to figure out what the approach is for java in this form of logging
      /*String trim = stackTraceElement.toString().trim();
      if (!trim.equals(""));*/
      messages.append(stackTraceElement.toString());
    }
    getLogger().logMessage(MessageType.VERBOSE, messages.toString());
    System.out.println(messages);
  }

  /**
   * Logging startup.
   *
   * @param webDriver the web driver
   */
  private void loggingStartup(WebDriver webDriver) {
    try {
      WebDriver driver = ((EventFiringWebDriver) webDriver).getWrappedDriver();
      String browserType;

      // Get info on what type of browser we are using
      RemoteWebDriver remoteWebDriver = (RemoteWebDriver) driver;

      if (remoteWebDriver != null) {
        browserType = remoteWebDriver.getCapabilities().toString();
      } else {
        browserType = driver.getClass().toString();
      }

      if (SeleniumConfig.getBrowserName().equalsIgnoreCase("Remote")) {
        getLogger().logMessage(MessageType.INFORMATION,
            StringProcessor.safeFormatter("Remote driver: " + browserType));
      } else {
        getLogger().logMessage(MessageType.INFORMATION,
            StringProcessor.safeFormatter("Local driver: " + browserType));
      }

    } catch (Exception e) {
      getLogger().logMessage(MessageType.ERROR,
          StringProcessor.safeFormatter("Failed to start driver because: %s", e.getMessage()));
      System.out.println(
          StringProcessor.safeFormatter("Failed to start driver because: %s", e.getMessage()));
    }
  }

}
