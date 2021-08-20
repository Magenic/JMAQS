/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.selenium;

import com.magenic.jmaqs.base.BaseGenericTest;
import com.magenic.jmaqs.selenium.constants.BrowserType;
import com.magenic.jmaqs.selenium.constants.RemoteBrowserType;
import com.magenic.jmaqs.utilities.helper.TestCategories;
import java.util.HashMap;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * The WebDriverFactory test class.
 */
public class WebDriverFactoryUnitTest extends BaseGenericTest {
  /**
   * Tests getting the default browser.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void getDefaultBrowserTest() {
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

  /**
   * Tests getting the default chrome options.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void getDefaultChromeOptionsTest() {
    ChromeOptions options = WebDriverFactory.getDefaultChromeOptions();
    Assert.assertNotNull(options);
  }

  /**
   * Tests getting the default headless chrome options.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void getDefaultHeadlessChromeOptionsTest() {
    ChromeOptions options = WebDriverFactory.getDefaultHeadlessChromeOptions();
    Assert.assertNotNull(options);
  }

  /**
   * Tests getting default Internet Explorer options.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void getDefaultInternetExplorerOptionsTest() {
      InternetExplorerOptions options = WebDriverFactory.getDefaultInternetExplorerOptions();
      Assert.assertNotNull(options);
  }

  /**
   * Tests getting default Fire Fox options.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void getDefaultFirefoxOptionsTest() {
    FirefoxOptions options = WebDriverFactory.getDefaultFirefoxOptions();
    Assert.assertNotNull(options);
  }

  /**
   * Tests getting the default Edge options.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void getDefaultEdgeOptionsTest() {
    EdgeOptions options = WebDriverFactory.getDefaultEdgeOptions();
    Assert.assertNotNull(options);
  }

  /**
   * Tests getting the Chrome driver.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void getChromeDriverTest() {
    ChromeDriver driver = null;

    try {
      driver = (ChromeDriver) WebDriverFactory.getBrowserWithDefaultConfiguration(BrowserType.CHROME);
      Assert.assertNotNull(driver);
    } finally {
      if (driver != null) {
        driver.quit();
      }
    }
  }

  /**
   * Tests getting the chrome driver.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void testGetChromeDriver() {
    final ChromeOptions defaultChromeOptions = WebDriverFactory.getDefaultChromeOptions();
    final WebDriver chromeDriver = WebDriverFactory.getChromeDriver(defaultChromeOptions);

    try {
      Assert.assertNotNull(chromeDriver);
    } finally {
      chromeDriver.quit();
    }
  }

  /**
   * Tests getting the headless Chrome driver.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void getHeadlessChromeDriverTest() {
    ChromeDriver driver = null;
    try {
      driver = (ChromeDriver) WebDriverFactory.getBrowserWithDefaultConfiguration(BrowserType.HEADLESS_CHROME);
      Assert.assertNotNull(driver);
    } finally {
      if (driver != null) {
        driver.quit();
      }
    }
  }

  /**
   * Tests getting the Fire Fox driver.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void getFirefoxDriverTest() {
    FirefoxDriver driver = null;
    try {
      driver = (FirefoxDriver) WebDriverFactory.getBrowserWithDefaultConfiguration(BrowserType.FIREFOX);
      Assert.assertNotNull(driver);
    } finally {
      if (driver != null) {
        driver.quit();
      }
    }
  }

  /**
   * Tests getting the Edge driver.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void getEdgeDriverTest() {
    EdgeDriver driver = null;
    try {
      driver = (EdgeDriver) WebDriverFactory.getBrowserWithDefaultConfiguration(BrowserType.EDGE);
      Assert.assertNotNull(driver);
    } finally {
      if (driver != null) {
        driver.quit();
      }
    }
  }

  /**
   * Tests getting the IE driver.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void getInternetExplorerDriverTest() {
    InternetExplorerDriver driver = null;

    if (System.getProperty("os.name").contains("Win")) {
      try {
        driver = (InternetExplorerDriver) WebDriverFactory.getBrowserWithDefaultConfiguration(
            BrowserType.IE);
        Assert.assertNotNull(driver);
      } finally {
        if (driver != null) {
          driver.quit();
        }
      }
    }
  }

  /**
   * Tests getting the Headless driver.
   */
  @Test(groups = TestCategories.SELENIUM)
  // TODO: Remote driver not being instantiated.
  public void getRemoteDriverTest() {
    RemoteWebDriver driver = null;
    try {
      driver = (RemoteWebDriver) WebDriverFactory.getBrowserWithDefaultConfiguration(BrowserType.REMOTE);
      Assert.assertNotNull(driver);
    } finally {
      if (driver != null) {
        driver.quit();
      }
    }
  }

  /**
   * Tests getting the default Remote options.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void getDefaultRemoteOptionsTest() {
    MutableCapabilities options = WebDriverFactory.getDefaultRemoteOptions();
    Assert.assertNotNull(options);
  }

  /**
   * Tests getting the remote options.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void getRemoteOptionsTest() {
    for (RemoteBrowserType browserType : RemoteBrowserType.values()) {
      MutableCapabilities options = WebDriverFactory.getRemoteOptions(browserType);
      Assert.assertNotNull(options);
    }
  }

  /**
   * Tests getting the remote Chrome options.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void getRemoteOptionsChromeTest() {
    MutableCapabilities options = WebDriverFactory.getRemoteOptions(RemoteBrowserType.CHROME, "testPlatform",
        "testVersion", null);
    Assert.assertNotNull(options);

    options = WebDriverFactory.getRemoteOptions(RemoteBrowserType.CHROME, null);
    Assert.assertNotNull(options);
  }

  /**
   * Tests setting the driver options.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void setDriverOptionsTest() {
    MutableCapabilities options = new ChromeOptions();
    HashMap<String, Object> additionalCapabilities = new HashMap<>();
    additionalCapabilities.put("testKey", "testValue");
    WebDriverFactory.setDriverOptions(options, additionalCapabilities);

    Assert.assertNotNull(options);
    Assert.assertEquals(options.getCapability("testKey"), "testValue");
  }

  /**
   * Tests setting the driver options handles when its null.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void setDriverOptionsHandlesNullTest() {
    MutableCapabilities options = new ChromeOptions();
    WebDriverFactory.setDriverOptions(options, null);
    Assert.assertNotNull(options);
  }

  /**
   * Tests setting the browser size.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void setBrowserSizeTest() {
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

  /**
   * Tests setting the browser to Maximize window size.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void setBrowserSizeMaximizeTest() {
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

  /**
   * Tests getting the headless Chrome window to maximum size string.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void getHeadlessWindowSizeStringMaximizeTest() {
    String size = WebDriverFactory.getHeadlessWindowSizeString("MAXIMIZE");
    Assert.assertEquals(size, "window-size=1920,1080");
  }

  /**
   * Tests getting headless chrome specified size window string.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void getHeadlessWindowSizeStringSpecificSizeTest() {
    String size = WebDriverFactory.getHeadlessWindowSizeString("123x456");
    Assert.assertEquals(size, "window-size=123,456");
  }

  /**
   * Tests extracting the dimension from a string.
   */
  @Test(groups = TestCategories.SELENIUM)
  public void extractDimensionFromStringTest() {
    Dimension dimension = WebDriverFactory.extractDimensionFromString("123x456");
    Assert.assertEquals(dimension.width, 123);
    Assert.assertEquals(dimension.height, 456);
  }

  /**
   * Tests extracting the dimension from an invalid string.
   */
  @Test(expectedExceptions = IllegalArgumentException.class, groups = TestCategories.SELENIUM)
  public void extractDimensionFromInvalidStringTest() {
    WebDriverFactory.extractDimensionFromString("invalidSize");
  }

  /**
   * Tests extracting the dimension from an invalid format.
   */
  @Test(expectedExceptions = NumberFormatException.class, groups = TestCategories.SELENIUM)
  public void extractDimensionFromInvalidFormatTest() {
    WebDriverFactory.extractDimensionFromString("notXValid");
  }
}