/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.webservices.jdk11;

import com.magenic.jmaqs.webservices.jdk8.MediaType;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.server.NotAcceptableStatusException;

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
  private HttpRequest.Builder baseHttpRequestBuilder;

  /**
   * Class Constructor that sets the http Client.
   * @param newHttpClient the http client to be set.
   */
  public WebServiceDriver(HttpClient newHttpClient) {
    this.baseHttpClient = newHttpClient;
    this.baseHttpRequestBuilder = HttpRequest.newBuilder();
  }

  /**
   * Class constructor that sets the HttpRequest Builder.
   * @param newHttpRequestBuilder the new Http request Builder to be set
   */
  public WebServiceDriver(HttpRequest.Builder newHttpRequestBuilder) {
    this.baseHttpClient = HttpClientFactory.getDefaultClient();
    this.baseHttpRequestBuilder = newHttpRequestBuilder;
  }

  /**
   * Class Constructor that sets both the HttpRequest Builder and Http Client.
   * @param newHttpClient the Http Client
   * @param newHttpRequestBuilder the Http Request Builder
   */
  public WebServiceDriver(HttpClient newHttpClient, HttpRequest.Builder newHttpRequestBuilder) {
    this.baseHttpClient = newHttpClient;
    this.baseHttpRequestBuilder = newHttpRequestBuilder;
  }

  /**
   * Sets http client.
   * @param httpClient the http client
   */
  public void setHttpClient(HttpClient httpClient) {
    this.baseHttpClient = httpClient;
  }

  /**
   * Gets http client.
   * @return the http client
   */
  public HttpClient getHttpClient() {
    return this.baseHttpClient;
  }

  /**
   * Sets the Http Request Builder.
   * @param httpRequestBuilder the new http request Builder to be set
   */
  public void setHttpRequestBuilder(HttpRequest.Builder httpRequestBuilder) {
    this.baseHttpRequestBuilder = httpRequestBuilder;
  }

  /**
   * Gets the http request Builder.
   * @return the http request Builder
   */
  public HttpRequest.Builder getHttpRequestBuilder() {
    return this.baseHttpRequestBuilder;
  }

  /**
   * Execute a web service get.
   * @param requestUri The request uri
   * @param responseMediaType The type of media you are expecting back
   * @param expectSuccess Assert a success code was returned
   * @return The response content as a string
   * @throws IOException if exception is thrown
   * @throws InterruptedException if exception is thrown
   */
  public HttpResponse<String> get(String requestUri, MediaType responseMediaType, boolean expectSuccess)
      throws IOException, InterruptedException {
    return this.getContent(requestUri, responseMediaType, expectSuccess);
  }

  /**
   * Execute a web service get.
   * @param requestUri The request uri
   * @param responseMediaType The type of media you are expecting back
   * @param expectedStatus Assert a specific status code was returned
   * @return The response content as a string
   * @throws IOException if exception is thrown
   * @throws InterruptedException if exception is thrown
   */
  public HttpResponse<String> get(String requestUri, MediaType responseMediaType,
      HttpStatus expectedStatus) throws IOException, InterruptedException {
    return this.getContent(requestUri, responseMediaType, expectedStatus);
  }

  /**
   * Execute a web service get.
   * @param requestUri The request uri
   * @param responseMediaType The type of media you are expecting back
   * @param expectSuccess Assert a success code was returned
   * @param type The Object Type
   * @param <T> The Object Type
   * @return The De-serialized Object
   * @throws IOException if exception is thrown
   * @throws InterruptedException if exception is thrown
   */
  public <T> T get(String requestUri, MediaType responseMediaType, boolean expectSuccess, Type type)
      throws IOException, InterruptedException {
    return this.getContent(requestUri, responseMediaType, expectSuccess, type);
  }

  /**
   * Execute a web service get.
   * @param requestUri The request uri
   * @param responseMediaType The type of media you are expecting back
   * @param expectedStatus Assert a specific status code was returned
   * @param type The Object Type
   * @param <T> The Object Type
   * @return The De-serialized Object
   * @throws IOException if exception is thrown
   * @throws InterruptedException if exception is thrown
   */
  public <T> T get(String requestUri, MediaType responseMediaType, HttpStatus expectedStatus, Type type)
      throws IOException, InterruptedException {
    return this.getContent(requestUri, responseMediaType, expectedStatus, type);
  }

  /**
   * Do a web service get for the given uri and media type.
   * @param requestUri The request uri
   * @param responseMediaType What type of media are we expecting
   * @param expectSuccess Assert a success code was returned
   * @return A http response message
   * @throws IOException if the exception is thrown
   * @throws InterruptedException if the exception is thrown
   */
  protected HttpResponse<String> getContent(String requestUri, MediaType responseMediaType, boolean expectSuccess)
      throws IOException, InterruptedException {
    this.checkIfMediaTypeNotPresent(responseMediaType.toString());

    HttpRequest httpRequest = buildHttpRequest(requestUri, RequestMethod.GET, responseMediaType);
    HttpResponse<String> response = baseHttpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

    // Should we check for success
    if (expectSuccess) {
      ensureSuccessStatusCode(response);
    }
    return response;
  }

  /**
   * Do a web service get for the given uri and media type.
   * @param requestUri The request uri
   * @param responseMediaType What type of media are we expecting
   * @param expectedStatus Assert a specific status code was returned
   * @return A http response message
   * @throws IOException if the exception is thrown
   * @throws InterruptedException if the exception is thrown
   */
  protected HttpResponse<String> getContent(String requestUri, MediaType responseMediaType, HttpStatus expectedStatus)
      throws IOException, InterruptedException {
    this.checkIfMediaTypeNotPresent(responseMediaType.toString());

    HttpRequest httpRequest = buildHttpRequest(requestUri, RequestMethod.GET, responseMediaType);
    HttpResponse<String> response = baseHttpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

    // We check for specific status
    ensureStatusCodesMatch(response, expectedStatus);
    return response;
  }

  /**
   * Do a web service get for the given uri and media type.
   * @param requestUri The request uri
   * @param responseMediaType What type of media are we expecting
   * @param expectSuccess Assert a success code was returned
   * @param type Type of object to deserialize into
   * @param <T> Type of object to deserialize into
   * @return De-serialized Object
   * @throws IOException if the exception is thrown
   * @throws InterruptedException if the exception is thrown
   */
  protected <T> T getContent(String requestUri, MediaType responseMediaType, boolean expectSuccess, Type type)
      throws IOException, InterruptedException {
    this.checkIfMediaTypeNotPresent(responseMediaType.toString());

    HttpRequest httpRequest = buildHttpRequest(requestUri, RequestMethod.GET, responseMediaType);
    HttpResponse<String> response = baseHttpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

    // Should we check for success
    if (expectSuccess) {
      ensureSuccessStatusCode(response);
    }
    return WebServiceUtilities.getResponseBody(response, responseMediaType, type);
  }

  /**
   * Do a web service get for the given uri and media type.
   * @param requestUri The request uri
   * @param responseMediaType What type of media are we expecting
   * @param expectedStatus Assert a specific status code was returned
   * @param type Type of object to deserialize into
   * @param <T> Type of object to deserialize into
   * @return De-serialized Object
   * @throws IOException if the exception is thrown
   * @throws InterruptedException if the exception is thrown
   */
  protected <T> T getContent(String requestUri, MediaType responseMediaType, HttpStatus expectedStatus, Type type)
      throws IOException, InterruptedException {
    this.checkIfMediaTypeNotPresent(responseMediaType.toString());

    HttpRequest httpRequest = buildHttpRequest(requestUri, RequestMethod.GET, responseMediaType);
    HttpResponse<String> response = baseHttpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

    // We check for specific status
    ensureStatusCodesMatch(response, expectedStatus);
    return WebServiceUtilities.getResponseBody(response, responseMediaType, type);
  }

  /**
   * Execute a web service post.
   * @param requestUri The request uri
   * @param responseMediaType The type of media being requested
   * @param content The post content
   * @param type the object to deserialize into
   * @param expectSuccess Assert a success code was returned
   * @param <T> The expected response type
   * @return The response to deserialize as - T
   * @throws IOException if exception is thrown
   * @throws InterruptedException if exception is thrown
   */
  public <T> T  post(String requestUri, MediaType responseMediaType, Object content,
      Type type, boolean expectSuccess) throws IOException, InterruptedException {
    HttpResponse<String> response = this.postContent(requestUri, responseMediaType, content, expectSuccess);
    return WebServiceUtilities.deserializeResponse(response, responseMediaType, type);
  }

  /**
   * Execute a web service post.
   * @param requestUri The request uri
   * @param responseMediaType The type of media being requested
   * @param content The post content
   * @param type the object to deserialize into
   * @param expectedStatus Assert a specific status code was returned
   * @param <T> The expected response type
   * @return The response will deserialize as - T
   * @throws IOException if exception is thrown
   * @throws InterruptedException if exception is thrown
   */
  public <T> T post(String requestUri, MediaType responseMediaType,
      Object content, Type type, HttpStatus expectedStatus)
      throws IOException, InterruptedException {
    HttpResponse<String> response = this.postContent(requestUri, responseMediaType, content, expectedStatus);
    return WebServiceUtilities.deserializeResponse(response, responseMediaType, type);
  }

  /**
   * Execute a web service post.
   * @param requestUri The request uri
   * @param responseMediaType The type of media being requested
   * @param content How to encode the post content
   * @param expectSuccess Assert a success code was returned
   * @return The response body as a string
   * @throws IOException if exception is thrown
   * @throws InterruptedException if exception is thrown
   */
  public HttpResponse<String> post(String requestUri, MediaType responseMediaType, Object content,
      boolean expectSuccess) throws IOException, InterruptedException {
    return this.postContent(requestUri, responseMediaType, content, expectSuccess);
  }

  /**
   * Execute a web service post.
   * @param requestUri The request uri
   * @param responseMediaType The type of media being requested
   * @param content The post content
   * @param expectedStatus Assert a specific status code was returned
   * @return The response body as a string
   * @throws IOException if exception is thrown
   * @throws InterruptedException if exception is thrown
   */
  public HttpResponse<String> post(String requestUri, MediaType responseMediaType, Object content,
      HttpStatus expectedStatus) throws IOException, InterruptedException {
    return this.postContent(requestUri, responseMediaType, content, expectedStatus);
  }

  /**
   * Execute a web service post for the given uri, content and media type.
   * @param requestUri The request uri
   * @param responseMediaType The type of media being requested
   * @param content How to encode the post content
   * @param expectSuccess Assert a success code was returned
   * @return The response body as a string
   * @throws IOException if exception is thrown
   * @throws InterruptedException if exception is thrown
   */
  protected HttpResponse<String> postContent(String requestUri, MediaType responseMediaType,
      Object content, boolean expectSuccess) throws IOException, InterruptedException {
    this.checkIfMediaTypeNotPresent(responseMediaType.toString());

    HttpRequest httpRequest = buildHttpRequest(requestUri, RequestMethod.POST,
        responseMediaType, createContent(content, responseMediaType));
    HttpResponse<String> response = baseHttpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

    // Should we check for success
    if (expectSuccess) {
      ensureSuccessStatusCode(response);
    }
    return response;
  }

  /**
   * Execute a web service post for the given uri, content and media type.
   * @param requestUri The request uri
   * @param responseMediaType The type of media being requested
   * @param content The post content
   * @param expectedStatus Assert a specific status code was returned
   * @return The response body as a string
   * @throws IOException if exception is thrown
   * @throws InterruptedException if exception is thrown
   */
  protected HttpResponse<String> postContent(String requestUri, MediaType responseMediaType,
      Object content, HttpStatus expectedStatus) throws IOException, InterruptedException {
    this.checkIfMediaTypeNotPresent(responseMediaType.toString());

    HttpRequest httpRequest = buildHttpRequest(requestUri, RequestMethod.POST,
        responseMediaType, createContent(content, responseMediaType));
    HttpResponse<String> response = baseHttpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

    // We check for specific status
    ensureStatusCodesMatch(response, expectedStatus);
    return response;
  }

  /**
   * Execute a web service put.
   *
   * @param requestUri        The request uri
   * @param expectedMediaType The type of media being requested
   * @param content           The put content
   * @param type              the request type
   * @param expectSuccess     Assert a success code was returned
   * @return The response to deserialize as the Http Response
   * @throws IOException          if the exception is thrown
   * @throws InterruptedException if the exception is thrown
   */
  public <T> T put(String requestUri, MediaType expectedMediaType, Object content,
      Type type, boolean expectSuccess) throws IOException, InterruptedException {
    HttpResponse<String> response =  this.putContent(requestUri, expectedMediaType, content, expectSuccess);
    return WebServiceUtilities.deserializeResponse(response, expectedMediaType, type);
  }

  /**
   * Execute a web service put.
   *
   * @param requestUri        The request uri
   * @param expectedMediaType The type of media being requested
   * @param content           The put content
   * @param type              the request type
   * @param expectedStatus    Assert a success code was returned
   * @return The response to deserialize as the Http Response
   * @throws IOException          if the exception is thrown
   * @throws InterruptedException if the exception is thrown
   */
  public <T> T put(String requestUri, MediaType expectedMediaType, Object content,
      Type type, HttpStatus expectedStatus) throws IOException, InterruptedException {
    HttpResponse<String> response = this.putContent(requestUri, expectedMediaType, content, expectedStatus);
    return WebServiceUtilities.deserializeResponse(response, expectedMediaType, type);
  }

  /**
   * Execute a web service put.
   *
   * @param requestUri        The request uri
   * @param expectedMediaType The type of media being requested
   * @param content           The put content
   * @param expectSuccess     Assert a success code was returned
   * @return The response body as a string
   * @throws IOException          if the exception is thrown
   * @throws InterruptedException if the exception is thrown
   */
  public HttpResponse<String> put(String requestUri, MediaType expectedMediaType, Object content,
      boolean expectSuccess) throws IOException, InterruptedException {
    return this.putContent(requestUri, expectedMediaType, content, expectSuccess);
  }

  /**
   * Execute a web service put.
   *
   * @param requestUri        The request uri
   * @param expectedMediaType The type of media being requested
   * @param content           The put content
   * @param expectedStatus     Assert a success code was returned
   * @return The response body as a string
   * @throws IOException          if the exception is thrown
   * @throws InterruptedException if the exception is thrown
   */
  public HttpResponse<String> put(String requestUri, MediaType expectedMediaType, Object content,
      HttpStatus expectedStatus) throws IOException, InterruptedException {
    return this.putContent(requestUri, expectedMediaType, content, expectedStatus);
  }

  /**
   * Do a web service put for the given uri, content and media type.
   *
   * @param requestUri        The request uri
   * @param responseMediaType The response media type
   * @param content           The put body
   * @param expectSuccess     Assert a success code was returned
   * @return A http response message
   * @throws IOException          if the exception is thrown
   * @throws InterruptedException if the exception is thrown
   */
  protected HttpResponse<String> putContent(String requestUri, MediaType responseMediaType,
      Object content, boolean expectSuccess) throws IOException, InterruptedException {
    this.checkIfMediaTypeNotPresent(responseMediaType.toString());

    HttpRequest httpRequest = buildHttpRequest(requestUri, RequestMethod.PUT,
        responseMediaType, createContent(content, responseMediaType));
    HttpResponse<String> response = baseHttpClient
        .send(httpRequest, HttpResponse.BodyHandlers.ofString());

    // Should we check for success
    if (expectSuccess) {
      ensureSuccessStatusCode(response);
    }
    return response;
  }

  /**
   * Do a web service put for the given uri, content and media type.
   *
   * @param requestUri        The request uri
   * @param responseMediaType The response media type
   * @param content           The put body
   * @param expectedStatus    Assert a specific status code was returned
   * @return A http response message
   * @throws IOException          if exception is thrown
   * @throws InterruptedException if exception is thrown
   */
  protected HttpResponse<String> putContent(String requestUri, MediaType responseMediaType,
      Object content, HttpStatus expectedStatus) throws IOException, InterruptedException {
    this.checkIfMediaTypeNotPresent(responseMediaType.toString());

    HttpRequest httpRequest = buildHttpRequest(requestUri, RequestMethod.PUT,
        responseMediaType, createContent(content, responseMediaType));
    HttpResponse<String> response = baseHttpClient
        .send(httpRequest, HttpResponse.BodyHandlers.ofString());

    // We check for specific status
    ensureStatusCodesMatch(response, expectedStatus);
    return response;
  }

  /**
   * Execute a web service patch.
   * @param requestUri The request uri
   * @param responseMediaType The type of media being requested
   * @param content The patch content
   * @param type the type of java class to be returned
   * @param expectSuccess Assert a success code was returned
   * @param <T> The expected response type
   * @return The response deserialized
   * @throws IOException if the exception is thrown
   * @throws InterruptedException if the exception is thrown
   */
  public <T> T patch(String requestUri, MediaType responseMediaType, Object content, Type type,
      boolean expectSuccess) throws IOException, InterruptedException {
    HttpResponse<String> response = this.patchContent(requestUri, responseMediaType, content, expectSuccess);
    return WebServiceUtilities.deserializeResponse(response, responseMediaType, type);
  }

  /**
   * Execute a web service patch.
   * @param requestUri The request uri
   * @param expectedMediaType The type of media being requested
   * @param content The put content
   * @param type the type of java class to be returned
   * @param expectedStatus Assert a specific status code was returned
   * @param <T> The expected response type
   * @return The response deserialized
   * @throws IOException if the exception is thrown
   * @throws InterruptedException if the exception is thrown
   */
  public <T> T patch(String requestUri, MediaType expectedMediaType, Object content, Type type,
      HttpStatus expectedStatus) throws IOException, InterruptedException {
    HttpResponse<String> response = this.patchContent(requestUri, expectedMediaType, content, expectedStatus);
    return WebServiceUtilities.deserializeResponse(response, expectedMediaType, type);
  }

  /**
   * Execute a web service patch.
   * @param requestUri The request uri
   * @param responseMediaType The type of media being requested
   * @param content The put content
   * @param expectSuccess Assert a success code was returned
   * @return The response body as a string
   * @throws IOException if the exception is thrown
   * @throws InterruptedException if the exception is thrown
   */
  public HttpResponse<String> patch(String requestUri, MediaType responseMediaType, Object content,
      boolean expectSuccess) throws IOException, InterruptedException {
    return this.patchContent(requestUri, responseMediaType, content, expectSuccess);
  }

  /**
   * Execute a web service patch.
   * @param requestUri The request uri
   * @param responseMediaType The type of media being requested
   * @param content The patch content
   * @param expectedStatus Assert a specific status code was returned
   * @return The response body as a string
   * @throws IOException if the exception is thrown
   * @throws InterruptedException if the exception is thrown
   */
  public HttpResponse<String> patch(String requestUri, MediaType responseMediaType, Object content,
      HttpStatus expectedStatus) throws IOException, InterruptedException {
    return this.patchContent(requestUri, responseMediaType, content, expectedStatus);
  }

  /**
   * Do a web service put for the given uri, content and media type.
   * @param requestUri The request uri
   * @param responseMediaType The response media type
   * @param content The put body
   * @param expectedStatus Assert a specific status code was returned
   * @return A http response message
   * @throws IOException if the exception is thrown
   * @throws InterruptedException if the exception is thrown
   */
  protected HttpResponse<String> patchContent(String requestUri, MediaType responseMediaType,
      Object content, HttpStatus expectedStatus) throws IOException, InterruptedException {
    this.checkIfMediaTypeNotPresent(responseMediaType.toString());

    HttpRequest httpRequest = buildHttpRequest(requestUri, RequestMethod.PATCH,
        responseMediaType, createContent(content, responseMediaType));
    HttpResponse<String> response = baseHttpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

    ensureStatusCodesMatch(response, expectedStatus);
    return response;
  }

  /**
   * Do a web service put for the given uri, content and media type.
   * @param requestUri The request uri
   * @param responseMediaType The response media type
   * @param content The put body
   * @param expectSuccess Assert a success code was returned
   * @return A http response message
   * @throws IOException if the exception is thrown
   * @throws InterruptedException if the exception is thrown
   */
  protected HttpResponse<String> patchContent(String requestUri, MediaType responseMediaType,
      Object content, boolean expectSuccess) throws IOException, InterruptedException {
    this.checkIfMediaTypeNotPresent(responseMediaType.toString());

    HttpRequest httpRequest = buildHttpRequest(requestUri, RequestMethod.PATCH, responseMediaType,
        createContent(content, responseMediaType));
    HttpResponse<String> response = baseHttpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

    if (expectSuccess) {
      ensureSuccessStatusCode(response);
    }
    return response;
  }

  /**
   * Execute a web service delete.
   * @param requestUri The request uri
   * @param expectedMediaType The type of media being requested
   * @param type the request type being done
   * @param expectSuccess Assert a success code was returned
   * @param <T> The expected response type
   * @return The response by deserialize as the T
   * @throws IOException if an exception is thrown
   * @throws InterruptedException if an exception is thrown
   */
  public <T> T delete(String requestUri, MediaType expectedMediaType, Type type, boolean expectSuccess)
      throws IOException, InterruptedException {
    HttpResponse<String> response = this.deleteContent(requestUri, expectedMediaType, expectSuccess);
    return WebServiceUtilities.deserializeResponse(response, expectedMediaType, type);
  }

  /**
   * Execute a web service delete.
   * @param requestUri The request uri
   * @param expectedMediaType The type of media being requested
   * @param expectedStatus Assert a specific status code was returned
   * @param type the request type being done
   * @param <T> The expected response type
   * @return The response by deserialize as the T
   * @throws IOException if an exception is thrown
   * @throws InterruptedException if an exception is thrown
   */
  public <T> T delete(String requestUri, MediaType expectedMediaType, Type type, HttpStatus expectedStatus)
      throws IOException, InterruptedException {
    HttpResponse<String> response = this.deleteContent(requestUri, expectedMediaType, expectedStatus);
    return WebServiceUtilities.deserializeResponse(response, expectedMediaType, type);
  }

  /**
   * Execute a web service delete.
   * @param requestUri The request uri
   * @param expectedMediaType The type of media being requested
   * @param expectSuccess Assert a success code was returned
   * @return The response as a string
   * @throws IOException if an exception is thrown
   * @throws InterruptedException if an exception is thrown
   */
  public HttpResponse<String> delete(String requestUri, MediaType expectedMediaType, boolean expectSuccess)
      throws IOException, InterruptedException {
    return this.deleteContent(requestUri, expectedMediaType, expectSuccess);
  }

  /**
   * Execute a web service delete.
   * @param requestUri The request uri
   * @param expectedMediaType The type of media being requested
   * @param expectedStatus Assert a specific status code was returned
   * @return The response as a string
   * @throws IOException if an exception is thrown
   * @throws InterruptedException if an exception is thrown
   */
  public HttpResponse<String> delete(String requestUri, MediaType expectedMediaType, HttpStatus expectedStatus)
      throws IOException, InterruptedException {
    return deleteContent(requestUri, expectedMediaType, expectedStatus);
  }

  /**
   * Do a web service delete for the given uri.
   * @param requestUri The request uri
   * @param returnMediaType The expected response media type
   * @param expectSuccess Assert a success code was returned
   * @return A http response message
   * @throws IOException if an exception is thrown
   * @throws InterruptedException if an exception is thrown
   */
  protected HttpResponse<String> deleteContent(String requestUri, MediaType returnMediaType,
      boolean expectSuccess) throws IOException, InterruptedException {
    HttpRequest httpRequest = buildHttpRequest(requestUri, RequestMethod.DELETE, returnMediaType);
    HttpResponse<String> response = baseHttpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

    // Should we check for success
    if (expectSuccess) {
      ensureSuccessStatusCode(response);
    }
    return response;
  }

  /**
   * Do a web service delete for the given uri.
   * @param requestUri The request uri
   * @param returnMediaType The expected response media type
   * @param expectedStatus Assert a specific status code was returned
   * @return A http response message
   * @throws IOException if an exception is thrown
   * @throws InterruptedException if an exception is thrown
   */
  protected HttpResponse<String> deleteContent(String requestUri, MediaType returnMediaType,
      HttpStatus expectedStatus) throws IOException, InterruptedException {
    HttpRequest httpRequest = buildHttpRequest(requestUri, RequestMethod.DELETE, returnMediaType);
    HttpResponse<String> response = baseHttpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

    // We check for specific status
    ensureStatusCodesMatch(response, expectedStatus);
    return response;
  }

  /**
   * Create http content.
   * @param content The content as a string
   * @param responseMediaType The type of the media being posted
   * @return The content as String
   * @throws IOException if the exception is thrown
   */
  private static String createContent(Object content, MediaType responseMediaType) throws IOException {
    return content instanceof String ? content.toString()
        : WebServiceUtilities.createStringEntity(content, responseMediaType);
  }

  /**
   * Build the Http Request.
   * @param requestUri The Request URI
   * @param requestMethod The Request Type
   * @param mediaType The Media Type
   * @return The Http Request
   */
  protected HttpRequest buildHttpRequest(String requestUri, RequestMethod requestMethod, MediaType mediaType) {
    return buildHttpRequest(requestUri, requestMethod, mediaType, null, new HashMap<>());
  }

  /**
   * Build the Http Request.
   * @param requestUri The Request URI
   * @param requestMethod The Request Type
   * @param mediaType The Media Type
   * @param content The Http Request Content
   * @return The Http Request
   */
  protected HttpRequest buildHttpRequest(String requestUri, RequestMethod requestMethod,
      MediaType mediaType, String content) {
    return buildHttpRequest(requestUri, requestMethod, mediaType, content, new HashMap<>());
  }

  /**
   * Build the Http Request.
   * @param requestUri The Request URI
   * @param requestMethod The Request Type
   * @param mediaType The Media Type
   * @param additionalHeaders The Additional Headers for the Http Request
   * @return The Http Request
   */
  protected HttpRequest buildHttpRequest(String requestUri, RequestMethod requestMethod,
      MediaType mediaType, Map<String, String> additionalHeaders) {
    return buildHttpRequest(requestUri, requestMethod, mediaType, null, additionalHeaders);
  }

  /**
   * Build the Http Request.
   * @param requestUri The Request URI
   * @param requestMethod The Request Type
   * @param mediaType The Media Type
   * @param content The Http Request Content
   * @param additionalHeaders The Additional Headers for the Http Request
   * @return The Http Request
   */
  protected HttpRequest buildHttpRequest(String requestUri, RequestMethod requestMethod,
      MediaType mediaType, String content, Map<String, String> additionalHeaders) {
    HttpRequest.Builder builder = this.baseHttpRequestBuilder.copy();

    builder
      .header("Content-Type", mediaType.toString())
        .uri(URI.create(requestUri));

    for (Map.Entry<String, String> header : additionalHeaders.entrySet()) {
      builder.header(header.getKey(), header.getValue());
    }

    if (requestMethod.equals(RequestMethod.POST)) {
      return builder.POST(HttpRequest.BodyPublishers.ofString(content)).build();
    } else if (requestMethod.equals(RequestMethod.PUT)) {
      return builder.PUT(HttpRequest.BodyPublishers.ofString(content)).build();
    } else if (requestMethod.equals(RequestMethod.DELETE)) {
      return builder.DELETE().build();
    } else if (requestMethod.equals(RequestMethod.PATCH)) {
      return builder.method("PATCH", HttpRequest.BodyPublishers.ofString(content)).build();
    }
    return builder.GET().build();
  }

  /**
   * Ensure the HTTP response was successful, if not throw a user-friendly error message.
   * @param response The HTTP response
   */
  private static void ensureSuccessStatusCode(HttpResponse<String> response) {
    // Make sure a response was returned
    if (response == null) {
      throw new NullPointerException(HttpStatus.NO_CONTENT + " Response was null");
    }

    // Check if it was a success and if not create a user-friendly error message
    if (response.statusCode() != HttpStatus.OK.value()) {
      throw new NotAcceptableStatusException(
          String.format("Response did not indicate a success. %s Response code was: %d",
              System.lineSeparator(), response.statusCode()));
    }
  }

  /**
   * Ensure the HTTP response has specified status, if not throw a user-friendly error message.
   * @param response The HTTP response
   * @param expectedStatus Assert a specific status code was returned
   */
  private static void ensureStatusCodesMatch(HttpResponse<String> response, HttpStatus expectedStatus) {
    // Make sure a response was returned
    if (response == null) {
      throw new NullPointerException(HttpStatus.NO_CONTENT + " Response was null");
    }

    // Check if it was a success and if not create a user-friendly error message
    if (response.statusCode() != expectedStatus.value()) {
      String body = response.body();
      throw new NotAcceptableStatusException(String.format("Response status did not match expected. %s "
                  + "Response code was: %d %s Expected code was: %d %s"
                  + "Body: %s", System.lineSeparator(), response.statusCode(),
              System.lineSeparator(), expectedStatus.hashCode(), System.lineSeparator(), body));
    }
  }

  /**
   * Check if the media type is supported.
   * @param mediaType Media type to add
   */
  private void checkIfMediaTypeNotPresent(String mediaType) {
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