# <img src="resources/jmaqslogo.jpg" height="32" width="32"> SeleniumConfig

## Overview
The SeleniumConfig class is used to get values from the SeleniumMaqs section of your test run properties.
<br>These values come from your config.xml, appsettings.json and/or test run parameters.

## SeleniumJMAQS
The SeleniumMaqs configuration section contains the following Keys:  
**Items are only respected when using the REMOTE browser*
* ***WebSiteBase*** : The base website url
* ***Browser*** : Which browser to use.  - *Deprecated, use WebDriverFactory, GetDefaultBrowser, or WebDriverFactory. GetBrowserWithDefaultConfiguration instead*

* ***GetBrowserType***
* ***GetRemoteBrowserType***

* ***BrowserSize*** : The browser resolution
* ***WebDriverHintPath*** : First place to look for the web drive EXE
* ***RemoteBrowser**** : The type of browser to use when executing remotely which something like Grid or SauceLabs
* ***HubUrl**** : The grid URL
* ***RemotePlatform**** : The remote OS
* ***RemoteBrowserVersion**** : The remote browser version
* ***SeleniumCommandTimeout*** : How long wait before saying the connection to Selenium has died
* ***BrowserWaitTime*** : How long to wait before rechecking for something - Used heavily with the MAQS waits
* ***BrowserTimeout*** : How long to wait for something before timing out - Used heavily with the MAQS waits
* ***SoftAssertScreenshot*** : If a screenshot should be taken when a soft assert fails
* ***ImageFormat*** :  What format screenshot should be saved as
* ***SavePagesourceOnFail*** : If page source is saved when a test fails
* ***UseProxy*** : If the browser should use a proxy address
* ***ProxyAddress*** : The proxy address and port the browser will use

## RemoteSeleniumCapsMaqs
Remote Selenium capabilities are used when only when you use a REMOTE browser.  
These are key value pairs that get added to the remote web driver's desired capabilities.

## Methods
Get the browser name:
```java
String driverName = SeleniumConfig.getBrowserName();
```
Get a web driver based on your configuration:
```java
WebDriver driver = SeleniumConfig.getBrowserType();
```
Get a web driver type based on your configuration:
```java
BrowserType type = SeleniumConfig.getBrowserType();
```
Get a web driver type for the specified browser:
```java
BrowserType type = SeleniumConfig.getBrowserType("Chrome");
```
Get a remote web driver type based on your configuration:
```java
RemoteBrowserType type = SeleniumConfig.getRemoteBrowserType();
```
Get a remoter web driver type for the specified browser:
```java
RemoteBrowserType type = SeleniumConfig.getRemoteBrowserType("Chrome");
```
Get the command timeout:
```java
Duration initTimeout = SeleniumConfig.getTimeout();
```
Get the web driver EXE hint path:
```java
String path = SeleniumConfig.getDriverHintPath();
```
Get the remote browser name:
```java
String browser = SeleniumConfig.getRemoteBrowserName();
```
Get the remote browser version:
```java
String version = SeleniumConfig.getRemoteBrowserVersion();
```
Get the remote browser platform:
```java
String platform = SeleniumConfig.getRemotePlatform();
```
Get the base website url:
```java
String siteUrl = SeleniumConfig.getWebSiteBase();
```
Get the if page source should be captured test failure:
```java
boolean savePageSourceOnFail = SeleniumConfig.getSavePagesourceOnFail();
```
Get the if screenshots should be taken when there is a soft assert failure:
```java
boolean saveSoftFailScreenshot = SeleniumConfig.getSoftAssertScreenshot();
```
Get the screenshot image format:
```java
String imageFormat = SeleniumConfig.getImageFormat();
```
# Sample config files
## config.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<configuration>
    <MagenicMaqs>
        <!-- Generic wait time in milliseconds - AKA how long do you wait for rechecking something -->
        <WaitTime>100</WaitTime>

        <!-- Generic time-out in milliseconds -->
        <Timeout>10000</Timeout>

        <!-- Do you want to create logs for your tests
        <Log>YES</Log>
        <Log>NO</Log>
        <Log>OnFail</Log>-->
        <Log>YES</Log>

        <!--Logging Levels
        <LogLevel>VERBOSE</LogLevel>
        <LogLevel>INFORMATION</LogLevel>
        <LogLevel>GENERIC</LogLevel>
        <LogLevel>SUCCESS</LogLevel>
        <LogLevel>WARNING</LogLevel>
        <LogLevel>ERROR</LogLevel>-->
        <LogLevel>VERBOSE</LogLevel>

        <!-- Logging Types
        <LogType>CONSOLE</LogType>
        <LogType>TXT</LogType>
        <LogType>HTML</LogType>-->
        <LogType>TXT</LogType>

        <!-- Log file path - Defaults to build location if no value is defined -->
        <FileLoggerPath>./target/logs</FileLoggerPath>
    </MagenicMaqs>

    <SeleniumMaqs>
        <!--Local browser settings
        <Browser>Chrome</Browser>
        <Browser>Internet Explorer</Browser>
        <Browser>Firefox</Browser>
        <Browser>Edge</Browser>
        <Browser>HEADLESSCHROME</Browser>-->
        <Browser>HEADLESSCHROME</Browser>

        <!--Remote browser settings-->
        <!--<Browser>REMOTE</Browser>-->
        <RemoteBrowser>Chrome</RemoteBrowser>
        <HubUrl>http://ondemand.saucelabs.com:80/wd/hub</HubUrl>
        <WebSiteBase>http://magenicautomation.azurewebsites.net/</WebSiteBase>
        <BrowserWaitTime>1000</BrowserWaitTime>
        <BrowserTimeout>20000</BrowserTimeout>
        <RemoteBrowserName>Chrome</RemoteBrowserName>
        <RemotePlatform>OS X 10.11</RemotePlatform>
        <RemoteVersion>54.0</RemoteVersion>

        <!--Browser Resize settings
        <BrowserSize>MAXIMIZE</BrowserSize>
        <BrowserSize>DEFAULT</BrowserSize>-->
        <BrowserSize>1024x768</BrowserSize>

        <SoftAssertScreenshot>Yes</SoftAssertScreenshot>
        <SavePagesourceOnFail>Yes</SavePagesourceOnFail>
        <ImageFormat>jpeg</ImageFormat>
        <WebDriverHintPath>./src/test/resources</WebDriverHintPath>
    </SeleniumMaqs>

    <RemoteSeleniumCapsMaqs>
        <username>Sauce_Labs_Username</username>
        <accessKey>Sauce_Labs_Accesskey</accessKey>
        <browserName>Chrome</browserName>
        <platform>OS X 10.11</platform>
        <version>54.0</version>
    </RemoteSeleniumCapsMaqs>
</configuration>
```
## appsettings.json
```json
{
  "SeleniumMaqs": {
    "WebSiteBase": "http://magenicautomation.azurewebsites.net/",
    "Browser": "Chrome",
    "HubUrl": "http://localhost:4444/wd/hub",
    "RemoteBrowser": "Chrome",
    "SeleniumCommandTimeout": "60000",
    "BrowserWaitTime": "100",
    "BrowserTimeout": "10000",
    "BrowserSize": "MAXIMIZE",
    "SoftAssertScreenshot": "NO",
    "ImageFormat": "Png",
    "SavePagesourceOnFail": "NO",
    "UseProxy": "NO",
    "ProxyAddress": "127.0.0.1:8080"
  },
  "RemoteSeleniumCapsMaqs": {
    "Username": "Sauce_Labs_Username",
    "AccessKey": "Sauce_Labs_Accesskey",
    "BrowserName": "Chrome",
    "Platform": "OS X 10.11",
    "Version": "54.0"
  },
  "MagenicMaqs": {
    "WaitTime": "100",
    "Timeout": "10000",
    "Log": "OnFail",
    "LogLevel": "INFORMATION",
    "LogType": "TXT"
  }
}
```