package com.magenic.jmaqs.webservices.jdk11;

import com.magenic.jmaqs.utilities.helper.TestCategories;
import com.magenic.jmaqs.webservices.jdk11.models.Product;
import com.magenic.jmaqs.webservices.jdk8.BaseWebServiceTest;
import com.magenic.jmaqs.webservices.jdk8.MediaType;
import com.magenic.jmaqs.webservices.jdk8.WebServiceConfig;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test web service gets.
 */
public class WebServiceDriverGetUnitTest extends BaseWebServiceTest {
  /**
   * String to hold the URL.
   */
  private static String url = WebServiceConfig.getWebServiceUri();

  /**
   * Test Json Get deserialize a single product.
   * @throws IOException if exception is thrown
   * @throws InterruptedException if exception is thrown
   * @throws URISyntaxException if exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void getProductXmlDeserialize() throws IOException, InterruptedException, URISyntaxException {
    WebServiceDriver client = new WebServiceDriver(url);
    HttpResponse<String> result = client.get("/api/XML_JSON/GetProduct/2", MediaType.APP_XML, false);
    Product products = WebServiceUtilities.getResponseBody(result, MediaType.APP_XML, Product.class);
    Assert.assertEquals(products.getName(), "Yo-yo", "Expected 3 products to be returned");
  }

  /**
   * Test XML get all items.
   * @throws IOException if exception is thrown
   * @throws InterruptedException if exception is thrown
   * @throws URISyntaxException if exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void getProductsXmlDeserialize() throws IOException, InterruptedException, URISyntaxException {
    WebServiceDriver client = new WebServiceDriver(url);
    HttpResponse<String> result = client.get("/api/XML_JSON/GetAllProducts", MediaType.APP_XML, false);
    Product[] products = WebServiceUtilities.getResponseBody(result, MediaType.APP_XML, Product[].class);
    Assert.assertEquals(products.length, 3, "Expected 3 products to be returned");
  }

  /**
   * Test Json Get deserialize a single product.
   * @throws IOException if exception is thrown
   * @throws URISyntaxException if exception is thrown
   * @throws InterruptedException if exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void getProductJsonDeserialize() throws IOException, URISyntaxException, InterruptedException {
    WebServiceDriver client = new WebServiceDriver(url);
    HttpResponse<String> response = client.get("/api/XML_JSON/GetProduct/2", MediaType.APP_JSON, false);
    Product products = WebServiceUtilities.getResponseBody(response, MediaType.APP_JSON, Product.class);
    Assert.assertEquals(products.getName(),"Yo-yo", "Expected 3 products to be returned");
  }

  /**
   * Test Json Get deserialize multiple products.
   * @throws IOException if exception is thrown
   * @throws URISyntaxException if exception is thrown
   * @throws InterruptedException if exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void getProductsJsonDeserialize() throws IOException, URISyntaxException, InterruptedException {
    WebServiceDriver client = new WebServiceDriver(url);
    HttpResponse<String> response = client.get("/api/XML_JSON/GetAllProducts", MediaType.APP_JSON, false);
    Product[] products = WebServiceUtilities.getResponseBody(response, MediaType.APP_JSON, Product[].class);
    Assert.assertEquals(products.length, 3, "Expected 3 products to be returned");
  }

  /**
   * Test string Get all.
   * @throws IOException if exception is thrown
   * @throws URISyntaxException if exception is thrown
   * @throws InterruptedException if exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void getProductsPlainText() throws IOException, URISyntaxException, InterruptedException {
    WebServiceDriver client = new WebServiceDriver(url);
    HttpResponse<String> result = client.get("/api/String/Get",  MediaType.PLAIN_TEXT, false);
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
   * @throws URISyntaxException if exception is thrown
   * @throws InterruptedException if exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void getStringById() throws IOException, URISyntaxException, InterruptedException {
    WebServiceDriver client = new WebServiceDriver(url);
    HttpResponse<String> result = client.get("/api/String/1",  MediaType.PLAIN_TEXT, false);
    Assert.assertTrue(result.body().contains("Tomato Soup"),
        "Was expecting a result with Tomato Soup but instead got - " + result);
  }

  /**
   * Test string Get by id.
   * @throws IOException if exception is thrown
   * @throws URISyntaxException if exception is thrown
   * @throws InterruptedException if exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void getStringByName() throws IOException, URISyntaxException, InterruptedException {
    WebServiceDriver client = new WebServiceDriver(url);
    HttpResponse<String> result = client.get("/api/String/Yo-yo",  MediaType.PLAIN_TEXT, false);
    Assert.assertTrue(result.body().contains("Yo-yo"),
        "Was expecting a result with Yo-yo but instead got - " + result);
  }

  /**
   * Test that we can use the web service utility to deserialize JSON.
   * @throws IOException if exception is thrown
   * @throws URISyntaxException if exception is thrown
   * @throws InterruptedException if exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void getResponseAndDeserializeJson()
      throws IOException, URISyntaxException, InterruptedException {
    WebServiceDriver client = new WebServiceDriver(url);
    HttpResponse<String> message = client.getWithResponse("/api/XML_JSON/GetAllProducts",
       MediaType.APP_JSON, true  );
    Product[] products = WebServiceUtilities.deserializeJson(message, Product[].class);
    Assert.assertEquals(products.length, 3, "Expected 3 products to be returned");
  }

  /**
   * Test that we can use the web service utility to deserialize XML.
   * @throws IOException if exception is thrown
   * @throws URISyntaxException if exception is thrown
   * @throws InterruptedException if exception is thrown
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void getResponseAndDeserializeXml()
      throws IOException, URISyntaxException, InterruptedException {
    WebServiceDriver client = new WebServiceDriver(url);
    HttpResponse<String> message = client.getWithResponse("/api/XML_JSON/GetAllProducts",
        MediaType.APP_XML, true);
    Product[] products = WebServiceUtilities.deserializeXml(message, Product[].class);
    Assert.assertEquals(products.length,3,"Expected 3 products to be returned");
  }
}