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
    return getRequest(WebServiceConfig.getWebServiceUri(), "");
  }

  public static HttpRequest getRequest(String baseUri) {
    return getRequest(WebServiceConfig.getWebServiceUri(), baseUri);
  }

  /**
   * Gets a HTTP client based on configuration values.
   * @param baseUri Base service uri
   * @return a HTTP Request
   */
  public static HttpRequest getRequest(String baseAddress, String baseUri) {
    return getRequest(baseAddress, baseUri, WebServiceConfig.getWebServiceTimeout());
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

  public static HttpRequest getRequest(String baseAddress, String baseUri, MediaType mediaType) {
    return getRequest(baseAddress, baseUri, WebServiceConfig.getWebServiceTimeout(), mediaType, RequestType.GET);
  }

  public static HttpRequest getRequest(String baseAddress, String baseUri, int timeout, MediaType mediaType) {
    return getRequest(baseAddress, baseUri, timeout, mediaType, RequestType.GET);
  }

  public static HttpRequest getRequest(String baseAddress, String baseUri, MediaType mediaType, RequestType requestType) {
    return getRequest(baseAddress, baseUri, WebServiceConfig.getWebServiceTimeout(), mediaType, requestType);
  }

  /**
   * Gets a HTTP Request based on configuration values.
   * @param baseUri Base service uri
   * @param timeout Web service timeout
   * @param mediaType media/content type to be received
   * @return A HTTP Request
   */
  public static HttpRequest getRequest(String baseAddress, String baseUri, int timeout, MediaType mediaType, RequestType requestType) {
    HttpRequest.Builder builder = HttpRequest.newBuilder()
        .uri(URI.create(baseAddress + baseUri))
        .timeout(Duration.ofSeconds(timeout))
        .header("Content-Type", mediaType.toString());

    switch (requestType) {
      case POST:
        //return builder.POST().build();
      case PUT:
        //return builder.PUT().build();
      case DELETE:
        return builder.DELETE().build();
      case GET:
        return builder.GET().build();
      default:
        throw new UnsupportedOperationException("This request type is not supported");
    }
/*
    return HttpRequest.newBuilder()
        .uri(URI.create(baseUri))
        .timeout(Duration.ofSeconds(timeout))
        .header("Content-Type", mediaType.toString());
         */
  }
}
