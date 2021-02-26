/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.selenium;

import com.magenic.jmaqs.selenium.unittestpagemodel.PageElementsPageModel;
import com.magenic.jmaqs.utilities.helper.exceptions.TimeoutException;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BaseSeleniumPageModelUnitTest extends BaseSeleniumTest {

  @Test
  public void testGetLogger() {
    PageElementsPageModel pageElementsPageModel = new PageElementsPageModel(getTestObject());
    Assert.assertNotNull(pageElementsPageModel.getLogger());
  }

  @Test
  public void testGetTestObject() {
    PageElementsPageModel pageElementsPageModel = new PageElementsPageModel(getTestObject());
    Assert.assertNotNull(pageElementsPageModel.getTestObject());
  }

  @Test
  public void testGetWebDriver() {
    PageElementsPageModel pageElementsPageModel = new PageElementsPageModel(getTestObject());
    Assert.assertNotNull(pageElementsPageModel.getWebDriver());
  }

  @Test
  public void testGetPerfTimerCollection() {
    PageElementsPageModel pageElementsPageModel = new PageElementsPageModel(getTestObject());
    Assert.assertNotNull(pageElementsPageModel.getPerfTimerCollection());
  }

  @Test
  public void testSetWebDriver() throws Exception {
    PageElementsPageModel pageElementsPageModel = new PageElementsPageModel(getTestObject());
    int hashCode = pageElementsPageModel.getWebDriver().hashCode();
    WebDriver drive = this.getBrowser();

    pageElementsPageModel.setWebDriver(drive);
    int hashCode1 = pageElementsPageModel.getWebDriver().hashCode();
    Assert.assertNotEquals(hashCode, hashCode1);

    drive.quit();
  }

  @Test
  public void testIsPageLoaded() {
    PageElementsPageModel pageElementsPageModel = new PageElementsPageModel(getTestObject());
    pageElementsPageModel.open();

    Assert.assertTrue(pageElementsPageModel.isPageLoaded());
  }

  @Test
  public void testGetElementCalledTwiceReturnsTheSameElement() {
    PageElementsPageModel pageElementsPageModel = new PageElementsPageModel(getTestObject());
    pageElementsPageModel.open();
    pageElementsPageModel.waitForPageLoad();

    LazyWebElement initElem = pageElementsPageModel.getLazyElement(pageElementsPageModel.pageTitleLocator);
    LazyWebElement cachedElem = pageElementsPageModel.getLazyElement(pageElementsPageModel.pageTitleLocator);

    Assert.assertSame(initElem, cachedElem);
  }

  @Test
  public void testGetLazyElementWithBy() throws TimeoutException, InterruptedException {
    PageElementsPageModel pageElementsPageModel = new PageElementsPageModel(getTestObject());
    pageElementsPageModel.open();
    pageElementsPageModel.waitForPageLoad();
    LazyWebElement lazyElement = pageElementsPageModel.getLazyElement(pageElementsPageModel.pageTitleLocator);
    LazyWebElement storedElement = pageElementsPageModel.getLazyElementStore()
        .get(pageElementsPageModel.pageTitleLocator.toString());

    Assert.assertNotNull(lazyElement);
    Assert.assertNotNull(storedElement);
    Assert.assertEquals(lazyElement, storedElement);
    Assert.assertEquals(lazyElement.getText(), "Elements to be automated");
  }

  @Test
  public void testGetLazyElementWithByAndName() throws TimeoutException, InterruptedException {
    PageElementsPageModel pageElementsPageModel = new PageElementsPageModel(getTestObject());
    pageElementsPageModel.open();
    pageElementsPageModel.waitForPageLoad();
    LazyWebElement lazyElement = pageElementsPageModel.getLazyElement(pageElementsPageModel.pageTitleLocator,
        "Page Title");
    LazyWebElement storedElement = pageElementsPageModel.getLazyElementStore()
        .get(pageElementsPageModel.pageTitleLocator.toString() + "Page Title");

    Assert.assertNotNull(lazyElement);
    Assert.assertNotNull(storedElement);
    Assert.assertEquals(lazyElement, storedElement);
    Assert.assertEquals(lazyElement.getText(), "Elements to be automated");
    Assert.assertEquals(lazyElement.getUserFriendlyName(), "Page Title");
  }

  @Test
  public void testGetLazyElementWithParentElementAndBy() throws TimeoutException, InterruptedException {
    PageElementsPageModel pageElementsPageModel = new PageElementsPageModel(getTestObject());
    pageElementsPageModel.open();
    pageElementsPageModel.waitForPageLoad();
    LazyWebElement lazyElement = pageElementsPageModel.getLazyElement(pageElementsPageModel.body,
        pageElementsPageModel.pageTitleLocator);
    LazyWebElement storedElement = pageElementsPageModel.getLazyElementStore()
        .get(pageElementsPageModel.pageTitleLocator.toString());

    Assert.assertNotNull(lazyElement);
    Assert.assertNotNull(storedElement);
    Assert.assertEquals(storedElement, lazyElement);
    Assert.assertEquals(lazyElement.getText(), "Elements to be automated");
  }

  @Test
  public void testGetLazyElementWithParentElementByAndName() throws TimeoutException, InterruptedException {
    PageElementsPageModel pageElementsPageModel = new PageElementsPageModel(getTestObject());
    pageElementsPageModel.open();
    pageElementsPageModel.waitForPageLoad();
    LazyWebElement lazyElement = pageElementsPageModel.getLazyElement(pageElementsPageModel.body,
        pageElementsPageModel.pageTitleLocator, "Page Title");
    LazyWebElement storedElement = pageElementsPageModel.getLazyElementStore()
        .get(pageElementsPageModel.pageTitleLocator.toString() + "Page Title");

    Assert.assertNotNull(lazyElement);
    Assert.assertNotNull(storedElement);
    Assert.assertEquals(storedElement, lazyElement);
    Assert.assertEquals(lazyElement.getText(), "Elements to be automated");
    Assert.assertEquals(lazyElement.getUserFriendlyName(), "Page Title");
  }
}