/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.database.providers;

import com.magenic.jmaqs.base.BaseGenericTest;
import com.magenic.jmaqs.utilities.helper.TestCategories;
import java.util.Iterator;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

/**
 * Class IDataSourceProviderUnitTest ...
 */
@Ignore("Will be tested later")
public class IDataSourceProviderUnitTest extends BaseGenericTest {

  /**
   * Method getData returns the data of this IDataSourceProviderUnitTest object.
   *
   * @return the data (type Iterator<Object>) of this IDataSourceProviderUnitTest object.
   */
  @DataProvider(name = "dataProvider")
  public Iterator<Object> getData() {
    return null;
  }

  /**
   * Method testGetDataSource ...
   */
  @Test(groups = TestCategories.DATABASE, dataProvider = "dataProvider")
  public void testGetDataSource() {
    throw new UnsupportedOperationException("Test method not implemented yet.");
  }

  /**
   * Method testGetDialect ...
   */
  @Test(groups = TestCategories.DATABASE, dataProvider = "dataProvider")
  public void testGetDialect() {
    throw new UnsupportedOperationException("Test method not implemented yet.");
  }
}