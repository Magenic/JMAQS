/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.webservices.jdk8.WebServiceDriverRequestUnitTest;

import com.magenic.jmaqs.utilities.helper.TestCategories;
import java.io.IOException;
import java.net.URISyntaxException;

import com.magenic.jmaqs.webservices.jdk8.BaseWebServiceTest;
import com.magenic.jmaqs.webservices.jdk8.WebServiceUtilities;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Tests the web service driver Get functionality.
 */
public class WebServiceDriverGetUnitTest extends BaseWebServiceTest {
  /**
   * Verify we can get content.
   *
   * @throws Exception
   *           There was a problem with the test
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void webServiceGetStringById() throws Exception {
    CloseableHttpResponse response = this.getWebServiceDriver().getContent("/api/String/1",
        ContentType.TEXT_PLAIN, true);
    String responseString = WebServiceUtilities.getResponseBody(response);

    Assert.assertTrue(responseString.contains("Tomato Soup"),
        "Was expecting a result with Tomato Soup but instead got - " + response.toString());
  }

  /**
   * Verify we can get content.
   *
   * @throws Exception
   *           There was a problem with the test
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void webServiceGetStringByName() throws Exception {
    CloseableHttpResponse response = this.getWebServiceDriver().getContent("/api/String/Hammer",
        ContentType.TEXT_PLAIN, true);
    String responseString = WebServiceUtilities.getResponseBody(response);

    Assert.assertTrue(responseString.contains("Hammer"),
        "Was expecting a result with Hammer but instead got - " + response.toString());
  }

  /**
   * Verify we can get content.
   *
   * @throws Exception
   *           There was a problem with the test
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void webServiceGetAllStrings() throws Exception {
    CloseableHttpResponse response = this.getWebServiceDriver().getContent("/api/String/Get",
        ContentType.TEXT_PLAIN, true);
    String responseString = WebServiceUtilities.getResponseBody(response);

    Assert.assertTrue(responseString.contains("Tomato Soup"),
        "Was expecting a result with Tomato Soup but instead got - " + response.toString());
    Assert.assertTrue(responseString.contains("Yo-yo"),
        "Was expecting a result with Yo-yo but instead got - " + response.toString());
    Assert.assertTrue(responseString.contains("Hammer"),
        "Was expecting a result with Hammer but instead got - " + response.toString());
  }

  /**
   * Tests getting all products in Xml format.
   * @throws IOException if an IO exception is thrown
   * @throws URISyntaxException if an incorrect URI syntax occurs
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void webServiceGetAllProductsXml() throws IOException, URISyntaxException {
    CloseableHttpResponse response = this.getWebServiceDriver().getContent("/api/XML_JSON/GetAllProducts",
        ContentType.APPLICATION_XML, true);
    String responseString = WebServiceUtilities.getResponseBody(response);
    Assert.assertTrue(responseString.contains("3"));
  }

  /**
   * Tests getting all products in Json format.
   * @throws IOException if an IO exception is thrown
   * @throws URISyntaxException if an incorrect URI syntax occurs
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void webServiceGetAllProductsJson() throws IOException, URISyntaxException {
    CloseableHttpResponse response = this.getWebServiceDriver().getContent("/api/XML_JSON/GetAllProducts",
        ContentType.APPLICATION_JSON, true);
    String responseString = WebServiceUtilities.getResponseBody(response);
    Assert.assertTrue(responseString.contains("3"));
  }

  /**
   * Tests getting a product in Xml format.
   * @throws IOException if an IO exception occurs
   * @throws URISyntaxException If the URI syntax isn't valid
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void webServiceGetProductXml() throws IOException, URISyntaxException {
    CloseableHttpResponse response = this.getWebServiceDriver().getContent("/api/XML_JSON/GetProduct/2",
        ContentType.APPLICATION_XML, true);
    String responseString = WebServiceUtilities.getResponseBody(response);
    Assert.assertTrue(responseString.contains("Yo-yo"));
  }

  /**
   * Tests getting a product in Json format.
   * @throws IOException if an IO exception occurs
   * @throws URISyntaxException If the URI syntax isn't valid
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void webServiceGetProductJson() throws IOException, URISyntaxException {
    CloseableHttpResponse response = this.getWebServiceDriver().getContent("/api/XML_JSON/GetProduct/2",
        ContentType.APPLICATION_JSON, true);
    String responseString = WebServiceUtilities.getResponseBody(response);
    Assert.assertTrue(responseString.contains("Yo-yo"));
  }

  /**
   * Verify a get error returns the expected code and message.
   *
   * @throws Exception
   *           There was a problem with the test
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void webServiceGetStringError() throws Exception {
    CloseableHttpResponse response = this.getWebServiceDriver().getContent("/api/String/-1",
        ContentType.TEXT_PLAIN, false);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 204);
  }
}
