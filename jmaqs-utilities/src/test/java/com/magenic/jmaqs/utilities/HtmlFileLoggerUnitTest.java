/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.utilities;

import com.magenic.jmaqs.utilities.helper.StringProcessor;
import com.magenic.jmaqs.utilities.logging.HtmlFileLogger;
import com.magenic.jmaqs.utilities.logging.LoggingConfig;
import com.magenic.jmaqs.utilities.logging.MessageType;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Unit test class for HtmlFileLogger.java
 */
@Test
public class HtmlFileLoggerUnitTest {
  /**
   * Test logging to a new file.
   */
  @Test
  public void HtmlFileLoggerNoAppendTest() {
    HtmlFileLogger logger = new HtmlFileLogger(false, "", "WriteToHtmlFileLogger");
    logger.logMessage(MessageType.WARNING, "Hello, this is a test.");
    File file = new File(logger.getFilePath());
    logger.close();
    file.delete();
  }

  /**
   * Test logging to an existing file.
   */
  @Test
  public void HtmlFileLoggerAppendFileTest() {
    HtmlFileLogger logger = new HtmlFileLogger(true, "", "WriteToExistingHtmlFileLogger");
    logger.logMessage(MessageType.WARNING, "This is a test to write to an existing file.");
    logger.logMessage(MessageType.WARNING, "This is a test to append to current file.");

    File file = new File(logger.getFilePath());
    logger.close();
    file.delete();
  }

  /**
   * Test Writing to the Html File Logger
   */
  @Test
  public void WriteToHtmlFileLogger() {
    HtmlFileLogger logger = new HtmlFileLogger("", "WriteToHtmlFileLogger");
    logger.logMessage(MessageType.WARNING, "Hello, this is a test.");

    File file = new File(logger.getFilePath());
    logger.close();
    file.delete();
  }

  /**
   * Test Writing to an Existing Html File Logger
   */
  @Test
  public void WriteToExistingHtmlFileLogger() {
    HtmlFileLogger logger = new HtmlFileLogger(true, "", "WriteToExistingHtmlFileLogger", MessageType.GENERIC);
    logger.logMessage(MessageType.WARNING, "This is a test.");
    logger.logMessage(MessageType.WARNING, "This is a test to write to an existing file.");

    File file = new File(logger.getFilePath());
    logger.close();
    file.delete();
  }

  /**
   * Verify HtmlFileLogger constructor creates the correct directory if it does
   * not already exist. Delete Directory after each run.
   */
  /*
   * @Test public void HtmlFileLoggerConstructorCreateDirectory() { HtmlFileLogger
   * logger = new HtmlFileLogger(true, Paths.get(LoggingConfig.getLogDirectory(),
   * "HtmlFileLoggerCreateDirectoryDelete").toString(),
   * "HtmlFileLoggerCreateDirectory", MessageType.GENERIC);
   * logger.logMessage(MessageType.WARNING,
   * "Test to ensure that the file in the created directory can be written to.");
   * 
   * File file = new File(logger.getFilePath());
   * Assert.assertTrue(this.readTextFile(logger.getFilePath()).contains(
   * "Test to ensure that the file in the created directory can be written to."));
   * file.delete();
   * 
   * file = new File(logger.getDirectory()); try {
   * FileUtils.deleteDirectory(file); } catch (IOException e) {
   * e.printStackTrace(); } }
   */

  /**
   * Verify that HtmlFileLogger can log message without defining a Message Type
   */
  @Test
  public void HtmlFileLoggerLogMessage() {
    HtmlFileLogger logger = new HtmlFileLogger(true, "", "HtmlFileLoggerLogMessage");
    logger.logMessage("Test to ensure LogMessage works as expected.");
    String htmlText = this.readTextFile(logger.getFilePath());

    File file = new File(logger.getFilePath());
    logger.close();
    file.delete();

    Assert.assertTrue(htmlText.contains("Test to ensure LogMessage works as expected."),
        "Expected Log Message to be contained in log.");
  }

  /**
   * Verify that HTML File Logger can log message and defining a Message Type.
   */
  @Test
  public void HtmlFileLoggerLogMessageSelectType() {
    HtmlFileLogger logger = new HtmlFileLogger(true, "", "HtmlFileLoggerLogMessageType");
    logger.logMessage(MessageType.GENERIC, "Test to ensure LogMessage works as expected.");
    String htmlText = this.readTextFile(logger.getFilePath());

    File file = new File(logger.getFilePath());
    logger.close();
    file.delete();

    Assert.assertTrue(htmlText.contains("Test to ensure LogMessage works as expected."),
        "Expected Log Message to be contained in log.");
  }

  /**
   * Verify that File Path field can be accessed and updated
   */
  @Test
  public void HtmlFileLoggerSetFilePath() {
    HtmlFileLogger logger = new HtmlFileLogger(true, "", "HtmlFileLoggerSetFilePath", MessageType.GENERIC);
    logger.setFilePath("test file path");
    String filePath = logger.getFilePath();

    File file = new File(logger.getFilePath());
    logger.close();
    file.delete();
    Assert.assertEquals(filePath, "test file path", "Expected 'test file path' as file path");
  }

  /**
   * Verify that HTML File Logger catches and handles errors caused by incorrect
   * file Paths
   */
  @Test
  public void HtmlFileLoggerCatchThrownException() {
    HtmlFileLogger logger = new HtmlFileLogger(true, "", "HtmlFileLoggerCatchThrownException", MessageType.GENERIC);
    logger.setFilePath("<>");

    logger.logMessage(MessageType.GENERIC, "Test throws error as expected.");
    File file = new File(logger.getFilePath());
    logger.close();
    file.delete();
  }

  /**
   * Verify that HTML File Logger catches and handles errors caused by incorrect
   * file Paths.
   */
  @Test(expectedExceptions = IllegalArgumentException.class)
  public void FileLoggerEmptyFileNameException() {
    HtmlFileLogger logger = new HtmlFileLogger("");
    logger.close();
  }

  /**
   * Verify File Logger with No Parameters assigns the correct default values.
   */
  @Test
  public void FileLoggerNoParameters() {
    HtmlFileLogger logger = new HtmlFileLogger();

    SoftAssert softAssert = new SoftAssert();
    softAssert.assertEquals(System.getProperty("java.io.tmpdir"), logger.getDirectory(),
        StringProcessor.safeFormatter("Expected Directory '%s'.", System.getProperty("java.io.tmpdir")));
    softAssert.assertEquals("FileLog.html", logger.getFileName(), "Expected correct File Name.");
    softAssert.assertEquals(MessageType.INFORMATION, logger.getMessageType(), "Expected Information Message Type.");

    softAssert.assertAll();

    File file = new File(logger.getFilePath());
    logger.close();
    file.delete();
  }

  /**
   * Verify File Logger with only append parameter assigns the correct default
   * values.
   */
  @Test
  public void FileLoggerAppendOnly() {
    HtmlFileLogger logger = new HtmlFileLogger(true);

    SoftAssert softAssert = new SoftAssert();
    softAssert.assertEquals(System.getProperty("java.io.tmpdir"), logger.getDirectory(),
        StringProcessor.safeFormatter("Expected Directory '%s'.", System.getProperty("java.io.tmpdir")));
    softAssert.assertEquals("FileLog.html", logger.getFileName(), "Expected correct File Name.");
    softAssert.assertEquals(MessageType.INFORMATION, logger.getMessageType(), "Expected Information Message Type.");

    softAssert.assertAll();

    File file = new File(logger.getFilePath());
    logger.close();
    file.delete();
  }

  /**
   * Verify File Logger with only File Name parameter assigns the correct default
   * values. Verify default extension is added '.html'
   */
  @Test
  public void FileLoggerNameOnlyAddExtension() {
    HtmlFileLogger logger = new HtmlFileLogger("FileNameOnly");

    SoftAssert softAssert = new SoftAssert();
    softAssert.assertEquals(System.getProperty("java.io.tmpdir"), logger.getDirectory(),
        StringProcessor.safeFormatter("Expected Directory '%s'.", System.getProperty("java.io.tmpdir")));
    softAssert.assertEquals("FileNameOnly.html", logger.getFileName(), "Expected correct File Name.");
    softAssert.assertEquals(MessageType.INFORMATION, logger.getMessageType(), "Expected Information Message Type.");

    softAssert.assertAll();

    File file = new File(logger.getFilePath());
    logger.close();
    file.delete();
  }

  /**
   * Verify File Logger with only Message Type parameter assigns the correct
   * default values.
   */
  @Test
  public void FileLoggerMessageTypeOnly() {
    HtmlFileLogger logger = new HtmlFileLogger(MessageType.WARNING);

    SoftAssert softAssert = new SoftAssert();
    softAssert.assertEquals(System.getProperty("java.io.tmpdir"), logger.getDirectory(),
        StringProcessor.safeFormatter("Expected Directory '%s'.", System.getProperty("java.io.tmpdir")));
    softAssert.assertEquals("FileLog.html", logger.getFileName(), "Expected correct File Name.");
    softAssert.assertEquals(MessageType.WARNING, logger.getMessageType(), "Expected Warning Message Type.");

    softAssert.assertAll();

    File file = new File(logger.getFilePath());
    logger.close();
    file.delete();
  }

  /**
   * Verify File Logger with only Append and File Name parameters assigns the
   * correct default values.
   */
  @Test
  public void FileLoggerAppendFileName() {
    HtmlFileLogger logger = new HtmlFileLogger(true, "AppendFileName");

    SoftAssert softAssert = new SoftAssert();
    softAssert.assertEquals(System.getProperty("java.io.tmpdir"), logger.getDirectory(),
        StringProcessor.safeFormatter("Expected Directory '%s'.", System.getProperty("java.io.tmpdir")));
    softAssert.assertEquals("AppendFileName.html", logger.getFileName(), "Expected correct File Name.");
    softAssert.assertEquals(MessageType.INFORMATION, logger.getMessageType(), "Expected Information Message Type.");

    softAssert.assertAll();

    File file = new File(logger.getFilePath());
    logger.close();
    file.delete();
  }

  /**
   * Verify File Logger with only Log Folder and Append parameters assigns the
   * correct default values.
   */
  @Test
  public void FileLoggerAppendLogFolder() {
    final String append_file_directory_path = LoggingConfig.getLogDirectory() + "/" + "Append File Directory";
    HtmlFileLogger logger = new HtmlFileLogger(append_file_directory_path, true);

    SoftAssert softAssert = new SoftAssert();
    softAssert.assertEquals(append_file_directory_path, logger.getDirectory(),
        "Expected Directory 'Append File Directory'.");
    softAssert.assertEquals("FileLog.html", logger.getFileName(), "Expected correct File Name.");
    softAssert.assertEquals(MessageType.INFORMATION, logger.getMessageType(), "Expected Information Message Type.");

    logger.close();

    softAssert.assertAll();
  }

  /**
   * Verify File Logger with only Log Folder and File Name parameters assigns the
   * correct default values.
   */
  @Test
  public void FileLoggerLogFolderFileName() {
    final String log_folder_file_name_directory = LoggingConfig.getLogDirectory() + "/"
        + "Log Folder File Name Directory";
    HtmlFileLogger logger = new HtmlFileLogger(log_folder_file_name_directory, "LogFolderFileName.html");

    SoftAssert softAssert = new SoftAssert();
    softAssert.assertEquals(log_folder_file_name_directory, logger.getDirectory(),
        "Expected Directory 'Log Folder File Name Directory'.");
    softAssert.assertEquals("LogFolderFileName.html", logger.getFileName(), "Expected correct File Name.");
    softAssert.assertEquals(MessageType.INFORMATION, logger.getMessageType(), "Expected Information Message Type.");

    logger.close();

    softAssert.assertAll();
  }

  /**
   * Verify File Logger with only Log Folder and Messaging Level parameters
   * assigns the correct default values.
   */
  @Test
  public void FileLoggerLogFolderMessagingLevel() {
    final String log_folder_messaging_level_directory_path = LoggingConfig.getLogDirectory() + "/"
        + "Log Folder Messaging Level Directory";
    HtmlFileLogger logger = new HtmlFileLogger(log_folder_messaging_level_directory_path, MessageType.WARNING);

    SoftAssert softAssert = new SoftAssert();
    softAssert.assertEquals(log_folder_messaging_level_directory_path, logger.getDirectory(),
        "Expected Directory 'Log Folder Messaging Level Directory'.");
    softAssert.assertEquals("FileLog.html", logger.getFileName(), "Expected correct File Name.");
    softAssert.assertEquals(MessageType.WARNING, logger.getMessageType(), "Expected Warning Message Type.");

    logger.close();

    softAssert.assertAll();
  }

  /**
   * Verify File Logger with only Append and Messaging Level parameters assigns
   * the correct default values.
   */
  @Test
  public void FileLoggerAppendMessagingLevel() {
    HtmlFileLogger logger = new HtmlFileLogger(true, MessageType.WARNING);

    SoftAssert softAssert = new SoftAssert();
    softAssert.assertEquals(System.getProperty("java.io.tmpdir"), logger.getDirectory(),
        StringProcessor.safeFormatter("Expected Directory '%s'.", System.getProperty("java.io.tmpdir")));
    softAssert.assertEquals("FileLog.html", logger.getFileName(), "Expected correct File Name.");
    softAssert.assertEquals(MessageType.WARNING, logger.getMessageType(), "Expected Warning Message Type.");

    softAssert.assertAll();

    File file = new File(logger.getFilePath());
    logger.close();
    file.delete();
  }

  /**
   * Verify File Logger with only Messaging Level and file name parameters assigns
   * the correct default values.
   */
  @Test
  public void FileLoggerMessagingLevelFileName() {
    HtmlFileLogger logger = new HtmlFileLogger(MessageType.WARNING, "MessagingTypeFile.html");

    SoftAssert softAssert = new SoftAssert();
    softAssert.assertEquals(System.getProperty("java.io.tmpdir"), logger.getDirectory(),
        StringProcessor.safeFormatter("Expected Directory '%s'.", System.getProperty("java.io.tmpdir")));
    softAssert.assertEquals("MessagingTypeFile.html", logger.getFileName(), "Expected correct File Name.");
    softAssert.assertEquals(MessageType.WARNING, logger.getMessageType(), "Expected Warning Message Type.");

    softAssert.assertAll();

    File file = new File(logger.getFilePath());
    logger.close();
    file.delete();
  }

  /**
   * Verify File Logger with only Append, log folder and file name parameters
   * assigns the correct default values.
   */
  @Test
  public void FileLoggerAppendLogFolderFileName() {
    final String appendLogFolderFileNameDirectoryPath = LoggingConfig.getLogDirectory() + "/"
        + "AppendLogFolderFileNameDirectory";
    HtmlFileLogger logger = new HtmlFileLogger(true, appendLogFolderFileNameDirectoryPath,
        "AppendLogFolderFileName.html");

    SoftAssert softAssert = new SoftAssert();
    softAssert.assertEquals(appendLogFolderFileNameDirectoryPath, logger.getDirectory(),
        " Expected Directory AppendLogFolderFileNameDirectory");
    softAssert.assertEquals("AppendLogFolderFileName.html", logger.getFileName(), "Expected correct File Name.");
    softAssert.assertEquals(MessageType.INFORMATION, logger.getMessageType(), "Expected Information Message Type.");

    logger.close();

    softAssert.assertAll();
  }

  /**
   * Verify File Logger with only Append, log folder and Messaging Level
   * parameters assigns the correct default values.
   */
  @Test
  public void FileLoggerAppendLogFolderMessagingLevel() {
    final String appendLogFolderFileNameDirectory = LoggingConfig.getLogDirectory() + "/"
        + "AppendLogFolderFileNameDirectory";
    HtmlFileLogger logger = new HtmlFileLogger(true, appendLogFolderFileNameDirectory, MessageType.WARNING);

    SoftAssert softAssert = new SoftAssert();
    softAssert.assertEquals(appendLogFolderFileNameDirectory, logger.getDirectory(),
        " Expected Directory AppendLogFolderFileNameDirectory");
    softAssert.assertEquals("FileLog.html", logger.getFileName(), "Expected correct File Name.");
    softAssert.assertEquals(MessageType.WARNING, logger.getMessageType(), "Expected Warning Message Type.");

    softAssert.assertAll();

    File file = new File(logger.getFilePath());
    logger.close();
    file.delete();
  }

  /**
   * Verify File Logger with only File Name, Append and Messaging Level parameters
   * assigns the correct default values.
   */
  @Test
  public void FileLoggerFileNameAppendMessagingLevel() {
    HtmlFileLogger logger = new HtmlFileLogger("FileNameAppendMessagingLevel.html", true, MessageType.WARNING);

    SoftAssert softAssert = new SoftAssert();
    softAssert.assertEquals(System.getProperty("java.io.tmpdir"), logger.getDirectory(),
        StringProcessor.safeFormatter("Expected Directory '%s'.", System.getProperty("java.io.tmpdir")));
    softAssert.assertEquals("FileNameAppendMessagingLevel.html", logger.getFileName(), "Expected correct File Name.");
    softAssert.assertEquals(MessageType.WARNING, logger.getMessageType(), "Expected Warning Message Type.");
    softAssert.assertAll();

    File file = new File(logger.getFilePath());
    logger.close();
    file.delete();
  }

  /**
   * Verify File Logger with only Log Folder, File Name and Messaging Level
   * parameters assigns the correct default values.
   */
  @Test
  public void FileLoggerLogFolderFileNameMessagingLevel() {
    final String logFolderFileNameMessagingLevelDirectoryPath = LoggingConfig.getLogDirectory() + "/"
        + "LogFolderFileNameMessagingLevelDirectory";
    HtmlFileLogger logger = new HtmlFileLogger(logFolderFileNameMessagingLevelDirectoryPath,
        "LogFolderFileNameMessagingLevel.html", MessageType.WARNING);

    SoftAssert softAssert = new SoftAssert();
    softAssert.assertEquals(logFolderFileNameMessagingLevelDirectoryPath, logger.getDirectory(),
        "Expected Directory 'LogFolderFileNameMessagingLevelDirectory'");
    softAssert.assertEquals("LogFolderFileNameMessagingLevel.html", logger.getFileName(),
        "Expected correct File Name.");
    softAssert.assertEquals(MessageType.WARNING, logger.getMessageType(), "Expected Warning Message Type.");

    softAssert.assertAll();

    File file = new File(logger.getFilePath());
    logger.close();
    file.delete();
  }

  /**
   * Verify that HTML File Logger catches and handles errors caused by empty file
   * name.
   */
  @Test(expectedExceptions = IllegalArgumentException.class)
  public void HtmlFileLoggerEmptyFileNameException() {
    HtmlFileLogger logger = new HtmlFileLogger("");
    logger.close();
  }

  /**
   * Read a file and return it as a string
   *
   * @param filePath The file path to read
   * @return The contents of the file
   */
  private String readTextFile(String filePath) {
    String text = "";
    try {
      text = new String(Files.readAllBytes(Paths.get(filePath)));
    } catch (Exception e) {
      e.printStackTrace();
    }

    return text;
  }
}
