/*
 * Copyright 2019 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.webservices.jdk8;

import com.beust.jcommander.internal.Lists;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;

/**
 * The Web Service Driver.
 */
public class WebServiceDriver {
  /**
   * The base HTTP client control.
   */
  private CloseableHttpClient baseHttpClient;

  /**
   * The URI to be stored for the Web Service.
   */
  private URI baseAddress;

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
  public WebServiceDriver(CloseableHttpClient newHttpClient) {
    this.baseHttpClient = newHttpClient;
  }

  /**
   * Set the HTTP client.
   * 
   * @param httpClient
   *          The HTTP client
   */
  public void setHttpClient(CloseableHttpClient httpClient) {
    this.baseHttpClient = httpClient;
  }

  /**
   * Get the HTTP client.
   * 
   * @return the HttpClient to interact with
   */
  public CloseableHttpClient getHttpClient(String mediaType) {
    if (this.baseHttpClient == null) {
      Header header = new BasicHeader(HttpHeaders.CONTENT_TYPE, mediaType);
      this.baseHttpClient = HttpClientBuilder.create().setDefaultHeaders(Lists.newArrayList(header))
          .setDefaultRequestConfig(getRequestTimeouts()).build();
    }

    return this.baseHttpClient;
  }

  /**
   * Get the base URI.
   * 
   * @return A URI containing the Web Service Address.
   */
  public URI getBaseWebServiceAddress() {
    return this.baseAddress;
  }

  /**
   * Sets the base Web Service address for the Web Service Driver.
   * 
   * @param address
   *          The string to set the URI to
   * @throws URISyntaxException
   *           thrown when the string can't be parsed into a URI object
   */
  public void setBaseWebServiceAddress(String address) throws URISyntaxException {
    this.baseAddress = new URI(address);
  }

  /**
   * Do a web service get.
   * 
   * @param requestUri
   *          The request URI
   * @param returnMediaType
   *          The return media type
   * @param expectSuccess
   *          Check to make sure a success code was returned, if not throw an exception
   * @return An HTTP response
   * @throws Exception
   *           A web service call exception
   */
  public CloseableHttpResponse getContent(String requestUri, ContentType returnMediaType,
      boolean expectSuccess) throws Exception {
    return getContent(requestUri, returnMediaType.toString(), expectSuccess);
  }

  /**
   * Do a web service get.
   * 
   * @param requestUri
   *          The request URI
   * @param returnMediaType
   *          The return media type
   * @param expectSuccess
   *          Check to make sure a success code was returned, if not throw an exception
   * @return An HTTP response
   * @throws Exception
   *           A web service call exception
   */
  public CloseableHttpResponse getContent(String requestUri, String returnMediaType,
      boolean expectSuccess) throws Exception {
    HttpGet newGet = new HttpGet(new URIBuilder(this.baseAddress).setPath(requestUri).build());

    return executeRequest(newGet, returnMediaType, expectSuccess);
  }

  /**
   * Do a web service put.
   * 
   * @param requestUri
   *          The request URI
   * @param content
   *          Put content
   * @param returnMediaType
   *          The return media type
   * @param expectSuccess
   *          Check to make sure a success code was returned, if not throw an exception
   * @return An HTTP response
   * @throws Exception
   *           A web service call exception
   */
  public CloseableHttpResponse putContent(String requestUri, HttpEntity content,
      ContentType returnMediaType, boolean expectSuccess) throws Exception {
    return this.putContent(requestUri, content, returnMediaType.toString(), expectSuccess);
  }

  /**
   * Do a web service put.
   * 
   * @param requestUri
   *          The request URI
   * @param content
   *          Put content
   * @param returnMediaType
   *          The return media type
   * @param expectSuccess
   *          Check to make sure a success code was returned, if not throw an exception
   * @return An HTTP response
   * @throws Exception
   *           A web service call exception
   */
  public CloseableHttpResponse putContent(String requestUri, HttpEntity content,
      String returnMediaType, boolean expectSuccess) throws Exception {
    HttpPut newPut = new HttpPut(new URIBuilder(this.baseAddress).setPath(requestUri).build());
    newPut.setEntity(content);
    return executeRequest(newPut, returnMediaType, expectSuccess);
  }

  /**
   * Do a web service patch.
   * 
   * @param requestUri
   *          The request URI
   * @param content
   *          Patch content
   * @param returnMediaType
   *          The return media type
   * @param expectSuccess
   *          Check to make sure a success code was returned, if not throw an exception
   * @return An HTTP response
   * @throws Exception
   *           A web service call exception
   */
  public CloseableHttpResponse patchContent(String requestUri, HttpEntity content,
      ContentType returnMediaType, boolean expectSuccess) throws Exception {
    return this.patchContent(requestUri, content, returnMediaType.toString(), expectSuccess);
  }

  /**
   * Do a web service patch.
   * 
   * @param requestUri
   *          The request URI
   * @param content
   *          Patch content
   * @param returnMediaType
   *          The return media type
   * @param expectSuccess
   *          Check to make sure a success code was returned, if not throw an exception
   * @return An HTTP response
   * @throws Exception
   *           A web service call exception
   */
  public CloseableHttpResponse patchContent(String requestUri, HttpEntity content,
      String returnMediaType, boolean expectSuccess) throws Exception {
    HttpPatch newPut = new HttpPatch(new URIBuilder(this.baseAddress).setPath(requestUri).build());
    newPut.setEntity(content);
    return executeRequest(newPut, returnMediaType, expectSuccess);
  }

  /**
   * Do a web service post.
   * 
   * @param requestUri
   *          The request URI
   * @param content
   *          Post content
   * @param returnMediaType
   *          The return media type
   * @param expectSuccess
   *          Check to make sure a success code was returned, if not throw an exception
   * @return An HTTP response
   * @throws Exception
   *           A web service call exception
   */
  public CloseableHttpResponse postContent(String requestUri, HttpEntity content,
      ContentType returnMediaType, boolean expectSuccess) throws Exception {
    return this.postContent(requestUri, content, returnMediaType.toString(), expectSuccess);
  }

  /**
   * Do a web service post.
   * 
   * @param requestUri
   *          The request URI
   * @param content
   *          Post content
   * @param returnMediaType
   *          The return media type
   * @param expectSuccess
   *          Check to make sure a success code was returned, if not throw an exception
   * @return An HTTP response
   * @throws Exception
   *           A web service call exception
   */
  public CloseableHttpResponse postContent(String requestUri, HttpEntity content,
      String returnMediaType, boolean expectSuccess) throws Exception {
    HttpPost newPost = new HttpPost(new URIBuilder(this.baseAddress).setPath(requestUri).build());
    newPost.setEntity(content);

    return executeRequest(newPost, returnMediaType, expectSuccess);
  }

  /**
   * Do a web service delete.
   * 
   * @param requestUri
   *          The request URI
   * @param returnMediaType
   *          The return media type
   * @param expectSuccess
   *          Check to make sure a success code was returned, if not throw an exception
   * @return An HTTP response
   * @throws Exception
   *           A web service call exception
   */
  public CloseableHttpResponse deleteContent(String requestUri, ContentType returnMediaType,
      boolean expectSuccess) throws Exception {
    return this.deleteContent(requestUri, returnMediaType.toString(), expectSuccess);
  }

  /**
   * Do a web service delete.
   * 
   * @param requestUri
   *          The request URI
   * @param returnMediaType
   *          The return media type
   * @param expectSuccess
   *          Check to make sure a success code was returned, if not throw an exception
   * @return An HTTP response
   * @throws Exception
   *           A web service call exception
   */
  public CloseableHttpResponse deleteContent(String requestUri, String returnMediaType,
      boolean expectSuccess) throws Exception {
    HttpDelete newDelete = new HttpDelete(
        new URIBuilder(this.baseAddress).setPath(requestUri).build());

    return executeRequest(newDelete, returnMediaType, expectSuccess);
  }

  /**
   * Execute a web service call.
   * 
   * @param request
   *          The web service call to execute
   * @param returnMediaType
   *          The return media type
   * @param expectSuccess
   *          Check to make sure a success code was returned, if not throw an exception
   * @return An HTTP response
   * @throws Exception
   *           A web service call exception
   */
  private CloseableHttpResponse executeRequest(HttpUriRequest request, String returnMediaType,
      boolean expectSuccess) throws Exception {

    CloseableHttpResponse response = this.getHttpClient(returnMediaType).execute(request);

    // Should we check for success
    if (expectSuccess) {
      ensureSuccessStatusCode(response);
    }

    return response;
  }

  /**
   * Ensure the response returned a success code.
   *
   * @param response
   *          And HTTP response
   * @throws Exception If the response was null or returned with an error code
   */
  private static void ensureSuccessStatusCode(final HttpResponse response)
          throws Exception {
    // Make sure a response was returned
    if (response == null) {
      throw new NullPointerException("Response was null");
    }

    // Check if it was a success and if not create a user friendly error
    // message
    if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
      String body = response.getStatusLine().toString();

      throw new Exception(
          String.format("Response did not indicate a success. %s Response code was: %s ",
              System.lineSeparator(), body));
    }

  }

  /**
   * Get the request configuration.
   * 
   * @return The request configuration
   */
  private RequestConfig getRequestTimeouts() {

    return RequestConfig.copy(RequestConfig.DEFAULT)
        .setConnectionRequestTimeout(WebServiceConfig.getWebServiceTimeOut() * 1000)
        .setConnectTimeout(WebServiceConfig.getWebServiceTimeOut() * 1000).build();
  }
}
