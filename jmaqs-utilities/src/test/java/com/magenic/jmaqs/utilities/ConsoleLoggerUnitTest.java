/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.utilities;

import com.magenic.jmaqs.utilities.logging.ConsoleLogger;
import com.magenic.jmaqs.utilities.logging.MessageType;
import org.testng.annotations.Test;

/**
 * Unit test class for ConsoleLogger.java
 */
@Test
public class ConsoleLoggerUnitTest {
  /**
   * Log message to a new console logger
   */
  @Test
  public void consoleLoggerLogMessage() {
    ConsoleLogger console = new ConsoleLogger();
    console.logMessage("Test String %s %s", "args1", "args2");
  }

  /**
   * Log message to a new console logger using defined message type
   */
  @Test
  public void consoleLoggerLogMessageSelectType() {
    ConsoleLogger console = new ConsoleLogger();
    console.logMessage(MessageType.GENERIC, "Test String %s", "args1");
  }

  /**
   * Write message to new console logger
   */
  @Test
  public void consoleLoggerWriteMessage() {
    ConsoleLogger console = new ConsoleLogger();
    console.write("Test String %s %s", "args1", "args2");
  }

  /**
   * Write message to new console logger using defined message type
   */
  @Test
  public void consoleLoggerWriteMessageSelectType() {
    ConsoleLogger console = new ConsoleLogger();
    console.write(MessageType.GENERIC, "TestString %s", "args1");
  }

    /**
     * Write message with new line to new console logger
     */
    @Test
    public void consoleLoggerWriteLineMessage() {
        ConsoleLogger console = new ConsoleLogger();
        console.write("Test String %s %s", "args1", "args2");
    }

    /**
     * Write message with new line to new console logger using defined message type
     */
    @Test
    public void consoleLoggerWriteMessageLineSelectType() {
        ConsoleLogger console = new ConsoleLogger();
        console.write(MessageType.GENERIC, "TestString %s", "args1");
    }
}
