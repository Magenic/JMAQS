package com.magenic.jmaqs.webservices.jdk8;

import com.magenic.jmaqs.utilities.helper.TestCategories;
import com.magenic.jmaqs.webservices.jdk8.models.Product;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;

public class WebServiceDriverPostUnitTest extends BaseWebServiceTest {

  @Test(groups = TestCategories.WEB_SERVICE)
  public void webServiceXmlPost() throws Exception {
    Product p = new Product();
    p.setCategory("ff");
    p.setId(4);
    p.setName("ff");
    p.setPrice(3.25);

    HttpEntity content = WebServiceUtilities.createStringEntity(p, ContentType.APPLICATION_XML);
    CloseableHttpResponse response = this.getWebServiceDriver()
        .putContent("/api/XML_JSON/Post/1", content, ContentType.APPLICATION_XML, true);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 405);
    Assert.assertEquals(response.getStatusLine().getReasonPhrase(), "Conflict");
  }

  @Test(groups = TestCategories.WEB_SERVICE)
  public void webServiceJsonPost() throws Exception {
    Product p = new Product();
    p.setCategory("ff");
    p.setId(4);
    p.setName("ff");
    p.setPrice(3.25);

    HttpEntity content = WebServiceUtilities.createStringEntity(p, ContentType.APPLICATION_JSON);
    CloseableHttpResponse response = this.getWebServiceDriver()
        .putContent("/api/XML_JSON/Post", content, ContentType.APPLICATION_JSON, true);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 405);
    Assert.assertEquals(response.getStatusLine().getReasonPhrase(), "Conflict");
  }

  @Test(groups = TestCategories.WEB_SERVICE)
  public void webServiceStringPost() throws Exception {
    Product p = new Product();
    p.setCategory("ff");
    p.setId(4);
    p.setName("ff");
    p.setPrice(3.25123);

    HttpEntity content = WebServiceUtilities.createStringEntity(p, ContentType.APPLICATION_XML);
    CloseableHttpResponse response = this.getWebServiceDriver()
        .putContent("/api/String/Post", content, ContentType.APPLICATION_XML, true);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 409);
    Assert.assertEquals(response.getStatusLine().getReasonPhrase(), "Conflict");
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
}
