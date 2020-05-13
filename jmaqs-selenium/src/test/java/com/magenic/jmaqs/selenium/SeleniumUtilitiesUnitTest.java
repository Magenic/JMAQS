/*
 * Copyright 2020 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.selenium;

import com.magenic.jmaqs.base.BaseGenericTest;
import com.magenic.jmaqs.selenium.factories.UIWaitFactory;
import com.magenic.jmaqs.utilities.helper.StringProcessor;
import com.magenic.jmaqs.utilities.helper.TestCategories;
import com.magenic.jmaqs.utilities.logging.ConsoleLogger;
import com.magenic.jmaqs.utilities.logging.FileLogger;
import java.io.File;
import java.nio.file.Paths;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * The type Selenium utilities test.
 */
public class SeleniumUtilitiesUnitTest extends BaseGenericTest {

  /**
   * Test capture screenshot no append.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void testCaptureScreenshotNoAppend() throws Exception {
    WebDriver webDriver = null;
    webDriver = WebDriverFactory.getDefaultBrowser();
    FileLogger fileLogger = (FileLogger) this.getTestObject().getLogger();
    SeleniumTestObject testObject = new SeleniumTestObject(webDriver, fileLogger,
        this.getTestObject().getFullyQualifiedTestName());
    this.setTestObject(testObject);

    // Open Google and take a screenshot
    webDriver.navigate().to("http://www.google.com");
    boolean isSuccess = SeleniumUtilities.captureScreenshot(webDriver, testObject);

    // Assert screenshot was successful
    Assert.assertTrue(isSuccess, "Expected Screenshot to be successful.");
    String[] arrayOfAssociatedFiles = testObject.getArrayOfAssociatedFiles();
    Assert
        .assertTrue(new File((arrayOfAssociatedFiles[arrayOfAssociatedFiles.length - 1])).exists(),
            "Checking that expected file path exists");
  }

  /**
   * Test capture screenshot append.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void testCaptureScreenshotAppend() throws Exception {
    WebDriver webDriver = null;
    webDriver = WebDriverFactory.getDefaultBrowser();
    FileLogger fileLogger = (FileLogger) this.getTestObject().getLogger();
    SeleniumTestObject testObject = new SeleniumTestObject(webDriver, fileLogger,
        this.getTestObject().getFullyQualifiedTestName());
    this.setTestObject(testObject);

    // Open Google and take a screenshot
    webDriver.navigate().to("http://www.google.com");
    final String testAppend = "testAppend";
    boolean isSuccess = SeleniumUtilities.captureScreenshot(webDriver, testObject, testAppend);

    // Assert screenshot was successful
    Assert.assertTrue(isSuccess, "Expected Screenshot to be successful.");
    String[] arrayOfAssociatedFiles = testObject.getArrayOfAssociatedFiles();
    Assert
        .assertTrue(new File((arrayOfAssociatedFiles[arrayOfAssociatedFiles.length - 1])).exists(),
            "Checking that expected file path exists");
    Assert.assertTrue(
        new File((arrayOfAssociatedFiles[arrayOfAssociatedFiles.length - 1])).getName()
            .contains(testAppend), "Checking that appended value was added to file");
  }

  /**
   * Test capture screenshot console logger.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void testCaptureScreenshotConsoleLogger() throws Exception {
    WebDriver webDriver = WebDriverFactory.getDefaultBrowser();
    ConsoleLogger consoleLogger = new ConsoleLogger();
    SeleniumTestObject testObject = new SeleniumTestObject(webDriver, consoleLogger,
        this.getTestObject().getFullyQualifiedTestName());
    this.setTestObject(testObject);

    // Open Google and take a screenshot
    webDriver.navigate().to("http://www.google.com");
    boolean isSuccess = SeleniumUtilities.captureScreenshot(webDriver, testObject, "testAppend");

    // Assert screenshot was NOT successful
    Assert.assertFalse(isSuccess, "Expected Screenshot to NOT be successful.");
  }

  /**
   * Test capture screenshot custom directory file name.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void testCaptureScreenshotCustomDirectoryFileName() throws Exception {
    WebDriver webDriver = WebDriverFactory.getDefaultBrowser();
    FileLogger fileLogger = (FileLogger) this.getTestObject().getLogger();
    SeleniumTestObject testObject = new SeleniumTestObject(webDriver, fileLogger,
        this.getTestObject().getFullyQualifiedTestName());
    this.setTestObject(testObject);

    // Open Google and take a screenshot
    webDriver.navigate().to("http://www.google.com");
    String dateTime = DateTimeFormatter.ofPattern("uuuu-MM-dd-HH-mm-ss-SSSS", Locale.getDefault())
        .format(LocalDateTime.now(Clock.systemUTC()));
    String filePath = SeleniumUtilities
        .captureScreenshot(webDriver, testObject, fileLogger.getDirectory(),
            StringProcessor.safeFormatter("%s - %s", "TestCustomName", dateTime));

    Assert.assertEquals(filePath, Paths.get(StringProcessor
        .safeFormatter("%s%s - %s%s", fileLogger.getDirectory(), "\\TestCustomName", dateTime,
            ".png")).normalize().toString());
    Assert.assertTrue(new File(filePath).exists(),
        "Checking that screenshot file exists at expected path.");

  }

  /**
   * Test save page source no append.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void testSavePageSourceNoAppend() throws Exception {
    WebDriver webDriver = WebDriverFactory.getDefaultBrowser();
    FileLogger fileLogger = (FileLogger) this.getTestObject().getLogger();
    SeleniumTestObject testObject = new SeleniumTestObject(webDriver, fileLogger,
        this.getTestObject().getFullyQualifiedTestName());
    this.setTestObject(testObject);

    // Open Google and take a screenshot
    webDriver.navigate().to("http://www.google.com");
    boolean isSuccess = SeleniumUtilities.savePageSource(webDriver, testObject);

    // Assert screenshot was successful
    Assert.assertTrue(isSuccess, "Expected Screenshot to be successful.");
    String[] arrayOfAssociatedFiles = testObject.getArrayOfAssociatedFiles();
    Assert
        .assertTrue(new File((arrayOfAssociatedFiles[arrayOfAssociatedFiles.length - 1])).exists(),
            "Checking that expected file path exists");
  }

  /**
   * Test save page source append.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void testSavePageSourceAppend() throws Exception {
    WebDriver webDriver = WebDriverFactory.getDefaultBrowser();
    FileLogger fileLogger = (FileLogger) this.getTestObject().getLogger();
    SeleniumTestObject testObject = new SeleniumTestObject(webDriver, fileLogger,
        this.getTestObject().getFullyQualifiedTestName());
    this.setTestObject(testObject);

    // Open Google and take a screenshot
    webDriver.navigate().to("http://www.google.com");
    final String testAppend = "testAppend";
    boolean isSuccess = SeleniumUtilities.savePageSource(webDriver, testObject, testAppend);

    // Assert screenshot was successful
    Assert.assertTrue(isSuccess, "Expected Screenshot to be successful.");
    String[] arrayOfAssociatedFiles = testObject.getArrayOfAssociatedFiles();
    Assert
        .assertTrue(new File((arrayOfAssociatedFiles[arrayOfAssociatedFiles.length - 1])).exists(),
            "Checking that expected file path exists");
    Assert.assertTrue(
        new File((arrayOfAssociatedFiles[arrayOfAssociatedFiles.length - 1])).getName()
            .contains(testAppend), "Checking that appended value was added to file");

  }

  /**
   * Test save page source custom directory file name.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void testSavePageSourceCustomDirectoryFileName() throws Exception {
    WebDriver webDriver = WebDriverFactory.getDefaultBrowser();
    FileLogger fileLogger = (FileLogger) this.getTestObject().getLogger();
    SeleniumTestObject testObject = new SeleniumTestObject(webDriver, fileLogger,
        this.getTestObject().getFullyQualifiedTestName());
    this.setTestObject(testObject);

    // Open Google and take a screenshot
    webDriver.navigate().to("http://www.google.com");
    String dateTime = DateTimeFormatter.ofPattern("uuuu-MM-dd-HH-mm-ss-SSSS", Locale.getDefault())
        .format(LocalDateTime.now(Clock.systemUTC()));
    String filePath = SeleniumUtilities
        .savePageSource(webDriver, testObject, fileLogger.getDirectory(),
            StringProcessor.safeFormatter("%s - %s", "TestCustomName", dateTime));

    // Assert File Path returned from Screenshot is the same as expected file path.
    Assert.assertEquals(filePath, Paths.get(StringProcessor
        .safeFormatter("%s%s - %s%s", fileLogger.getDirectory(), "\\TestCustomName", dateTime,
            ".txt")).normalize().toString());
    Assert.assertTrue(new File(filePath).exists(),
        "Checking that page source file exists at expected path.");
    String[] arrayOfAssociatedFiles = testObject.getArrayOfAssociatedFiles();
    Assert
        .assertTrue(new File((arrayOfAssociatedFiles[arrayOfAssociatedFiles.length - 1])).exists(),
            "Checking that expected file path exists");

  }

  /**
   * Test save page source console logger.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void testSavePageSourceConsoleLogger() throws Exception {
    WebDriver webDriver = WebDriverFactory.getDefaultBrowser();
    ConsoleLogger consoleLogger = new ConsoleLogger();
    SeleniumTestObject testObject = new SeleniumTestObject(webDriver, consoleLogger,
        this.getTestObject().getFullyQualifiedTestName());
    this.setTestObject(testObject);

    // Open Google and take a screenshot
    webDriver.navigate().to("http://www.google.com");
    boolean isSuccess = SeleniumUtilities.savePageSource(webDriver, testObject);

    // Assert screenshot was successful
    Assert.assertTrue(isSuccess, "Expected Screenshot to be successful.");
    String[] arrayOfAssociatedFiles = testObject.getArrayOfAssociatedFiles();
    Assert
        .assertTrue(new File((arrayOfAssociatedFiles[arrayOfAssociatedFiles.length - 1])).exists(),
            "Checking that expected file path exists");
  }

  /**
   * Test web element to web driver.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void testWebElementToWebDriver() throws Exception {
    WebDriver webDriver = WebDriverFactory.getDefaultBrowser();
    EventFiringWebDriver eventFiringWebDriver = new EventFiringWebDriver(webDriver);
    ConsoleLogger consoleLogger = new ConsoleLogger();
    SeleniumTestObject testObject = new SeleniumTestObject(eventFiringWebDriver, consoleLogger,
        this.getTestObject().getFullyQualifiedTestName());
    this.setTestObject(testObject);

    // Open Google and take a screenshot
    eventFiringWebDriver.navigate().to("http://www.google.com");
    WebElement input = UIWaitFactory.getWaitDriver(eventFiringWebDriver)
        .waitForVisibleElement(By.tagName("div"));
    WebDriver webElementToWebDriver = SeleniumUtilities.webElementToWebDriver(input);
    Assert.assertNotNull(webElementToWebDriver, "Expected extracted webdriver to not be null");
  }

  /**
   * Test kill driver.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void testKillDriver() throws Exception {
    WebDriver webDriver = WebDriverFactory.getDefaultBrowser();
    ConsoleLogger consoleLogger = new ConsoleLogger();
    SeleniumTestObject testObject = new SeleniumTestObject(webDriver, consoleLogger,
        this.getTestObject().getFullyQualifiedTestName());
    this.setTestObject(testObject);

    // Open Google then Kill the Driver
    webDriver.navigate().to("http://www.google.com");
    SeleniumUtilities.killDriver(webDriver);

    // Assert that the Session ID is null in the Selenium Driver
    Assert.assertNull(((RemoteWebDriver) webDriver).getSessionId(),
        "Expected Selenium Driver Session ID to be null.");
  }
}