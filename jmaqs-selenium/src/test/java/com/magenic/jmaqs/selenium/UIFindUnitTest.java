/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.selenium;

import com.magenic.jmaqs.selenium.factories.UIFindFactory;
import com.magenic.jmaqs.selenium.pageModels.AutomationPageModel;
import com.magenic.jmaqs.utilities.helper.TestCategories;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * The UI Find functionality unit test.
 */
public class UIFindUnitTest extends BaseSeleniumTest {

  /**
   * the Automation page model.
   */
  private AutomationPageModel automationPageModel;

  /**
   * Sets up the page model and navigates to the test page for the unit test.
   */
  public void navigateToTestPage() {
    automationPageModel = new AutomationPageModel(this.getTestObject());
    this.getWebDriver().navigate().to(automationPageModel.testSiteAutomationUrl);
  }

  /**
   * Verify findElement works, validating a specific selector is found.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void findElementFound() {
    navigateToTestPage();
    UIFind find = UIFindFactory.getFind(this.getWebDriver());
    WebElement element = find.findElement(automationPageModel.automationNamesLabel);
    Assert.assertEquals(element.getText(), "Names");
  }

  /**
   * Verify findElement works, validating a specific selector is not found.
   * The expected exception is to catch the exception thrown in find.findElement.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void findElementNotFound() {
    navigateToTestPage();
    UIFind find = UIFindFactory.getFind(this.getWebDriver());
    Assert.assertNull(find.findElement(automationPageModel.notInPage, false));
  }

  /**
   * Verify findElements works, validating that there are 3 found.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void findElementsFound() {
    navigateToTestPage();
    UIFind find = UIFindFactory.getFind(this.getWebDriver());
    List<WebElement> list = find.findElements(automationPageModel.dropdownToggleClassSelector);
    Assert.assertEquals(list.size(), 3, "There are 3 elements with dropdown classes");

    Assert.assertEquals(list.get(0).getText(), "Manage");
    Assert.assertTrue(list.get(0).isDisplayed());
    Assert.assertEquals(list.get(1).getText(), "Automation");
    Assert.assertTrue(list.get(1).isDisplayed());
    Assert.assertEquals(list.get(2).getText(), "Training");
    Assert.assertTrue(list.get(2).isDisplayed());

    List<WebElement> elements = find.findElements(automationPageModel.flowerTable);
    Assert.assertEquals(elements.size(), 20);
  }

  /**
   * Verify findElements works, validating that there are none found.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void findElementsNotFound() {
    navigateToTestPage();
    UIFind find = UIFindFactory.getFind(this.getWebDriver());
    List<WebElement> list = find.findElements(automationPageModel.notInPage, false);
    Assert.assertEquals(list.size(), 0);
  }

  /**
   * Verify Find.Elements() throws an exception when there are no Elements existing on the page.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void findElementsNotFoundThrowException() {
    navigateToTestPage();
    UIFind find = UIFindFactory.getFind(this.getWebDriver());
    List<WebElement> elements = find.findElements(automationPageModel.notInPage, false);
    Assert.assertEquals(elements.size(), 0);
  }

  /**
   * Verify FindElementWithText = Validate null is returned if the element is not found.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void findElementWithTextElementNotFound() {
    navigateToTestPage();
    UIFind find = UIFindFactory.getFind(this.getWebDriver());
    Assert.assertNull(find.findElementWithText(automationPageModel.notInPage, "notInPage", false),
        "Element was not found");
  }

  /**
   * Verify FindElementWithText,
   * Validating specific text is found within a specific selector.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void findElementWithText() {
    navigateToTestPage();
    UIFind find = UIFindFactory.getFind(this.getWebDriver());
    String text = find.findElement(automationPageModel.automationShowDialog1).getText();
    Assert.assertNotNull(find.findElementWithText(automationPageModel.automationShowDialog1, text),
        "Element was not found");
  }

  /**
   * Verify FindElementWithText,
   * Validating specific text is NOT found within a specific selector.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void findElementWithTextNotFound() {
    navigateToTestPage();
    UIFind find = UIFindFactory.getFind(this.getWebDriver());
    Assert.assertNull(find.findElementWithText(automationPageModel.homeButton, "#notfound", false),
        "Element was not found");
  }

  /**
   * Verify FindIndexOfElementWithText works,
   * Validating the correct index is returned for a specific Selector and text.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void findIndexOfElementWithText() {
    navigateToTestPage();
    UIFind find = UIFindFactory.getFind(this.getWebDriver());
    Assert.assertEquals(find.findIndexOfElementWithText(automationPageModel.flowerTable, "Red"), 3);
  }

  /**
   * Verify FindIndexOfElementWithText works,
   * Validating index is not returned for a specific Selector and text.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void findIndexOfElementWithTextNotFound() {
    navigateToTestPage();
    UIFind find = UIFindFactory.getFind(this.getWebDriver());
    Assert.assertEquals(find.findIndexOfElementWithText(automationPageModel.flowerTable,
        "#notfound", false), -1);
  }

  /**
   *  Verify FindIndexOfElementWithText works,
   *  Validate that index of -1 is returned if an empty list is returned by ElemList.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void findIndexOfElementWithTextWithNotFoundElement() {
    navigateToTestPage();
    UIFind find = UIFindFactory.getFind(this.getWebDriver());
    Assert.assertEquals(find.findIndexOfElementWithText(automationPageModel.notInPage,
        "#notfound", false), -1);
  }

  /**
   * Verify FindIndexOfElementWithText works,
   * Validating the correct index is returned for a specific collection and text.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void findIndexOfElementInCollection() {
    navigateToTestPage();
    UIFind find = UIFindFactory.getFind(this.getWebDriver());
    Assert.assertEquals(find.findIndexOfElementWithText(
        find.findElements(automationPageModel.flowerTable), "10 in"), 0);
  }

  /**
   * Verify FindIndexOfElementWithText works,
   * Validating -1 is returned for a specific collection and text.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void findIndexOfElementInCollectionNotFound() {
    navigateToTestPage();
    UIFind find = UIFindFactory.getFind(this.getWebDriver());
    Assert.assertEquals(find.findIndexOfElementWithText(find.findElements(automationPageModel.flowerTable),
        "#notfound", false), -1);
  }

  /**
   * Verify FindIndexOfElementWithText works,
   * NotFoundException is thrown when an Empty input list is entered with assert == true.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void findIndexOfElementInCollectionEmptyInputList() {
    navigateToTestPage();
    UIFind find = UIFindFactory.getFind(this.getWebDriver());
    int index = find.findIndexOfElementWithText((List<WebElement>) null, "#notfound", false);
    Assert.assertEquals(index, -1);
  }

  /**
   * Verify FindIndexOfElementWithText works,
   * NotFoundException is thrown when the element is not found and assert == true.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void findIndexOfElementInCollectionTextNotFoundAssertIsTrue() {
    navigateToTestPage();
    UIFind find = UIFindFactory.getFind(this.getWebDriver());
    int index = find.findIndexOfElementWithText(find.findElements(automationPageModel.flowerTable),
        "#notfound", false);
    Assert.assertEquals(index, -1);
  }
}