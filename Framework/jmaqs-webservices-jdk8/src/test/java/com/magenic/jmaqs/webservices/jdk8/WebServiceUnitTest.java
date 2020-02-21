/*
 * Copyright 2020 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.webservices.jdk8;

import com.magenic.jmaqs.utilities.helper.TestCategories;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Web service wrapper unit tests.
 */
public class WebServiceUnitTest extends BaseWebServiceTest {

  /**
   * Verify we can get content.
   * 
   * @throws Exception
   *           There was a problem with the test
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void webServiceGetVerificationTest() throws Exception {

    CloseableHttpResponse response = this.getWebServiceDriver().getContent("/api/String/1",
        ContentType.TEXT_PLAIN, true);
    String responseString = WebServiceUtilities.getResponseBody(response);

    Assert.assertTrue(responseString.contains("Tomato Soup"),
        "Was expecting a result with Tomato Soup but instead got - " + response.toString());
  }

  /**
   * Verify a get error returns the expected code and message.
   * 
   * @throws Exception
   *           There was a problem with the test
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void webServiceGetError() throws Exception {

    CloseableHttpResponse response = this.getWebServiceDriver().getContent("/api/String/-1",
        ContentType.TEXT_PLAIN, false);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 204);
  }

  /**
   * Verify delete works.
   * 
   * @throws Exception
   *           There was a problem with the test
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void webServiceDelete() throws Exception {

    CloseableHttpResponse response = this.getWebServiceDriver()
        .deleteContent("/api/XML_JSON/Delete/1", ContentType.TEXT_PLAIN, false);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
  }

  /**
   * Verify a patch error returns the expected code and message.
   * 
   * @throws Exception
   *           There was a problem with the test
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void webServicePatchError() throws Exception {
    HttpEntity content = WebServiceUtilities.createStringEntity("", ContentType.APPLICATION_XML);
    CloseableHttpResponse response = this.getWebServiceDriver()
        .patchContent("/api/XML_JSON/Put/1", content, ContentType.APPLICATION_XML, false);

    Assert.assertEquals(response.getStatusLine().getStatusCode(), 405);
    Assert.assertEquals(response.getStatusLine().getReasonPhrase(), "Method Not Allowed");
  }

  /**
   * Verify a post error returns the expected code and message.
   * 
   * @throws Exception
   *           There was a problem with the test
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void webServicePostError() throws Exception {
    CloseableHttpResponse response = this.getWebServiceDriver()
        .postContent("/api/String", null, ContentType.TEXT_PLAIN, false);

    Assert.assertEquals(response.getStatusLine().getStatusCode(), 400);
    Assert.assertEquals(response.getStatusLine().getReasonPhrase(), "Bad Request");
  }

  /**
   * Verify a put error returns the expected code and message.
   * 
   * @throws Exception
   *           There was a problem with the test
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void webServicePutError() throws Exception {
    HttpEntity content = WebServiceUtilities.createStringEntity("", ContentType.APPLICATION_XML);
    CloseableHttpResponse response = this.getWebServiceDriver()
        .putContent("/api/XML_JSON/Put/1", content, ContentType.APPLICATION_XML, false);

    Assert.assertEquals(response.getStatusLine().getStatusCode(), 409);
    Assert.assertEquals(response.getStatusLine().getReasonPhrase(), "Conflict");
  }
}