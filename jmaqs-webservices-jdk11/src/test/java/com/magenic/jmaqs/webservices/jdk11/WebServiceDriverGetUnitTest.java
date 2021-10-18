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
import java.net.http.HttpResponse;
import org.springframework.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test web service gets.
 */
public class WebServiceDriverGetUnitTest extends BaseWebServiceTest {

  /**
   * String to hold the URL.
   */
  private static final String baseUrl = WebServiceConfig.getWebServiceUri();

  /**
   * The web service driver to be used in a test.
   */
  private final WebServiceDriver webServiceDriver = new WebServiceDriver(HttpClientFactory.getDefaultClient());


  /**
   * Test Json Get deserialize a single product.
   * @throws IOException if exception is thrown
   * @throws InterruptedException if exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void getProductXmlDeserialize() throws IOException, InterruptedException {
    Product products = webServiceDriver.get(baseUrl + "/api/XML_JSON/GetProduct/2", MediaType.APP_XML, true, Product.class);
    Assert.assertEquals(products.getName(), "Yo-yo", "Expected 3 products to be returned");
  }

  /**
   * Test XML get all items.
   * @throws IOException if exception is thrown
   * @throws InterruptedException if exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void getProductsXmlDeserialize() throws IOException, InterruptedException {
    Product[] products = webServiceDriver.get(baseUrl + "/api/XML_JSON/GetAllProducts", MediaType.APP_XML, false, Product[].class);
    Assert.assertEquals(products.length, 3, "Expected 3 products to be returned");
  }

  /**
   * Test Json Get deserialize a single product.
   * @throws IOException if exception is thrown
   * @throws InterruptedException if exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void getProductJsonDeserialize() throws IOException, InterruptedException {
    HttpResponse<String> response = webServiceDriver.get(
        baseUrl + "/api/XML_JSON/GetProduct/2", MediaType.APP_JSON, false);
    Product products = WebServiceUtilities.getResponseBody(response, MediaType.APP_JSON, Product.class);
    Assert.assertEquals(products.getName(),"Yo-yo", "Expected 3 products to be returned");
  }

  /**
   * Test Json Get deserialize multiple products.
   * @throws IOException if exception is thrown
   * @throws InterruptedException if exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void getProductsJsonDeserialize() throws IOException, InterruptedException {
    Product[] products = webServiceDriver.get(baseUrl + "/api/XML_JSON/GetAllProducts",
        MediaType.APP_JSON, HttpStatus.OK, Product[].class);
    Assert.assertEquals(products.length, 3, "Expected 3 products to be returned");
  }

  /**
   * Test string Get all.
   * @throws IOException if exception is thrown
   * @throws InterruptedException if exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void getProductsPlainText() throws IOException, InterruptedException {
    HttpResponse<String> result = webServiceDriver.get(baseUrl + "/api/String/Get",  MediaType.PLAIN_TEXT, false);
    Assert.assertTrue(result.body().contains("Tomato Soup"),
        "Was expecting a result with Tomato Soup but instead got - " + result);
    Assert.assertTrue(result.body().contains("Yo-yo"),
        "Was expecting a result with Yo-yo but instead got - " + result);
    Assert.assertTrue(result.body().contains("Hammer"),
        "Was expecting a result with Hammer but instead got - " + result);
  }

  /**
   * Test string Get by id.
   * @throws IOException if exception is thrown
   * @throws InterruptedException if exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void getStringById() throws IOException, InterruptedException {
    HttpResponse<String> result = webServiceDriver.get(baseUrl + "/api/String/1",  MediaType.PLAIN_TEXT, false);
    Assert.assertTrue(result.body().contains("Tomato Soup"),
        "Was expecting a result with Tomato Soup but instead got - " + result);
  }

  /**
   * Test string Get by id.
   * @throws IOException if exception is thrown
   * @throws InterruptedException if exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void getStringByName() throws IOException, InterruptedException {
    HttpResponse<String> result = webServiceDriver.get(baseUrl + "/api/String/Yo-yo",  MediaType.PLAIN_TEXT, false);
    Assert.assertTrue(result.body().contains("Yo-yo"),
        "Was expecting a result with Yo-yo but instead got - " + result);
  }

  /**
   * Test that we can use the web service utility to deserialize JSON.
   * @throws IOException if exception is thrown
   * @throws InterruptedException if exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void getResponseAndDeserializeJson() throws IOException, InterruptedException {
    HttpResponse<String> message = webServiceDriver.get(baseUrl + "/api/XML_JSON/GetAllProducts",
       MediaType.APP_JSON, true  );
    Product[] products = WebServiceUtilities.deserializeJson(message, Product[].class);
    Assert.assertNotNull(products);
    Assert.assertEquals(products.length, 3, "Expected 3 products to be returned");
  }

  /**
   * Test that we can use the web service utility to deserialize XML.
   * @throws IOException if exception is thrown
   * @throws InterruptedException if exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void getResponseAndDeserializeXml() throws IOException, InterruptedException {
    HttpResponse<String> message = webServiceDriver.get(baseUrl + "/api/XML_JSON/GetAllProducts",
        MediaType.APP_XML, true);
    Product[] products = WebServiceUtilities.deserializeXml(message, Product[].class);
    Assert.assertNotNull(products);
    Assert.assertEquals(products.length,3,"Expected 3 products to be returned");
  }

  /**
   * Test that we can use the web service utility to deserialize JSON and have an expected Status.
   * @throws IOException if exception is thrown
   * @throws InterruptedException if exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void getResponseAndDeserializeJsonExpectedStatus() throws IOException, InterruptedException {
    HttpResponse<String> message = webServiceDriver.get(baseUrl + "/api/XML_JSON/GetAllProducts",
        MediaType.APP_JSON, HttpStatus.OK);
    Product[] products = WebServiceUtilities.deserializeJson(message, Product[].class);
    Assert.assertNotNull(products);
    Assert.assertEquals(products.length, 3, "Expected 3 products to be returned");
  }

  /**
   * Test that we can use the web service utility to deserialize XML and have an expected Status.
   * @throws IOException if exception is thrown
   * @throws InterruptedException if exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void getResponseAndDeserializeXmlExpectedStatus() throws IOException, InterruptedException {
    HttpResponse<String> message = webServiceDriver.get(baseUrl + "/api/XML_JSON/GetAllProducts",
        MediaType.APP_XML, HttpStatus.OK);
    Product[] products = WebServiceUtilities.deserializeXml(message, Product[].class);
    Assert.assertNotNull(products);
    Assert.assertEquals(products.length,3,"Expected 3 products to be returned");
  }

  /**
   * Test type parameterized Get request with expected status.
   * @throws IOException if an exception is thrown
   * @throws InterruptedException if an exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void getTypeParamWithExpectedStatus() throws IOException, InterruptedException {
    Product[] res = webServiceDriver.get(baseUrl + "/api/XML_JSON/GetAllProducts",
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
}