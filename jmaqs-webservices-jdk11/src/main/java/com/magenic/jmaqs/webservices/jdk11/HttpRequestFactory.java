/*
 * Copyright 2020 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.webservices.jdk11;

import com.magenic.jmaqs.webservices.jdk8.MediaType;
import com.magenic.jmaqs.webservices.jdk8.WebServiceConfig;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;

public class HttpRequestFactory {

  /**
   * class constructor.
   */
  private HttpRequestFactory() {}

  /**
   * Gets a default TTP client based on configuration values.
   * @return A HTTP client
   */
  public static HttpRequest getDefaultRequest() {
    return getRequest(WebServiceConfig.getWebServiceUri(), "");
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
   * @param baseAddress the base website address
   * @param baseUri Base service uri
   * @param timeout Web service timeout
   * @param mediaType the media type being used
   * @return a HTTP Request
   */
  public static HttpRequest getRequest(String baseAddress, String baseUri, int timeout, MediaType mediaType) {
    return getRequest(baseAddress, baseUri, timeout, mediaType, null);
  }

  /**
   * Gets a HTTP Request based on configuration values.
   * @param baseUri Base service uri
   * @param timeout Web service timeout
   * @param mediaType media/content type to be received
   * @return A HTTP Request
   */
  public static HttpRequest getRequest(String baseAddress, String baseUri, int timeout, MediaType mediaType, Map<Object, Object> data) {
    HttpRequest.Builder builder = HttpRequest.newBuilder().uri(URI.create(baseAddress + baseUri))
        .timeout(Duration.ofSeconds(timeout)).header("Content-Type", mediaType.toString());

    if (baseUri.toLowerCase().contains("post")) {
      return builder.POST(buildFormDataFromMap(data)).build();
    } else if (baseUri.toLowerCase().contains("put")) {
      return builder.PUT(buildFormDataFromMap(data)).build();
    } else if (baseUri.toLowerCase().contains("delete")) {
      return builder.DELETE().build();
    }
    return builder.GET().build();
  }

  /**
   * Creates a Body publisher based on given data.
   * @param data the data to be turned into a body publisher
   * @return a Body Publisher
   */
  private static HttpRequest.BodyPublisher buildFormDataFromMap(Map<Object, Object> data) {
    StringBuilder stringBuilder = new StringBuilder();
    for (Map.Entry<Object, Object> entry : data.entrySet()) {
      if (stringBuilder.length() > 0) {
        stringBuilder.append("&");
      }
      stringBuilder.append(URLEncoder.encode(entry.getKey().toString(), StandardCharsets.UTF_8));
      stringBuilder.append("=");
      stringBuilder.append(URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8));
    }
    return HttpRequest.BodyPublishers.ofString(stringBuilder.toString());
  }
}
