/*
 * Copyright 2020 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.selenium.exceptions;

public class WebDriverFactoryException extends Exception {
  public WebDriverFactoryException(String message, Exception exception) {
    super(message, exception);
  }
}
