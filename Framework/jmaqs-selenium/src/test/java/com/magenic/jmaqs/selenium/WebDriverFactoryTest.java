/*
 * Copyright 2019 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.selenium;

import com.magenic.jmaqs.selenium.constants.WebDriverFile;
import com.magenic.jmaqs.utilities.helper.TestCategories;
import java.util.HashMap;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

/**
 * The WebDriverFactory test class.
 */
public class WebDriverFactoryTest {

  @Test(groups = TestCategories.Selenium)
  public void getDefaultBrowserTest() throws Exception {
    WebDriver driver = null;
    try {
      driver = WebDriverFactory.getDefaultBrowser();
      Assert.assertNotNull(driver);
    } finally {
      if (driver != null) {
        driver.quit();
      }
    }
  }

  @Test(groups = TestCategories.Selenium)
  public void getDefaultChromeOptionsTest() {
    ChromeOptions options = WebDriverFactory.getDefaultChromeOptions();
    Assert.assertNotNull(options);
  }

  @Test(groups = TestCategories.Selenium)
  public void getDefaultHeadlessChromeOptionsTest() {
    ChromeOptions options = WebDriverFactory.getDefaultHeadlessChromeOptions();
    Assert.assertNotNull(options);
  }

  @Test(groups = TestCategories.Selenium)
  public void getDefaultInternetExplorerOptionsTest() {
    InternetExplorerOptions options = WebDriverFactory.getDefaultInternetExplorerOptions();
    Assert.assertNotNull(options);
  }

  @Test(groups = TestCategories.Selenium)
  public void getDefaultFirefoxOptionsTest() {
    FirefoxOptions options = WebDriverFactory.getDefaultFirefoxOptions();
    Assert.assertNotNull(options);
  }

  @Test(groups = TestCategories.Selenium)
  public void getDefaultEdgeOptionsTest() {
    EdgeOptions options = WebDriverFactory.getDefaultEdgeOptions();
    Assert.assertNotNull(options);
  }

  @Test(groups = TestCategories.Selenium)
  public void getChromeDriverTest() throws Exception {
    ChromeDriver driver = null;
    try {
      driver = (ChromeDriver) WebDriverFactory.getBrowserWithDefaultConfiguration(BrowserType.Chrome);
      Assert.assertNotNull(driver);
    } finally {
      if (driver != null) {
        driver.quit();
      }
    }
  }

  @Test(groups = TestCategories.Selenium)
  public void getHeadlessChromeDriverTest() throws Exception {
    ChromeDriver driver = null;
    try {
      driver = (ChromeDriver) WebDriverFactory.getBrowserWithDefaultConfiguration(BrowserType.HeadlessChrome);
      Assert.assertNotNull(driver);
    } finally {
      if (driver != null) {
        driver.quit();
      }
    }
  }

  @Test(groups = TestCategories.Selenium)
  public void getFirefoxDriverTest() throws Exception {
    FirefoxDriver driver = null;
    try {
      driver = (FirefoxDriver) WebDriverFactory.getBrowserWithDefaultConfiguration(BrowserType.Firefox);
      Assert.assertNotNull(driver);
    } finally {
      if (driver != null) {
        driver.quit();
      }
    }
  }

  @Test(groups = TestCategories.Selenium)
  public void getInternetExplorerDriverTest() throws Exception {
    InternetExplorerDriver driver = null;
    try {
      driver = (InternetExplorerDriver) WebDriverFactory.getBrowserWithDefaultConfiguration(BrowserType.IE);
      Assert.assertNotNull(driver);
    } finally {
      if (driver != null) {
        driver.quit();
      }
    }
  }

  @Test(groups = TestCategories.Selenium)
  public void getDefaultRemoteOptionsTest() {
    MutableCapabilities options = WebDriverFactory.getDefaultRemoteOptions();
    Assert.assertNotNull(options);
  }

  @Test(groups = TestCategories.Selenium)
  public void getRemoteOptionsChromeTest() {
    MutableCapabilities options = WebDriverFactory
        .getRemoteOptions(RemoteBrowserType.Chrome, "testPlatform", "testVersion", null);
    Assert.assertNotNull(options);
  }

  @Test(groups = TestCategories.Selenium)
  public void getRemoteOptionsIeTest() {
    MutableCapabilities options = WebDriverFactory.getRemoteOptions(RemoteBrowserType.IE);
    Assert.assertNotNull(options);
  }

  @Test(groups = TestCategories.Selenium)
  public void getRemoteOptionsFirefoxTest() {
    MutableCapabilities options = WebDriverFactory.getRemoteOptions(RemoteBrowserType.Firefox);
    Assert.assertNotNull(options);
  }

  @Test(groups = TestCategories.Selenium)
  public void getRemoteOptionsEdgeTest() {
    MutableCapabilities options = WebDriverFactory.getRemoteOptions(RemoteBrowserType.Edge);
    Assert.assertNotNull(options);
  }

  @Test(groups = TestCategories.Selenium)
  public void getRemoteOptionsSafariTest() {
    MutableCapabilities options = WebDriverFactory.getRemoteOptions(RemoteBrowserType.Safari);
    Assert.assertNotNull(options);
  }

  @Test(groups = TestCategories.Selenium)
  public void setDriverOptionsTest() {
    MutableCapabilities options = new ChromeOptions();
    HashMap additionalCapabilities = new HashMap<String, Object>();
    additionalCapabilities.put("testKey", "testValue");
    WebDriverFactory.setDriverOptions(options, additionalCapabilities);

    Assert.assertNotNull(options);
    Assert.assertEquals(options.getCapability("testKey"), "testValue");
  }

  @Test(groups = TestCategories.Selenium)
  public void setDriverOptionsHandlesNullTest() {
    MutableCapabilities options = new ChromeOptions();
    WebDriverFactory.setDriverOptions(options, null);

    Assert.assertNotNull(options);
  }

  @Test(groups = TestCategories.Selenium)
  public void setBrowserSizeTest() throws Exception {
    WebDriver driver = null;
    try {
      driver = WebDriverFactory.getDefaultBrowser();
      WebDriverFactory.setBrowserSize(driver, "1920x1080");
      Assert.assertEquals(driver.manage().window().getSize().width, 1920);
      Assert.assertEquals(driver.manage().window().getSize().height, 1080);
    } finally {
      if (driver != null) {
        driver.quit();
      }
    }
  }

  @Test(groups = TestCategories.Selenium)
  public void setBrowserSizeMaximizeTest() throws Exception {
    WebDriver driver = null;
    try {
      driver = WebDriverFactory.getDefaultBrowser();
      WebDriverFactory.setBrowserSize(driver, "MAXIMIZE");
    } finally {
      if (driver != null) {
        driver.quit();
      }
    }
  }

  @Test(groups = TestCategories.Selenium)
  public void getHeadlessWindowSizeStringMaximizeTest() {
    String size = WebDriverFactory.getHeadlessWindowSizeString("MAXIMIZE");
    Assert.assertEquals(size, "window-size=1920,1080");
  }

  @Test(groups = TestCategories.Selenium)
  public void getHeadlessWindowSizeStringSpecificSizeTest() {
    String size = WebDriverFactory.getHeadlessWindowSizeString("123x456");
    Assert.assertEquals(size, "window-size=123,456");
  }

  @Test(groups = TestCategories.Selenium)
  public void extractDimensionFromStringTest() {
    Dimension dimension = WebDriverFactory.extractDimensionFromString("123x456");
    Assert.assertEquals(dimension.width, 123);
    Assert.assertEquals(dimension.height, 456);
  }

  @Test(expectedExceptions = IllegalArgumentException.class, groups = TestCategories.Selenium)
  public void extractDimensionFromInvalidStringTest() {
    WebDriverFactory.extractDimensionFromString("invalidSize");
  }

  @Test(expectedExceptions = NumberFormatException.class, groups = TestCategories.Selenium)
  public void extractDimensionFromInvalidFormatTest() {
    WebDriverFactory.extractDimensionFromString("notXValid");
  }

  @Test(groups = TestCategories.Selenium)
  public void getDriverLocationTest() {
    String driverLocation = WebDriverFactory.getDriverLocation(WebDriverFile.CHROME.getFile());
    Assert.assertFalse(driverLocation.isEmpty());
  }

  @Test(groups = TestCategories.Selenium)
  public void getDriverLocationConfigHintPathTest() {
    String driverLocation = WebDriverFactory.getDriverLocation(WebDriverFile.CHROME.getFile());
    Assert.assertFalse(driverLocation.isEmpty());
    Assert.assertEquals(driverLocation, SeleniumConfig.getDriverHintPath(),
        "Checking that driver location and config hint path are the same.");
  }

  @Test(groups = TestCategories.Selenium)
  @Ignore("Not able to be tested yet")
  public void getDriverLocationDefaultHintPathTest() {
    String driverLocation = WebDriverFactory.getDriverLocation(WebDriverFile.CHROME.getFile(), "");
    Assert.assertFalse(driverLocation.isEmpty());
    Assert.assertEquals(driverLocation, SeleniumConfig.getDriverHintPath(),
        "Checking that driver location and default hint path are the same.");
  }

  @Test(groups = TestCategories.Selenium)
  @Ignore("Not able to be tested yet")
  public void getDriverLocationTestLocationTest() {
    String driverLocation = WebDriverFactory.getDriverLocation(WebDriverFile.CHROME.getFile());
    Assert.assertFalse(driverLocation.isEmpty());
    Assert.assertEquals(driverLocation, SeleniumConfig.getDriverHintPath(),
        "Checking that driver location and config hint path are the same.");
  }

  @Test(groups = TestCategories.Selenium)
  @Ignore("Not able to be tested yet")
  public void getDriverLocationTestResourcesLocationTest() {
    String driverLocation = WebDriverFactory.getDriverLocation(WebDriverFile.CHROME.getFile());
    Assert.assertFalse(driverLocation.isEmpty());
    Assert.assertEquals(driverLocation, SeleniumConfig.getDriverHintPath(),
        "Checking that driver location and config hint path are the same.");
  }

  @Test(groups = TestCategories.Selenium)
  public void getDriverLocationDoesNotExistTest() {
    String driverLocation = WebDriverFactory.getDriverLocation("doesNotExist.exe", "", false);
    Assert.assertEquals(driverLocation, "");
  }

  @Test(expectedExceptions = RuntimeException.class, groups = TestCategories.Selenium)
  public void getDriverLocationMustExistTest() {
    String driverLocation = WebDriverFactory.getDriverLocation("doesNotExist.exe", "", true);
  }

  @Test(groups = TestCategories.Selenium)
  public void getWindowsEdgeDriverLocationTest() {
    String driverLocation = WebDriverFactory.getWindowsEdgeDriverLocation("testFile");
    Assert.assertEquals(driverLocation, "");
  }
}
