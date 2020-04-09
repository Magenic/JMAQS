/*
 * Copyright 2020 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.webservices.jdk11;

import com.magenic.jmaqs.utilities.helper.TestCategories;
import com.magenic.jmaqs.webservices.jdk8.MediaType;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;

import com.magenic.jmaqs.webservices.jdk8.WebServiceConfig;
import org.testng.Assert;
import org.testng.annotations.Test;

public class WebServiceDriverUnitTest {
  /**
   * Verifies that basic GET features work with the WebServiceDriver.
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void setHttpClient() throws URISyntaxException {
    WebServiceDriver webServiceDriver1 = new WebServiceDriver(
        "http://magenicautomation.azurewebsites.net");
    webServiceDriver1.setHttpClient(webServiceDriver1.getHttpClient(MediaType.APP_JSON));
    WebServiceDriver webServiceDriver2 = new WebServiceDriver(
        webServiceDriver1.getHttpClient(MediaType.APP_XML));
    Assert.assertNotNull(webServiceDriver1.getHttpClient(MediaType.APP_JSON),
        "Driver 1 is null");
    Assert.assertNotNull(webServiceDriver2, "Driver 2 is null");
  }

  /**
   * Verifies that basic GET features work with the WebServiceDriver.
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void setDefaultHttpClient() throws URISyntaxException {
    HttpRequest request = HttpRequest.newBuilder(new URI(WebServiceConfig.getWebServiceUri())).build();
    WebServiceDriver webServiceDriver1 = new WebServiceDriver(request);
    webServiceDriver1.setHttpClient(webServiceDriver1.getHttpClient(MediaType.APP_JSON));
    WebServiceDriver webServiceDriver2 = new WebServiceDriver(
        webServiceDriver1.getHttpClient(MediaType.APP_XML));
    Assert.assertNotNull(webServiceDriver1.getHttpClient(MediaType.APP_JSON),"Driver 1 is null");
    Assert.assertNotNull(webServiceDriver2, "Driver 2 is null");
  }
}
