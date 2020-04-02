package com.magenic.jmaqs.webservices.jdk11;

import com.magenic.jmaqs.base.BaseTestObject;
import com.magenic.jmaqs.base.DriverManager;
import java.net.http.HttpClient;
import java.util.function.Supplier;

/**
 * Web Service Driver Manager Class.
 */
public class WebServiceDriverManager extends DriverManager {
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
  public WebServiceDriverManager(Supplier<HttpClient> getDriverFunction,
      BaseTestObject baseTestObject) {
    super(getDriverFunction, baseTestObject);
    this.webServiceDriver = new WebServiceDriver(getDriverFunction.get());
  }

  /**
   * Instantiates a new Web Service Driver Manager.
   *
   * @param driver            Web Service Driver
   * @param baseTestObject    The Base Test Object.
   */
  public WebServiceDriverManager(WebServiceDriver driver, BaseTestObject baseTestObject) {
    super(() -> driver, baseTestObject);
    this.webServiceDriver = driver;
  }

  /**
   * Get the Web Service Driver.
   *
   * @return  The Web Service Driver.
   */
  public WebServiceDriver getWebServiceDriver() {
    // Create default Web Service Driver if null.
    if (this.webServiceDriver == null) {
      // If Base Driver Supplier returns Web Service Driver, use that Web Service Driver.
      if (getBase() instanceof WebServiceDriver) {
        this.webServiceDriver = (WebServiceDriver) getBase();
      } else {
        // If Base Driver Supplier returns CloseableHttpClient, use that to instantiate Web Service Driver.
        this.webServiceDriver = new WebServiceDriver((HttpClient) getBase());
      }
    }

    return this.webServiceDriver;
  }

  /**
   * Overrides the Web Service Driver.
   *
   * @param driver  Web Service Driver
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
