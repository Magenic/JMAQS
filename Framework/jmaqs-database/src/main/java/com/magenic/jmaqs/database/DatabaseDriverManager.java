/*
 * Copyright 2020 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.database;

import com.magenic.jmaqs.base.BaseTestObject;
import com.magenic.jmaqs.base.DriverManager;
import java.util.function.Supplier;

public class DatabaseDriverManager extends DriverManager {

  /**
   * Instantiates a new Driver manager.
   *
   * @param getDriverFunction Supplier wrapped function for driver creation
   * @param baseTestObject    the base test object
   */
  public DatabaseDriverManager(Supplier getDriverFunction, BaseTestObject baseTestObject) {
    super(getDriverFunction, baseTestObject);
  }

  public DatabaseDriver getDatabaseDriver() {
    return (DatabaseDriver) getBase();
  }

  @Override
  public void close() throws Exception {
    if (!this.isDriverInitialized()) {
      return;
    }

    DatabaseDriver databaseDriver = this.getDatabaseDriver();
    databaseDriver.close();
    this.baseDriver = null;
  }

}
