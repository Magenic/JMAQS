/*
 * Copyright 2020 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.webservices.jdk8.soap;

import com.magenic.jmaqs.base.BaseExtendableTest;
import com.magenic.jmaqs.utilities.helper.StringProcessor;
import com.magenic.jmaqs.utilities.logging.Logger;
import com.magenic.jmaqs.webservices.jdk8.WebServiceConfig;
import com.magenic.jmaqs.webservices.jdk8.WebServiceTestObject;
import java.net.URI;
import java.net.URISyntaxException;
import javax.xml.soap.SOAPException;
import org.testng.ITestResult;

/**
 * Base web service test class.
 */
public class BaseSoapWebServiceTest extends BaseExtendableTest<WebServiceTestObject> {

  /**
   * Get the Web Service Driver.
   *
   * @return WebServiceDriver
   */
  public SoapWebServiceDriver getWebServiceDriver() {
    return (SoapWebServiceDriver) this.getTestObject().getWebServiceDriver();
  }

  /**
   * Set the webServiceDriver.
   *
   * @param webServiceDriver the webservice driver object
   */
  public void setWebServiceDriver(SoapWebServiceDriver webServiceDriver) {
    this.getTestObject().setWebServiceDriver(webServiceDriver);
  }

  /*
   * (non-Javadoc)
   *
   * @see com.magenic.jmaqs.utilities.BaseTest.BaseTest# beforeLoggingTeardown
   * (org.testng.ITestResult)
   */
  @Override protected void beforeLoggingTeardown(ITestResult resultType) {
    // There is no before logging tear-down required
  }

  /**
   * Gets new WebServiceDriver.
   *
   * @return WebServiceDriver
   * @throws URISyntaxException when URI is incorrect
   */
  protected SoapWebServiceDriver getWebServiceClient() throws URISyntaxException, SOAPException {
    return new SoapWebServiceDriver(new URI(WebServiceConfig.getWebServiceUri()));
  }

  /**
   * Creates a new test object.
   */
  @Override protected void createNewTestObject() {
    Logger logger = this.createLogger();
    try {

      WebServiceTestObject webServiceTestObject = new WebServiceTestObject(this.getWebServiceClient(), logger,
          this.getFullyQualifiedTestClassName());
      this.setTestObject(webServiceTestObject);
    } catch (URISyntaxException | SOAPException e) {
      getLogger().logMessage(StringProcessor.safeFormatter("Test Object could not be created: %s", e.getMessage()));
    }
  }
}
