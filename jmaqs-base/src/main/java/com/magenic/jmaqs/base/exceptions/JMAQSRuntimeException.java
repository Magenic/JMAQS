/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.base.exceptions;

/**
 * The JMAQS Runtime exception.
 */
public class JMAQSRuntimeException extends RuntimeException {

  /**
   * JMAQS runtime exception.
   * @param message the message to be displayed in the exception.
   * @param exception the exception to be thrown
   */
  public JMAQSRuntimeException(String message, Exception exception) {
    super(message, exception);
  }

  /**
   * JMAQS runtim exception.
   * @param message the message to be displayed in the exception.
   */
  public JMAQSRuntimeException(String message) {
    super(message);
  }
}

