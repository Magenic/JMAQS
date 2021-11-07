/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.selenium.pageModels;

import com.magenic.jmaqs.selenium.SeleniumConfig;
import com.magenic.jmaqs.selenium.SeleniumTestObject;
import org.openqa.selenium.By;

/**
 * The Async page model.
 */
public class AsyncPageModel extends MainPageModel{

  /**
   * Unit testing site URL - Async page.
   */
  public final String testSiteAsyncUrl = SeleniumConfig.getWebSiteBase() + "Automation/AsyncPage";

  /**
   * Dropdown selector.
   */
  public final By asyncDropdownCssSelector = By.cssSelector("#Selector");

  /**
   * Dropdown label.
   */
  public final By asyncOptionsLabel = By.cssSelector("#Label");

  /**
   * Asynchronous div that loads after a delay on Async Testing Page.
   */
  public final By asyncLoadingTextDiv = By.cssSelector("#loading-div-text");

  /**
   * Dropdown label - hidden once dropdown loads.
   */
  public final By asyncLoadingLabel = By.cssSelector("#LoadingLabel");

  /**
   * Instantiates a new Async page model.
   *
   * @param testObject the test object
   */
  public AsyncPageModel(SeleniumTestObject testObject) {
    super(testObject);
  }
}