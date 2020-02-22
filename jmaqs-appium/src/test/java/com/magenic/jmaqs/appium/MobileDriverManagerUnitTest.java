/*
 * Copyright 2020 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.appium;

import com.magenic.jmaqs.base.BaseGenericTest;
import com.magenic.jmaqs.base.BaseTest;
import io.appium.java_client.AppiumDriver;
import java.util.function.Supplier;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.Test;

/**
 * The type Mobile driver manager unit test.
 */
public class MobileDriverManagerUnitTest extends BaseGenericTest {
  /**
   * Test get mobile driver.
   */
  @Test
  public void testGetMobileDriver() {
    Supplier<AppiumDriver<WebElement>> supplier = AppiumDriverFactory::getDefaultMobileDriver;
    MobileDriverManager mobileDriverManager = new MobileDriverManager(supplier,
        this.getTestObject());

    Assert.assertNotNull(mobileDriverManager.getMobileDriver(),
        "Expected Mobile Driver to not be null.");
  }

  /**
   * Test close.
   */
  @Test
  public void testClose() {
    Supplier<AppiumDriver<WebElement>> supplier = AppiumDriverFactory::getDefaultMobileDriver;
    MobileDriverManager mobileDriverManager = new MobileDriverManager(supplier,
        this.getTestObject());

    mobileDriverManager.close();
    Assert.assertNull(mobileDriverManager.getBaseDriver(), "Expected Mobile Driver to be null.");
  }

  /**
   * Test close null base driver.
   */
  @Test
  public void testCloseNullBaseDriver() {
    Supplier<AppiumDriver<WebElement>> supplier = AppiumDriverFactory::getDefaultMobileDriver;
    MobileDriverManager mobileDriverManager = new MobileDriverManager(supplier,
        this.getTestObject());

    // Close once to make Base Driver null
    mobileDriverManager.close();

    // Close again with driver not initialized
    mobileDriverManager.close();

    Assert.assertNull(mobileDriverManager.getBaseDriver(), "Expected Base Driver to be null.");

  }
}
