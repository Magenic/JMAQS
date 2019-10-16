/*
 * Copyright 2019 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.selenium;

import com.magenic.jmaqs.utilities.helper.TestCategories;
import java.util.Map;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

/**
 * Selenium configuration tests.
 */
public class SeleniumConfigTest {

  /**
   * Remote capabilities username identifier
   */
  private String username = "username";
  /**
   * Remote browser access key identifier
   */
  private String accessKey = "accessKey";
  /**
   * Remote browser name identifier
   */
  private String browserName = "browserName";
  /**
   * Remote version platform identifier
   */
  private String platform = "platform";
  /**
   * Remote browser version identifier
   */
  private String version = "version";

  /**
   * Browser check.
   */
  @Test(groups = TestCategories.Selenium)
  public void getBrowser() throws Exception {

    WebDriver driver = SeleniumConfig.browser();

    Assert.assertNotNull(driver);
    driver.quit();
  }

  /**
   * Browser name.
   */
  @Test(groups = TestCategories.Selenium)
  public void getBrowserName() {

    String driverName = SeleniumConfig.getBrowserName();

    Assert.assertTrue(driverName.equalsIgnoreCase("HEADLESSCHROME"));
  }

  /**
   * Web site base.
   */
  @Test(groups = TestCategories.Selenium)
  public void getWebsiteBase() {

    String website = SeleniumConfig.getWebSiteBase();

    Assert.assertTrue(website.equalsIgnoreCase("http://magenicautomation.azurewebsites.net/"));
  }

  /**
   * Hub Url.
   */
  @Test(groups = TestCategories.Selenium)
  public void getHubUrl() {

    String hubUrl = SeleniumConfig.getHubUrl();

    Assert.assertTrue(hubUrl.equalsIgnoreCase("http://ondemand.saucelabs.com:80/wd/hub"));
  }

  /**
   * Command timeout.
   */
  @Test(groups = TestCategories.Selenium)
  public void getCommandTimeout() {

    int timeout = SeleniumConfig.getCommandTimeout();

    Assert.assertEquals(timeout, 61000);
  }

  /**
   * Driver hint path.
   */
  @Test(groups = TestCategories.Selenium)
  public void getDriverHintPath() {

    String path = SeleniumConfig.getDriverHintPath();

    Assert.assertEquals(path, "./src/test/resources");
  }

  /**
   * Remote browser name test.
   */
  @Test(groups = TestCategories.Selenium)
  public void getRemoteBrowserName() {

    String browser = SeleniumConfig.getRemoteBrowserName();

    Assert.assertEquals(browser, "Chrome");
  }

  /**
   * Remote browser check.
   */
  @Test(groups = TestCategories.Selenium)
  public void getRemoteBrowser() throws Exception {

    WebDriver driver = SeleniumConfig.getRemoteBrowser();

    Assert.assertNotNull(driver);
    driver.quit();

  }

  /**
   * Remote browser with string.
   */
  @Test(groups = TestCategories.Selenium)
  public void getRemoteBrowserWithString() throws Exception {

    WebDriver driver = SeleniumConfig.getRemoteBrowser("HEADLESSCHROME");

    Assert.assertNotNull(driver);
    driver.quit();
  }

  /**
   * Remote platform test.
   */
  @Test(groups = TestCategories.Selenium)
  public void getRemotePlatform() {

    String platform = SeleniumConfig.getRemotePlatform();

    Assert.assertEquals(platform, "");
  }

  /**
   * Remote browser version.
   */
  @Test(groups = TestCategories.Selenium)
  public void getRemoteBrowserVersion() {

    String version = SeleniumConfig.getRemoteBrowserVersion();

    Assert.assertEquals(version, "");
  }

  /**
   * Browser with string.
   */
  @Test(groups = TestCategories.Selenium)
  public void getBrowserWithString() throws Exception {

    WebDriver driver = SeleniumConfig.browser("HEADLESSCHROME");

    Assert.assertNotNull(driver);
    driver.quit();
  }

  /**
   * Get Web Wait Driver.
   *
   * @throws Exception Can throw new Exception
   */
  @Test(groups = TestCategories.Selenium)
  public void getWaitDriver() throws Exception {
    WebDriver driver = SeleniumConfig.browser();
    WebDriverWait driverWait = SeleniumConfig.getWaitDriver(driver);

    Assert.assertNotNull(driverWait);
    driver.quit();
  }

  /**
   * Verify remote capabilities section of config
   */
  @Test(groups = TestCategories.Selenium)
  public void getRemoteCapabilitiesAsStrings() {
    Map<String, String> capabilitiesAsStrings = SeleniumConfig.getRemoteCapabilitiesAsStrings();

    SoftAssert softAssert = new SoftAssert();
    softAssert.assertTrue(capabilitiesAsStrings.containsKey(username));
    softAssert.assertEquals(capabilitiesAsStrings.get(username), "Sauce_Labs_Username");
    softAssert.assertTrue(capabilitiesAsStrings.containsKey(accessKey));
    softAssert.assertEquals(capabilitiesAsStrings.get(accessKey), "Sauce_Labs_Accesskey");
    softAssert.assertTrue(capabilitiesAsStrings.containsKey(browserName));
    softAssert.assertEquals(capabilitiesAsStrings.get(browserName), "Chrome");
    softAssert.assertTrue(capabilitiesAsStrings.containsKey(platform));
    softAssert.assertEquals(capabilitiesAsStrings.get(platform), "OS X 10.11");
    softAssert.assertTrue(capabilitiesAsStrings.containsKey(version));
    softAssert.assertEquals(capabilitiesAsStrings.get(version), "54.0");
    softAssert.assertAll();
  }

  /**
   * Verify remote capabilities section of config
   */
  @Test(groups = TestCategories.Selenium)
  public void getRemoteCapabilitiesAsObjects() {
    Map<String, Object> capabilitiesAsStrings = SeleniumConfig.getRemoteCapabilitiesAsObjects();

    SoftAssert softAssert = new SoftAssert();
    softAssert.assertTrue(capabilitiesAsStrings.containsKey(username));
    softAssert.assertEquals(capabilitiesAsStrings.get(username), "Sauce_Labs_Username");
    softAssert.assertTrue(capabilitiesAsStrings.containsKey(accessKey));
    softAssert.assertEquals(capabilitiesAsStrings.get(accessKey), "Sauce_Labs_Accesskey");
    softAssert.assertTrue(capabilitiesAsStrings.containsKey(browserName));
    softAssert.assertEquals(capabilitiesAsStrings.get(browserName), "Chrome");
    softAssert.assertTrue(capabilitiesAsStrings.containsKey(platform));
    softAssert.assertEquals(capabilitiesAsStrings.get(platform), "OS X 10.11");
    softAssert.assertTrue(capabilitiesAsStrings.containsKey(version));
    softAssert.assertEquals(capabilitiesAsStrings.get(version), "54.0");
    softAssert.assertAll();
  }

  /**
   * Verify SavePagesourceOnFail is enabled.
   */
  @Test(groups = TestCategories.Selenium)
  public void getSavePagesourceOnFail() {
    boolean value = SeleniumConfig.getSavePagesourceOnFail();

    Assert.assertTrue(value);
  }

  /**
   * Verify SoftAssertScreenshot is enabled.
   */
  @Test(groups = TestCategories.Selenium)
  public void getSoftAssertScreenshot() {
    boolean value = SeleniumConfig.getSoftAssertScreenshot();

    Assert.assertTrue(value);
  }

  /**
   * Get browser size.
   */
  @Test(groups = TestCategories.Selenium)
  public void getBrowserSize() {
    String value = SeleniumConfig.getBrowserSize();

    Assert.assertNotNull(value);
  }

  @Test(groups = TestCategories.Selenium)
  public void getBrowserTypeIeTest() {
    BrowserType browserType = SeleniumConfig.getBrowserType("ie");
    Assert.assertEquals(browserType, BrowserType.IE);
  }

  @Test(groups = TestCategories.Selenium)
  public void getBrowserTypeFirefoxTest() {
    BrowserType browserType = SeleniumConfig.getBrowserType("firefox");
    Assert.assertEquals(browserType, BrowserType.Firefox);
  }

  @Test(groups = TestCategories.Selenium)
  public void getBrowserTypeChromeTest() {
    BrowserType browserType = SeleniumConfig.getBrowserType("chrome");
    Assert.assertEquals(browserType, BrowserType.Chrome);
  }

  @Test(groups = TestCategories.Selenium)
  public void getBrowserTypeHeadlessChromeTest() {
    BrowserType browserType = SeleniumConfig.getBrowserType("headlesschrome");
    Assert.assertEquals(browserType, BrowserType.HeadlessChrome);
  }

  @Test(groups = TestCategories.Selenium)
  public void getBrowserTypeEdgeTest() {
    BrowserType browserType = SeleniumConfig.getBrowserType("edge");
    Assert.assertEquals(browserType, BrowserType.Edge);
  }

  @Test(groups = TestCategories.Selenium)
  public void getBrowserTypeRemoteTest() {
    BrowserType browserType = SeleniumConfig.getBrowserType("remote");
    Assert.assertEquals(browserType, BrowserType.Remote);
  }

  @Test(expectedExceptions = IllegalArgumentException.class, groups = TestCategories.Selenium)
  public void getBrowserTypePhantomJsTest() {
    SeleniumConfig.getBrowserType("phantomjs");
  }

  @Test(expectedExceptions = IllegalArgumentException.class, groups = TestCategories.Selenium)
  public void getBrowserTypeInvalidTest() {
    SeleniumConfig.getBrowserType("invalid");
  }

  @Test(groups = TestCategories.Selenium)
  public void getRemoteBrowserTypeIeTest() {
    RemoteBrowserType remoteType = SeleniumConfig.getRemoteBrowserType("ie");
    Assert.assertEquals(remoteType, RemoteBrowserType.IE);
  }

  @Test(groups = TestCategories.Selenium)
  public void getRemoteBrowserTypeFirefoxTest() {
    RemoteBrowserType remoteType = SeleniumConfig.getRemoteBrowserType("firefox");
    Assert.assertEquals(remoteType, RemoteBrowserType.Firefox);
  }

  @Test(groups = TestCategories.Selenium)
  public void getRemoteBrowserTypeChromeTest() {
    RemoteBrowserType remoteType = SeleniumConfig.getRemoteBrowserType("chrome");
    Assert.assertEquals(remoteType, RemoteBrowserType.Chrome);
  }

  @Test(groups = TestCategories.Selenium)
  public void getRemoteBrowserTypeSafariTest() {
    RemoteBrowserType remoteType = SeleniumConfig.getRemoteBrowserType("safari");
    Assert.assertEquals(remoteType, RemoteBrowserType.Safari);
  }

  @Test(groups = TestCategories.Selenium)
  public void getRemoteBrowserTypeEdgeTest() {
    RemoteBrowserType remoteType = SeleniumConfig.getRemoteBrowserType("edge");
    Assert.assertEquals(remoteType, RemoteBrowserType.Edge);
  }

  @Test(expectedExceptions = IllegalArgumentException.class, groups = TestCategories.Selenium)
  public void getRemoteBrowserTypeInvalidTest() {
    SeleniumConfig.getRemoteBrowserType("invalid");
  }
}
