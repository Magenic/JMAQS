/*
 * Copyright 2020 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.webservices.jdk8;

import com.magenic.jmaqs.utilities.helper.TestCategories;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.URISyntaxException;

public class BaseWebServiceTestUnitTest extends BaseWebServiceTest {

  @Test(groups = TestCategories.WebService)
  public void testGetWebServiceDriver() {
    Assert.assertNotNull(this.getWebServiceDriver());
  }

  @Test(groups = TestCategories.WebService)
  public void testGetWebServiceTestObject() {
    Assert.assertNotNull(this.getTestObject());
  }

  @Test(groups = TestCategories.WebService)
  public void testSetWebServiceDriver() {
    int hashCode = this.getWebServiceDriver().hashCode();
    try {
      this.setWebServiceDriver(this.getWebServiceClient());
    } catch (Exception e) {
      e.printStackTrace();
    }
    int hashCode1 = this.getWebServiceDriver().hashCode();
    Assert.assertNotEquals(hashCode, hashCode1);
  }

  @Test(groups = TestCategories.WebService)
  public void testGetWebServiceClient() throws URISyntaxException {
    Assert.assertNotNull(this.getWebServiceClient());
  }
}