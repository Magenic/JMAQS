# <img src="resources/jmaqslogo.jpg" height="32" width="32"> Appium Configuration

## Overview
The AppiumConfig class is used to get values from the AppiumMaqs section of your test run properties.
<br>These values come from your config.xml and/or test run parameters.

## AppiumMaqs
The AppiumMaqs configuration section contains the following Keys:
* ***PlatformName***: The mobile device's platform name />
* ***PlatformVersion***: The platform version/>
* ***DeviceName***: The mobile device's name />
* ***MobileHubUrl***: the mobile hub URL />
* ***MobileCommandTimeout***: How long to wait before the mobile command should time out />
* ***MobileWaitTime***: The wait time for the mobile device />
* ***MobileTimeout***: The timeout for the mobile device />

## AppiumCapsMaqs
The AppiumCapsMaqs configuration section contains the following Keys:
* ***Username*** : The username />
* ***AccessKey***: The access key />
* ***DeviceName***: The mobile device name />
* ***DeviceOrientation***: The device's screen orientation />
* ***BrowserName***: The browser name being used />

## Available methods
Get the initialized Appium timeout:
```java
TimeSpan timeout = AppiumConfig.getCommandTimeout();
```

Get the Device Name:
```java
String name = AppiumConfig.getDeviceName();
```

Get mobile hub url:
```java
Url proxyAddress = AppiumConfig.getMobileHubUrl();
```

Get the mobile OS type:
```java
string platformName = AppiumConfig.getPlatformName();
```

Get the OS Version:
```java
String platformVersion = AppiumConfig.getPlatformVersion();
```

Get if we should save page source on fail:
```java
boolean pageSourceOnFail = AppiumConfig.getSavePagesourceOnFail();
```

Get the wait default wait driver:
```java
boolean pageSourceOnFail = AppiumConfig.getWaitDriver();
```

Get the mobile device If no browser is provided in the project configuration file. We default to Android:
```java
AppiumDriver driver = AppiumConfig.getWaitDriver();
```

Set the script and page timeouts:
```java
AppiumDriver driver = new AppiumDriver();
AppiumConfig.setTimouts(driver);
```

# Sample config files
## config.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<configuration>
  <configSections>
    <section name="MagenicMaqs" type="System.Configuration.NameValueSectionHandler" />
    <section name="AppiumMaqs" type="System.Configuration.NameValueSectionHandler" />
    <section name="AppiumCapsMaqs" type="System.Configuration.NameValueSectionHandler" />
  </configSections>
  <MagenicMaqs>
    <!-- Do you want to create logs for your tests
    <add key="Log" value="YES"/>
    <add key="Log" value="NO"/>
    <add key="Log" value="OnFail"/>-->
    <add key="Log" value="OnFail" />

    <!--Logging Levels
    <add key="LogLevel" value="VERBOSE"/>
    <add key="LogLevel" value="INFORMATION"/>
    <add key="LogLevel" value="GENERIC"/>
    <add key="LogLevel" value="SUCCESS"/>
    <add key="LogLevel" value="WARNING"/>
    <add key="LogLevel" value="ERROR"/>-->
    <add key="LogLevel" value="VERBOSE" />

    <!-- Logging Types
    <add key="LogType" value="CONSOLE"/>
    <add key="LogType" value="TXT"/>
    <add key="LogType" value="HTML"/>-->
    <add key="LogType" value="TXT" />

    <!-- Log file path - Defaults to build location if no value is defined
    <add key="FileLoggerPath" value="C:\Frameworks\"/>-->

    <!--Retry and overall timeout in milliseconds-->
    <add key="WaitTime" value="1000" />
    <add key="Timeout" value="10000" />
  </MagenicMaqs>
  <AppiumMaqs>
    <!-- Device settings -->
    <add key="PlatformName" value="Android" />
    <add key="PlatformVersion" value="6.0" />
    <add key="DeviceName" value="Android GoogleAPI Emulator" />

    <!-- Appium or grid connection -->
    <add key="MobileHubUrl" value="http://ondemand.saucelabs.com:80/wd/hub" />

    <!-- Command time-out in milliseconds -->
    <add key="MobileCommandTimeout" value="122000" />

    <!-- Wait time in milliseconds - AKA how long do you wait for rechecking something -->
    <add key="MobileWaitTime" value="1000" />

    <!-- Time-out in milliseconds -->
    <add key="MobileTimeout" value="10000" />

    <!-- Do you want to take screenshots upon Soft Assert Failures
    <add key="SoftAssertScreenshot" value="YES"/>
    <add key="SoftAssertScreenshot" value="NO"/>-->
    <add key="SoftAssertScreenshot" value="NO" />

    <!-- Screenshot Image Formats
    <add key="ImageFormat" value="Bmp"/>
    <add key="ImageFormat" value="Gif"/>
    <add key="ImageFormat" value="Jpeg"/>
    <add key="ImageFormat" value="Png"/>
    <add key="ImageFormat" value="Tiff"/>-->
    <add key="ImageFormat" value="Png" />

    <!-- Do you want to save page source when a Soft Assert fails
    <add key="SavePagesourceOnFail" value="YES"/>
    <add key="SavePagesourceOnFail" value="NO"/> -->
    <add key="SavePagesourceOnFail" value="NO" />
  </AppiumMaqs>
  <AppiumCapsMaqs>
    <add key="username" value="Partner_Magenic" />
    <add key="accessKey" value="7e0592a4-16de-4c6b-9b87-ee61aa43ceac" />
    <add key="deviceName" value="Android Emulator" />
    <add key="deviceOrientation" value="portrait" />
    <add key="browserName" value="Chrome" />
  </AppiumCapsMaqs>
</configuration>
```