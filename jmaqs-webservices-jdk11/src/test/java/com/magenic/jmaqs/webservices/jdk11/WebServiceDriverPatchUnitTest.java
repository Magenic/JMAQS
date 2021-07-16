package com.magenic.jmaqs.webservices.jdk11;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.magenic.jmaqs.utilities.helper.TestCategories;
import com.magenic.jmaqs.webservices.jdk11.models.Product;
import com.magenic.jmaqs.webservices.jdk8.BaseWebServiceTest;
import com.magenic.jmaqs.webservices.jdk8.MediaType;
import com.magenic.jmaqs.webservices.jdk8.WebServiceConfig;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.http.HttpResponse;
import org.springframework.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test web service gets using the base test driver.
 */
public class WebServiceDriverPatchUnitTest extends BaseWebServiceTest {
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
   * Verify the string status code.
   * @throws JsonProcessingException if the exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void patchJSONSerializedVerifyStatusCode() throws IOException, InterruptedException {
    String content = WebServiceUtilities.createStringEntity(product, MediaType.APP_JSON);
    HttpResponse<String> result = webServiceDriver.patch(
        baseUrl + "/api/XML_JSON/Patch/1", MediaType.APP_JSON, content, true);
    Assert.assertEquals(result.statusCode(), HttpStatus.OK.value());
  }

  /**
   * Patch With JSON Type.
   * @throws IOException if the exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void patchJSONWithType() throws IOException, InterruptedException {
    String content = WebServiceUtilities.createStringEntity(product, MediaType.APP_JSON);
    Product result = webServiceDriver.patch(baseUrl + "/api/XML_JSON/Patch/1",
        MediaType.APP_JSON, content, Product.class, true);

    Assert.assertEquals(product.getCategory(), result.getCategory());
    Assert.assertEquals(product.getId(), result.getId());
    Assert.assertEquals(product.getName(), result.getName());
    Assert.assertEquals(product.getPrice(), result.getPrice());
  }

  /**
   * Verify the stream status code.
   * @throws JsonProcessingException if the exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void patchJSONStreamSerializedVerifyStatusCode() throws IOException, InterruptedException {
    String content = WebServiceUtilities.createStringEntity(product, MediaType.APP_JSON);
    HttpResponse<String> result = webServiceDriver.patch(
        baseUrl + "/api/XML_JSON/Patch/1", MediaType.APP_JSON, content, true);
    Assert.assertEquals(result.statusCode(), HttpStatus.OK.value());
  }

  /**
   * XML string verify status code.
   * @throws JsonProcessingException if the exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void patchXMLSerializedVerifyStatusCode() throws IOException, InterruptedException {
    String content = WebServiceUtilities.createStringEntity(product, MediaType.APP_XML);
    HttpResponse<String> result = webServiceDriver.patch(
        baseUrl + "/api/XML_JSON/Patch/1", MediaType.APP_XML, content, true);
    Assert.assertEquals(result.statusCode(), HttpStatus.OK.value());
  }

  /**
   * XML stream verify status code.
   * @throws JsonProcessingException if the exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void patchXMLStreamSerializedVerifyStatusCode() throws IOException, InterruptedException {
    String content = WebServiceUtilities.createStringEntity(product, MediaType.APP_XML);
    HttpResponse<String> result = webServiceDriver.patch(
        baseUrl + "/api/XML_JSON/Patch/1", MediaType.APP_XML, content, true);
    Assert.assertEquals(result.statusCode(), HttpStatus.OK.value());
  }

  /**
   * Verify put returns an empty string.
   * @throws IOException if the exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void patchXMLWithType() throws IOException, InterruptedException {
    String content = WebServiceUtilities.createStringEntity(product, MediaType.APP_XML);
    Product result = webServiceDriver.patch(baseUrl + "/api/XML_JSON/Patch/1",
        MediaType.APP_XML, content, Product.class, true);
    Assert.assertEquals(result.getCategory(), product.getCategory());
    Assert.assertEquals(product.getId(), result.getId());
    Assert.assertEquals(product.getName(), result.getName());
    Assert.assertEquals(product.getPrice(), result.getPrice());
  }

  /**
   * Patch string without utility.
   * @throws IOException if the exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void patchStringWithoutMakeContent() throws IOException, InterruptedException {
    HttpResponse<String> result = webServiceDriver.patch(baseUrl + "/api/String/Patch/1",
        MediaType.PLAIN_TEXT, "Test", true);
    Assert.assertEquals(result.body(), "\"Patched\"");
  }

  /**
   * Patch string with utility.
   * @throws JsonProcessingException if the exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void patchStringWithMakeContent()
      throws IOException, InterruptedException {
    String content = WebServiceUtilities.createStringEntity("Test", MediaType.PLAIN_TEXT);
    HttpResponse<String> result = webServiceDriver.patch(baseUrl + "/api/String/Patch/1",
        MediaType.PLAIN_TEXT, content, true);
    Assert.assertEquals(result.body(), "\"Patched\"");
  }

  /**
   * Patch string without utility to verify status code.
   * @throws IOException if the exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void patchStringWithoutContentStatusCode()
      throws IOException, InterruptedException {
    HttpResponse<String> result = webServiceDriver.patch(
        baseUrl + "/api/String/Patch/1", MediaType.PLAIN_TEXT, "Test", true);
    Assert.assertEquals(result.statusCode(), HttpStatus.OK.value());
  }

  /**
   * Patch string with utility to verify status code.
   * @throws JsonProcessingException if the exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void patchStringMakeContentStatusCode()
      throws IOException, InterruptedException {
    String content = WebServiceUtilities.createStringEntity("Test", MediaType.PLAIN_TEXT);
    HttpResponse<String> result = webServiceDriver.patch(
        baseUrl + "/api/String/Patch/1", MediaType.PLAIN_TEXT, content, true);
    Assert.assertEquals(result.statusCode(), HttpStatus.OK.value());
  }

  /**
   * Verifying other http status codes.
   */
  @Test(groups = TestCategories.WEB_SERVICE, expectedExceptions = NullPointerException.class)
  public void patchExpectContentError() throws IOException,
      InterruptedException {
   HttpResponse<String> result = webServiceDriver.patch(
        baseUrl + "/api/String/Patch/1", MediaType.PLAIN_TEXT, null, false);
    Assert.assertEquals(result.statusCode(), HttpStatus.BAD_REQUEST.value());
  }

  /**
   * Testing string returned for Patch.
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void patchExpectStringError() throws IOException, InterruptedException {
    HttpResponse<String> result = webServiceDriver.patch(baseUrl + "/api/String/Patch/",
        MediaType.PLAIN_TEXT, "", false);
    String expected = "{\"Message\":\"The requested resource does not support http method 'PATCH'.\"}";
    Assert.assertEquals(result.body(), expected);
  }
  
  /**
   * Test type parameterized Patch request with expected status.
   * @throws IOException if an exception is thrown
   * @throws InterruptedException if an exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void patchTypeParamWithExpectedStatus() throws IOException, InterruptedException {
    String req = WebServiceUtilities.createStringEntity(product, MediaType.APP_JSON);
    Product res = webServiceDriver.patch(baseUrl +"/api/XML_JSON/Patch/1",
        MediaType.APP_JSON, req, Product.class, HttpStatus.OK);
    Assert.assertNotNull(res);
  }

  /**
   * Test Patch request with expected status.
   * @throws IOException if an exception is thrown
   * @throws InterruptedException if an exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void patchWithExpectedStatus() throws IOException, InterruptedException {
    String req = WebServiceUtilities.createStringEntity(product, MediaType.APP_JSON);
    HttpResponse<String> res = webServiceDriver.patch(
        baseUrl+ "/api/XML_JSON/Patch/1", MediaType.APP_JSON, req, HttpStatus.OK);
    Assert.assertNotNull(res);
  }

  /**
   * Test more params Patch with response request with expected status.
   * @throws IOException if an exception is thrown
   * @throws InterruptedException if an exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void patchMoreParamsWithResponseWithExpectedStatus() throws IOException, InterruptedException {
    HttpResponse<String> res = webServiceDriver.patch(baseUrl + "/api/XML_JSON/Patch/1",
        MediaType.APP_JSON, WebServiceUtilities.serializeJson(product), HttpStatus.OK);
    Assert.assertNotNull(res);
  }

  /**
   * Test Patch with response request with expected status.
   * @throws IOException if an exception is thrown
   * @throws InterruptedException if an exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void patchWithResponseWithExpectedStatus() throws IOException, InterruptedException {
    String req = WebServiceUtilities.createStringEntity(product, MediaType.APP_JSON);
    HttpResponse<String> res = webServiceDriver.patch(
        baseUrl + "/api/XML_JSON/Patch/1", MediaType.APP_JSON, req, HttpStatus.OK);
    Assert.assertNotNull(res);
  }
}
