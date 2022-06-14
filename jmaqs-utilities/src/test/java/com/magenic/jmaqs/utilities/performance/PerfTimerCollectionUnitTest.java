/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.utilities.performance;

import com.magenic.jmaqs.utilities.logging.ConsoleLogger;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Performance Timer Collection unit test class.
 */
public class PerfTimerCollectionUnitTest {

  /**
   * Tests getting the logger.
   */
  @Test
  public void testGetLog() {
    ConsoleLogger logger = new ConsoleLogger();
    PerfTimerCollection perfTimerCollection = new PerfTimerCollection(logger, "TestCase");
    Assert.assertNotNull(perfTimerCollection.getLog());
  }

  /**
   * Tests setting the logger.
   */
  @Test
  public void testSetLog() {
    ConsoleLogger logger = new ConsoleLogger();
    PerfTimerCollection perfTimerCollection = new PerfTimerCollection(logger, "TestCase");
    perfTimerCollection.setLog(logger);
    Assert.assertNotNull(perfTimerCollection.getLog());
  }

  /**
   * Tests getting the test name.
   */
  @Test
  public void testGetTestName() {
    ConsoleLogger logger = new ConsoleLogger();
    PerfTimerCollection perfTimerCollection = new PerfTimerCollection(logger, "TestCase");
    Assert.assertEquals(perfTimerCollection.getTestName(), "TestCase");
  }

  /**
   * Tests setting the test name.
   */
  @Test
  public void testSetTestName() {
    ConsoleLogger logger = new ConsoleLogger();
    PerfTimerCollection perfTimerCollection = new PerfTimerCollection(logger, "TestCase");
    Assert.assertEquals(perfTimerCollection.getTestName(), "TestCase");
    perfTimerCollection.setTestName("NewTestCaseName");
    Assert.assertEquals(perfTimerCollection.getTestName(), "NewTestCaseName");

  }
}