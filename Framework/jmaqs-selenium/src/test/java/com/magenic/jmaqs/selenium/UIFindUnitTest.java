/*
 * Copyright 2019 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.selenium;

import com.magenic.jmaqs.selenium.factories.UIFindFactory;
import com.magenic.jmaqs.utilities.helper.TestCategories;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * The UI Find functionality unit test.
 */
public class UIFindUnitTest extends BaseSeleniumTest {
  /**
   * Url for the site.
   */
  private static String siteUrl = SeleniumConfig.getWebSiteBase();

  /**
   * Automation site url.
   */
  private static String siteAutomationUrl = siteUrl + "Automation/";

  /**
   * Flower table.
   */
  private static By flowerTable = By.cssSelector("#FlowerTable TD");

  /**
   * Home button css selector.
   */
  private By homeButton = By.cssSelector("#homeButton > a");

  /**
   * Selector that is not in page.
   */
  private By notInPage = By.cssSelector("NOTINPAGE");

  /**
   * Names label.
   */
  private By automationNamesLabel = By.cssSelector("#Dropdown > p > strong > label");

  /**
   * Home button css selector.
   */
  private By dropdownToggleClassSelector = By.className("dropdown-toggle");

  /**
   * First dialog button.
   */
  private By automationShowDialog1 = By.cssSelector("#showDialog1");

  /**
   * Verify findElement works, validating a specific selector is found.
   */
  @Test(groups = TestCategories.Selenium)
  public void findElementFound() {
    this.getWebDriver().navigate().to(siteAutomationUrl);
    UIFind find = UIFindFactory.getFind(this.getWebDriver());
    WebElement element = find.findElement(automationNamesLabel);
    Assert.assertEquals("Names", element.getText());
  }

  /**
   * Verify findElement works, validating a specific selector is not found.
   * The expected exception is to catch the exception thrown in find.findElement.
   */
  @Test(groups = TestCategories.Selenium)
  public void findElementNotFound() {
    this.getWebDriver().navigate().to(siteAutomationUrl);
    UIFind find = UIFindFactory.getFind(this.getWebDriver());
    Assert.assertNull(find.findElement(notInPage, false));
  }

  /**
   * Verify findElements works, validating that there are 3 found.
   */
  @Test(groups = TestCategories.Selenium)
  public void findElementsFound() {
    this.getWebDriver().navigate().to(siteAutomationUrl);
    UIFind find = UIFindFactory.getFind(this.getWebDriver());
    List<WebElement> list = find.findElements(dropdownToggleClassSelector);
    Assert.assertEquals(3, list.size(), "There are 3 elements with dropdown classes");

    Assert.assertEquals(list.get(0).getText(), "Manage");
    Assert.assertTrue(list.get(0).isDisplayed());
    Assert.assertEquals(list.get(1).getText(), "Automation");
    Assert.assertTrue(list.get(1).isDisplayed());
    Assert.assertEquals(list.get(2).getText(), "Training");
    Assert.assertTrue(list.get(2).isDisplayed());

    List<WebElement> elements = find.findElements(flowerTable);
    Assert.assertEquals(elements.size(), 20);
  }

  /**
   * Verify findElements works, validating that there are none found.
   */
  @Test(groups = TestCategories.Selenium)
  public void findElementsNotFound() {
    this.getWebDriver().navigate().to(siteAutomationUrl);
    UIFind find = UIFindFactory.getFind(this.getWebDriver());
    List<WebElement> list = find.findElements(notInPage, false);
    Assert.assertEquals(list.size(), 0);
  }

  /**
   * Verify Find.Elements() throws an exception when there are no Elements existing on the page.
   */
  @Test(groups = TestCategories.Selenium)
  public void findElementsNotFoundThrowException() {
    this.getWebDriver().navigate().to(siteAutomationUrl);
    UIFind find = UIFindFactory.getFind(this.getWebDriver());
    List<WebElement> elements = find.findElements(notInPage, false);
    Assert.assertEquals(elements.size(), 0);
  }

  /**
   * Verify FindElementWithText = Validate null is returned if the element is not found.
   */
  @Test(groups = TestCategories.Selenium)
  public void findElementWithTextElementNotFound() {
    this.getWebDriver().navigate().to(siteAutomationUrl);
    UIFind find = UIFindFactory.getFind(this.getWebDriver());
    Assert.assertNull(find.findElementWithText(notInPage, "notInPage", false),
        "Element was not found");
  }

  /**
   * Verify FindElementWithText,
   * Validating specific text is found within a specific selector.
   */
  @Test(groups = TestCategories.Selenium)
  public void findElementWithText() {
    this.getWebDriver().navigate().to(siteAutomationUrl);
    UIFind find = UIFindFactory.getFind(this.getWebDriver());
    String text = find.findElement(automationShowDialog1).getText();
    Assert.assertNotNull(find.findElementWithText(automationShowDialog1, text),
        "Element was not found");
  }

  /**
   * Verify FindElementWithText,
   * Validating specific text is NOT found within a specific selector.
   */
  @Test(groups = TestCategories.Selenium)
  public void findElementWithTextNotFound() {
    this.getWebDriver().navigate().to(siteAutomationUrl);
    UIFind find = UIFindFactory.getFind(this.getWebDriver());
    Assert.assertNull(find.findElementWithText(homeButton, "#notfound", false),
        "Element was not found");
  }

  /**
   * Verify FindIndexOfElementWithText works,
   * Validating the correct index is returned for a specific Selector and text.
   */
  @Test(groups = TestCategories.Selenium)
  public void findIndexOfElementWithText() {
    this.getWebDriver().navigate().to(siteAutomationUrl);
    UIFind find = UIFindFactory.getFind(this.getWebDriver());
    Assert.assertEquals(3, find.findIndexOfElementWithText(flowerTable, "Red"));
  }

  /**
   * Verify FindIndexOfElementWithText works,
   * Validating index is not returned for a specific Selector and text.
   */
  @Test(groups = TestCategories.Selenium)
  public void findIndexOfElementWithTextNotFound() {
    this.getWebDriver().navigate().to(siteAutomationUrl);
    UIFind find = UIFindFactory.getFind(this.getWebDriver());
    Assert.assertEquals(find.findIndexOfElementWithText(flowerTable,
        "#notfound", false), -1);
  }

  /**
   *  Verify FindIndexOfElementWithText works,
   *  Validate that index of -1 is returned if an empty list is returned by ElemList.
   */
  @Test(groups = TestCategories.Selenium)
  public void findIndexOfElementWithTextWithNotFoundElement() {
    this.getWebDriver().navigate().to(siteAutomationUrl);
    UIFind find = UIFindFactory.getFind(this.getWebDriver());
    Assert.assertEquals(find.findIndexOfElementWithText(notInPage,
        "#notfound", false), -1);
  }

  /**
   * Verify FindIndexOfElementWithText works,
   * Validating the correct index is returned for a specific collection and text.
   */
  @Test(groups = TestCategories.Selenium)
  public void findIndexOfElementInCollection() {
    this.getWebDriver().navigate().to(siteAutomationUrl);
    UIFind find = UIFindFactory.getFind(this.getWebDriver());
    Assert.assertEquals(0, find.findIndexOfElementWithText(
        find.findElements(flowerTable), "10 in"));
  }

  /**
   * Verify FindIndexOfElementWithText works,
   * Validating -1 is returned for a specific collection and text.
   */
  @Test(groups = TestCategories.Selenium)
  public void findIndexOfElementInCollectionNotFound() {
    this.getWebDriver().navigate().to(siteAutomationUrl);
    UIFind find = UIFindFactory.getFind(this.getWebDriver());
    Assert.assertEquals(find.findIndexOfElementWithText(find.findElements(flowerTable),
        "#notfound", false), -1);
  }

  /**
   * Verify FindIndexOfElementWithText works,
   * NotFoundException is thrown when an Empty input list is entered with assert == true.
   */
  @Test(groups = TestCategories.Selenium)
  public void findIndexOfElementInCollectionEmptyInputList() {
    this.getWebDriver().navigate().to(siteAutomationUrl);
    UIFind find = UIFindFactory.getFind(this.getWebDriver());
    List<WebElement> list = null;
    int index = find.findIndexOfElementWithText(list, "#notfound", false);
    Assert.assertEquals(index, -1);
  }

  /**
   * Verify FindIndexOfElementWithText works,
   * NotFoundException is thrown when the element is not found and assert == true.
   */
  @Test(groups = TestCategories.Selenium)
  public void findIndexOfElementInCollectionTextNotFoundAssertIsTrue() {
    this.getWebDriver().navigate().to(siteAutomationUrl);
    UIFind find = UIFindFactory.getFind(this.getWebDriver());
    int index = find.findIndexOfElementWithText(find.findElements(flowerTable),
        "#notfound", false);
    Assert.assertEquals(index, -1);
  }
}