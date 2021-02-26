/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.selenium;

import com.magenic.jmaqs.base.BaseGenericTest;
import com.magenic.jmaqs.utilities.helper.TestCategories;
import java.util.function.Supplier;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * The type Selenium driver manager test.
 */
public class SeleniumDriverManagerUnitTest extends BaseGenericTest {

  /**
   * The Get driver.
   */
  private final Supplier<WebDriver> getDriver = () -> {
    WebDriver driver = null;
    try {
      driver = WebDriverFactory.getDefaultBrowser();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return driver;
  };

  /**
   * Test close not initialized.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void testCloseNotInitialized() {
    SeleniumDriverManager seleniumDriverManager = new SeleniumDriverManager(getDriver, this.getTestObject());
    Assert.assertFalse(seleniumDriverManager.isDriverInitialized(),
        "Checking that driver is not initialized - Pre Close");
    seleniumDriverManager.close();
    Assert.assertFalse(seleniumDriverManager.isDriverInitialized(),
        "Checking that driver is not initialized - Post Close");
  }

  /**
   * Test close initialized.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void testCloseInitialized() {
    SeleniumDriverManager seleniumDriverManager = new SeleniumDriverManager(getDriver, this.getTestObject());
    WebDriver webDriver = seleniumDriverManager.getWebDriver();
    webDriver.quit();
    Assert.assertTrue(seleniumDriverManager.isDriverInitialized(), "Checking that driver is initialized");
    seleniumDriverManager.close();
    Assert.assertFalse(seleniumDriverManager.isDriverInitialized(), "Checking that driver is not initialized");
  }

  /**
   * Test get web driver.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void testGetWebDriver() {
    try (SeleniumDriverManager seleniumDriverManager = new SeleniumDriverManager(getDriver, this.getTestObject())) {
      WebDriver webDriver = seleniumDriverManager.getWebDriver();
      Assert.assertNotNull(webDriver, "Checking that driver is not null");
    }
  }

  /**
   * Test log verbose.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void testLogVerbose() {
    try (SeleniumDriverManager seleniumDriverManager = new SeleniumDriverManager(getDriver, this.getTestObject())) {
      // List list;
      seleniumDriverManager.logVerbose("Logging verbose messaging");
    }
  }

    /**
   * Test creating driver manager with instantiated web driver .
   */
  @Test(groups = TestCategories.SELENIUM)
  public void testDriverManWithInstantiatedDriver() {
    try (SeleniumDriverManager seleniumDriverManager = new SeleniumDriverManager(getDriver.get(), this.getTestObject())) {
      seleniumDriverManager.logVerbose("Run with new driver");
    }
  }
}