/*
 * Copyright 2019 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.utilities.logging;

import com.magenic.jmaqs.utilities.helper.StringProcessor;

import java.util.Arrays;

/**
 * Abstract logging interface base class.
 */
public abstract class Logger {
  /**
   * Default date format.
   */
  protected static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

  /**
   * Log Level value area.
   */
  private MessageType logLevel = MessageType.INFORMATION;

  /**
   *  Log Level value save area.
   */
  private MessageType logLevelSaved = MessageType.SUSPENDED;

  /**
   * Initializes a new instance of the Logger class.
   */
  protected Logger() {
    this(MessageType.INFORMATION);
  }

  /**
    * Initializes a new instance of the Logger class.
   * @param level The logging level.
   */
  protected Logger(MessageType level) {
    this.logLevel = level;
  }

  /**
   * Set the logging level.
   * @param level The logging level.
   */
  public void setLoggingLevel(MessageType level) {
    this.logLevel = level;
  }

  /**
   * Suspends logging.
   */
  public void suspendLogging() {
    if (this.logLevel != MessageType.SUSPENDED) {
      this.logLevelSaved = this.logLevel;
      this.logLevel = MessageType.SUSPENDED;
      this.logMessage(MessageType.VERBOSE, "Suspending Logging..");
    }
  }

  /**
    * Continue logging after it was suspended.
    */
  public void continueLogging() {
    // Check if the logging was suspended
    if (this.logLevelSaved != MessageType.SUSPENDED) {
      // Return to the log level at the suspension of logging
      this.logLevel = this.logLevelSaved;
    }

    this.logLevelSaved = MessageType.SUSPENDED;
    this.logMessage(MessageType.VERBOSE, "Logging Continued..");
  }

  /**
   * Write the formatted message (one line) to the console as a generic message.
   * 
   * @param messageType
   *          The type of message
   * @param message
   *          The message text
   * @param args
   *          String format arguments
   */
  public abstract void logMessage(MessageType messageType, String message, Object... args);

  /**
   * Write the formatted message (one line) to the console as a generic message.
   * 
   * @param message
   *          The message text
   * @param args
   *          String format arguments
   */
  public abstract void logMessage(String message, Object... args);

  /**
   * Determine if the message should be logged.
   * The message should be logged if it's level is greater than or equal to the current logging level.
   *
   * @param messageType
   *          The type of message being logged.
   * @return
   *          True if the message should be logged.
   */
  boolean shouldMessageBeLogged(MessageType messageType) {
    // The message should be logged if it's level is less than or equal to the current logging level
    return messageType.getValue() <= this.logLevel.getValue();
  }

  /**
   * Get the message for an unknown message type.
   * @param type The message type
   * @return The unknown message type message
   */
  protected String unknownMessageTypeMessage(MessageType type) {
    return "Message will be displayed with the MessageType of: " + type + " " + MessageType.GENERIC;
  }

  /**
   * Log a verbose message and include the automation specific call stack data.
   * @param message Message to be written
   * @param args The arguments being used
   */
  public void logVerbose(String message, Object... args) {
    StringBuilder messages = new StringBuilder();
    messages.append(StringProcessor.safeFormatter(message, args));

    StackTraceElement[] stackTrace = new Throwable().getStackTrace();

    //var fullName = methodInfo.DeclaringType.FullName + "." + methodInfo.Name;
    //var methodInfo = System.MethodBase.GetCurrentMethod();
    String fullName = new Throwable().getStackTrace()[0].getMethodName();

    for (String stackLevel : Arrays.toString(stackTrace).split(System.lineSeparator(), -1)) {
      String trimmed = stackLevel.trim();

      // starts with hard coded will have to be changed to work with Java instead of C# log
      if (!trimmed.startsWith("at Microsoft.") && !trimmed.startsWith("at System.")
              && !trimmed.startsWith("at NUnit.") && !trimmed.startsWith("at " + fullName)) {
        messages.append(stackLevel);
      }
    }
  }
}