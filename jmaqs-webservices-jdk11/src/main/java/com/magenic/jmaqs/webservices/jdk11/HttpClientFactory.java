package com.magenic.jmaqs.webservices.jdk11;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.http.HttpClient;
import java.time.Duration;

/**
 * Http client factory
 */
public class HttpClientFactory {
  /**
   * Gets a HTTP client based on configuration values.
   * @return A HTTP client
   * @throws IOException if there is an IO Exception
   */
  public static HttpClient getDefaultClient() throws IOException {
    return getClient(WebServiceConfig.getWebServiceTimeOut());
  }

  /**
   * Gets a HTTP client based on configuration values.
   * @param timeout Web service timeout
   * @return A HTTP client
   */
  public static HttpClient getClient(int timeout) throws IOException {
    return getClient(Duration.ofSeconds(timeout), WebServiceConfig.getUseProxy(),
        WebServiceConfig.getProxyAddress());
  }

  /**
   * Gets a HTTP client based on configuration values.
   * @param timeout Web service timeout
   * @param useProxy Use a proxy
   * @param proxyAddress The proxy address to use
   * @return A HTTP client
   */
  public static HttpClient getClient(Duration timeout, boolean useProxy, String proxyAddress) {
    HttpClient client;

    if (useProxy) {
      // sets up proxy settings
      client = HttpClient.newBuilder()
          .version(HttpClient.Version.HTTP_2)
          .followRedirects(HttpClient.Redirect.NORMAL)
          //.authenticator(Authenticator.getDefault())
          .connectTimeout(timeout)
          .proxy(ProxySelector.of(new InetSocketAddress(proxyAddress, 8080)))
          .build();
    } else {
      client = HttpClient.newBuilder()
          .version(HttpClient.Version.HTTP_2)
          .followRedirects(HttpClient.Redirect.NORMAL)
          //.authenticator(Authenticator.getDefault())
          .connectTimeout(timeout)
          .build();
    }
    return client;
  }
}
