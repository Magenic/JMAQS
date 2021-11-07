/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.selenium;

import com.magenic.jmaqs.selenium.factories.UIWaitFactory;
import com.magenic.jmaqs.selenium.pageModels.AsyncPageModel;
import com.magenic.jmaqs.selenium.pageModels.AutomationPageModel;
import com.magenic.jmaqs.selenium.pageModels.IFramePageModel;
import com.magenic.jmaqs.utilities.helper.TestCategories;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * The UI wait for class functionality unit test.
 */
public class UIWaitForUnitTest extends BaseSeleniumTest {

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
   * Tests the functionality that waits for the IFrame to load.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitForIFrameToLoad() {
    setUp();
    UIWait wait = navigateToTestSite(iframePageModel.testSiteIFrameUrl);
    wait.waitForIframeToLoad(iframePageModel.iframeLocator);
  }

  /**
   * Tests the functionality that waits for the IFrame to load.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitForIFrameToLoadWithTimeOut() {
    setUp();
    UIWait wait = navigateToTestSite(iframePageModel.testSiteIFrameUrl);
    wait.waitForIframeToLoad(iframePageModel.iframeLocator, 10000, 500);
  }

  /**
   * Tests the functionality that waits for the attribute texts equals.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitForAttributeTextEqualsFound() {
    setUp();
    UIWait wait = navigateToTestSite(asyncPageModel.testSiteAsyncUrl);
    WebElement element = wait.waitForAttributeTextEquals(asyncPageModel.asyncLoadingTextDiv, "style", "display: block;");
    Assert.assertNotNull(element);
    Assert.assertEquals(element.getText(), "Loaded");
  }

  /**
   * Tests the functionality that waits for a clickable element scrolls into view.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitForClickableElementAndScrollIntoView() {
    setUp();
    UIWait wait = navigateToTestSite(automationPageModel.testSiteAutomationUrl);
    Assert.assertNotNull(wait.waitForClickableElementAndScrollIntoView(automationPageModel.automationShowDialog1));
    Assert.assertNotNull(wait.waitForClickableElementAndScrollIntoView(automationPageModel.automationShowDialog1, 10000, 1000));
  }

  /**
   * Tests the functionality that waits for the present element.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitForPresentElement() {
    setUp();
    UIWait wait = navigateToTestSite(automationPageModel.testSiteAutomationUrl);
    Assert.assertNotNull(wait.waitForPresentElement(automationPageModel.flowerTableTitle));
    Assert.assertNotNull(wait.waitForPresentElement(automationPageModel.flowerTableTitle, 10000, 1000));
  }

  /**
   * Tests the functionality that waits for elements.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitForElements() {
    setUp();
    UIWait wait = navigateToTestSite(automationPageModel.testSiteAutomationUrl);
    Assert.assertEquals(wait.waitForElements(automationPageModel.flowerTable).size(), 20);
    Assert.assertEquals(wait.waitForElements(automationPageModel.flowerTable, 10000, 1000).size(), 20);
  }

  /**
   * Tests the functionality that waits for an enabled element.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitForEnabledElement() {
    setUp();
    UIWait wait = navigateToTestSite(automationPageModel.testSiteAutomationUrl);
    Assert.assertNotNull(wait.waitForEnabledElement(automationPageModel.flowerTableTitle));
    Assert.assertNotNull(wait.waitForEnabledElement(automationPageModel.flowerTableTitle, 10000, 1000));
  }

  /**
   * Verify WaitForClickableElement wait works.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitForClickableElement() {
    setUp();
    UIWait wait = navigateToTestSite(automationPageModel.testSiteUrl);
    WebElement element = wait.waitForClickableElement(automationPageModel.homeButton);
    Assert.assertNotNull(element, "Null element was returned");
    element = wait.waitForClickableElement(automationPageModel.homeButton, 10000, 1000);
    Assert.assertNotNull(element, "Null element was returned");
  }

  /**
   * Verify WaitForVisibleElement wait works.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitForVisibleElement() {
    setUp();
    UIWait wait = navigateToTestSite(asyncPageModel.testSiteAsyncUrl);
    WebElement element = wait.waitForVisibleElement(asyncPageModel.asyncDropdownCssSelector);
    Assert.assertNotNull(element, "Null element was returned");
    element = wait.waitForVisibleElement(asyncPageModel.asyncDropdownCssSelector, 10000, 1000);
    Assert.assertNotNull(element, "Null element was returned");
  }

  /**
   * Verify WaitForExactText wait works.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitForExactText() {
    setUp();
    UIWait wait = navigateToTestSite(asyncPageModel.testSiteAsyncUrl);
    WebElement element = wait.waitForExactText(asyncPageModel.asyncOptionsLabel, "Options");
    Assert.assertNotNull(element, "Null element was returned");
  }

  /**
   * Verify WaitForContainsText wait works.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitForContainsText() {
    setUp();
    UIWait wait = navigateToTestSite(automationPageModel.testSiteAutomationUrl);
    WebElement element = wait.waitForContainsText(automationPageModel.automationNamesLabel, "Name");
    Assert.assertNotNull(element, "Null element was returned");
  }

  /**
   * Verify WaitForContainsText wait does not work.
   */
  @Test(groups = TestCategories.SELENIUM, expectedExceptions = NotFoundException.class)
  public void waitForContainsTextException() {
    setUp();
    UIWait wait = navigateToTestSite(automationPageModel.testSiteAutomationUrl);
    wait.waitForContainsText(automationPageModel.notInPage, "Name");
  }

  /**
   * Verify WaitForAbsentElement wait works.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitForAbsentElement() {
    setUp();
    UIWait wait = navigateToTestSite(automationPageModel.testSiteUrl);
    wait.waitForAbsentElement(automationPageModel.notInPage);
  }

  /**
   * Verify WaitForAbsentElement wait fails.
   */
  @Test(groups = TestCategories.SELENIUM, expectedExceptions = TimeoutException.class)
  public void waitForAbsentElementFail() {
    setUp();
    UIWait wait = navigateToTestSite(automationPageModel.testSiteUrl);
    wait.waitForAbsentElement(automationPageModel.homeButton);
  }

  /**
   * Verify WaitForPageLoad wait works.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitForPageLoad() {
    setUp();
    UIWait wait = navigateToTestSite(automationPageModel.testSiteUrl);
    wait.waitForPageLoad();
  }

  /**
   * Verify that WaitForAttributeTextEquals throws an exception for instances
   * where the attribute is not found. An attribute check that should have failed
   * to find the given string equal to an elements attribute passed
   */
  @Test(groups = TestCategories.SELENIUM, expectedExceptions = NotFoundException.class)
  public void waitForAttributeEqualsNotFound() {
    setUp();
    UIWait wait = navigateToTestSite(automationPageModel.testSiteAutomationUrl);
    WebElement element = wait.waitForAttributeTextEquals(automationPageModel.foodTable, "Flower Table", "Summary");
    Assert.assertEquals(element.getAttribute("Text"), "Flower Table");
  }

  /**
   * Verify that WaitForAttributeTextEquals can find an attribute value after
   * waiting.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitForAttributeEqualsFound() {
    setUp();
    UIWait wait = navigateToTestSite(asyncPageModel.testSiteAsyncUrl);
    Assert.assertNotNull(wait.waitForAttributeTextEquals(asyncPageModel.asyncLoadingTextDiv, "style", "display: block;"));
    Assert.assertNotNull(wait.waitForAttributeTextEquals(asyncPageModel.asyncLoadingTextDiv, "style", "display: block;", 10000, 1000));
  }

  /**
   * Navigates to the specified test site.
   * Because many tests use different urls, this is not a setup method
   * @param url the url to be navigated to
   * @return a UIWait class for wait functionality
   */
  private UIWait navigateToTestSite(String url) {
    setUp();
    WebDriverFactory.setBrowserSize(this.getWebDriver(), "Maximize");
    this.getWebDriver().navigate().to(url);
    UIWaitFactory.getWaitDriver(this.getWebDriver()).waitForPageLoad();
    return UIWaitFactory.getWaitDriver(this.getWebDriver());
  }
}