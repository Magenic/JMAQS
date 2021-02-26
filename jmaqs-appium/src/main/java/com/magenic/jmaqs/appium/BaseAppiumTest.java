/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.appium;

import com.magenic.jmaqs.base.BaseExtendableTest;
import com.magenic.jmaqs.utilities.logging.FileLogger;
import com.magenic.jmaqs.utilities.logging.LoggingEnabled;
import com.magenic.jmaqs.utilities.logging.MessageType;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.testng.ITestResult;

/**
 * Base Appium Test Class.
 */
public class BaseAppiumTest extends BaseExtendableTest<AppiumTestObject> {

  /**
   * Initialize a new instance of the BaseAppiumTest class.
   */
  public BaseAppiumTest() {
    // Needs an empty constructor
  }

  /**
   * Gets the appium driver.
   *
   * @return the appium driver
   */
  public AppiumDriver<WebElement> getAppiumDriver() {
    return this.getTestObject().getAppiumDriver();
  }

  /**
   * Sets appium driver.
   *
   * @param mobileDriver the mobile driver
   */
  public void setAppiumDriver(AppiumDriver<WebElement> mobileDriver) {
    this.getTestObject().setAppiumDriver(mobileDriver);
  }

  /**
   * Gets new mobile driver.
   *
   * @return the mobile driver
   */
  protected AppiumDriver<WebElement> getMobileDriver() {
    return AppiumDriverFactory.getDefaultMobileDriver();
  }

  /**
   * Steps to do before logging teardown results.
   *
   * @param resultType The test result
   */
  @Override
  protected void beforeLoggingTeardown(ITestResult resultType) {
    try {

      if (this.getTestObject().getAppiumManager().isDriverInitialized() && this
          .getLogger() instanceof FileLogger && resultType.getStatus() != ITestResult.SUCCESS
          && this.loggingEnabledSetting != LoggingEnabled.NO) {
        AppiumUtilities.captureScreenshot(this.getAppiumDriver(), this.getTestObject(), "Final");
        if (AppiumConfig.getSavePageSourceOnFail()) {
          AppiumUtilities
              .savePageSource(this.getAppiumDriver(), this.getTestObject(), "FinalPageSource");
        }
      }

    } catch (Exception e) {
      this.tryToLog(MessageType.WARNING, "Failed to get screen shot because: %s", e.getMessage());
    }
  }

  @Override
  protected void createNewTestObject() {
    this.setTestObject(new AppiumTestObject(this::getMobileDriver, this.createLogger(),
        this.getFullyQualifiedTestClassName()));
  }
}
