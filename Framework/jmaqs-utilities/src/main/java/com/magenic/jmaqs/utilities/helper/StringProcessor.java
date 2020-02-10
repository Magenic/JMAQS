/*
 * Copyright 2019 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.utilities.helper;

/**
 * Initializes a new instance of the StringProcessor class.
 */
public final class StringProcessor {

  /**
   * Constructor StringProcessor creates a new StringProcessor instance.
   */
  private StringProcessor() {
  }

  /**
   * Creates a string based on the arguments. If no args are applied, then we want to just return
   * the message
   *
   * @param message The message being used
   * @param args    The arguments being used
   * @return A final string
   */
  public static String safeFormatter(String message, Object... args) {
    try {
      return String.format(message, args);
    } catch (Exception e) {
      StringBuilder builder = new StringBuilder();
      builder.append("Message: " + message);
      builder.append(" Arguments: ");
      for (Object arg : args) {
        builder.append(arg.toString() + " ");
      }

      return builder.toString();
    }
  }
}
