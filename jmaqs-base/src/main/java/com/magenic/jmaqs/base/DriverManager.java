/*
 * Copyright 2020 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.base;

import com.magenic.jmaqs.utilities.logging.Logger;
import java.util.function.Supplier;

/**
 * The type Driver manager.
 */
public abstract class DriverManager<T> implements AutoCloseable {

  /**
   * Base Test Object.
   */
  private final BaseTestObject baseTestObject;

  /**
   * The Base driver.
   */
  protected Object baseDriver;

  /**
   * The Get driver.
   */
  protected Supplier<T> getDriverSupplier;

  /**
   * Instantiates a new Driver manager.
   *
   * @param getDriverFunction driver function supplier
   * @param baseTestObject    the base test object
   */
  public DriverManager(Supplier<T> getDriverFunction, BaseTestObject baseTestObject) {
    this.baseTestObject = baseTestObject;
    this.getDriverSupplier = getDriverFunction;
  }

  /**
   * Gets base driver.
   *
   * @return the base driver
   */
  public Object getBaseDriver() {
    return baseDriver;
  }

  /**
   * Sets base driver.
   *
   * @param baseDriver the base driver
   */
  public void setBaseDriver(Object baseDriver) {
    this.baseDriver = baseDriver;
  }

  /**
   * Is driver initialized boolean.
   *
   * @return the boolean
   */
  public boolean isDriverInitialized() {
    return this.baseDriver != null;
  }

  /**
   * Gets logger.
   *
   * @return the logger
   */
  public Logger getLogger() {
    return this.baseTestObject.getLogger();
  }

  /**
   * Get base object.
   *
   * @return the object
   */
  protected Object getBase() {
    if (this.baseDriver == null) {
      this.baseDriver = this.getDriverSupplier.get();
    }

    return this.baseDriver;
  }
}
