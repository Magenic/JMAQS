/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.base.exceptions;

public class JMAQSRuntimeException extends RuntimeException {

  public JMAQSRuntimeException(String message, Exception exception) {
    super(message, exception);
  }

  public JMAQSRuntimeException(String message) {
    super(message);
  }
}

