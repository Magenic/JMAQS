package com.magenic.jmaqs.webservices.jdk8;

import com.magenic.jmaqs.utilities.helper.TestCategories;
import com.magenic.jmaqs.webservices.jdk8.models.Product;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;

public class WebServiceDriverPatchUnitTest extends BaseWebServiceTest {

  @Test(groups = TestCategories.WEB_SERVICE)
  public void webServiceStringPatch() throws Exception {
    Product p = new Product();
    p.setCategory("ff");
    p.setId(4);
    p.setName("ff");
    p.setPrice(3.25);

    HttpEntity content = WebServiceUtilities.createStringEntity(p, ContentType.APPLICATION_JSON);
    CloseableHttpResponse response = this.getWebServiceDriver()
        .putContent("/api/String/Patch/2", content, ContentType.TEXT_PLAIN, true);

    Assert.assertEquals(response.getStatusLine().getStatusCode(), 405);
    Assert.assertEquals(response.getStatusLine().getReasonPhrase(), "Patched");
  }


  @Test(groups = TestCategories.WEB_SERVICE)
  public void webServiceXmlPatch() throws Exception {
    Product p = new Product();
    p.setCategory("ff");
    p.setId(4);
    p.setName("ff");
    p.setPrice(3.25);

    HttpEntity content = WebServiceUtilities.createStringEntity(p, ContentType.APPLICATION_XML);
    CloseableHttpResponse response = this.getWebServiceDriver()
        .putContent("/api/XML_JSON/Patch/2", content, ContentType.APPLICATION_XML, true);

    Assert.assertEquals(response.getStatusLine().getStatusCode(), 405);
    Assert.assertEquals(response.getStatusLine().getReasonPhrase(), "Patched");
  }

  @Test(groups = TestCategories.WEB_SERVICE)
  public void webServiceJsonPatch() throws Exception {
    Product p = new Product();
    p.setCategory("ff");
    p.setId(4);
    p.setName("ff");
    p.setPrice(3.25123);

    HttpEntity content = WebServiceUtilities.createStringEntity(p, ContentType.APPLICATION_JSON);
    CloseableHttpResponse response = this.getWebServiceDriver()
        .putContent("/api/XML_JSON/Patch/2", content, ContentType.APPLICATION_JSON, true);

    Assert.assertEquals(response.getStatusLine().getStatusCode(), 405);
    Assert.assertEquals(response.getStatusLine().getReasonPhrase(), "Patched");
  }

  /**
   * Verify a patch error returns the expected code and message.
   *
   * @throws Exception
   *           There was a problem with the test
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void webServicePatchError() throws Exception {
    HttpEntity content = WebServiceUtilities.createStringEntity("", ContentType.APPLICATION_XML);
    CloseableHttpResponse response = this.getWebServiceDriver()
        .patchContent("/api/XML_JSON/Put/1", content, ContentType.APPLICATION_XML, false);

    Assert.assertEquals(response.getStatusLine().getStatusCode(), 405);
    Assert.assertEquals(response.getStatusLine().getReasonPhrase(), "Method Not Allowed");
  }
}
