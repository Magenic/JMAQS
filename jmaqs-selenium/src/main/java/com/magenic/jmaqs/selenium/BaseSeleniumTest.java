/*
 * Copyright 2020 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.selenium;

import com.magenic.jmaqs.base.BaseExtendableTest;
import com.magenic.jmaqs.selenium.exceptions.WebDriverFactoryException;
import com.magenic.jmaqs.utilities.helper.StringProcessor;
import com.magenic.jmaqs.utilities.logging.LoggingEnabled;
import com.magenic.jmaqs.utilities.logging.MessageType;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;

/**
 * Base Selenium Test class.
 */
public abstract class BaseSeleniumTest extends BaseExtendableTest<SeleniumTestObject> {

  /**
   * Initialize a new instance of the BaseSeleniumTest class.
   */
  public BaseSeleniumTest() {
  }

  /**
   * Get WebDriver.
   *
   * @return WebDriver web driver
   */
  public WebDriver getWebDriver() {
    return this.getTestObject().getWebDriver();
  }

  /**
   * Sets web driver.
   *
   * @param webDriver the web driver
   * @throws Exception exception
   */
  public void setWebDriver(WebDriver webDriver) throws Exception {
    this.getTestObject().setWebDriver(webDriver);
  }

  /**
   * Take a screen shot if needed and tear down the web driver.
   *
   * @param resultType The test result type
   */
  @Override
  protected void beforeLoggingTeardown(ITestResult resultType) {
    // Try to take a screen shot
    try {
      if (this.getWebDriver() != null && resultType.getStatus() != ITestResult.SUCCESS
          && this.getLoggingEnabledSetting() != LoggingEnabled.NO) {
        SeleniumUtilities.captureScreenshot(getWebDriver(), getTestObject());
      }
    } catch (Exception e) {
      this.tryToLog(MessageType.WARNING, "Failed to get screen shot because: %s", e.getMessage());
    }
  }

  /**
   * Get the current browser.
   *
   * @return Current browser Web Driver
   * @throws WebDriverFactoryException Throws exception
   */
  protected WebDriver getBrowser() throws WebDriverFactoryException {
    // Returns the web driver
    return WebDriverFactory.getDefaultBrowser();
  }

  @Override
  protected void createNewTestObject() {
    try {
      this.setTestObject(
          new SeleniumTestObject(this.getBrowser(), this.createLogger(), this.getFullyQualifiedTestClassName()));
    } catch (Exception e) {
      getLogger().logMessage(StringProcessor.safeFormatter("Test Object could not be created: %s", e.getMessage()));
    }
  }
}