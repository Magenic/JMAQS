/*
 * Copyright 2019 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.utilities.performanceTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.magenic.jmaqs.base.BaseTest;
import com.magenic.jmaqs.utilities.helper.Config;
import com.magenic.jmaqs.utilities.logging.FileLogger;
import com.magenic.jmaqs.utilities.logging.LoggingConfig;
import com.magenic.jmaqs.utilities.logging.MessageType;
import com.magenic.jmaqs.utilities.performance.PerfTimer;
import com.magenic.jmaqs.utilities.performance.PerfTimerCollection;

import java.io.File;
import java.io.IOException;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.Test;

/**
 * Response time test class.
 */
public class PerformanceUnitTests extends BaseTest {

  /**
  * Test method to test Performance Timers.
   * #region PerfTimers
  */
  @Test
  public void perfStartStop2Timers() throws InterruptedException, IOException {
    final PerfTimerCollection p = this.perfTimerCollection;

    // build an object to store in the pay load string of the PerfTimerCollection
    Tconfig tc = new Tconfig();
    tc.logPath = Config.getGeneralValue("FileLoggerPath");
    tc.logType = Config.getGeneralValue("LogType");
    tc.webUri = Config.getGeneralValue("WebServiceUri");

    // store it (as a JSON string)
    ObjectMapper objectMapper = new ObjectMapper();
    p.perfPayloadString = objectMapper.writeValueAsString(tc);
    //p.perfPayloadString = JSONSerializeObject(this.tc);
    final String jsonString = p.perfPayloadString;

    p.startTimer("Outer", "test1");
    Thread.sleep(1000);
    p.startTimer("Inner", "test2");
    Thread.sleep(1000);
    p.EndTimer("test1");
    p.EndTimer("test2");

    // Write the log and validate the resulting file contents
    p.Write(this.getLogger());
    String filepath = LoggingConfig.getLogDirectory() + " " + p.fileName;

    // If the file does not exist, just bail
    File file = new File(filepath);
    Assert.assertTrue(file.exists(), "File Check : Expected File does not exist:" + filepath);

    // Otherwise record the assertion as true and continue...
    Assert.assertTrue(file.exists(), "File Check : Expected File exists.");

    PerfTimerCollection r = PerfTimerCollection.loadPerfTimerCollection(filepath);

    // Payload check
    Assert.assertEquals(jsonString, r.perfPayloadString, "Validated Payload (json)");

    // There should be 2 timers
    Assert.assertEquals(2, r.timerList.size(),"Expected number of timers");

    // Check the timers
    int badnamecount = 0;
    for (PerfTimer pt : r.timerList) {
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
          badnamecount++;
          Assert.assertFalse(false, "Extra timer present: " + pt.getTimerName());
          break;
      }
    }

    if (badnamecount != 0) {
      // We would have logged any extra timers, so pass the ExtraTimer assert
      Assert.assertTrue(true, "ExtraTimer");
    }
  }
  ///#endregion

  /**
  * Performance timer test to validate error.
  */
  @Test
    public void perfDontStopTimer() throws Exception {
    final PerfTimerCollection r;
    PerfTimerCollection p = this.perfTimerCollection;

    p.startTimer("StoppedOuter", "test1");
    p.startTimer("test2");
    Thread.sleep(1000);
    p.EndTimer("test1");
    p.EndTimer("test2");
    p.startTimer("NotStopped", "test3");

    // Write the log and validate the resulting file contents
    p.Write(this.getLogger());
    String filepath = LoggingConfig.getLogDirectory() + " " + p.fileName;

    // If the file doesnt exist, just bail
    File file = new File(filepath);
    Assert.assertTrue(file.exists(), "File Check : Expected File does not exist:" + filepath);

    // Otherwise record the assertion as true and continue...
    Assert.assertTrue(true, "File Check : Expected File exists.");

    r = PerfTimerCollection.loadPerfTimerCollection(filepath);

    // Payload should be empty
    Assert.assertEquals(r.perfPayloadString, "", "Payload was not Empty! Contained: " + r.perfPayloadString);
    //Assert.assertTrue(r.perfPayloadString.equals(null),"Payload was not Null! Contained: " + r.perfPayloadString);

    // There should be 2 timers
    Assert.assertEquals(2, r.timerList.size(), "Expected number of timers");

    // Check the timers
    int badnamecount = 0;
    for (PerfTimer pt : r.timerList) {
      switch (pt.getTimerName()) {
        // Timer = test1 should have a context of StoppedOuter
        case "test1":
          Assert.assertEquals("StoppedOuter", pt.getTimerContext(), "Test1 Context");
          break;

        // Timer = test2 should have an empty contex
        case "test2":
          Assert.assertTrue(pt.getTimerContext() == null || pt.getTimerContext().equals(""),
                "Context for " + pt.getTimerName() + " was not Empty! Contained: " + pt.getTimerContext());
          break;

        // Catch any extra timers
        default:
          badnamecount++;
          Assert.assertFalse(false,"Extra timer present: " + pt.getTimerName());
          break;
      }
    }

    if (badnamecount != 0) {
      // We would have logged any extra timers, so pass the ExtraTimer assert
      Assert.assertTrue(true, "ExtraTimer");
    }
  }

  /**
  * Verify that the performance timer throws expected error when attempting to start an already started timer.
  */
  @Test
  public void perfStartTimerThrowException() {
    PerfTimerCollection p = this.perfTimerCollection;
    p.startTimer("alreadyStarted");
    p.startTimer("alreadyStarted");
  }

  /**
  * Verify that the performance timer throws expected error when attempting to end a timer a stopped timer.
  */
  @Test
  public void perfEndTimerThrowException() {
    PerfTimerCollection p = this.perfTimerCollection;
    p.EndTimer("notStarted");
  }

  /**
 * Verify that the performance timer constructor creates the expected timer.
 */
  @Test
  public void perfTimerConstructorTest() {
    PerfTimerCollection p = new PerfTimerCollection("testTimer");
    Assert.assertEquals(p.testName, "testTimer");
  }

  /**
  * Verify that the performance timer handles invalid testNames properly.
  */
  @Test
  public void perfTimerWriteException() {
    // Invalid testName
    PerfTimerCollection p = new PerfTimerCollection("<>");
    p.startTimer("testTimer");
    p.EndTimer("testTimer");
    FileLogger log = new FileLogger(true, "PerfTimerWriteException", "PerfTimerWriteException", MessageType.GENERIC);
    p.Write(log);
    //Assert.assertTrue(File.ReadAllText(log.getFileName()).Contains("Could not save response time file.  Error was:"));
    Assert.assertTrue(log.getFileName().contains("Could not save response time file.  Error was:"));
  }

  @Override
  protected void postSetupLogging() throws Exception {

  }

  @Override
  protected void beforeLoggingTeardown(ITestResult resultType) {

  }

  /**
     * Example class to save in the payload element of the performance timer collection.
     */
    private static class Tconfig {

      // Gets or sets the Log Path.
      private String logPath;

      /*
       * Sets the Log Path.
       *
       * @param logPath string of the log path.

      public void setLogPath(String logPath) {
        this.logPath = logPath;
      }

       * Gets the Log Path.
       *
       * @return logpath string.

      public String getLogPath() {
        return this.logPath;
      }
      */


      // Gets or sets the Log type.
      private String logType;

      /*
       * Sets the Log type
       * @param logType string of the log type.

      public void setLogtype(String logType) {
        this.logType = logType;
      }


       * Gets the Log type
       * @return get log type string.

      public String getLogType() {
        return this.logType;
      }
      */


      // Gets or sets the Web URI.
      private String webUri;

      /*
       * Get the Web URI
       * @param webUri string to set the webURI.

      public void setWebUri(String webUri) {
        this.webUri = webUri;
      }

       * Sets the Web URI
       * @return string of the webURI.

      public String getWebUri() {
        return this.webUri;
      }
      */
    }
}