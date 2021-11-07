/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.selenium;

import com.magenic.jmaqs.selenium.factories.FluentWaitFactory;
import com.magenic.jmaqs.selenium.factories.UIWaitFactory;
import com.magenic.jmaqs.selenium.pageModels.AutomationPageModel;
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
   * Test get fluent wait object.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void testGetFluentWaitObject() {
    int timeout = 1000;
    int polling = 500;

    AutomationPageModel automationPageModel = new AutomationPageModel(this.getTestObject());
    automationPageModel.open(automationPageModel.testSiteAutomationUrl);
    WebElement elementDriver = UIWaitFactory.getWaitDriver(automationPageModel.getWebDriver())
        .waitForClickableElement(automationPageModel.automationShowDialog1);

    FluentWait<WebElement> fluentWait = FluentWaitFactory
        .getNewElementFluentWait(elementDriver, timeout, polling);

    String assertNotNullErrorTemplate = "The %s was null when it was expected to not be.";
    Assert.assertNotNull(fluentWait, String.format(assertNotNullErrorTemplate, "fluentWait"));
  }
}