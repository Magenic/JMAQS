/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.utilities.performance;

import com.magenic.jmaqs.utilities.logging.Logger;

/**
 * Response timer collection class.
 * Object to be owned by Test Class (Object), and passed to page Constructors to insert Performance Timers.
 */
public class PerfTimerCollection {

  /**
   * The logger.
   */
  protected Logger log;

  /**
   * The Test Name.
   */
  private String testName;

  /**
   * Gets the Logger.
   *
   * @return The Logger
   */
  protected Logger getLog() {
    return this.log;
  }

  /**
   * Sets the Logger.
   *
   * @param logger The logger to set.
   */
  public void setLog(Logger logger) {
    this.log = logger;
  }

  /**
   * Gets the Test Name.
   *
   * @return The Test Name
   */
  public String getTestName() {
    return this.testName;
  }

  /**
   * Sets the Test Name.
   *
   * @param testName The Test Name
   */
  public void setTestName(String testName) {
    this.testName = testName;
  }

  /**
   * Initializes a new instance of the PerfTimerCollection class.
   *
   * @param logger                 Logger to use
   * @param fullyQualifiedTestName Test Name
   */
  public PerfTimerCollection(Logger logger, String fullyQualifiedTestName) {
    this.log = logger;
    this.testName = fullyQualifiedTestName;
  }
}
