/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.selenium;

import com.magenic.jmaqs.utilities.helper.StringProcessor;
import com.magenic.jmaqs.utilities.logging.FileLogger;
import com.magenic.jmaqs.utilities.logging.LoggingConfig;
import com.magenic.jmaqs.utilities.logging.MessageType;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsDriver;

/**
 * The type Selenium utilities.
 */
public class SeleniumUtilities {

  /**
   * The format for the date.
   */
  private static final String DEFAULT_DATE_TIME_FORMAT = "uuuu-MM-dd-HH-mm-ss-SSSS";

  /**
   * String value of the log message prefix.
   */
  private static final String LOG_MESSAGE_PREFIX = "%s - %s%s";

  private SeleniumUtilities() {
  }

  /**
   * Capture screenshot boolean.
   *
   * @param webDriver  the web driver
   * @param testObject the test object
   * @return the boolean
   */
  public static boolean captureScreenshot(WebDriver webDriver, SeleniumTestObject testObject) {
    return captureScreenshot(webDriver, testObject, "");
  }

  /**
   * Capture screenshot boolean.
   *
   * @param webDriver  the web driver
   * @param testObject the test object
   * @param appendName the appended name
   * @return the boolean
   */
  public static boolean captureScreenshot(WebDriver webDriver, SeleniumTestObject testObject,
      String appendName) {
    try {
      // Check if we are using a file logger. If not, return false.
      if (!(testObject.getLogger() instanceof FileLogger)) {
        return false;
      }

      // Calculate the file name with Date Time Stamp
      String directory = ((FileLogger) testObject.getLogger()).getDirectory();
      String fileNameWithoutExtension = StringProcessor
          .safeFormatter(LOG_MESSAGE_PREFIX, testObject.getFullyQualifiedTestName(),
              DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT, Locale.getDefault())
                  .format(LocalDateTime.now(Clock.systemUTC())), appendName);
      captureScreenshot(webDriver, testObject, directory, fileNameWithoutExtension);

      testObject.getLogger().logMessage(MessageType.INFORMATION, "Screenshot saved.");
      return true;
    } catch (Exception exception) {
      testObject.getLogger().logMessage(MessageType.ERROR,
          String.format("Screenshot error: %s", exception.getMessage()));
      return false;
    }
  }

  /**
   * Capture screenshot string.
   *
   * @param webDriver                the web driver
   * @param testObject               the test object
   * @param directory                the directory
   * @param fileNameWithoutExtension the file name without extension
   * @return the string
   */
  public static String captureScreenshot(WebDriver webDriver, SeleniumTestObject testObject,
      String directory, String fileNameWithoutExtension) {
    File tempFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
    String path = calculateFileName(directory, fileNameWithoutExtension, ".png");

    // Make sure the directory exists
    validateDirectoryStructure(testObject, directory);

    // Try to copy the temporary file to desired file path
    try {
      copyFileToPath(tempFile, path);
    } catch (IOException exception) {
      testObject.getLogger().logMessage(MessageType.ERROR,
          String.format("Screenshot error: %s", exception.getMessage()));
    }

    testObject.addAssociatedFile(path);
    return path;
  }

  private static void copyFileToPath(File tempFile, String path) throws IOException {
    Files.copy(tempFile.toPath(), new File(path).toPath(), StandardCopyOption.COPY_ATTRIBUTES);
  }

  /**
   * Save page source boolean.
   *
   * @param webDriver  the web driver
   * @param testObject the test object
   * @return the boolean
   */
  public static boolean savePageSource(WebDriver webDriver, SeleniumTestObject testObject) {
    return savePageSource(webDriver, testObject, "");
  }

  /**
   * Save page source boolean.
   *
   * @param webDriver  the web driver
   * @param testObject the test object
   * @param appendName the appended name
   * @return the boolean
   */
  public static boolean savePageSource(WebDriver webDriver, SeleniumTestObject testObject,
      String appendName) {
    try {
      String path;

      // Check if we are using a file logger.
      if (!(testObject.getLogger() instanceof FileLogger)) {
        // Since this is not a file logger we will need to use a generic file name
        path = savePageSource(webDriver, testObject, LoggingConfig.getLogDirectory(),
            StringProcessor.safeFormatter(LOG_MESSAGE_PREFIX, "PageSource",
                DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT, Locale.getDefault())
                    .format(LocalDateTime.now(Clock.systemUTC())), appendName));
      } else {
        // Calculate the file name
        String directory = ((FileLogger) testObject.getLogger()).getDirectory();
        String fileNameWithoutExtension = StringProcessor
            .safeFormatter(LOG_MESSAGE_PREFIX, testObject.getFullyQualifiedTestName() + "_PS",
                DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT, Locale.getDefault())
                    .format(LocalDateTime.now(Clock.systemUTC())), appendName);

        path = savePageSource(webDriver, testObject, directory, fileNameWithoutExtension);
      }

      testObject.getLogger().logMessage(MessageType.INFORMATION, "Page Source saved: " + path);
      return true;
    } catch (Exception exception) {
      testObject.getLogger().logMessage(MessageType.ERROR,
          String.format("Page Source error: %s", exception.getMessage()));
      return false;
    }
  }

  /**
   * Save page source string.
   *
   * @param webDriver                the web driver
   * @param testObject               the test object
   * @param directory                the directory
   * @param fileNameWithoutExtension the file name without extension
   * @return the string
   */
  public static String savePageSource(WebDriver webDriver, SeleniumTestObject testObject,
      String directory, String fileNameWithoutExtension) {
    // Save the current page source into a string
    String pageSource = webDriver.getPageSource();

    // Make sure the directory exists
    validateDirectoryStructure(testObject, directory);

    // Calculate the file name
    String path = calculateFileName(directory, fileNameWithoutExtension, ".txt");

    try (FileWriter writer = new FileWriter(path, false)) {
      writer.write(pageSource);

      // Flush File Writer
      writer.flush();
    } catch (IOException exception) {
      testObject.getLogger().logMessage(MessageType.ERROR, "Failed to start File Writer.");
    }

    testObject.addAssociatedFile(path);
    return path;
  }

  private static String calculateFileName(String directory, String fileNameWithoutExtension,
      String fileExtension) {
    return Paths.get(directory, fileNameWithoutExtension + fileExtension).normalize().toString();
  }

  private static void validateDirectoryStructure(SeleniumTestObject testObject, String directory) {
    try {
      Path path = new File(directory).toPath();
      if (!path.toFile().isDirectory()) {
        Files.createDirectories(path);
      }
    } catch (IOException exception) {
      testObject.getLogger()
          .logMessage(MessageType.ERROR, "Failed to create directories: " + exception.getMessage());
    }
  }

  /**
   * Web element to web driver web driver.
   *
   * @param webElement the web element
   * @return the web driver
   */
  public static WebDriver webElementToWebDriver(WebElement webElement) {
    WebDriver driver;
    driver = ((WrapsDriver) webElement).getWrappedDriver();

    // If this an even firing wrapper get the base wrapper
    if (driver instanceof WrapsDriver) {
      return ((WrapsDriver) driver).getWrappedDriver();
    }
    return driver;
  }

  /**
   * Kill driver.
   *
   * @param webDriver the web driver
   */
  public static void killDriver(WebDriver webDriver) {
    try {
      webDriver.close();
    } finally {
      webDriver.quit();
    }
  }
}