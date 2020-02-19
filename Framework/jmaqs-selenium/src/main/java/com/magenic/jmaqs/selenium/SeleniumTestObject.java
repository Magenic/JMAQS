/*
 * Copyright 2020 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.selenium;

import com.magenic.jmaqs.base.BaseTestObject;
import com.magenic.jmaqs.utilities.logging.Logger;
import java.util.function.Supplier;
import org.openqa.selenium.WebDriver;

/**
 * The SeleniumTestObject Class.
 */
public class SeleniumTestObject extends BaseTestObject {

  /**
   * Instantiates a new Selenium test object.
   *
   * @param getDriverSupplier      the get driver supplier
   * @param logger                 the logger
   * @param fullyQualifiedTestName the fully qualified test name
   */
  public SeleniumTestObject(Supplier<WebDriver> getDriverSupplier, Logger logger,
      String fullyQualifiedTestName) {
    super(logger, fullyQualifiedTestName);
    this.getManagerStore().put((SeleniumDriverManager.class).getCanonicalName(),
        new SeleniumDriverManager(getDriverSupplier, this));
  }

  /**
   * Instantiates a new Selenium test object.
   *
   * @param webDriver              the web driver
   * @param logger                 the logger
   * @param fullyQualifiedTestName the fully qualified test name
   */
  public SeleniumTestObject(WebDriver webDriver, Logger logger, String fullyQualifiedTestName) {
    super(logger, fullyQualifiedTestName);
    this.getManagerStore().put((SeleniumDriverManager.class).getCanonicalName(),
        new SeleniumDriverManager((() -> webDriver), this));
  }

  /**
   * Get the WebDriver Object.
   *
   * @return A WebDriver Object
   */
  public WebDriver getWebDriver() {
    return this.getWebManager().getWebDriver();
  }

  /**
   * Gets the Selenium driver manager.
   *
   * @return the web manager
   */
  public SeleniumDriverManager getWebManager() {
    return (SeleniumDriverManager) this.getManagerStore()
        .get(SeleniumDriverManager.class.getCanonicalName());
  }

  /**
   * Sets web driver.
   *
   * @param driver the driver
   */
  public void setWebDriver(WebDriver driver) {
    this.getManagerStore().put(SeleniumDriverManager.class.getCanonicalName(),
        new SeleniumDriverManager((() -> driver), this));
  }

  /**
   * Sets web driver using Supplier.
   *
   * @param webDriverSupplier the web driver supplier
   */
  public void setWebDriver(Supplier<WebDriver> webDriverSupplier) {
    this.getManagerStore().put(SeleniumDriverManager.class.getCanonicalName(),
        new SeleniumDriverManager(webDriverSupplier, this));
  }

}
