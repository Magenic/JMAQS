/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.webservices.jdk11;

import com.magenic.jmaqs.utilities.helper.TestCategories;
import com.magenic.jmaqs.webservices.jdk11.models.Product;
import com.magenic.jmaqs.webservices.jdk8.BaseWebServiceTest;
import com.magenic.jmaqs.webservices.jdk8.MediaType;
import com.magenic.jmaqs.webservices.jdk8.WebServiceConfig;
import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.net.http.HttpResponse;
import org.springframework.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

public class WebServiceStatusCodeUnitTest extends BaseWebServiceTest {
  /**
   * String to hold the URL.
   */
  private static final String baseUrl = WebServiceConfig.getWebServiceUri();

  /**
   * Product object to be used in a test.
   */
  private final Product product = new Product(4, "ff", "ff", BigDecimal.valueOf(3.25));

  /**
   * The web service driver to be used in a test.
   */
  private final WebServiceDriver webServiceDriver = new WebServiceDriver(HttpClientFactory.getDefaultClient());

  /**
   * Test type parameterized Get request with expected status.
   * @throws IOException if an exception is thrown
   * @throws InterruptedException if an exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void getTypeParamWithExpectedStatus() throws IOException, InterruptedException {
    Object res = webServiceDriver.get(baseUrl + "/api/XML_JSON/GetAllProducts",
            MediaType.APP_JSON, HttpStatus.OK, Product[].class);
    Assert.assertNotNull(res);
  }

  /**
   * Test Get request with expected status.
   * @throws IOException if an exception is thrown
   * @throws InterruptedException if an exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void getWithExpectedStatus() throws IOException, InterruptedException {
    HttpResponse<String> res = webServiceDriver.get(
        baseUrl + "/api/XML_JSON/GetAllProducts", MediaType.APP_XML, HttpStatus.OK);
    Assert.assertNotNull(res);
  }

  /**
   * Test Get with response request with expected status.
   * @throws IOException if an exception is thrown
   * @throws InterruptedException if an exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void getWithResponseWithExpectedStatus() throws IOException, InterruptedException {
    HttpResponse<String> res = webServiceDriver.get(
        baseUrl + "/api/XML_JSON/GetAllProducts", MediaType.APP_XML, HttpStatus.OK);
    Assert.assertNotNull(res);
  }

  /**
   * Test type parameterized Post request with expected status.
   * @throws IOException if an exception is thrown
   * @throws InterruptedException if an exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void postTypeParamWithExpectedStatus() throws IOException, InterruptedException {
    HttpResponse<String> res = webServiceDriver.post(baseUrl + "/api/XML_JSON/Post",
        MediaType.APP_JSON, product, MediaType.APP_JSON, HttpStatus.OK);
    Assert.assertNotNull(res);
  }

  /**
   * Test Post request with expected status.
   * @throws IOException if an exception is thrown
   * @throws InterruptedException if an exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void postWithExpectedStatus() throws IOException, InterruptedException {
    String req = WebServiceUtilities.createStringEntity(product, MediaType.APP_JSON);
    HttpResponse<String> res = webServiceDriver.post(baseUrl + "/api/XML_JSON/Post",
            MediaType.APP_JSON, req, MediaType.APP_JSON, HttpStatus.OK);
    Assert.assertNotNull(res);
  }

  /**
   * Test more parameters Post request with expected status
   * @throws IOException if an exception is thrown
   * @throws InterruptedException if an exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void postMoreParamsWithExpectedStatus() throws IOException, InterruptedException {
    HttpResponse<String> res = webServiceDriver.post(baseUrl + "/api/XML_JSON/Post",
        MediaType.APP_JSON, product, MediaType.APP_JSON, true);
    Assert.assertNotNull(res);
  }

  /**
   * Test more parameters Post with response request with expected status.
   * @throws IOException if an exception is thrown
   * @throws InterruptedException if an exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void postMoreParamsWithResponseWithExpectedStatus() throws IOException, InterruptedException {
    Object res = webServiceDriver.post(baseUrl + "/api/XML_JSON/Post",
        MediaType.APP_JSON, product, MediaType.APP_JSON, true);
    Assert.assertNotNull(res);
  }

  /**
   * Test Post with response request with expected status.
   * @throws IOException if an exception is thrown
   * @throws InterruptedException if an exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)

  public void postWithResponseWithExpectedStatus() throws IOException, InterruptedException {
    Object res = webServiceDriver.post(baseUrl + "/api/XML_JSON/Post",
        MediaType.APP_JSON, product, MediaType.APP_JSON, HttpStatus.OK);
    Assert.assertNotNull(res);
  }
}