/*
 * Copyright 2019 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.selenium;

import com.magenic.jmaqs.utilities.helper.Config;
import com.magenic.jmaqs.utilities.helper.TestCategories;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.Dimension;
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
    WebDriver driver = WebDriverFactory.getDefaultBrowser();

    try {
      Assert.assertNotNull(driver);
    } finally {
      driver.quit();
    }
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
   * Verify resize browser window to Maximum lengths
   */
  @Test(groups = TestCategories.Selenium)
  public void resizeBrowserWindowMaximize() throws Exception {
    /*
     * Create driver at whatever size
     * Manually maximize the window
     * Override the Config BrowserSize value to MAXIMIZE
     * Verify new and old driver size values are the same
     */

    // Using FireFox because headless Chrome does not respect Maximize as of 8/24/2018
    WebDriver driverManualSize = WebDriverFactory
            .getBrowserWithDefaultConfiguration(BrowserType.Firefox);

    try {
      driverManualSize.manage().window().maximize();

      int manuallyMaximizedWidth = driverManualSize.manage().window().getSize().width;
      int manuallyMaximizedHidth = driverManualSize.manage().window().getSize().height;


      HashMap<String, String> browserSize = new HashMap<>();
      browserSize.put("BrowserSize", "MAXIMIZE");
      Config.addTestSettingValues(browserSize, "SeleniumMaqs", true);

      WebDriver driverConfigSize = WebDriverFactory
              .getBrowserWithDefaultConfiguration(BrowserType.Firefox);

      try {
        Assert.assertEquals(manuallyMaximizedWidth,
                driverConfigSize.manage().window().getSize().width);
        Assert.assertEquals(manuallyMaximizedHidth,
                driverConfigSize.manage().window().getSize().height);
      } finally {
        driverConfigSize.quit();
      }
    } finally {
      driverManualSize.quit();
    }
  }

  /**
   * Verify resize browser window to Default lengths
   */
  // [DoNotParallelize]
  @Test(groups = TestCategories.Selenium)
  public void resizeBrowserWindowDefault() throws Exception {
    /*
     * Create driver at default size,
     * Set the driver window so that it is not at a default size
     * Create a new browser at default size and verify it is created at the default size and not changed size
     */
    HashMap<String, String> browserSize = new HashMap<>();
    browserSize.put("BrowserSize", "MAXIMIZE");
    Config.addTestSettingValues(browserSize, "SeleniumMaqs", true);

    WebDriver driverChangeSize = WebDriverFactory.getDefaultBrowser();

    try {
      int defaultWidth = driverChangeSize.manage().window().getSize().width;
      int defaultHeight = driverChangeSize.manage().window().getSize().height;
      int nonDefaultWidth = defaultWidth + 1;
      int nonDefaultHeight = defaultHeight + 1;

      Dimension dimension = new Dimension(nonDefaultWidth, nonDefaultHeight);
      driverChangeSize.manage().window().setSize(dimension);

      WebDriver driverDefault = WebDriverFactory.getDefaultBrowser();

      try {
        Assert.assertEquals(defaultWidth, driverDefault.manage().window().getSize().width);
        Assert.assertEquals(defaultHeight, driverDefault.manage().window().getSize().height);
      } finally {
        driverDefault.quit();
      }
    } finally {
      driverChangeSize.quit();
    }
  }

  /**
   * Verify resize browser window to custom lengths 1024x768
   */
  // [DoNotParallelize]
  @Test(groups = TestCategories.Selenium)
  public void resizeBrowserWindowCustom() throws Exception {
    int expectedWidth = 1024;
    int expectedHeight = 768;

    HashMap<String, String> browserSize = new HashMap<>();
    browserSize.put("BrowserSize", expectedWidth + "x" + expectedHeight);
    Config.addTestSettingValues(browserSize,
            "SeleniumMaqs",
            true);

    WebDriver driver = WebDriverFactory.getDefaultBrowser();

    try {
      Assert.assertEquals(expectedWidth, driver.manage().window().getSize().width);
      Assert.assertEquals(expectedHeight, driver.manage().window().getSize().height);
    } finally {
      driver.quit();
    }
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
   * Command timeout.
   */
  @Test(groups = TestCategories.Selenium)
  public void setTimeout() throws Exception {
    WebDriver driver = WebDriverFactory.getDefaultBrowser();
    SeleniumConfig.setTimeouts(driver);
    Assert.assertEquals(0, SeleniumConfig.getTimeoutTime());
  }

  /**
   * Command timeout.
   */
  @Test(groups = TestCategories.Selenium)
  public void setWaitTime() throws Exception {
    WebDriver driver = WebDriverFactory.getDefaultBrowser();
    driver.manage().timeouts().wait(SeleniumConfig.getWaitTime());
    Assert.assertEquals(0, SeleniumConfig.getTimeoutTime());
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
    WebDriver driver = WebDriverFactory.getDefaultBrowser();
    WebDriverWait driverWait = SeleniumConfig.getWaitDriver(driver);
    Assert.assertNotNull(driverWait);
    driver.quit();
  }

  /**
   * Verify remote capabilities section of config
   */
  @Test(groups = TestCategories.Selenium)
  public void getRemoteCapabilitiesAsStrings() {
    Map<String, String> capabilitiesAsStrings =
            SeleniumConfig.getRemoteCapabilitiesAsStrings();

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
    Map<String, Object> capabilitiesAsStrings =
            SeleniumConfig.getRemoteCapabilitiesAsObjects();

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
  public void getSavePageSourceOnFail() {
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
}
