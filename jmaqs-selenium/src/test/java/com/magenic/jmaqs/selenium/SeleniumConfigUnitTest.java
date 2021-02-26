/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.selenium;

import com.magenic.jmaqs.base.BaseGenericTest;
import com.magenic.jmaqs.selenium.constants.BrowserType;
import com.magenic.jmaqs.selenium.constants.RemoteBrowserType;
import com.magenic.jmaqs.utilities.helper.TestCategories;
import java.time.Duration;
import java.util.Map;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

/**
 * Selenium configuration tests.
 */
public class SeleniumConfigUnitTest extends BaseGenericTest {

  /**
   * Remote capabilities username identifier.
   */
  private String username = "username";
  /**
   * Remote browser access key identifier.
   */
  private String accessKey = "accessKey";
  /**
   * Remote browser name identifier.
   */
  private String browserName = "browserName";
  /**
   * Remote version platform identifier.
   */
  private String platform = "platform";
  /**
   * Remote browser version identifier.
   */
  private String version = "version";

  /**
   * Browser name.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void getBrowserName() {

    String driverName = SeleniumConfig.getBrowserName();

    Assert.assertTrue(driverName.equalsIgnoreCase("HEADLESSCHROME"));
  }

  /**
   * Web site base.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void getWebsiteBase() {

    String website = SeleniumConfig.getWebSiteBase();

    Assert.assertTrue(website.equalsIgnoreCase("http://magenicautomation.azurewebsites.net/"));
  }

  /**
   * Hub Url.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void getHubUrl() {

    String hubUrl = SeleniumConfig.getHubUrl();

    Assert.assertTrue(hubUrl.equalsIgnoreCase("http://ondemand.saucelabs.com:80/wd/hub"));
  }

  /**
   * Driver hint path.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void getDriverHintPath() {

    String path = SeleniumConfig.getDriverHintPath();

    Assert.assertEquals(path, "./src/test/resources/drivers");
  }

  /**
   * Remote browser name test.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void getRemoteBrowserName() {

    String browser = SeleniumConfig.getRemoteBrowserName();

    Assert.assertEquals(browser, "Chrome");
  }

  /**
   * Remote platform test.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void getRemotePlatform() {

    String platform = SeleniumConfig.getRemotePlatform();

    Assert.assertEquals(platform, "OS X 10.11");
  }

  /**
   * Remote browser version.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void getRemoteBrowserVersion() {

    String version = SeleniumConfig.getRemoteBrowserVersion();

    Assert.assertEquals(version, "54.0");
  }

  /**
   * Verify remote capabilities section of config.
   */
  @Test(groups = TestCategories.SELENIUM)
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
   * Verify remote capabilities section of config.
   */
  @Test(groups = TestCategories.SELENIUM)
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
  @Test(groups = TestCategories.SELENIUM)
  public void getSavePagesourceOnFail() {
    boolean value = SeleniumConfig.getSavePagesourceOnFail();

    Assert.assertTrue(value);
  }

  /**
   * Verify SoftAssertScreenshot is enabled.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void getSoftAssertScreenshot() {
    boolean value = SeleniumConfig.getSoftAssertScreenshot();

    Assert.assertTrue(value);
  }

  /**
   * Get wait time.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void getWaitTime() {
    Duration value = SeleniumConfig.getWaitTime();

    Assert.assertEquals(value.toMillis(), 1000);
  }

  /**
   * Get timeout time.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void getTimeoutTime() {
    Duration value = SeleniumConfig.getTimeoutTime();

    Assert.assertEquals(value.toMillis(), 20000);
  }

  /**
   * Get browser size.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void getBrowserSize() {
    String value = SeleniumConfig.getBrowserSize();

    Assert.assertNotNull(value);
  }

  @Test(groups = TestCategories.SELENIUM)
  public void getBrowserTypeIeTest() {
    BrowserType browserType = SeleniumConfig.getBrowserType("ie");
    Assert.assertEquals(browserType, BrowserType.IE);
  }

  @Test(groups = TestCategories.SELENIUM)
  public void getBrowserTypeFirefoxTest() {
    BrowserType browserType = SeleniumConfig.getBrowserType("firefox");
    Assert.assertEquals(browserType, BrowserType.FIREFOX);
  }

  @Test(groups = TestCategories.SELENIUM)
  public void getBrowserTypeChromeTest() {
    BrowserType browserType = SeleniumConfig.getBrowserType("chrome");
    Assert.assertEquals(browserType, BrowserType.CHROME);
  }

  @Test(groups = TestCategories.SELENIUM)
  public void getBrowserTypeHeadlessChromeTest() {
    BrowserType browserType = SeleniumConfig.getBrowserType("headlesschrome");
    Assert.assertEquals(browserType, BrowserType.HEADLESS_CHROME);
  }

  @Test(groups = TestCategories.SELENIUM)
  public void getBrowserTypeEdgeTest() {
    BrowserType browserType = SeleniumConfig.getBrowserType("edge");
    Assert.assertEquals(browserType, BrowserType.EDGE);
  }

  @Test(groups = TestCategories.SELENIUM)
  public void getBrowserTypeRemoteTest() {
    BrowserType browserType = SeleniumConfig.getBrowserType("remote");
    Assert.assertEquals(browserType, BrowserType.REMOTE);
  }

  @Test(expectedExceptions = IllegalArgumentException.class, groups = TestCategories.SELENIUM)
  public void getBrowserTypePhantomJsTest() {
    SeleniumConfig.getBrowserType("phantomjs");
  }

  @Test(expectedExceptions = IllegalArgumentException.class, groups = TestCategories.SELENIUM)
  public void getBrowserTypeInvalidTest() {
    SeleniumConfig.getBrowserType("invalid");
  }

  @Test(groups = TestCategories.SELENIUM)
  public void getRemoteBrowserTypeIeTest() {
    RemoteBrowserType remoteType = SeleniumConfig.getRemoteBrowserType("ie");
    Assert.assertEquals(remoteType, RemoteBrowserType.IE);
  }

  @Test(groups = TestCategories.SELENIUM)
  public void getRemoteBrowserTypeFirefoxTest() {
    RemoteBrowserType remoteType = SeleniumConfig.getRemoteBrowserType("firefox");
    Assert.assertEquals(remoteType, RemoteBrowserType.FIREFOX);
  }

  @Test(groups = TestCategories.SELENIUM)
  public void getRemoteBrowserTypeChromeTest() {
    RemoteBrowserType remoteType = SeleniumConfig.getRemoteBrowserType("chrome");
    Assert.assertEquals(remoteType, RemoteBrowserType.CHROME);
  }

  @Test(groups = TestCategories.SELENIUM)
  public void getRemoteBrowserTypeSafariTest() {
    RemoteBrowserType remoteType = SeleniumConfig.getRemoteBrowserType("safari");
    Assert.assertEquals(remoteType, RemoteBrowserType.SAFARI);
  }

  @Test(groups = TestCategories.SELENIUM)
  public void getRemoteBrowserTypeEdgeTest() {
    RemoteBrowserType remoteType = SeleniumConfig.getRemoteBrowserType("edge");
    Assert.assertEquals(remoteType, RemoteBrowserType.EDGE);
  }

  @Test(groups = TestCategories.SELENIUM, expectedExceptions = IllegalArgumentException.class)
  public void getRemoteBrowserTypeInvalidTest() {
    SeleniumConfig.getRemoteBrowserType("invalid");
  }
}
