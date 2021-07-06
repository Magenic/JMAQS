/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.accessibility;

import com.deque.html.axecore.selenium.AxeBuilder;
import com.deque.html.axecore.selenium.AxeReporter;
import com.magenic.jmaqs.selenium.BaseSeleniumTest;
import com.magenic.jmaqs.selenium.SeleniumConfig;
import com.magenic.jmaqs.selenium.factories.UIWaitFactory;
import com.magenic.jmaqs.utilities.helper.TestCategories;
import com.magenic.jmaqs.utilities.logging.FileLogger;
import com.magenic.jmaqs.utilities.logging.MessageType;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AccessibilityUnitTest extends BaseSeleniumTest {
  /**
   * Unit testing site URL - Login page.
   */
  private static final String TestSiteUrl = SeleniumConfig.getWebSiteBase();

  @BeforeMethod
  public void setup() {
    this.getWebDriver().navigate().to(TestSiteUrl);
    UIWaitFactory.getWaitDriver(getWebDriver()).waitForPageLoad();
  }

  /**
   * Verify we get verbose message back.
   */
  @Test(groups = TestCategories.ACCESSIBILITY)
  public void testAccessibilityCheckVerbose() throws IOException {
    String filePath = ((FileLogger)getLogger()).getFilePath();
    AccessibilityUtilities.checkAccessibility(getTestObject(), false);
    String logContent = Files.lines(Paths.get(filePath),
        StandardCharsets.UTF_8).collect(Collectors.joining(System.lineSeparator()));

    Assert.assertTrue(logContent.contains("Found 20 items"), "Expected to find 20 pass matches.");
    Assert.assertTrue(logContent.contains("Found 62 items"), "Expected to find 62 inapplicable matches.");
    Assert.assertTrue(logContent.contains("Found 6 items"), "Expected to find 6 violations matches.");
    Assert.assertTrue(logContent.contains("INCOMPLETE check for"), "Expected to find any incomplete matches.");

    File file = new File(filePath);
    Assert.assertTrue(file.delete());
  }

  /**
   * Verify message levels are respected.
   */
  @Test(groups = TestCategories.ACCESSIBILITY)
  public void testAccessibilityCheckRespectsMessageLevel() {
    String filePath = ((FileLogger)getLogger()).getFilePath();
    FileLogger fileLogger = new FileLogger(filePath, "LevTest.txt", MessageType.WARNING);

    try {
      AccessibilityUtilities.checkAccessibility(getTestObject().getWebDriver(), fileLogger, false);

      String logContent = Files.lines(Paths.get(fileLogger.getFilePath()),
          StandardCharsets.UTF_8).collect(Collectors.joining(System.lineSeparator()));

      Assert.assertFalse(logContent.contains("PASSES check for"),
          "Did not expect expected to check for pass matches.");
      Assert.assertFalse(logContent.contains("INAPPLICABLE check for"),
          "Did not expect expected to check for inapplicable matches.");
      Assert.assertFalse(logContent.contains("INCOMPLETE check for"),
          "Did not expected to find any incomplete matches.");
      Assert.assertTrue(logContent.contains("Found 6 items"),
          "Expected to find 6 violations matches.");
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      File file = new File(filePath);
      Assert.assertTrue(file.delete(), "File was not deleted");
      Assert.assertFalse(file.exists(), "File Still exists");
    }
  }

  /**
   * Verify inapplicable only check respected.
   */
  @Test(groups = TestCategories.ACCESSIBILITY)
  public void testAccessibilityInapplicableCheckRespectsMessageLevel() {
    String filePath = ((FileLogger)getLogger()).getFilePath();
    FileLogger fileLogger = new FileLogger(filePath, getTestContext().getName() + ".txt", MessageType.INFORMATION);

    try {
      AccessibilityUtilities
          .checkAccessibilityInapplicable(getTestObject().getWebDriver(), fileLogger, MessageType.WARNING, false);
      String logContent = Files.lines(Paths.get(fileLogger.getFilePath()),
          StandardCharsets.UTF_8).collect(Collectors.joining(System.lineSeparator()));

      Assert.assertFalse(logContent.contains("PASSES check"), "Did not expect pass check");
      Assert.assertFalse(logContent.contains("INAPPLICABLE check"), "Did not expect inapplicable check");
      Assert.assertFalse(logContent.contains("INCOMPLETE check"), "Did not expect incomplete check");
      Assert.assertTrue(logContent.contains("VIOLATIONS check"), "Did expect violation check");
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      File file = new File(filePath);
      Assert.assertTrue(file.delete());
    }
  }

  /**
   * Verify incomplete only check respected
   */
  @Test(groups = TestCategories.ACCESSIBILITY)
  public void testAccessibilityIncompleteCheckRespectsMessageLevel() {
    String filePath = ((FileLogger)getLogger()).getFilePath();
    FileLogger fileLogger = new FileLogger(filePath, getTestContext().getName() + ".txt", MessageType.INFORMATION);

    try {
      AccessibilityUtilities
          .checkAccessibilityIncomplete(getTestObject().getWebDriver(), fileLogger, MessageType.WARNING, false);
      String logContent = Files.lines(Paths.get(fileLogger.getFilePath()),
          StandardCharsets.UTF_8).collect(Collectors.joining(System.lineSeparator()));

      Assert.assertFalse(logContent.contains("PASSES check"), "Did not expect pass check");
      Assert.assertFalse(logContent.contains("INAPPLICABLE check"), "Did not expect inapplicable check");
      Assert.assertFalse(logContent.contains("INCOMPLETE check"), "Did not expect incomplete check");
      Assert.assertTrue(logContent.contains("VIOLATIONS check"), "Did expect violation check");
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      File file = new File(filePath);
      Assert.assertTrue(file.delete());
    }
  }

  /**
   * Verify passes only check respected
   */
  @Test(groups = TestCategories.ACCESSIBILITY)
  public void testAccessibilityPassesCheckRespectsMessageLevel() {
    String filePath = ((FileLogger)getLogger()).getFilePath();
    FileLogger fileLogger = new FileLogger(filePath, getTestContext().getName() + ".txt", MessageType.INFORMATION);

    try {
      AccessibilityUtilities
          .checkAccessibilityPasses(getTestObject().getWebDriver(), fileLogger, MessageType.SUCCESS);
      String logContent = Files.lines(Paths.get(fileLogger.getFilePath()),
          StandardCharsets.UTF_8).collect(Collectors.joining(System.lineSeparator()));

      Assert.assertFalse(logContent.contains("PASSES check"), "Did not expect pass check");
      Assert.assertFalse(logContent.contains("INAPPLICABLE check"), "Did not expect inapplicable check");
      Assert.assertFalse(logContent.contains("INCOMPLETE check"), "Did not expect incomplete check");
      Assert.assertTrue(logContent.contains("VIOLATIONS check"), "Did expect violation check");
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      File file = new File(filePath);
      Assert.assertTrue(file.delete());
    }
  }

  /**
   * Verify violation only check respected.
   */
  @Test(groups = TestCategories.ACCESSIBILITY)
  public void testAccessibilityViolationsCheckRespectsMessageLevel() {
    String filePath = ((FileLogger)getLogger()).getFilePath();
    FileLogger fileLogger = new FileLogger(filePath, getTestContext().getName() + ".txt", MessageType.INFORMATION);

    try {
      AccessibilityUtilities
          .checkAccessibilityViolations(getTestObject().getWebDriver(), fileLogger, MessageType.ERROR, false);
      String logContent = Files.lines(Paths.get(fileLogger.getFilePath()),
          StandardCharsets.UTF_8).collect(Collectors.joining(System.lineSeparator()));

      Assert.assertFalse(logContent.contains("PASSES check"), "Did not expect pass check");
      Assert.assertFalse(logContent.contains("INAPPLICABLE check"), "Did not expect inapplicable check");
      Assert.assertFalse(logContent.contains("INCOMPLETE check"), "Did not expect incomplete check");
      Assert.assertTrue(logContent.contains("VIOLATIONS check"), "Did expect violation check");
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      File file = new File(filePath);
      Assert.assertTrue(file.delete());
    }
  }

  /**
   * Verify accessibility exception will be thrown.
   */
    @Test(groups = TestCategories.ACCESSIBILITY, expectedExceptions = RuntimeException.class)
  public void testAccessibilityCheckThrows() {
      AccessibilityUtilities.checkAccessibility(getTestObject(), true);
  }

  /**
   * Verify accessibility does not throw when no exception are found
   */
  @Test(groups = TestCategories.ACCESSIBILITY)
  public void testAccessibilityCheckNoThrowOnNoResults() {
    // There should be 0 incomplete items found
    AccessibilityUtilities.checkAccessibilityIncomplete(getTestObject().getWebDriver(),
        getTestObject().getLogger(), MessageType.WARNING, true);
  }

  /**
   * Verify we can get readable results directly
   */
  @Test(groups = TestCategories.ACCESSIBILITY)
  public void testAccessibilityReadableResults() {
    AxeReporter.getReadableAxeResults("TEST", getWebDriver(), new AxeBuilder().analyze(getWebDriver()).getViolations());
    String messages = AxeReporter.getAxeResultString();

    Assert.assertTrue(messages.contains("TEST check for"), "Expected header.");
    Assert.assertTrue(messages.contains("Found 6 items"), "Expected to find 6 violations matches.");
  }
}