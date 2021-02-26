/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.selenium.exceptions;

import com.magenic.jmaqs.base.exceptions.JMAQSRuntimeException;

/**
 * The type Web driver factory exception.
 */
public class WebDriverFactoryException extends JMAQSRuntimeException {

  /**
   * Instantiates a new Web driver factory exception.
   *
   * @param message   the message
   * @param exception the exception
   */
  public WebDriverFactoryException(String message, Exception exception) {
    super(message, exception);
  }
}
