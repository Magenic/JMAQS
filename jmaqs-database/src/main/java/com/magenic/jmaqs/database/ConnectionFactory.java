/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.database;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.persistence.EntityManagerFactory;
import org.apache.commons.io.FileUtils;
import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;
import org.hibernate.jpa.boot.internal.PersistenceUnitInfoDescriptor;

/**
 * Class ConnectionFactory ...
 */
public class ConnectionFactory {

  /**
   * Constructor ConnectionFactory creates a new ConnectionFactory instance.
   */
  private ConnectionFactory() {

  }

  public static final String HIBERNATE_DIALECT = "hibernate.dialect";
  public static final String HIBERNATE_CONNECTION_DATASOURCE = "hibernate.connection.datasource";

  /**
   * Method getOpenConnection returns the openConnection of this ConnectionFactory object.
   *
   * @return the openConnection (type DatabaseDriver) of this ConnectionFactory object.
   */
  public static DatabaseDriver getOpenConnection() {
    final EntityManagerFactory entityManagerFactory = getEntityManagerFactory();
    return new DatabaseDriver(entityManagerFactory);

  }

  /**
   * Method getEntityManagerFactory returns the entityManagerFactory of this ConnectionFactory object.
   *
   * @return the entityManagerFactory (type EntityManagerFactory) of this ConnectionFactory object.
   */
  public static EntityManagerFactory getEntityManagerFactory() {
    DatabasePersistenceUnitInfo persistenceUnitInfo = getPersistenceUnitInfo();
    Map<String, Object> configuration = new HashMap<>();
    PersistenceUnitInfoDescriptor persistenceUnitInfoDescriptor = new PersistenceUnitInfoDescriptor(
        persistenceUnitInfo);
    return new EntityManagerFactoryBuilderImpl(persistenceUnitInfoDescriptor, configuration)
        .withDataSource(DatabaseConfig.getProvider().getDataSource()).build();
  }

  /**
   * Method getProperties returns the properties of this ConnectionFactory object.
   *
   * @return the properties (type Properties) of this ConnectionFactory object.
   */
  protected static Properties getProperties() {
    Properties properties = new Properties();
    properties.put(HIBERNATE_DIALECT, DatabaseConfig.getProvider().getDialect());
    properties.put(HIBERNATE_CONNECTION_DATASOURCE, DatabaseConfig.getProvider().getDataSource());
    properties.putAll(DatabaseConfig.getDatabaseCapabilitiesAsObjects());

    return properties;
  }

  /**
   * Method getEntityFiles returns the entityFiles of this ConnectionFactory object.
   *
   * @return the entityFiles (type File[]) of this ConnectionFactory object.
   */
  public static File[] getEntityFiles() {
    String[] extensions = { "java" };
    final Collection<File> files = FileUtils
        .listFiles(new File(DatabaseConfig.getEntityDirectoryString()), extensions, true);
    File[] fileArray = new File[files.size()];
    return files.toArray(fileArray);
  }

  /**
   * Method getEntityClassNames returns the entityClassNames of this ConnectionFactory object.
   *
   * @return the entityClassNames (type List of String) of this ConnectionFactory object.
   */
  public static List<String> getEntityClassNames() {
    final String entityPackageString = DatabaseConfig.getEntityPackageString();
    Function<File, String> function = file -> {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(entityPackageString).append(".")
          .append(file.getName().replace(".java", ""));
      return stringBuilder.toString();
    };

    return Arrays.stream(getEntityFiles()).map(function).collect(Collectors.toList());
  }

  /**
   * Method getPersistenceUnitInfo returns the persistenceUnitInfo of this ConnectionFactory object.
   *
   * @return the persistenceUnitInfo (type DatabasePersistenceUnitInfo) of this ConnectionFactory object.
   */
  public static DatabasePersistenceUnitInfo getPersistenceUnitInfo() {
    return getPersistenceUnitInfo(DatabaseConfig.getDatabaseName());
  }

  /**
   * Method getPersistenceUnitInfo ...
   *
   * @param name of type String
   * @return DatabasePersistenceUnitInfo
   */
  public static DatabasePersistenceUnitInfo getPersistenceUnitInfo(String name) {
    return new DatabasePersistenceUnitInfo(name, getEntityClassNames(), getProperties());
  }

}
