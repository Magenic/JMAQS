package com.company.automation.test;

import com.google.gson.Gson;
import com.magenic.jmaqs.webservices.BaseWebServiceTest;
import com.magenic.jmaqs.webservices.WebServiceUtils;
import com.company.automation.models.ProductJson;
import org.apache.http.entity.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * The type Web service test.
 */
public class WebServiceTest extends BaseWebServiceTest {

  /**
   * Gets json deserialized Test.
   */
  @Test public void getJsonDeserialized() {
    String result = "";
    try {
      result = WebServiceUtils.getResponseBody(this.getWebServiceTestObject().getWebServiceWrapper()
          .getContent("/api/XML_JSON/GetProduct/1", ContentType.APPLICATION_JSON, false));
    } catch (Exception e) {
      e.printStackTrace();
    }

    ProductJson productJson = new Gson().fromJson(result, ProductJson.class);
    Assert.assertEquals(productJson.getId(), 1, "Expected to get product 1");
  }
}
