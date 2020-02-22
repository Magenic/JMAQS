/*
 * Copyright 2020 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.appium;

import com.magenic.jmaqs.appium.constants.PlatformType;
import com.magenic.jmaqs.utilities.helper.StringProcessor;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.windows.WindowsDriver;
import java.net.URL;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * The type Appium driver factory.
 */
public class AppiumDriverFactory {

  /**
   * Instantiates a new Appium driver factory.
   */
  private AppiumDriverFactory() {
  }

  /**
   * Gets default mobile driver.
   *
   * @return the default mobile driver
   */
  public static AppiumDriver<WebElement> getDefaultMobileDriver() {
    return getDefaultMobileDriver(AppiumConfig.getDeviceType());
  }

  /**
   * Gets default mobile driver.
   *
   * @param deviceType the device type
   * @return the default mobile driver
   */
  public static AppiumDriver<WebElement> getDefaultMobileDriver(PlatformType deviceType) {
    AppiumDriver<WebElement> appiumDriver;
    URL mobileHubUrl = AppiumConfig.getMobileHubUrl();
    Duration duration = AppiumConfig.getCommandTimeout();
    DesiredCapabilities capabilities = getDefaultMobileOptions();

    switch (deviceType) {
      case ANDROID:
        appiumDriver = getAndroidDriver(mobileHubUrl, capabilities, duration);
        break;
      case IOS:
        appiumDriver = getIosDriver(mobileHubUrl, capabilities, duration);
        break;
      case WINDOWS:
        appiumDriver = getWindowsDriver(mobileHubUrl, capabilities, duration);
        break;
      default:
        throw new IllegalStateException(
            StringProcessor.safeFormatter("Mobile OS type '%s' is not supported " + deviceType));
    }

    if (deviceType != PlatformType.WINDOWS) {
      appiumDriver.manage().timeouts()
          .implicitlyWait(AppiumConfig.getMobileTimeout().toMillis(), TimeUnit.MILLISECONDS);
    }

    return appiumDriver;
  }

  /**
   * Gets default mobile options.
   *
   * @return the default mobile options
   */
  public static DesiredCapabilities getDefaultMobileOptions() {
    DesiredCapabilities capabilities = new DesiredCapabilities();
    capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, AppiumConfig.getDeviceName());
    capabilities.setCapability(CapabilityType.PLATFORM_NAME, AppiumConfig.getPlatformName());
    capabilities
        .setCapability(MobileCapabilityType.PLATFORM_VERSION, AppiumConfig.getPlatformVersion());
    return mergeCapabilities(capabilities, AppiumConfig.getCapabilitiesAsObjects());
  }

  /**
   * Gets default mobile options.
   *
   * @param capabilities the capabilities
   * @return the default mobile options
   */
  public static DesiredCapabilities getDefaultMobileOptions(Map<String, Object> capabilities) {
    return new DesiredCapabilities(capabilities);
  }

  /**
   * Gets android driver.
   *
   * @param mobileHub the mobile hub
   * @param options   the options
   * @param timeout   the timeout
   * @return the android driver
   */
  public static AppiumDriver<WebElement> getAndroidDriver(URL mobileHub,
      DesiredCapabilities options, Duration timeout) {

    return createDriver(() -> {
      AppiumDriver<WebElement> driver = new AndroidDriver<>(mobileHub, options);
      driver.manage().timeouts().implicitlyWait(timeout.toMillis(), TimeUnit.MILLISECONDS);
      return driver;
    });
  }

  /**
   * Gets ios driver.
   *
   * @param mobileHub the mobile hub
   * @param options   the options
   * @param timeout   the timeout
   * @return the ios driver
   */
  public static AppiumDriver<WebElement> getIosDriver(URL mobileHub, DesiredCapabilities options,
      Duration timeout) {
    return createDriver(() -> {
      AppiumDriver<WebElement> driver = new IOSDriver<>(mobileHub, options);
      driver.manage().timeouts().implicitlyWait(timeout.toMillis(), TimeUnit.MILLISECONDS);
      return driver;
    });
  }

  /**
   * Gets windows driver.
   *
   * @param mobileHub the mobile hub
   * @param options   the options
   * @param timeout   the timeout
   * @return the windows driver
   */
  public static AppiumDriver<WebElement> getWindowsDriver(URL mobileHub,
      DesiredCapabilities options, Duration timeout) {
    return createDriver(() -> {
      AppiumDriver<WebElement> driver = new WindowsDriver<>(mobileHub, options);
      driver.manage().timeouts().implicitlyWait(timeout.toMillis(), TimeUnit.MILLISECONDS);
      return driver;
    });

  }

  /**
   * Merge capabilities desired capabilities.
   *
   * @param capabilities          original capabilities object
   * @param capabilitiesAsObjects Map of String, Object
   * @return merged capabilities object
   */
  public static DesiredCapabilities mergeCapabilities(DesiredCapabilities capabilities,
      Map<String, Object> capabilitiesAsObjects) {

    Consumer<String> mergeConsumer = (String s) -> capabilities
        .setCapability(s, capabilitiesAsObjects.get(s));
    capabilitiesAsObjects.keySet().iterator().forEachRemaining(mergeConsumer);
    return capabilities;
  }

  /**
   * Create driver appium driver.
   *
   * @param createFunction the create function
   * @return the appium driver
   */
  public static AppiumDriver<WebElement> createDriver(
      Supplier<AppiumDriver<WebElement>> createFunction) {
    AppiumDriver<WebElement> appiumDriver = null;

    try {
      appiumDriver = createFunction.get();
      return appiumDriver;
    } catch (Exception e) {
      if (e.getClass().isInstance(IllegalArgumentException.class)) {
        throw e;
      } else {
        try {

          if (appiumDriver != null) {
            appiumDriver.quit();
          }

        } catch (Exception quitException) {
          throw new WebDriverException(
              "Appium driver setup and teardown failed. Your driver may be out of date",
              quitException);
        }
      }

      throw new WebDriverException("Your driver may be out of date or unsupported.", e);
    }
  }
}
