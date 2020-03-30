/*
 * Copyright 2020 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.webservices.jdk11;

/**
 * The enum Media type.
 */
public enum MediaType {
  /**
   * App json media type.
   */
  APP_JSON("application/json"),
  /**
   * App xml media type.
   */
  APP_XML("application/xml"),
  /**
   * Plain text media type.
   */
  PLAIN_TEXT("text/plain"),
  /**
   * Image png media type.
   */
  IMAGE_PNG("image/png");

  /**
   * The Media type.
   */
  private final String mediaTypeString;

  MediaType(String string) {
    this.mediaTypeString = string;
  }

  /**
   * getter for the Property of the Enum.
   */
  public String toString() {
    return mediaTypeString;
  }
}
