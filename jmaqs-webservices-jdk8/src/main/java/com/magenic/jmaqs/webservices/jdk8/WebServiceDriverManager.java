/*
 * Copyright 2020 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.webservices.jdk8;

import com.magenic.jmaqs.base.BaseTestObject;
import com.magenic.jmaqs.base.DriverManager;
import java.util.function.Supplier;
import org.apache.http.impl.client.CloseableHttpClient;

/**
 * Web Service Driver Manager Class.
 */
public class WebServiceDriverManager extends DriverManager<CloseableHttpClient> {
  /**
   * Web Service Driver variable.
   */
  private WebServiceDriver webServiceDriver;

  /**
   * Instantiates a new Web Service Driver Manager.
   *
   * @param getDriverFunction Function that specifies how to get the driver.
   * @param baseTestObject    The Base Test Object.
   */
  public WebServiceDriverManager(Supplier<CloseableHttpClient> getDriverFunction, BaseTestObject baseTestObject) {
    super(getDriverFunction, baseTestObject);
  }

  /**
   * Instantiates a new Web Service Driver Manager.
   *
   * @param driver         Web Service Driver
   * @param baseTestObject The Base Test Object.
   */
  public WebServiceDriverManager(WebServiceDriver driver, BaseTestObject baseTestObject) {
    super(driver::getHttpClient, baseTestObject);
    this.webServiceDriver = driver;
  }

  /**
   * Get the Web Service Driver.
   *
   * @return The Web Service Driver.
   */
  public WebServiceDriver getWebServiceDriver() {
    // Create default Web Service Driver if null.
    if (this.webServiceDriver == null) {
      this.webServiceDriver = new WebServiceDriver(getBase());
    }

    return this.webServiceDriver;
  }

  /**
   * Overrides the Web Service Driver.
   *
   * @param driver Web Service Driver
   */
  public void overrideDriver(WebServiceDriver driver) {
    this.webServiceDriver = driver;
  }

  /**
   * Close Method sets Base Driver to Null.
   */
  public void close() {
    this.setBaseDriver(null);
  }
}
