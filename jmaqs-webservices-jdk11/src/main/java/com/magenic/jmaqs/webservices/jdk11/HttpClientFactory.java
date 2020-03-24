package com.magenic.jmaqs.webservices.jdk11;

import java.io.IOException;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.time.Duration;

/**
 * Http client factory
 */
public class HttpClientFactory {
  /**
   * Gets a HTTP client based on configuration values.
   * @return A HTTP client
   * @throws URISyntaxException if the uri has a syntax issue
   */
  public static HttpClient getDefaultClient() throws URISyntaxException, IOException {
    return getClient(WebServiceConfig.getWebServiceUri(),
        WebServiceConfig.getWebServiceTimeOut());
  }

  /**
   * Gets a HTTP client based on configuration values.
   * @param baseAddress Base service uri
   * @param timeout Web service timeout
   * @return A HTTP client
   */
  public static HttpClient getClient(String baseAddress, int timeout) throws IOException {
    return getClient(URI.create(baseAddress), Duration.ofSeconds(timeout), WebServiceConfig.getUseProxy(),
        new URL(WebServiceConfig.getProxyAddress()));
  }

  /**
   * Gets a HTTP client based on configuration values.
   * @param baseAddress Base service uri
   * @param timeout Web service timeout
   * @param useProxy Use a proxy
   * @param proxyAddress The proxy address to use
   * @return A HTTP client
   */
  public static HttpClient getClient(URI baseAddress, Duration timeout,
      boolean useProxy, URL proxyAddress) throws IOException {
    HttpRequest request = HttpRequest.newBuilder(baseAddress).build();
    HttpClient client = null;

    if (useProxy) {
      client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).connectTimeout(timeout)
          .proxy(ProxySelector.of(new InetSocketAddress(String.valueOf(proxyAddress), 8080)))
          .authenticator(Authenticator.getDefault()).build();
    } else {
      client = HttpClient.newHttpClient();
      client.s
    }

    HttpClient.newBuilder().connectTimeout(timeout).build();

    return new HttpClient(handler)
    {
      BaseAddress = baseAddress,
      Timeout = timeout
    };
  }
}
