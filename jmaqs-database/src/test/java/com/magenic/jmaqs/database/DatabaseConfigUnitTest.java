/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.database;

import com.magenic.jmaqs.base.BaseGenericTest;
import com.magenic.jmaqs.database.constants.DataProviderType;
import com.magenic.jmaqs.database.providers.IDataSourceProvider;
import com.magenic.jmaqs.database.providers.SQLProvider;
import com.magenic.jmaqs.utilities.helper.TestCategories;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * The type Database config unit test.
 */
public class DatabaseConfigUnitTest extends BaseGenericTest {

  /**
   * Test get connection string.
   */
  @Test(groups = TestCategories.DATABASE)
  public void testGetConnectionString() {
    Assert.assertEquals(DatabaseConfig.getConnectionString(),
        "jdbc:sqlserver://mssql");
  }

  /**
   * Test get provider type string.
   */
  @Test(groups = TestCategories.DATABASE)
  public void testGetProviderTypeString() {
    Assert.assertEquals(DatabaseConfig.getProviderTypeString(), "SQL");
  }

  /**
   * Test get entity directory string.
   */
  @Test(groups = TestCategories.DATABASE)
  public void testGetEntityDirectoryString() {
    Assert.assertEquals(DatabaseConfig.getEntityDirectoryString(),
        "./src/test/java/com/magenic/jmaqs/database/entities/");
  }

  @Test(groups = TestCategories.DATABASE)
  public void testGetProviderType() {
    final DataProviderType providerType = DatabaseConfig.getProviderType();
    Assert.assertEquals(providerType, DataProviderType.SQL);
  }

  @Test(groups = TestCategories.DATABASE)
  public void testGetProvider() {
    final IDataSourceProvider provider = DatabaseConfig.getProvider();
    Assert.assertTrue(provider instanceof SQLProvider);
  }

  @Test(groups = TestCategories.DATABASE)
  public void testGetEntityPackageString() {
    Assert.assertEquals(DatabaseConfig.getEntityPackageString(),
        "com.magenic.jmaqs.database.entities");
  }

  @Test(groups = TestCategories.DATABASE)
  public void testGetDatabaseName() {
    Assert.assertEquals(DatabaseConfig.getDatabaseName(), "MagenicAutomation");
  }

  @Test(groups = TestCategories.DATABASE)
  public void testGetDatabaseUser() {
    Assert.assertEquals(DatabaseConfig.getDatabaseUser(), "sa");
  }

  @Test(groups = TestCategories.DATABASE)
  public void testGetDatabasePassword() {
    Assert.assertEquals(DatabaseConfig.getDatabasePassword(), "magenicMAQS2");
  }
}
