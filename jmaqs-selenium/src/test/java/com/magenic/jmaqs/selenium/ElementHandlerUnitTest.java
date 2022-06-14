/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.selenium;

import com.magenic.jmaqs.selenium.factories.UIWaitFactory;
import com.magenic.jmaqs.utilities.helper.ListProcessor;
import com.magenic.jmaqs.utilities.helper.TestCategories;
import com.magenic.jmaqs.utilities.logging.FileLogger;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

/**
 * Unit tests for ElementHandler class.
 */
public class ElementHandlerUnitTest extends BaseSeleniumTest {

  /**
   * Url for the site.
   */
  private static String siteUrl = SeleniumConfig.getWebSiteBase();

  /**
   * Automation site url.
   */
  private static String siteAutomationUrl = siteUrl + "Automation/";

  /**
   * Options for computer parts list.
   */
  private static By computerPartsListOptions = By.cssSelector("#computerParts > option");

  /**
   * Swagger link.
   */
  private static By swaggerLinkBy = By.cssSelector("#SwaggerPageLink > a");

  /**
   * First name textbox.
   */
  private static By firstNameTextBox = By.cssSelector("#TextFields > p:nth-child(1) > input[type=\"text\"]");

  /**
   * Female radio button.
   */
  private static By femaleRadioButton = By.cssSelector("#FemaleRadio");

  /**
   * First checkbox.
   */
  private static By checkbox = By.cssSelector("#Checkbox1");

  /**
   * Name dropdown list.
   */
  private static By nameDropdown = By.cssSelector("#namesDropdown");

  /**
   * Computer parts list.
   */
  private static By computerPartsList = By.cssSelector("#computerParts");

  /**
   * Employee link.
   */
  private static By employeeButton = By.cssSelector("#EmployeeButton > a");

  /**
   * Employee page title.
   */
  private static By employeePageTitle = By.cssSelector("body > div.container.body-content > h2");

  /**
   * Unit Test for creating a sorted comma delimited String.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void createSortedCommaDelimitedStringFromWebElementsTest() {
    String expectedText = "Hard Drive, Keyboard, Monitor, Motherboard, Mouse, Power Supply";
    navigateToUrl();
    Assert.assertEquals(ElementHandler.createCommaDelimitedString(getWebDriver(), computerPartsListOptions, true),
        expectedText, "Expected String does not match actual");
  }

  /**
   * Unit Test for creating a sorted comma delimited String.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void createCommaDelimitedStringFromWebElementsTest() {
    String expectedText = "Motherboard, Power Supply, Hard Drive, Monitor, Mouse, Keyboard";
    navigateToUrl();
    Assert.assertEquals(ElementHandler.createCommaDelimitedString(getWebDriver(), computerPartsListOptions),
        expectedText, "Expected String does not match actual");
  }

  /**
   * Unit test for entering text into a textbox and getting text from a textbox.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void setTextBoxAndVerifyValueTest() {
    String expectedValue = "Tester";
    navigateToUrl();
    ElementHandler.setTextBox(getWebDriver(), firstNameTextBox, expectedValue);
    String actualValue = ElementHandler.getElementAttribute(getWebDriver(), firstNameTextBox);
    verifyText(actualValue, expectedValue);
  }

  /**
   * Unit Test for checking a radio button.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void checkRadioButtonTest() {
    navigateToUrl();
    ElementHandler.clickButton(getWebDriver(), femaleRadioButton, false);
    Assert.assertTrue(
        UIWaitFactory.getWaitDriver(getWebDriver()).waitForClickableElement(femaleRadioButton).isSelected(),
        "Radio button was not selected");
  }

  /**
   * Unit Test for checking a checkbox.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void checkCheckBoxTest() {
    navigateToUrl();
    ElementHandler.checkCheckBox(getWebDriver(), checkbox, true);
    Assert.assertTrue(UIWaitFactory.getWaitDriver(getWebDriver()).waitForClickableElement(checkbox).isSelected(),
        "Checkbox was not enabled");
  }

  /**
   * Unit Test for get element attribute function.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void getElementAttributeTest() {
    String expectedText = "http://magenicautomation.azurewebsites.net/Swagger";
    navigateToUrl();
    String actualText = ElementHandler.getElementAttribute(getWebDriver(), swaggerLinkBy, "href");
    verifyText(actualText, expectedText);
  }

  /**
   * Unit Test for selecting an item from a dropdown and getting the selected item
   * from a dropdown (By actual value).
   */
  @Test(groups = TestCategories.SELENIUM)
  public void selectItemFromDropDownTest() {
    String expectedSelection = "Emily";
    navigateToUrl();
    ElementHandler.selectDropDownOption(getWebDriver(), nameDropdown, expectedSelection);
    String actualSelection = ElementHandler.getSelectedOptionFromDropdown(getWebDriver(), nameDropdown);
    verifyText(actualSelection, expectedSelection);
  }

  /**
   * Unit Test for selecting an item from a dropdown and getting the selected item
   * from a dropdown (By list value).
   */
  @Test(groups = TestCategories.SELENIUM)
  public void selectItemFromDropDownByValueTest() {
    String expectedSelection = "Jack";
    navigateToUrl();
    ElementHandler.selectDropDownOptionByValue(getWebDriver(), nameDropdown, "two");
    String actualSelection = ElementHandler.getSelectedOptionFromDropdown(getWebDriver(), nameDropdown);
    verifyText(actualSelection, expectedSelection);
  }

  /**
   * Unit Test for selecting multiple items from a list box and getting all
   * selected items in a list box(By actual value).
   */
  @Test(groups = TestCategories.SELENIUM)
  public void selectMultipleItemsFromListBoxTest() {
    final StringBuilder results = new StringBuilder();
    ArrayList<String> itemsToSelect = new ArrayList<String>();
    itemsToSelect.add("Monitor");
    itemsToSelect.add("Hard Drive");
    itemsToSelect.add("Keyboard");
    navigateToUrl();
    ElementHandler.selectMultipleElementsFromListBox(getWebDriver(), computerPartsList, itemsToSelect);
    ArrayList<String> selectedItems = (ArrayList<String>) ElementHandler.getSelectedOptionsFromDropdown(getWebDriver(),
        computerPartsList);
    ListProcessor.listOfStringsCompared(itemsToSelect, selectedItems, results, false);
    if (results.length() > 0) {
      Assert.fail(results.toString());
    }
  }

  /**
   * Unit Test for selecting multiple items from a list box and getting all
   * selected items in a list box(By list value).
   */
  @Test(groups = TestCategories.SELENIUM)
  public void selectMultipleItemsFromListBoxTestByValue() {
    ArrayList<String> itemsToSelect = new ArrayList<String>();
    itemsToSelect.add("one");
    itemsToSelect.add("four");
    itemsToSelect.add("five");
    navigateToUrl();
    ElementHandler.selectMultipleElementsFromListBoxByValue(getWebDriver(), computerPartsList, itemsToSelect);
    ArrayList<String> selectedItems = (ArrayList<String>) ElementHandler.getSelectedOptionsFromDropdown(getWebDriver(),
        computerPartsList);

    if (selectedItems.size() != 3) {
      Assert.fail("Does not contain 3 elements: " + selectedItems.toString());
    }
  }

  /**
   * Unit test for ClickElementByJavaScript using a hover dropdown, where dropdown
   * is not visible.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void clickElementByJavascriptFromHoverDropdown() {
    navigateToUrl();
    ElementHandler.clickElementByJavaScript(getWebDriver(), employeeButton);
    UIWaitFactory.getWaitDriver(getWebDriver()).waitForPageLoad();
    UIWaitFactory.getWaitDriver(getWebDriver()).waitForExactText(employeePageTitle, "Index");
  }

  /**
   * Test to verify scrolling into view.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void scrollIntoView() {
    navigateToUrl();
    ElementHandler.scrollIntoView(getWebDriver(), checkbox);
  }

  /**
   * Test to verify scrolling into view.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void scrollIntoViewWithCoords() {
    navigateToUrl();
    ElementHandler.scrollIntoView(getWebDriver(), checkbox, 50, 0);
  }

  /**
   * Test to verify scrolling into view when passing a parent WebElement.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void scrollIntoViewElement() {
    navigateToUrl();
    WebElement element = this.getWebDriver().findElement(By.cssSelector("body"));
    ElementHandler.scrollIntoView(element, checkbox);
  }

  /**
   * Test to verify scrolling into view when passing a parent WebElement with
   * coordinates.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void scrollIntoViewElementWithCoords() {
    navigateToUrl();
    WebElement element = this.getWebDriver().findElement(By.cssSelector("body"));
    ElementHandler.scrollIntoView(element, checkbox, 50, 0);
  }

  /**
   * Test to verify scrolling into view.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void executingScrolling() {
    navigateToUrl();
    ElementHandler.executeScrolling(getWebDriver(), 50, 0);
  }

  /**
   * Unit test for ClickElementByJavaScript where the element is not present.
   */
  @Test(expectedExceptions = NoSuchElementException.class, groups = TestCategories.SELENIUM)
  public void clickElementByJavascriptFromHoverDropdownNotFound() {
    navigateToUrl();
    ElementHandler.clickElementByJavaScript(getWebDriver(), By.cssSelector(".NotPresent"));
  }

  /**
   * Verify slow type text is correctly typed.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void slowTypeTest() {
    navigateToUrl();
    ElementHandler.slowType(getWebDriver(), firstNameTextBox, "Test input slowtype");
    Assert.assertEquals(
        UIWaitFactory.getWaitDriver(getWebDriver()).waitForClickableElement(firstNameTextBox).getAttribute("value"),
        "Test input slowtype");
  }

  /**
   * Verify Send Secret Keys suspends logging.
   */
  @Ignore("This can be uncommented when the logger functions as expected.")
  @Test(groups = TestCategories.SELENIUM)
  public void sendSecretTextSuspendLoggingTest() throws IOException {
    this.navigateToUrl();
    this.getWebDriver().findElement(firstNameTextBox).sendKeys("somethingTest");
    this.getWebDriver().findElement(firstNameTextBox).clear();
    ElementHandler.sendSecretKeys(getWebDriver(), firstNameTextBox, "secretKeys", this.getLogger());

    FileLogger logger = (FileLogger) this.getTestObject().getLogger();
    File file = new File(logger.getFilePath());

    Assert.assertTrue(FileUtils.readFileToString(file, StandardCharsets.UTF_8).contains("somethingTest"),
        "Failed to assert the logger logged normal keys.");
    Assert.assertFalse(FileUtils.readFileToString(file, StandardCharsets.UTF_8).contains("secretKeys"),
        "Failed to assert the logger did not log secret keys.");
  }

  /**
   * Verify Send Secret Keys re-enables after suspending logging.
   */
  @Ignore("This can be uncommented when the logger functions as expected.")
  @Test(groups = TestCategories.SELENIUM)
  public void sendSecretTextContinueLoggingTest() throws IOException {
    this.navigateToUrl();
    ElementHandler.sendSecretKeys(getWebDriver(), firstNameTextBox, "secretKeys", this.getLogger());
    this.getWebDriver().findElement(firstNameTextBox).clear();
    this.getWebDriver().findElement(firstNameTextBox).sendKeys("somethingTest");

    FileLogger logger = (FileLogger) this.getTestObject().getLogger();
    File file = new File(logger.getFilePath());

    Assert.assertFalse(FileUtils.readFileToString(file, StandardCharsets.UTF_8).contains("secretKeys"),
        "Failed to assert the logger did not log the secret keys.");
    Assert.assertTrue(FileUtils.readFileToString(file, StandardCharsets.UTF_8).contains("somethingTest"),
        "Failed to assert the logger did continued logging normally after secret keys.");
  }

  /**
   * Verify two Strings are equal. If not fail test.
   *
   * @param actualValue   Actual displayed text
   * @param expectedValue Expected text
   */
  private static void verifyText(String actualValue, String expectedValue) {
    Assert.assertEquals(actualValue, expectedValue, "Values are not equal");
  }

  /**
   * Navigate to test page url and wait for page to load.
   */
  private void navigateToUrl() {
    getWebDriver().navigate().to(siteAutomationUrl);
    UIWaitFactory.getWaitDriver(getWebDriver()).waitForPageLoad();
  }

}