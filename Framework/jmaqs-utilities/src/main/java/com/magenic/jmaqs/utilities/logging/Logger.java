/*
 * Copyright 2019 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.utilities.logging;

import com.magenic.jmaqs.utilities.helper.StringProcessor;

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
  private MessageType logLevel;

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
   *
   * @param level
   *          The logging level.
   */
  protected Logger(MessageType level) {
    this.setLoggingLevel(level);
  }

  /**
   * Set the logging level.
   *
   * @param level
   *          The logging level.
   */
  void setLoggingLevel(MessageType level) {
    this.logLevel = level;
  }

  private MessageType getLoggingLevel(){
    return this.logLevel;
  }

  public void setLogLevelSaved(MessageType logLevelSaved){
    this.logLevelSaved = logLevelSaved;
  }

  public MessageType getLogLevelSaved(){
    return this.logLevelSaved;
  }

  /**
   * Suspends logging.
   */
  public void suspendLogging() {
    if (this.getLoggingLevel() != MessageType.SUSPENDED) {
      this.setLogLevelSaved(this.getLoggingLevel());
      this.setLoggingLevel(MessageType.SUSPENDED);
      this.logMessage(MessageType.VERBOSE, "Suspending Logging..");
    }
  }

  /**
    * Continue logging after it was suspended.
    */
  public void continueLogging() {
    // Check if the logging was suspended
    if (this.getLogLevelSaved() != MessageType.SUSPENDED) {
      // Return to the log level at the suspension of logging
      this.setLoggingLevel(this.getLogLevelSaved());
    }

    this.setLogLevelSaved(MessageType.SUSPENDED);
    this.logMessage(MessageType.VERBOSE, "Logging Continued..");
  }

  /**
   * Write the formatted message (one line) to the console as a generic message.
   * @param messageType The type of message
   * @param message The message text
   * @param args String format arguments
   */
  public abstract void logMessage(MessageType messageType, String message, Object... args);

    /**
     * Write the formatted message (one line) to the console as a generic message.
     *
     * @param message The message text
     * @param args    String format arguments
     */
    public void logMessage(String message, Object... args) {
      logMessage(message);
    }

    /**
   * Determine if the message should be logged.
   * The message should be logged if it's level is greater than or equal to the current logging level.
   * @param messageType The type of message being logged.
   * @return True if the message should be logged.
   */
  protected boolean shouldMessageBeLogged(MessageType messageType) {
    // The message should be logged if it's level is less than or equal to the current logging level
    return messageType.getValue() <= this.getLoggingLevel().getValue();
  }

  /**
   * Get the message for an unknown message type.
   * @param type The Message Type.
   * @return The Unknown Message Type Message.
   */
  protected String unknownMessageTypeMessage(MessageType type) {
    return StringProcessor.safeFormatter("Unknown MessageType: %s%s%s%s", type.name(),
            System.lineSeparator(), "Message will be displayed with the MessageType of: ", MessageType.GENERIC.name());
  }
}