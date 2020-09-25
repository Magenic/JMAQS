/*
 * Copyright 2020 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.base;

import com.magenic.jmaqs.utilities.helper.TestCategories;
import com.magenic.jmaqs.utilities.logging.Logger;
import com.magenic.jmaqs.utilities.performance.PerfTimerCollection;
import java.io.File;
import java.io.IOException;
import java.util.function.Supplier;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * The type Base test object test.
 */
public class BaseTestObjectUnitTest extends BaseGenericTest {

  /**
   * Test Constructor with Log and Method.
   */
  @Test(groups = TestCategories.FRAMEWORK)
  public void testBaseTestObject1() {
    final BaseTestObject testObject = this.getTestObject();

    // final String methodName = this.method.getName();
    BaseTestObject baseTestObject = new BaseTestObject(testObject.getLogger(), "FakeTestName");
    Assert.assertNotNull(baseTestObject, "Checking that Base Test Object instantiated correctly");
  }

  /**
   * Test Constructor with Test Object.
   */
  @Test(groups = TestCategories.FRAMEWORK)
  public void testBaseTestObject2() {
    final BaseTestObject testObject = this.getTestObject();
    BaseTestObject baseTestObject = new BaseTestObject(testObject);
    Assert.assertNotNull(baseTestObject, "Checking that Base Test Object instantiated correctly");

  }

  /**
   * Test set value.
   */
  @Test(groups = TestCategories.FRAMEWORK)
  public void testSetValue() {
    BaseTestObject testObject = this.getTestObject();
    final String key = "SetKey";
    final String value = "SetKey Value";
    testObject.setValue(key, value);
    Assert.assertTrue(testObject.getValues().containsKey(key), "Checking that key exists in test object dictionary");
    Assert.assertEquals(testObject.getValues().get(key), value, "Checking that value set correctly");
  }

  /**
   * Test set object.
   */
  @Test(groups = TestCategories.FRAMEWORK)
  public void testSetObject() {

    BaseTestObject testObject = this.getTestObject();
    final String key = "SetObject";
    final Object object = new Object();
    testObject.setObject(key, object);
    Assert.assertTrue(testObject.getObjects().containsKey(key), "Checking that key exists in test object dictionary");
    Assert.assertEquals(testObject.getObjects().get(key), object, "Checking that value set correctly");
  }

  /**
   * Test get log.
   */
  @Test(groups = TestCategories.FRAMEWORK)
  public void testGetLog() {
    BaseTestObject testObject = this.getTestObject();
    Assert.assertNotNull(testObject.getLogger(), "Checking that logger is not null.");
  }

  /**
   * Test set log.
   */
  @Test(groups = TestCategories.FRAMEWORK)
  public void testSetLog() {
    BaseTestObject testObject = this.getTestObject();
    final Logger logger = this.getLogger();
    testObject.setLogger(logger);
    Assert.assertEquals(testObject.getLogger(), logger, "Checking that logger set correctly.");
  }

  /**
   * Test Get Perf Collection Timer - Not Null.
   */
  @Test(groups = TestCategories.FRAMEWORK)
  public void testGetPerfTimerCollectionNotNull() {
    BaseTestObject testObject = this.getTestObject();
    Assert.assertNotNull(testObject.getPerfTimerCollection(), "Checking that logger is not null.");
  }

  /**
   * Test Set Perf Collection Timer - Get/Set.
   */
  @Test(groups = TestCategories.FRAMEWORK)
  public void testSetPerfTimerCollectionGetSet() {
    BaseTestObject testObject = this.getTestObject();
    final PerfTimerCollection perfTimerCollection = new PerfTimerCollection(testObject.getLogger(), "FakeTestName");
    testObject.setPerfTimerCollection(perfTimerCollection);
    Assert.assertEquals(testObject.getPerfTimerCollection(), perfTimerCollection,
        "Checking that perf timer collection set correctly.");
  }

  /**
   * Test get values.
   */
  @Test(groups = TestCategories.FRAMEWORK)
  public void testGetValues() {
    BaseTestObject testObject = this.getTestObject();
    Assert.assertNotNull(testObject.getValues(), "Checking that values is not null.");
  }

  /**
   * Test get objects.
   */
  @Test(groups = TestCategories.FRAMEWORK)
  public void testGetObjects() {
    BaseTestObject testObject = this.getTestObject();
    Assert.assertNotNull(testObject.getObjects(), "Checking that objects is not null.");
  }

  /**
   * Test Get Manager Store - Not Null.
   */
  @Test(groups = TestCategories.FRAMEWORK)
  public void testGetManagerStoreNotNull() {
    BaseTestObject testObject = this.getTestObject();
    Assert.assertNotNull(testObject.getManagerStore(), "Checking that objects is not null.");
  }

  /**
   * Test add driver manager.
   */
  @Test(groups = TestCategories.FRAMEWORK)
  public void testAddDriverManager() {
    BaseTestObject testObject = this.getTestObject();
    final Supplier<String> supplier = () -> null;
    DriverManager<String> driverManager = getDriverManager(testObject, supplier);
    Assert.assertEquals(testObject.getManagerStore().size(), 0, "Checking that manager store is empty");
    testObject.addDriverManager(driverManager);
    Assert.assertEquals(testObject.getManagerStore().size(), 1, "Checking that manager store has 1 object added");

  }

  /**
   * Test add driver manager - Overwrite True.
   */
  @Test(groups = TestCategories.FRAMEWORK)
  public void testAddDriverManagerTrue() {
    BaseTestObject testObject = this.getTestObject();
    final Supplier<String> supplier = () -> null;
    final DriverManager<String> driverManager = getDriverManager(testObject, supplier);
    final DriverManager<String> driverManager2 = getDriverManager(testObject, supplier);
    Assert.assertEquals(testObject.getManagerStore().size(), 0, "Checking that manager store is empty");
    testObject.addDriverManager(driverManager, true);
    Assert.assertEquals(testObject.getManagerStore().size(), 1, "Checking that manager store has 1 object added");
    testObject.addDriverManager(driverManager2, true);
    Assert.assertEquals(testObject.getManagerStore().size(), 1, "Checking that manager store has 1 object added");
  }

  /**
   * Test add driver manager - Overwrite False.
   */
  @Test(groups = TestCategories.FRAMEWORK)
  public void testAddDriverManagerFalse() {
    BaseTestObject testObject = this.getTestObject();
    final Supplier<String> supplier = () -> null;
    final DriverManager<String> driverManager = getDriverManager(testObject, supplier);

    Assert.assertEquals(testObject.getManagerStore().size(), 0, "Checking that manager store is empty");
    testObject.addDriverManager(driverManager, false);
    Assert.assertEquals(testObject.getManagerStore().size(), 1, "Checking that manager store has 1 object added");
  }

  /**
   * Test add driver manager 2.
   */
  @Test(groups = TestCategories.FRAMEWORK)
  public void testAddDriverManager2() {
    BaseTestObject testObject = this.getTestObject();
    final Supplier<String> supplier = () -> null;
    final DriverManager<String> driverManager = getDriverManager(testObject, supplier);
    final String key = "DriverManager1";
    Assert.assertEquals(testObject.getManagerStore().size(), 0, "Checking that manager store is empty");
    testObject.addDriverManager(key, driverManager);
    Assert.assertEquals(testObject.getManagerStore().size(), 1, "Checking that manager store has 1 object added");
    Assert.assertTrue(testObject.getManagerStore().containsKey(key), "Checking if key exists in Manager Store");
  }

  /**
   * Test close.
   */
  @Test(groups = TestCategories.FRAMEWORK)
  public void testClose() {
    BaseTestObject testObject = this.getTestObject();
    final Supplier<String> supplier = () -> null;
    final DriverManager<String> driverManager = getDriverManager(testObject, supplier);
    final String key = "DriverManager1";
    testObject.addDriverManager(key, driverManager);
    testObject.close();
    Assert.assertNull(testObject.getManagerStore(), "Checking that manager store has been closed");
    Assert.assertEquals(testObject.getValues().size(), 0, "Checking if values in manager store are closed");

  }

  /**
   * Test add associated file.
   */
  @Test(groups = TestCategories.FRAMEWORK)
  public void testAddAssociatedFile() {
    BaseTestObject testObject = this.getTestObject();
    File temp = null;
    try {
      temp = File.createTempFile("tempfile", ".tmp");
    } catch (IOException e) {
      e.printStackTrace();
    }
    assert temp.exists();

    Assert.assertTrue(testObject.addAssociatedFile(temp.getAbsolutePath()), "Checking that associated file was added");
    Assert.assertEquals((testObject.getArrayOfAssociatedFiles()).length, 1,
        "Checking that one file was added to array.");
  }

  /**
   * Test remove associated file.
   */
  @Test(groups = TestCategories.FRAMEWORK)
  public void testRemoveAssociatedFile() {
    BaseTestObject testObject = this.getTestObject();
    File temp = null;
    try {
      temp = File.createTempFile("tempfile", ".tmp");
    } catch (IOException e) {
      e.printStackTrace();
    }
    assert temp.exists();
    final String path = temp.getAbsolutePath();

    Assert.assertTrue(testObject.addAssociatedFile(path), "Checking that associated file was added");
    Assert.assertTrue(testObject.removeAssociatedFile(path), "Checking that assocai");
  }

  /**
   * Test get array of associated files.
   */
  @Test(groups = TestCategories.FRAMEWORK)
  public void testGetArrayOfAssociatedFiles() {
    BaseTestObject testObject = this.getTestObject();
    File temp = null;
    try {
      temp = File.createTempFile("tempfile", ".tmp");
    } catch (IOException e) {
      e.printStackTrace();
    }
    assert temp.exists();
    final String path = temp.getAbsolutePath();
    Assert.assertTrue(testObject.addAssociatedFile(path), "Checking that associated file was added");
    Assert.assertNotNull(testObject.getArrayOfAssociatedFiles(), "Checking that array is instantiated");
    Assert.assertEquals(testObject.getArrayOfAssociatedFiles().length, 1, "Checking that array is not empty");
  }

  /**
   * Test contains associated file.
   */
  @Test(groups = TestCategories.FRAMEWORK)
  public void testContainsAssociatedFile() {
    BaseTestObject testObject = this.getTestObject();
    File temp = null;
    try {
      temp = File.createTempFile("tempfile", ".tmp");
    } catch (IOException e) {
      e.printStackTrace();
    }
    assert temp.exists();
    final String path = temp.getAbsolutePath();
    Assert.assertTrue(testObject.addAssociatedFile(path), "Checking that associated file was added");
    Assert.assertNotNull(testObject.getArrayOfAssociatedFiles(), "Checking that array is instantiated");
    Assert.assertTrue(testObject.containsAssociatedFile(path), "Checking if array contains file");
  }

  // Test Setup Objects
  /*
   * private Logger getLogger() { return new Logger() {
   * 
   * @Override public void logMessage(MessageType messageType, String message,
   * Object... args) {
   * 
   * }
   * 
   * @Override public void logMessage(String message, Object... args) {
   * 
   * } }; }
   */

  private DriverManager<String> getDriverManager(BaseTestObject testObject, Supplier<String> supplier) {
    return new DriverManager<String>(supplier, testObject) {
      @Override
      public void close() throws Exception {
      }
    };
  }
}