/*
 * Copyright 2020 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.webservices.jdk8;

import com.beust.jcommander.internal.Lists;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;
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
 * The type Web service driver.
 */
public class WebServiceDriver {
  private CloseableHttpClient baseHttpClient;

  private URI baseAddress;

  /**
   * Instantiates a new Web service driver.
   *
   * @param baseAddress the base address
   * @throws URISyntaxException the uri syntax exception
   */
  public WebServiceDriver(String baseAddress) throws URISyntaxException {
    this(new URI(baseAddress));
  }

  /**
   * Instantiates a new Web service driver.
   *
   * @param baseAddress the base address
   */
  public WebServiceDriver(URI baseAddress) {
    this.baseAddress = baseAddress;
  }

  /**
   * Instantiates a new Web service driver.
   *
   * @param newHttpClient the new http client
   */
  public WebServiceDriver(CloseableHttpClient newHttpClient) {
    this.baseHttpClient = newHttpClient;
  }

  /**
   * Sets http client.
   *
   * @param httpClient the http client
   */
  public void setHttpClient(CloseableHttpClient httpClient) {
    this.baseHttpClient = httpClient;
  }

  /**
   * Gets http client.
   *
   * @param mediaType the media type
   * @return the http client
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
   * Gets base web service address.
   *
   * @return the base web service address
   */
  public URI getBaseWebServiceAddress() {
    return this.baseAddress;
  }

  /**
   * Sets base web service address.
   *
   * @param address the address
   * @throws URISyntaxException the uri syntax exception
   */
  public void setBaseWebServiceAddress(String address) throws URISyntaxException {
    this.baseAddress = new URI(address);
  }

  /**
   * Gets content.
   *
   * @param requestUri      the request uri
   * @param returnMediaType the return media type
   * @param expectSuccess   the expect success
   * @return the content
   * @throws IOException        the io exception
   * @throws URISyntaxException the uri syntax exception
   */
  public CloseableHttpResponse getContent(String requestUri, ContentType returnMediaType,
      boolean expectSuccess) throws IOException, URISyntaxException {
    return getContent(requestUri, returnMediaType.toString(), expectSuccess);
  }

  /**
   * Gets content.
   *
   * @param requestUri      the request uri
   * @param returnMediaType the return media type
   * @param expectSuccess   the expect success
   * @return the content
   * @throws IOException        the io exception
   * @throws URISyntaxException the uri syntax exception
   */
  public CloseableHttpResponse getContent(String requestUri, String returnMediaType,
      boolean expectSuccess) throws IOException, URISyntaxException {
    HttpGet newGet = new HttpGet(new URIBuilder(this.baseAddress).setPath(requestUri).build());

    return executeRequest(newGet, returnMediaType, expectSuccess);
  }

  /**
   * Put content closeable http response.
   *
   * @param requestUri      the request uri
   * @param content         the content
   * @param returnMediaType the return media type
   * @param expectSuccess   the expect success
   * @return the closeable http response
   * @throws IOException        the io exception
   * @throws URISyntaxException the uri syntax exception
   */
  public CloseableHttpResponse putContent(String requestUri, HttpEntity content,
      ContentType returnMediaType, boolean expectSuccess) throws IOException, URISyntaxException {
    return this.putContent(requestUri, content, returnMediaType.toString(), expectSuccess);
  }

  /**
   * Put content closeable http response.
   *
   * @param requestUri      the request uri
   * @param content         the content
   * @param returnMediaType the return media type
   * @param expectSuccess   the expect success
   * @return the closeable http response
   * @throws IOException        the io exception
   * @throws URISyntaxException the uri syntax exception
   */
  public CloseableHttpResponse putContent(String requestUri, HttpEntity content,
      String returnMediaType, boolean expectSuccess) throws IOException, URISyntaxException {
    HttpPut newPut = new HttpPut(new URIBuilder(this.baseAddress).setPath(requestUri).build());
    newPut.setEntity(content);
    return executeRequest(newPut, returnMediaType, expectSuccess);
  }

  /**
   * Patch content closeable http response.
   *
   * @param requestUri      the request uri
   * @param content         the content
   * @param returnMediaType the return media type
   * @param expectSuccess   the expect success
   * @return the closeable http response
   * @throws IOException        the io exception
   * @throws URISyntaxException the uri syntax exception
   */
  public CloseableHttpResponse patchContent(String requestUri, HttpEntity content,
      ContentType returnMediaType, boolean expectSuccess) throws IOException, URISyntaxException {
    return this.patchContent(requestUri, content, returnMediaType.toString(), expectSuccess);
  }

  /**
   * Patch content closeable http response.
   *
   * @param requestUri      the request uri
   * @param content         the content
   * @param returnMediaType the return media type
   * @param expectSuccess   the expect success
   * @return the closeable http response
   * @throws IOException        the io exception
   * @throws URISyntaxException the uri syntax exception
   */
  public CloseableHttpResponse patchContent(String requestUri, HttpEntity content,
      String returnMediaType, boolean expectSuccess) throws IOException, URISyntaxException {
    HttpPatch newPut = new HttpPatch(new URIBuilder(this.baseAddress).setPath(requestUri).build());
    newPut.setEntity(content);
    return executeRequest(newPut, returnMediaType, expectSuccess);
  }

  /**
   * Post content closeable http response.
   *
   * @param requestUri      the request uri
   * @param content         the content
   * @param returnMediaType the return media type
   * @param expectSuccess   the expect success
   * @return the closeable http response
   * @throws IOException        the io exception
   * @throws URISyntaxException the uri syntax exception
   */
  public CloseableHttpResponse postContent(String requestUri, HttpEntity content,
      ContentType returnMediaType, boolean expectSuccess) throws IOException, URISyntaxException {
    return this.postContent(requestUri, content, returnMediaType.toString(), expectSuccess);
  }

  /**
   * Post content closeable http response.
   *
   * @param requestUri      the request uri
   * @param content         the content
   * @param returnMediaType the return media type
   * @param expectSuccess   the expect success
   * @return the closeable http response
   * @throws IOException        the io exception
   * @throws URISyntaxException the uri syntax exception
   */
  public CloseableHttpResponse postContent(String requestUri, HttpEntity content,
      String returnMediaType, boolean expectSuccess) throws IOException, URISyntaxException {
    HttpPost newPost = new HttpPost(new URIBuilder(this.baseAddress).setPath(requestUri).build());
    newPost.setEntity(content);

    return executeRequest(newPost, returnMediaType, expectSuccess);
  }

  /**
   * Delete content closeable http response.
   *
   * @param requestUri      the request uri
   * @param returnMediaType the return media type
   * @param expectSuccess   the expect success
   * @return the closeable http response
   * @throws IOException        the io exception
   * @throws URISyntaxException the uri syntax exception
   */
  public CloseableHttpResponse deleteContent(String requestUri, ContentType returnMediaType,
      boolean expectSuccess) throws IOException, URISyntaxException {
    return this.deleteContent(requestUri, returnMediaType.toString(), expectSuccess);
  }

  /**
   * Delete content closeable http response.
   *
   * @param requestUri      the request uri
   * @param returnMediaType the return media type
   * @param expectSuccess   the expect success
   * @return the closeable http response
   * @throws IOException        the io exception
   * @throws URISyntaxException the uri syntax exception
   */
  public CloseableHttpResponse deleteContent(String requestUri, String returnMediaType,
      boolean expectSuccess) throws IOException, URISyntaxException {
    HttpDelete newDelete = new HttpDelete(
        new URIBuilder(this.baseAddress).setPath(requestUri).build());

    return executeRequest(newDelete, returnMediaType, expectSuccess);
  }

  private CloseableHttpResponse executeRequest(HttpUriRequest request, String returnMediaType,
      boolean expectSuccess) throws IOException {

    CloseableHttpResponse response = this.getHttpClient(returnMediaType).execute(request);

    // Should we check for success
    if (expectSuccess) {
      ensureSuccessStatusCode(response);
    }

    return response;
  }

  private static void ensureSuccessStatusCode(final HttpResponse response)
      throws HttpResponseException {
    // Make sure a response was returned
    if (response == null) {
      throw new NullPointerException("Response was null");
    }

    // Check if it was a success and if not create a user friendly error
    // message
    if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
      String body = response.getStatusLine().toString();

      throw new HttpResponseException(response.getStatusLine().getStatusCode(), String
          .format("Response did not indicate a success. %s Response code was: %s ",
              System.lineSeparator(), body));
    }

  }

  private RequestConfig getRequestTimeouts() {

    return RequestConfig.copy(RequestConfig.DEFAULT)
        .setConnectionRequestTimeout(WebServiceConfig.getWebServiceTimeOut() * 1000)
        .setConnectTimeout(WebServiceConfig.getWebServiceTimeOut() * 1000).build();
  }
}
