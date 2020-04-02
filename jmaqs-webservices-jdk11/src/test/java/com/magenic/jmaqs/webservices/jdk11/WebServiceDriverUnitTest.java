/*
 * Copyright 2020 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.webservices.jdk11;

import com.magenic.jmaqs.utilities.helper.TestCategories;
import java.net.http.HttpRequest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class WebServiceDriverUnitTest {
  /**
   * Verifies that basic GET features work with the WebServiceDriver.
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void setHttpClient() {
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
   * Tests the setting of the http request.
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void setHttpRequest() {
    WebServiceDriver webServiceDriver = new WebServiceDriver(
        "http://magenicautomation.azurewebsites.net");
    Assert.assertNotNull(webServiceDriver.getHttpRequest());
  }

  /**
   * Tests the setting of default Http Client and request.
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void getDefaultHttpClientAndRequest() {
    WebServiceDriver webServiceDriver = new WebServiceDriver();
    HttpRequest request = webServiceDriver.getHttpRequest();
    Assert.assertNotNull(webServiceDriver.getHttpRequest());
  }
}
