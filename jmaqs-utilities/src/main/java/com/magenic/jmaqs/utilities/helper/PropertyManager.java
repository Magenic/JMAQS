/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.utilities.helper;

/**
 * Handles the management of System properties from Maven at compile time.
 */
public class PropertyManager {

  /**
   * Private constructor to enforce non-instantiation.
   */
  private PropertyManager() {

  }

  /**
   * Gets the property set from maven cli.
   *
   * @param key The key to get the property.
   * @return The value of the property. Null if not found.
   */
  public static String get(String key) {
    return System.getProperty(key);
  }

  /**
   * Gets the property set from maven cli. if null, it returns
   * the default value.
   *
   * @param key          The key to get the property.
   * @param defaultValue The value to return if the key is null.
   * @return The value of the property or the default value if the value is null.
   */
  public static String get(String key, String defaultValue) {
    String property = get(key);
    return property == null ? defaultValue : property;
  }
}
