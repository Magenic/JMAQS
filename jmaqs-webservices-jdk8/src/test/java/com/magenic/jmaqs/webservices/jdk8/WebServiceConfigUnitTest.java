
/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.webservices.jdk8;

import com.magenic.jmaqs.base.BaseGenericTest;
import com.magenic.jmaqs.utilities.helper.TestCategories;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Unit tests for the Web Service Config functionality.
 */
public class WebServiceConfigUnitTest extends BaseGenericTest {
  /**
   * gets if proxy can be used.
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void getWebServiceUri() {
    Assert.assertEquals(WebServiceConfig.getWebServiceUri(),
        "http://magenicautomation.azurewebsites.net", "the web service URI does not match");
  }

  /**
   * gets if proxy can be used.
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void getWebServiceTimeOut() {
    Assert.assertEquals(WebServiceConfig.getWebServiceTimeOut(), 10000,
        "Use proxy did not come back false");
  }

  /**
   * gets if proxy can be used.
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void getUseProxy() {
    Assert.assertFalse(WebServiceConfig.getUseProxy(), "Use proxy did not come back false");
  }

  /**
   * gets the proxy address.
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void getProxyAddress() {
    Assert.assertEquals(WebServiceConfig.getProxyAddress(), "127.0.0.1:8001",
        "Proxy address is not the same");
  }
}
