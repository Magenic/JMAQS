
/*
 * Copyright 2019 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.selenium;

import com.magenic.jmaqs.base.BaseExtendableTest;
import com.magenic.jmaqs.utilities.helper.StringProcessor;
import com.magenic.jmaqs.utilities.logging.Logger;
import com.magenic.jmaqs.utilities.logging.LoggingEnabled;
import com.magenic.jmaqs.utilities.logging.MessageType;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;

/**
 * Base Selenium Test class.
 */
public class BaseSeleniumTest extends BaseExtendableTest<SeleniumTestObject> {

  /**
   * Initialize a new instance of the BaseSeleniumTest class.
   * Setup the web driver for each test class
   */
  BaseSeleniumTest() {
  }

  /**
   * Thread local storage of SeleniumTestObject.
   */
  private ThreadLocal<SeleniumTestObject> seleniumTestObject =
          new ThreadLocal<>();

  /**
   * Get WebDriver.
   * @return WebDriver
   */
  public WebDriver getWebDriver() {
    return this.getSeleniumTestObject().getWebDriver();
  }

  /**
   * Get SeleniumWait.
   * @return SeleniumWait
   */
  SeleniumWait getSeleniumWait() {
    return this.getSeleniumTestObject().getSeleniumWait();
  }

  /**
   * Get the seleniumTestObject for this test.
   * @return The seleniumTestObject
   */
  SeleniumTestObject getSeleniumTestObject() {
    return this.seleniumTestObject.get();
  }

  /**
   * Log info about the web driver setup.
   */
  @Override
  protected void postSetupLogging() {
    try {

      if (SeleniumConfig.getBrowserName()
              .equalsIgnoreCase("Remote")) {
        this.getLogger().logMessage(MessageType.INFORMATION,
                "Remote driver: %s",
            SeleniumConfig.getRemoteBrowserName());
      } else {
        this.getLogger().logMessage(MessageType.INFORMATION,
                "Loaded driver: %s",
            SeleniumConfig.getBrowserName());
      }

      WebDriver driver = WebDriverFactory.getDefaultBrowser();
      SeleniumWait wait = new SeleniumWait(driver);

      seleniumTestObject.set(new SeleniumTestObject(driver,
              wait, this.getLogger(), this.getFullyQualifiedTestClassName()));
    } catch (Exception e) {
      this.getLogger().logMessage(MessageType.ERROR,
              "Failed to start driver because: %s",
          e.getMessage());
      System.out.println(
          StringProcessor.safeFormatter(
                  "Browser type %s is not supported",
                  e.getMessage()));
    }
  }

  /**
   * Get the current browser.
   * @return Current browser Web Driver
   * @throws Exception Throws exception
   */
  protected  WebDriver getBrowser() throws Exception {
    // Returns the web driver
    return WebDriverFactory.getDefaultBrowser();
  }

  /**
   * Take a screen shot if needed and tear down the web driver.
   * @param resultType The test result type
   */
  @Override
  protected void beforeLoggingTeardown(
          final ITestResult resultType) {
    // Try to take a screen shot
    try {
      if (this.getWebDriver() != null && resultType.getStatus()
              != ITestResult.SUCCESS
              && this.getLoggingEnabledSetting() != LoggingEnabled.NO) {

        SeleniumUtilities.captureScreenshot(this.getWebDriver(),
                this.getLogger(), "");
      }
    } catch (Exception e) {
      this.tryToLog(MessageType.WARNING,
              "Failed to get screen shot because: %s", e.getMessage());
    }

    this.tryToLog(MessageType.INFORMATION, "Close");

    // tear down the web driver
    try {
      this.getSeleniumTestObject().getWebDriver().quit();
    } catch (Exception e) {
      this.tryToLog(MessageType.WARNING,
              "Failed to quit because: %s", e.getMessage());
    }
  }

  /**
   * Create a Selenium test object.
   */
  @Override
  protected void createNewTestObject() {
    //FIXME: Workaround to get module working.  Must Refactor.
    Logger logger = this.createLogger();
    WebDriver driver = null;
    try {
      driver = WebDriverFactory.getDefaultBrowser();
    } catch (Exception e) {
      e.printStackTrace();
    }
    SeleniumWait wait = new SeleniumWait(driver);
    SeleniumTestObject newSeleniumTestObject =
            new SeleniumTestObject(driver, wait,
                    logger, this.getFullyQualifiedTestClassName());
    this.setTestObject(newSeleniumTestObject);
    this.seleniumTestObject.set(newSeleniumTestObject);
  }
}
