/*
 * Copyright 2019 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.base;

import com.magenic.jmaqs.utilities.helper.TestCategories;
import com.magenic.jmaqs.utilities.logging.ConsoleLogger;
import com.magenic.jmaqs.utilities.logging.FileLogger;
import com.magenic.jmaqs.utilities.logging.Logger;
import com.magenic.jmaqs.utilities.logging.LoggingConfig;
import com.magenic.jmaqs.utilities.logging.MessageType;
import com.magenic.jmaqs.utilities.performance.PerfTimerCollection;
import java.util.ArrayList;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.Test;

/**
 * Unit test class for BaseTest class.
 */
@Test(groups = TestCategories.Framework)
public class BaseTestUnitTest extends BaseTest {
  /**
   * Verify fully qualified test name.
   */
  @Test(groups = TestCategories.Framework)
  public void fullyQualifiedTestNameTest() {
    String testName = this.getFullyQualifiedTestClassName();

    Assert.assertEquals(testName,
        "com.magenic.jmaqs.base.BaseTestUnitTest.fullyQualifiedTestNameTest");
  }

  /**
   * Validate setting a new logger.
   */
  @Test(groups = TestCategories.Framework)
  public void fileLoggerTest() {
    this.setLogger(new FileLogger());

    if (!(this.getLogger() instanceof FileLogger)) {
      Assert.fail("FileLogger was not set.");
    }
  }

  /**
   * Validate Logging Verbose works.
   */
  @Test(groups = TestCategories.Framework)
  public void logVerboseTest() {
    this.logVerbose("This is a test to log verbose.");
  }

  /**
   * Validate that Try To Log is working.
   */
  @Test(groups = TestCategories.Framework)
  public void tryToLogTest() {
    this.tryToLog(MessageType.INFORMATION, "Try to log message.");
  }

  /**
   * Validate adding exceptions to the Logged Exception list adds the exceptions correctly.
   */
  @Test(groups = TestCategories.Framework)
  public void addLoggedExceptionsTest() {
    ArrayList<String> exceptions = new ArrayList<String>();
    exceptions.add("First Exception.");
    exceptions.add("Second Exception.");
    exceptions.add("Third Exception.");
    this.setLoggedExceptions(exceptions);

    Assert.assertTrue(this.getLoggedExceptions().size() == 3,
        "Expect that 3 Logged exceptions are in this exception list.");
  }

  /**
   * Validate the Logging Enabled Setting is YES (set in Config).
   */
  @Test(groups = TestCategories.Framework)
  public void loggingEnabledSettingTest() {
    Assert.assertEquals(this.getLoggingEnabledSetting(), LoggingConfig.getLoggingEnabledSetting());
  }

  /**
   * Validate Setting the Test Object to a new Test Object (Console Logger instead of File Logger).
   */
  @Test(groups = TestCategories.Framework)
  public void setTestObjectTest() {
    Logger logger = new ConsoleLogger();
    BaseTestObject baseTestObject = new BaseTestObject(logger,
        this.getFullyQualifiedTestClassName());
    this.setTestObject(baseTestObject);

    Assert.assertTrue(this.getTestObject().getLog() instanceof ConsoleLogger,
        "Expected Test Object to be set to have a Console Logger.");
  }

  @Test(groups = TestCategories.Framework)
  public void testSetPerformanceCollection() {
    PerfTimerCollection perfTimerCollection = new PerfTimerCollection(this.getLogger(),
        this.getFullyQualifiedTestClassName());
    this.setPerfTimerCollection(perfTimerCollection);
  }

  @Test(groups = TestCategories.Framework)
  public void testGetPerformanceCollection() {
    PerfTimerCollection perfTimerCollection = new PerfTimerCollection(this.getLogger(),
        this.getFullyQualifiedTestClassName());
    this.setPerfTimerCollection(perfTimerCollection);
    Assert.assertNotNull(this.getPerfTimerCollection());
  }

  @Test(groups = TestCategories.Framework)
  public void testGetTestContext() {
    Assert.assertNotNull(this.getTestContext());
  }

  @Test(groups = TestCategories.Framework)
  public void testSetTestContext() {
    final ITestContext testContext = this.getTestContext();
    testContext.setAttribute("testName", "SetTestContext");
    this.setTestContext(testContext);
    Assert.assertNotNull(this.getTestContext());
    Assert.assertEquals(this.getTestContext().getAttribute("testName"), "SetTestContext");
  }

  @Test(groups = TestCategories.Framework)
  public void testGetManagerStore() {
    Assert.assertNotNull(this.getManagerStore());
  }

  /*
   * (non-Javadoc)
   *
   * @see com.magenic.jmaqs.utilities.BaseTest.BaseTest#beforeLoggingTeardown(org.testng.
   * ITestResult)
   */
  @Override
  protected void beforeLoggingTeardown(ITestResult resultType) {

  }

}
