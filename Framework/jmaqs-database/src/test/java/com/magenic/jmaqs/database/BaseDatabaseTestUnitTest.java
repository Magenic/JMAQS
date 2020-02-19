/*
 * Copyright 2020 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.database;

import com.magenic.jmaqs.utilities.helper.TestCategories;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BaseDatabaseTestUnitTest extends BaseDatabaseTest {

  @Test(groups = TestCategories.Database)
  public void testGetDatabaseDriver() {
    final DatabaseDriver databaseDriver = getDatabaseDriver();
    Assert.assertNotNull(databaseDriver);
  }

  @Test(groups = TestCategories.Database)
  public void testSetDatabaseDriver() {
    final DatabaseDriver connection = getConnection();
    final int hashCode = getDatabaseDriver().hashCode();
    setDatabaseDriver(connection);
    final int hashCode1 = getDatabaseDriver().hashCode();
    Assert.assertNotEquals(hashCode, hashCode1);
  }

  @Test(groups = TestCategories.Database)
  public void testGetConnection() {
    final DatabaseDriver connection = getConnection();
    Assert.assertNotNull(connection);
    Assert.assertNotEquals(connection.hashCode(), getDatabaseDriver().hashCode());
  }
}