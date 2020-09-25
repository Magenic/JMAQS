package com.magenic.jmaqs.webservices.jdk11;

import com.magenic.jmaqs.utilities.helper.TestCategories;
import com.magenic.jmaqs.webservices.jdk8.WebServiceConfig;
import org.testng.Assert;
import org.testng.annotations.Test;

public class WebServiceUtilitiesUnitTest {

  /**
   * Verifies that getting the Use Proxy works properly
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void getUseProxyTest() {
    // Assert false as config is set to false
    Assert.assertFalse(WebServiceConfig.getUseProxy());
  }

  /**
   * Verifies that basic setting the Http Request Builder works
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void getProxyPortTest() {
    Assert.assertEquals(WebServiceConfig.getProxyPort(), 8080);
  }

  /**
   * Verifies that basic setting the Http Request Builder works
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void getProxyAddressTest() {
    Assert.assertEquals(WebServiceConfig.getProxyAddress(), "127.0.0.1:8001");
  }
}
