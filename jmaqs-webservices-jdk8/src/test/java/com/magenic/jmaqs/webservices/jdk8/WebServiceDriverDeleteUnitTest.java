package com.magenic.jmaqs.webservices.jdk8;

import com.magenic.jmaqs.utilities.helper.TestCategories;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;

public class WebServiceDriverDeleteUnitTest extends BaseWebServiceTest{
  /**
   * Verify delete works.
   *
   * @throws Exception
   *           There was a problem with the test
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void webServiceDelete() throws Exception {

    CloseableHttpResponse response = this.getWebServiceDriver()
        .deleteContent("/api/XML_JSON/Delete/1", ContentType.TEXT_PLAIN, false);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
  }

  /**
   * Verify delete works.
   *
   * @throws Exception
   *           There was a problem with the test
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void webServiceStringDelete() throws Exception {

    CloseableHttpResponse response = this.getWebServiceDriver()
        .deleteContent("/api/String/Delete/1", ContentType.TEXT_PLAIN, false);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
  }
}
