/*
 * Copyright 2020 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.webservices.jdk11;

import com.beust.jcommander.internal.Lists;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;


/**
 * The Web Service Driver.
 */
public class WebServiceDriver {
  /**
   * The base HTTP client control.
   */
  private HttpClient baseHttpClient;

  /**
   * The URI to be stored for the Web Service.
   */
  private URI baseAddress;

  /**
   * Sets http client.
   *
   * @param httpClient the http client
   */
  public void setHttpClient(HttpClient httpClient) {
    this.baseHttpClient = httpClient;
  }

  /**
   * Gets http client.
   *
   * @param mediaType the media type
   * @return the http client
   */
  public HttpClient getHttpClient(String mediaType) {
    if (this.baseHttpClient == null) {
      Header header = new BasicHeader(HttpHeaders.CONTENT_TYPE, mediaType);
      this.baseHttpClient = HttpClientBuilder.create().setDefaultHeaders(Lists.newArrayList(header))
          //.setDefaultRequestConfig(getRequestTimeouts()).build();
          .setDefaultRequestConfig(10000).build();
    }
    return this.baseHttpClient;
  }


  /**
   * Constructor.
   *
   * @param baseAddress
   *          The base URI address to use
   * @throws URISyntaxException
   *           URI syntax is invalid
   */
  public WebServiceDriver(String baseAddress) throws URISyntaxException {
    this(new URI(baseAddress));
  }

  /**
   * Constructor.
   *
   * @param baseAddress
   *          The base URI address to use
   */
  public WebServiceDriver(URI baseAddress) {
    this.baseAddress = baseAddress;
  }

  /**
   * Class Constructor that sets the http Client.
   * @param newHttpClient the http client to be set.
   */
  public WebServiceDriver(HttpClient newHttpClient) {
    this.baseHttpClient = newHttpClient;
  }

  /**
   * Ensure the HTTP response was successful, if not throw a user friendly error message.
   * @param response The HTTP response
   * @throws HttpResponseException
   */
  private static void ensureSuccessStatusCode(HttpResponse response) throws HttpResponseException {
    // Make sure a response was returned
    if (response == null) {
      throw new HttpResponseException(response.statusCode(), "Response was null");
    }

    // Check if it was a success and if not create a user friendly error message
    if (response.statusCode() != 200) {
      String body = response.body().toString();

      throw new HttpResponseException(response.statusCode(),
          String.format("Response did not indicate a success. %s Response code was: %s",
              System.lineSeparator(), body));
    }
  }

  /**
   * Ensure the HTTP response has specified status, if not throw a user friendly error message.
   * @param response The HTTP response
   * @param expectedStatus Assert a specific status code was returned
   * @throws HttpResponseException
   */
  private static void ensureStatusCodesMatch(HttpResponse response, HttpStatus expectedStatus)
      throws HttpResponseException {
    // Make sure a response was returned
    if (response == null) {
      throw new HttpResponseException(response.statusCode(), "Response was null");
    }

    // Check if it was a success and if not create a user friendly error message
    if (response.statusCode() != expectedStatus.hashCode()) {
      String body = response.body().toString();
      throw new HttpResponseException(response.statusCode(),
          String.format("Response status did not match expected. %s "
                  + "Response code was: %s %s Expected code was: %s %s"
                  + "Body: %s", System.lineSeparator(), response.statusCode(),
              System.lineSeparator(), expectedStatus.hashCode(), System.lineSeparator(), body));
    }
  }

  /**
   * Add accept media type if it isn't already added
   * @param mediaType Media type to add
   */
  private void addAcceptIfNotPresent(String mediaType) {
    // Make sure a media type was passed in
    if (mediaType.isEmpty() || mediaType == null) {
      return;
    }

    // Look for the media type
    for (MediaTypeWithQualityHeaderValue header : this.baseHttpClient.DefaultRequestHeaders.Accept) {
      if (header.MediaType.Equals(mediaType, StringComparison.CurrentCultureIgnoreCase)) {
        // Type was found so return
        return;
      }
    }

    // Add the type
    this.baseHttpClient.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue(mediaType));
  }
}