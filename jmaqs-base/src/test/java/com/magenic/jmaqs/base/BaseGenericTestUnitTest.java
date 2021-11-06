/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.base;

import com.magenic.jmaqs.utilities.helper.TestCategories;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Base generic test unit tests.
 */
public class BaseGenericTestUnitTest extends BaseGenericTest {

  /**
   * Test the creation of a new test object.
   */
  @Test(groups = TestCategories.FRAMEWORK)
  public void testCreateTestObject() {
    Assert.assertNotNull(this.getTestObject());
  }
}