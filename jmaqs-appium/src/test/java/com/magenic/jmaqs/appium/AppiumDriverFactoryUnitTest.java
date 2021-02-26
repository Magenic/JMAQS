/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.appium;

import com.magenic.jmaqs.appium.constants.PlatformType;
import com.magenic.jmaqs.base.BaseGenericTest;
import com.magenic.jmaqs.utilities.helper.TestCategories;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

/**
 * The type Appium driver factory test.
 */
public class AppiumDriverFactoryUnitTest extends BaseGenericTest {

  /**
   * The Sauce labs config.
   */
  private static DesiredCapabilities sauceLabsConfig;

  /**
   * Sets up.
   */
  @BeforeClass
  public void setUp() {
    sauceLabsConfig = new DesiredCapabilities();
    sauceLabsConfig.setCapability("username", "Partner_Magenic");
    sauceLabsConfig.setCapability("accessKey", "0a4d7d84-f93b-43e6-9af1-1c89ac143355");
    sauceLabsConfig.setCapability("deviceOrientation", "portrait");
  }

  /**
   * Test get default mobile driver.
   */
  @Test(groups = TestCategories.APPIUM)
  public void testGetDefaultMobileDriver() {
    AppiumDriver<WebElement> defaultMobileDriver = AppiumDriverFactory.getDefaultMobileDriver();
    Assert.assertNotNull(defaultMobileDriver, "Checking if default driver is null");
  }

  /**
   * Test test get default mobile driver android.
   */
  @Test(groups = TestCategories.APPIUM)
  public void testTestGetDefaultMobileDriverAndroid() {
    AppiumDriver<WebElement> defaultMobileDriver = AppiumDriverFactory.getDefaultMobileDriver(PlatformType.ANDROID);
    Assert.assertNotNull(defaultMobileDriver, "Checking if default driver is null");
  }

  /**
   * Test get default mobile options.
   */
  @Test(groups = TestCategories.APPIUM)
  public void testGetDefaultMobileOptions() {
    final DesiredCapabilities defaultMobileOptions = AppiumDriverFactory.getDefaultMobileOptions();
    // Consumer is used by the iterator for bulk processing and verification of the
    // keys in the Map.
    // More elegant solution oppose to a for each.
    Consumer<String> assertionConsumer = (String s) -> {
      Assert.assertNotNull(defaultMobileOptions.is(s), String.format("Checking if capability key %s is not null", s));
    };
    defaultMobileOptions.getCapabilityNames().forEach(assertionConsumer);
  }

  /**
   * Test test get default mobile options.
   */
  @Test(groups = TestCategories.APPIUM)
  public void testTestGetDefaultMobileOptions() {
    final Map<String, Object> capabilitiesAsObjects = AppiumConfig.getCapabilitiesAsObjects();
    DesiredCapabilities capabilities = AppiumDriverFactory.getDefaultMobileOptions(capabilitiesAsObjects);
    // Consumer is used by the iterator for bulk processing and verification of the
    // keys in the Map.
    // More elegant solution oppose to a for each.
    Consumer<String> assertionConsumer = (String s) -> {
      Assert.assertNotNull(capabilities.is(s), String.format("Checking if capability key %s is not null", s));
      Assert.assertEquals(capabilities.getCapability(s), capabilitiesAsObjects.get(s),
          String.format("Checking if capability value for key %s matches", s));
    };
    capabilities.getCapabilityNames().forEach(assertionConsumer);
  }

  /**
   * Test get android driver.
   */
  @Test(groups = TestCategories.APPIUM)
  public void testGetAndroidDriver() {
    DesiredCapabilities capabilities = new DesiredCapabilities();
    capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
    capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Chrome");
    capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "6.0");
    capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android GoogleAPI Emulator");
    capabilities = AppiumDriverFactory.mergeCapabilities(capabilities, sauceLabsConfig.asMap());
    AppiumDriver<WebElement> androidDriver = AppiumDriverFactory.getAndroidDriver(AppiumConfig.getMobileHubUrl(),
        capabilities, AppiumConfig.getMobileTimeout());
    Assert.assertNotNull(androidDriver, "Checking if android driver is null");
  }

  /**
   * Test get ios driver.
   */
  @Test(groups = TestCategories.APPIUM)
  public void testGetIOSDriver() {
    DesiredCapabilities capabilities = new DesiredCapabilities();
    capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
    capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Safari");
    capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "12.2");
    capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone X Simulator");
    capabilities = AppiumDriverFactory.mergeCapabilities(capabilities, sauceLabsConfig.asMap());
    AppiumDriver<WebElement> iosDriver = AppiumDriverFactory.getIosDriver(AppiumConfig.getMobileHubUrl(), capabilities,
        AppiumConfig.getMobileTimeout());
    Assert.assertNotNull(iosDriver, "Checking if ios driver is null");
  }

  /**
   * Test get windows driver.
   */
  @Test(groups = TestCategories.APPIUM)
  @Ignore("Work on Windows implementation")
  public void testGetWindowsDriver() {
    DesiredCapabilities appCapabilities = new DesiredCapabilities();
    appCapabilities.setCapability("app", "Microsoft.WindowsCalculator_8wekyb3d8bbwe!App");
    appCapabilities.setCapability(MobileCapabilityType.UDID, "0C0E26E7-966B-4C89-A765-32C5C997A456");
    AppiumDriver<WebElement> windowsDriver = null;
    try {
      windowsDriver = AppiumDriverFactory.getWindowsDriver(new URL("http://127.0.0.1:4723"), appCapabilities,
          AppiumConfig.getMobileTimeout());
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    Assert.assertNotNull(windowsDriver, "Checking if windows app driver is null");
  }

  /**
   * Test create driver.
   */
  @Test(groups = TestCategories.APPIUM, expectedExceptions = WebDriverException.class)
  public void testCreateDriverException() {
    Supplier<AppiumDriver<WebElement>> appiumDriverSupplier = () -> {
      try {
        AppiumDriver<WebElement> driver = new AppiumDriver<WebElement>(new URL("http://127.0.0.1:4723"),
            new DesiredCapabilities());
        return driver;
      } catch (MalformedURLException e) {
        e.printStackTrace();
      }
      return null;
    };

    AppiumDriverFactory.createDriver(appiumDriverSupplier);
  }

}