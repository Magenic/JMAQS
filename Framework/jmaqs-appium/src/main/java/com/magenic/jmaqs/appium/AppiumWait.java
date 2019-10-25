/*
 * Copyright 2019 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.appium;

import com.magenic.jmaqs.selenium.SeleniumWait;

import io.appium.java_client.AppiumDriver;

/**
 * Class AppiumWait.
 * @deprecated in favor of a static service
 */
@Deprecated
public class AppiumWait extends SeleniumWait {

  /**
   * Constructor for SeleniumWait object.
   *
   * @param appiumDriver
   *          Appium Driver
   */
  public AppiumWait(AppiumDriver appiumDriver) {
    super(appiumDriver);
  }

  /**
   * Constructor for SeleniumWait object.
   *
   * @param appiumDriver
   *          Appium Driver
   * @param implicitWaitTimeout
   *          int value of seconds to wait before timing out
   * @param fluentRetryTime
   *          int value of seconds to wait before retry
   */
  public AppiumWait(AppiumDriver appiumDriver, int implicitWaitTimeout, int fluentRetryTime) {
    super(appiumDriver, implicitWaitTimeout, fluentRetryTime);
  }
}