/*
 * Copyright 2019 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.utilities.logging;

import com.magenic.jmaqs.utilities.ConsoleCopy;
import com.magenic.jmaqs.utilities.helper.StringProcessor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import com.magenic.jmaqs.utilities.helper.TestCategories;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

/**
 * Unit test class for FileLogger.java
 */
@Test(singleThreaded = true, groups = TestCategories.Utilities)
public class FileLoggerUnitTest {
  @DataProvider(name = "logLevels")
  public static Object[][] data() {
    return new Object[][] {
      { "VERBOSE", new HashMap<String, Boolean>() {{
          put("VERBOSE", true); put("INFORMATION", true); put("GENERIC", true);
          put("SUCCESS", true); put("WARNING", true); put("ERROR", true);
        }}
      },
      { "INFORMATION", new HashMap<String, Boolean>() {{
          put("VERBOSE", false); put("INFORMATION", true); put("GENERIC", true);
          put("SUCCESS", true); put("WARNING", true); put("ERROR", true);
        }}
      },
      { "GENERIC", new HashMap<String, Boolean>() {{
          put("VERBOSE", false); put("INFORMATION", false); put("GENERIC", true);
          put("SUCCESS", true); put("WARNING", true); put("ERROR", true);
        }}
      },
      { "SUCCESS", new HashMap<String, Boolean>() {{
          put("VERBOSE", false); put("INFORMATION", false); put("GENERIC", false);
          put("SUCCESS", true); put("WARNING", true); put("ERROR", true);
        }}
      },
      { "WARNING", new HashMap<String, Boolean>() {{
          put("VERBOSE", false); put("INFORMATION", false); put("GENERIC", false);
          put("SUCCESS", false); put("WARNING", true); put("ERROR", true);
        }}
      },
      { "ERROR", new HashMap<String, Boolean>() {{
          put("VERBOSE", false); put("INFORMATION", false); put("GENERIC", false);
          put("SUCCESS", false); put("WARNING", false); put("ERROR", true);
        }}
      },
      { "SUSPENDED", new HashMap<String, Boolean>() {{
          put("VERBOSE", false); put("INFORMATION", false); put("GENERIC", false);
          put("SUCCESS", false); put("WARNING", false); put("ERROR", false);
        }}
      }
    };
  }

  /**
   * Verify the text file logger respects hierarchical logging
   * @param logLevel The type of logging.
   * @param levels  What should appear for each level.
   */
  @Test(dataProvider = "logLevels", groups = TestCategories.Utilities)
  public void testHierarchicalTxtFileLogger(String logLevel, HashMap<String, Boolean> levels) {
    FileLogger logger = new FileLogger(true,
            LoggingConfig.getLogDirectory(),
            this.getFileName("TestHierarchicalTxtFileLogger_" + logLevel,
                    "txt"),
            MessageType.GENERIC);
    this.testHierarchicalLogging(logger,
            logger.getFilePath(),
            logLevel,
            levels);
  }

  /**
   * Verify the console logger respects hierarchical logging
   * @param logLevel  The type of logging.
   * @param levels  What should appear for each level.
   */
  @Test(dataProvider = "logLevels", groups = TestCategories.Utilities)
  public void testHierarchicalConsoleLogger(String logLevel, HashMap<String, Boolean> levels) {
    // Calculate a file path
    String path = Paths.get(LoggingConfig.getLogDirectory(),
            this.getFileName("TestHierarchicalConsoleLogger" + logLevel,
                    "txt")).toString();

    // Pipe the console to this file
    try(ConsoleCopy ignored = new ConsoleCopy(path)) {
      ConsoleLogger consoleLogger = new ConsoleLogger();
      this.testHierarchicalLogging(consoleLogger, path, logLevel, levels);
    }
  }

  /**
   * Verify the Html File logger respects hierarchical logging
   * @param logLevel The type of logging.
   * @param levels What should appear for each level.
   */
  @Test(dataProvider = "logLevels", groups = TestCategories.Utilities)
  public void testHierarchicalHtmlFileLogger(String logLevel, HashMap<String, Boolean> levels) {
    HtmlFileLogger logger = new HtmlFileLogger(true,
            LoggingConfig.getLogDirectory(),
            this.getFileName("TestHierarchicalHtmlFileLogger" + logLevel,
                    "html"),
            MessageType.GENERIC);
    this.testHierarchicalLogging(logger,
            logger.getFilePath(),
            logLevel,
            levels);
  }

  /**
   * Base Test Method.
   * Each test method that you want to run
   * must have the [Test] attribute.
   */
  @Test(groups = TestCategories.Utilities)
  public void testFileLogger() {
    FileLogger logger = new FileLogger("", "TestFileLogger");
    logger.logMessage(MessageType.WARNING, "Hello");
  }

  /**
   * Test logging to a new file.
   */
  @Test(groups = TestCategories.Utilities)
  public void fileLoggerNoAppendTest() {
    FileLogger logger = new FileLogger(false,
            "",
            "WriteToFileLogger");
    logger.logMessage(MessageType.WARNING,
            "Hello, this is a test.");
  }

  /**
   * Test logging to an existing file.
   */
  @Test(groups = TestCategories.Utilities)
  public void fileLoggerAppendFileTest() {
    FileLogger logger = new FileLogger(true,
            "",
            "WriteToExistingFileLogger");
    logger.logMessage(MessageType.WARNING,
            "This is a test to write to an existing file.");
  }

  /**
   * Verify the logging suspension functions
   */
  @Test(groups = TestCategories.Utilities)
  public void testSuspendLogger() {
    SoftAssert softAssert = new SoftAssert();

    // Start logging
    FileLogger logger = new FileLogger(true,
            LoggingConfig.getLogDirectory(),
            this.getFileName("TestHierarchicalTxtFileLogger",
                    "txt"),
            MessageType.GENERIC);
    logger.setLoggingLevel(MessageType.VERBOSE);

    logger.logMessage(MessageType.VERBOSE, "HellO");

    // Suspend logging
    logger.suspendLogging();
    logger.logMessage(MessageType.ERROR, "GoodByE");

    // Continue logging
    logger.continueLogging();
    logger.logMessage(MessageType.VERBOSE, "BacK");

    // Get the log file content
    String logContents = this.readTextFile(logger.getFilePath());

    // Verify that logging was active
    boolean helloFound = logContents.contains("HellO");
    softAssert.assertTrue(helloFound,
            "'HellO' was not found.  Logging Failed");

    // Verify that logging was suspended
    boolean goodbyeFound = logContents.contains("GoodByE");
    softAssert.assertFalse(goodbyeFound,
            "'GoodByE' was found when it should not be written. Logging Failed");

    // Verify that logging was active
    boolean backFound = logContents.contains("BacK");
    softAssert.assertTrue(backFound, "'BacK' was not found. Logging Failed");

    // Fail the test if any soft asserts failed
    softAssert.assertAll();
  }

  /**
   * Test Writing to the File Logger
   */
  @Test(groups = TestCategories.Utilities)
  public void writeToFileLogger() {
    FileLogger logger = new FileLogger("", "WriteToFileLogger");
    logger.logMessage(MessageType.WARNING, "Hello, this is a test.");
  }

  /**
   * Test Writing to an Existing File Logger
   */
  @Test(groups = TestCategories.Utilities)
  public void writeToExistingFileLogger() {
    FileLogger logger = new FileLogger(true, "",
            "WriteToExistingFileLogger",
            MessageType.GENERIC);
    logger.logMessage(MessageType.WARNING,
            "This is a test.");
    logger.logMessage(MessageType.WARNING,
            "This is a test to write to an existing file.");
  }

  /**
   * Verify FileLogger constructor creates the correct directory if it does not
   * already exist. Delete Directory after each run.
   * @throws IOException warning in case exception is thrown
   */
  @Test(groups = TestCategories.Utilities)
  public void fileLoggerConstructorCreateFile() throws IOException {
    String message = "Test to ensure that the file in the created directory can be written to.";
    FileLogger logger = new FileLogger(true,
            Paths.get(LoggingConfig.getLogDirectory(),
            "FileLoggerCreateDirectoryDelete").toString(),
            "FileLoggerCreateDirectory",
            MessageType.GENERIC);

    logger.logMessage(MessageType.WARNING,
            "Test to ensure that the file in the created directory can be written to.");

    File file = new File(logger.getFilePath());
    String actualMessage = this.readTextFile(file.getAbsolutePath());
    Assert.assertTrue(actualMessage.contains(message),
            "Expected '" + message + "' but got '"
                    + actualMessage + "' for: " + file.getCanonicalPath());
    Assert.assertTrue(file.delete(), "The File was not Deleted");
    }

  /**
   * Verify that File Logger can log message without defining a Message Type
   */
  @Test(groups = TestCategories.Utilities)
  public void fileLoggerLogMessage() {
    FileLogger logger = new FileLogger(true,
            "",
            "FileLoggerLogMessage");
    logger.logMessage("Test to ensure LogMessage works as expected.");
    Assert.assertTrue(this.readTextFile(logger.getFilePath())
                    .contains("Test to ensure LogMessage works as expected."),
            "Expected Log Message to be contained in log.");
  }

  /**
   * Verify that File Logger can log message and defining a Message Type
   */
  @Test(groups = TestCategories.Utilities)
  public void fileLoggerLogMessageSelectType() {
    FileLogger logger = new FileLogger(true,
            "",
            "FileLoggerLogMessage");
    logger.logMessage(MessageType.GENERIC,
            "Test to ensure LogMessage works as expected.");
    Assert.assertTrue(this.readTextFile(logger.getFilePath())
                    .contains("Test to ensure LogMessage works as expected."),
            "Expected Log Message to be contained in log.");
  }

  /**
   * Verify that File Path field can be accessed and updated
   */
  @Test(groups = TestCategories.Utilities)
  public void fileLoggerSetFilePath() {
    FileLogger logger = new FileLogger(true,
            "",
            "FileLoggerSetFilePath",
            MessageType.GENERIC);
    logger.setFilePath("test file path");
    Assert.assertEquals(logger.getFilePath(), "test file path");
  }

  /**
   * Verify that File Logger catches and
   * handles errors caused by incorrect file Paths
   */
  @Test(groups = TestCategories.Utilities)
  public void fileLoggerCatchThrownException() {
    FileLogger logger = new FileLogger(true,
            "",
            "FileLoggerCatchThrownException",
            MessageType.GENERIC);
    logger.setFilePath("<>");
    logger.logMessage(MessageType.GENERIC, "test throws error");
  }

  /**
   * Test File Logger with empty file
   * name throws Illegal Argument Exception.
   */
  @Test(expectedExceptions = IllegalArgumentException.class, groups = TestCategories.Utilities)
  public void fileLoggerEmptyFileNameException() {
    new FileLogger("");
  }

  /**
   * Verify File Logger with No Parameters
   * assigns the correct default values.
   */
  @Test(groups = TestCategories.Utilities)
  public void fileLoggerNoParameters() throws IOException {
    FileLogger logger = new FileLogger();

    SoftAssert softAssert = new SoftAssert();
    softAssert.assertEquals(System.getProperty("java.io.tmpdir"),
            logger.getDirectory(),
            StringProcessor.safeFormatter(
            "Expected Directory '%s'.",
                    System.getProperty("java.io.tmpdir")));
    softAssert.assertEquals("FileLog.txt", logger.getFileName(),
            "Expected correct File Name.");
    softAssert.assertEquals(MessageType.INFORMATION,
            logger.getMessageType(),
            "Expected Information Message Type.");

    File file = new File(logger.getFilePath());
    softAssert.assertTrue(file.createNewFile(), "File was not Created");
    softAssert.assertTrue(file.exists(), "File does not Exist");
    softAssert.assertTrue(file.delete(), "File was not deleted");
    softAssert.assertAll();
  }

  /**
   * Verify File Logger with only append
   * parameter assigns the correct default values.
   */
  @Test(groups = TestCategories.Utilities)
  public void fileLoggerAppendOnly() throws IOException {
    FileLogger logger = new FileLogger(true);

    SoftAssert softAssert = new SoftAssert();
    softAssert.assertEquals(System.getProperty("java.io.tmpdir"),
            logger.getDirectory(),
            StringProcessor.safeFormatter("Expected Directory '%s'.",
            System.getProperty("java.io.tmpdir")));
    softAssert.assertEquals("FileLog.txt", logger.getFileName(),
            "Expected correct File Name.");
    softAssert.assertEquals(MessageType.INFORMATION, logger.getMessageType(),
            "Expected Information Message Type.");

    File file = new File(logger.getFilePath());
    softAssert.assertTrue(file.createNewFile(), "File was not Created");
    softAssert.assertTrue(file.exists(), "File does not Exist");
    softAssert.assertTrue(file.delete(), "File was not deleted");
    softAssert.assertAll();
  }

  /**
   * Verify File Logger with only File Name
   * parameter assigns the correct default values.
   * Verify default extension is added '.txt'
   */
  @Test(groups = TestCategories.Utilities)
  public void fileLoggerNameOnlyAddExtension() throws IOException {
    FileLogger logger = new FileLogger("FileNameOnly");

    SoftAssert softAssert = new SoftAssert();
    softAssert.assertEquals(System.getProperty("java.io.tmpdir"),
            logger.getDirectory(),
            StringProcessor.safeFormatter("Expected Directory '%s'.",
                    System.getProperty("java.io.tmpdir")));
    softAssert.assertEquals("FileNameOnly.txt",
            logger.getFileName(),
            "Expected correct File Name.");
    softAssert.assertEquals(MessageType.INFORMATION,
            logger.getMessageType(),
            "Expected Information Message Type.");
    File file = new File(logger.getFilePath());
    softAssert.assertTrue(file.createNewFile(), "File was not Created");
    softAssert.assertTrue(file.exists(), "File does not Exist");
    softAssert.assertTrue(file.delete(), "File was not deleted");
    softAssert.assertAll();
  }

  /**
   * Verify File Logger with only Message Type
   * parameter assigns the correct default values.
   */
  @Test(groups = TestCategories.Utilities)
  public void fileLoggerMessageTypeOnly() throws IOException {
    FileLogger logger = new FileLogger(MessageType.WARNING);

    SoftAssert softAssert = new SoftAssert();
    softAssert.assertEquals(System.getProperty("java.io.tmpdir"),
            logger.getDirectory(),
            StringProcessor.safeFormatter("Expected Directory '%s'.",
                    System.getProperty("java.io.tmpdir")));
    softAssert.assertEquals("FileLog.txt", logger.getFileName(),
            "Expected correct File Name.");
    softAssert.assertEquals(MessageType.WARNING, logger.getMessageType(),
            "Expected Warning Message Type.");

    File file = new File(logger.getFilePath());
    softAssert.assertTrue(file.createNewFile(), "File was not Created");
    softAssert.assertTrue(file.exists(), "File does not Exist");
    softAssert.assertTrue(file.delete(), "File was not deleted");
    softAssert.assertAll();
  }

  /**
   * Verify File Logger with only Append and,
   * File Name parameters assigns the correct default values.
   */
  @Test(groups = TestCategories.Utilities)
  public void fileLoggerAppendFileName() throws IOException {
    FileLogger logger = new FileLogger(true, "AppendFileName");

    SoftAssert softAssert = new SoftAssert();
    softAssert.assertEquals(System.getProperty("java.io.tmpdir"),
            logger.getDirectory(),
            StringProcessor.safeFormatter("Expected Directory '%s'.",
                    System.getProperty("java.io.tmpdir")));
    softAssert.assertEquals("AppendFileName.txt", logger.getFileName(),
            "Expected correct File Name.");
    softAssert.assertEquals(MessageType.INFORMATION, logger.getMessageType(),
            "Expected Information Message Type.");

    File file = new File(logger.getFilePath());
    softAssert.assertTrue(file.createNewFile(), "File was not Created");
    softAssert.assertTrue(file.exists(), "File does not Exist");
    softAssert.assertTrue(file.delete(), "File was not deleted");
    softAssert.assertAll();
  }

  /**
   * Verify File Logger with only Log Folder and,
   * Append parameters assigns the correct default values.
   */
  @Test(groups = TestCategories.Utilities)
  public void fileLoggerAppendLogFolder() throws IOException {
    FileLogger logger = new FileLogger("Append File Directory", true);

    SoftAssert softAssert = new SoftAssert();
    softAssert.assertEquals("Append File Directory", logger.getDirectory(),
            "Expected Directory 'Append File Directory'.");
    softAssert.assertEquals("FileLog.txt",
            logger.getFileName(),
            "Expected correct File Name.");
    softAssert.assertEquals(MessageType.INFORMATION,
            logger.getMessageType(),
            "Expected Information Message Type.");

    File file = new File(logger.getFilePath());
    softAssert.assertTrue(file.createNewFile(), "File was not Created");
    softAssert.assertTrue(file.exists(), "File does not Exist");
    softAssert.assertTrue(file.getAbsoluteFile().delete(), "File was not deleted");
    softAssert.assertTrue(file.getParentFile().getAbsoluteFile().delete(), "File was not deleted");
    softAssert.assertAll();
  }

  /**
   * Verify File Logger with only Log Folder and,
   * File Name parameters assigns the correct default values.
   */
  @Test(groups = TestCategories.Utilities)
  public void fileLoggerLogFolderFileName() throws IOException {
    FileLogger logger = new FileLogger("Log Folder File Name Directory",
            "LogFolderFileName.txt");

    SoftAssert softAssert = new SoftAssert();
    softAssert.assertEquals("Log Folder File Name Directory",
            logger.getDirectory(),
            "Expected Directory 'Log Folder File Name Directory'.");
    softAssert.assertEquals("LogFolderFileName.txt",
            logger.getFileName(),
            "Expected correct File Name.");
    softAssert.assertEquals(MessageType.INFORMATION,
            logger.getMessageType(),
            "Expected Information Message Type.");

    File file = new File(logger.getFilePath());
    softAssert.assertTrue(file.createNewFile(), "File was not Created");
    softAssert.assertTrue(file.exists(), "File does not Exist");
    softAssert.assertTrue(file.delete(), "File was not deleted");
    softAssert.assertTrue(file.getParentFile().delete(), "Directory was not deleted");
    softAssert.assertAll();
  }

  /**
   * Verify File Logger with only Log Folder and,
   * Messaging Level parameters assigns the correct default values.
   */
  @Test(groups = TestCategories.Utilities)
  public void fileLoggerLogFolderMessagingLevel() throws IOException {
    FileLogger logger = new FileLogger("Log Folder Messaging Level Directory",
            MessageType.WARNING);

    SoftAssert softAssert = new SoftAssert();
    softAssert.assertEquals("Log Folder Messaging Level Directory", logger.getDirectory(),
            "Expected Directory 'Log Folder Messaging Level Directory'.");
    softAssert.assertEquals("FileLog.txt", logger.getFileName(), "Expected correct File Name.");
    softAssert.assertEquals(MessageType.WARNING, logger.getMessageType(), "Expected Warning Message Type.");

    File file = new File(logger.getFilePath());
    softAssert.assertTrue(file.createNewFile(), "File was not Created");
    softAssert.assertTrue(file.exists(), "File does not Exist");
    softAssert.assertTrue(file.delete(), "File was not deleted");
    softAssert.assertTrue(file.getParentFile().delete(), "Directory was not deleted");
    softAssert.assertAll();
  }

  /**
   * Verify File Logger with only Append and,
   * Messaging Level parameters assigns the correct default values.
   */
  @Test(groups = TestCategories.Utilities)
  public void fileLoggerAppendMessagingLevel() throws IOException {
    FileLogger logger = new FileLogger(true, MessageType.WARNING);

    SoftAssert softAssert = new SoftAssert();
    softAssert.assertEquals(System.getProperty("java.io.tmpdir"),
            logger.getDirectory(),
            StringProcessor.safeFormatter("Expected Directory '%s'.",
                    System.getProperty("java.io.tmpdir")));
    softAssert.assertEquals("FileLog.txt",
            logger.getFileName(),
            "Expected correct File Name.");
    softAssert.assertEquals(MessageType.WARNING,
            logger.getMessageType(),
            "Expected Warning Message Type.");

    File file = new File(logger.getFilePath());
    softAssert.assertTrue(file.createNewFile(), "File was not Created");
    softAssert.assertTrue(file.exists(), "File was not Created");
    softAssert.assertTrue(file.delete(), "File was not deleted");
    softAssert.assertAll();
  }

  /**
   * Verify File Logger with only Messaging Level and,
   * file name parameters assigns the correct default values.
   */
  @Test(groups = TestCategories.Utilities)
  public void fileLoggerMessagingLevelFileName() throws IOException {
    FileLogger logger = new FileLogger(MessageType.WARNING,
            "MessagingTypeFile.txt");

    SoftAssert softAssert = new SoftAssert();
    softAssert.assertEquals(System.getProperty("java.io.tmpdir"),
            logger.getDirectory(),
            StringProcessor.safeFormatter(
            "Expected Directory '%s'.",
                    System.getProperty("java.io.tmpdir")));
    softAssert.assertEquals("MessagingTypeFile.txt",
            logger.getFileName(),
            "Expected correct File Name.");
    softAssert.assertEquals(MessageType.WARNING,
            logger.getMessageType(),
            "Expected Warning Message Type.");

    File file = new File(logger.getFilePath());
    softAssert.assertTrue(file.getAbsoluteFile().createNewFile(), "File was not Created");
    softAssert.assertTrue(file.exists(), "File does not Exist");
    softAssert.assertTrue(file.getAbsoluteFile().delete(), "File was not deleted");
    softAssert.assertAll();
  }

  /**
   * Verify File Logger with only Append,
   * log folder and file name parameters assigns the correct default values.
   */
  @Test(groups = TestCategories.Utilities)
  public void fileLoggerAppendLogFolderFileName() throws IOException {
    FileLogger logger = new FileLogger(true,
            "AppendLogFolderFileNameDirectory",
            "AppendLogFolderFileName.txt");

    SoftAssert softAssert = new SoftAssert();
    softAssert.assertEquals("AppendLogFolderFileNameDirectory",
            logger.getDirectory(),
            " Expected Directory AppendLogFolderFileNameDirectory");
    softAssert.assertEquals("AppendLogFolderFileName.txt",
            logger.getFileName(),
            "Expected correct File Name.");
    softAssert.assertEquals(MessageType.INFORMATION,
            logger.getMessageType(),
            "Expected Information Message Type.");

    File file = new File(logger.getFilePath());
    softAssert.assertTrue(file.createNewFile(), "File was not Created");
    softAssert.assertTrue(file.exists(), "File does not Exist");
    softAssert.assertTrue(file.delete(), "File was not deleted");
    softAssert.assertAll();
  }

  /**
   * Verify File Logger with only Append,
   * log folder and Messaging Level parameters assigns the correct default values.
   */
  @Test(groups = TestCategories.Utilities)
  public void fileLoggerAppendLogFolderMessagingLevel() throws IOException {
    FileLogger logger = new FileLogger(
            true,
            "AppendLogFolderFileNameDirectory",
            MessageType.WARNING);

    SoftAssert softAssert = new SoftAssert();
    softAssert.assertEquals("AppendLogFolderFileNameDirectory",
            logger.getDirectory(),
            " Expected Directory AppendLogFolderFileNameDirectory");
    softAssert.assertEquals("FileLog.txt",
            logger.getFileName(),
            "Expected correct File Name.");
    softAssert.assertEquals(MessageType.WARNING,
            logger.getMessageType(),
            "Expected Warning Message Type.");

    File file = new File(logger.getFilePath());
    softAssert.assertTrue(file.createNewFile(), "File was not Created");
    softAssert.assertTrue(file.exists(), "File does not Exist");
    softAssert.assertTrue(file.getAbsoluteFile().delete(), "File was not deleted");
    softAssert.assertTrue(file.getParentFile().delete(), "Directory was not deleted");
    softAssert.assertAll();
  }

  /**
   * Verify File Logger with only File Name,
   * Append and Messaging Level parameters assigns the correct default values.
   */
  @Test(groups = TestCategories.Utilities)
  public void fileLoggerFileNameAppendMessagingLevel() throws IOException {
    FileLogger logger = new FileLogger("FileNameAppendMessagingLevel.txt",
            true,
            MessageType.WARNING);

    SoftAssert softAssert = new SoftAssert();
    softAssert.assertEquals(System.getProperty("java.io.tmpdir"),
            logger.getDirectory(),
            StringProcessor.safeFormatter("Expected Directory '%s'.",
                    System.getProperty("java.io.tmpdir")));
    softAssert.assertEquals("FileNameAppendMessagingLevel.txt",
            logger.getFileName(),
            "Expected correct File Name.");
    softAssert.assertEquals(MessageType.WARNING,
            logger.getMessageType(),
            "Expected Warning Message Type.");

    File file = new File(logger.getFilePath());
    softAssert.assertTrue(file.createNewFile(), "File was not Created");
    softAssert.assertTrue(file.exists(), "File does not Exist");
    softAssert.assertTrue(file.delete(), "File was not deleted");
    softAssert.assertAll();
  }

  /**
   * Verify File Logger with only Log Folder.
   * File Name and Messaging Level parameters assigns the correct default values.
   */
  @Test(groups = TestCategories.Utilities)
  public void fileLoggerLogFolderFileNameMessagingLevel() throws IOException {
    FileLogger logger = new FileLogger("LogFolderFileNameMessagingLevelDirectory",
            "LogFolderFileNameMessagingLevel.txt",
            MessageType.WARNING);

    SoftAssert softAssert = new SoftAssert();
    softAssert.assertEquals("LogFolderFileNameMessagingLevelDirectory",
            logger.getDirectory(),
            "Expected Directory 'LogFolderFileNameMessagingLevelDirectory'");
    softAssert.assertEquals("LogFolderFileNameMessagingLevel.txt",
            logger.getFileName(),
            "Expected correct File Name.");
    softAssert.assertEquals(MessageType.WARNING,
            logger.getMessageType(),
            "Expected Warning Message Type.");

    File file = new File(logger.getFilePath());
    softAssert.assertTrue(file.createNewFile(), "File was not Created");
    softAssert.assertTrue(file.exists(), "File does not Exist");
    softAssert.assertTrue(file.delete(), "File was not deleted");
    softAssert.assertTrue(file.getParentFile().delete(), "Directory was not deleted");
    softAssert.assertAll();
  }

  /**
   * Verify hierarchical logging is respected
   * @param logger The logger we are checking
   * @param filePath  Where the log output can be found
   * @param logLevelText The type of logging
   * @param levels What should appear for each level
   */
  private void testHierarchicalLogging(Logger logger,
                                       String filePath,
                                       String logLevelText,
                                       HashMap<String, Boolean> levels) {
    // Create a soft assert
    SoftAssert softAssert = new SoftAssert();

    // Get the log level
    MessageType logLevel = MessageType.valueOf(logLevelText);
    logger.setLoggingLevel(logLevel);

    // Set the logger options to set the log level and add log entries to the file
    logger.logMessage(logLevel, "\nThe Log level is set to " + logLevel);

    // Message template
    String logLine = "Test Log item %s";

    // Log the test messages
    logger.logMessage(MessageType.VERBOSE, logLine, MessageType.VERBOSE);
    logger.logMessage(MessageType.INFORMATION, logLine, MessageType.INFORMATION);
    logger.logMessage(MessageType.GENERIC, logLine, MessageType.GENERIC);
    logger.logMessage(MessageType.SUCCESS, logLine, MessageType.SUCCESS);
    logger.logMessage(MessageType.WARNING, logLine, MessageType.WARNING);
    logger.logMessage(MessageType.ERROR, logLine, MessageType.ERROR);

    // Get the file content
    String logContents = this.readTextFile(filePath);

    // Verify that only the logged messages at the log level or below are logged
    for(HashMap.Entry<String, Boolean> level : levels.entrySet()) {
      if ((!level.getKey().equals("Row")) && (!level.getKey().equals("LogLevel"))) {
        // Verify if the Message Type is found
        boolean logMessageFound = logContents.contains(String.format(logLine, level.getKey()));
        softAssert.assertEquals(Boolean.toString(logMessageFound), level.getValue().toString(),
                "Looking for '" + String.format(logLine, level.getKey()) + "' with Logger of type '"
                        + logLevel.name() + "'. \nLog Contents: " + logContents);
      }
    }

    // Set the log level so that the soft asserts log
    logger.setLoggingLevel(MessageType.VERBOSE);

    // Fail test if any soft asserts failed
    softAssert.assertAll();
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

  /**
   * Get a unique file name
   * @param testName Prepend text
   * @param extension The file extension
   * @return A unique file name
   */
  private String getFileName(String testName, String extension) {
    return StringProcessor.safeFormatter("UtilitiesUnitTesting.%s-%s.%s",
            testName,
            UUID.randomUUID().toString(),
            extension);
  }
}