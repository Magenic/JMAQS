/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.database.providers;

import com.magenic.jmaqs.database.constants.DataProviderType;
import javax.sql.DataSource;
import org.sqlite.SQLiteDataSource;

/**
 * Class SQLiteProvider.
 */
public class SQLiteProvider implements IDataSourceProvider {

  /**
   * Field dbUrl.
   */
  private String dbUrl;
  /**
   * Field dataProviderType.
   */
  private static final DataProviderType dataProviderType = DataProviderType.SQLITE;

  /**
   * Constructor SQLiteProvider creates a new SQLiteProvider instance.
   *
   * @param dbUrl of type String
   */
  public SQLiteProvider(String dbUrl) {
    this.dbUrl = dbUrl;
  }

  @Override
  public DataSource getDataSource() {
    SQLiteDataSource dataSource = new SQLiteDataSource();
    dataSource.setUrl(dbUrl);
    return dataSource;
  }

  @Override
  public String getDialect() {
    return dataProviderType.getDialectString();
  }
}
