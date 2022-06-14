/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.selenium;

import com.magenic.jmaqs.utilities.helper.TestCategories;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * The type Base selenium test unit test.
 */
public class BaseSeleniumTestUnitTest extends BaseSeleniumTest {

  @Test(groups = TestCategories.SELENIUM)
  public void testGetWebDriver() {
    Assert.assertNotNull(this.getWebDriver(), "Checking that Selenium Driver is not null through BaseSeleniumTest");
  }

  @Test(groups = TestCategories.SELENIUM)
  public void testSetWebDriver() {
    int hashCode = this.getWebDriver().hashCode();
    try {
      this.setWebDriver(this.getBrowser());
    } catch (Exception e) {
      e.printStackTrace();
    }
    int hashCode1 = this.getWebDriver().hashCode();
    Assert.assertNotEquals(hashCode, hashCode1);
  }

  @Test(groups = TestCategories.SELENIUM)
  public void testGetSeleniumTestObject() {
    Assert
        .assertNotNull(this.getTestObject(), "Checking that Selenium Test Object is not null through BaseSeleniumTest");
  }

  @Test(groups = TestCategories.SELENIUM)
  public void testGetBrowser() {

    WebDriver driver = null;

    try {
      driver = this.getBrowser();
      Assert.assertNotNull(driver, "Checking that Selenium Driver is not null through BaseSeleniumTest");
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
        driver.quit();
    }
  }

  @DataProvider(name = "data")
  public Object[][] getData() {
    return new Object[][] { { "First" }, { "Second" }, { "Third" } };
  }

  /**
   * Verify issue is resolved with upcasting the BaseTestObject to one of
   * it's concrete implementation when using TestNG Data providers.
   *
   * @param data The data being provided for each test
   * @see <a href=https://github.com/Magenic/JMAQS/issues/314>JMAQS github issue 314</a>
   */
  @Test(dataProvider = "data", groups = TestCategories.SELENIUM)
  public void testUpcastingToSeleniumTestObjectAfterDataProviderIteration(String data) {
    Assert.assertNotNull(data);
    Assert.assertTrue(this.getTestObject() instanceof SeleniumTestObject);
  }
}