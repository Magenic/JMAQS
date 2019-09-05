/*
 * Copyright 2019 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.webservices;

import com.magenic.jmaqs.webservices.HttpClientWrapper;
import com.magenic.jmaqs.webservices.WebServiceUtils;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test with the web service wrapper directly.
 */
public class WebServiceWithWrapperTest {
  /**
   * Verifies that basic GET features work with the HttpClientWrapper.
   * 
   * @throws Exception
   *           Web service get failed
   */
  @Test
  public void webServiceGetVerificationTest() throws Exception {
    HttpClientWrapper client = new HttpClientWrapper("http://magenicautomation.azurewebsites.net");
    CloseableHttpResponse response = client.getContent("/api/String/1", ContentType.TEXT_PLAIN,
        true);
    String responseString = WebServiceUtils.getResponseBody(response);

    Assert.assertTrue(responseString.contains("Tomato Soup"),
        "Was expecting a result with Tomato Soup but instead got - " + response.toString());
  }
}
