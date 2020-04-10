package com.magenic.jmaqs.webservices.jdk11;

import com.magenic.jmaqs.webservices.jdk8.MediaType;
import com.magenic.jmaqs.webservices.jdk8.WebServiceConfig;
import java.net.URI;
import java.net.http.HttpRequest;
import java.time.Duration;

/**
 * Handles the request portion of the HTTP client request.
 */
public class HttpRequestFactory {
  /**
   * private constructor.
   */
  private HttpRequestFactory() { }

  /**
   * Gets a HTTP Request based on configuration values.
   * @param baseUri the web service uri to be run
   * @return A HTTP client
   */
  public static HttpRequest getRequest(String baseUri) {
    return getRequest(WebServiceConfig.getWebServiceUri(), baseUri);
  }

  /**
   * Gets a HTTP Request based on configuration values.
   * @param baseAddress the bast url
   * @param baseUri Base service uri
   * @return a HTTP Request
   */
  public static HttpRequest getRequest(String baseAddress, String baseUri) {
    return getRequest(baseAddress, baseUri, WebServiceConfig.getWebServiceTimeOut());
  }

  /**
   * Gets a HTTP Request based on configuration values.
   * @param baseAddress the bast url
   * @param baseUri Base service uri
   * @param timeout Web service timeout
   * @return a HTTP Request
   */
  public static HttpRequest getRequest(String baseAddress, String baseUri, int timeout) {
    return getRequest(baseAddress, baseUri, timeout, MediaType.APP_JSON, null);
  }

  /**
   * Gets a HTTP Request based on configuration values.
   * @param baseAddress the bast url
   * @param baseUri Base service uri
   * @param mediaType web service media type
   * @return a HTTP Request
   */
  public static HttpRequest getRequest(String baseAddress, String baseUri, MediaType mediaType) {
    return getRequest(baseAddress, baseUri, WebServiceConfig.getWebServiceTimeOut(), mediaType, null);
  }

  /**
   * Gets a HTTP Request based on configuration values.
   * @param baseAddress the bast url
   * @param baseUri Base service uri
   * @param timeout Web service timeout
   * @param mediaType media/content type to be received
   * @param content the content to be posted/put
   * @return A HTTP Request
   */
  public static HttpRequest getRequest(String baseAddress, String baseUri, int timeout, MediaType mediaType, String content) {
    HttpRequest.Builder builder = HttpRequest.newBuilder().uri(URI.create(baseAddress.concat(baseUri)))
        .timeout(Duration.ofSeconds(timeout)).header("Content-Type", mediaType.toString());

    if (baseUri.toLowerCase().contains("post")) {
      return builder.POST(HttpRequest.BodyPublishers.ofString(content)).build();
    } else if (baseUri.toLowerCase().contains("put")) {
      return builder.PUT(HttpRequest.BodyPublishers.ofString(content)).build();
    } else if (baseUri.toLowerCase().contains("delete")) {
      return builder.DELETE().build();
    }
    return builder.GET().build();
  }
}
