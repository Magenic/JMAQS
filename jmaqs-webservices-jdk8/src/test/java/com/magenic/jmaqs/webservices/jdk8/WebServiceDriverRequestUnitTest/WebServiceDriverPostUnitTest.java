/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.webservices.jdk8.WebServiceDriverRequestUnitTest;

import com.magenic.jmaqs.utilities.helper.TestCategories;
import com.magenic.jmaqs.webservices.jdk8.BaseWebServiceTest;
import com.magenic.jmaqs.webservices.jdk8.WebServiceUtilities;
import com.magenic.jmaqs.webservices.jdk8.models.Product;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;

/**
 * Tests the web service driver Post functionality.
 */
public class WebServiceDriverPostUnitTest extends BaseWebServiceTest {

  /**
   * Test posting with an XML object.
   * @throws Exception if an error occurs
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void webServiceXmlPost() throws Exception {
    Product p = new Product();
    p.setCategory("ff");
    p.setId(4);
    p.setName("ff");
    p.setPrice(BigDecimal.valueOf(3.25));

    HttpEntity content = WebServiceUtilities.createStringEntity(p, ContentType.APPLICATION_XML);
    CloseableHttpResponse response = this.getWebServiceDriver()
        .postContent("/api/XML_JSON/Post/1", content,
            ContentType.APPLICATION_XML, true);

    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    Assert.assertEquals(response.getStatusLine().getReasonPhrase(), "OK");
  }

  /**
   * Test posting with a JSON object.
   * @throws Exception if an error occurs
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void webServiceJsonPost() throws Exception {
    Product p = new Product();
    p.setCategory("ff");
    p.setId(4);
    p.setName("ff");
    p.setPrice(BigDecimal.valueOf(3.25));

    HttpEntity content = WebServiceUtilities.createStringEntity(p, ContentType.APPLICATION_JSON);
    CloseableHttpResponse response = this.getWebServiceDriver()
        .postContent("/api/XML_JSON/Post", content,
            ContentType.APPLICATION_JSON, true);

    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    Assert.assertEquals(response.getStatusLine().getReasonPhrase(), "OK");
  }

  /**
   * Test posting with a String object.
   * @throws Exception if an error occurs
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void webServiceStringPost() throws Exception {
    HttpEntity content = WebServiceUtilities.createStringEntity("Test", ContentType.TEXT_PLAIN);
    CloseableHttpResponse response = this.getWebServiceDriver()
        .postContent("/api/String", content, ContentType.TEXT_PLAIN, true);

    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    Assert.assertEquals(response.getStatusLine().getReasonPhrase(), "OK");
  }

  /**
   * Verify a post error returns the expected code and message.
   *
   * @throws Exception
   *           There was a problem with the test
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void webServicePostStringError() throws Exception {
    CloseableHttpResponse response = this.getWebServiceDriver()
        .postContent("/api/String", null, ContentType.TEXT_PLAIN, false);

    Assert.assertEquals(response.getStatusLine().getStatusCode(), 400);
    Assert.assertEquals(response.getStatusLine().getReasonPhrase(), "Bad Request");
  }
}
