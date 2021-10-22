/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.selenium;

import com.magenic.jmaqs.selenium.factories.UIWaitFactory;
import com.magenic.jmaqs.utilities.helper.TestCategories;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Unit tests for the ActionBuilder class.
 */
public class ActionBuilderUnitTest extends BaseSeleniumTest {

  /**
   * Url for the automation page.
   */
  private static final String siteAutomationUrl = SeleniumConfig.getWebSiteBase() + "Automation/";

  /**
   * Manage dropdown selector.
   */
  private static final By manageDropdown = By
      .cssSelector("body > div.navbar > div > div > ul > li:nth-child(2) > a");

  /**
   * Employee link.
   */
  private static final By employeeButton = By.cssSelector("#EmployeeButton > a");

  /**
   * Employee page title.
   */
  private static final By employeePageTitle = By.cssSelector("body > div.container.body-content > h2");

  /**
   * List box option 1.
   */
  private static final By listBoxOption1 = By.cssSelector("#computerParts > option:nth-child(1)");

  /**
   * List box option 2.
   */
  private static final By listBoxOption2 = By.cssSelector("#computerParts > option:nth-child(2)");

  /**
   * Slider element.
   */
  private static final By slider = By.cssSelector("#slider > span");

  /**
   * Slider value label.
   */
  private static final By sliderLabelNumber = By.cssSelector("#sliderNumber");

  /**
   * Element with context menu for testing right click.
   */
  private static final By rightClickableElementWithContextMenu = By.cssSelector("#rightclickspace");

  /**
   * Text within context menu triggered by right click on specific element.
   */
  private static final By rightClickContextSaveText = By.cssSelector("#RightClickSaveText");

  @Test(groups = TestCategories.SELENIUM)
  public void hoverOverTest() {
    this.navigateToUrl();
    ActionBuilder.hoverOver(this.getWebDriver(), manageDropdown);
    UIWaitFactory.getWaitDriver(this.getWebDriver()).waitForClickableElement(employeeButton)
        .click();
    UIWaitFactory.getWaitDriver(this.getWebDriver()).waitForExactText(employeePageTitle, "Index");
  }

  @Test(groups = TestCategories.SELENIUM)
  public void pressModifierKeyTest() {
    this.navigateToUrl();

    UIWaitFactory.getWaitDriver(this.getWebDriver()).waitForClickableElement(listBoxOption1)
        .click();
    ActionBuilder.pressModifierKey(this.getWebDriver(), Keys.CONTROL);
    UIWaitFactory.getWaitDriver(this.getWebDriver()).waitForClickableElement(listBoxOption2)
        .click();

    Assert.assertTrue(
        UIWaitFactory.getWaitDriver(this.getWebDriver()).waitForClickableElement(listBoxOption1)
            .isSelected());
    Assert.assertTrue(
        UIWaitFactory.getWaitDriver(this.getWebDriver()).waitForClickableElement(listBoxOption2)
            .isSelected());
  }

  @Test(groups = TestCategories.SELENIUM)
  public void moveSliderTest() {
    this.navigateToUrl();
    ActionBuilder.slideElement(this.getWebDriver(), slider, 50);
    Assert.assertEquals(this.getWebDriver().findElement(sliderLabelNumber).getAttribute("value"),
        "4");
  }

  @Test(groups = TestCategories.SELENIUM)
  public void rightClickToTriggerContextMenu() {
    this.navigateToUrl();
    ActionBuilder.rightClick(this.getWebDriver(), rightClickableElementWithContextMenu);
    Assert.assertTrue(this.getWebDriver().findElement(rightClickContextSaveText).isDisplayed());
  }

  private void navigateToUrl() {
    this.getWebDriver().navigate().to(siteAutomationUrl);
    UIWaitFactory.getWaitDriver(this.getWebDriver()).waitForPageLoad();
  }

}
