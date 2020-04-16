package com.magenic.jmaqs.webservices.jdk11;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.magenic.jmaqs.utilities.helper.TestCategories;
import com.magenic.jmaqs.webservices.jdk11.models.Product;
import com.magenic.jmaqs.webservices.jdk8.BaseWebServiceTest;
import com.magenic.jmaqs.webservices.jdk8.MediaType;
import com.magenic.jmaqs.webservices.jdk8.WebServiceConfig;
import java.io.IOException;
import java.net.URISyntaxException;
import org.springframework.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test web service gets using the base test driver.
 */
public class WebServiceDriverPatchUnitTest extends BaseWebServiceTest {
  /**
   * Verify the string status code.
   * @throws JsonProcessingException if the exception is thrown
   * @throws URISyntaxException if the exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void patchJSONSerializedVerifyStatusCode()
      throws IOException, URISyntaxException, InterruptedException {
    Product p = new Product();
    p.setCategory("ff");
    p.setId(4);
    p.setName("ff");
    p.setPrice(3.25);
    var content = WebServiceUtilities.makeStringContent(p, MediaType.APP_JSON);
    WebServiceDriver webServiceDriver = new WebServiceDriver(WebServiceConfig.getWebServiceUri());
    var result = webServiceDriver.patchWithResponse("/api/XML_JSON/Patch/1", MediaType.APP_JSON, content, true);
    Assert.assertEquals(result.statusCode(), HttpStatus.OK.value());
  }

  /**
   * Patch With JSON Type.
   * @throws IOException if the exception is thrown
   * @throws URISyntaxException if the exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void patchJSONWithType() throws IOException, URISyntaxException, InterruptedException {
    Product p = new Product();
    p.setCategory("ff");
    p.setId(4);
    p.setName("ff");
    p.setPrice(3.25);
    var content = WebServiceUtilities.makeStringContent(p, MediaType.APP_JSON);
    WebServiceDriver webServiceDriver = new WebServiceDriver(WebServiceConfig.getWebServiceUri());
    Product result = webServiceDriver.patch("/api/XML_JSON/Patch/1", MediaType.APP_JSON, content, Product.class, true);

    Assert.assertEquals(p.getCategory(), result.getCategory());
    Assert.assertEquals(p.getId(), result.getId());
    Assert.assertEquals(p.getName(), result.getName());
    Assert.assertEquals(p.getPrice(), result.getPrice());
  }

  /**
   * Verify the stream status code.
   * @throws JsonProcessingException if the exception is thrown
   * @throws URISyntaxException if the exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void patchJSONStreamSerializedVerifyStatusCode()
      throws IOException, URISyntaxException, InterruptedException {
    Product p = new Product();
    p.setCategory("ff");
    p.setId(4);
    p.setName("ff");
    p.setPrice(3.25);
    var content = WebServiceUtilities.makeStringContent(p, MediaType.APP_JSON);
    WebServiceDriver webServiceDriver = new WebServiceDriver(WebServiceConfig.getWebServiceUri());
    var result = webServiceDriver.patchWithResponse("/api/XML_JSON/Patch/1", MediaType.APP_JSON, content, true);
    Assert.assertEquals(result.statusCode(), HttpStatus.OK.value());
  }

  /**
   * XML string verify status code.
   * @throws JsonProcessingException if the exception is thrown
   * @throws URISyntaxException if the exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void patchXMLSerializedVerifyStatusCode()
      throws IOException, URISyntaxException, InterruptedException {
    Product p = new Product();
    p.setCategory("ff");
    p.setId(4);
    p.setName("ff");
    p.setPrice(3.25);
    var content = WebServiceUtilities.makeStringContent(p, MediaType.APP_XML);
    WebServiceDriver webServiceDriver = new WebServiceDriver(WebServiceConfig.getWebServiceUri());
    var result = webServiceDriver.patchWithResponse("/api/XML_JSON/Patch/1", MediaType.APP_XML, content, true);
    Assert.assertEquals(result.statusCode(), HttpStatus.OK.value());
  }

  /**
   * XML stream verify status code.
   * @throws JsonProcessingException if the exception is thrown
   * @throws URISyntaxException if the exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void patchXMLStreamSerializedVerifyStatusCode()
      throws IOException, URISyntaxException, InterruptedException {
    Product p = new Product();
    p.setCategory("ff");
    p.setId(4);
    p.setName("ff");
    p.setPrice(3.25);
    var content = WebServiceUtilities.makeStringContent(p, MediaType.APP_XML);
    WebServiceDriver webServiceDriver = new WebServiceDriver(WebServiceConfig.getWebServiceUri());
    var result = webServiceDriver.patchWithResponse("/api/XML_JSON/Patch/1", MediaType.APP_XML, content, true);
    Assert.assertEquals(result.statusCode(), HttpStatus.OK.value());
  }

  /**
   * Verify put returns an empty string.
   * @throws IOException if the exception is thrown
   * @throws URISyntaxException if the exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void patchXMLWithType() throws IOException, URISyntaxException, InterruptedException {
    Product p = new Product();
    p.setCategory("ff");
    p.setId(4);
    p.setName("ff");
    p.setPrice(3.25);

    var content = WebServiceUtilities.makeStringContent(p, MediaType.APP_XML);
    WebServiceDriver webServiceDriver = new WebServiceDriver(WebServiceConfig.getWebServiceUri());
    Product result = webServiceDriver.patch("/api/XML_JSON/Patch/1", MediaType.APP_XML, content, Product.class, true);
    Assert.assertEquals(result.getCategory(), p.getCategory());
    Assert.assertEquals(p.getId(), result.getId());
    Assert.assertEquals(p.getName(), result.getName());
    Assert.assertEquals(p.getPrice(), result.getPrice());
  }

  /**
   * Patch string without utility.
   * @throws URISyntaxException if the exception is thrown
   * @throws IOException if the exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void patchStringWithoutMakeContent()
      throws URISyntaxException, IOException, InterruptedException {
    WebServiceDriver webServiceDriver = new WebServiceDriver(WebServiceConfig.getWebServiceUri());
    var result = webServiceDriver.patch("/api/String/Patch/1", MediaType.PLAIN_TEXT,
        "Test", MediaType.PLAIN_TEXT, true, true);
    Assert.assertEquals(result, "\"Patched\"");
  }

  /**
   * Patch string with utility.
   * @throws JsonProcessingException if the exception is thrown
   * @throws URISyntaxException if the exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void patchStringWithMakeContent()
      throws IOException, URISyntaxException, InterruptedException {
    var content = WebServiceUtilities.makeStringContent("Test", MediaType.PLAIN_TEXT);
    WebServiceDriver webServiceDriver = new WebServiceDriver(WebServiceConfig.getWebServiceUri());
    var result = webServiceDriver.patch("/api/String/Patch/1", MediaType.PLAIN_TEXT, content, true);
    Assert.assertEquals(result, "\"Patched\"");
  }

  /**
   * Patch string without utility to verify status code.
   * @throws URISyntaxException if the exception is thrown
   * @throws IOException if the exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void patchStringWithoutContentStatusCode()
      throws URISyntaxException, IOException, InterruptedException {
    WebServiceDriver webServiceDriver = new WebServiceDriver(WebServiceConfig.getWebServiceUri());
    var result = webServiceDriver.patchWithResponse("/api/String/Patch/1", MediaType.PLAIN_TEXT,
        "Test", MediaType.PLAIN_TEXT, true, true);
    Assert.assertEquals(result.statusCode(), HttpStatus.OK.value());
  }

  /**
   * Patch string with utility to verify status code.
   * @throws URISyntaxException if the exception is thrown
   * @throws JsonProcessingException if the exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void patchStringMakeContentStatusCode()
      throws URISyntaxException, IOException, InterruptedException {
    var content = WebServiceUtilities.makeStringContent("Test", MediaType.PLAIN_TEXT);
    WebServiceDriver webServiceDriver = new WebServiceDriver(WebServiceConfig.getWebServiceUri());
    var result = webServiceDriver.patchWithResponse("/api/String/Patch/1", MediaType.PLAIN_TEXT, content, true);
    Assert.assertEquals(result.statusCode(), HttpStatus.OK.value());
  }

  /**
   * Verifying other http status codes.
   * @throws URISyntaxException if the exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void patchExpectContentError() throws URISyntaxException, IOException,
      InterruptedException {
    WebServiceDriver webServiceDriver = new WebServiceDriver(WebServiceConfig.getWebServiceUri());
    var result = webServiceDriver.patchWithResponse("/api/String/Patch/1", MediaType.PLAIN_TEXT, "", false);
    Assert.assertEquals(result.statusCode(), HttpStatus.BAD_REQUEST.value());
  }

  /**
   * Testing string returned for Patch.
   * @throws URISyntaxException if the exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void patchExpectStringError() throws URISyntaxException, IOException,
      InterruptedException {
    WebServiceDriver webServiceDriver = new WebServiceDriver(WebServiceConfig.getWebServiceUri());
    var result = webServiceDriver.patch("/api/String/Patch/", MediaType.PLAIN_TEXT, "", false);
    var expected = "{\"Message\":\"Value is required\"}";
    Assert.assertEquals(result, expected);
  }
}
