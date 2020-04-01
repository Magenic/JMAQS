package com.magenic.jmaqs.webservices.jdk11;

import java.net.URI;
import java.net.http.HttpRequest;
import java.time.Duration;

public class HttpRequestFactory {
  /**
   * Gets a HTTP Request based on configuration values.
   * @return A HTTP client
   */
  public static HttpRequest getRequest(String baseUri) {
    return getRequest(WebServiceConfig.getWebServiceUri(), baseUri);
  }

  /**
   * Gets a HTTP Request based on configuration values.
   * @param baseUri Base service uri
   * @return a HTTP Request
   */
  public static HttpRequest getRequest(String baseAddress, String baseUri) {
    return getRequest(baseAddress, baseUri, WebServiceConfig.getWebServiceTimeOut());
  }

  /**
   * Gets a HTTP Request based on configuration values.
   * @param baseUri Base service uri
   * @param timeout Web service timeout
   * @return a HTTP Request
   */
  public static HttpRequest getRequest(String baseAddress, String baseUri, int timeout) {
    return getRequest(baseAddress, baseUri, timeout, MediaType.APP_JSON);
  }

  /**
   * Gets a HTTP Request based on configuration values.
   * @param baseUri Base service uri
   * @param mediaType web service media type
   * @return a HTTP Request
   */
  public static HttpRequest getRequest(String baseAddress, String baseUri, MediaType mediaType) {
    return getRequest(baseAddress, baseUri, WebServiceConfig.getWebServiceTimeOut(), mediaType);
  }

  /**
   * Gets a HTTP Request based on configuration values.
   * @param baseUri Base service uri
   * @param timeout Web service timeout
   * @param mediaType media/content type to be received
   * @return A HTTP Request
   */
  public static HttpRequest getRequest(String baseAddress, String baseUri, int timeout, MediaType mediaType) {
    return HttpRequest.newBuilder()
        .uri(URI.create(baseAddress + baseUri))
        .timeout(Duration.ofSeconds(timeout))
        .header("Content-Type", mediaType.toString())
        .build();
  }
}
