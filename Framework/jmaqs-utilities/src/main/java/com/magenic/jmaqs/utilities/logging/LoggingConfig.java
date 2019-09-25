/*
 * Copyright 2019 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.utilities.logging;

import com.magenic.jmaqs.utilities.helper.Config;
import com.magenic.jmaqs.utilities.helper.StringProcessor;

/**
 * Get logging config data.
 */
public class LoggingConfig {
  /**
   * Get our logging state - Yes, no or on failure.
   * @return The log enabled state.
   */
  public static LoggingEnabled getLoggingEnabledSetting() {
    switch (Config.getGeneralValue("Log", "NO").toUpperCase()) {
      case "YES":
        return LoggingEnabled.YES;
      case "ONFAIL":
        return LoggingEnabled.ONFAIL;
      case "NO":
        return LoggingEnabled.NO;
      default:
        throw new IllegalArgumentException(StringProcessor.safeFormatter(
                "Log value %s is not a valid option",
                Config.getGeneralValue("Log", "NO")));
    }
  }

  /**
   * Get our logging level.
   * @return MessageType - The current log level.
   */
  static MessageType getLoggingLevelSetting() {
    switch (Config.getGeneralValue("LogLevel", "INFORMATION").toUpperCase()) {
      case "VERBOSE":
        return MessageType.VERBOSE; // Includes this and all of those below
      case "INFORMATION":
        return MessageType.INFORMATION; // Includes this and all of those below
      case "GENERIC":
        return MessageType.GENERIC; // Includes this and all of those below
      case "SUCCESS":
        return MessageType.SUCCESS; // Includes this and all of those below
      case "WARNING":
        return MessageType.WARNING; // Includes this and all of those below
      case "ERROR":
        return MessageType.ERROR;   // Includes errors only
      case "SUSPENDED":
        return MessageType.SUSPENDED;  // All logging is suspended
      default:
        throw new IllegalArgumentException(StringProcessor.safeFormatter(
                "Logging level value '{0}' is not a valid option",
                Config.getGeneralValue("LogLevel")));
    }
  }

  /**
   * Get the logger.
   * @param fileName File name to use for the log.
   * @return The logger.
   */
  public static Logger getLogger(final String fileName) {
    // Disable logging means we just send any logged messages to the console
    if (getLoggingEnabledSetting() == LoggingEnabled.NO) {
      return new ConsoleLogger();
    }

    String logDirectory = getLogDirectory();

    switch (Config.getGeneralValue("LogType", "CONSOLE").toUpperCase()) {
      case "CONSOLE":
        return new ConsoleLogger();
      case "TXT":
        return new FileLogger(false, logDirectory, fileName);
      default:
        throw new IllegalArgumentException(StringProcessor.safeFormatter(
            "Log type %s is not a valid option",
                Config.getGeneralValue("LogType",
                        "CONSOLE")));
    }
  }

  /**
   * Gets the File Directory to store log files.
   * @return String of file path.
   */
  public static String getLogDirectory() {
    String path = "";
    try {
      path = LoggingConfig.class.getProtectionDomain().getCodeSource()
              .getLocation().toURI().getPath();
    } catch (Exception ignored) {
    }
    /*
    String path = Path.Combine(
    Path.GetDirectoryName(Assembly.GetExecutingAssembly().Location), "Logs");
    return Config.getGeneralValue("FileLoggerPath", path);

    String path = new File("").getAbsolutePath().concat("\\Logs");
    return Config.getGeneralValue("FileLoggerPath", path);
  */
    return Config.getGeneralValue("FileLoggerPath", path);
  }
}
