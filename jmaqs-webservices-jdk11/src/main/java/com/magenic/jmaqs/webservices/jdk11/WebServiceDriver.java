/*
 * Copyright 2020 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.webservices.jdk11;

import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static javafx.scene.input.KeyCode.T;

/**
 * The Web Service Driver.
 */
public class WebServiceDriver {
  /**
   * The base HTTP client control.
   */
  private HttpClient baseHttpClient;

  /**
   * the base HTTP request control.
   */
  private HttpRequest baseHttpRequest;

  /**
   * The URI to be stored for the Web Service.
   */  private URI baseAddress;

  /**
   * Class Constructor that sets the http Client.
   * @param newHttpClient the http client to be set.
   */
  public WebServiceDriver(HttpClient newHttpClient) {
    this.baseHttpClient = newHttpClient;
    this.baseHttpRequest = HttpRequestFactory.getDefaultRequest();
  }

  /**
   * Class constructor that sets the HttpRequest.
   * @param newHttpRequest the new Http request to be set
   * @throws IOException the Sexception if it occurs
   */
  public WebServiceDriver(HttpRequest newHttpRequest) throws IOException {
    this.baseHttpClient = HttpClientFactory.getDefaultClient();
    this.baseHttpRequest = newHttpRequest;
  }

  /**
   * Class Constructor that sets the base address as a URI.
   * @param baseAddress The base URI address to use
   */
  public WebServiceDriver(URI baseAddress) throws IOException {
    setHttpClient(HttpClientFactory.getDefaultClient());
    setHttpRequest(HttpRequestFactory.getRequest(baseAddress));
  }

  /**
   * Class Constructor that sets the base address as a string.
   * @param baseAddress The base URI address to use
   * @throws URISyntaxException URI syntax is invalid
   */
  public WebServiceDriver(String baseAddress) throws URISyntaxException, IOException {
    this(new URI(baseAddress));
  }

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
    return this.baseHttpClient;
  }

  /**
   * sets the Http Request.
   * @param httpRequest the new http request to be set
   */
  public void setHttpRequest(HttpRequest httpRequest){
    this.baseHttpRequest = httpRequest;
  }

  /**
   * gets the http request.
   * @return the http request
   */
  public HttpRequest getHttpRequest() {
    return this.baseHttpRequest;
  }

  /// <summary>
  /// Execute a web service get
  /// </summary>
  /// <param name="requestUri">The request uri</param>
  /// <param name="expectedMediaType">The type of media you are expecting back</param>
  /// <param name="expectSuccess">Assert a success code was returned</param>
  /// <returns>The response content as a string</returns>
  public String get(String requestUri, String expectedMediaType, boolean expectSuccess)
      throws HttpResponseException {
    HttpResponse response = this.getWithResponse(requestUri, expectedMediaType, expectSuccess);
    return response.Content.ReadAsStringAsync().GetAwaiter().GetResult();
  }

  /// <summary>
  /// Execute a web service get
  /// </summary>
  /// <param name="requestUri">The request uri</param>
  /// <param name="expectedMediaType">The type of media you are expecting back</param>
  /// <param name="expectedStatus">Assert a specific status code was returned</param>
  /// <returns>The response content as a string</returns>
  public String get(String requestUri, String expectedMediaType, HttpStatus expectedStatus)
      throws HttpResponseException {
    HttpResponse response = this.getWithResponse(requestUri, expectedMediaType, expectedStatus);
    return response.Content.ReadAsStringAsync().GetAwaiter().GetResult();
  }

  /// <summary>
  /// Execute a web service get
  /// </summary>
  /// <param name="requestUri">The request uri</param>
  /// <param name="expectedMediaType">The type of media you are expecting back</param>
  /// <param name="expectSuccess">Assert a success code was returned</param>
  /// <returns>The http response message</returns>
  public HttpResponse getWithResponse(String requestUri, String expectedMediaType, boolean expectSuccess)
      throws HttpResponseException {
    return this.getContent(requestUri, expectedMediaType, expectSuccess).GetAwaiter().GetResult();
  }

  /// <summary>
  /// Execute a web service get
  /// </summary>
  /// <param name="requestUri">The request uri</param>
  /// <param name="expectedMediaType">The type of media you are expecting back</param>
  /// <param name="expectedStatus">Assert a specific status code was returned</param>
  /// <returns>The http response message</returns>
  public HttpResponse getWithResponse(String requestUri, String expectedMediaType, HttpStatus expectedStatus)
      throws HttpResponseException {
    return this.getContent(requestUri, expectedMediaType, expectedStatus).GetAwaiter().GetResult();
  }

  /// <summary>
  /// Do a web service get for the given uri and media type
  /// </summary>
  /// <param name="requestUri">The request uri</param>
  /// <param name="mediaType">What type of media are we expecting</param>
  /// <param name="expectSuccess">Assert a success code was returned</param>
  /// <returns>A http response message</returns>
  protected HttpResponse getContent(String requestUri, String mediaType, boolean expectSuccess)
      throws HttpResponseException {
    this.checkIfMediaTypeNotPresent(mediaType);
    HttpResponse response = await this.HttpClient.GetAsync(requestUri).ConfigureAwait(false);

    // Should we check for success
    if (expectSuccess) {
      ensureSuccessStatusCode(response);
    }

    return response;
  }

  /// <summary>
  /// Do a web service get for the given uri and media type
  /// </summary>
  /// <param name="requestUri">The request uri</param>
  /// <param name="mediaType">What type of media are we expecting</param>
  /// <param name="expectedStatus">Assert a specific status code was returned</param>
  /// <returns>A http response message</returns>
  protected HttpResponse getContent(String requestUri, String mediaType, HttpStatus expectedStatus)
      throws HttpResponseException {
    this.checkIfMediaTypeNotPresent(mediaType);
    HttpResponse response = await this.HttpClient.GetAsync(requestUri).ConfigureAwait(false);

    // We check for specific status
    ensureStatusCodesMatch(response, expectedStatus);

    return response;
  }

  /**
   * Ensure the HTTP response was successful, if not throw a user friendly error message.
   * @param response The HTTP response
   * @throws HttpResponseException if the HttpResponse is null
   */
  public static <T> void ensureSuccessStatusCode(HttpResponse response) throws HttpResponseException {
    // Make sure a response was returned
    if (response == null) {
      throw new HttpResponseException(HttpStatus.SC_NO_CONTENT, "Response was null");
    }

    // Check if it was a success and if not create a user friendly error message
    if (response.statusCode() != HttpStatus.SC_OK) {
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
   * @throws HttpResponseException if the HttpResponse is null
   */
  public static <T> void ensureStatusCodesMatch(HttpResponse response, HttpStatus expectedStatus)
      throws HttpResponseException {
    // Make sure a response was returned
    if (response == null) {
      throw new HttpResponseException(HttpStatus.SC_NO_CONTENT, "Response was null");
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
   * Check if the media type is supported.
   * @param mediaType Media type to add
   */
  public void checkIfMediaTypeNotPresent(String mediaType) {
    // Make sure a media type was passed in
    if (mediaType.isEmpty()) {
      return;
    }

    // Look for the media type
    for (MediaType media : MediaType.values()) {
      if (media.toString().equals(mediaType)) {
        return;
      }
    }

    // Add the type or throw exception???
    throw new UnsupportedOperationException("Media Type is not supported");
  }
}