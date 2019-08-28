/*
 * Copyright 2019 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.utilities.logging;

/**
 * The type of message.
 */
public enum MessageType {
  /**
   * Suspended message.
   */
  SUSPENDED(-1),

  /**
   * Error message.
   */
  ERROR(0),

  /**
   * Warning message.
   */
  WARNING(1),

  /**
   * Success message.
   */
  SUCCESS(2),

  /**
   * Generic message.
   */
  GENERIC(3),

  /**
   * Informational message - Our default message type.
   */
  INFORMATION(4),

  /**
   * Verbose message.
   */
  VERBOSE(5);

  /**
   * The value of current Message Type.
   */
  private int value;

  /**
   * Initializes a new instance of Message Type.
   *
   * @param value
   *          The value.
   */
  MessageType(int value) {
    this.value = value;
  }

  /**
   * Gets the value of the current Message Type.
   *
   * @return
   *        Value of current Message Type.
   */
  public int getValue() {
    return this.value;
  }
}