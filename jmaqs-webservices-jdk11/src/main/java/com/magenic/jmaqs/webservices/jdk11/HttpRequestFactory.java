package com.magenic.jmaqs.webservices.jdk11;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;

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
    return getRequest(baseAddress, baseUri, timeout, MediaType.APP_JSON, null);
  }

  /**
   * Gets a HTTP Request based on configuration values.
   * @param baseUri Base service uri
   * @param mediaType web service media type
   * @return a HTTP Request
   */
  public static HttpRequest getRequest(String baseAddress, String baseUri, MediaType mediaType) {
    return getRequest(baseAddress, baseUri, WebServiceConfig.getWebServiceTimeOut(), mediaType, null);
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
/*
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
 */
  }

    private static BodyPublisher buildFormDataFromMap(Map<Object, Object> data) {
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
