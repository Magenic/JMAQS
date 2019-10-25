/*
 * Copyright 2019 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.appium;

import com.magenic.jmaqs.base.BaseTestObject;
import com.magenic.jmaqs.selenium.SeleniumWait;
import com.magenic.jmaqs.utilities.logging.Logger;
import io.appium.java_client.AppiumDriver;
import java.util.function.Supplier;
import org.openqa.selenium.WebElement;

/**
 * Class AppiumTestObject.
 */
public class AppiumTestObject extends BaseTestObject {

  /**
   * The appium driver.
   * @deprecated driver managers are the preferred approach now.
   */
  @Deprecated
  protected AppiumDriver appiumDriver;

  /**
   * The appium wait.
   *
   * @deprecated Use static service for waits. {@link com.magenic.jmaqs.appium.AppiumWait} is Deprecated.
   */
  @Deprecated
  protected AppiumWait appiumWait;

  /**
   * Instantiates a new appium test object.
   *
   * @param appiumDriver           the appium driver
   * @param wait                   the wait // * @param fullyQualifiedTestName the fully qualified test name
   * @param fullyQualifiedTestName the fully qualified test name
   * @param logger                 the logger
   * @deprecated {@link com.magenic.jmaqs.appium.AppiumWait} is Deprecated
   */
  @Deprecated
  public AppiumTestObject(AppiumDriver appiumDriver, AppiumWait wait, String fullyQualifiedTestName,
      Logger logger) {
    super(logger, fullyQualifiedTestName);
    this.appiumDriver = appiumDriver;
    this.appiumWait = wait;
  }

  /**
   * Instantiates a new Appium test object.
   *
   * @param appiumDriver           the appium driver
   * @param logger                 the logger
   * @param fullyQualifiedTestName the fully qualified test name
   */
  public AppiumTestObject(AppiumDriver<WebElement> appiumDriver, Logger logger, String fullyQualifiedTestName) {
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
   * Gets the appium wait.
   *
   * @return the appium wait
   * @deprecated {@link com.magenic.jmaqs.appium.AppiumWait} is deprecated.
   */
  @Deprecated
  public SeleniumWait getAppiumWait() {
    return this.appiumWait;
  }

  /**
   * Sets the appium wait.
   *
   * @param wait the new appium wait
   * @deprecated {@link com.magenic.jmaqs.appium.AppiumWait} is deprecated.
   */
  @Deprecated
  public void setAppiumWait(AppiumWait wait) {
    this.appiumWait = wait;
  }

  /**
   * Gets the appium driver.
   *
   * @return the appium driver
   */
  public AppiumDriver getAppiumDriver() {

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
