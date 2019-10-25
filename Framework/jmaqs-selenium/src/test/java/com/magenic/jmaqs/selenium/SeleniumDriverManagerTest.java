/*
 * Copyright 2019 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.selenium;

import com.magenic.jmaqs.base.BaseTestObject;
import com.magenic.jmaqs.utilities.logging.ConsoleLogger;
import com.magenic.jmaqs.utilities.logging.Logger;
import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Supplier;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * The type Selenium driver manager test.
 */
public class SeleniumDriverManagerTest {

  private BaseTestObject baseTestObject;
  private String methodName;

  /**
   * Sets .
   *
   * @param method the method
   */
  @BeforeMethod
  public void setup(Method method) {
    Logger logger = new ConsoleLogger();
    methodName = method.getDeclaringClass().getName();
    this.baseTestObject = new BaseTestObject(logger, methodName);
  }

  private final Supplier<Object> getDriver = () -> {
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
  @Test
  public void testCloseNotInitialized() {
    SeleniumDriverManager seleniumDriverManager = new SeleniumDriverManager(getDriver,
        baseTestObject);
    Assert.assertFalse(seleniumDriverManager.isDriverInitialized(),
        "Checking that driver is not initialized - Pre Close");
    seleniumDriverManager.close();
    Assert.assertFalse(seleniumDriverManager.isDriverInitialized(),
        "Checking that driver is not initialized - Post Close");
  }

  /**
   * Test close initialized.
   */
  @Test
  public void testCloseInitialized() {

    SeleniumDriverManager seleniumDriverManager = new SeleniumDriverManager(getDriver,
        baseTestObject);
    WebDriver webDriver = seleniumDriverManager.getWebDriver();
    webDriver.quit();
    Assert.assertTrue(seleniumDriverManager.isDriverInitialized(),
        "Checking that driver is initialized");
    seleniumDriverManager.close();
    Assert.assertFalse(seleniumDriverManager.isDriverInitialized(),
        "Checking that driver is not initialized");
  }

  /**
   * Test get web driver.
   */
  @Test
  public void testGetWebDriver() {
    SeleniumDriverManager seleniumDriverManager = new SeleniumDriverManager(getDriver,
        baseTestObject);
    WebDriver webDriver = seleniumDriverManager.getWebDriver();
    Assert.assertNotNull(webDriver, "Checking that driver is not null");
  }

  /**
   * Test log verbose.
   */
  @Test
  @Ignore("Ignore until logger rework")
  public void testLogVerbose() {
    SeleniumDriverManager seleniumDriverManager = new SeleniumDriverManager(getDriver,
        baseTestObject);
    List list;
    seleniumDriverManager.logVerbose("Logging verbose messaging");

  }
}