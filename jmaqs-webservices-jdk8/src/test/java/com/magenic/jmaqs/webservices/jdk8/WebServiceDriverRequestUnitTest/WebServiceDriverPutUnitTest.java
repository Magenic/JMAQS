/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.webservices.jdk8.WebServiceDriverRequestUnitTest;

import com.magenic.jmaqs.utilities.helper.TestCategories;
import com.magenic.jmaqs.webservices.jdk8.BaseWebServiceTest;
import com.magenic.jmaqs.webservices.jdk8.WebServiceUtilities;
import com.magenic.jmaqs.webservices.jdk8.models.Product;
import java.math.BigDecimal;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Tests the web service driver Put functionality.
 */
public class WebServiceDriverPutUnitTest extends BaseWebServiceTest {
  /**
   * verify an object serialized as a XML can do a PUT request.
   * @throws Exception There was a problem with the test
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void webServiceXmlPut() throws Exception {
    Product p = new Product();
    p.setCategory("ff");
    p.setId(4);
    p.setName("ff");
    p.setPrice(BigDecimal.valueOf(3.25));

    HttpEntity content = WebServiceUtilities.createStringEntity(p, ContentType.APPLICATION_XML);
    CloseableHttpResponse response = this.getWebServiceDriver()
        .putContent("/api/XML_JSON/Put/1", content, ContentType.APPLICATION_XML, true);

    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    Assert.assertEquals(response.getStatusLine().getReasonPhrase(), "OK");
  }

  /**
   * verify an object serialized as a JSON can do a PUT request.
   * @throws Exception There was a problem with the test
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void webServiceJsonPut() throws Exception {
    Product p = new Product();
    p.setCategory("ff");
    p.setId(4);
    p.setName("ff");
    p.setPrice(BigDecimal.valueOf(3.25));

    HttpEntity content = WebServiceUtilities.createStringEntity(p, ContentType.APPLICATION_JSON);
    CloseableHttpResponse response = this.getWebServiceDriver()
        .putContent("/api/XML_JSON/Put/1", content, ContentType.APPLICATION_JSON, true);

    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    Assert.assertEquals(response.getStatusLine().getReasonPhrase(), "OK");
  }

  /**
   * verify an object serialized as a String can do a PUT request.
   * @throws Exception There was a problem with the test
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void webServiceStringPut() throws Exception {
    Product p = new Product();
    p.setCategory("ff");
    p.setId(4);
    p.setName("ff");
    p.setPrice(BigDecimal.valueOf(3.25));

    HttpEntity content = WebServiceUtilities.createStringEntity(p, ContentType.TEXT_PLAIN);
    CloseableHttpResponse response = this.getWebServiceDriver()
        .putContent("/api/String/Put/1", content, ContentType.TEXT_PLAIN, false);

    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    Assert.assertEquals(response.getStatusLine().getReasonPhrase(), "OK");
  }

  /**
   * Verify a put error returns the expected code and message.
   *
   * @throws Exception
   *           There was a problem with the test
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void webServicePutError() throws Exception {
    HttpEntity content = WebServiceUtilities.createStringEntity(null, ContentType.APPLICATION_XML);
    CloseableHttpResponse response = this.getWebServiceDriver()
        .putContent("/api/XML_JSON/Put/1", content, ContentType.APPLICATION_XML, false);

    Assert.assertEquals(response.getStatusLine().getStatusCode(), 409);
    Assert.assertEquals(response.getStatusLine().getReasonPhrase(), "Conflict");
  }
}
