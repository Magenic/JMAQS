/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.selenium.pageModels;

import com.magenic.jmaqs.selenium.BaseSeleniumPageModel;
import com.magenic.jmaqs.selenium.LazyWebElement;
import com.magenic.jmaqs.selenium.SeleniumConfig;
import com.magenic.jmaqs.selenium.SeleniumTestObject;
import com.magenic.jmaqs.utilities.helper.exceptions.TimeoutException;
import org.openqa.selenium.By;

/**
 * The main site page model.
 */
public class MainPageModel extends BaseSeleniumPageModel {

  /**
   * Unit testing site URL - Login page.
   */
  public final String testSiteUrl = SeleniumConfig.getWebSiteBase();

  /**
   * Home button css selector.
   */
  public final By homeButton = By.cssSelector("#homeButton > a");

  /**
   * Selector that is not in page.
   */
  public final By notInPage = By.cssSelector("NotInPage");

  /**
   * Home button css selector.
   */
  public final By dropdownToggleClassSelector = By.className("dropdown-toggle");

  /**
   * Page title selector.
   */
  public final By pageTitle = By.cssSelector("body > div.container.body-content > div > h2");

  /**
   * The lazy element of the main page title.
   */
  private final LazyWebElement mainPageTitle = new LazyWebElement(getTestObject(), pageTitle, "main page title");

  /**
   * Manage dropdown selector.
   */
  public final By manageDropdown = By
      .cssSelector("body > div.navbar > div > div > ul > li:nth-child(2) > a");

  /**
   * Instantiates a new Main page model.
   *
   * @param testObject the test object
   */
  public MainPageModel(SeleniumTestObject testObject) {
    super(testObject);
  }

  /**
   * Opens the page to the specified url.
   *
   */
  public void open() {
    open(testSiteUrl);
  }

  /**
   * Open.
   *
   * @param url the url
   */
  public void open(String url) {
    this.getTestObject().getWebDriver().get(url);
  }

  /**
   * Is page loaded boolean.
   *
   * @return the boolean
   */
  @Override
  public boolean isPageLoaded() {
    try {
      return mainPageTitle.doesExist();
    } catch (TimeoutException | InterruptedException e) {
      e.printStackTrace();
    }
    return false;
  }
}