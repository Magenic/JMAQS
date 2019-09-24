/*
 * Copyright 2019 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.utilities.logging;

import com.magenic.jmaqs.utilities.helper.StringProcessor;
import com.magenic.jmaqs.utilities.helper.TestCategories;
import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.IOException;
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
  @Test(groups = TestCategories.Utilities)
  public void htmlFileLoggerNoAppendTest() {
    HtmlFileLogger logger = new HtmlFileLogger(false, "", "WriteToHtmlFileLogger");
    logger.logMessage(MessageType.WARNING, "Hello, this is a test.");
    File file = new File(logger.getFilePath());
    Assert.assertTrue(file.delete(), "File was not deleted");
  }

  /**
   * Test logging to an existing file.
   */
  @Test(groups = TestCategories.Utilities)
  public void htmlFileLoggerAppendFileTest() {
    HtmlFileLogger logger = new HtmlFileLogger(true, "", "WriteToExistingHtmlFileLogger");
    logger.logMessage(MessageType.WARNING, "This is a test to write to an existing file.");
    logger.logMessage(MessageType.WARNING, "This is a test to append to current file.");
    
    File file = new File(logger.getFilePath());
    Assert.assertTrue(file.delete());
  }

  /**
   * Test Writing to the Html File Logger
   */
  @Test(groups = TestCategories.Utilities)
  public void writeToHtmlFileLogger() {
    HtmlFileLogger logger = new HtmlFileLogger("", "WriteToHtmlFileLogger");
    logger.logMessage(MessageType.WARNING, "Hello, this is a test.");

    File file = new File(logger.getFilePath());
    Assert.assertTrue(file.delete());
  }

  /**
   * Test Writing to an Existing Html File Logger
   */
  @Test(groups = TestCategories.Utilities)
  public void writeToExistingHtmlFileLogger() {
    HtmlFileLogger logger = new HtmlFileLogger(true,
            "",
            "WriteToExistingHtmlFileLogger",
            MessageType.GENERIC);
    logger.logMessage(MessageType.WARNING, "This is a test.");
    logger.logMessage(MessageType.WARNING, "This is a test to write to an existing file.");

    File file = new File(logger.getFilePath());
    Assert.assertTrue(file.delete());
  }

  /**
   * Verify HtmlFileLogger constructor creates the correct directory if it does not already exist.
   * Delete Directory after each run.
   */
  @Test(groups = TestCategories.Utilities)
  public void htmlFileLoggerConstructorCreateDirectory() {
    HtmlFileLogger logger = new HtmlFileLogger(true, Paths.get(LoggingConfig.getLogDirectory(),
            "HtmlFileLoggerCreateDirectoryDelete").toString(), "HtmlFileLoggerCreateDirectory", MessageType.GENERIC);
    logger.logMessage(MessageType.WARNING, "Test to ensure that the file in the created directory can be written to.");

    File file = new File(logger.getFilePath());
    Assert.assertTrue(this.readTextFile(logger.getFilePath()).contains(
            "Test to ensure that the file in the created directory can be written to."));
    Assert.assertTrue(file.delete());

    file = new File(logger.getDirectory());
    try {
      FileUtils.deleteDirectory(file);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Verify that HtmlFileLogger can log message without defining a Message Type
   */
  @Test(groups = TestCategories.Utilities)
  public void htmlFileLoggerLogMessage() {
    HtmlFileLogger logger = new HtmlFileLogger(true, "", "HtmlFileLoggerLogMessage");
    logger.logMessage("Test to ensure LogMessage works as expected.");
    String htmlText = this.readTextFile(logger.getFilePath());

    File file = new File(logger.getFilePath());
    Assert.assertTrue(file.exists(), "the File/log exists");
    Assert.assertTrue(htmlText.contains("Test to ensure LogMessage works as expected."),
            "Expected Log Message to be contained in log.");
  }

  /**
   * Verify that HTML File Logger can log message and defining a Message Type.
   */
  @Test(groups = TestCategories.Utilities)
  public void htmlFileLoggerLogMessageSelectType() {
    HtmlFileLogger logger = new HtmlFileLogger(true, "", "HtmlFileLoggerLogMessageType");
    logger.logMessage(MessageType.GENERIC, "Test to ensure LogMessage works as expected.");
    String htmlText = this.readTextFile(logger.getFilePath());
    Assert.assertTrue(htmlText.contains("Test to ensure LogMessage works as expected."),
            "Expected Log Message to be contained in log.");
  }

  /**
   * Verify that File Path field can be accessed and updated
   */
  @Test(groups = TestCategories.Utilities)
  public void htmlFileLoggerSetFilePath() throws IOException {
    SoftAssert softAssert = new SoftAssert();

    HtmlFileLogger logger = new HtmlFileLogger(true, "", "HtmlFileLoggerSetFilePath", MessageType.GENERIC);
    logger.setFilePath("test file path");
    String filePath = logger.getFilePath();

    File file = new File(logger.getFilePath());
    softAssert.assertTrue(file.createNewFile(), "New File was not created");
    softAssert.assertTrue(file.exists(), "File does not Exist");
    softAssert.assertTrue(file.delete());
    softAssert.assertEquals(filePath, "test file path", "Expected 'test file path' as file path");
    softAssert.assertAll();
  }

  /**
   * Verify that HTML File Logger catches and handles errors caused by incorrect file Paths
   */
  @Test(groups = TestCategories.Utilities)
  public void HtmlFileLoggerCatchThrownException() {
    HtmlFileLogger logger = new HtmlFileLogger(true,
            "",
            "HtmlFileLoggerCatchThrownException",
            MessageType.GENERIC);
    logger.setFilePath("<>");

    logger.logMessage(MessageType.GENERIC, "Test throws error as expected.");
  }

  /**
   * Verify that HTML File Logger catches and handles errors caused by incorrect file Paths.
   */
  @Test(expectedExceptions = IllegalArgumentException.class, groups = TestCategories.Utilities)
  public void FileLoggerEmptyFileNameException() {
    HtmlFileLogger logger = new HtmlFileLogger("");

    Assert.assertEquals(logger.getMessageType(), MessageType.ERROR, "Log type was not error");
  }
  
  /**
   * Verify File Logger with No Parameters assigns the correct default values.
   */
  @Test(groups = TestCategories.Utilities)
  public void FileLoggerNoParameters() {
    HtmlFileLogger logger = new HtmlFileLogger();

    SoftAssert softAssert = new SoftAssert();
    softAssert.assertEquals(System.getProperty("java.io.tmpdir"), logger.getDirectory(), StringProcessor.safeFormatter(
            "Expected Directory '%s'.", System.getProperty("java.io.tmpdir")));
    softAssert.assertEquals("FileLog.html", logger.getFileName(), "Expected correct File Name.");
    softAssert.assertEquals(MessageType.INFORMATION, logger.getMessageType(), "Expected Information Message Type.");

    File file = new File(logger.getFilePath());
    softAssert.assertTrue(file.delete());
    softAssert.assertAll();
  }

  /**
   * Verify File Logger with only append parameter assigns the correct default values.
   */
  @Test(groups = TestCategories.Utilities)
  public void FileLoggerAppendOnly() {
    HtmlFileLogger logger = new HtmlFileLogger(true);

    SoftAssert softAssert = new SoftAssert();
    softAssert.assertEquals(System.getProperty("java.io.tmpdir"), logger.getDirectory(), StringProcessor.safeFormatter(
            "Expected Directory '%s'.", System.getProperty("java.io.tmpdir")));
    softAssert.assertEquals("FileLog.html", logger.getFileName(), "Expected correct File Name.");
    softAssert.assertEquals(MessageType.INFORMATION, logger.getMessageType(), "Expected Information Message Type.");

    File file = new File(logger.getFilePath());
    softAssert.assertTrue(file.delete());
    softAssert.assertAll();
  }

  /**
   * Verify File Logger with only File Name parameter assigns the correct default values.
   * Verify default extension is added '.html'
   */
  @Test(groups = TestCategories.Utilities)
  public void FileLoggerNameOnlyAddExtension() {
    HtmlFileLogger logger = new HtmlFileLogger("FileNameOnly");

    SoftAssert softAssert = new SoftAssert();
    softAssert.assertEquals(System.getProperty("java.io.tmpdir"), logger.getDirectory(), StringProcessor.safeFormatter(
            "Expected Directory '%s'.", System.getProperty("java.io.tmpdir")));
    softAssert.assertEquals("FileNameOnly.html", logger.getFileName(), "Expected correct File Name.");
    softAssert.assertEquals(MessageType.INFORMATION, logger.getMessageType(), "Expected Information Message Type.");

    File file = new File(logger.getFilePath());
    softAssert.assertTrue(file.delete());
    softAssert.assertAll();
  }

  /**
   * Verify File Logger with only Message Type parameter assigns the correct default values.
   */
  @Test(groups = TestCategories.Utilities)
  public void FileLoggerMessageTypeOnly() {
    HtmlFileLogger logger = new HtmlFileLogger(MessageType.WARNING);

    SoftAssert softAssert = new SoftAssert();
    softAssert.assertEquals(System.getProperty("java.io.tmpdir"),
            logger.getDirectory(),
            StringProcessor.safeFormatter("Expected Directory '%s'.",
                    System.getProperty("java.io.tmpdir")));
    softAssert.assertEquals("FileLog.html",
            logger.getFileName(),
            "Expected correct File Name.");
    softAssert.assertEquals(MessageType.WARNING,
            logger.getMessageType(),
            "Expected Warning Message Type.");

    File file = new File(logger.getFilePath());
    softAssert.assertTrue(file.delete(), "File was not deleted");
    softAssert.assertAll();
  }

  /**
   * Verify File Logger with only Append and File Name parameters assigns the correct default values.
   */
  @Test(groups = TestCategories.Utilities)
  public void FileLoggerAppendFileName() {
    HtmlFileLogger logger = new HtmlFileLogger(true, "AppendFileName");

    SoftAssert softAssert = new SoftAssert();
    softAssert.assertEquals(System.getProperty("java.io.tmpdir"),
            logger.getDirectory(),
            StringProcessor.safeFormatter("Expected Directory '%s'.",
                    System.getProperty("java.io.tmpdir")));
    softAssert.assertEquals("AppendFileName.html",
            logger.getFileName(),
            "Expected correct File Name.");
    softAssert.assertEquals(MessageType.INFORMATION,
            logger.getMessageType(),
            "Expected Information Message Type.");

    File file = new File(logger.getFilePath());
    softAssert.assertTrue(file.delete(), "File was not deleted");
    softAssert.assertAll();
  }

  /**
   * Verify File Logger with only Log Folder and Append parameters assigns the correct default values.
   */
  @Test(groups = TestCategories.Utilities)
  public void FileLoggerAppendLogFolder() {
    HtmlFileLogger logger = new HtmlFileLogger("Append File Directory", true);

    SoftAssert softAssert = new SoftAssert();
    softAssert.assertEquals("Append File Directory",
            logger.getDirectory(),
            "Expected Directory 'Append File Directory'.");
    softAssert.assertEquals("FileLog.html",
            logger.getFileName(),
            "Expected correct File Name.");
    softAssert.assertEquals(MessageType.INFORMATION,
            logger.getMessageType(),
            "Expected Information Message Type.");

    File file = new File(logger.getFilePath());
    softAssert.assertTrue(file.delete(), "File was not deleted");
    softAssert.assertTrue(file.getParentFile().delete(), "Directory was not deleted");
    softAssert.assertAll();
  }

  /**
   * Verify File Logger with only Log Folder and File Name parameters assigns the correct default values.
   */
  @Test(groups = TestCategories.Utilities)
  public void FileLoggerLogFolderFileName() {
    HtmlFileLogger logger = new HtmlFileLogger("Log Folder File Name Directory",
            "LogFolderFileName.html");

    SoftAssert softAssert = new SoftAssert();
    softAssert.assertEquals("Log Folder File Name Directory",
            logger.getDirectory(),
            "Expected Directory 'Log Folder File Name Directory'.");
    softAssert.assertEquals("LogFolderFileName.html",
            logger.getFileName(),
            "Expected correct File Name.");
    softAssert.assertEquals(MessageType.INFORMATION,
            logger.getMessageType(),
            "Expected Information Message Type.");

    File file = new File(logger.getFilePath());
    softAssert.assertTrue(file.delete(), "File was not deleted");
    softAssert.assertTrue(file.getParentFile().delete(), "Directory was not deleted");
    softAssert.assertAll();
  }

  /**
   * Verify File Logger with only Log Folder and Messaging Level parameters assigns the correct default values.
   */
  @Test(groups = TestCategories.Utilities)
  public void FileLoggerLogFolderMessagingLevel() {
    HtmlFileLogger logger = new HtmlFileLogger("Log Folder Messaging Level Directory", MessageType.WARNING);

    SoftAssert softAssert = new SoftAssert();
    softAssert.assertEquals("Log Folder Messaging Level Directory", logger.getDirectory(),
            "Expected Directory 'Log Folder Messaging Level Directory'.");
    softAssert.assertEquals("FileLog.html", logger.getFileName(), "Expected correct File Name.");
    softAssert.assertEquals(MessageType.WARNING, logger.getMessageType(), "Expected Warning Message Type.");

    File file = new File(logger.getFilePath());
    softAssert.assertTrue(file.delete(), "File was not deleted");
    softAssert.assertTrue(file.getParentFile().delete(), "Directory was not deleted");
    softAssert.assertAll();
  }

  /**
   * Verify File Logger with only Append and Messaging Level parameters assigns the correct default values.
   */
  @Test(groups = TestCategories.Utilities)
  public void FileLoggerAppendMessagingLevel() {
    HtmlFileLogger logger = new HtmlFileLogger(true, MessageType.WARNING);

    SoftAssert softAssert = new SoftAssert();
    softAssert.assertEquals(System.getProperty("java.io.tmpdir"), logger.getDirectory(), StringProcessor.safeFormatter(
            "Expected Directory '%s'.", System.getProperty("java.io.tmpdir")));
    softAssert.assertEquals("FileLog.html", logger.getFileName(), "Expected correct File Name.");
    softAssert.assertEquals(MessageType.WARNING, logger.getMessageType(), "Expected Warning Message Type.");

    File file = new File(logger.getFilePath());
    softAssert.assertTrue(file.delete(), "File was not deleted");
    softAssert.assertAll();
  }

  /**
   * Verify File Logger with only Messaging Level and file name parameters assigns the correct default values.
   */
  @Test(groups = TestCategories.Utilities)
  public void FileLoggerMessagingLevelFileName() {
    HtmlFileLogger logger = new HtmlFileLogger(MessageType.WARNING, "MessagingTypeFile.html");

    SoftAssert softAssert = new SoftAssert();
    softAssert.assertEquals(System.getProperty("java.io.tmpdir"), logger.getDirectory(), StringProcessor.safeFormatter(
            "Expected Directory '%s'.", System.getProperty("java.io.tmpdir")));
    softAssert.assertEquals("MessagingTypeFile.html", logger.getFileName(), "Expected correct File Name.");
    softAssert.assertEquals(MessageType.WARNING, logger.getMessageType(), "Expected Warning Message Type.");

    File file = new File(logger.getFilePath());
    softAssert.assertTrue(file.delete(), "File was not deleted");
    softAssert.assertAll();
  }

  /**
   * Verify File Logger with only Append, log folder and file name parameters assigns the correct default values.
   */
  @Test(groups = TestCategories.Utilities)
  public void FileLoggerAppendLogFolderFileName() {
    HtmlFileLogger logger = new HtmlFileLogger(
            true,
            "AppendLogFolderFileNameDirectory",
            "AppendLogFolderFileName.html");

    SoftAssert softAssert = new SoftAssert();
    softAssert.assertEquals("AppendLogFolderFileNameDirectory", logger.getDirectory(),
            " Expected Directory AppendLogFolderFileNameDirectory");
    softAssert.assertEquals("AppendLogFolderFileName.html", logger.getFileName(), "Expected correct File Name.");
    softAssert.assertEquals(MessageType.INFORMATION, logger.getMessageType(), "Expected Information Message Type.");

    File file = new File(logger.getFilePath());
    softAssert.assertTrue(file.delete(), "File was not deleted");
    softAssert.assertTrue(file.getParentFile().delete(), "Directory was not deleted");
    softAssert.assertAll();
  }

  /**
   * Verify File Logger with only Append, log folder and Messaging Level parameters assigns the correct default values.
   */
  @Test(groups = TestCategories.Utilities)
  public void FileLoggerAppendLogFolderMessagingLevel() {
    HtmlFileLogger logger = new HtmlFileLogger(
            true, "AppendLogFolderFileNameDirectory", MessageType.WARNING);

    SoftAssert softAssert = new SoftAssert();
    softAssert.assertEquals("AppendLogFolderFileNameDirectory", logger.getDirectory(),
            " Expected Directory AppendLogFolderFileNameDirectory");
    softAssert.assertEquals("FileLog.html", logger.getFileName(), "Expected correct File Name.");
    softAssert.assertEquals(MessageType.WARNING, logger.getMessageType(), "Expected Warning Message Type.");

    File file = new File(logger.getFilePath());
    softAssert.assertTrue(file.delete(), "File was not deleted");
    softAssert.assertTrue(file.getParentFile().delete(), "Directory was not deleted");
    softAssert.assertAll();
  }

  /**
   * Verify File Logger with only File Name, Append and Messaging Level parameters assigns the correct default values.
   */
  @Test(groups = TestCategories.Utilities)
  public void FileLoggerFileNameAppendMessagingLevel() {
    HtmlFileLogger logger = new HtmlFileLogger(
            "FileNameAppendMessagingLevel.html", true, MessageType.WARNING);

    SoftAssert softAssert = new SoftAssert();
    softAssert.assertEquals(System.getProperty("java.io.tmpdir"),
            logger.getDirectory(),
            StringProcessor.safeFormatter("Expected Directory '%s'.", System.getProperty("java.io.tmpdir")));
    softAssert.assertEquals("FileNameAppendMessagingLevel.html", logger.getFileName(),
            "Expected correct File Name.");
    softAssert.assertEquals(MessageType.WARNING, logger.getMessageType(), "Expected Warning Message Type.");

    File file = new File(logger.getFilePath());
    softAssert.assertTrue(file.delete(), "File was not deleted");
    softAssert.assertAll();
  }

  /**
   * Verify File Logger with only Log Folder, File Name and Messaging Level parameters assigns the correct default values.
   */
  @Test(groups = TestCategories.Utilities)
  public void FileLoggerLogFolderFileNameMessagingLevel() {
    HtmlFileLogger logger = new HtmlFileLogger(
            "LogFolderFileNameMessagingLevelDirectory",
            "LogFolderFileNameMessagingLevel.html",
            MessageType.WARNING);

    SoftAssert softAssert = new SoftAssert();
    softAssert.assertEquals("LogFolderFileNameMessagingLevelDirectory", logger.getDirectory(),
            "Expected Directory 'LogFolderFileNameMessagingLevelDirectory'");
    softAssert.assertEquals("LogFolderFileNameMessagingLevel.html", logger.getFileName(),
            "Expected correct File Name.");
    softAssert.assertEquals(MessageType.WARNING, logger.getMessageType(), "Expected Warning Message Type.");

    File file = new File(logger.getFilePath());
    softAssert.assertTrue(file.delete(), "File was not deleted");
    softAssert.assertTrue(file.getParentFile().delete(), "Directory was not deleted");
    softAssert.assertAll();
  }

  /**
   * Verify that HTML File Logger catches and handles errors caused by empty file name.
   */
  @Test(expectedExceptions = IllegalArgumentException.class, groups = TestCategories.Utilities)
  public void HtmlFileLoggerEmptyFileNameException() {
      HtmlFileLogger logger = new HtmlFileLogger("");
      logger.logMessage("");
  }

  /**
   * Read a file and return it as a string
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