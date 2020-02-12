/*
 * Copyright 2019 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.selenium;

import com.magenic.jmaqs.selenium.constants.BrowserType;
import com.magenic.jmaqs.selenium.constants.RemoteBrowserType;
import com.magenic.jmaqs.selenium.constants.WebDriverFile;
import com.magenic.jmaqs.utilities.helper.StringProcessor;
import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariOptions;

/**
 * Static web driver factory.
 */
public class WebDriverFactory {

  /**
   * Constant for the maximizing browser size.
   */
  private static final String WINDOW_MAX = "MAXIMIZE";

  /**
   * Private constructor.
   */
  private WebDriverFactory() {
  }

  /**
   * Get the default web driver based on the test run configuration.
   *
   * @return A web driver
   */
  public static WebDriver getDefaultBrowser() throws Exception {
    return getBrowserWithDefaultConfiguration(SeleniumConfig.getBrowserType());
  }

  /**
   * Get the default web driver (for the specified browser type) based on the test run configuration.
   *
   * @param browser The type of browser
   * @return A web driver
   * @throws Exception An exception
   */
  public static WebDriver getBrowserWithDefaultConfiguration(BrowserType browser) throws Exception {
    String size = SeleniumConfig.getBrowserSize();

    try {
      switch (browser) {
        case IE:
          return getInternetExplorerDriver(getDefaultInternetExplorerOptions(), size);
        case FIREFOX:
          return getFirefoxDriver(getDefaultFirefoxOptions(), size);
        case CHROME:
          return getChromeDriver(getDefaultChromeOptions(), size);
        case HEADLESS_CHROME:
          return getHeadlessChromeDriver(getDefaultHeadlessChromeOptions(size));
        case EDGE:
          return getEdgeDriver(getDefaultEdgeOptions(), size);
        case REMOTE:
          return new RemoteWebDriver(new URL(SeleniumConfig.getHubUrl()),
              getDefaultRemoteOptions());
        default:
          throw new IllegalArgumentException(
              StringProcessor.safeFormatter("Browser type '%s' is not supported", browser));
      }
    } catch (IllegalArgumentException e) {
      throw e;
    } catch (Exception e) {
      // Log that something went wrong
      throw new Exception("Your web driver may be out of date or unsupported.", e);
    }
  }

  /**
   * Get the default Chrome options.
   *
   * @return The default Chrome options
   */
  public static ChromeOptions getDefaultChromeOptions() {
    ChromeOptions chromeOptions = new ChromeOptions();
    chromeOptions.addArguments("test-type");
    chromeOptions.addArguments("--disable-web-security");
    chromeOptions.addArguments("--allow-running-insecure-content");
    chromeOptions.addArguments("--disable-extensions");

    return chromeOptions;
  }

  /**
   * Get the default headless Chrome options.
   *
   * @return The default headless Chrome options
   */
  public static ChromeOptions getDefaultHeadlessChromeOptions() {
    return getDefaultHeadlessChromeOptions(WINDOW_MAX);
  }

  /**
   * Get the default headless Chrome options.
   *
   * @param size Browser size in the following format: MAXIMIZE, DEFAULT, or #x# (such as 1920x1080)
   * @return The default headless Chrome options
   */
  public static ChromeOptions getDefaultHeadlessChromeOptions(String size) {
    ChromeOptions headlessChromeOptions = new ChromeOptions();
    headlessChromeOptions.addArguments("test-type");
    headlessChromeOptions.addArguments("--disable-web-security");
    headlessChromeOptions.addArguments("--allow-running-insecure-content");
    headlessChromeOptions.addArguments("--disable-extensions");
    headlessChromeOptions.addArguments("--no-sandbox");
    headlessChromeOptions.addArguments("--headless");
    headlessChromeOptions.addArguments(getHeadlessWindowSizeString(size));

    return headlessChromeOptions;
  }

  /**
   * Get the default IE options.
   *
   * @return The default IE options
   */
  public static InternetExplorerOptions getDefaultInternetExplorerOptions() {
    InternetExplorerOptions options = new InternetExplorerOptions();
    options.ignoreZoomSettings();

    return options;
  }

  /**
   * Get the default Firefox options.
   *
   * @return The default Firefox options
   */
  public static FirefoxOptions getDefaultFirefoxOptions() {
    FirefoxOptions firefoxOptions = new FirefoxOptions();
    firefoxOptions.setProfile(new FirefoxProfile());

    return firefoxOptions;
  }

  /**
   * Get the default Edge options.
   *
   * @return The default Edge options
   */
  public static EdgeOptions getDefaultEdgeOptions() {
    EdgeOptions edgeOptions = new EdgeOptions();
    edgeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL.toString());

    return edgeOptions;
  }

  /**
   * Initialize a new Chrome driver.
   *
   * @param chromeOptions Browser options
   * @return A new Chrome driver
   */
  public static WebDriver getChromeDriver(ChromeOptions chromeOptions) {
    return getChromeDriver(chromeOptions, WINDOW_MAX);
  }

  /**
   * Initialize a new Chrome driver.
   *
   * @param chromeOptions Browser options
   * @param size          Browser size in the following format: MAXIMIZE, DEFAULT, or #x# (such as 1920x1080)
   * @return A new Chrome driver
   */
  public static WebDriver getChromeDriver(ChromeOptions chromeOptions, String size) {
    System.setProperty("webdriver.chrome.driver",
        getDriverLocation(WebDriverFile.CHROME.getFileName()) + File.separator
            + WebDriverFile.CHROME.getFileName());
    WebDriver driver = new ChromeDriver(chromeOptions);
    setBrowserSize(driver, size);
    return driver;
  }

  /**
   * Initialize a new headless Chrome driver.
   *
   * @param headlessChromeOptions Browser options
   * @return A new headless Chrome driver
   */
  public static WebDriver getHeadlessChromeDriver(ChromeOptions headlessChromeOptions) {
    System.setProperty("webdriver.chrome.driver",
        getDriverLocation(WebDriverFile.CHROME.getFileName()) + File.separator
            + WebDriverFile.CHROME.getFileName());
    return new ChromeDriver(headlessChromeOptions);
  }

  /**
   * Initialize a new Firefox driver.
   *
   * @param firefoxOptions Browser options
   * @param size           Browser size in the following format: MAXIMIZE, DEFAULT, or #x# (such as 1920x1080)
   * @return A new Firefox driver
   */
  public static WebDriver getFirefoxDriver(FirefoxOptions firefoxOptions, String size) {
    System.setProperty("webdriver.gecko.driver",
        getDriverLocation(WebDriverFile.FIREFOX.getFileName()) + File.separator
            + WebDriverFile.FIREFOX.getFileName());

    WebDriver driver = new FirefoxDriver(firefoxOptions);
    setBrowserSize(driver, size);

    return driver;
  }

  /**
   * Initialize a new Edge driver.
   *
   * @param edgeOptions Browser options
   * @param size        Browser size in the following format: MAXIMIZE, DEFAULT, or #x# (such as 1920x1080)
   * @return A new Edge driver
   */
  public static WebDriver getEdgeDriver(EdgeOptions edgeOptions, String size) {
    String driverLocation = getDriverLocation(WebDriverFile.EDGE.getFileName(),
        getWindowsEdgeDriverLocation(WebDriverFile.EDGE.getFileName()));

    // If we can't find an installed edge driver, look in the normal places
    if (driverLocation.isEmpty()) {
      driverLocation = getDriverLocation(WebDriverFile.EDGE.getFileName());
    }

    System.setProperty("webdriver.edge.driver",
        driverLocation + File.separator + WebDriverFile.EDGE.getFileName());
    EdgeDriver driver = new EdgeDriver(edgeOptions);
    setBrowserSize(driver, size);
    return driver;
  }

  /**
   * Get a new IE driver.
   *
   * @param internetExplorerOptions Browser options
   * @param size                    Browser size in the following format: MAXIMIZE, DEFAULT, or #x# (such as 1920x1080)
   * @return A new IE driver
   */
  public static WebDriver getInternetExplorerDriver(InternetExplorerOptions internetExplorerOptions,
      String size) {
    System.setProperty("webdriver.ie.driver",
        getDriverLocation(WebDriverFile.IE.getFileName()) + File.separator + WebDriverFile.IE
            .getFileName());
    InternetExplorerDriver driver = new InternetExplorerDriver(internetExplorerOptions);
    setBrowserSize(driver, size);

    return driver;
  }

  /**
   * Get the default remote driver options - Default values are pulled from the configuration.
   *
   * @return The remote driver options
   */
  public static MutableCapabilities getDefaultRemoteOptions() {
    RemoteBrowserType remoteBrowser = SeleniumConfig.getRemoteBrowserType();
    String remotePlatform = SeleniumConfig.getRemotePlatform();
    String remoteBrowserVersion = SeleniumConfig.getRemoteBrowserVersion();
    HashMap<String, Object> capabilities = (HashMap<String, Object>) SeleniumConfig
        .getRemoteCapabilitiesAsObjects();

    return getRemoteOptions(remoteBrowser, remotePlatform, remoteBrowserVersion, capabilities);
  }

  /**
   * Get the remote driver options.
   *
   * @param remoteBrowser The remote browser type
   * @return The remote driver options
   */
  public static MutableCapabilities getRemoteOptions(RemoteBrowserType remoteBrowser) {
    return getRemoteOptions(remoteBrowser, "", "", null);
  }

  /**
   * Get the remote driver options.
   *
   * @param remoteBrowser      The remote browser type
   * @param remoteCapabilities Additional remote capabilities
   * @return The remote driver options
   */
  public static MutableCapabilities getRemoteOptions(RemoteBrowserType remoteBrowser,
      Map<String, Object> remoteCapabilities) {
    return getRemoteOptions(remoteBrowser, "", "", remoteCapabilities);
  }

  /**
   * Get the remote driver options.
   *
   * @param remoteBrowser        The remote browser type
   * @param remotePlatform       The remote platform
   * @param remoteBrowserVersion The remote browser version
   * @param remoteCapabilities   Additional remote capabilities
   * @return The remote driver options
   */
  public static MutableCapabilities getRemoteOptions(RemoteBrowserType remoteBrowser,
      String remotePlatform, String remoteBrowserVersion, Map<String, Object> remoteCapabilities) {
    MutableCapabilities options;
    switch (remoteBrowser) {
      case IE:
        options = new InternetExplorerOptions();
        break;

      case FIREFOX:
        options = new FirefoxOptions();
        break;

      case CHROME:
        options = new ChromeOptions();
        break;

      case EDGE:
        options = new EdgeOptions();
        break;

      case SAFARI:
        options = new SafariOptions();
        break;

      default:
        throw new IllegalArgumentException(StringProcessor
            .safeFormatter("Remote browser type '%s' is not supported", remoteBrowser));
    }

    // Make sure the remote capabilities dictionary exists
    if (remoteCapabilities == null) {
      remoteCapabilities = new HashMap<>();
    }

    // Add a platform setting if one was provided
    if (!remotePlatform.isEmpty() && !remoteCapabilities.containsKey("platform")) {
      remoteCapabilities.put("platform", remotePlatform);
    }

    // Add a remote browser setting if one was provided
    if (!remoteBrowserVersion.isEmpty() && !remoteCapabilities.containsKey("version")) {
      remoteCapabilities.put("version", remoteBrowserVersion);
    }

    // Add additional capabilities to the driver options
    setDriverOptions(options, remoteCapabilities);

    return options;
  }

  /**
   * Add additional capabilities to the driver options.
   *
   * @param driverOptions          The driver option you want to add capabilities to
   * @param additionalCapabilities Capabilities to add
   */
  public static void setDriverOptions(MutableCapabilities driverOptions,
      Map<String, Object> additionalCapabilities) {
    // If there are no additional capabilities just return
    if (additionalCapabilities == null) {
      return;
    }

    additionalCapabilities.forEach((key, value) -> {
      if ((value instanceof String) && !((String) value).isEmpty()) {
        driverOptions.setCapability(key, value);
      }
    });
  }

  /**
   * Sets the browser size based on the provide string value.
   * Browser size is expected to be: MAXIMIZE, DEFAULT, or #x# (such as 1920x1080).
   * MAXIMIZE just maximizes the browser.
   * DEFAULT does not change the current size.
   * #x# sets a custom size.
   *
   * @param webDriver the webDriver from the Browser method
   * @param size      Browser size in the following format: MAXIMIZE, DEFAULT, or #x# (such as 1920x1080)
   */
  public static void setBrowserSize(WebDriver webDriver, String size) {
    size = size.toUpperCase();

    if (size.equals(WINDOW_MAX)) {
      webDriver.manage().window().maximize();
    } else if (!size.equals("DEFAULT")) {
      webDriver.manage().window().setSize(extractDimensionFromString(size));
    }
  }

  /**
   * Get the browser/browser size as a string.
   *
   * @param size Browser size in the following format: MAXIMIZE, DEFAULT, or #x# (such as 1920x1080)
   * @return The browser size as a string - Specifically for headless Chrome options
   */
  public static String getHeadlessWindowSizeString(String size) {
    if (size.equals(WINDOW_MAX) || size.equals("DEFAULT")) {
      // If we need a string default to 1920 by 1080
      return "window-size=1920,1080";
    } else {
      Dimension dimension = extractDimensionFromString(size);
      return String.format("window-size=%s,%s", dimension.width, dimension.height);
    }
  }

  /**
   * Get the window size as a Dimension object.
   *
   * @param size The size in a #X# format
   * @return The dimension object with width and height defined
   */
  public static Dimension extractDimensionFromString(String size) {
    String[] sizes = size.split("[xX]");

    if (!size.toUpperCase().contains("X") || sizes.length != 2) {
      throw new IllegalArgumentException(
          "Browser size is expected to be in an expected format: 1920x1080");
    }

    try {
      int width = Integer.parseInt(sizes[0]);
      int height = Integer.parseInt(sizes[1]);
      return new Dimension(width, height);
    } catch (NumberFormatException e) {
      throw new NumberFormatException(
          "Length and Width must be a string that is an integer value: 400x400");
    }
  }

  /**
   * Get the web driver location.
   *
   * @param driverFile The web driver file, including extension
   * @return The path to the web driver
   */
  public static String getDriverLocation(String driverFile) {
    return getDriverLocation(driverFile, "", true);
  }

  /**
   * Get the web driver location.
   *
   * @param driverFile      The web driver file, including extension
   * @param defaultHintPath The default location for the specific driver
   * @return The path to the web driver
   */
  public static String getDriverLocation(String driverFile, String defaultHintPath) {
    return getDriverLocation(driverFile, defaultHintPath, true);
  }

  /**
   * Get the web driver location.
   *
   * @param driverFile      The web driver file, including extension
   * @param defaultHintPath The default location for the specific driver
   * @param mustExist       Do we need to know where this drive is located,
   *                        if this is true and the file is not found an error will be thrown
   * @return The path to the web driver
   */
  public static String getDriverLocation(String driverFile, String defaultHintPath,
      boolean mustExist) {
    // Get the hint path from the config
    String hintPath = SeleniumConfig.getDriverHintPath();

    // Try the hint path first
    if (!hintPath.isEmpty() && Paths.get(hintPath, driverFile).toFile().exists()) {
      return hintPath;
    }

    // Try the default hint path next
    if (!defaultHintPath.isEmpty() && Paths.get(defaultHintPath, driverFile).toFile().exists()) {
      return Paths.get(defaultHintPath).toString();
    }

    // Try the test location
    Path path = Paths.get(new File("").getAbsolutePath());
    String testLocation = path.getParent().toString();
    if (Paths.get(testLocation, driverFile).toFile().exists()) {
      return testLocation;
    }

    // Try resources
    ClassLoader classLoader = WebDriverFactory.class.getClassLoader();
    URL url = classLoader.getResource(driverFile);
    if (url != null) {
      File file = new File(url.getPath());
      return file.getParent();
    }

    // We didn't find the web driver so throw an error if we need to know where it is
    if (mustExist) {
      throw new RuntimeException(
          StringProcessor.safeFormatter("Unable to find driver for '%s'", driverFile));
    }

    return "";
  }

  /**
   * Get the file path for the Edge driver pre-installed on the system.
   *
   * @param file The file we are looking for
   * @return The parent folder of the given file or the empty string if the file is not found
   */
  static String getWindowsEdgeDriverLocation(String file) {
    String edgeDriverFolder = "Microsoft Web Driver";

    Path path = Paths.get(System.getenv("ProgramW6432"), edgeDriverFolder, file);
    if (path.toFile().isFile()) {
      return path.getParent().toString();
    }

    path = Paths.get(System.getenv("ProgramFiles(x86)"), edgeDriverFolder, file);
    if (path.toFile().isFile()) {
      return path.getParent().toString();
    }

    path = Paths.get(System.getenv("ProgramFiles"), edgeDriverFolder, file);
    if (path.toFile().isFile()) {
      return path.getParent().toString();
    }

    return "";
  }
}