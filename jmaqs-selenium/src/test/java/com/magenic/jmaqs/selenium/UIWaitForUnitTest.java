/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.selenium;

import com.magenic.jmaqs.selenium.factories.UIWaitFactory;
import com.magenic.jmaqs.utilities.helper.TestCategories;
import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UIWaitForUnitTest extends BaseSeleniumTest {

  /**
   * Unit testing site URL - Login page.
   */
  private final String testSiteUrl = SeleniumConfig.getWebSiteBase();

  /**
   * Unit testing site URL - IFrame page.
   */
  private final String testSiteIFrameUrl = testSiteUrl + "Automation/iFramePage";

  /**
   * Unit testing site URL - Async page.
   */
  private final String testSiteAsyncUrl = testSiteUrl + "Automation/AsyncPage";

  /**
   * Unit testing site URL - Automation page.
   */
  private final String testSiteAutomationUrl = testSiteUrl + "Automation/";

  /**
   * the IFrame element with the source.
   */
  private final By iFrameLocator = By.cssSelector("#mageniciFrame");

  /**
   * Flower table title.
   */
  private final By flowerTableTitle = By.cssSelector("#FlowerTable > caption > strong");

  /**
   * Asynchronous div that loads after a delay on Async Testing Page.
   */
  private final By asyncLoadingTextDiv = By.cssSelector("#loading-div-text");

  /**
   * Food table.
   */
  private final By foodTable = By.cssSelector("#FoodTable");

  /**
   * Home button css selector.
   */
  private final By homeButton = By.cssSelector("#homeButton > a");

  /**
   * Flower table.
   */
  private final By flowerTable = By.cssSelector("#FlowerTable TD");

  /**
   * Selector that is not in page.
   */
  private final By notInPage = By.cssSelector("NotInPage");

  /**
   * Dropdown selector.
   */
  private final By asyncDropdownCssSelector = By.cssSelector("#Selector");

  /**
   * Dropdown label.
   */
  private final By asyncOptionsLabel = By.cssSelector("#Label");

  /**
   * Names label.
   */
  private final By automationNamesLabel = By.cssSelector("#Dropdown > p > strong > label");

  /**
   * First dialog button.
   */
  private final By automationShowDialog1 = By.cssSelector("#showDialog1");

  /**
   * Tests the functionality that waits for the IFrame to load.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitForIFrameToLoad() {
    UIWait wait = navigateToTestSite(testSiteIFrameUrl);
    wait.waitForIframeToLoad(iFrameLocator);
  }

  /**
   * Tests the functionality that waits for the IFrame to load.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitForIFrameToLoadWithTimeOut() {
    UIWait wait = navigateToTestSite(testSiteIFrameUrl);
    wait.waitForIframeToLoad(iFrameLocator, 10000, 500);
  }

  /**
   * Tests the functionality that waits for the attribute texts equals.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitForAttributeTextEqualsFound() {
    UIWait wait = navigateToTestSite(testSiteAsyncUrl);
    WebElement element = wait.waitForAttributeTextEquals(asyncLoadingTextDiv, "style", "display: block;");
    Assert.assertNotNull(element);
    Assert.assertEquals(element.getText(), "Loaded");
  }

  /**
   * Tests the functionality that waits for a clickable element scrolls into view.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitForClickableElementAndScrollIntoView() {
    UIWait wait = navigateToTestSite(testSiteAutomationUrl);
    Assert.assertNotNull(wait.waitForClickableElementAndScrollIntoView(automationShowDialog1));
    Assert.assertNotNull(wait.waitForClickableElementAndScrollIntoView(automationShowDialog1, 10000, 1000));
  }

  /**
   * Tests the functionality that waits for the present element.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitForPresentElement() {
    UIWait wait = navigateToTestSite(testSiteAutomationUrl);
    Assert.assertNotNull(wait.waitForPresentElement(flowerTableTitle));
    Assert.assertNotNull(wait.waitForPresentElement(flowerTableTitle, 10000, 1000));
  }

  /**
   * Tests the functionality that waits for elements.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitForElements() {
    UIWait wait = navigateToTestSite(testSiteAutomationUrl);
    Assert.assertEquals(wait.waitForElements(flowerTable).size(), 20);
    Assert.assertEquals(wait.waitForElements(flowerTable, 10000, 1000).size(), 20);
  }

  /**
   * Tests the functionality that waits for an enabled element.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitForEnabledElement() {
    UIWait wait = navigateToTestSite(testSiteAutomationUrl);
    Assert.assertNotNull(wait.waitForEnabledElement(flowerTableTitle));
    Assert.assertNotNull(wait.waitForEnabledElement(flowerTableTitle, 10000, 1000));
  }

  /**
   * Verify WaitForClickableElement wait works.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitForClickableElement() {
    UIWait wait = navigateToTestSite(testSiteUrl);
    WebElement element = wait.waitForClickableElement(homeButton);
    Assert.assertNotNull(element, "Null element was returned");
    element = wait.waitForClickableElement(homeButton, 10000, 1000);
    Assert.assertNotNull(element, "Null element was returned");
  }

  /**
   * Verify WaitForVisibleElement wait works.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitForVisibleElement() {
    UIWait wait = navigateToTestSite(testSiteAsyncUrl);
    WebElement element = wait.waitForVisibleElement(asyncDropdownCssSelector);
    Assert.assertNotNull(element, "Null element was returned");
    element = wait.waitForVisibleElement(asyncDropdownCssSelector, 10000, 1000);
    Assert.assertNotNull(element, "Null element was returned");
  }

  /**
   * Verify WaitForExactText wait works.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitForExactText() {
    UIWait wait = navigateToTestSite(testSiteAsyncUrl);
    WebElement element = wait.waitForExactText(asyncOptionsLabel, "Options");
    Assert.assertNotNull(element, "Null element was returned");
  }

  /**
   * Verify WaitForContainsText wait works.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitForContainsText() {
    UIWait wait = navigateToTestSite(testSiteAutomationUrl);
    WebElement element = wait.waitForContainsText(automationNamesLabel, "Name");
    Assert.assertNotNull(element, "Null element was returned");
  }

  /**
   * Verify WaitForContainsText wait does not work.
   */
  @Test(groups = TestCategories.SELENIUM, expectedExceptions = NotFoundException.class)
  public void waitForContainsTextException() {
    UIWait wait = navigateToTestSite(testSiteAutomationUrl);
    wait.waitForContainsText(notInPage, "Name");
  }

  /**
   * Verify WaitForAbsentElement wait works.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitForAbsentElement() {
    UIWait wait = navigateToTestSite(testSiteUrl);
    wait.waitForAbsentElement(notInPage);
  }

  /**
   * Verify WaitForAbsentElement wait fails.
   */
  @Test(groups = TestCategories.SELENIUM, expectedExceptions = TimeoutException.class)
  public void waitForAbsentElementFail() {
    UIWait wait = navigateToTestSite(testSiteUrl);
    wait.waitForAbsentElement(homeButton);
  }

  /**
   * Verify WaitForPageLoad wait works.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitForPageLoad() {
    UIWait wait = navigateToTestSite(testSiteUrl);
    wait.waitForPageLoad();
  }

  /**
   * Verify that WaitForAttributeTextEquals throws an exception for instances
   * where the attribute is not found. An attribute check that should have failed
   * to find the given string equal to an elements attribute passed
   */
  @Test(groups = TestCategories.SELENIUM, expectedExceptions = NotFoundException.class)
  public void waitForAttributeEqualsNotFound() {
    UIWait wait = navigateToTestSite(testSiteAutomationUrl);
    WebElement element = wait.waitForAttributeTextEquals(foodTable, "Flower Table", "Summary");
    Assert.assertEquals(element.getAttribute("Text"), "Flower Table");
  }

  /**
   * Verify that WaitForAttributeTextEquals can find an attribute value after
   * waiting.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitForAttributeEqualsFound() {
    UIWait wait = navigateToTestSite(testSiteAsyncUrl);
    Assert.assertNotNull(wait.waitForAttributeTextEquals(asyncLoadingTextDiv, "style", "display: block;"));
    Assert.assertNotNull(wait.waitForAttributeTextEquals(asyncLoadingTextDiv, "style", "display: block;", 10000, 1000));
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
