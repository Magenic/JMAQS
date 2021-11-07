/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.selenium;

import com.magenic.jmaqs.selenium.factories.UIWaitFactory;
import com.magenic.jmaqs.selenium.pageModels.AutomationPageModel;
import com.magenic.jmaqs.utilities.helper.TestCategories;
import com.magenic.jmaqs.utilities.logging.FileLogger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WrapsDriver;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

/**
 * Unit tests for EventHandler class.
 */
public class EventHandlerUnitTest extends BaseSeleniumTest {

  /**
   * The Automation page model.
   */
  private AutomationPageModel automationPageModel;

  /**
   * The web driver to be used in the tests.
   */
  private WebDriver webDriverWithHandler;

  /**
   * Navigate to test page url and wait for page to load.
   */
  public void navigateToTestPage() {
    // Navigate to the Automation site, wait for the page to load, and set up the event handler
    automationPageModel = new AutomationPageModel(this.getTestObject());
    getWebDriver().navigate().to(automationPageModel.testSiteAutomationUrl);
    UIWaitFactory.getWaitDriver(getWebDriver()).waitForPageLoad();

    // Navigate to the Automation site and set up the event handler
    webDriverWithHandler = getWebDriver();
  }

  /**
   * Test that checks if the correct messages are logged when clicking an element.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void eventHandlerClickElement() {
    navigateToTestPage();

    // Use the Event Firing Web Driver to click an element, then get the log text
    webDriverWithHandler.findElement(automationPageModel.checkbox).click();
    String logText = this.readTextFile(((FileLogger) this.getLogger()).getFilePath());

    // Assert the expected Event Handler logs exist.
    SoftAssert softAssert = new SoftAssert();
    softAssert.assertTrue(logText.contains("Before clicking element"),
        "Expected message to be logged before clicking element.");
    softAssert.assertTrue(logText.contains("Element clicked"),
        "Expected message to be logged after clicking element.");
    softAssert.assertAll();
  }

  /**
   * Test that checks if the correct messages are logged when changing the value
   * of an element.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void eventHandlerChangeValueOf() {
    navigateToTestPage();

    // Use the Event Firing Web Driver to change the value of an element, then get
    // the log text
    webDriverWithHandler.findElement(automationPageModel.firstNameTextBox).sendKeys("Change Value");
    String logText = this.readTextFile(((FileLogger) this.getLogger()).getFilePath());

    // Assert the expected Event Handler logs exist.
    SoftAssert softAssert = new SoftAssert();
    softAssert.assertTrue(logText.contains("Element value before change"),
        "Expected message to be logged before changing the value of element.");
    softAssert.assertTrue(logText.contains("Element value changed to"),
        "Expected message to be logged after changing the value of element.");
    softAssert.assertAll();
  }

  /**
   * Test that checks if the correct messages are logged when finding an element.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void eventHandlerFindBy() {
    navigateToTestPage();

    // Use the Event Firing Web Driver to find an element, then get the log text
    webDriverWithHandler.findElement(automationPageModel.computerPartsList);
    String logText = this.readTextFile(((FileLogger) this.getLogger()).getFilePath());

    // Assert the expected Event Handler logs exist.
    SoftAssert softAssert = new SoftAssert();
    softAssert.assertTrue(logText.contains("Before finding element By"),
        "Expected message to be logged before clicking element.");
    softAssert.assertTrue(logText.contains("Found element By"),
            "Expected message to be logged after clicking element.");
    softAssert.assertAll();
  }

  /**
   * Test that checks if the correct messages are logged when navigating back to
   * the previous page.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void eventHandlerNavigateBack() {
    navigateToTestPage();

    // Use the Event Firing Web Driver to navigate back to a page, then get the log text
    webDriverWithHandler.findElement(automationPageModel.homeButton).click();
    webDriverWithHandler.navigate().back();
    String logText = this.readTextFile(((FileLogger) this.getLogger()).getFilePath());

    // Assert the expected Event Handler logs exist.
    SoftAssert softAssert = new SoftAssert();
    softAssert.assertTrue(logText.contains("Before navigating back to previous page"),
        "Expected message to be logged before navigating back to the previous page.");
    softAssert.assertTrue(logText.contains("Navigated back to previous page"),
        "Expected message to be logged after navigating back to the previous page.");
    softAssert.assertAll();
  }

  /**
   * Test that checks if the correct messages are logged when navigating forward
   * to a page.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void eventHandlerNavigateForward() {
    navigateToTestPage();

    // Use the Event Firing Web Driver to navigate forward to a page, then get the log text
    webDriverWithHandler.findElement(automationPageModel.homeButton).click();
    webDriverWithHandler.navigate().back();
    webDriverWithHandler.navigate().forward();
    String logText = this.readTextFile(((FileLogger) this.getLogger()).getFilePath());

    // Assert the expected Event Handler logs exist.
    SoftAssert softAssert = new SoftAssert();
    softAssert.assertTrue(logText.contains("Before navigating forward to next page"),
        "Expected message to be logged before navigating to a page.");
    softAssert.assertTrue(logText.contains("Navigated forward to next page"),
        "Expected message to be logged after navigating to a page.");
    softAssert.assertAll();
  }

  /**
   * Test that checks if the correct messages are logged when refreshing a page.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void eventHandlerRefresh() {
    navigateToTestPage();

    // Use the Event Firing Web Driver to refresh the page, then get the log text
    webDriverWithHandler.navigate().refresh();
    String logText = this.readTextFile(((FileLogger) this.getLogger()).getFilePath());

    // Assert the expected Event Handler logs exist.
    SoftAssert softAssert = new SoftAssert();
    softAssert.assertTrue(logText.contains("Before Refreshing the page"),
        "Expected message to be logged before refreshing a page.");
    softAssert.assertTrue(logText.contains("Page refreshed"),
        "Expected message to be logged after refreshing a page.");
    softAssert.assertAll();
  }

  /**
   * Test that checks if the correct messages are logged when navigating to a
   * page.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void eventHandlerNavigateTo() {
    navigateToTestPage();

    // Use the Event Firing Web Driver to navigate to a page, then get the log text
    webDriverWithHandler.navigate().to(automationPageModel.testSiteAutomationUrl);
    String logText = this.readTextFile(((FileLogger) this.getLogger()).getFilePath());

    // Assert the expected Event Handler logs exist.
    SoftAssert softAssert = new SoftAssert();
    softAssert.assertTrue(logText.contains("Before navigating to page"),
        "Expected message to be logged before navigating to a page.");
    softAssert.assertTrue(logText.contains("After navigating to page"),
        "Expected message to be logged after navigating to a page.");
    softAssert.assertAll();
  }

  /**
   * Test that checks if the correct messages are logged when running a script.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void eventHandlerScript() {
    navigateToTestPage();

    // Use the Event Firing Web Driver to execute a script, then get the log text
    JavascriptExecutor javascriptExecutor = (JavascriptExecutor) webDriverWithHandler;
    javascriptExecutor.executeScript("document.querySelector(\"#homeButton > a\");");
    String logText = this.readTextFile(((FileLogger) this.getLogger()).getFilePath());

    // Assert the expected Event Handler logs exist.
    SoftAssert softAssert = new SoftAssert();
    softAssert.assertTrue(logText.contains("Before executing script"),
        "Expected message to be logged before running a script.");
    softAssert.assertTrue(logText.contains("Script executed"),
        "Expected message to be logged after running a script.");
    softAssert.assertAll();
  }

  /**
   * Test that checks if the correct messages are logged when switching windows.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void eventHandlerSwitchWindow() {
    navigateToTestPage();

    // Use the Event Firing Web Driver to open a new tab, then get the log text
    ((JavascriptExecutor) webDriverWithHandler).executeScript("window.open()");
    ArrayList<String> tabs = new ArrayList<>(webDriverWithHandler.getWindowHandles());
    webDriverWithHandler.switchTo().window(tabs.get(1));
    String logText = this.readTextFile(((FileLogger) this.getLogger()).getFilePath());

    // Assert the expected Event Handler logs exist.
    SoftAssert softAssert = new SoftAssert();
    softAssert.assertTrue(logText.contains("Before switching to window"),
        "Expected message to be logged before switching windows.");
    softAssert.assertTrue(logText.contains("Switched to window"),
            "Expected message to be logged after switching windows.");
    softAssert.assertAll();
  }

  /**
   * Test that checks if the correct messages are logged when accepting an alert.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void eventHandlerAcceptAlert() {
    navigateToTestPage();

    // Use the Event Firing Web Driver to accept an alert, then get the log text
    UIWait waitDriver = UIWaitFactory.getWaitDriver(((WrapsDriver) this.getWebDriver()).getWrappedDriver());
    waitDriver.waitForClickableElement(automationPageModel.alert).click();
    Alert alert = webDriverWithHandler.switchTo().alert();
    alert.accept();
    String logText = this.readTextFile(((FileLogger) this.getLogger()).getFilePath());

    // Assert the expected Event Handler logs exist.
    SoftAssert softAssert = new SoftAssert();
    softAssert.assertTrue(logText.contains("Before accepting the alert"),
        "Expected message to be logged before accepting an alert.");
    softAssert
        .assertTrue(logText.contains("Alert accepted"),
            "Expected message to be logged after accepting an alert.");
    softAssert.assertAll();
  }

  /**
   * Test that checks if the correct messages are logged when dismissing an alert.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void eventHandlerAcceptDismiss() {
    navigateToTestPage();

    // Use the Event Firing Web Driver to dismiss an alert, then get the log text
    UIWait waitDriver = UIWaitFactory.getWaitDriver(((WrapsDriver) this.getWebDriver()).getWrappedDriver());
    waitDriver.waitForClickableElement(automationPageModel.alertWithConfirm).click();
    Alert alert = webDriverWithHandler.switchTo().alert();
    alert.dismiss();
    String logText = this.readTextFile(((FileLogger) this.getLogger()).getFilePath());

    // Assert the expected Event Handler logs exist.
    SoftAssert softAssert = new SoftAssert();
    softAssert.assertTrue(logText.contains("Before dismissing the alert"),
        "Expected message to be logged before dismissing an alert.");
    softAssert
        .assertTrue(logText.contains("Alert dismissed"),
            "Expected message to be logged after dismissing an alert.");
    softAssert.assertAll();
  }

  /**
   * Test that checks if the correct messages are logged when getting the text
   * from an element.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void eventHandlerGetText() {
    navigateToTestPage();

    // Use the Event Firing Web Driver to get the text from an element, then get the log text
    webDriverWithHandler.findElement(automationPageModel.swaggerLinkBy).getText();
    String logText = this.readTextFile(((FileLogger) this.getLogger()).getFilePath());

    // Assert the expected Event Handler logs exist.
    SoftAssert softAssert = new SoftAssert();
    softAssert.assertTrue(logText.contains("Before getting text from element"),
        "Expected message to be logged before getting text from an element.");
    softAssert.assertTrue(logText.contains("Got element text"),
        "Expected message to be logged after getting text from an element.");
    softAssert.assertAll();
  }

  /**
   * Test that checks if the correct messages are logged when taking a screenshot.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void eventHandlerScreenshot() {
    navigateToTestPage();

    // Use the Event Firing Web Driver to take a screenshot, then get the log text
    TakesScreenshot takeScreenshot = ((TakesScreenshot) webDriverWithHandler);
    takeScreenshot.getScreenshotAs(OutputType.FILE);
    String logText = this.readTextFile(((FileLogger) this.getLogger()).getFilePath());

    // Assert the expected Event Handler logs exist.
    SoftAssert softAssert = new SoftAssert();
    softAssert.assertTrue(logText.contains("Before screenshot capture"),
        "Expected message to be logged before taking a screenshot.");
    softAssert.assertTrue(logText.contains("After screenshot capture"),
        "Expected message to be logged after taking a screenshot.");
    softAssert.assertAll();
  }

  /**
   * Read a file and return it as a string.
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