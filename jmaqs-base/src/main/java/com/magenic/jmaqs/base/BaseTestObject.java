/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.base;

import com.magenic.jmaqs.base.exceptions.DriverDisposalException;
import com.magenic.jmaqs.utilities.helper.StringProcessor;
import com.magenic.jmaqs.utilities.logging.Logger;
import com.magenic.jmaqs.utilities.logging.MessageType;
import com.magenic.jmaqs.utilities.performance.PerfTimerCollection;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * The BaseTestObject class.
 */
public class BaseTestObject implements AutoCloseable {
  /**
   * The Logger.
   */
  private Logger logger;

  /**
   * The Performance Timer Collection.
   */
  private PerfTimerCollection perfTimerCollection;

  /**
   * Concurrent Hash Map of string key value pairs.
   */
  private ConcurrentHashMap<String, String> values;

  /**
   * Concurrent Hash Map of string key and object value pairs.
   */
  private ConcurrentHashMap<String, Object> objects;

  /**
   * Dictionary of String key and driver value pairs.
   */
  private ManagerDictionary managerStore;

  /**
   * ArrayList of Strings for associated files.
   */
  private final ArrayList<String> associatedFiles;

  /**
   * The Fully Qualified Test Name.
   */
  private final String fullyQualifiedTestName;

  /**
   * Was the object closed.
   */
  private boolean isClosed = false;

  /**
   * Check if the object has been closed.
   *
   * @return True if the object is closed
   */
  public boolean getClosed() {
    return this.isClosed;
  }

  /**
   * Initializes a new instance of the BaseTestObject class.
   *
   * @param logger                 The test's logger
   * @param fullyQualifiedTestName The test's fully qualified test name
   */
  public BaseTestObject(final Logger logger, final String fullyQualifiedTestName) {
    this.logger = logger;
    this.perfTimerCollection = new PerfTimerCollection(logger, fullyQualifiedTestName);
    this.values = new ConcurrentHashMap<>();
    this.objects = new ConcurrentHashMap<>();
    this.managerStore = new ManagerDictionary();
    this.associatedFiles = new ArrayList<>();
    this.fullyQualifiedTestName = fullyQualifiedTestName;

    logger.logMessage(MessageType.INFORMATION, "Setup test object for " + fullyQualifiedTestName);
  }

  /**
   * Initializes a new instance of the BaseTestObject class.
   *
   * @param baseTestObject An existing base test object
   */
  public BaseTestObject(final BaseTestObject baseTestObject) {
    this.logger = baseTestObject.getLogger();
    this.perfTimerCollection = baseTestObject.getPerfTimerCollection();
    this.values = (ConcurrentHashMap<String, String>) baseTestObject.getValues();
    this.objects = (ConcurrentHashMap<String, Object>) baseTestObject.getObjects();
    this.managerStore = baseTestObject.getManagerStore();
    this.associatedFiles = new ArrayList<>();
    this.fullyQualifiedTestName = baseTestObject.getFullyQualifiedTestName();

    baseTestObject.getLogger().logMessage(MessageType.INFORMATION, "Setup test object");
  }

  /**
   * Gets the logger.
   *
   * @return The logger
   */
  public Logger getLogger() {
    return this.logger;
  }

  /**
   * Sets the logger.
   *
   * @param logger The logger to use
   */
  public void setLogger(final Logger logger) {
    this.logger = logger;
  }

  /**
   * Gets the Performance Timer Collection.
   *
   * @return Performance Timer Collection
   */
  public PerfTimerCollection getPerfTimerCollection() {
    return this.perfTimerCollection;
  }

  /**
   * Sets the Performance Timer Collection.
   *
   * @param perfTimerCollection Performance Timer Collection
   */
  public void setPerfTimerCollection(final PerfTimerCollection perfTimerCollection) {
    this.perfTimerCollection = perfTimerCollection;
  }

  public String getFullyQualifiedTestName() {
    return this.fullyQualifiedTestName;
  }

  /**
   * Gets the Concurrent Hash Map of string key value pairs.
   *
   * @return Concurrent Hash Map of string key value pairs
   */
  public ConcurrentMap<String, String> getValues() {
    return this.values;
  }

  /**
   * Sets the Concurrent Hash Map of string key and object value pairs.
   *
   * @param values Concurrent Hash Map of string key value pairs to use
   */
  protected void setValues(final ConcurrentHashMap<String, String> values) {
    this.values = values;
  }

  /**
   * Gets the Concurrent Hash Map of string key and object value pairs.
   *
   * @return Concurrent Hash Map of string key and object value pairs
   */
  public ConcurrentMap<String, Object> getObjects() {
    return this.objects;
  }

  /**
   * Sets the Concurrent Hash Map of string key and object value pairs.
   *
   * @param objects Concurrent Hash Map of string key and object value pairs to
   *                use
   */
  protected void setObjects(final ConcurrentHashMap<String, Object> objects) {
    this.objects = objects;
  }

  /**
   * Gets the Concurrent Hash Map of string key and driver value pairs.
   *
   * @return Concurrent Hash Map of string key and driver value pairs
   */
  public ManagerDictionary getManagerStore() {
    return this.managerStore;
  }

  /**
   * Sets the Concurrent Hash Map of string key and driver value pairs.
   *
   * @param managerStore Concurrent Hash Map of string key and driver value pairs
   *                     to use.
   */
  protected void setManagerStore(final ManagerDictionary managerStore) {
    this.managerStore = managerStore;
  }

  /**
   * Sets a string value, will replace if the key already exists.
   *
   * @param key   The key
   * @param value The value to associate with the key
   */
  public void setValue(final String key, final String value) {
    if (this.values.containsKey(key)) {
      this.values.replace(key, value);
    } else {
      this.values.put(key, value);
    }
  }

  /**
   * Sets an object value, will replace if the key already exists.
   *
   * @param key   The key
   * @param value The value to associate with the key
   */
  public void setObject(final String key, final Object value) {
    if (this.objects.containsKey(key)) {
      this.objects.replace(key, value);
    } else {
      this.objects.put(key, value);
    }
  }

  /**
   * Add driver manager.
   *
   * @param <T>           the type parameter
   * @param driverManager the driver manager
   */
  public <T extends DriverManager<?>> void addDriverManager(final T driverManager) {
    this.addDriverManager(driverManager, false);
  }

  /**
   * Add driver manager.
   *
   * @param <T>              the type parameter
   * @param driverManager    the driver manager
   * @param overrideIfExists the override if exists
   */
  public <T extends DriverManager<?>> void addDriverManager(final T driverManager, final boolean overrideIfExists) {
    if (overrideIfExists) {
      this.overrideDriverManager(driverManager.getClass().getTypeName(), driverManager);
    } else {
      this.addDriverManager(driverManager.getClass().getTypeName(), driverManager);
    }
  }

  /**
   * Add driver manager.
   *
   * @param key           the key
   * @param driverManager the driver manager
   */
  public void addDriverManager(final String key, final DriverManager<?> driverManager) {
    this.managerStore.put(key, driverManager);
  }

  /**
   * Override driver manager.
   *
   * @param key           the key
   * @param driverManager the driver manager
   */
  public void overrideDriverManager(final String key, final DriverManager<?> driverManager) {
    if (this.managerStore.containsKey(key)) {
      this.managerStore.putOrOverride(key, driverManager);
    } else {
      this.managerStore.put(key, driverManager);
    }
  }

  /**
   * Add associated file boolean.
   *
   * @param path the path
   * @return the boolean
   */
  public boolean addAssociatedFile(final String path) {
    if (new File(path).exists()) {
      return this.associatedFiles.add(path);
    }

    return false;
  }

  /**
   * Dispose of the driver store.
   *
   * @param closing the closing
   */
  // In C#, but might not be necessary
  public void close(final boolean closing) {
    if (!closing) {
      this.close();
    }
  }

  /**
   * Dispose of the driver store.
   */
  @Override
  public void close() {
    if (this.managerStore == null) {
      return;
    }

    this.logger.logMessage(MessageType.VERBOSE, "Start dispose");

    for (final DriverManager<?> singleDriver : this.managerStore.values()) {
      if (singleDriver != null) {
        try {
          singleDriver.close();
        } catch (final Exception e) {
          throw new DriverDisposalException(StringProcessor.safeFormatter("Unable to properly dispose of driver"), e);
        }
      }
      this.managerStore = null;
      this.logger.logMessage(MessageType.VERBOSE, "End dispose");
    }

    isClosed = true;
  }

  /**
   * Remove associated file boolean.
   *
   * @param path the path
   * @return the boolean
   */
  public boolean removeAssociatedFile(final String path) {
    return this.associatedFiles.remove(path);
  }

  /**
   * Get array of associated files string [ ].
   *
   * @return the string [ ]
   */
  public String[] getArrayOfAssociatedFiles() {
    return this.associatedFiles.toArray(new String[0]);
  }

  /**
   * Contains associated file boolean.
   *
   * @param path the path
   * @return the boolean
   */
  public boolean containsAssociatedFile(final String path) {
    return this.associatedFiles.contains(path);
  }
}
