package com.magenic.jmaqs.webservices.jdk11;

import com.magenic.jmaqs.utilities.helper.TestCategories;
import com.magenic.jmaqs.webservices.jdk8.MediaType;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;

import org.testng.Assert;
import org.testng.annotations.Test;

public class WebServiceDriverUnitTest {

  /**
   * Verifies that basic setting the Http Client works
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void setHttpClient() throws URISyntaxException, IOException {
    WebServiceDriver webServiceDriver1 = new WebServiceDriver(
        HttpClientFactory.getDefaultClient());
    webServiceDriver1.setHttpClient(webServiceDriver1.getHttpClient(MediaType.APP_JSON.toString()));
    WebServiceDriver webServiceDriver2 = new WebServiceDriver(
        webServiceDriver1.getHttpClient(MediaType.APP_XML.toString()));
    Assert.assertNotNull(webServiceDriver1.getHttpClient(MediaType.APP_JSON.toString()),
        "Driver 1 is null");
    Assert.assertNotNull(webServiceDriver2, "Driver 2 is null");
  }

  /**
   * Verifies that basic setting the Http Request Builder works
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void setHttpRequest() throws IOException, URISyntaxException {
    WebServiceDriver webServiceDriver = new WebServiceDriver(
        HttpClientFactory.getDefaultClient());
    webServiceDriver.setHttpRequestBuilder(HttpRequest.newBuilder());
    Assert.assertNotNull(webServiceDriver.getHttpRequestBuilder());
  }
}
