/*
 * Copyright 2020 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.base.exceptions;

public class DriverDisposalException extends RuntimeException {
  public DriverDisposalException(String message, Exception exception) {
    super(message, exception);
  }
}
