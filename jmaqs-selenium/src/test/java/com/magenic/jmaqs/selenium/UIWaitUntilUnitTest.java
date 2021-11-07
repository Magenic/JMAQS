/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.selenium;

import com.magenic.jmaqs.selenium.factories.UIWaitFactory;
import com.magenic.jmaqs.selenium.pageModels.AsyncPageModel;
import com.magenic.jmaqs.selenium.pageModels.AutomationPageModel;
import com.magenic.jmaqs.selenium.pageModels.IFramePageModel;
import com.magenic.jmaqs.utilities.helper.TestCategories;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * The UI wait until class functionality unit test.
 */
public class UIWaitUntilUnitTest extends BaseSeleniumTest {

  /**
   * the Automation page model.
   */
  private AutomationPageModel automationPageModel;

  /**
   * the Async page model.
   */
  private AsyncPageModel asyncPageModel;

  /**
   * the IFrame page model.
   */
  private IFramePageModel iframePageModel;

  /**
   * Sets up the page models for the unit tests.
   */
  public void setUp() {
    automationPageModel = new AutomationPageModel(this.getTestObject());
    asyncPageModel = new AsyncPageModel(this.getTestObject());
    iframePageModel = new IFramePageModel(this.getTestObject());
  }

  /**
   * Tests the functionality that waits until the IFrame to load.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitUntilIFrameToLoad() {
    setUp();
    UIWait wait = navigateToTestSite(iframePageModel.testSiteIFrameUrl);
    Assert.assertTrue(wait.waitUntilIframeToLoad(iframePageModel.iframeLocator));
  }

  /**
   * Tests the functionality that waits until the IFrame to load.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitUntilIFrameToLoadException() {
    setUp();
    UIWait wait = navigateToTestSite(iframePageModel.testSiteIFrameUrl);
    Assert.assertFalse(wait.waitUntilIframeToLoad(iframePageModel.notInPage));
  }

  /**
   * Tests the functionality that waits until the IFrame to load.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitUntilIFrameToLoadWithTimeOut() {
    setUp();
    UIWait wait = navigateToTestSite(iframePageModel.testSiteIFrameUrl);
    Assert.assertTrue(wait.waitUntilIframeToLoad(iframePageModel.iframeLocator, 10000, 500));
  }

  /**
   * Tests the functionality that waits until the attribute texts equals.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitUntilAttributeTextEquals() {
    setUp();
    UIWait wait = navigateToTestSite(asyncPageModel.testSiteAsyncUrl);
    Assert.assertTrue(wait.waitUntilAttributeTextEquals(asyncPageModel.asyncLoadingLabel, "style", "display: none;"));
    Assert.assertTrue(wait.waitUntilAttributeTextEquals(asyncPageModel.asyncLoadingLabel, "style", "display: none;", 10000, 2000));
  }

  /**
   * Tests the functionality that waits until an attribute contains text.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitUntilAttributeTextContainsFound() {
    setUp();
    UIWait wait = navigateToTestSite(asyncPageModel.testSiteAsyncUrl);
    Assert.assertTrue(wait.waitUntilAttributeTextContains(asyncPageModel.asyncDropdownCssSelector, "id", ""));
    Assert.assertTrue(wait.waitUntilAttributeTextContains(asyncPageModel.asyncDropdownCssSelector, "id", "", 10000, 1000));
  }

  /**
   * Verify that the WaitUntilAttributeTextContains method returns false for
   * objects that don't have this text inside attribute value within timeout.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitUntilAttributeTextContainsFalse() {
    setUp();
    UIWait wait = navigateToTestSite(asyncPageModel.testSiteAsyncUrl);
    Assert.assertFalse(wait.waitUntilAttributeTextContains(asyncPageModel.asyncDropdownCssSelector, "notTheRightId", "id"));
    Assert
        .assertFalse(wait.waitUntilAttributeTextContains(asyncPageModel.asyncDropdownCssSelector, "notTheRightId", "id", 10000, 1000));
  }

  /**
   * Verify that the WaitUntilAttributeTextContains method returns false for
   * objects that don't have this attribute value within timeout.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitUntilAttributeTextEqualsFalse() {
    setUp();
    UIWait wait = navigateToTestSite(asyncPageModel.testSiteAsyncUrl);
    Assert.assertFalse(wait.waitUntilAttributeTextEquals(asyncPageModel.asyncLoadingLabel, "display:", "style"));
    Assert.assertFalse(wait.waitUntilAttributeTextEquals(asyncPageModel.asyncLoadingLabel, "display:", "style", 10000, 1000));
  }

  /**
   * Tests the functionality that waits until a clickable element scrolls into
   * view.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitUntilClickableElementAndScrollIntoView() {
    setUp();
    UIWait wait = navigateToTestSite(automationPageModel.testSiteAutomationUrl);
    Assert.assertTrue(wait.waitUntilClickableElementAndScrollIntoView(automationPageModel.automationShowDialog1));
    Assert.assertTrue(wait.waitUntilClickableElementAndScrollIntoView(automationPageModel.automationShowDialog1, 10000, 1000));
  }

  /**
   * Tests the functionality that waits until an enabled element.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitUntilEnabledElement() {
    setUp();
    UIWait wait = navigateToTestSite(automationPageModel.testSiteAutomationUrl);
    Assert.assertTrue(wait.waitUntilEnabledElement(automationPageModel.flowerTableTitle));
    Assert.assertTrue(wait.waitUntilEnabledElement(automationPageModel.flowerTableTitle, 10000, 1000));
  }

  /**
   * Tests the functionality that waits until a disabled element.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitUntilDisabledElement() {
    setUp();
    UIWait wait = navigateToTestSite(automationPageModel.testSiteAutomationUrl);
    Assert.assertTrue(wait.waitUntilDisabledElement(automationPageModel.disabledField));
    Assert.assertTrue(wait.waitUntilDisabledElement(automationPageModel.disabledField, 10000, 1000));
  }

  /**
   * Test the functionality that resets the wait driver.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void resetWaitDriver() {
    setUp();
    UIWait wait = UIWaitFactory.getWaitDriver(this.getWebDriver());
    Assert.assertNotNull(wait.resetWaitDriver());
  }

  /**
   * Test the functionality that gets a new wait driver.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void getNewWaitDriver() {
    setUp();
    UIWait wait = navigateToTestSite(automationPageModel.testSiteUrl);
    Assert.assertNotNull(wait.getNewWaitDriver());
    Assert.assertNotNull(wait.getNewWaitDriver(10000));
    Assert.assertNotNull(wait.getNewWaitDriver(this.getTestObject().getWebDriver()));
    Assert.assertNotNull(wait.getNewWaitDriver(this.getTestObject().getWebDriver(), 10000, 1000));
  }



  /**
   * Verify WaitUntilPageLoad wait works.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitUntilPageLoad() {
    setUp();
    UIWait wait = navigateToTestSite(automationPageModel.testSiteUrl);
    Assert.assertTrue(wait.waitUntilPageLoad(), "Page failed to load");
  }

  /**
   * Verify WaitUntilClickableElement wait works.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitUntilClickableElement() {
    setUp();
    UIWait wait = navigateToTestSite(automationPageModel.testSiteAutomationUrl);
    Assert.assertTrue(wait.waitUntilClickableElement(automationPageModel.automationShowDialog1), "Failed to find element");
    Assert.assertTrue(wait.waitUntilClickableElement(automationPageModel.automationShowDialog1, 10000, 1000), "Failed to find element");
  }

  /**
   * Verify WaitUntilVisibleElement wait works.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitUntilVisibleElement() {
    setUp();
    UIWait wait = navigateToTestSite(automationPageModel.testSiteAutomationUrl);
    Assert.assertTrue(wait.waitUntilVisibleElement(automationPageModel.automationShowDialog1), "Failed to find element");
    Assert.assertTrue(wait.waitUntilVisibleElement(automationPageModel.automationShowDialog1, 10000, 1000), "Failed to find element");
  }

  /**
   * Verify WaitUntilExactText wait works.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitUntilExactText() {
    setUp();
    UIWait wait = navigateToTestSite(automationPageModel.testSiteAutomationUrl);
    Assert.assertTrue(wait.waitUntilExactText(automationPageModel.automationShowDialog1, "Show dialog"), "Failed to find element");
    Assert.assertTrue(wait.waitUntilExactText(automationPageModel.automationShowDialog1, "Show dialog", 10000, 1000),
        "Failed to find element");
  }

  /**
   * Verify WaitUntilExactText text does not match.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitUntilExactTextDoNotMatch() {
    setUp();
    UIWait wait = navigateToTestSite(automationPageModel.testSiteAutomationUrl);
    Assert.assertFalse(wait.waitUntilExactText(automationPageModel.automationShowDialog1, "Text"), "Failed to find element");
  }

  /**
   * Verify WaitUntilExactText text does not match.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitUntilExactTextNullElement() {
    setUp();
    UIWait wait = navigateToTestSite(automationPageModel.testSiteAutomationUrl);
    Assert.assertFalse(wait.waitUntilExactText(null, "Text"), "Failed to find element");
  }

  /**
   * Verify WaitUntilContainsText wait works.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitUntilContainsText() {
    setUp();
    UIWait wait = navigateToTestSite(automationPageModel.testSiteAutomationUrl);
    Assert.assertTrue(wait.waitUntilContainsText(automationPageModel.automationShowDialog1, "dialog"), "Failed to find element");
    Assert.assertTrue(wait.waitUntilContainsText(automationPageModel.automationShowDialog1, "dialog", 10000, 1000),
        "Failed to find element");
  }

  /**
   * Verify WaitUntilContainsText does not match.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitUntilContainsTextNotMatch() {
    UIWait wait = navigateToTestSite(automationPageModel.testSiteAutomationUrl);
    Assert.assertFalse(wait.waitUntilContainsText(automationPageModel.automationShowDialog1, "text"), "Failed to find element");
  }

  /**
   * Verify WaitUntilContainsText with null element.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitUntilContainsTextNullElement() {
    setUp();
    UIWait wait = navigateToTestSite(automationPageModel.testSiteAutomationUrl);
    Assert.assertFalse(wait.waitUntilContainsText(null, "text"), "Failed to find element");
  }

  /**
   * Test for the wait until absent.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitUntilAbsentElement() {
    setUp();
    UIWait wait = navigateToTestSite(automationPageModel.testSiteUrl);
    Assert.assertTrue(wait.waitUntilAbsentElement(automationPageModel.notInPage));
    Assert.assertTrue(wait.waitUntilAbsentElement(automationPageModel.notInPage, 10000, 1000));
  }

  /**
   * Verify that the WaitUntilAttributeTextEquals works with async objects.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitUntilAttributeEquals() {
    setUp();
    UIWait wait = navigateToTestSite(asyncPageModel.testSiteAsyncUrl);
    Assert.assertTrue(wait.waitUntilAttributeTextEquals(asyncPageModel.asyncLoadingLabel, "style", "display: none;"));
  }

  /**
   * Verify that the WaitUntilAttributeTextContains works with async objects.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitUntilAttributeContains() {
    setUp();
    UIWait wait = navigateToTestSite(asyncPageModel.testSiteAsyncUrl);
    Assert.assertTrue(wait.waitUntilAttributeTextContains(asyncPageModel.asyncLoadingLabel, "style", "none;"));
  }

  /**
   * Navigates to the specified test site.
   * Because many tests use different urls, this is not a setup method
   * @param url the url to be navigated to
   * @return a UIWait class for wait functionality
   */
  private UIWait navigateToTestSite(String url) {
    WebDriverFactory.setBrowserSize(this.getWebDriver(), "Maximize");
    this.getWebDriver().navigate().to(url);
    UIWaitFactory.getWaitDriver(this.getWebDriver()).waitForPageLoad();
    return UIWaitFactory.getWaitDriver(this.getWebDriver());
  }
}