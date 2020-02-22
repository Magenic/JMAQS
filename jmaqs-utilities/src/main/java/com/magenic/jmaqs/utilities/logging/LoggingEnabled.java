/*
 * Copyright 2020 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.utilities.logging;

/**
 * The type of message.
 */
public enum LoggingEnabled {
  /**
   * Yes log.
   */
  YES,

  /**
   * Only save a log when there is a failure.
   */
  ONFAIL,

  /**
   * No, don't log.
   */
  NO,
}