/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.webservices.jdk8;

import com.magenic.jmaqs.base.BaseGenericTest;
import com.magenic.jmaqs.utilities.helper.TestCategories;
import java.net.URISyntaxException;
import java.util.function.Supplier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Tests the Web Service Test Object functionality.
 */
public class WebServiceTestObjectUnitTest extends BaseGenericTest {

  /**
   * Test object creation with driver.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void testWebServiceTestObjectCreationWithDriver() throws URISyntaxException {
    WebServiceDriver defaultBrowser = getWebServiceDriver();
    WebServiceTestObject testObject = new WebServiceTestObject(defaultBrowser, this.getLogger(),
        this.getFullyQualifiedTestClassName());
    Assert.assertNotNull(testObject, "Checking that the Web service test object via driver is not null");
  }

  /**
   * Test object creation with supplier.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void testWebServiceTestObjectCreationWithSupplier() {
    Supplier<CloseableHttpClient> httpClientSupplier = getCloseableHttpClientSupplier();
    WebServiceTestObject testObject = new WebServiceTestObject(httpClientSupplier, this.getLogger(),
        this.getFullyQualifiedTestClassName());
    Assert.assertNotNull(testObject, "Checking that the Web service test object via driver is not null");
  }

  /**
   * tests getting the web service driver.
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void testGetWebServiceDriver() throws URISyntaxException {
    WebServiceDriver testObject = getWebServiceDriver();
    Assert.assertNotNull(testObject, "Checking that the Web service test object via driver is null");
  }

  /**
   * tests getting the web service driver manager.
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void testGetWebServiceDriverManager() throws URISyntaxException {
    WebServiceDriver defaultBrowser = getWebServiceDriver();
    try (WebServiceTestObject testObject = new WebServiceTestObject(defaultBrowser, this.getLogger(),
        this.getFullyQualifiedTestClassName())) {
      Assert.assertNotNull(testObject.getWebServiceDriverManager(),
          "Checking that the Web service driver manager is null");
    }
  }

  /**
   * tests overriding the web service driver with a web service driver.
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void testSetWebServiceDriverWithDriver() throws URISyntaxException {
    WebServiceDriver serviceDriver = getWebServiceDriver();
    try (WebServiceTestObject testObject = new WebServiceTestObject(serviceDriver, this.getLogger(),
        this.getFullyQualifiedTestClassName())) {
      testObject.setWebServiceDriver(serviceDriver);
      Assert.assertNotNull(testObject.getWebServiceDriver(), "the web service driver is null");
      int hashCode = testObject.getWebServiceDriver().hashCode();
      testObject.setWebServiceDriver(getWebServiceDriver());
      int hashCode1 = testObject.getWebServiceDriver().hashCode();
      Assert.assertNotEquals(hashCode, hashCode1);
    }
  }

  /**
   * tests overriding the web service driver with a closeable http client.
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void testSetWebServiceDriverWithHttpClient() throws URISyntaxException {
    WebServiceDriver serviceDriver = getWebServiceDriver();
    CloseableHttpClient client = HttpClientBuilder.create().build();
    try (WebServiceTestObject testObject = new WebServiceTestObject(serviceDriver, this.getLogger(),
        this.getFullyQualifiedTestClassName())) {
      testObject.setWebServiceDriver(client);
      Assert.assertNotNull(testObject.getWebServiceDriver(), "the web service driver is null");
      int hashCode = testObject.getWebServiceDriver().hashCode();
      testObject.setWebServiceDriver(client);
      int hashCode1 = testObject.getWebServiceDriver().hashCode();
      Assert.assertNotEquals(hashCode, hashCode1);
    }
  }

  /**
   * tests overriding the web service driver with a supplier.
   */
  @Test(groups = TestCategories.WEB_SERVICE)
  public void testSetWebServiceDriverSupplier() {
    Supplier<CloseableHttpClient> httpClientSupplier = getCloseableHttpClientSupplier();
    try (WebServiceTestObject testObject = new WebServiceTestObject(httpClientSupplier, this.getLogger(),
        this.getFullyQualifiedTestClassName())) {
      Assert.assertNotNull(testObject.getWebServiceDriver(), "the web service driver is null");
      int hashCode = testObject.getWebServiceDriver().hashCode();
      testObject.setWebServiceDriver(getCloseableHttpClientSupplier());
      int hashCode1 = testObject.getWebServiceDriver().hashCode();
      Assert.assertNotEquals(hashCode, hashCode1);
    }
  }

  /**
   * gets a Closeable Http client supplier.
   *
   * @return a Closeable http client supplier.
   */
  private Supplier<CloseableHttpClient> getCloseableHttpClientSupplier() {
    CloseableHttpClient client = HttpClientBuilder.create().build();
    return () -> client;
  }

  /**
   * gets the web service driver via the config uri.
   *
   * @return a web service driver.
   * @throws URISyntaxException if URI is invalid.
   */
  private WebServiceDriver getWebServiceDriver() throws URISyntaxException {
    return new WebServiceDriver(WebServiceConfig.getWebServiceUri());
  }
}
