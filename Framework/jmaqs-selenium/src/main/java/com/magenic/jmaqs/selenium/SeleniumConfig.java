/*
 * Copyright 2019 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.selenium;

import com.magenic.jmaqs.utilities.helper.Config;
import com.magenic.jmaqs.utilities.helper.ConfigSection;
import com.magenic.jmaqs.utilities.helper.StringProcessor;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Selenium specific configuration class.
 */
public final class SeleniumConfig {
  /**
   * The web service configuration section.
   */
  private static final ConfigSection SELENIUM_SECTION = ConfigSection.SeleniumMaqs;

  /**
   * The remote selenium configuration section.
   */
  private static final ConfigSection SELENIUM_CAPS_SECTION = ConfigSection.RemoteSeleniumCapsMaqs;

  /**
   * Get the file extension for the screenshots.
   *
   * @return The type of file, defaults to .png
   */
  static String getScreenShotExtension() {
    return Config.getValueForSection(SELENIUM_SECTION,"ImageFormat", ".png");
  }
  
  /**
   * Get the browser type name - Example: Chrome.
   *
   * @return The browser type
   */
  static String getBrowserName() {
    return Config.getValueForSection(SELENIUM_SECTION,"Browser", "Chrome");
  }

  /**
   * Get the hub URL.
   *
   * @return the hub URL
   */
  static String getHubUrl() {
    return Config.getValueForSection(SELENIUM_SECTION, "HubUrl");
  }

  /**
   * Get the web site base url.
   *
   * @return The web site base url
   */
  static String getWebSiteBase() {
    return Config.getValueForSection(SELENIUM_SECTION,"WebSiteBase");
  }

  /**
   * Get the SavePageSourceOnFail flag from config.
   *
   * @return True if the flag is set to "Yes"
   */
  static boolean getSavePagesourceOnFail() {
    return Config.getValueForSection(SELENIUM_SECTION,
            "SavePagesourceOnFail").equalsIgnoreCase("Yes");
  }

  /**
   * Get the SoftAssertScreenshot flag from config.
   *
   * @return True if the flag is set to "Yes"
   */
  static boolean getSoftAssertScreenshot() {
    return Config.getValueForSection(SELENIUM_SECTION,
            "SoftAssertScreenshot").equalsIgnoreCase("Yes");
  }

  /**
   * Get the hint path for the web driver.
   *
   * @return The hint path for the web driver
   */
  static String getDriverHintPath() {
    String defaultPath = new java.io.File("Resources").getAbsolutePath();
    return Config.getValueForSection(SELENIUM_SECTION,"WebDriverHintPath", defaultPath);
  }

  /**
   * Get the remote browser type name.
   *
   * @return The browser type being used on grid
   */
  static String getRemoteBrowserName() {
    return Config.getValueForSection(SELENIUM_SECTION,"RemoteBrowser", "Chrome");
  }

  /**
   * Get the remote browser. If no remote browser is provided in the project configuration file, we
   * default to Chrome.
   *
   * @return The web driver
   */
  static WebDriver getRemoteBrowser() throws Exception {
    return browser(getRemoteBrowserName());
  }

  /**
   * Get the webdriver for the provided remote browser. Browsers are maximized by default.
   *
   * @param remoteBrowser
   *          The browser type we want to use
   * @return A WebDriver
   */
  static WebDriver getRemoteBrowser(String remoteBrowser) throws Exception {
    WebDriver webDriver = null;

    try {
      switch (remoteBrowser.toUpperCase()) {
        case "INTERNET EXPLORER":
        case "INTERNETEXPLORER":
        case "IE":
          System.setProperty("webdriver.ie.driver",
              getDriverLocation("IEDriverServer.exe") + File.separator + "IEDriverServer.exe");
          webDriver = new InternetExplorerDriver();
          break;
        case "FIREFOX":

          System.setProperty("webdriver.gecko.driver",
              getDriverLocation("geckodriver.exe") + File.separator + "geckodriver.exe");

          FirefoxOptions options = new FirefoxOptions();
          options.setProfile(new FirefoxProfile());

          webDriver = new FirefoxDriver(GeckoDriverService.createDefaultService(), options);
          break;
        case "CHROME":
          ChromeOptions chromeOptions = new ChromeOptions();
          chromeOptions.addArguments("test-type");
          chromeOptions.addArguments("--disable-web-security");
          chromeOptions.addArguments("--allow-running-insecure-content");
          chromeOptions.addArguments("--disable-extensions");

          System.setProperty("webdriver.chrome.driver",
              getDriverLocation("chromedriver.exe") + File.separator + "chromedriver.exe");
          webDriver = new ChromeDriver(chromeOptions);
          break;
        case "HEADLESSCHROME":
          ChromeOptions headlessChromeOptions = new ChromeOptions();
          headlessChromeOptions.addArguments("test-type");
          headlessChromeOptions.addArguments("--disable-web-security");
          headlessChromeOptions.addArguments("--allow-running-insecure-content");
          headlessChromeOptions.addArguments("--disable-extensions");
          headlessChromeOptions.addArguments("--no-sandbox");
          headlessChromeOptions.addArguments("--headless");

          System.setProperty("webdriver.chrome.driver",
              getDriverLocation("chromedriver.exe") + File.separator + "chromedriver.exe");
          webDriver = new ChromeDriver(headlessChromeOptions);
          break;
        case "EDGE":
          EdgeOptions edgeOptions = new EdgeOptions();
          edgeOptions.setPageLoadStrategy("Normal");

          System.setProperty("webdriver.edge.driver",
              getDriverLocation("MicrosoftWebDriver.exe",
                  getProgramFilesFolder("Microsoft Web Driver", "MicrosoftWebDriver.exe"))
                  + File.separator + "MicrosoftWebDriver.exe");
          webDriver = new EdgeDriver(edgeOptions);
          break;
        case "REMOTE":
          // MalformedURLException exception is thrown if no protocol is
          // specified, or an unknown protocol is found, or spec is null.
          try {
            webDriver = new RemoteWebDriver(new URL(Config.getValueForSection(SELENIUM_SECTION,
                    "HubUrl")),
                getRemoteCapabilities());
          } catch (MalformedURLException e) {
            throw new Exception("Malformed URL Exception thrown"
                    + " trying to create the remote web driver.", e);
          }
          break;
        default:
          throw new RuntimeException(
              StringProcessor.safeFormatter("Browser type %s is not supported",
                      remoteBrowser));
      }

      // Maximize the browser and than return it
      webDriver.manage().window().maximize();
      return webDriver;
    } catch (Exception e) {
      if (webDriver != null) {
        try {
          webDriver.quit();
        } catch (Exception quitExecption) {
          throw new Exception("Failed to quit Web driver during setup", quitExecption);
        }
      }
      String errorException = "Failed to setup web driver. "
              + "Your driver may be out of date or unsupported. Exception: ";
      throw new Exception(MessageFormat.format("{1} {0}", errorException, e));
    }
  }

  /**
   * Get the remote platform type name.
   *
   * @return The platform (or OS) to run remote tests against
   */
  static String getRemotePlatform() {
    return Config.getValueForSection(SELENIUM_SECTION,"RemotePlatform");
  }

  /**
   * Get the remote browser version.
   *
   * @return The browser version to run against on grid
   */
  static String getRemoteBrowserVersion() {
    return Config.getValueForSection(SELENIUM_SECTION,"RemoteBrowserVersion");
  }

  /**
   * Get the browser. If no browser is provided in the project configuration file, we default to
   * Chrome. Browsers are maximized by default
   *
   * @return The web driver
   */
  static WebDriver browser() throws Exception {
    return browser(getBrowserName());
  }

  /**
   * Get the webdriver for the provided browser. Browsers are maximized by default.
   *
   * @param browser
   *          The browser type we want to use
   * @return A WebDriver
   */
  static WebDriver browser(String browser) throws Exception {
    WebDriver webDriver = null;

    try {
      switch (browser.toUpperCase()) {
        case "INTERNET EXPLORER":
        case "INTERNETEXPLORER":
        case "IE":
          System.setProperty("webdriver.ie.driver",
                  getDriverLocation("IEDriverServer.exe") + File.separator + "IEDriverServer.exe");
          webDriver = new InternetExplorerDriver();
          break;
        case "FIREFOX":

          System.setProperty("webdriver.gecko.driver",
                  getDriverLocation("geckodriver.exe") + File.separator + "geckodriver.exe");

          FirefoxOptions options = new FirefoxOptions();
          options.setProfile(new FirefoxProfile());

          webDriver = new FirefoxDriver(GeckoDriverService.createDefaultService(), options);
          break;
        case "CHROME":
          ChromeOptions chromeOptions = new ChromeOptions();
          chromeOptions.addArguments("test-type");
          chromeOptions.addArguments("--disable-web-security");
          chromeOptions.addArguments("--allow-running-insecure-content");
          chromeOptions.addArguments("--disable-extensions");

          System.setProperty("webdriver.chrome.driver",
                  getDriverLocation("chromedriver.exe") + File.separator + "chromedriver.exe");
          webDriver = new ChromeDriver(chromeOptions);
          break;
        case "HEADLESSCHROME":
          ChromeOptions headlessChromeOptions = new ChromeOptions();
          headlessChromeOptions.addArguments("test-type");
          headlessChromeOptions.addArguments("--disable-web-security");
          headlessChromeOptions.addArguments("--allow-running-insecure-content");
          headlessChromeOptions.addArguments("--disable-extensions");
          headlessChromeOptions.addArguments("--no-sandbox");
          headlessChromeOptions.addArguments("--headless");
          headlessChromeOptions.addArguments("--window-size=1920,1080");

          System.setProperty("webdriver.chrome.driver",
                  getDriverLocation("chromedriver.exe") + File.separator + "chromedriver.exe");
          webDriver = new ChromeDriver(headlessChromeOptions);
          break;
        case "EDGE":
          EdgeOptions edgeOptions = new EdgeOptions();
          edgeOptions.setPageLoadStrategy("Normal");

          System.setProperty("webdriver.edge.driver",
                  getDriverLocation("MicrosoftWebDriver.exe",
                          getProgramFilesFolder("Microsoft Web Driver", "MicrosoftWebDriver.exe"))
                          + File.separator + "MicrosoftWebDriver.exe");
          webDriver = new EdgeDriver(edgeOptions);
          break;
        case "REMOTE":
          // MalformedURLException exception is thrown if no protocol is
          // specified, or an unknown protocol is found, or spec is null.
          try {
            webDriver = new RemoteWebDriver(new URL(Config.getValueForSection(SELENIUM_SECTION,
                    "HubUrl")),
                      getRemoteCapabilities());
          } catch (MalformedURLException e) {
            throw new Exception("Malformed URL Exception thrown "
                    + "trying to create the remote web driver.", e);
          }
          break;
        default:
          throw new RuntimeException(
                    StringProcessor.safeFormatter("Browser type %s is not supported", browser));
      }

      // Maximize the browser and than return it
      webDriver.manage().window().maximize();
      return webDriver;
    } catch (Exception e) {
      if (webDriver != null) {
        try {
          webDriver.quit();
        } catch (Exception quitException) {
          throw new Exception("Failed to quit Web driver during setup", quitException);
        }
      }
      String errorException = "Failed to setup web driver. "
              + "Your driver may be out of date or unsupported. Exception: ";
      throw new Exception(MessageFormat.format("{1} {0}", errorException, e));
    }
  }

  /**
   * Set the script and page timeouts.
   *
   * @param driver
   *          Brings in a WebDriver
   */
  static void setTimeouts(WebDriver driver) {
    int timeoutTime = Integer.parseInt(Config.getGeneralValue("Timeout", "0"));
    driver.manage().timeouts().setScriptTimeout(timeoutTime, null);
    driver.manage().timeouts().pageLoadTimeout(timeoutTime, null);
  }

  /**
   * Get the initialize Selenium timeout.
   *
   * @return The initialize timeout
   */
  static int getCommandTimeout() {
    String value = Config.getValueForSection(SELENIUM_SECTION, "SeleniumCommandTimeout", "60000");
    try {
      Integer.parseInt(value);
      return Integer.parseInt(value);
    } catch (NumberFormatException e) {
      throw new NumberFormatException("SeleniumCommandTimeout should be a number, "
              + "but the current value is: " + value);
    }
  }

  /**
   * Get the remote desired capability.
   *
   * @return The remote desired capability
   */

  private static DesiredCapabilities getRemoteCapabilities() {
    DesiredCapabilities capabilities;
    String remoteBrowser = getRemoteBrowserName();
    String remotePlatform = getRemotePlatform();
    String remoteBrowserVersion = getRemoteBrowserVersion();

    switch (remoteBrowser.toUpperCase()) {
      case "INTERNET EXPLORER":
      case "INTERNETEXPLORER":
      case "IE":
        capabilities = DesiredCapabilities.internetExplorer();
        break;
      case "FIREFOX":
        capabilities = DesiredCapabilities.firefox();
        break;
      case "CHROME":
        capabilities = DesiredCapabilities.chrome();
        break;
      case "EDGE":
        capabilities = DesiredCapabilities.edge();
        break;
      case "SAFARI":
        capabilities = DesiredCapabilities.safari();
        break;
      default:
        // changed from exception to illegal argument exception
        throw new IllegalArgumentException(StringProcessor
            .safeFormatter("Remote browser type %s is not supported", remoteBrowser));
    }

    // Add a platform setting if one was provided
    if (remotePlatform.length() > 0) {
      capabilities.setCapability("platform", remotePlatform);
    }

    // Add a remote browser setting if one was provided
    if (remoteBrowserVersion.length() > 0) {
      capabilities.setCapability("version", remoteBrowserVersion);
    }

    return capabilities;
  }

  /**
   * Get the remote capabilities as a HashMap
   * @return HashMap of remote capabilities
   */
  static Map<String, String> getRemoteCapabilitiesAsStrings() {
    return Config.getSection(SELENIUM_CAPS_SECTION);
  }

  /**
   * Get the remote capabilities as a HashMap
   * @return HashMap of remote capabilities
   */
  static Map<String, Object> getRemoteCapabilitiesAsObjects() {
    return new HashMap<>(getRemoteCapabilitiesAsStrings());
  }

  /**
   * Get the web driver location.
   *
   * @param driverFile
   *          The web drive file, including extension
   * @param defaultHintPath
   *          The default location for the specific driver
   * @param mustExist
   *          Do we need to know where this drive is located, if this is true and the file is not
   *          found an error will be thrown
   * @return The path to the web driver
   */
  private static String getDriverLocation(String driverFile,
                                          String defaultHintPath,
                                          boolean mustExist) {
    // Get the hint path from the app.config
    String hintPath = getDriverHintPath();

    // Try the hintpath first
    if (!(hintPath == null || hintPath.isEmpty())
        && Files.exists(Paths.get(hintPath, driverFile))) {
      return hintPath;
    }

    // Try the default hint path next
    if (!(defaultHintPath == null || defaultHintPath.isEmpty())
        && Files.exists(Paths.get(defaultHintPath, driverFile))) {
      return Paths.get(defaultHintPath).toString();
    }

    // get the root path of the project

    Path path = Paths.get(new File("").getAbsolutePath());
    String testLocation = path.getParent().toString();
    // Try the test dll location
    if (Files.exists(Paths.get(testLocation, driverFile))) {
      return testLocation;
    }

    ClassLoader classLoader = SeleniumConfig.class.getClassLoader();
    URL url = classLoader.getResource(driverFile);
    if (url != null) {
      File file = new File(url.getPath());
      return file.getParent();
    }

    // We didn't find the web driver so throw an error if we need to know
    // where it is
    if (mustExist) {
      throw new RuntimeException(
          StringProcessor.safeFormatter("Unable to find driver for '%s'", driverFile));
    }

    return "";
  }

  /**
   * Get the web driver location.
   *
   * @param driverFile
   *          The web drive file, including extension
   * @return The overloaded method
   */
  private static String getDriverLocation(String driverFile) {

    return getDriverLocation(driverFile, "", true);
  }

  /**
   * Get the web driver location.
   *
   * @param driverFile
   *          The web drive file, including extension
   * @param defaultHintPath
   *          The default location for the specific driver
   *
   * @return The overloaded method
   */
  private static String getDriverLocation(String driverFile, String defaultHintPath) {

    return getDriverLocation(driverFile, defaultHintPath, true);
  }

  /**
   * Get the programs file folder which contains given file.
   *
   * @param folderName
   *          The programs file sub folder
   * @param file
   *          The file we are looking for
   * @return The parent folder of the given file or the empty String if the file is not found
   */
  private static String getProgramFilesFolder(String folderName, String file) {
    // Handle 64 bit systems first
    boolean is64bit = false;
    if (System.getProperty("os.name").contains("Windows")) {
      is64bit = (System.getenv("ProgramFiles(x86)") != null);
    } else {
      is64bit = (System.getProperty("os.arch").indexOf("64") != -1);
    }

    if (is64bit) {
      Path path = Paths.get(System.getenv("ProgramW6432"), folderName, file);

      if (Files.isRegularFile(path)) {
        return path.getParent().toString();
      }

      path = Paths.get(System.getenv("ProgramFiles(x86)"), folderName, file);

      if (Files.isRegularFile(path)) {
        return path.getParent().toString();
      }

    } else {
      Path path = Paths.get(System.getenv("ProgramFiles"), folderName, file);

      if (path.toFile().isFile()) {
        return path.getParent().toString();
      }
    }

    return "";
  }

  /**
   * Get the default wait driver.
   *
   * @param driver
   *          The Web Driver
   * @return A WebDriverWait
   */
  static WebDriverWait getWaitDriver(WebDriver driver) {
    return new WebDriverWait(driver, getTimeoutTime());
  }

  /**
   * Get the timeout from config.
   *
   * @return The timeout time
   */
  static int getTimeoutTime() {
    return Integer.parseInt(Config.getGeneralValue("BrowserTimeout", "0"));
  }

  /**
   * Get wait from config.
   *
   * @return The wait time
   */
  /*
    static int getWaitTime() {
    return Integer.parseInt(Config.getGeneralValue("BrowserWaitTime", "0"));
  }
   */

  /**
   * Get browser size from config.
   *
   * @return The browser size
   */
  static String getBrowserSize() {
    return Config.getValueForSection(SELENIUM_SECTION, "BrowserSize", "MAXIMIZE".toUpperCase());
  }
}