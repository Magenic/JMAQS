/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.base;

import com.magenic.jmaqs.utilities.helper.TestCategories;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DriverManagerUnitTest extends BaseGenericTest {

  @Test(groups = TestCategories.FRAMEWORK)
  public void testGetBaseDriver() throws Exception {
    DriverManager<String> driverManager = getDriverManager();
    driverManager.setBaseDriver("Fake String");
    Assert.assertNotNull(driverManager.getBaseDriver());

  }

  @Test(groups = TestCategories.FRAMEWORK)
  public void testSetBaseDriver() {
    DriverManager<String> driverManager = getDriverManager();
    driverManager.setBaseDriver("Fake String");
    Assert.assertNotNull(driverManager.getBaseDriver());
  }

  @Test(groups = TestCategories.FRAMEWORK)
  public void testIsDriverInitializedTrue() {
    DriverManager<String> driverManager = getDriverManager();
    Assert.assertNotNull(driverManager.getBase());
    Assert.assertTrue(driverManager.isDriverInitialized());
  }

  @Test(groups = TestCategories.FRAMEWORK)
  public void testIsDriverInitializedFalse() {
    DriverManager<String> driverManager = getDriverManager();
    Assert.assertFalse(driverManager.isDriverInitialized());
  }

  @Test(groups = TestCategories.FRAMEWORK)
  public void testGetLogger() {
    DriverManager<String> driverManager = getDriverManager();
    Assert.assertNotNull(driverManager.getLogger());
  }

  @Test(groups = TestCategories.FRAMEWORK)
  public void testGetBase() {
    DriverManager<String> driverManager = getDriverManager();
    Assert.assertNotNull(driverManager.getBase());
  }

  private DriverManager<String> getDriverManager() {
    return new DriverManager<String>(() -> "Fake String here", getTestObject()) {
      @Override
      public void close() throws Exception {
        this.close();
      }
    };
  }
}