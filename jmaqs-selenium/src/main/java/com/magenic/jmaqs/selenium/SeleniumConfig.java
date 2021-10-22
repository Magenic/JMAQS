/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.selenium;

import com.magenic.jmaqs.selenium.constants.BrowserType;
import com.magenic.jmaqs.selenium.constants.RemoteBrowserType;
import com.magenic.jmaqs.utilities.helper.Config;
import com.magenic.jmaqs.utilities.helper.ConfigSection;
import com.magenic.jmaqs.utilities.helper.StringProcessor;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * Selenium specific configuration class.
 */
public final class SeleniumConfig {

  /**
   * Private constructor.
   */
  private SeleniumConfig() {

  }

  /**
   * The web service configuration section.
   */
  public static final ConfigSection SELENIUM_SECTION = ConfigSection.SELENIUM_MAQS;

  /**
   * The remote selenium configuration section.
   */
  private static final ConfigSection SELENIUM_CAPS_SECTION = ConfigSection.REMOTE_SELENIUM_CAPS_MAQS;

  /**
   * Get the browser type.
   *
   * @return The browser type
   */
  public static BrowserType getBrowserType() {
    return getBrowserType(getBrowserName());
  }

  /**
   * Get the browser type based on the provided browser name.
   *
   * @param browserName Name of the browse as a string
   * @return The browser type
   */
  public static BrowserType getBrowserType(String browserName) {
    switch (browserName.toUpperCase()) {
      case "INTERNET EXPLORER":
      case "INTERNETEXPLORER":
      case "IE":
        return BrowserType.IE;
      case "FIREFOX":
        return BrowserType.FIREFOX;
      case "CHROME":
        return BrowserType.CHROME;
      case "HEADLESSCHROME":
        return BrowserType.HEADLESS_CHROME;
      case "EDGE":
        return BrowserType.EDGE;
      case "REMOTE":
        return BrowserType.REMOTE;
      case "PHANTOMJS":
      case "PHANTOM JS":
      case "PHANTOM":
        throw new IllegalArgumentException("Selenium no longer supports PhantomJS");
      default:
        throw new IllegalArgumentException(
            StringProcessor.safeFormatter("Browser type '%s' is not supported", browserName));
    }
  }

  /**
   * Get the remote browser type.
   *
   * @return The remote browser type
   */
  public static RemoteBrowserType getRemoteBrowserType() {
    return getRemoteBrowserType(getRemoteBrowserName());
  }

  /**
   * Get the remote browser type.
   *
   * @param remoteBrowser Name of the remote browser
   * @return The remote browser type
   */
  public static RemoteBrowserType getRemoteBrowserType(String remoteBrowser) {
    switch (remoteBrowser.toUpperCase()) {
      case "INTERNET EXPLORER":
      case "INTERNETEXPLORER":
      case "IE":
        return RemoteBrowserType.IE;
      case "FIREFOX":
        return RemoteBrowserType.FIREFOX;
      case "CHROME":
        return RemoteBrowserType.CHROME;
      case "SAFARI":
        return RemoteBrowserType.SAFARI;
      case "EDGE":
        return RemoteBrowserType.EDGE;
      default:
        throw new IllegalArgumentException(StringProcessor
            .safeFormatter("Remote browser type '%s' is not supported", remoteBrowser));
    }
  }

  /**
   * Get the browser type name - Example: Chrome.
   *
   * @return The browser type
   */
  public static String getBrowserName() {
    return Config.getValueForSection(SELENIUM_SECTION, "Browser", "Chrome");
  }

  /**
   * Get the hint path for the web driver.
   *
   * @return The hint path for the web driver
   */
  public static String getDriverHintPath() {
    String defaultPath = new java.io.File("Resources").getAbsolutePath();
    return Config.getValueForSection(SELENIUM_SECTION, "WebDriverHintPath", defaultPath);
  }

  /**
   * Get the remote browser type name.
   *
   * @return The browser type being used on grid
   */
  public static String getRemoteBrowserName() {
    return Config.getValueForSection(SELENIUM_SECTION, "RemoteBrowserName", "Chrome");
  }

  /**
   * Get the remote browser version.
   *
   * @return The browser version to run against on grid
   */
  public static String getRemoteBrowserVersion() {
    return Config.getValueForSection(SELENIUM_SECTION, "RemoteVersion");
  }

  /**
   * Get the remote platform type name.
   *
   * @return The platform (or OS) to run remote tests against
   */
  public static String getRemotePlatform() {
    return Config.getValueForSection(SELENIUM_SECTION, "RemotePlatform");
  }

  /**
   * Get the hub URL.
   *
   * @return the hub URL
   */
  public static String getHubUrl() {
    return Config.getValueForSection(SELENIUM_SECTION, "HubUrl");
  }

  /**
   * Get the website base url.
   *
   * @return The website base url
   */
  public static String getWebSiteBase() {
    return Config.getValueForSection(SELENIUM_SECTION, "WebSiteBase");
  }

  /**
   * Get the SavePageSourceOnFail flag from config.
   *
   * @return True if the flag is set to "Yes"
   */
  public static boolean getSavePageSourceOnFail() {
    return Config.getValueForSection(SELENIUM_SECTION, "SavePagesourceOnFail")
        .equalsIgnoreCase("Yes");
  }

  /**
   * Get the SoftAssertScreenshot flag from config.
   *
   * @return True if the flag is set to "Yes"
   */
  public static boolean getSoftAssertScreenshot() {
    return Config.getValueForSection(SELENIUM_SECTION, "SoftAssertScreenshot")
        .equalsIgnoreCase("Yes");
  }

  /**
   * Get the file extension for the screenshots.
   *
   * @return The type of file, defaults to .png
   */
  public static String getImageFormat() {
    return Config.getValueForSection(SELENIUM_SECTION, "ImageFormat", ".png");
  }

  /**
   * Get the remote capabilities as a HashMap.
   *
   * @return HashMap of remote capabilities
   */
  public static Map<String, String> getRemoteCapabilitiesAsStrings() {
    return Config.getSection(SELENIUM_CAPS_SECTION);
  }

  /**
   * Get the remote capabilities as a HashMap.
   *
   * @return HashMap of remote capabilities
   */
  public static Map<String, Object> getRemoteCapabilitiesAsObjects() {
    return new HashMap<>(getRemoteCapabilitiesAsStrings());
  }

  /**
   * Get wait from config.
   *
   * @return The wait time (in milliseconds)
   */
  public static Duration getWaitTime() {
    return Duration.ofMillis(Integer.parseInt(Config.getValueForSection(SELENIUM_SECTION,"BrowserWaitTime", "0")));
  }

  /**
   * Get the timeout from config.
   *
   * @return The timeout time (in milliseconds)
   */
  public static Duration getTimeoutTime() {
    return Duration.ofMillis(Integer.parseInt(Config.getValueForSection(SELENIUM_SECTION, "BrowserTimeout", "0")));
  }

  /**
   * Get browser size from config.
   *
   * @return The browser size
   */
  public static String getBrowserSize() {
    return Config.getValueForSection(SELENIUM_SECTION, "BrowserSize", "MAXIMIZE".toUpperCase());
  }
}