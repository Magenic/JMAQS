/*
 * Copyright 2020 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.utilities.helper.exceptions;

/**
 * Custom TimeoutException, as the built in one does not allow for a passed in
 * inner exception.
 */
public class TimeoutException extends Exception {

  private static final long serialVersionUID = 1;

  /**
   * Initializes an instance of the TimeoutException class.
   *
   * @param errorMessage The desired custom error message
   */
  public TimeoutException(String errorMessage) {
    super(errorMessage);
  }

  /**
   * Initializes an instance of the TimeoutException class.
   *
   * @param errorMessage The desired custom error message
   * @param err          An inner exception
   */
  public TimeoutException(String errorMessage, Throwable err) {
    super(errorMessage, err);
  }
}
