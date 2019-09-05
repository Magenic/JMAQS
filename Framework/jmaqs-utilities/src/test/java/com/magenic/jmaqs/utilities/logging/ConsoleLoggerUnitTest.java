/*
 * Copyright 2019 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.utilities.logging;

import com.magenic.jmaqs.utilities.helper.TestCategories;
import org.testng.annotations.Test;

/**
 * Unit test class for ConsoleLogger.java
 */
@Test
public class ConsoleLoggerUnitTest {
  /**
   * Log message to a new console logger
   */
  @Test(groups = TestCategories.Utilities)
  public void consoleLoggerLogMessage() {
    ConsoleLogger console = new ConsoleLogger();
    console.logMessage("Test String %s %s", "args1", "args2");
  }

  /**
   * Write message to new console logger
   */
  @Test(groups = TestCategories.Utilities)
  public void consoleLoggerWriteMessage() {
    ConsoleLogger console = new ConsoleLogger();
    console.write("Test String %s %s", "args1", "args2");
  }

  /**
   * Log message to a new console logger using defined message type
   */
  @Test(groups = TestCategories.Utilities)
  public void consoleLoggerLogMessageSelectType() {
    ConsoleLogger console = new ConsoleLogger();
    console.logMessage(MessageType.GENERIC, "Test String %s", "args1");
  }


  /**
   * Write message to new console logger using defined message type
   */
  @Test(groups = TestCategories.Utilities)
  public void consoleLoggerWriteMessageSelectType() {
    ConsoleLogger console = new ConsoleLogger();
    console.write(MessageType.GENERIC, "TestString %s", "args1");
  }

    /**
     * Write message with new line to new console logger
     */
    @Test(groups = TestCategories.Utilities)
    public void consoleLoggerWriteLineMessage() {
        ConsoleLogger console = new ConsoleLogger();
        console.write("Test String %s %s", "args1", "args2");
    }

    /**
     * Write message with new line to new console logger using defined message type
     */
    @Test(groups = TestCategories.Utilities)
    public void consoleLoggerWriteMessageLineSelectType() {
        ConsoleLogger console = new ConsoleLogger();
        console.write(MessageType.GENERIC, "TestString %s", "args1");
    }
}