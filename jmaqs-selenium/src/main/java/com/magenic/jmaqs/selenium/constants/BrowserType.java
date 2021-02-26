/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.selenium.constants;

/**
 * Known browser types.
 */
public enum BrowserType {
  /**
   * Chrome web browser.
   */
  CHROME,

  /**
   * Edge web browser.
   */
  EDGE,

  /**
   * Firefox web browser.
   */
  FIREFOX,

  /**
   * Chrome web browser - run headless.
   */
  HEADLESS_CHROME,

  /**
   * IE web browser.
   */
  IE,

  /**
   * Remote web browser - Used when executing on Grid or cloud based provides like Sauce Labs.
   */
  REMOTE
}