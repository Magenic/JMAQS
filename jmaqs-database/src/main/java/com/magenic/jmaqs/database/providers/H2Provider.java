/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.database.providers;

import javax.sql.DataSource;

/**
 * Class H2Provider ...
 */
public class H2Provider implements IDataSourceProvider {

  /**
   * Constructor H2Provider creates a new H2Provider instance.
   *
   * @param connectionString of type String
   */
  public H2Provider(String connectionString) {

  }

  /**
   * Method getDataSource returns the dataSource of this H2Provider object.
   *
   * @return the dataSource (type DataSource) of this H2Provider object.
   */
  @Override
  public DataSource getDataSource() {
    return null;
  }

  /**
   * Method getDialect returns the dialect of this H2Provider object.
   *
   * @return the dialect (type String) of this H2Provider object.
   */
  @Override
  public String getDialect() {
    return null;
  }
}
