/*
 * Copyright 2020 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.selenium;

import com.magenic.jmaqs.utilities.helper.TestCategories;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * The type Base selenium test unit test.
 */
public class BaseSeleniumTestUnitTest extends BaseSeleniumTest {

  @Test(groups = TestCategories.SELENIUM)
  public void testGetWebDriver() {
    Assert.assertNotNull(this.getWebDriver(),
        "Checking that Selenium Driver is not null through BaseSeleniumTest");
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
    Assert.assertNotNull(this.getTestObject(),
        "Checking that Selenium Test Object is not null through BaseSeleniumTest");
  }

  @Test(groups = TestCategories.SELENIUM)
  public void testGetBrowser() {
    try {
      Assert.assertNotNull(this.getBrowser(),
          "Checking that Selenium Driver is not null through BaseSeleniumTest");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}