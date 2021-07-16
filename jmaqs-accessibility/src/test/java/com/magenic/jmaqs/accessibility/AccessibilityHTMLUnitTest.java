/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.accessibility;

import com.deque.html.axecore.results.AxeRuntimeException;
import com.deque.html.axecore.results.Results;
import com.deque.html.axecore.selenium.AxeBuilder;
import com.deque.html.axecore.selenium.ResultType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.magenic.jmaqs.selenium.BaseSeleniumTest;
import com.magenic.jmaqs.selenium.LazyWebElement;
import com.magenic.jmaqs.selenium.SeleniumConfig;
import com.magenic.jmaqs.selenium.UIWait;
import com.magenic.jmaqs.selenium.factories.UIWaitFactory;
import com.magenic.jmaqs.utilities.helper.TestCategories;
import com.magenic.jmaqs.utilities.logging.FileLogger;
import com.magenic.jmaqs.utilities.logging.MessageType;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AccessibilityHTMLUnitTest extends BaseSeleniumTest {
  /**
   * The file that has a sample result with an error.
   */
  private static final File axeResultWithErrorFile = new File("src/test/resources/testFiles/sampleResults.json");

  /**
   * Unit testing site URL - Login page.
   */
  private static final String TestSiteUrl = SeleniumConfig.getWebSiteBase();

  /**
   * Unit testing site URL - Automation page.
   */
  private static final String TestSiteAutomationUrl = TestSiteUrl + "Automation/";

  private UIWait wait;

  @BeforeMethod
  public void setup() {
    this.getWebDriver().navigate().to(TestSiteUrl);
    wait = UIWaitFactory.getWaitDriver(this.getWebDriver());
    UIWaitFactory.getWaitDriver(this.getWebDriver()).waitForPageLoad();
  }

  @AfterMethod
  public void cleanUp() {
    deleteFiles(Arrays.asList(this.getTestObject().getArrayOfAssociatedFiles()));
  }


  /**
   * Verify we can create and associate an accessibility HTML report.
   * @throws IOException if an exception is thrown
   */
  @Test(groups = TestCategories.ACCESSIBILITY)
  public void testAccessibilityHtmlReport() throws IOException, ParseException {
    AccessibilityUtilities.createAccessibilityHtmlReport(this.getTestObject(),
        new AxeBuilder().analyze(this.getWebDriver()), false);

    String filePath = Arrays.stream(this.getTestObject().getArrayOfAssociatedFiles())
        .filter(x -> x.contains(".html")).findFirst().map(Object::toString).orElse("");
    Assert.assertTrue(filePath.length() > 0, "Accessibility report is empty");
  }

  /**
   * Verify we can create and associate multiple accessibility HTML reports.
   * @throws IOException if an exception is thrown
   */
  @Test(groups = TestCategories.ACCESSIBILITY)
  public void accessibilityMultipleHtmlReports() throws IOException, ParseException {
    // Create 3 reports
    AccessibilityUtilities.createAccessibilityHtmlReport(this.getTestObject(), false);
    AccessibilityUtilities.createAccessibilityHtmlReport(this.getTestObject(), false);
    AccessibilityUtilities.createAccessibilityHtmlReport(this.getTestObject(), false);

    long count = Arrays.stream(this.getTestObject().getArrayOfAssociatedFiles())
        .filter(x -> x.contains(".html")).count();
    Assert.assertEquals(count, 3,
        "Expected 3 accessibility reports but see " + count + " instead");
  }

  /**
   * Verify we throw an exception if the scan has an error.
   */
  @Test(groups = TestCategories.ACCESSIBILITY, expectedExceptions = AxeRuntimeException.class)
  public void accessibilityHtmlReportWithError() throws IOException, ParseException {
    String axeResultWithError = FileUtils.readFileToString(axeResultWithErrorFile, StandardCharsets.UTF_8);
    Results results = new ObjectMapper().readValue(axeResultWithError, Results.class);
    AccessibilityUtilities.createAccessibilityHtmlReport(this.getTestObject(), results, false);

    String filePath = Arrays.stream(this.getTestObject().getArrayOfAssociatedFiles())
        .filter(x -> x.contains(".html")).findFirst().map(Object::toString).orElse("");
    Assert.assertTrue(filePath.length() > 0, "Accessibility report is empty");
  }

  /**
   * Verify we throw an exception if the scan has an error and are using lazy elements.
   */
  @Test(groups = TestCategories.ACCESSIBILITY, expectedExceptions = AxeRuntimeException.class)
  public void accessibilityHtmlReportWithErrorFromLazyElement() throws IOException, ParseException {
    this.getWebDriver().navigate().to(TestSiteAutomationUrl);
    wait.waitForPageLoad();

    String axeResultWithError = FileUtils.readFileToString(axeResultWithErrorFile, StandardCharsets.UTF_8);
    Results error = new ObjectMapper().readValue(axeResultWithError, Results.class);
    AccessibilityUtilities.createAccessibilityHtmlReport(this.getTestObject(), error,false);

    String filePath = Arrays.stream(this.getTestObject().getArrayOfAssociatedFiles())
        .filter(x -> x.contains(".html")).findFirst().map(Object::toString).orElse("");

    Assert.assertTrue(filePath.length() > 0, "Accessibility report is empty");
  }

  /**
   * Verify we throw an exception if there are violations and we choose the throw exception option.
   * @throws IOException if an exception is thrown
   */
  @Test(groups = TestCategories.ACCESSIBILITY, expectedExceptions = RuntimeException.class)
  public void accessibilityHtmlReportWithViolation() throws IOException, ParseException {
    AccessibilityUtilities.createAccessibilityHtmlReport(this.getTestObject(), true);
  }

  /**
   * Verify we can create an accessibility HTML report off a lazy element.
   * @throws IOException if exception is thrown
   */
  @Test(groups = TestCategories.ACCESSIBILITY)
  public void accessibilityHtmlReportWithLazyElement() throws IOException, ParseException {
    this.getWebDriver().get(TestSiteAutomationUrl);
    wait.waitForPageLoad();

    LazyWebElement foodTable = new LazyWebElement(this.getTestObject(),
        By.id("FoodTable"), "Food Table");

    AccessibilityUtilities.createAccessibilityHtmlReport(this.getTestObject(),
        foodTable.getRawExistingElement(), false);

    String filePath = Arrays.stream(this.getTestObject().getArrayOfAssociatedFiles())
        .filter(x -> x.contains(".html")).findFirst().map(Object::toString).orElse("");
    Assert.assertFalse(filePath.isEmpty(), "Accessibility report is empty");
  }

  /**
   *  Verify we can create an accessibility HTML report off a normal web element.
   * @throws IOException if exception is thrown
   */
  @Test(groups = TestCategories.ACCESSIBILITY)
  public void accessibilityHtmlReportWithElement() throws IOException, ParseException {
    this.getWebDriver().navigate().to(TestSiteAutomationUrl);
    wait.waitForPageLoad();

    AccessibilityUtilities.createAccessibilityHtmlReport(this.getTestObject(),
        this.getWebDriver().findElement(By.id("FoodTable")), false);

    String filePath = Arrays.stream(this.getTestObject().getArrayOfAssociatedFiles())
        .filter(x -> x.contains(".html")).findFirst().map(Object::toString).orElse("");

    File file = new File(filePath);
    FileInputStream fis = new FileInputStream(file);
    String fileString = IOUtils.toString(fis, StandardCharsets.UTF_8);
    Assert.assertFalse(fileString.isEmpty(), "Accessibility report is empty");
  }

  /**
   * Verify we suppress the JS logging associated with running Axe.
   * @throws IOException if an exception is thrown
   */
  @Test(groups = TestCategories.ACCESSIBILITY)
  public void accessibilityHtmlLogSuppression() throws IOException, ParseException {
    // Make sure we are not using verbose logging
    this.getLogger().setLoggingLevel(MessageType.INFORMATION);

    this.getWebDriver().navigate().to(TestSiteAutomationUrl);
    wait.waitForPageLoad();

    AccessibilityUtilities.createAccessibilityHtmlReport(this.getTestObject(), false);

    // The script executed message should be suppressed when we run the accessibility check
    FileInputStream fis = new FileInputStream(((FileLogger)this.getLogger()).getFilePath());
    String file = IOUtils.toString(fis, StandardCharsets.UTF_8);

    Assert.assertFalse(file.contains("Script executed"), "Logging was not suppressed as expected.");
  }

  /**
   * Verify we create an HTML file with only the violations.
   * @throws IOException if an IO exception occurs
   * @throws ParseException if a parsing exception occurs
   */
  @Test(groups = TestCategories.ACCESSIBILITY)
  public void accessibilityHtmlReportViolationsOnly() throws IOException, ParseException {
    // Make sure we are not using verbose logging
    this.getLogger().setLoggingLevel(MessageType.INFORMATION);

    AccessibilityUtilities.createAccessibilityHtmlReport(this.getTestObject(),
        false, EnumSet.of(ResultType.Violations));

    // The script executed message should be suppressed when we run the accessibility check
    FileInputStream fis = new FileInputStream(((FileLogger)this.getLogger()).getFilePath());
    String fileString = IOUtils.toString(fis, StandardCharsets.UTF_8);
    Assert.assertFalse(fileString.contains("Passes "), "Passes were still in the report");
    Assert.assertFalse(fileString.contains("Inapplicable "), "Inapplicable were still in the report");
    Assert.assertFalse(fileString.contains("Incomplete  "), "Incomplete were still in the report");
  }

  /**
   *  Verify we can create an accessibility HTML report off a normal web element.
   * @throws IOException if exception is thrown
   */
  @Test(groups = TestCategories.ACCESSIBILITY)
  public void accessibilityHtmlViolationsReportWithElement() throws IOException, ParseException {
    this.getWebDriver().navigate().to(TestSiteAutomationUrl);
    wait.waitForPageLoad();

    AccessibilityUtilities.createAccessibilityHtmlReport(this.getTestObject(),
        this.getWebDriver().findElement(By.id("FoodTable")),
        false, EnumSet.of(ResultType.Violations));

    String filePath = Arrays.stream(this.getTestObject().getArrayOfAssociatedFiles())
        .filter(x -> x.contains(".html")).findFirst().map(Object::toString).orElse("");
    Assert.assertFalse(filePath.isEmpty(), "Accessibility report is empty");
  }

  private void deleteFiles(List<String> files) {
    for (String file : files) {
      File filePath = new File(file).getAbsoluteFile();
      if (filePath.exists()) {
        filePath.delete();
      }
    }
  }
}