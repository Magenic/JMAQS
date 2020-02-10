/*
 * Copyright 2019 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.base;

import com.magenic.jmaqs.utilities.helper.StringProcessor;
import com.magenic.jmaqs.utilities.logging.ConsoleLogger;
import com.magenic.jmaqs.utilities.logging.FileLogger;
import com.magenic.jmaqs.utilities.logging.Logger;
import com.magenic.jmaqs.utilities.logging.LoggingConfig;
import com.magenic.jmaqs.utilities.logging.LoggingEnabled;
import com.magenic.jmaqs.utilities.logging.MessageType;
import com.magenic.jmaqs.utilities.performance.PerfTimerCollection;

import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * Base test class.
 */
public abstract class BaseTest {

  /**
   * All logged exceptions caught and saved to be thrown later.
   */
  protected ConcurrentHashMap<String, ArrayList<String>> loggedExceptions;

  /**
   * Logging Enabled Setting from Config file.
   */
  protected LoggingEnabled loggingEnabledSetting;

  /**
   * The test result object.
   */
  private ITestResult testResult;

  /**
   * The Collection of Base Test Objects to use.
   */
  ConcurrentManagerHashMap baseTestObjects;

  /**
   * The Performance Timer Collection.
   */
  private PerfTimerCollection perfTimerCollection;

  /**
   * The TestNG Test Context.
   */
  private ITestContext testContextInstance;

  /**
   * The Fully Qualified Test Class Name.
   */
  private ThreadLocal<String> fullyQualifiedTestClassName = new ThreadLocal<>();

  /**
   * Initializes a new instance of the BaseTest class.
   */
  public BaseTest() {
    this.loggedExceptions = new ConcurrentHashMap<String, ArrayList<String>>();
    this.baseTestObjects = new ConcurrentManagerHashMap();
  }

  /**
   * Gets the Performance Timer Collection.
   *
   * @return Performance Timer Collection
   */
  public PerfTimerCollection getPerfTimerCollection() {
    return this.perfTimerCollection;
  }

  /**
   * Sets the Performance Timer Collection.
   *
   * @param perfTimerCollection Performance Timer Collection to use
   */
  public void setPerfTimerCollection(PerfTimerCollection perfTimerCollection) {
    this.perfTimerCollection = perfTimerCollection;
  }

  /**
   * Gets the Logger for this test.
   *
   * @return Logger object
   */
  public Logger getLogger() {
    return this.getTestObject().getLog();
  }

  /**
   * Set the Logger for this test.
   *
   * @param log The Logger object
   */
  public void setLogger(Logger log) {
    this.getTestObject().setLog(log);
  }

  /**
   * Gets the Logging Enabled setting.
   *
   * @return Logging Enabled setting
   */
  public LoggingEnabled getLoggingEnabledSetting() {
    return this.loggingEnabledSetting;
  }

  /**
   * Set the Logging Enabled setting.
   *
   * @param setting The LoggingEnabled enum
   */
  private void setLoggingEnabled(LoggingEnabled setting) {
    this.loggingEnabledSetting = setting;
  }

  /**
   * Get logged exceptions for this test.
   *
   * @return ArrayList of logged exceptions for this test
   */
  public ArrayList<String> getLoggedExceptions() {
    if (!this.loggedExceptions.containsKey(this.getFullyQualifiedTestClassName())) {
      return new ArrayList<String>();
    } else {
      return this.loggedExceptions.get(this.getFullyQualifiedTestClassName());
    }
  }

  /**
   * Set Logged Exception List - Add/Update entry in Hash Map with test class name as key.
   *
   * @param loggedExceptionList ArrayList of logged exceptions to use.
   */
  public void setLoggedExceptions(ArrayList<String> loggedExceptionList) {
    this.loggedExceptions.put(this.getFullyQualifiedTestClassName(), loggedExceptionList);
  }

  /**
   * Gets the Driver Store.
   *
   * @return The Driver Store
   */
  public ManagerDictionary getManagerStore() {
    return this.getTestObject().getManagerStore();
  }

  /**
   * Gets the TestNG Test Context.
   *
   * @return The TestNG Test Context
   */
  public ITestContext getTestContext() {
    return this.testContextInstance;
  }

  /**
   * Sets the TestNG Test context.
   *
   * @param testContext The TestNG Test Context to use
   */
  public void setTestContext(ITestContext testContext) {
    this.testContextInstance = testContext;
  }

  /**
   * Get the BaseTestObject for this test.
   *
   * @return The BaseTestObject
   */
  public BaseTestObject getTestObject() {
    if (!this.baseTestObjects.containsKey(this.getFullyQualifiedTestClassName())) {
      this.createNewTestObject();
    }

    return this.baseTestObjects.get(this.getFullyQualifiedTestClassName());
  }

  /**
   * Sets the Test Object.
   *
   * @param baseTestObject The Base Test Object to use
   */
  public void setTestObject(BaseTestObject baseTestObject) {
    String key = this.getFullyQualifiedTestClassName();
    if (this.baseTestObjects.containsKey(key)) {
      this.baseTestObjects.replace(key, baseTestObject);
    } else {
      this.baseTestObjects.put(key, baseTestObject);
    }
  }

  /**
   * Setup before a test.
   *
   * @param method      The initial executing Method object
   * @param testContext The initial executing Test Context object
   * @throws Exception Throws exception if get logger fails
   */
  @BeforeMethod(alwaysRun = true)
  public void setup(Method method, ITestContext testContext) throws Exception {
    this.testContextInstance = testContext;

    // Get the Fully Qualified Test Class Name and set it in the object
    String testName = method.getDeclaringClass() + "." + method.getName();
    testName = testName.replaceFirst("class ", "");
    this.fullyQualifiedTestClassName.set(testName);

    this.createNewTestObject();
  }

  /**
   * Cleanup after a test.
   */
  @AfterMethod(alwaysRun = true)
  public void teardown() {
    try {
      this.beforeLoggingTeardown(testResult);
    } catch (Exception e) {
      this.tryToLog(MessageType.WARNING, "Failed before logging teardown because: %s",
          e.getMessage());
    }

    // Log the test result
    if (testResult.getStatus() == ITestResult.SUCCESS) {
      this.tryToLog(MessageType.SUCCESS, "Test Passed");
    } else if (testResult.getStatus() == ITestResult.FAILURE) {
      this.tryToLog(MessageType.ERROR, "Test Failed");
    } else if (testResult.getStatus() == ITestResult.SKIP) {
      this.tryToLog(MessageType.INFORMATION, "Test was skipped");
    } else {
      this.tryToLog(MessageType.WARNING, "Test had an unexpected result.");
    }

    // Cleanup log files we don't want
    try {
      if ((this.getLogger() instanceof FileLogger) && testResult.getStatus() == ITestResult.SUCCESS
          && this.loggingEnabledSetting == LoggingEnabled.ONFAIL) {
        Files.delete(Paths.get(((FileLogger) this.getLogger()).getFilePath()));
      }
    } catch (Exception e) {
      this.tryToLog(MessageType.WARNING, "Failed to cleanup log files because: %s", e.getMessage());
    }

    // Get the Fully Qualified Test Name
    String fullyQualifiedTestName = this.getFullyQualifiedTestClassName();

    try (BaseTestObject baseTestObject = this.getTestObject()) {
      // Release logged messages
      this.loggedExceptions.remove(this.getFullyQualifiedTestClassName());

      // Release the Base Test Object
      this.baseTestObjects.remove(fullyQualifiedTestName, baseTestObject);
    }


    // Create console logger to log subsequent messages
    this.setTestObject(new BaseTestObject(new ConsoleLogger(), fullyQualifiedTestName));
  }

  /**
   * Set the test result after each test execution.
   *
   * @param testResult The result object
   */
  @AfterMethod(alwaysRun = true)
  public void setTestResult(ITestResult testResult) {
    this.testContextInstance = testResult.getTestContext();
    this.testResult = testResult;
  }

  /**
   * Steps to do before logging teardown results.
   *
   * @param resultType The test result
   */
  protected abstract void beforeLoggingTeardown(ITestResult resultType);

  /**
   * Setup logging data.
   *
   * @return Logger
   */
  protected Logger createLogger() {
    Logger log;

    this.loggingEnabledSetting = LoggingConfig.getLoggingEnabledSetting();
    this.setLoggedExceptions(new ArrayList<String>());

    if (this.loggingEnabledSetting != LoggingEnabled.NO) {
      log = LoggingConfig.getLogger(StringProcessor
          .safeFormatter("%s - %s", this.getFullyQualifiedTestClassName(),
              DateTimeFormatter.ofPattern("uuuu-MM-dd-HH-mm-ss-SSSS", Locale.getDefault())
                  .format(LocalDateTime.now(Clock.systemUTC()))));
    } else {
      log = new ConsoleLogger();
    }

    return log;
  }

  /**
   * Get the type of test result.
   *
   * @return The type of test result
   *//*
  protected TestResultType getResultType() {
    switch (this.testResult.getStatus()) {
      case ITestResult.SUCCESS:
        return TestResultType.PASS;
      case ITestResult.FAILURE:
        return TestResultType.FAIL;
      case ITestResult.SKIP:
        return TestResultType.SKIP;
      default:
        return TestResultType.OTHER;
    }
  }*/

  /* *//**
   * Get the test result type as text.
   *
   * @return The test result type as text
   *//*
  protected String getResultText() {
    switch (this.testResult.getStatus()) {
      case ITestResult.SUCCESS:
        return "SUCCESS";
      case ITestResult.FAILURE:
        return "FAILURE";
      case ITestResult.SKIP:
        return "SKIP";
      default:
        return "OTHER";
    }
  }*/

  /**
   * Get the fully qualified test name.
   *
   * @return The test name including class
   */
  protected String getFullyQualifiedTestClassName() {
    return this.fullyQualifiedTestClassName.get();
  }

  /**
   * Try to log a message - Do not fail if the message is not logged.
   *
   * @param messageType The type of message
   * @param message     The message text
   * @param args        String format arguments
   */
  protected void tryToLog(MessageType messageType, String message, Object... args) {
    // Get the formatted message
    String formattedMessage = StringProcessor.safeFormatter(message, args);

    try {
      // Write to the log
      this.getLogger().logMessage(messageType, formattedMessage);

      // If this was an error and written to a file, add it to the console
      // output as well
      if (messageType == MessageType.ERROR && !(this.getLogger() instanceof ConsoleLogger)) {
        System.out.println(formattedMessage);
      }
    } catch (Exception e) {
      System.out.println(formattedMessage);
      System.out.println("Logging failed because: " + e.getMessage());
    }
  }

  /**
   * Log a verbose message and include the automation specific call stack data.
   *
   * @param message The message text
   * @param args    String format arguments
   */
  protected void logVerbose(String message, Object... args) {
    StringBuilder messages = new StringBuilder();
    messages.append(StringProcessor.safeFormatter(message, args) + System.lineSeparator());

    for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
      // If the stack trace element is from the com.magenic package (excluding this method) append the stack trace line 
      if (element.toString().startsWith("com.magenic") && !element.toString()
          .contains("BaseTest.logVerbose")) {
        messages.append(element.toString() + System.lineSeparator());
      }
    }

    this.getLogger().logMessage(MessageType.VERBOSE, messages.toString());
  }

  /**
   * Create a Base test object.
   */
  protected void createNewTestObject() {
    Logger newLogger = this.createLogger();
    this.setTestObject(new BaseTestObject(newLogger, this.getFullyQualifiedTestClassName()));
  }
}
