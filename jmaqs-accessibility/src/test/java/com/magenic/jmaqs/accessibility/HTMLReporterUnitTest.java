/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.accessibility;

import com.deque.html.axecore.results.Results;
import com.deque.html.axecore.selenium.ResultType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.magenic.jmaqs.selenium.BaseSeleniumTest;
import com.magenic.jmaqs.selenium.UIWait;
import com.magenic.jmaqs.selenium.factories.UIWaitFactory;
import com.magenic.jmaqs.utilities.helper.TestCategories;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.EnumSet;
import java.util.UUID;
import java.util.stream.Collectors;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.testng.annotations.Test;
import org.testng.Assert;

public class HTMLReporterUnitTest extends BaseSeleniumTest {
  private static final File integrationTestTargetFile = new File("src/test/resources/testFiles/integration-test-target.html");
  private static final String integrationTestTargetUrl = integrationTestTargetFile.getAbsolutePath();

  private static final File integrationTestJsonResultFile = new File("src/test/resources/testFiles/sampleResults.json");
  private static final String integrationTestJsonResultUrl = integrationTestJsonResultFile.getAbsolutePath();

  @Test(groups = TestCategories.ACCESSIBILITY)
  public void htmlReportFullPage() throws IOException, ParseException {
    this.getWebDriver().get("file:///" + new File(integrationTestTargetUrl).getAbsolutePath());
    UIWait wait = UIWaitFactory.getWaitDriver(getWebDriver());
    wait.waitForPageLoad();

    String path = createReportPath();
    HtmlReporter.createAxeHtmlReport(this.getWebDriver(), path);
    validateReport(path, 5, 46, 0, 49);

    File file = new File(path);

    if (file.exists()) {
      Assert.assertTrue(file.delete(), "File was not deleted");
    }
  }

  @Test(groups = TestCategories.ACCESSIBILITY)
  public void htmlViolationsOnlyReportFullPage() throws IOException, ParseException {
    this.getWebDriver().get("file:///" + new File(integrationTestTargetUrl).getAbsolutePath());
    UIWait wait = UIWaitFactory.getWaitDriver(getWebDriver());
    wait.waitForPageLoad();

    String path = createReportPath();
    HtmlReporter.createAxeHtmlReport(this.getWebDriver(), path,
        EnumSet.of(ResultType.Violations));

    // Check violations
    validateReport(path, 5, 0, 0, 0);
    assertResultNotWritten(path,
        EnumSet.of(ResultType.Passes, ResultType.Inapplicable, ResultType.Incomplete));

    File file = new File(path);

    if (file.exists()) {
      Assert.assertTrue(file.delete(), "File was not deleted");
    }
  }

  @Test(groups = TestCategories.ACCESSIBILITY)
  public void htmlPassesInapplicableViolationsOnlyReportFullPage() throws IOException, ParseException {
    this.getWebDriver().get("file:///" + new File(integrationTestTargetUrl).getAbsolutePath());
    UIWait wait = UIWaitFactory.getWaitDriver(getWebDriver());
    wait.waitForPageLoad();

    String path = createReportPath();
    HtmlReporter.createAxeHtmlReport(this.getWebDriver(), path,
        EnumSet.of(ResultType.Passes, ResultType.Inapplicable, ResultType.Violations));

    // Check Passes
    validateReport(path, 5, 46, 0, 49);
    assertResultNotWritten(path, EnumSet.of(ResultType.Incomplete));


    File file = new File(path);

    if (file.exists()) {
      Assert.assertTrue(file.delete(), "File was not deleted");
    }
  }

  @Test(groups = TestCategories.ACCESSIBILITY)
  public void htmlReportOnElement() throws IOException, ParseException {
    this.getWebDriver().get("file:///" + new File(integrationTestTargetUrl).getAbsolutePath());
    UIWait wait = UIWaitFactory.getWaitDriver(getWebDriver());
    wait.waitForPageLoad();

    String path = createReportPath();
    HtmlReporter.createAxeHtmlReport(this.getWebDriver(),
        this.getWebDriver().findElement(By.cssSelector("main")), path);
    validateReport(path, 3, 16, 0, 61);

    File file = new File(path);

    if (file.exists()) {
      Assert.assertTrue(file.delete(), "File was not deleted");
    }
  }

  @Test(groups = TestCategories.ACCESSIBILITY)
  public void reportSampleResults() throws IOException, ParseException {
    this.getWebDriver().get("file:///" + new File(integrationTestTargetUrl).getAbsolutePath());
    UIWait wait = UIWaitFactory.getWaitDriver(getWebDriver());
    wait.waitForPageLoad();

    String path = createReportPath();
    Results results = new ObjectMapper().readValue(new File(integrationTestJsonResultUrl), Results.class);

    HtmlReporter.createAxeHtmlReport(this.getWebDriver(), results, path);
    validateReport(path, 3, 5, 2, 4);

    String text = new String(Files.readAllBytes(Paths.get(path)));
    Document doc = Jsoup.parse(text);

    String errorMessage = doc.selectFirst("#ErrorMessage").text();
    Assert.assertEquals(errorMessage, "AutomationError");

    String reportContext = doc.selectFirst("#reportContext").text();
    Assert.assertTrue(reportContext.contains("Url: https://www.google.com/"), "URL is not in the document");
    Assert.assertTrue(reportContext.contains("Orientation: landscape-primary"), "Orientation is not in the document");
    Assert.assertTrue(reportContext.contains("Size: 1200 x 646"), "Size is not in the document");
    Assert.assertTrue(reportContext.contains("Time: 14-Apr-20 01:33:59"), "Time is not in the document: " + reportContext);
    Assert.assertTrue(reportContext.contains("User agent: AutoAgent"), "User Agent is not in the document");
    Assert.assertTrue(reportContext.contains("Using: axe-core (3.4.1)"), "Using is not in the document");

    File file = new File(path);

    if (file.exists()) {
      Assert.assertTrue( file.delete(), "File was not deleted");
    }
  }

  private String createReportPath() {
    return FileSystems.getDefault().getPath("target/logs") + UUID.randomUUID().toString() + ".html";
  }

  private void validateReport(String path, int violationCount, int passCount, int incompleteCount, int inapplicableCount)
      throws IOException {
    String text = Files.lines(Paths.get(path), StandardCharsets.UTF_8)
        .collect(Collectors.joining(System.lineSeparator()));

    Document doc = Jsoup.parse(text);

    // Check the Element count for each result type
    assertElementCount(doc, violationCount, ResultType.Violations);
    assertElementCount(doc, passCount, ResultType.Passes);
    assertElementCount(doc, inapplicableCount, ResultType.Inapplicable);
    assertElementCount(doc, incompleteCount, ResultType.Incomplete);

    // Check header data
    Assert.assertTrue(text.contains("Using: axe-core"), "Expected to find 'Using: axe-core'");

    // Check the result count for each result type
    assertResultCount(text, violationCount, ResultType.Violations);
    assertResultCount(text, incompleteCount, ResultType.Incomplete);
    assertResultCount(text, passCount, ResultType.Passes);
    assertResultCount(text, inapplicableCount, ResultType.Inapplicable);
  }

  private void assertElementCount(Document doc, int count, ResultType resultType) {
    String ending = resultType.equals(ResultType.Inapplicable) ? "div.findings" : "div > div.htmlTable";
    String xpath = "#" + resultType + "Section > " + ending;
    Elements liNodes = doc.select(xpath) != null ? doc.select(xpath) : new Elements();
    Assert.assertEquals(liNodes.size(), count, "Expected " + count + " " + resultType);
  }

  private void assertResultCount(String text, int count, ResultType resultType) {
    if (count != 0) {
      Assert.assertTrue(text.contains(resultType + ": " + count),
          "Expected to find '" + resultType + ": " + count);
    }
  }

  private void assertResultNotWritten(String path, EnumSet<ResultType> resultTypeArray) throws IOException {
    String text = Files.lines(Paths.get(path), StandardCharsets.UTF_8)
        .collect(Collectors.joining(System.lineSeparator()));

    for (ResultType resultType : resultTypeArray) {
      Assert.assertFalse(text.contains(resultType + ": "),
          "Expected to not find '" + resultType  + ": '");
    }
  }
}