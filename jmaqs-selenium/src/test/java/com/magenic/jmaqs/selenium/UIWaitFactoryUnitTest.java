/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.selenium;

import com.magenic.jmaqs.selenium.factories.UIWaitFactory;
import com.magenic.jmaqs.selenium.unittestpagemodel.PageElementsPageModel;
import com.magenic.jmaqs.utilities.helper.TestCategories;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * The type Ui wait factory unit test.
 */
public class UIWaitFactoryUnitTest extends BaseSeleniumTest {

  /**
   * Url for the site.
   */
  private static final String siteUrl = SeleniumConfig.getWebSiteBase();

  /**
   * Automation site url.
   */
  private static final String siteAutomationUrl = siteUrl + "Automation/";

  /**
   * The constant assertEqualErrorTemplate.
   */
  private static final String assertEqualErrorTemplate = "%s was not equal to %s when they were expected to be.";
  /**
   * The constant assertNotEqualErrorTemplate.
   */
  private static final String assertNotEqualErrorTemplate = "%s was equal to %s when they were expected not to be.";

  /**
   * Gets wait driver test.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void getWaitDriverTest() {
    UIWait waitDriver = UIWaitFactory.getWaitDriver(this.getWebDriver());

    String assertNotNullErrorTemplate = "The %s was null when it was expected to not be.";
    Assert.assertNotNull(waitDriver, String.format(assertNotNullErrorTemplate, "waitDriver"));
  }

  /**
   * Gets wait driver test.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void getWaitDriverWithTimeoutTest() {
    UIWait waitDriver = UIWaitFactory.getWaitDriver(this.getWebDriver(), 5000, 1000);

    String assertNotNullErrorTemplate = "The %s was null when it was expected to not be.";
    Assert.assertNotNull(waitDriver, String.format(assertNotNullErrorTemplate, "waitDriver"));
  }

  /**
   * Gets wait driver when one exists.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void getWaitDriverWhenOneExists() {
    UIWait firstWaitDriver = UIWaitFactory.getWaitDriver(this.getWebDriver());
    UIWait secondWaitDriver = UIWaitFactory.getWaitDriver(this.getWebDriver());

    Assert.assertEquals(firstWaitDriver.getWaitDriver(), secondWaitDriver.getWaitDriver(),
        String.format(assertEqualErrorTemplate, "firstWaitDriver", "secondWaitDriver"));
  }

  /**
   * Sets wait driver when driver key does not exist.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void setWaitDriverWhenDriverKeyDoesNotExist() {
    UIWait waitDriver = new UIWait(this.getWebDriver());
    UIWaitFactory.setWaitDriver(this.getWebDriver(), waitDriver.getWaitDriver());

    UIWait existingDriver = UIWaitFactory.getWaitDriver(this.getWebDriver());

    Assert.assertEquals(waitDriver.getWaitDriver(), existingDriver.getWaitDriver(),
        String.format(assertEqualErrorTemplate, "waitDriver", "existingDriver"));
  }

  /**
   * Sets wait driver when driver key does exist.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void setWaitDriverWhenDriverKeyDoesExist() {
    UIWait oldWaitDriver = UIWaitFactory.getWaitDriver(this.getWebDriver());
    UIWait newWaitDriver = new UIWait(this.getWebDriver());

    UIWaitFactory.setWaitDriver(this.getWebDriver(), newWaitDriver.getWaitDriver());
    UIWait overriddenWaitDriver = UIWaitFactory.getWaitDriver(this.getWebDriver());

    Assert.assertEquals(newWaitDriver.getWaitDriver(), overriddenWaitDriver.getWaitDriver(),
        String.format(assertEqualErrorTemplate, "newWaitDriver", "overriddenWaitDriver"));
    Assert.assertNotEquals(oldWaitDriver.getWaitDriver(), overriddenWaitDriver.getWaitDriver(),
        String.format(assertNotEqualErrorTemplate, "oldWaitDriver", "overrideWaitDriver"));
  }

  /**
   * Gets wait driver with web element.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void getWaitDriverWithWebElement() {
    PageElementsPageModel pageModel = new PageElementsPageModel(this.getTestObject());
    pageModel.open(siteAutomationUrl);
    WebElement elementDriver = UIWaitFactory
        .getWaitDriver(pageModel.getWebDriver())
        .waitForClickableElement(pageModel.showDialog1ButtonLocator);

    UIWait waitDriver = UIWaitFactory.getWaitDriver(elementDriver);

    Assert.assertNotNull(waitDriver);
  }

  /**
   * Remove wait driver.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void removeWaitDriver() {
    UIWait waitDriverToBeRemoved = UIWaitFactory.getWaitDriver(this.getWebDriver());
    UIWaitFactory.removeWaitDriver(this.getWebDriver());

    UIWait newWaitDriver = UIWaitFactory.getWaitDriver(this.getWebDriver());

    Assert.assertNotEquals(waitDriverToBeRemoved, newWaitDriver,
        String.format(assertNotEqualErrorTemplate, "waitDriverToBeRemoved", "newWaitDriver"));
  }

}
