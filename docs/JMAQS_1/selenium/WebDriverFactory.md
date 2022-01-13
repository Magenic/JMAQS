# <img src="resources/jmaqslogo.jpg" height="32" width="32"> WebDriverFactory

## Overview
Factory class that is used for Web driver configuration.

## Available Methods
[GetDefaultBrowser](#GetDefaultBrowser)  
[GetBrowserWithDefaultConfiguration](#GetBrowserWithDefaultConfiguration)  
[GetDefaultChromeOptions](#GetDefaultChromeOptions)  
[GetDefaultHeadlessChromeOptions](#GetDefaultHeadlessChromeOptions)  
[GetDefaultInternetExplorerOptions](#GetDefaultInternetExplorerOptions)  
[GetDefaultFireFoxOptions](#GetDefaultFireFoxOptions)  
[GetDefaultEdgeOptions](#GetDefaultEdgeOptions)  
[GetChromeDriver](#GetChromeDriver)  
[GetHeadlessChromeDriver](#GetHeadlessChromeDriver)  
[GetFireFoxDriver](#GetFireFoxDriver)  
[GetEdgeDriver](#GetEdgeDriver)  
[GetInternetExplorerDriver](#GetInternetExplorerDriver)  
[GetDefaultRemoteOptions](#GetDefaultRemoteOptions)  
[getRemoteOptions](#GetRemoteOptions)  
[SetDriverOptions](#SetDriverOptions)  
[SetBrowserSize](#setBrowserSize)  
[GetHeadlessWindowSizeString](#GetHeadlessWindowSizeString)    
[ExtractDimensionFromString](#ExtractDimensionFromString)  
[GetDriverLocation](#GetDriverLocation)  
[GetWindowsEdgeDriverLocation](#GetWindowsEdgeDriverLocation)  

## GetDefaultBrowser
Get the default web driver based on the test run configuration.
```java
driver = WebDriverFactory.getDefaultBrowser();
```

## GetBrowserWithDefaultConfiguration
Get the default web driver (for the specified browser type) based on the test run configuration.
```java
return getBrowserWithDefaultConfiguration(SeleniumConfig.getBrowserType());
```

## GetDefaultChromeOptions
Get the default Chrome options.
```java
webDriver = getChromeDriver(getDefaultChromeOptions(), size);
```

## GetDefaultHeadlessChromeOptions
```java
ChromeOptions options = WebDriverFactory.getDefaultHeadlessChromeOptions();
ChromeOptions options = WebDriverFactory.getDefaultHeadlessChromeOptions(size);
```

## GetDefaultInternetExplorerOptions
Get the default IE options.
```java
InternetExplorerOptions getDefaultInternetExplorerOptions()
```

## GetDefaultFireFoxOptions
Get the default Firefox options.
```java
webDriver = getFirefoxDriver(getDefaultFirefoxOptions(), size);
```

## GetDefaultEdgeOptions
Get the default Edge options.
```java
webDriver = getEdgeDriver(getDefaultEdgeOptions(), size);
```

## GetChromeDriver
Initialize a new Chrome driver.
```java
webDriver = getChromeDriver(getDefaultChromeOptions(), size);
```

## GetHeadlessChromeDriver
Initialize a new headless Chrome driver.
```java
webDriver = getHeadlessChromeDriver(getDefaultHeadlessChromeOptions(size));
```

## GetFireFoxDriver
Initialize a new Firefox driver.
```java
webDriver = getFirefoxDriver(getDefaultFirefoxOptions(), size);
```

## GetEdgeDriver
Initialize a new Edge driver.
```java
webDriver = getEdgeDriver(getDefaultEdgeOptions(), size);
```

## GetInternetExplorerDriver
Get a new IE driver.
```java
webDriver = getInternetExplorerDriver(getDefaultInternetExplorerOptions(), size);
```

## GetDefaultRemoteOptions
Get the default remote driver options.
<br> Default values are pulled from the configuration.
```java
MutableCapabilities options = WebDriverFactory.getDefaultRemoteOptions();
```

## GetRemoteOptions
Get the remote driver options.
```java
MutableCapabilities options = WebDriverFactory.getRemoteOptions(RemoteBrowserType.IE);
```

## SetDriverOptions
Add additional capabilities to the driver options.
```java
HashMap additionalCapabilities = new HashMap<>();
additionalCapabilities.put("testKey", "testValue");
WebDriverFactory.setDriverOptions(options, additionalCapabilities);
```

## SetBrowserSize
Sets the browser size based on the provided string value. 
<br>Browser size is expected to be: MAXIMIZE, DEFAULT, or #x# (such as 1920x1080).
<br>MAXIMIZE just maximizes the browser.
<br>DEFAULT does not change the current size.
```java
WebDriverFactory.setBrowserSize(driver, "1920x1080");
WebDriverFactory.setBrowserSize(driver, "MAXIMIZE");
```

## GetHeadlessWindowSizeString
Get the browser/browser size as a string.
```java
String size = WebDriverFactory.getHeadlessWindowSizeString("MAXIMIZE");
```

## ExtractDimensionFromString
Get the window size as a Dimension object.
```java
Dimension dimension = WebDriverFactory.extractDimensionFromString("123x456");
```

## GetDriverLocation
Get the web driver location.
```java
String driverLocation = WebDriverFactory.getDriverLocation(WebDriverFile.CHROME.getFile());
```

## GetWindowsEdgeDriverLocation
Get the file path for the Edge driver pre-installed on the system.
```java
String driverLocation = WebDriverFactory.getWindowsEdgeDriverLocation("testFile");
```