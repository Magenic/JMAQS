package com.magenic.jmaqs.webservices.jdk11;

import java.net.URI;
import java.net.http.HttpRequest;
import java.time.Duration;

public class HttpRequestFactory {
  /**
   * Gets a HTTP client based on configuration values.
   * @return A HTTP client
   */
  public static HttpRequest getDefaultRequest() {
    return getRequest(URI.create(WebServiceConfig.getWebServiceUri()));
  }

  /**
   * Gets a HTTP client based on configuration values.
   * @param baseUri Base service uri
   * @return a HTTP Request
   */
  public static HttpRequest getRequest(URI baseUri) {
    return getRequest(baseUri, WebServiceConfig.getWebServiceTimeOut());
  }

  /**
   * Gets a HTTP Request based on configuration values.
   * @param baseUri Base service uri
   * @param timeout Web service timeout
   * @return a HTTP Request
   */
  public static HttpRequest getRequest(URI baseUri, int timeout) {
    return getRequest(baseUri, timeout, MediaType.APP_JSON);
  }

  /**
   * Gets a HTTP Request based on configuration values.
   * @param baseUri Base service uri
   * @param timeout Web service timeout
   * @param mediaType media/content type to be received
   * @return A HTTP Request
   */
  public static HttpRequest getRequest(URI baseUri, int timeout, MediaType mediaType) {
    return HttpRequest.newBuilder()
        .uri(baseUri)
        .timeout(Duration.ofSeconds(timeout))
        .header("Content-Type", mediaType.toString())
        .build();
  }
}
