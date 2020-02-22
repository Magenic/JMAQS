/*
 * Copyright 2020 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.utilities.helper;

/**
 * Custom FunctionException, as you can't throw a generic exception.
 */
public class FunctionException extends Exception {
  /**
   * Initializes an instance of the FunctionException class.
   *
   * @param errorMessage The desired custom error message
   * @param err          An inner exception
   */
  public FunctionException(String errorMessage, Throwable err) {
    super(errorMessage, err);
  }
}
