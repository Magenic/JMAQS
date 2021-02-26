/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.appium;

import com.magenic.jmaqs.base.BaseTestObject;
import com.magenic.jmaqs.utilities.logging.Logger;
import io.appium.java_client.AppiumDriver;
import java.util.function.Supplier;
import org.openqa.selenium.WebElement;

/**
 * Class AppiumTestObject.
 */
public class AppiumTestObject extends BaseTestObject {

  /**
   * Instantiates a new Appium test object.
   *
   * @param appiumDriver           the appium driver
   * @param logger                 the logger
   * @param fullyQualifiedTestName the fully qualified test name
   */
  public AppiumTestObject(AppiumDriver<WebElement> appiumDriver, Logger logger,
      String fullyQualifiedTestName) {
    super(logger, fullyQualifiedTestName);
    this.getManagerStore().put(MobileDriverManager.class.getCanonicalName(),
        new MobileDriverManager((() -> appiumDriver), this));
  }

  /**
   * Instantiates a new Appium test object.
   *
   * @param appiumDriverSupplier   the appium driver supplier
   * @param logger                 the logger
   * @param fullyQualifiedTestName the fully qualified test name
   */
  public AppiumTestObject(Supplier<AppiumDriver<WebElement>> appiumDriverSupplier, Logger logger,
      String fullyQualifiedTestName) {
    super(logger, fullyQualifiedTestName);
    this.getManagerStore().put(MobileDriverManager.class.getCanonicalName(),
        new MobileDriverManager(appiumDriverSupplier, this));
  }

  /**
   * Gets the appium driver.
   *
   * @return the appium driver
   */
  public AppiumDriver<WebElement> getAppiumDriver() {

    return this.getAppiumManager().getMobileDriver();
  }

  /**
   * Gets appium manager.
   *
   * @return the appium manager
   */
  public MobileDriverManager getAppiumManager() {
    return (MobileDriverManager) this.getManagerStore()
        .get(MobileDriverManager.class.getCanonicalName());
  }

  /**
   * Sets appium driver.
   *
   * @param appiumDriver the appium driver
   */
  public void setAppiumDriver(AppiumDriver<WebElement> appiumDriver) {
    this.getManagerStore().put(MobileDriverManager.class.getCanonicalName(),
        new MobileDriverManager((() -> appiumDriver), this));
  }

  /**
   * Sets appium driver.
   *
   * @param appiumDriverSupplier the appium driver supplier
   */
  public void setAppiumDriver(Supplier<AppiumDriver<WebElement>> appiumDriverSupplier) {
    this.getManagerStore().put(MobileDriverManager.class.getCanonicalName(),
        new MobileDriverManager(appiumDriverSupplier, this));
  }
}
