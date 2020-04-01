package com.magenic.jmaqs.webservices.jdk11;

import com.magenic.jmaqs.utilities.helper.TestCategories;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;

import org.testng.Assert;
import org.testng.annotations.Test;

public class WebServiceDriverUnitTest {
  /**
   * Verifies that basic GET features work with the WebServiceDriver.
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void setHttpClient() throws URISyntaxException, IOException {
    WebServiceDriver webServiceDriver1 = new WebServiceDriver(
        "http://magenicautomation.azurewebsites.net");
    webServiceDriver1.setHttpClient(webServiceDriver1.getHttpClient(MediaType.APP_JSON));
    WebServiceDriver webServiceDriver2 = new WebServiceDriver(
        webServiceDriver1.getHttpClient(MediaType.APP_XML));
    Assert.assertNotNull(webServiceDriver1.getHttpClient(MediaType.APP_JSON),
        "Driver 1 is null");
    Assert.assertNotNull(webServiceDriver2, "Driver 2 is null");
  }

  @Test(groups = TestCategories.WEB_SERVICE)
  public void setHttpRequest() {
    WebServiceDriver webServiceDriver = new WebServiceDriver(
        "http://magenicautomation.azurewebsites.net");
    Assert.assertNotNull(webServiceDriver.getHttpRequest());
  }
}
