/*
 * Copyright 2020 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.webservices.jdk11;

import com.magenic.jmaqs.webservices.jdk8.MediaType;
import com.magenic.jmaqs.webservices.jdk8.WebServiceConfig;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.time.Duration;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Http request factory.
 */
public class HttpRequestFactory {
  /**
   * class constructor.
   */
  private HttpRequestFactory() { }

  /**
   * Gets a default TTP client based on configuration values.
   * @return A HTTP client
   */
  public static HttpRequest getDefaultRequest() {
    return getRequest(WebServiceConfig.getWebServiceUri());
  }

  /**
   * Gets a HTTP client based on configuration values.
   * @param baseUri Base service uri
   * @return A HTTP client
   */
  public static HttpRequest getRequest(String baseUri) {
    return getRequest(WebServiceConfig.getWebServiceUri(), baseUri);
  }

  /**
   * Gets a HTTP client based on configuration values.
   * @param baseAddress the base url address
   * @param baseUri Base service uri
   * @return a HTTP Request
   */
  public static HttpRequest getRequest(String baseAddress, String baseUri) {
    return getRequest(baseAddress, baseUri, WebServiceConfig.getWebServiceTimeOut());
  }

  /**
   * Gets a HTTP Request based on configuration values.
   * @param baseAddress the base url address
   * @param baseUri Base service uri
   * @param timeout Web service timeout
   * @return a HTTP Request
   */
  public static HttpRequest getRequest(String baseAddress, String baseUri, int timeout) {
    return getRequest(baseAddress, baseUri, timeout, MediaType.APP_JSON);
  }

  /**
   * Gets a HTTP Request based on configuration values.
   * @param baseAddress the base website address
   * @param baseUri Base service uri
   * @param timeout Web service timeout
   * @param mediaType the media type being used
   * @return a HTTP Request
   */
  public static HttpRequest getRequest(String baseAddress, String baseUri, int timeout, MediaType mediaType) {
    return setUpRequest(baseAddress, baseUri, timeout, mediaType, "", RequestMethod.GET);
  }

  /**
   * Gets a HTTP Request based on configuration values.
   * @param baseAddress the base url address
   * @param baseUri Base service uri
   * @param timeout Web service timeout
   * @param mediaType media/content type to be received
   * @param content the content to be used in a POST/PUT/PATCH
   * @param requestType the type of request to be done
   * @return A HTTP Request
   */
  public static HttpRequest setUpRequest(String baseAddress, String baseUri, int timeout,
      MediaType mediaType, String content, RequestMethod requestType) {
    HttpRequest.Builder builder = HttpRequest.newBuilder()
        .uri(URI.create(baseAddress + baseUri))
        .timeout(Duration.ofSeconds(timeout))
        .version(HttpClient.Version.HTTP_2)
        .header("Content-Type", mediaType.toString());

    if (requestType.equals(RequestMethod.POST)) {
      return builder.POST(HttpRequest.BodyPublishers.ofString(content)).build();
    } else if (requestType.equals(RequestMethod.PUT)) {
      return builder.PUT(HttpRequest.BodyPublishers.ofString(content)).build();
    } else if (requestType.equals(RequestMethod.DELETE)) {
      return builder.DELETE().build();
    } else if (requestType.equals(RequestMethod.PATCH)) {
      builder.method("PATCH", HttpRequest.BodyPublishers.ofString(content));
    }
    return builder.GET().build();
  }
}
