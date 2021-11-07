/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.selenium.pageModels;

import com.magenic.jmaqs.selenium.SeleniumConfig;
import com.magenic.jmaqs.selenium.SeleniumTestObject;
import org.openqa.selenium.By;

/**
 * The IFrame page model.
 */
public class IFramePageModel extends MainPageModel{

  /**
   * Unit testing site URL - IFrame page.
   */
  public final String testSiteIFrameUrl = SeleniumConfig.getWebSiteBase() + "Automation/iFramePage";

  /**
   * The Iframe locator.
   */
  public By iframeLocator = By.id("mageniciFrame");

  /**
   * Instantiates a new IFrame page model.
   *
   * @param testObject the test object
   */
  public IFramePageModel(SeleniumTestObject testObject) {
    super(testObject);
  }

  /**
   * Opens the page to the specified url.
   */
  public void open() {
    open(testSiteIFrameUrl);
  }
}