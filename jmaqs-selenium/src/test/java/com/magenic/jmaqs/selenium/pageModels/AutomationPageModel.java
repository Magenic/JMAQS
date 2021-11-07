/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.selenium.pageModels;

import com.magenic.jmaqs.selenium.LazyWebElement;
import com.magenic.jmaqs.selenium.SeleniumConfig;
import com.magenic.jmaqs.selenium.SeleniumTestObject;
import com.magenic.jmaqs.utilities.helper.exceptions.TimeoutException;
import org.openqa.selenium.By;

/**
 * The Automation page model.
 */
public class AutomationPageModel extends MainPageModel {

  /**
   * Unit testing site URL - Automation page.
   */
  public final String testSiteAutomationUrl = SeleniumConfig.getWebSiteBase() + "Automation/";

  /**
   * First dialog button.
   */
  public final By automationShowDialog1 = By.cssSelector("#showDialog1");

  /**
   * Flower table title.
   */
  public final By flowerTableTitle = By.cssSelector("#FlowerTable > caption > strong");

  /**
   * Flower table.
   */
  public final By flowerTable = By.cssSelector("#FlowerTable TD");

  /**
   * Food table.
   */
  public final By foodTable = By.cssSelector("#FoodTable");

  /**
   * Gets the disabled item.
   */
  public final By disabledField = By.cssSelector("#disabledField > INPUT");

  /**
   * Names label.
   */
  public final By automationNamesLabel = By.cssSelector("#Dropdown > p > strong > label");

  /**
   * Alert button.
   */
  public final By alert = By.id("javascriptAlertButton");

  /**
   * Alert button with confirm option.
   */
  public final By alertWithConfirm = By.id("javascriptConfirmAlertButton");

  /**
   * Swagger link.
   */
  public final By swaggerLinkBy = By.cssSelector("#SwaggerPageLink > a");

  /**
   * First name text box.
   */
  public final By firstNameTextBox = By.cssSelector("#TextFields > p:nth-child(1) > input[type=\"text\"]");

  /**
   * First checkbox.
   */
  public final By checkbox = By.cssSelector("#Checkbox1");

  /**
   * Computer parts list.
   */
  public final By computerPartsList = By.cssSelector("#computerParts");

  /**
   * Options for computer parts list.
   */
  public final By computerPartsListOptions = By.cssSelector("#computerParts > option");

  /**
   * List box option 1.
   */
  public final By listBoxOption1 = By.cssSelector("#computerParts > option:nth-child(1)");

  /**
   * List box option 2.
   */
  public final By listBoxOption2 = By.cssSelector("#computerParts > option:nth-child(2)");

  /**
   * Female radio button.
   */
  public final By femaleRadioButton = By.cssSelector("#FemaleRadio");
  
  /**
   * Name dropdown list.
   */
  public final By nameDropdown = By.cssSelector("#namesDropdown");
  
  /**
   * Employee link.
   */
  public final By employeeButton = By.cssSelector("#EmployeeButton > a");

  /**
   * Employee page title.
   */
  public final By employeePageTitle = By.cssSelector("body > div.container.body-content > h2");

  /**
   * The body selector.
   */
  public By bodyLocator = By.cssSelector("body");

  /**
   *  The body lazy element.
   */
  public LazyWebElement body = new LazyWebElement(getTestObject(), bodyLocator, "Body");

  /**
   * The page model lazy element.
   */
  public LazyWebElement pageTitle = new LazyWebElement(getTestObject(), employeePageTitle , "Page Title");

  /**
   * Slider element.
   */
  public final By slider = By.cssSelector("#slider > span");

  /**
   * Slider value label.
   */
  public final By sliderLabelNumber = By.cssSelector("#sliderNumber");

  /**
   * Element with context menu for testing right click.
   */
  public final By rightClickableElementWithContextMenu = By.cssSelector("#rightclickspace");

  /**
   * Text within context menu triggered by right click on specific element.
   */
  public final By rightClickContextSaveText = By.cssSelector("#RightClickSaveText");

  /**
   * Instantiates a new Automation page model.
   *
   * @param testObject the test object
   */
  public AutomationPageModel(SeleniumTestObject testObject) {
    super(testObject);
  }

  /**
   * Opens the page to the specified url.
   *
   */
  public void open() {
    open(testSiteAutomationUrl);
  }

  /**
   * Is page loaded boolean.
   *
   * @return the boolean
   */
  @Override
  public boolean isPageLoaded() {
    try {
      return pageTitle.doesExist();
    } catch (TimeoutException | InterruptedException e) {
      e.printStackTrace();
    }
    return false;
  }
}