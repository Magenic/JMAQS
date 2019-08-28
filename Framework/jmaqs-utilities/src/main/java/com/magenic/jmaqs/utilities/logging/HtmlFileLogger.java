/*
 * Copyright 2019 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.utilities.logging;

import com.magenic.jmaqs.utilities.helper.StringProcessor;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Helper class for adding logs to an HTML file. Allows configurable file path.
 */
public class HtmlFileLogger extends FileLogger implements AutoCloseable {
  /**
   * The default log name.
   */
  private static final String DEFAULTLOGNAME = "FileLog.html";

  /**
   * Default header for the HTML file, this gives us our colored text.
   */
  private static final String DEFAULTHTMLHEADER = "<!DOCTYPE html><html><header><title>Test Log</title></header><body>";

  /**
   * Initializes a new instance of the FileLogger class.
   */
  public HtmlFileLogger() {
    this(false, "", DEFAULTLOGNAME, MessageType.INFORMATION);
  }

  /**
   * Initializes a new instance of the HtmlFileLogger class.
   * @param append Append document if true
   */
  public HtmlFileLogger(boolean append) {
    this(append, "", DEFAULTLOGNAME, MessageType.INFORMATION);
  }

  /**
   * Initializes a new instance of the HtmlFileLogger class.
   * @param name File name
   */
  public HtmlFileLogger(String name) {
    this(false, "", name, MessageType.INFORMATION);
  }

  /**
   * Initializes a new instance of the HtmlFileLogger class.
   * @param messageLevel Messaging Level
   */
  public HtmlFileLogger(MessageType messageLevel) {
    this(false, "", DEFAULTLOGNAME, messageLevel);
  }

  /**
   * Initializes a new instance of the HtmlFileLogger class.
   * @param append Append document if true
   * @param name File name
   */
  public HtmlFileLogger(boolean append, String name) {
    this(append, "", name, MessageType.INFORMATION);
  }

  /**
   * Initializes a new instance of the HtmlFileLogger class.
   * @param logFolder Where log files should be saved
   * @param append  Append document if true
   */
  public HtmlFileLogger(String logFolder, boolean append) {
    this(append, logFolder, DEFAULTLOGNAME, MessageType.INFORMATION);
  }

  /**
   * Initializes a new instance of the HtmlFileLogger class.
   * @param logFolder Where log files should be saved
   * @param name File Name
   */
  public HtmlFileLogger(String logFolder, String name) {
    this(false, logFolder, name, MessageType.INFORMATION);
  }

  /**
   * Initializes a new instance of the HtmlFileLogger class.
   * @param logFolder Where log files should be saved
   * @param messageLevel Messaging Level
   */
  public HtmlFileLogger(String logFolder, MessageType messageLevel) {
    this(false, logFolder, DEFAULTLOGNAME, messageLevel);
  }

  /**
   * Initializes a new instance of the HtmlFileLogger class.
   * @param append  Append document if true
   * @param messageLevel Messaging Level
   */
  public HtmlFileLogger(boolean append, MessageType messageLevel) {
    this(append, "", DEFAULTLOGNAME, messageLevel);
  }

  /**
   * Initializes a new instance of the HtmlFileLogger class.
   * @param messageLevel Messaging Level
   * @param name File Name
   */
  public HtmlFileLogger(MessageType messageLevel, String name) {
    this(false, "", name, messageLevel);
  }

  /**
   * Initializes a new instance of the HtmlFileLogger class.
   * @param append Append document if true
   * @param logFolder Where log files should be saved
   * @param name File Name
   */
  public HtmlFileLogger(boolean append, String logFolder, String name) {
    this(append, logFolder, name, MessageType.INFORMATION);
  }

  /**
   * Initializes a new instance of the HtmlFileLogger class.
   * @param append Append document if true
   * @param logFolder Where log files should be saved
   * @param messageLevel  Messaging Level
   */
  public HtmlFileLogger(boolean append, String logFolder, MessageType messageLevel) {
    this(append, logFolder, DEFAULTLOGNAME, messageLevel);
  }

  /**
   * Initializes a new instance of the HtmlFileLogger class.
   *
   * @param name File Name
   * @param append Append document if true
   * @param messageLevel  Messaging Level
   */
  public HtmlFileLogger(String name, boolean append, MessageType messageLevel) {
    this(append, "", name, messageLevel);
  }

  /**
   * Initializes a new instance of the HtmlFileLogger class.
   * @param logFolder  Where log files should be saved
   * @param name File Name
   * @param messageLevel  Messaging Level
   */
  public HtmlFileLogger(String logFolder, String name, MessageType messageLevel) {
    this(false, logFolder, name, messageLevel);
  }

  /**
   * Initializes a new instance of the HtmlFileLogger class.
   * @param append True to append to an existing log file or false to overwrite it.
   *          If the file does not exist this, flag will have no affect.
   * @param logFolder  Where log files should be saved
   * @param name File Name
   * @param messageLevel Messaging Level
   */
  public HtmlFileLogger(Boolean append, String logFolder, String name, MessageType messageLevel) {
    super(append, logFolder, name, messageLevel);

    try (FileWriter writer = new FileWriter(this.getFilePath(), true)) {
      writer.write(DEFAULTHTMLHEADER);
    } catch (IOException e) {
      ConsoleLogger console = new ConsoleLogger();
      console.logMessage(MessageType.ERROR, StringProcessor.safeFormatter(
              "Failed to write to event log because: %s", e.getMessage()));
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see com.magenic.jmaqs.utilities.Logging.Logger #logMessage(java.lang.String,
   * java.lang.Object[])
   */
  @Override
  public void logMessage(String message, Object... args) {
    this.logMessage(MessageType.INFORMATION, message, args);
  }

  /*
   * (non-Javadoc)
   *
   * @see com.magenic.jmaqs.utilities.Logging.Logger #logMessage(com.magenic.jmaqs.utilities.
   * Logging.MessageType, java.lang.String, java.lang.Object[])
   */
  @Override
  public void logMessage(MessageType messageType, String message, Object... args) {
    // If the message level is greater that the current log level then do not log it.
    if (this.shouldMessageBeLogged(messageType)) {
      // Log the message
      try (FileWriter writer = new FileWriter(this.getFilePath(), true)) {
        Date dateObject = new Date();
        SimpleDateFormat format = new SimpleDateFormat(Logger.DEFAULT_DATE_FORMAT);
        String date = format.format(dateObject);

        // Set the style
        writer.write(this.getTextWithColorFlag(messageType));

        // Add the content
        writer.write(StringProcessor.safeFormatter("%s%s%s", System.lineSeparator(), System.lineSeparator(), date));
        writer.write(StringProcessor.safeFormatter("%s:\t", messageType.name()));
        writer.write(StringProcessor.safeFormatter(System.lineSeparator() + message, args));

        // Close off the style
        writer.write("</p>");

        // Close the pre tag when logging Errors
        if (messageType.name().equals("ERROR")) {
          writer.write("</pre>");
        }
      } catch (Exception e) {
        // Failed to write to the event log, write error to the console instead
        ConsoleLogger console = new ConsoleLogger();
        console.logMessage(MessageType.ERROR, StringProcessor.safeFormatter(
                "Failed to write to event log because: %s", e.getMessage()));
        console.logMessage(messageType, message, args);
      }
    }
  }

  /**
   * Gets the file extension.
   * @return File Extension
   */
  @Override
  protected String getExtension() {
    return ".html";
  }

  /**
   * Close the class and HTML file.
   */
  @Override
  public void close() {
    File file = new File(this.getFilePath());
    if (file.exists()) {
      try (FileWriter writer = new FileWriter(this.getFilePath(), true)) {
        writer.write("</body></html>");
      } catch (IOException e) {
        ConsoleLogger console = new ConsoleLogger();
        console.logMessage(MessageType.ERROR, StringProcessor.safeFormatter(
                "Failed to write to event log because: %s", e.getMessage()));
      }
    }

    // Suggest to JVM to run garbage collector. No guarantee that this will run.
    System.gc();
  }

  /**
   * Get the HTML style key for the given message type.
   * @param type The message type
   * @return  String - The HTML style key for the given message type
   */
  private String getTextWithColorFlag(MessageType type) {
    switch (type) {
      case VERBOSE:
        return "<p style =\"color:purple\">";
      case ERROR:
        return "<pre><p style=\"color:red\">";
      case GENERIC:
        return "<p style =\"color:black\">";
      case INFORMATION:
        return "<p style =\"color:blue\">";
      case SUCCESS:
        return "<p style=\"color:green\">";
      case WARNING:
        return "<p style=\"color:orange\">";
      default:
        System.out.println(this.unknownMessageTypeMessage(type));
        return "<p style=\"color:hotpink\">";
    }
  }
}