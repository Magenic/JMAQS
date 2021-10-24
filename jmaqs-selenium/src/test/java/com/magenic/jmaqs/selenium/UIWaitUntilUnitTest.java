/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.selenium;

import com.magenic.jmaqs.selenium.factories.UIWaitFactory;
import com.magenic.jmaqs.utilities.helper.TestCategories;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * The UI wait class functionality unit test.
 */
public class UIWaitUntilUnitTest extends BaseSeleniumTest {

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
   * Dropdown selector.
   */
  private final By asyncDropdownCssSelector = By.cssSelector("#Selector");

  /**
   * Dropdown label - hidden once dropdown loads.
   */
  private final By asyncLoadingLabel = By.cssSelector("#LoadingLabel");

  /**
   * Selector that is not in page.
   */
  private final By notInPage = By.cssSelector("NotInPage");

  /**
   * First dialog button.
   */
  private final By automationShowDialog1 = By.cssSelector("#showDialog1");

  /**
   * Flower table title.
   */
  private final By flowerTableTitle = By.cssSelector("#FlowerTable > caption > strong");

  /**
   * Gets the disabled item.
   */
  private final By disabledField = By.cssSelector("#disabledField > INPUT");

  /**
   * the IFrame element with the source.
   */
  private final By magenicIFrameLocator = By.cssSelector("#mageniciFrame");

  /**
   * Tests the functionality that waits until the IFrame to load.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitUntilIFrameToLoad() {
    this.getWebDriver().navigate().to(testSiteIFrameUrl);
    UIWait wait = UIWaitFactory.getWaitDriver(this.getWebDriver());
    wait.waitForPageLoad();
    WebDriverFactory.setBrowserSize(this.getWebDriver(), "Maximize");
    Assert.assertTrue(wait.waitUntilIframeToLoad(magenicIFrameLocator));
  }

  /**
   * Tests the functionality that waits until the IFrame to load.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitUntilIFrameToLoadWithTimeOut() {
    this.getWebDriver().navigate().to(testSiteIFrameUrl);
    UIWait wait = UIWaitFactory.getWaitDriver(this.getWebDriver());
    wait.waitForPageLoad();
    WebDriverFactory.setBrowserSize(this.getWebDriver(), "Maximize");
    Assert.assertTrue(wait.waitUntilIframeToLoad(magenicIFrameLocator, 5000, 1000));
  }

  /**
   * Tests the functionality that waits until the attribute texts equals.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitUntilAttributeTextEquals() {
    this.getWebDriver().navigate().to(testSiteAsyncUrl);
    UIWait wait = UIWaitFactory.getWaitDriver(this.getWebDriver());
    Assert.assertTrue(wait.waitUntilAttributeTextEquals(asyncLoadingLabel, "style", "display: none;"));
    Assert.assertTrue(wait.waitUntilAttributeTextEquals(asyncLoadingLabel, "style", "display: none;", 10000, 2000));
  }

  /**
   * Tests the functionality that waits until an attribute contains text.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitUntilAttributeTextContainsFound() {
    this.getWebDriver().navigate().to(testSiteAsyncUrl);
    UIWait wait = UIWaitFactory.getWaitDriver(this.getWebDriver());
    Assert.assertTrue(wait.waitUntilAttributeTextContains(asyncDropdownCssSelector, "id", ""));
    Assert.assertTrue(wait.waitUntilAttributeTextContains(asyncDropdownCssSelector, "id", "", 10000, 1000));
  }

  /**
   * Verify that the WaitUntilAttributeTextContains method returns false for
   * objects that don't have this text inside attribute value within timeout.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitUntilAttributeTextContainsFalse() {
    this.getWebDriver().navigate().to(testSiteAsyncUrl);
    UIWaitFactory.getWaitDriver(this.getWebDriver()).waitForPageLoad();

    UIWait wait = UIWaitFactory.getWaitDriver(this.getWebDriver());
    Assert.assertFalse(wait.waitUntilAttributeTextContains(asyncDropdownCssSelector, "notTheRightId", "id"));
    Assert
        .assertFalse(wait.waitUntilAttributeTextContains(asyncDropdownCssSelector, "notTheRightId", "id", 10000, 1000));
  }

  /**
   * Verify that the WaitUntilAttributeTextContains method returns false for
   * objects that don't have this attribute value within timeout.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitUntilAttributeTextEqualsFalse() {
    getWebDriver().navigate().to(testSiteAsyncUrl);
    UIWaitFactory.getWaitDriver(this.getWebDriver()).waitForPageLoad();

    UIWait wait = UIWaitFactory.getWaitDriver(this.getWebDriver());
    Assert.assertFalse(wait.waitUntilAttributeTextEquals(asyncLoadingLabel, "display:", "style"));
    Assert.assertFalse(wait.waitUntilAttributeTextEquals(asyncLoadingLabel, "display:", "style", 10000, 1000));
  }

  /**
   * Tests the functionality that waits until a clickable element scrolls into
   * view.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitUntilClickableElementAndScrollIntoView() {
    this.getWebDriver().navigate().to(testSiteAutomationUrl);
    UIWait wait = UIWaitFactory.getWaitDriver(this.getWebDriver());
    Assert.assertTrue(wait.waitUntilClickableElementAndScrollIntoView(automationShowDialog1));
    Assert.assertTrue(wait.waitUntilClickableElementAndScrollIntoView(automationShowDialog1, 10000, 1000));
  }

  /**
   * Tests the functionality that waits until an enabled element.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitUntilEnabledElement() {
    this.getWebDriver().navigate().to(testSiteAutomationUrl);
    UIWait wait = UIWaitFactory.getWaitDriver(this.getWebDriver());
    Assert.assertTrue(wait.waitUntilEnabledElement(flowerTableTitle));
    Assert.assertTrue(wait.waitUntilEnabledElement(flowerTableTitle, 10000, 1000));
  }

  /**
   * Tests the functionality that waits until a disabled element.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitUntilDisabledElement() {
    this.getWebDriver().navigate().to(testSiteAutomationUrl);
    UIWait wait = UIWaitFactory.getWaitDriver(this.getWebDriver());
    Assert.assertTrue(wait.waitUntilDisabledElement(disabledField));
    Assert.assertTrue(wait.waitUntilDisabledElement(disabledField, 10000, 1000));
  }

  /**
   * Test the functionality that resets the wait driver.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void resetWaitDriver() {
    UIWait wait = UIWaitFactory.getWaitDriver(this.getWebDriver());
    Assert.assertNotNull(wait.resetWaitDriver());
  }

  /**
   * Test the functionality that gets a new wait driver.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void getNewWaitDriver() {
    this.getWebDriver().navigate().to(testSiteUrl);
    UIWait wait = UIWaitFactory.getWaitDriver(this.getWebDriver());
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
    getWebDriver().navigate().to(testSiteUrl);
    UIWaitFactory.getWaitDriver(this.getWebDriver()).waitForPageLoad();

    UIWait wait = UIWaitFactory.getWaitDriver(this.getWebDriver());
    Assert.assertTrue(wait.waitUntilPageLoad(), "Page failed to load");
  }

  /**
   * Verify WaitUntilClickableElement wait works.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitUntilClickableElement() {
    this.getWebDriver().navigate().to(testSiteAutomationUrl);
    UIWaitFactory.getWaitDriver(this.getWebDriver()).waitForPageLoad();

    UIWait wait = UIWaitFactory.getWaitDriver(this.getWebDriver());
    Assert.assertTrue(wait.waitUntilClickableElement(automationShowDialog1), "Failed to find element");
    Assert.assertTrue(wait.waitUntilClickableElement(automationShowDialog1, 10000, 1000), "Failed to find element");
  }

  /**
   * Verify WaitUntilVisibleElement wait works.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitUntilVisibleElement() {
    this.getWebDriver().navigate().to(testSiteAutomationUrl);
    UIWaitFactory.getWaitDriver(this.getWebDriver()).waitForPageLoad();

    UIWait wait = UIWaitFactory.getWaitDriver(this.getWebDriver());
    Assert.assertTrue(wait.waitUntilVisibleElement(automationShowDialog1), "Failed to find element");
    Assert.assertTrue(wait.waitUntilVisibleElement(automationShowDialog1, 10000, 1000), "Failed to find element");
  }

  /**
   * Verify WaitUntilVisibleElement wait works.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitUntilVisibleInvalidElement() {
    this.getWebDriver().navigate().to(testSiteAutomationUrl);
    UIWaitFactory.getWaitDriver(this.getWebDriver()).waitForPageLoad();

    UIWait wait = UIWaitFactory.getWaitDriver(this.getWebDriver());
    Assert.assertFalse(wait.waitUntilVisibleElement(notInPage), "Element was found");
  }

  /**
   * Verify WaitUntilExactText wait works.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitUntilExactText() {
    this.getWebDriver().navigate().to(testSiteAutomationUrl);
    UIWaitFactory.getWaitDriver(this.getWebDriver()).waitForPageLoad();

    UIWait wait = UIWaitFactory.getWaitDriver(this.getWebDriver());
    Assert.assertTrue(wait.waitUntilExactText(automationShowDialog1, "Show dialog"), "Failed to find element");
    Assert.assertTrue(wait.waitUntilExactText(automationShowDialog1, "Show dialog", 10000, 1000),
        "Failed to find element");
  }

  /**
   * Verify WaitUntilContainsText wait works.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitUntilContainsText() {
    this.getWebDriver().navigate().to(testSiteAutomationUrl);
    UIWaitFactory.getWaitDriver(this.getWebDriver()).waitForPageLoad();

    UIWait wait = UIWaitFactory.getWaitDriver(this.getWebDriver());
    Assert.assertTrue(wait.waitUntilContainsText(automationShowDialog1, "dialog"), "Failed to find element");
    Assert.assertTrue(wait.waitUntilContainsText(automationShowDialog1, "dialog", 10000, 1000),
        "Failed to find element");
  }

  /**
   * Test for the wait until absent.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitUntilAbsentElement() {
    this.getWebDriver().navigate().to(testSiteUrl);
    UIWaitFactory.getWaitDriver(this.getWebDriver()).waitForPageLoad();

    UIWait wait = UIWaitFactory.getWaitDriver(this.getWebDriver());
    Assert.assertTrue(wait.waitUntilAbsentElement(notInPage));
    Assert.assertTrue(wait.waitUntilAbsentElement(notInPage, 10000, 1000));
  }

  /**
   * Verify that the WaitUntilAttributeTextEquals works with async objects.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitUntilAttributeEquals() {
    this.getWebDriver().navigate().to(testSiteAsyncUrl);
    UIWaitFactory.getWaitDriver(this.getWebDriver()).waitForPageLoad();

    UIWait wait = UIWaitFactory.getWaitDriver(this.getWebDriver());
    Assert.assertTrue(wait.waitUntilAttributeTextEquals(asyncLoadingLabel, "style", "display: none;"));
  }

  /**
   * Verify that the WaitUntilAttributeTextContains works with async objects.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void waitUntilAttributeContains() {
    this.getWebDriver().navigate().to(testSiteAsyncUrl);
    UIWaitFactory.getWaitDriver(this.getWebDriver()).waitForPageLoad();

    UIWait wait = UIWaitFactory.getWaitDriver(this.getWebDriver());
    Assert.assertTrue(wait.waitUntilAttributeTextContains(asyncLoadingLabel, "style", "none;"));
  }
}