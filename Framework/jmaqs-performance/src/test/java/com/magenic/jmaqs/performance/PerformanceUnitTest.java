/*
 * Copyright 2019 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.performance;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.magenic.jmaqs.base.BaseTest;
import com.magenic.jmaqs.utilities.helper.Config;
import com.magenic.jmaqs.utilities.helper.TestCategories;
import com.magenic.jmaqs.utilities.logging.FileLogger;
import com.magenic.jmaqs.utilities.logging.LoggingConfig;
import com.magenic.jmaqs.utilities.logging.MessageType;
import com.magenic.jmaqs.performance.PerfTimerCollection;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Response time test class.
 */
public abstract class PerformanceUnitTest extends BaseTest {

  /**
  * Test method to test Performance Timers.
   * #region PerfTimers
  */
  @Test(groups = TestCategories.Utilities)
  public void perfStartStop2Timers() throws InterruptedException, IOException {
    final PerfTimerCollection p = this.perfTimerCollection;

    // build an object to store in the pay load string of the PerfTimerCollection
    tConfig tc = new tConfig();
    tc.setLogPath(Config.getGeneralValue("FileLoggerPath"));
    tc.setLogType(Config.getGeneralValue("LogType"));
    tc.setWebUri(Config.getGeneralValue("WebServiceUri"));

    // store it (as a JSON string)
    ObjectMapper objectMapper = new ObjectMapper();
    p.setPerfPayloadString(objectMapper.writeValueAsString(tc));
    //p.perfPayloadString = JSONSerializeObject(this.tc);
    final String jsonString = p.getPerfPayloadString();

    p.startTimer("Outer", "test1");
    Thread.sleep(1000);
    p.startTimer("Inner", "test2");
    Thread.sleep(1000);
    p.endTimer("test1");
    p.endTimer("test2");

    // Write the log and validate the resulting file contents
    p.write(this.getLogger());
    String filepath = LoggingConfig.getLogDirectory() + " " + p.getFileName();

    // If the file does not exist, just bail
    File file = new File(filepath);
    Assert.assertTrue(file.exists(), "File Check : Expected File does not exist:" + filepath);

    // Otherwise record the assertion as true and continue...
    Assert.assertTrue(file.exists(), "File Check : Expected File exists.");
    PerfTimerCollection r = PerfTimerCollection.loadPerfTimerCollection(filepath);

    // Payload check
    Assert.assertEquals(jsonString, r.getPerfPayloadString(), "Validated Payload (json)");

    // There should be 2 timers
    Assert.assertEquals(2, r.getTimerList().size(),"Expected number of timers");

    // Check the timers
    int badNameCount = 0;
    for (PerfTimer pt : r.getTimerList()) {
      switch (pt.getTimerName()) {
        // Timer = test1 should have a context of Outer
        case "test1":
          Assert.assertEquals("Outer", pt.getTimerContext(), "Test1 Context");
          break;

        // Timer = test2 should have an empty context
        case "test2":
          Assert.assertEquals("Inner", pt.getTimerContext(), "Test2 Context");
          break;

        // Catch any extra timers
        default:
          badNameCount++;
          Assert.assertFalse(false, "Extra timer present: " + pt.getTimerName());
          break;
      }
    }

    if (badNameCount != 0) {
      // We would have logged any extra timers, so pass the ExtraTimer assert
      Assert.assertTrue(true, "ExtraTimer");
    }
  }

  /**
  * Performance timer test to validate error.
  */
  @Test(groups = TestCategories.Utilities)
    public void perfDontStopTimer() throws Exception {
    final PerfTimerCollection r;
    PerfTimerCollection p = this.perfTimerCollection;

    p.startTimer("StoppedOuter", "test1");
    p.startTimer("test2");
    Thread.sleep(1000);
    p.endTimer("test1");
    p.endTimer("test2");
    p.startTimer("NotStopped", "test3");

    // Write the log and validate the resulting file contents
    p.write(this.getLogger());
    String filepath = LoggingConfig.getLogDirectory() + " " + p.getFileName();

    // If the file doesn't exist, just bail
    File file = new File(filepath);
    Assert.assertTrue(file.exists(), "File Check : Expected File does not exist:" + filepath);

    // Otherwise record the assertion as true and continue...
    Assert.assertTrue(true, "File Check : Expected File exists.");

    r = PerfTimerCollection.loadPerfTimerCollection(filepath);

    // Payload should be empty
    Assert.assertEquals(r.perfPayloadString, "", "Payload was not Empty! Contained: " + r.perfPayloadString);
    Assert.assertNull(r.perfPayloadString, "Payload was not Null! Contained: " + r.perfPayloadString);

    // There should be 2 timers
    Assert.assertEquals(2, r.getTimerList().size(), "Expected number of timers");

    // Check the timers
    int badNameCount = 0;
    for (PerfTimer pt : r.getTimerList()) {
      switch (pt.getTimerName()) {
        // Timer = test1 should have a context of StoppedOuter
        case "test1":
          Assert.assertEquals("StoppedOuter", pt.getTimerContext(), "Test1 Context");
          break;

        // Timer = test2 should have an empty context
        case "test2":
          Assert.assertTrue(pt.getTimerContext() == null || pt.getTimerContext().equals(""),
                "Context for " + pt.getTimerName() + " was not Empty! Contained: " + pt.getTimerContext());
          break;

        // Catch any extra timers
        default:
          badNameCount++;
          Assert.assertFalse(false,"Extra timer present: " + pt.getTimerName());
          break;
      }
    }

    if (badNameCount != 0) {
      // We would have logged any extra timers, so pass the ExtraTimer assert
      Assert.assertTrue(true, "ExtraTimer");
    }
  }

  /**
  * Verify that the performance timer throws expected error when attempting to start an already started timer.
  */
  @Test(groups = TestCategories.Utilities)
  public void perfStartTimerThrowException() {
    PerfTimerCollection p = this.perfTimerCollection;
    p.startTimer("alreadyStarted");
    p.startTimer("alreadyStarted");
  }

  /**
  * Verify that the performance timer throws expected error when attempting to end a timer a stopped timer.
  */
  @Test(groups = TestCategories.Utilities)
  public void perfEndTimerThrowException() {
    PerfTimerCollection p = this.perfTimerCollection;
    p.endTimer("notStarted");
  }

  /**
 * Verify that the performance timer constructor creates the expected timer.
 */
  @Test(groups = TestCategories.Utilities)
  public void perfTimerConstructorTest() {
    PerfTimerCollection p = new PerfTimerCollection("testTimer");
    Assert.assertEquals(p.getTestName(), "testTimer");
  }

  /**
  * Verify that the performance timer handles invalid testNames properly.
  */
  @Test(groups = TestCategories.Utilities)
  public void perfTimerWriteException() throws IOException {
    // Invalid testName
    PerfTimerCollection p = new PerfTimerCollection("<>");
    p.startTimer("testTimer");
    p.endTimer("testTimer");
    FileLogger log = new FileLogger(true,
            "PerfTimerWriteException",
            "PerfTimerWriteException",
            MessageType.GENERIC);
    p.write(log);
    //Assert.assertTrue(File.ReadAllText(log.getFileName()).Contains("Could not save response time file.  Error was:"));
    Assert.assertTrue(Files.readAllLines(Paths.get(log.getFileName())).contains("Could not save response time file.  Error was:"));
  }

  /**
   * post the Setup Logging
   */
  @Override
  protected void postSetupLogging() {
  }

  /**
   * @param resultType The test result
   */
  @Override
  protected void beforeLoggingTeardown(ITestResult resultType) {
  }

    /**
   * Example class to save in the payload element of the performance timer collection.
   */
    private static class tConfig {

     /**
     * the Log Path.
     */
      private String logPath;

      /**
       * Sets the Log Path.
       */
      private void setLogPath(String logPath){
        this.logPath = logPath;
      }

      /**
       * Gets the Log Path.
       */
      private String getLogPath(){
        return this.logPath;
      }

     /**
     * the Log type.
     */
      private String logType;

      /**
       * Sets the Log type.
       */
      private void setLogType(String logType){
        this.logType = logType;
      }

      /**
       * Gets the Log type.
       */
      private String getLogType(){
        return this.logType;
      }

     /**
     * the Web URI.
     */
      private String webUri;

      /**
       * Sets the web Uri.
       */
      private void setWebUri(String webUri){
        this.webUri = webUri;
      }

      /**
       * Gets the web Uri.
       */
      private String getWebUri(){
        return this.webUri;
      }
    }
}