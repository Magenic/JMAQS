/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.selenium;

import com.magenic.jmaqs.selenium.factories.FluentWaitFactory;
import com.magenic.jmaqs.selenium.factories.UIWaitFactory;
import com.magenic.jmaqs.selenium.unittestpagemodel.PageElementsPageModel;
import com.magenic.jmaqs.utilities.helper.TestCategories;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * The type Fluent wait factory unit test.
 */
public class FluentWaitFactoryUnitTest extends BaseSeleniumTest {

  /**
   * Url for the site.
   */
  private static final String siteUrl = SeleniumConfig.getWebSiteBase();

  /**
   * Automation site url.
   */
  private static final String siteAutomationUrl = siteUrl + "Automation/";

  /**
   * Test get fluent wait object.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void testGetFluentWaitObject() {
    int timeout = 1000;
    int polling = 500;

    PageElementsPageModel pageModel = new PageElementsPageModel(this.getTestObject());
    pageModel.open(siteAutomationUrl);
    WebElement elementDriver = UIWaitFactory.getWaitDriver(pageModel.getWebDriver())
        .waitForClickableElement(pageModel.showDialog1ButtonLocator);

    FluentWait<WebElement> fluentWait = FluentWaitFactory
        .getNewElementFluentWait(elementDriver, timeout, polling);

    String assertNotNullErrorTemplate = "The %s was null when it was expected to not be.";
    Assert.assertNotNull(fluentWait, String.format(assertNotNullErrorTemplate, "fluentWait"));
  }
}