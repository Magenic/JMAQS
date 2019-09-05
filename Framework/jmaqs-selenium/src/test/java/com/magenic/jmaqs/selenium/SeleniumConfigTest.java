/*
 * Copyright 2019 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.selenium;

import com.magenic.jmaqs.utilities.helper.TestCategories;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Selenium configuration tests.
 */
public class SeleniumConfigTest {
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
   * @throws Exception
   *            Can throw new Exception
   */
  @Test(groups = TestCategories.Selenium)
  public void getWaitDriver() throws Exception {
    WebDriver driver = SeleniumConfig.browser();
    WebDriverWait driverWait = SeleniumConfig.getWaitDriver(driver);

    Assert.assertNotNull(driverWait);
    driver.quit();
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
}
