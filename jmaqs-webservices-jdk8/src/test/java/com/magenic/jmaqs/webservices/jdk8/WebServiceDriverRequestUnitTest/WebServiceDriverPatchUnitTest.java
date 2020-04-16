package com.magenic.jmaqs.webservices.jdk8.WebServiceDriverRequestUnitTest;

import com.magenic.jmaqs.utilities.helper.TestCategories;
import com.magenic.jmaqs.webservices.jdk8.BaseWebServiceTest;
import com.magenic.jmaqs.webservices.jdk8.WebServiceUtilities;
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
    p.setCategory("gg");

    HttpEntity content = WebServiceUtilities.createStringEntity(p, ContentType.APPLICATION_JSON);
    CloseableHttpResponse response = this.getWebServiceDriver()
        .patchContent("/api/String/Patch/1", content, ContentType.TEXT_PLAIN, true);

    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    Assert.assertEquals(response.getStatusLine().getReasonPhrase(), "OK");
  }


  @Test(groups = TestCategories.WEB_SERVICE)
  public void webServiceXmlPatch() throws Exception {
    Product p = new Product();
    p.setCategory("gg");

    HttpEntity content = WebServiceUtilities.createStringEntity(p, ContentType.APPLICATION_XML);
    CloseableHttpResponse response = this.getWebServiceDriver()
        .patchContent("/api/XML_JSON/Patch/1", content, ContentType.APPLICATION_XML, true);

    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    Assert.assertEquals(response.getStatusLine().getReasonPhrase(), "OK");
  }

  @Test(groups = TestCategories.WEB_SERVICE)
  public void webServiceJsonPatch() throws Exception {
    Product p = new Product();
    p.setCategory("gg");

    HttpEntity content = WebServiceUtilities.createStringEntity(p, ContentType.APPLICATION_JSON);
    CloseableHttpResponse response = this.getWebServiceDriver()
        .patchContent("/api/XML_JSON/Patch/1", content, ContentType.APPLICATION_JSON, true);

    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    Assert.assertEquals(response.getStatusLine().getReasonPhrase(), "OK");
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
