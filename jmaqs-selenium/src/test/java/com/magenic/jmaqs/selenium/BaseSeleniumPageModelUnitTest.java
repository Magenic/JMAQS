/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.selenium;

import com.magenic.jmaqs.selenium.pageModels.AutomationPageModel;
import com.magenic.jmaqs.utilities.helper.TestCategories;
import com.magenic.jmaqs.utilities.helper.exceptions.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * The base selenium page model unit tests.
 */
public class BaseSeleniumPageModelUnitTest extends BaseSeleniumTest {

  /**
   * the Automation page model.
   */
  private AutomationPageModel automationPageModel;

  /**
   * Sets up the page model to be tested.
   */
  public void setUp() {
    automationPageModel = new AutomationPageModel(this.getTestObject());
    automationPageModel.open();
    automationPageModel.waitForPageLoad();
  }

  /**
   * Tests getting the page model logger.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void testGetLogger() {
    setUp();
    Assert.assertNotNull(automationPageModel.getLogger());
  }

  /**
   * Tests getting the page model's test object.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void testGetTestObject() {
    setUp();
    Assert.assertNotNull(automationPageModel.getTestObject());
  }

  /**
   * Tests getting the page model web driver.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void testGetWebDriver() {
    setUp();
    Assert.assertNotNull(automationPageModel.getWebDriver());
  }

  /**
   * Tests getting the page model performance timer collection.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void testGetPerfTimerCollection() {
    setUp();
    Assert.assertNotNull(automationPageModel.getPerfTimerCollection());
  }

  /**
   * Tests setting the page model web driver
   */
  @Test(groups = TestCategories.SELENIUM)
  public void testSetWebDriver() {
    setUp();
    int hashCode = automationPageModel.getWebDriver().hashCode();
    WebDriver drive = this.getBrowser();

    automationPageModel.setWebDriver(drive);
    int hashCode1 = automationPageModel.getWebDriver().hashCode();
    Assert.assertNotEquals(hashCode, hashCode1);
    drive.quit();
  }

  /**
   * Tests if the page is loaded.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void testIsPageLoaded() {
    setUp();
    Assert.assertTrue(automationPageModel.isPageLoaded());
  }

  /**
   * Tests getting the element called twice and returns the same element.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void testGetElementCalledTwiceReturnsTheSameElement() {
    setUp();
    LazyWebElement initElem = automationPageModel.getLazyElement(automationPageModel.employeePageTitle);
    LazyWebElement cachedElem = automationPageModel.getLazyElement(automationPageModel.employeePageTitle);

    Assert.assertSame(initElem, cachedElem);
  }

  /**
   * Tests getting the lazy element with the By type.
   * @throws TimeoutException if a timeout occurs
   * @throws InterruptedException if an interruption occurs
   */
  @Test(groups = TestCategories.SELENIUM)
  public void testGetLazyElementWithBy() throws TimeoutException, InterruptedException {
    setUp();
    LazyWebElement lazyElement = automationPageModel.getLazyElement(automationPageModel.employeePageTitle);
    LazyWebElement storedElement = automationPageModel.getLazyElementStore()
        .get(automationPageModel.employeePageTitle.toString());

    Assert.assertNotNull(lazyElement);
    Assert.assertNotNull(storedElement);
    Assert.assertEquals(lazyElement, storedElement);
    Assert.assertEquals(lazyElement.getText(), "Elements to be automated");
  }

  /**
   * Tests getting the lazy element with By and its Name
   * @throws TimeoutException if a Timeout occurs
   * @throws InterruptedException if an interruption occurs
   */
  @Test(groups = TestCategories.SELENIUM)
  public void testGetLazyElementWithByAndName() throws TimeoutException, InterruptedException {
    setUp();
    LazyWebElement lazyElement = automationPageModel.getLazyElement(automationPageModel.employeePageTitle,
        "Page Title");
    LazyWebElement storedElement = automationPageModel.getLazyElementStore()
        .get(automationPageModel.employeePageTitle + "Page Title");

    Assert.assertNotNull(lazyElement);
    Assert.assertNotNull(storedElement);
    Assert.assertEquals(lazyElement, storedElement);
    Assert.assertEquals(lazyElement.getText(), "Elements to be automated");
    Assert.assertEquals(lazyElement.getUserFriendlyName(), "Page Title");
  }

  /**
   * Tests getting the lazy element with parent element and the By value.
   * @throws TimeoutException if a timeout occurs
   * @throws InterruptedException if an interruption occurs
   */
  @Test(groups = TestCategories.SELENIUM)
  public void testGetLazyElementWithParentElementAndBy() throws TimeoutException, InterruptedException {
    setUp();
    LazyWebElement lazyElement = automationPageModel.getLazyElement(automationPageModel.body,
        automationPageModel.employeePageTitle);
    LazyWebElement storedElement = automationPageModel.getLazyElementStore()
        .get(automationPageModel.employeePageTitle.toString());

    Assert.assertNotNull(lazyElement);
    Assert.assertNotNull(storedElement);
    Assert.assertEquals(storedElement, lazyElement);
    Assert.assertEquals(lazyElement.getText(), "Elements to be automated");
  }

  /**
   * Test getting the lazy element with the parent By and Name
   * @throws TimeoutException if a timeout occurs
   * @throws InterruptedException if an interruption occurs
   */
  @Test(groups = TestCategories.SELENIUM)
  public void testGetLazyElementWithParentElementByAndName() throws TimeoutException, InterruptedException {
    setUp();
    LazyWebElement lazyElement = automationPageModel.getLazyElement(automationPageModel.body,
        automationPageModel.employeePageTitle, "Page Title");
    LazyWebElement storedElement = automationPageModel.getLazyElementStore()
        .get(automationPageModel.employeePageTitle + "Page Title");

    Assert.assertNotNull(lazyElement);
    Assert.assertNotNull(storedElement);
    Assert.assertEquals(storedElement, lazyElement);
    Assert.assertEquals(lazyElement.getText(), "Elements to be automated");
    Assert.assertEquals(lazyElement.getUserFriendlyName(), "Page Title");
  }
}