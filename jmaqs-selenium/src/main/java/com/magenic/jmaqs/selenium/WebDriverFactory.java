/*
 * Copyright 2020 (C) Magenic, All rights Reserved
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
 * The type Web driver factory.
 */
public class WebDriverFactory {

  private static final String WINDOW_MAX = "MAXIMIZE";

  private WebDriverFactory() {
  }

  /**
   * Gets default browser.
   *
   * @return the default browser
   * @throws Exception the exception
   */
  public static WebDriver getDefaultBrowser() throws Exception {
    return getBrowserWithDefaultConfiguration(SeleniumConfig.getBrowserType());
  }

  /**
   * Gets browser with default configuration.
   *
   * @param browser the browser
   * @return the browser with default configuration
   * @throws Exception the exception
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
   * Gets default chrome options.
   *
   * @return the default chrome options
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
   * Gets default headless chrome options.
   *
   * @return the default headless chrome options
   */
  public static ChromeOptions getDefaultHeadlessChromeOptions() {
    return getDefaultHeadlessChromeOptions(WINDOW_MAX);
  }

  /**
   * Gets default headless chrome options.
   *
   * @param size the size
   * @return the default headless chrome options
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
   * Gets default internet explorer options.
   *
   * @return the default internet explorer options
   */
  public static InternetExplorerOptions getDefaultInternetExplorerOptions() {
    InternetExplorerOptions options = new InternetExplorerOptions();
    options.ignoreZoomSettings();

    return options;
  }

  /**
   * Gets default firefox options.
   *
   * @return the default firefox options
   */
  public static FirefoxOptions getDefaultFirefoxOptions() {
    FirefoxOptions firefoxOptions = new FirefoxOptions();
    firefoxOptions.setProfile(new FirefoxProfile());

    return firefoxOptions;
  }

  /**
   * Gets default edge options.
   *
   * @return the default edge options
   */
  public static EdgeOptions getDefaultEdgeOptions() {
    EdgeOptions edgeOptions = new EdgeOptions();
    edgeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL.toString());

    return edgeOptions;
  }

  /**
   * Gets chrome driver.
   *
   * @param chromeOptions the chrome options
   * @return the chrome driver
   */
  public static WebDriver getChromeDriver(ChromeOptions chromeOptions) {
    return getChromeDriver(chromeOptions, WINDOW_MAX);
  }

  /**
   * Gets chrome driver.
   *
   * @param chromeOptions the chrome options
   * @param size          the size
   * @return the chrome driver
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
   * Gets headless chrome driver.
   *
   * @param headlessChromeOptions the headless chrome options
   * @return the headless chrome driver
   */
  public static WebDriver getHeadlessChromeDriver(ChromeOptions headlessChromeOptions) {
    System.setProperty("webdriver.chrome.driver",
        getDriverLocation(WebDriverFile.CHROME.getFileName()) + File.separator
            + WebDriverFile.CHROME.getFileName());
    return new ChromeDriver(headlessChromeOptions);
  }

  /**
   * Gets firefox driver.
   *
   * @param firefoxOptions the firefox options
   * @param size           the size
   * @return the firefox driver
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
   * Gets edge driver.
   *
   * @param edgeOptions the edge options
   * @param size        the size
   * @return the edge driver
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
   * Gets internet explorer driver.
   *
   * @param internetExplorerOptions the internet explorer options
   * @param size                    the size
   * @return the internet explorer driver
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
   * Gets default remote options.
   *
   * @return the default remote options
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
   * Gets remote options.
   *
   * @param remoteBrowser the remote browser
   * @return the remote options
   */
  public static MutableCapabilities getRemoteOptions(RemoteBrowserType remoteBrowser) {
    return getRemoteOptions(remoteBrowser, "", "", null);
  }

  /**
   * Gets remote options.
   *
   * @param remoteBrowser      the remote browser
   * @param remoteCapabilities the remote capabilities
   * @return the remote options
   */
  public static MutableCapabilities getRemoteOptions(RemoteBrowserType remoteBrowser,
      Map<String, Object> remoteCapabilities) {
    return getRemoteOptions(remoteBrowser, "", "", remoteCapabilities);
  }

  /**
   * Gets remote options.
   *
   * @param remoteBrowser        the remote browser
   * @param remotePlatform       the remote platform
   * @param remoteBrowserVersion the remote browser version
   * @param remoteCapabilities   the remote capabilities
   * @return the remote options
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
   * Sets driver options.
   *
   * @param driverOptions          the driver options
   * @param additionalCapabilities the additional capabilities
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
   * Sets browser size.
   *
   * @param webDriver the web driver
   * @param size      the size
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
   * Gets headless window size string.
   *
   * @param size the size
   * @return the headless window size string
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
   * Extract dimension from string dimension.
   *
   * @param size the size
   * @return the dimension
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
   * Gets driver location.
   *
   * @param driverFile the driver file
   * @return the driver location
   */
  public static String getDriverLocation(String driverFile) {
    return getDriverLocation(driverFile, "", true);
  }

  /**
   * Gets driver location.
   *
   * @param driverFile      the driver file
   * @param defaultHintPath the default hint path
   * @return the driver location
   */
  public static String getDriverLocation(String driverFile, String defaultHintPath) {
    return getDriverLocation(driverFile, defaultHintPath, true);
  }

  /**
   * Gets driver location.
   *
   * @param driverFile      the driver file
   * @param defaultHintPath the default hint path
   * @param mustExist       the must exist
   * @return the driver location
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
   * Gets windows edge driver location.
   *
   * @param file the file
   * @return the windows edge driver location
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