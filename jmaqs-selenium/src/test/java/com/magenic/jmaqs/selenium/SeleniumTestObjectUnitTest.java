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
   * Test appium test object creation with driver.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void testSeleniumTestObjectCreationWithDriver() {

    WebDriver defaultBrowser = null;
    try {
      defaultBrowser = WebDriverFactory.getDefaultBrowser();
    } catch (Exception e) {
      e.printStackTrace();
      Assert.fail("Driver creation failed. ");
    }
    SeleniumTestObject testObject = new SeleniumTestObject(defaultBrowser, this.getLogger(),
        this.getFullyQualifiedTestClassName());

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

  @Test(groups = TestCategories.SELENIUM)
  public void testGetWebDriver() {
    WebDriver defaultBrowser = null;
    try {
      defaultBrowser = WebDriverFactory.getDefaultBrowser();
    } catch (Exception e) {
      e.printStackTrace();
      Assert.fail("Driver creation failed. ");
    }

    try (SeleniumTestObject testObject = new SeleniumTestObject(defaultBrowser, this.getLogger(),
        this.getFullyQualifiedTestClassName())) {
      WebDriver webDriver = testObject.getWebDriver();
      Assert.assertNotNull(webDriver, "Checking that selenium driver can be retrieved from test object");
    } finally {
      defaultBrowser.quit();
    }
  }

  @Test(groups = TestCategories.SELENIUM)
  public void testGetWebManager() {
    WebDriver defaultBrowser = null;
    try {
      defaultBrowser = WebDriverFactory.getDefaultBrowser();
    } catch (Exception e) {
      e.printStackTrace();
      Assert.fail("Driver creation failed. ");
    }
    try (SeleniumTestObject testObject = new SeleniumTestObject(defaultBrowser, this.getLogger(),
        this.getFullyQualifiedTestClassName())) {
      SeleniumDriverManager webManager = testObject.getWebManager();
      Assert.assertNotNull(webManager, "Checking that selenium driver manager can be retrieved from test object");
    } finally {
      defaultBrowser.quit();
    }
  }

  @Test(groups = TestCategories.SELENIUM)
  public void testSetWebDriver() {
    WebDriver defaultBrowser = null;
    try {
      defaultBrowser = WebDriverFactory.getDefaultBrowser();
    } catch (Exception e) {
      e.printStackTrace();
      Assert.fail("Driver creation failed. ");
    }
    try (SeleniumTestObject testObject = new SeleniumTestObject(defaultBrowser, this.getLogger(),
        this.getFullyQualifiedTestClassName())) {
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
      defaultBrowser.quit();
    }
  }

  @Test(groups = TestCategories.SELENIUM)
  public void testSetWebDriverSupplier() {
    WebDriver defaultBrowser = null;
    try {
      defaultBrowser = WebDriverFactory.getDefaultBrowser();
    } catch (Exception e) {
      e.printStackTrace();
      Assert.fail("Driver creation failed. ");
    }
    WebDriver finalDefaultBrowser = defaultBrowser;
    try (SeleniumTestObject testObject = new SeleniumTestObject((() -> finalDefaultBrowser), this.getLogger(),
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
      defaultBrowser.quit();
    }
  }
}