/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.selenium;

import com.magenic.jmaqs.base.BaseGenericTest;
import com.magenic.jmaqs.utilities.helper.TestCategories;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * The type Selenium test object test.
 */
public class SeleniumTestObjectUnitTest extends BaseGenericTest {

  /**
   * The default browser that is set in the test.
   */
  private WebDriver defaultBrowser = null;

  /**
   * Test appium test object creation with driver.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void testSeleniumTestObjectCreationWithDriver() {
    defaultBrowser = setDefaultBrowser();
    SeleniumTestObject testObject = new SeleniumTestObject(
        defaultBrowser, this.getLogger(), this.getFullyQualifiedTestClassName());

    defaultBrowser.quit();
    Assert.assertNotNull(testObject, "Checking that selenium test object via driver is not null");
  }

  /**
   * Test appium test object creation with supplier.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void testSeleniumTestObjectCreationWithSupplier() {
    try (SeleniumTestObject testObject = new SeleniumTestObject(() -> {
      try {
        return WebDriverFactory.getDefaultBrowser();
      } catch (Exception e) {
        Assert.fail("Driver creation failed.");
      }
      return null;
    }, this.getLogger(), this.getFullyQualifiedTestClassName())) {
      Assert.assertNotNull(testObject, "Checking that selenium test object via supplier is not null");
    }
  }

  /**
   * Test getting the web driver.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void testGetWebDriver() {
    defaultBrowser = setDefaultBrowser();

    try (SeleniumTestObject testObject = new SeleniumTestObject(
        defaultBrowser, this.getLogger(), this.getFullyQualifiedTestClassName())) {
      WebDriver webDriver = testObject.getWebDriver();
      Assert.assertNotNull(webDriver, "Checking that selenium driver can be retrieved from test object");
    } finally {
      if (defaultBrowser != null) {
        defaultBrowser.quit();
      }
    }
  }

  /**
   * Test getting the web manager.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void testGetWebManager() {
    defaultBrowser = setDefaultBrowser();

    try (SeleniumTestObject testObject = new SeleniumTestObject(
        defaultBrowser, this.getLogger(), this.getFullyQualifiedTestClassName())) {
      SeleniumDriverManager webManager = testObject.getWebManager();
      Assert.assertNotNull(webManager, "Checking that selenium driver manager can be retrieved from test object");
    } finally {
      if (defaultBrowser != null) {
        defaultBrowser.quit();
      }
    }
  }

  /**
   * Tests setting the web driver.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void testSetWebDriver() {
    defaultBrowser = setDefaultBrowser();

    try (SeleniumTestObject testObject = new SeleniumTestObject(
        defaultBrowser, this.getLogger(), this.getFullyQualifiedTestClassName())) {
      int hashCode = testObject.getWebDriver().hashCode();

      try {
        testObject.setWebDriver(WebDriverFactory.getDefaultBrowser());
      } catch (Exception e) {
        e.printStackTrace();
        Assert.fail("2nd Driver creation failed. ");
      }

      int hashCode1 = testObject.getWebDriver().hashCode();

      Assert.assertNotEquals(hashCode, hashCode1,
          String.format("Checking that the selenium driver is not the same as previous version.  First: %d, Second: %d",
              hashCode, hashCode1));
    } finally {
      if (defaultBrowser != null) {
        defaultBrowser.quit();
      }
    }
  }

  /**
   * Test setting the web driver supplier.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void testSetWebDriverSupplier() {
    defaultBrowser = setDefaultBrowser();

    try (SeleniumTestObject testObject = new SeleniumTestObject((() -> defaultBrowser), this.getLogger(),
        this.getFullyQualifiedTestClassName())) {
      int hashCode = testObject.getWebDriver().hashCode();
      try {
        testObject.setWebDriver(() -> {
          try {
            return WebDriverFactory.getDefaultBrowser();
          } catch (Exception e) {
            e.printStackTrace();
          }
          Assert.fail("2nd Driver creation failed. ");
          return null;
        });
      } catch (Exception e) {
        e.printStackTrace();
        Assert.fail("2nd Driver creation failed. ");
      }

      int hashCode1 = testObject.getWebDriver().hashCode();

      Assert.assertNotEquals(hashCode, hashCode1,
          String.format("Checking that the selenium driver is not the same as previous version.  First: %d, Second: %d",
              hashCode, hashCode1));
    } finally {
      if (defaultBrowser != null) {
        defaultBrowser.quit();
      }
    }
  }

  /**
   * Sets the default browser from the web driver factory.
   * @return the default web driver
   */
  private WebDriver setDefaultBrowser() {
    try {
      return WebDriverFactory.getDefaultBrowser();
    } catch (Exception e) {
      Assert.fail("Driver creation failed.");
    }
    return null;
  }
}