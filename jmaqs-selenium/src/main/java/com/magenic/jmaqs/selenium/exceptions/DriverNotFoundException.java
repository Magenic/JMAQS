/*
 * Copyright 2020 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.selenium.exceptions;

/**
 * The type Driver not found exception.
 */
public class DriverNotFoundException extends RuntimeException {
  /**
   * Instantiates a new Driver not found exception.
   *
   * @param message the message
   */
  public DriverNotFoundException(String message) {
    super(message);
  }
}
