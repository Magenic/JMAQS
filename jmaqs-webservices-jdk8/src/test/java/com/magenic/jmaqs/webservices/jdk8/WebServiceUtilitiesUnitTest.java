/*
 * Copyright 2020 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.webservices.jdk8;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.magenic.jmaqs.webservices.jdk8.models.Product;
import com.magenic.jmaqs.utilities.helper.TestCategories;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Unit tests for the Web Service Utilities functionality.
 */
public class WebServiceUtilitiesUnitTest extends BaseWebServiceTest {
  /**
  The web service object used for the test.
   */
  private final Product product = new Product(1, "Milk", "Dairy", BigDecimal.TEN);

  /**
   * Tests the functionality of the Get Response body.
   * @throws Exception if there is an error in the request
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void testGetResponseBody() throws Exception {
    CloseableHttpResponse response = this.getWebServiceDriver()
        .getContent("/api/String/1", ContentType.TEXT_PLAIN, true);
    String responseString = WebServiceUtilities.getResponseBody(response);

    Assert.assertNotNull(responseString, "Response body did not deserialize string correctly");
  }

  /**
   * Test the functionality of the Get response body as an object from Json.
   * @throws Exception if an error occurs
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void testGetResponseBodyAsObjectFromJson() throws Exception {
    CloseableHttpResponse response = this.getWebServiceDriver()
        .getContent("/api/XML_JSON/GetProduct/1", ContentType.APPLICATION_JSON, true);
    Product jsonProduct = WebServiceUtilities.getResponseBody(response, ContentType.APPLICATION_JSON, Product.class);

    Assert.assertNotNull(jsonProduct, "Response body did not deserialize object from json correctly");
  }

  /**
   * Get response body as an object from Xml.
   * @throws Exception if an error occurs
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void testGetResponseBodyAsObjectFromXml() throws Exception {
    CloseableHttpResponse response = this.getWebServiceDriver()
        .getContent("/api/XML_JSON/GetProduct/1", ContentType.APPLICATION_XML, true);
    Product xmlProduct = WebServiceUtilities
        .getResponseBody(response, ContentType.APPLICATION_XML, Product.class);

    Assert.assertNotNull(xmlProduct, "Response body did not deserialize object from xml correctly");
  }

  /**
   * Try to get a response body as an object from
   * neither Xml or Jason, this should throw an error which is caught.
   * @throws Exception because the object is not supported
   */
  @Test(expectedExceptions = IllegalArgumentException.class, groups = TestCategories.WEB_SERVICE)
  public void testGetResponseBodyAsObjectFromNeitherXmlOrJson() throws Exception {
    CloseableHttpResponse response = this.getWebServiceDriver()
        .getContent("/api/XML_JSON/GetProduct/1", ContentType.APPLICATION_OCTET_STREAM, true);
    WebServiceUtilities.getResponseBody(response, ContentType.APPLICATION_OCTET_STREAM, Product.class);

    Assert.fail("Exception was not thrown for attempting to deserialize json to an object");
  }

  /**
   * Tests when an object deserialize to Json.
   * @throws Exception if the deserialize process fails
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void testDeserializeJson() throws Exception {
    CloseableHttpResponse response = this.getWebServiceDriver()
        .getContent("/api/XML_JSON/GetProduct/1", ContentType.APPLICATION_JSON, true);
    Product product = WebServiceUtilities.deserializeJson(response, Product.class);

    Assert.assertNotNull(product);
  }

  /**
   * Tests when an object deserialize to Xml.
   * @throws Exception if the deserialize process fails
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void testDeserializeXml() throws Exception {
    CloseableHttpResponse response = this.getWebServiceDriver()
        .getContent("/api/XML_JSON/GetProduct/1", ContentType.APPLICATION_XML, true);
    Product product = WebServiceUtilities.deserializeXml(response, Product.class);

    Assert.assertNotNull(product);
  }

  /**
   * Tests creating a string entity from Json.
   * @throws JsonProcessingException if there is an error in converting Json to a string
   * @throws UnsupportedEncodingException if there is an unsupported conversion
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void testCreateStringEntityJson()
      throws JsonProcessingException, UnsupportedEncodingException {
    HttpEntity entity = WebServiceUtilities.createStringEntity(this.product, ContentType.APPLICATION_JSON);
    Assert.assertNotNull(entity, "string entity wasn't created using content type application/json");
  }

  /**
   * Tests creating a string entity from Xml.
   * @throws JsonProcessingException if there is an error in converting Json to a string
   * @throws UnsupportedEncodingException if there is an unsupported conversion
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void testCreateStringEntityXml()
      throws JsonProcessingException, UnsupportedEncodingException {
    HttpEntity entity = WebServiceUtilities.createStringEntity(this.product, ContentType.APPLICATION_XML);
    Assert.assertNotNull(entity, "string entity wasn't created using content type application/xml");
  }

  /**
   * Tests when an object serialize to Json.
   * @throws JsonProcessingException if the serialize process fails
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void testSerializeJson() throws JsonProcessingException {
    String expectedJson = "{\"Id\":1,\"Name\":\"Milk\",\"Category\":\"Dairy\",\"Price\":10}";
    String actualJson = WebServiceUtilities.serializeJson(this.product);

    Assert.assertEquals(expectedJson, actualJson, String
        .format("the json values compared aren't equal, expected was %s while actual was %s",
            expectedJson, actualJson));
  }

  /**
   * Tests when an object serialize to xml.
   * @throws JsonProcessingException if the serialize process fails
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void testSerializeXml() throws JsonProcessingException {
    String expectedXml = "<?xml version='1.0' encoding='UTF-8'?>"
        + "<Product xmlns=\"http://schemas.datacontract.org/2004/07/AutomationTestSite.Models\">"
        + "<Id>1</Id><Name>Milk</Name><Category>Dairy</Category><Price>10</Price></Product>";
    String actualXml = WebServiceUtilities.serializeXml(this.product);
    Assert.assertEquals(expectedXml, actualXml, String
        .format("the xml values compared aren't equal, expected was %s while actual was %s", expectedXml, actualXml));
  }

  /**
   * Tests that an error occurs when trying to create a string entity
   * with an unsupported object type.
   * @throws JsonProcessingException if an error occurs in the object to string process
   * @throws UnsupportedEncodingException If there is an illegal or unsupported exception thrown
   */
  @Test(expectedExceptions = IllegalArgumentException.class, groups = TestCategories.WEB_SERVICE)
  public void testCreateStringEntityNotJsonOrXml()
      throws JsonProcessingException, UnsupportedEncodingException {
    WebServiceUtilities.createStringEntity(this.product, ContentType.APPLICATION_FORM_URLENCODED);

    Assert.fail("Expected exception of IllegalArgumentException was not caught for content type that did not contain xml or json");
  }

  /**
   * Tests creating a string entity custom content type.
   * @throws JsonProcessingException if an error is thrown during conversion to string
   * @throws UnsupportedEncodingException if there is an unsupported type
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void testCreateStringEntityCustomContentType()
      throws JsonProcessingException, UnsupportedEncodingException {
    HttpEntity entity = WebServiceUtilities.createStringEntity(this.product,
        Charset.defaultCharset(), "application/json");

    Assert.assertNotNull(entity, "Entity was not set correctly with custom charset and mime-type");
  }
}
