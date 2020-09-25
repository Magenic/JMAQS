package com.magenic.jmaqs.webservices.jdk11;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.magenic.jmaqs.utilities.helper.TestCategories;
import com.magenic.jmaqs.webservices.jdk11.models.Product;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;

public class WebServiceUtilitiesUnitTest extends BaseWebServiceTest {
  private final Product product = new Product(1, "Milk", "Dairy", BigDecimal.TEN);

  @Test(groups = TestCategories.WEB_SERVICE)
  public void testGetResponseBody() throws Exception {
    HttpResponse response = this.getWebServiceDriver()
        .getContent("/api/String/1", ContentType.TEXT_PLAIN, true);
    String responseString = WebServiceUtilities.getResponseBody(response);

    Assert.assertNotNull(responseString, "Response body did not deserialize string correctly");
  }

  @Test(groups = TestCategories.WEB_SERVICE)
  public void testGetResponseBodyAsObjectFromJson() throws Exception {
    HttpResponse response = this.getWebServiceDriver()
        .getContent("/api/XML_JSON/GetProduct/1", ContentType.APPLICATION_JSON, true);
    Product jsonProduct = WebServiceUtilities
        .getResponseBody(response, ContentType.APPLICATION_JSON, Product.class);

    Assert
        .assertNotNull(jsonProduct, "Response body did not deserialize object from json correctly");
  }

  @Test(groups = TestCategories.WEB_SERVICE)
  public void testGetResponseBodyAsObjectFromXml() throws Exception {
    HttpResponse response = this.getWebServiceDriver()
        .getContent("/api/XML_JSON/GetProduct/1", ContentType.APPLICATION_XML, true);
    Product xmlProduct = WebServiceUtilities
        .getResponseBody(response, ContentType.APPLICATION_XML, Product.class);

    Assert.assertNotNull(xmlProduct, "Response body did not deserialize object from xml correctly");
  }

  @Test(expectedExceptions = IllegalArgumentException.class, groups = TestCategories.WEB_SERVICE)
  public void testGetResponseBodyAsObjectFromNeitherXmlOrJson() throws Exception {
    HttpResponse response = this.getWebServiceDriver()
        .getContent("/api/XML_JSON/GetProduct/1", ContentType.APPLICATION_OCTET_STREAM, true);
    Product xmlProduct = WebServiceUtilities
        .getResponseBody(response, ContentType.APPLICATION_OCTET_STREAM, Product.class);

    Assert.fail("Exception was not thrown for attempting to deserialize json to an object");
  }

  @Test(groups = TestCategories.WEB_SERVICE)
  public void testDeserializeJson() throws Exception {
    HttpResponse response = this.getWebServiceDriver()
        .getContent("/api/XML_JSON/GetProduct/1", ContentType.APPLICATION_JSON, true);
    Product product = WebServiceUtilities.deserializeJson(response, Product.class);

    Assert.assertNotNull(product);
  }

  @Test(groups = TestCategories.WEB_SERVICE)
  public void testDeserializeXml() throws Exception {
    HttpResponse response = this.getWebServiceDriver()
        .getContent("/api/XML_JSON/GetProduct/1", ContentType.APPLICATION_XML, true);
    Product product = WebServiceUtilities.deserializeXml(response, Product.class);

    Assert.assertNotNull(product);
  }

  @Test(groups = TestCategories.WEB_SERVICE)
  public void testCreateStringEntityJson() throws JsonProcessingException {
    HttpEntity entity = WebServiceUtilities
        .createStringEntity(this.product, ContentType.APPLICATION_JSON);

    Assert
        .assertNotNull(entity, "string entity wasn't created using content type application/json");
  }

  @Test(groups = TestCategories.WEB_SERVICE)
  public void testCreateStringEntityXml() throws JsonProcessingException {
    HttpEntity entity = WebServiceUtilities
        .createStringEntity(this.product, ContentType.APPLICATION_XML);

    Assert.assertNotNull(entity, "string entity wasn't created using content type application/xml");
  }

  @Test(groups = TestCategories.WEB_SERVICE)
  public void testSerializeJson() throws JsonProcessingException {
    String expectedJson = "{\"Id\":1,\"Name\":\"Milk\",\"Category\":\"Dairy\",\"Price\":10}";
    String actualJson = WebServiceUtilities.serializeJson(this.product);

    Assert.assertEquals(expectedJson, actualJson, String
        .format("the json values compared aren't equal, expected was %s while actual was %s",
            expectedJson, actualJson));
  }

  @Test(groups = TestCategories.WEB_SERVICE)
  public void testSerializeXml() throws JsonProcessingException {
    String expectedXml = "<Product><Id>1</Id><Name>Milk</Name><Category>Dairy</Category><Price>10</Price></Product>";
    String actualXml = WebServiceUtilities.serializeXml(this.product);

    Assert.assertEquals(expectedXml, actualXml, String
        .format("the xml values compared aren't equal, expected was %s while actual was %s",
            expectedXml, actualXml));
  }

  @Test(expectedExceptions = IllegalArgumentException.class, groups = TestCategories.WEB_SERVICE)
  public void testCreateStringEntityNotJsonOrXml() throws JsonProcessingException {
    HttpEntity entity = WebServiceUtilities
        .createStringEntity(this.product, ContentType.APPLICATION_FORM_URLENCODED);

    Assert.fail(
        "Expected exception of IllegalArgumentException was not caught for content type that did not contain xml or json");
  }

  @Test(groups = TestCategories.WEB_SERVICE)
  public void testCreateStringEntityCustomContentType() throws JsonProcessingException {
    HttpEntity entity = WebServiceUtilities
        .createStringEntity(this.product, Charset.defaultCharset(), "application/json");

    Assert.assertNotNull(entity, "Entity was not set correctly with custom charset and mime-type");
  }
}
