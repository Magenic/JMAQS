/*
 * Copyright 2019 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.base;

import com.magenic.jmaqs.utilities.helper.TestCategories;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BaseGenericTestUnitTest extends BaseGenericTest {

  @Test(groups = TestCategories.Framework)
  public void testCreateTestObject() {
    Assert.assertNotNull(this.getTestObject());
  }
}