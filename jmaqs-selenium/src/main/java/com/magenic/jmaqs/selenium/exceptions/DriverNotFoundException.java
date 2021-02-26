/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.selenium.exceptions;

import com.magenic.jmaqs.base.exceptions.JMAQSRuntimeException;

/**
 * The type Driver not found exception.
 */
public class DriverNotFoundException extends JMAQSRuntimeException {
  /**
   * Instantiates a new Driver not found exception.
   *
   * @param message the message
   */
  public DriverNotFoundException(String message, Exception exception) {
    super(message, exception);
  }

  /**
   * Instantiates a new Driver not found exception.
   *
   * @param message the message
   */
  public DriverNotFoundException(String message) {
    super(message);
  }
}
