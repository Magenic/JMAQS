/*
 * Copyright 2019 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.webservices.jdk11;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;

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
}