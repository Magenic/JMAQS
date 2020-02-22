/*
 * Copyright 2020 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.selenium.unittestpagemodel;

import com.magenic.jmaqs.selenium.SeleniumTestObject;
import org.openqa.selenium.By;

/**
 * The type Frame page model.
 */
public class IFramePageModel {

  /**
   * The Test object.
   */
  private SeleniumTestObject testObject;

  /**
   * The Iframe locator.
   */
  public By iframeLocator = By.id("mageniciFrame");

  /**
   * Instantiates a new Frame page model.
   *
   * @param testObject the test object
   */
  public IFramePageModel(SeleniumTestObject testObject) {
    this.testObject = testObject;
  }

  /**
   * Open.
   *
   * @param url the url
   */
  public void open(String url) {
    this.testObject.getWebDriver().get(url);
  }

  /**
   * Gets selenium test object.
   *
   * @return the selenium test object
   */
  public SeleniumTestObject getSeleniumTestObject() {
    return this.testObject;
  }

}
