/*
 * Copyright 2019 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.utilities.logging;

import static com.magenic.jmaqs.utilities.logging.MessageType.INFORMATION;

import com.magenic.jmaqs.utilities.helper.StringProcessor;
import com.magenic.jmaqs.utilities.logging.console.ConsoleColor;
import com.magenic.jmaqs.utilities.logging.console.FontColor;
import com.magenic.jmaqs.utilities.logging.console.Attribute;
//import org.fusesource.jansi.AnsiConsole;

/**
 * Helper class for logging to the console.
 */
public class ConsoleLogger extends Logger {
  /**
   * Initializes a new instance of the ConsoleLogger class.
   */
  public ConsoleLogger() { this(INFORMATION); }

  /**
   * Initializes a new instance of the ConsoleLogger class.
   * @param level The logging level
   */
  public ConsoleLogger(final MessageType level) { super(level); }

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
   * Write the formatted message (one line)
   * to the console as the specified type.
   * @param messageType The type of message
   * @param message The message text
   * @param args String format arguments
   */
  @Override
  public void logMessage(MessageType messageType,
                         String message, Object... args) {
    this.writeLine(messageType, message, args);
  }

  /**
   * Write the formatted message to
   * the console as a generic message.
   * @param message The message text
   * @param args String format arguments
   */
  public void write(String message, Object... args) {
    this.setColorWriteAndRestore(INFORMATION, false, message, args);
  }

  /**
   * Write the formatted message to
   * the console as the given message type.
   * @param type The type of message
   * @param message The message text
   * @param args Message string format arguments
   */
  public void write(MessageType type, String message, Object... args) {
    this.setColorWriteAndRestore(type, false, message, args);
  }

  /**
   * Write the formatted message followed by
   * a line break to the console as a generic message.
   * @param message The message text
   * @param args String format arguments
   */
  public void writeLine(String message, Object... args) {
    this.setColorWriteAndRestore(INFORMATION, true, message, args);
  }

  /**
   * Write the formatted message followed by a line
   * break to the console as the given message type.
   * @param type The type of message
   * @param message The message text
   * @param args Message string format arguments
   */
  public void writeLine(MessageType type, String message, Object... args) {
    this.setColorWriteAndRestore(type, true, message, args);
  }

  /**
   * Set the console colors.
   * @param font The font type of the message
   * @return String of the console Font and Background Colors
   */
  private static String setConsoleColors(FontColor font) {
    return setConsoleColors(Attribute.Clear, font, ConsoleColor.Black);
  }

  /**
   * Set the console colors.
   * @param font The font color of the message
   * @param console The background color of the color
   * @return String of the console Font and Background Colors
   */
  private static String setConsoleColors(FontColor font, ConsoleColor console) {
    return setConsoleColors(Attribute.Clear, font, console);
  }

  /**
   * Set the console colors.
   * @param font The font color of the message
   * @param console The background color of the color
   * @param attribute The Attribute type of the font
   * @return String of the console Font and Background Colors
   */
  private static String setConsoleColors(Attribute attribute, FontColor font,
                                         ConsoleColor console) {
    return "\\033[" + attribute.getCode() + ";" + font.getCode() + ";" + console.getCode() + "m";
  }

  /**
   * write the message to the console.
   * @param type The type of message
   * @param line Is this a write-line command, else it is just a write
   * @param message  The log message
   * @param args Message string format arguments
   */
  private void writeToConsole(MessageType type,
                              boolean line, String message, Object... args) {
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
   * Change the console color to match the message type,
   * write the message and restore the previous console colors.
   * @param type the message type
   * @param line bool to write to the command line
   * @param message  message that is to be written
   * @param args object of messages to be written
   */
  private void setColorWriteAndRestore(MessageType type,
                                       boolean line, String message, Object... args) {
    // Just return if there is no message or this type of message should not be logged
    if ((message.isEmpty() && message == null) || !this.shouldMessageBeLogged(type)) {
      return;
    }

    // Save the original console colors
    final FontColor originalFont = FontColor.White;
    final ConsoleColor originalBack = ConsoleColor.Black;
    final Attribute attribute = Attribute.Clear;

    // Update console colors
    setConsoleColor(type);

    String result = null;

    for (int i = 0; i <= args.length; i++) {
      result += args[i].toString() + " ";
    }

    try {
      // If this is a write-line command
      if (line) {
        System.out.print(result);
      } else {
        System.out.print(result);
      }
    } catch (Exception e) {
      System.out.print("Failed to write to the console because: " + e.getMessage());
    }

    // Cleanup after yourself
    setConsoleColors(attribute, originalFont, originalBack);
  }

  /**
   * Set the console color based on the message type.
   * @param type The type of message that will be written
   * @return String of the console Font and Background
   */
  private String setConsoleColor(MessageType type) {
    switch (type) {
      case SUSPENDED:
        // Suspended so we do nothing
        return "";
      case VERBOSE:
        return setConsoleColors(FontColor.Black, ConsoleColor.White);
      case INFORMATION:
        return setConsoleColors(FontColor.Blue, ConsoleColor.White);
      case GENERIC:
        return setConsoleColors(FontColor.White);
      case SUCCESS:
        return setConsoleColors(FontColor.Green);
      case WARNING:
        return setConsoleColors(FontColor.Yellow);
      case ERROR:
        return setConsoleColors(FontColor.Red);
      default:
        System.out.print(this.unknownMessageTypeMessage(type));
        return setConsoleColors(FontColor.White);
    }
  }

  /**
   * Writes to the console in color.
   * @param msg The message that will be written
   * @param ansiFormatCode Code that dictates the console Font and Background color
   * TODO Get color output onto console
   */
  public void printWithColor(Object msg, String ansiFormatCode) {
    StringBuilder output = new StringBuilder();
    output.append(ansiFormatCode);
    output.append(msg);
    this.writeToConsole(MessageType.ERROR, true, output.toString());
    //AnsiConsole.out.println(output.toString());
  }
}