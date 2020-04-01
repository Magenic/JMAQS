package com.magenic.jmaqs.webservices.jdk11;

public enum RequestType {
  /**
   * Get Request Type.
   */
  GET("GET"),
  /**
   * GET Request Type.
   */
  POST("POST"),
  /**
   * Put Request Type.
   */
  PUT("PUT"),
  /**
   * Delete Request Type.
   */
  DELETE("DELETE");

  /**
   * The Media type.
   */
  private final String requestTypeString;

  RequestType(String mediaTypeString) {
    this.requestTypeString = mediaTypeString;
  }

  /**
   * getter for the Property of the Enum.
   */
  public String toString() {
    return requestTypeString;
  }
}
