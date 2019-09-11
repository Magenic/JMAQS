/*
 * Copyright 2019 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.utilities.logging;

import com.magenic.jmaqs.utilities.helper.StringProcessor;
import com.magenic.jmaqs.utilities.logging.ConsoleColors.ConsoleColor;
import com.magenic.jmaqs.utilities.logging.ConsoleColors.FontColor;

/**
 * Helper class for logging to the console.
 */
public class ConsoleLogger extends Logger {
  /**
   * Initializes a new instance of the ConsoleLogger class.
   */
  public ConsoleLogger() {
    this(MessageType.INFORMATION);
  }

  /**
   * Initializes a new instance of the ConsoleLogger class.
   * @param level The logging level.
   */
  public ConsoleLogger(MessageType level) {
    super(level);
  }

  /**
   * Write the formatted message (one line) to the console as a generic message.
   * @param message The message text
   * @param args String format arguments
   */
  @Override
  public void logMessage(String message, Object... args) {
    this.writeLine(message, args);
  }

  /**
   * Write the formatted message (one line) to the console as the specified type.
   * @param messageType The type of message
   * @param message The message text
   * @param args String format arguments
   */
  @Override
  public void logMessage(MessageType messageType, String message, Object... args) {
    this.writeLine(messageType, message, args);
  }

  /**
   * Write the formatted message to the console as a generic message.
   * @param message The message text
   * @param args String format arguments
   */
  public void write(String message, Object... args) {
    this.writeToConsole(MessageType.INFORMATION, false, message, args);
  }

  /**
   * Write the formatted message to the console as a generic message
   * @param message The message text
   * @param args String format arguments
   */
  public void writeColor(String message, Object... args) {
    this.setColorWriteAndRestore(MessageType.INFORMATION, false, message, args);
  }

  /**
   * Write the formatted message to the console as the given message type.
   * @param type The type of message
   * @param message  The message text
   * @param args Message string format arguments
   */
  public void write(MessageType type, String message, Object... args) {
    this.writeToConsole(type, false, message, args);
  }

  /**
   * Write the formatted message followed by a line break to the console as a generic message.
   * @param message The message text
   * @param args String format arguments
   */
  private void writeLine(String message, Object... args) {
    this.writeToConsole(MessageType.INFORMATION, true, message, args);
  }

  /**
   * Write the formatted message followed by a line break to the console as the given message type.
   * @param type The type of message
   * @param message The message text
   * @param args Message string format arguments
   */
  private void writeLine(MessageType type, String message, Object... args) {
    this.writeToConsole(type, true, message, args);
  }

  /**
   * write the message to the console.
   * @param type The type of message
   * @param line Is this a write-line command, else it is just a write
   * @param message The log message
   * @param args  Message string format arguments
   */
  private void writeToConsole(MessageType type, boolean line, String message, Object... args) {
    // Just return if there is no message
    if (message == null || message.isEmpty() || !this.shouldMessageBeLogged(type)) {
      return;
    }

    String result = StringProcessor.safeFormatter(message, args);
    try {
      // If this a write-line command
      if (line) {
        System.out.println(result);
      } else {
        System.out.print(result);
      }
    } catch (Exception e) {
      System.out.println(StringProcessor.safeFormatter("Failed to write to the console because: %s",
          e.getMessage()));
    }
  }

  /**
   * Set the console colors
   * @param font The fort color
   * @param background The background color
   */
  private static void setConsoleColor(ConsoleColor background, FontColor font) {
/*
// TODO: set the console Font and Background Colors

    if (background == null) {
      background = ConsoleColor.BLACK;
    }

    ConsoleColor backgrounColor = background;
    FontColor fontColor = font;
    // Console.ForegroundColor = font;
    // Console.BackgroundColor = background;

 */
  }

  /**
   * Change the console color to match the message type, write the message and restore the previous console colors
   * @param type The type of message
   * @param line Is this a write-line command, else it is just a write
   * @param message The log message
   * @param args Message string format arguments
   */
  private void setColorWriteAndRestore(MessageType type, boolean line, String message, Object... args) {
    // Just return if there is no message or this type of message should not be logged
    if (message.isEmpty() || !this.shouldMessageBeLogged(type)) {
      return;
    }

    // TODO: Get the Current Console Font and Background Colors
    // Save the original console colors
    // ConsoleColor originalBackground = Console.BackgroundColor;
    // FontColor originalFont = Console.ForegroundColor;
    ConsoleColor originalBackground = ConsoleColor.BLACK;
    FontColor originalFont = FontColor.WHITE;

    // Update console colors
    setConsoleColor(type);
    String result = StringProcessor.safeFormatter(message, args);

    try {
      // If this a write-line command
      if (line) {
        logMessage(result);
      }
    } catch (Exception e) {
      logMessage(StringProcessor.safeFormatter("Failed to write to the console because: {0}", e.getMessage()));
    }

    // Cleanup after yourself
    setConsoleColor(originalBackground, originalFont);
  }

  /**
   * Set the console color based on the message type
   * @param type The type of message that will be written
   */
  private void setConsoleColor(MessageType type) {
    switch (type) {
      case SUSPENDED:
        // Suspended so we do nothing
        break;
      case VERBOSE:
        setConsoleColor(ConsoleColor.BLACK, FontColor.WHITE);
        break;
      case INFORMATION:
        setConsoleColor(ConsoleColor.BLUE, FontColor.WHITE);
        break;
      case GENERIC:
        setConsoleColor(null, FontColor.WHITE);
        break;
      case SUCCESS:
        setConsoleColor(null, FontColor.GREEN);
        break;
      case WARNING:
        setConsoleColor(null, FontColor.YELLOW);
        break;
      case ERROR:
        setConsoleColor(null, FontColor.RED);
        break;
      default:
        setConsoleColor(null, FontColor.YELLOW);
        logMessage(unknownMessageTypeMessage(type));
        setConsoleColor(null, FontColor.WHITE);
        break;
    }
  }
}