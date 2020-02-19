/*
 * Copyright 2020 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.database.providers;

import javax.sql.DataSource;

/**
 * Interface IDataSourceProvider.
 */
public interface IDataSourceProvider {

  /**
   * Method getDataSource returns the dataSource of this IDataSourceProvider object.
   *
   * @return the dataSource (type DataSource) of this IDataSourceProvider object.
   */
  DataSource getDataSource();

  /**
   * Method getDialect returns the dialect of this IDataSourceProvider object.
   *
   * @return the dialect (type String) of this IDataSourceProvider object.
   */
  String getDialect();
}
