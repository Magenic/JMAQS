/*
 * Copyright 2019 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.base;

import static org.testng.Assert.assertNotNull;

import com.magenic.jmaqs.utilities.helper.TestCategories;
import java.util.function.Supplier;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.Test;

/**
 * Manager Dictionary Unit Tests.
 */
public class ManagerDictionaryUnitTest extends BaseGenericTest {

  @Test(groups = TestCategories.Framework)
  public void testClose() {

    ManagerDictionary managerDictionary = new ManagerDictionary();
    final String dm1 = "DM1";
    managerDictionary.put(dm1, getTestDriverManager());
    final String dm2 = "DM2";
    managerDictionary.put(dm2, getTestDriverManager());
    final String dm3 = "DM3";
    managerDictionary.put(dm3, getTestDriverManager());

    try {
      managerDictionary.close();
      Assert.assertFalse(managerDictionary.containsKey(dm1));
      Assert.assertFalse(managerDictionary.containsKey(dm2));
      Assert.assertFalse(managerDictionary.containsKey(dm3));
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  @Test(groups = TestCategories.Framework)
  public void testGetDriver() {
    final String dm1 = "DM1";
    ManagerDictionary managerDictionary = new ManagerDictionary();
    managerDictionary.putOrOverride(dm1, getTestDriverManager());
    Assert.assertTrue(managerDictionary.containsKey(dm1));
    assertNotNull(managerDictionary.getDriver(dm1));
  }

  @Test(groups = TestCategories.Framework)
  public void testPut() {
    final String dm1 = "DM1";
    ManagerDictionary managerDictionary = new ManagerDictionary();
    managerDictionary.put(dm1, getTestDriverManager());
    Assert.assertTrue(managerDictionary.containsKey(dm1));
    assertNotNull(managerDictionary.get(dm1));
  }

  private TestDriverManager getTestDriverManager() {
    Supplier<Object> supplier = () -> null;
    return new TestDriverManager(supplier, getTestObject());
  }

  @Test(groups = TestCategories.Framework)
  public void testPutOrOverride() {
    ManagerDictionary managerDictionary = new ManagerDictionary();
    TestDriverManager testManager = getTestDriverManager();
    managerDictionary.putOrOverride(testManager);
    Assert.assertTrue(managerDictionary.containsValue(testManager));
  }

  @Test(groups = TestCategories.Framework)
  public void testPutOrOverride1() {
    final String dm1 = "DM1";
    ManagerDictionary managerDictionary = new ManagerDictionary();
    TestDriverManager testManager = getTestDriverManager();
    managerDictionary.putOrOverride(dm1, testManager);
    Assert.assertTrue(managerDictionary.containsKey(dm1));
    assertNotNull(managerDictionary.get(dm1));
  }

  @Test(groups = TestCategories.Framework)
  public void testRemove() {
    final String dm1 = "DM1";
    final String dm2 = "DM2";
    ManagerDictionary managerDictionary = new ManagerDictionary();
    managerDictionary.put(dm1, getTestDriverManager());
    managerDictionary.put(dm2, getTestDriverManager());
    Assert.assertTrue(managerDictionary.containsKey(dm1));
    Assert.assertTrue(managerDictionary.containsKey(dm2));
    System.out.println("Removing DM2 entry...");
    Assert.assertTrue(managerDictionary.remove(dm2),"Checking if remove reported as successful");
    Assert.assertTrue(managerDictionary.containsKey(dm1));
    Assert.assertFalse(managerDictionary.containsKey(dm2));
  }

  /**
   * Test Driver Manager for Unit Tests.
   */
  private class TestDriverManager extends DriverManager {
    TestDriverManager(Supplier<Object> getDriverFunction, BaseTestObject baseTestObject) {
      super(getDriverFunction, baseTestObject);
    }

    @Override
    public void close() throws Exception {
      System.out.println("Closing Test Driver Manager...");
    }
  }
}