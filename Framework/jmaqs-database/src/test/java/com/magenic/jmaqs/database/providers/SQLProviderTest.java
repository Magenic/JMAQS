/*
 * Copyright 2020 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.database.providers;

import com.magenic.jmaqs.base.BaseGenericTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SQLProviderTest extends BaseGenericTest {

  @Test
  public void testGetDataSource() {
    SQLProvider sqlProvider = new SQLProvider("http://127.0.0.1");
    Assert.assertNotNull(sqlProvider.getDataSource());
  }

  @Test
  public void testGetDialect() {
    SQLProvider sqlProvider = new SQLProvider("http://127.0.0.1");
    Assert.assertEquals(sqlProvider.getDialect(), "org.hibernate.dialect.SQLServerDialect");
  }
}