/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.appium;

import com.magenic.jmaqs.base.BaseTestObject;
import com.magenic.jmaqs.base.DriverManager;
import com.magenic.jmaqs.utilities.helper.StringProcessor;
import com.magenic.jmaqs.utilities.logging.MessageType;
import io.appium.java_client.AppiumDriver;
import java.util.function.Supplier;
import org.openqa.selenium.WebElement;

/**
 * Mobile Driver Manager Class.
 */
public class MobileDriverManager extends DriverManager<AppiumDriver<WebElement>> {
  /**
   * Instantiates a new Mobile Driver Manager.
   *
   * @param getDriverFunction Function that specifies how to get the driver.
   * @param baseTestObject    The Base Test Object.
   */
  public MobileDriverManager(Supplier<AppiumDriver<WebElement>> getDriverFunction, BaseTestObject baseTestObject) {
    super(getDriverFunction, baseTestObject);
  }

  /**
   * Instantiates a new Appium Driver Manager.
   *
   * @param driver         Appium Driver
   * @param baseTestObject The Base Test Object.
   */
  public MobileDriverManager(AppiumDriver<WebElement> driver, BaseTestObject baseTestObject) {
    super(() -> driver, baseTestObject);
    this.baseDriver = driver;
  }

  /**
   * Get the Appium driver.
   *
   * @return The Appium Driver
   */
  public AppiumDriver<WebElement> getMobileDriver() {
    return getBase();
  }

  /**
   * Cleanup the Appium Driver.
   */
  public void close() {
    // If we never created the driver we don't have any cleanup to do
    if (!this.isDriverInitialized()) {
      return;
    }

    try {
      AppiumDriver<WebElement> driver = this.getMobileDriver();
      driver.close();
      driver.quit();
    } catch (Exception e) {
      this.getLogger().logMessage(MessageType.ERROR,
          StringProcessor.safeFormatter("Failed to close mobile driver because: %s", e.getMessage()));
    }

    this.baseDriver = null;
  }
}
