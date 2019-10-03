/*
 * Copyright 2019 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.selenium.constants;

/**
 * Contains constants for web driver file names.
 */
public enum WebDriverFile {

  /**
   * The Chrome driver file.
   */
  CHROME("chromedriver.exe"),
  /**
   * The Firefox driver file.
   */
  FIREFOX("geckodriver.exe"),
  /**
   * The Internet Explorer driver file.
   */
  IE("IEDriverServer.exe"),
  /**
   * The Edge driver file.
   */
  EDGE("MicrosoftWebDriver.exe");

  /**
   * This driver file.
   */
  private String file;

  WebDriverFile(String file) {
    this.file = file;
  }

  public String getFile() {
    return file;
  }
}
