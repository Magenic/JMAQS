/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.webservices.jdk11;

import com.magenic.jmaqs.utilities.helper.TestCategories;
import com.magenic.jmaqs.webservices.jdk11.models.Product;
import com.magenic.jmaqs.webservices.jdk8.BaseWebServiceTest;
import com.magenic.jmaqs.webservices.jdk8.WebServiceConfig;
import com.magenic.jmaqs.webservices.jdk8.MediaType;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.springframework.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test web service posts.
 */
public class WebServiceDriverPostUnitTest extends BaseWebServiceTest {
  /**
   * String to hold the URL.
   */
  private static final String baseUrl = WebServiceConfig.getWebServiceUri();

  /**
   * Product object to be used in a test.
   */
  private final Product product = new Product(4, "ff", "ff", BigDecimal.valueOf(3.25));

  WebServiceDriver webServiceDriver = new WebServiceDriver(HttpClientFactory.getDefaultClient(),
      HttpRequest.newBuilder());

  /**
   * Post JSON request to verify status codes.
   * @throws IOException if the exception is thrown
   * @throws InterruptedException if the exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void postJSONSerializedVerifyStatusCode() throws IOException, InterruptedException {
    HttpResponse<String> result = webServiceDriver.post(baseUrl + "/api/XML_JSON/Post",
        MediaType.APP_JSON, product, MediaType.APP_JSON, true);
    Assert.assertEquals(result.statusCode(), HttpStatus.OK.value());
  }

  /**
   * Post JSON stream request to verify status codes.
   * @throws IOException if the exception is thrown
   * @throws InterruptedException if the exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void postJSONStreamSerializedVerifyStatusCode()
      throws IOException, InterruptedException {
    HttpResponse<String> result = webServiceDriver.post(baseUrl + "/api/XML_JSON/Post",
        MediaType.APP_JSON, product, MediaType.APP_JSON, HttpStatus.OK);
    Assert.assertEquals(result.statusCode(), HttpStatus.OK.value());
  }

  /**
   * Post XML request to verify status codes.
   * @throws IOException if the exception is thrown
   * @throws InterruptedException if the exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void postXMLSerializedVerifyStatusCode()
      throws IOException, InterruptedException {
    String content = WebServiceUtilities.createStringEntity(product, MediaType.APP_XML);
    HttpResponse<String> result = webServiceDriver.post(baseUrl + "/api/XML_JSON/Post",
        MediaType.APP_XML, content, MediaType.APP_XML, HttpStatus.OK);
    Assert.assertEquals(result.statusCode(), HttpStatus.OK.value());
  }

  /**
   * Post with JSON.
   * @throws IOException if exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void postWithJson() throws IOException, InterruptedException {
    String content = WebServiceUtilities.createStringEntity(product, MediaType.APP_JSON);
    HttpResponse<String> result = webServiceDriver.post(baseUrl + "/api/XML_JSON/Post",
        MediaType.APP_JSON, content, MediaType.APP_JSON, true);
    Assert.assertTrue(result.body().isEmpty());
  }

  /**
   * Post with JSON.
   * @throws IOException if exception is thrown
   * @throws InterruptedException if exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void postWithXml() throws IOException, InterruptedException {
    String content = WebServiceUtilities.createStringEntity(product, MediaType.APP_XML);
    HttpResponse<String> result = webServiceDriver.post(baseUrl + "/api/XML_JSON/Post",
        MediaType.APP_XML, content, MediaType.APP_XML, true);
    Assert.assertTrue(result.body().isEmpty());
  }

  /**
   * Post XML to verify no string is returned.
   * @throws IOException if the exception is thrown
   * @throws InterruptedException if the exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void postXMLSerializedVerifyEmptyStringError() throws IOException, InterruptedException {
    HttpResponse<String> result = webServiceDriver.post(baseUrl + "/api/XML_JSON/Post",
        MediaType.APP_XML, "", MediaType.APP_XML, false);
    Assert.assertTrue(result.body().contains("value is required"));
  }

  /**
   * Post string without utility.
   * @throws IOException if the exception is thrown
   * @throws InterruptedException if the exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void postStringWithoutMakeContent() throws IOException, InterruptedException {
    HttpResponse<String> result = webServiceDriver.post(baseUrl + "/api/String",
        MediaType.PLAIN_TEXT, "test", MediaType.PLAIN_TEXT, true);
    Assert.assertEquals(result.body(), "");
  }

  /**
   * Post stream without utility.
   * @throws IOException if the exception is thrown
   * @throws InterruptedException if the exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void postReturnWhenNoContentInResponse() throws IOException, InterruptedException {
    Product result = webServiceDriver.post(baseUrl + "/api/XML_JSON/Post",
        MediaType.APP_JSON, product, Product.class, HttpStatus.OK);
    Assert.assertNull(result);

    result = webServiceDriver.post(baseUrl + "/api/XML_JSON/Post",
        MediaType.APP_XML, product, Product.class, true);
    Assert.assertNull(result);
  }

  /**
   * Post string with utility.
   * @throws IOException if the exception is thrown
   * @throws InterruptedException if the exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void postStringWithMakeContent() throws IOException, InterruptedException {
    String content = WebServiceUtilities.createStringEntity("Test", MediaType.PLAIN_TEXT);
    HttpResponse<String> result = webServiceDriver.post(baseUrl + "/api/String",
        MediaType.PLAIN_TEXT, content, MediaType.PLAIN_TEXT, true);
    Assert.assertEquals(result.body(), "");
  }

  /**
   * Post string without utility to verify status code.
   * @throws IOException if the exception is thrown
   * @throws InterruptedException if the exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void postStringWithoutContentStatusCode() throws IOException, InterruptedException {
    HttpResponse<String> result = webServiceDriver.post(baseUrl + "/api/String",
        MediaType.PLAIN_TEXT, "Test" , MediaType.PLAIN_TEXT, true);
    Assert.assertEquals(result.statusCode(), HttpStatus.OK.value());
  }

  /**
   * Verifying other http status codes.
   * @throws IOException if the exception is thrown
   * @throws InterruptedException if the exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void postExpectContentError() throws IOException, InterruptedException {
    HttpResponse<String> result = webServiceDriver.post(baseUrl + "/api/String",
        MediaType.PLAIN_TEXT, "", MediaType.PLAIN_TEXT, false);
    Assert.assertEquals(result.statusCode(), HttpStatus.BAD_REQUEST.value());
  }

  /**
   * Testing string returned.
   * @throws IOException if the exception is thrown
   * @throws InterruptedException if the exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void postExpectStringError() throws IOException, InterruptedException {
    HttpResponse<String> result = webServiceDriver.post(baseUrl + "/api/String",
        MediaType.PLAIN_TEXT, "", MediaType.PLAIN_TEXT, false);
    Assert.assertEquals(result.body(), "{\"Message\":\"No data\"}");
  }

  /**
   * Testing string returned.
   * @throws IOException if the exception is thrown
   * @throws InterruptedException if the exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void postExpectStringErrorEmptyHttpContent() throws IOException, InterruptedException {
    HttpResponse<String> result = webServiceDriver.post(baseUrl + "/api/String",
        MediaType.PLAIN_TEXT, "", MediaType.PLAIN_TEXT, false);
    Assert.assertEquals(result.body(), "{\"Message\":\"No data\"}");
  }
}