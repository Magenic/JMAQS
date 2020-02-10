/*
 * Copyright 2020 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.base;

import com.magenic.jmaqs.utilities.helper.TestCategories;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DriverManagerUnitTest extends BaseGenericTest {

  @Test(groups = TestCategories.Framework)
  public void testGetBaseDriver() throws Exception {
    DriverManager driverManager = getDriverManager();
    driverManager.setBaseDriver("Fake String");
    Assert.assertNotNull(driverManager.getBaseDriver());

  }

  @Test(groups = TestCategories.Framework)
  public void testSetBaseDriver() {
    DriverManager driverManager = getDriverManager();
    driverManager.setBaseDriver("Fake String");
    Assert.assertNotNull(driverManager.getBaseDriver());
  }

  @Test(groups = TestCategories.Framework)
  public void testIsDriverInitializedTrue() {
    DriverManager driverManager = getDriverManager();
    Assert.assertNotNull(driverManager.getBase());
    Assert.assertTrue(driverManager.isDriverInitialized());
  }

  @Test(groups = TestCategories.Framework)
  public void testIsDriverInitializedFalse() {
    DriverManager driverManager = getDriverManager();
    Assert.assertFalse(driverManager.isDriverInitialized());
  }

  @Test(groups = TestCategories.Framework)
  public void testGetLogger() {
    DriverManager driverManager = getDriverManager();
    Assert.assertNotNull(driverManager.getLogger());
  }

  @Test(groups = TestCategories.Framework)
  public void testGetBase() {
    DriverManager driverManager = getDriverManager();
    Assert.assertNotNull(driverManager.getBase());
  }

  private DriverManager getDriverManager() {
    return new DriverManager(() -> "Fake String here", getTestObject()) {
      @Override
      public void close() throws Exception {
        this.close();
      }
    };
  }
}