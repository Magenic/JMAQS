/*
 * Copyright 2019 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.base;

import com.magenic.jmaqs.utilities.helper.TestCategories;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.Test;

/**
 * Manager Dictionary Unit Tests.
 */
public class ConcurrentManagerHashMapUnitTest extends BaseTest {

  @Test(groups = TestCategories.Framework)
  public void testClear() {

    ConcurrentManagerHashMap newHash = new ConcurrentManagerHashMap();
    newHash.put("1", new BaseTestObject(this.getTestObject()));
    newHash.put("2", new BaseTestObject(this.getTestObject()));

    newHash.clear();

    Assert.assertEquals(newHash.mappingCount(), 0);
  }

  @Test(groups = TestCategories.Framework)
  public void testRemoveWithKey() {

    ConcurrentManagerHashMap newHash = new ConcurrentManagerHashMap();
    newHash.put("1", new BaseTestObject(this.getTestObject()));
    BaseTestObject temp = new BaseTestObject(this.getTestObject());
    newHash.put("2", temp);

    Assert.assertEquals(temp, newHash.remove("2"), "Did not get correct object");

    Assert.assertTrue(newHash.containsKey("1"), "Expected item '1'");
    Assert.assertFalse(newHash.containsKey("2"), "Was not expecting item '2'");
  }

  @Test(groups = TestCategories.Framework)
  public void testRemoveWithKeyAndValue() {

    ConcurrentManagerHashMap newHash = new ConcurrentManagerHashMap();
    newHash.put("1", new BaseTestObject(this.getTestObject()));
    BaseTestObject temp = new BaseTestObject(this.getTestObject());
    newHash.put("2", temp);

    Assert.assertTrue(newHash.remove("2", temp ), "Did not remove");

    Assert.assertTrue(newHash.containsKey("1"), "Expected item '1'");
    Assert.assertFalse(newHash.containsKey("2"), "Was not expecting item '2'");
  }

  @Test(groups = TestCategories.Framework)
  public void testReplace() {

    ConcurrentManagerHashMap newHash = new ConcurrentManagerHashMap();
    newHash.put("1", new BaseTestObject(this.getTestObject()));
    BaseTestObject temp = new BaseTestObject(this.getTestObject());
    BaseTestObject temp2 = new BaseTestObject(this.getTestObject());
    newHash.put("2", temp);


    Assert.assertEquals(temp, newHash.replace("2", temp2 ), "Did not replace");

    Assert.assertTrue(newHash.containsKey("1"), "Expected item '1'");
    Assert.assertTrue(newHash.containsKey("2"), "Expected item '2'");
  }

  @Test(groups = TestCategories.Framework)
  public void testReplaceWithNewAndOld() {

    ConcurrentManagerHashMap newHash = new ConcurrentManagerHashMap();
    newHash.put("1", new BaseTestObject(this.getTestObject()));
    BaseTestObject temp = new BaseTestObject(this.getTestObject());
    BaseTestObject temp2 = new BaseTestObject(this.getTestObject());
    newHash.put("2", temp);

    Assert.assertTrue(newHash.replace("2", temp, temp2 ), "Did not replace");

    Assert.assertTrue(newHash.containsKey("1"), "Expected item '1'");
    Assert.assertTrue(newHash.containsKey("2"), "Expected item '2'");
    Assert.assertTrue(temp.getClosed(), "Expected item old item to be closed");
    Assert.assertFalse(temp2.getClosed(), "Did not expect new item to be closed");
  }

    /*
     * (non-Javadoc)
     *
     * @see com.magenic.jmaqs.utilities.BaseTest.BaseTest#beforeLoggingTeardown(org.testng.
     * ITestResult)
     */
    @Override
    protected void beforeLoggingTeardown(ITestResult resultType) {

    }
  }