# <img src="resources/jmaqslogo.jpg" height="32" width="32"> Web Service Configuration

## Overview
The WebServiceConfig class is used to get values from the WebServiceMaqs section of your test run properties.
<br>These values come from your App.config, appsettings.json and/or test run parameters.

## WebServiceMaqs
The WebServiceMaqs configuration section contains the following Keys:
* ***WebServiceUri*** : The base webservice uri
* ***WebServiceTimeout*** : How long to wait for something before timing out - Used heavily with the JMAQS waits

## Available methods
Get the base web service URL:
```java
String url = WebServiceConfig.getWebServiceUri();
```

Get the web service timeout:
```java
int timeout = WebServiceConfig.getWebServiceTimeout();
```

Get the if HTTP client should use proxy
```java
boolean useProxy = WebServiceConfig.getUseProxy();
```

Get the proxy address to use
```java
string proxyAddress = WebServiceConfig.getProxyAddress();
```

# Sample config files
## App.config
```xml
<?xml version="1.0" encoding="utf-8" ?>
<configuration>
  <configSections>
    <section name="MagenicMaqs" type="System.Configuration.NameValueSectionHandler"/>
    <section name="WebServiceMaqs" type="System.Configuration.NameValueSectionHandler"/>
  </configSections>
  <WebServiceMaqs>
    <!-- Web service root -->
    <add key="WebServiceUri" value="http://magenicautomation.azurewebsites.net" />

    <!-- Time-out in milliseconds -->
    <add key="WebServiceTimeout" value="10000" />

    <!-- Proxy settings -->
    <add key="UseProxy" value="NO" />
    <add key="ProxyAddress" value="127.0.0.1:8080" />
  </WebServiceMaqs>
  <MagenicMaqs>
    <!-- Wait time in milliseconds - AKA how long do you wait for rechecking something -->
    <add key="WaitTime" value="100"/>

    <!-- Time-out in milliseconds seconds -->
    <add key="Timeout" value="1000"/>

    <!-- Do you want to create logs for your tests
    <add key="Log" value="YES"/>
    <add key="Log" value="NO"/>
    <add key="Log" value="OnFail"/>-->
    <add key="Log" value="OnFail"/>

    <!--Logging Levels
    <add key="LogLevel" value="VERBOSE"/>
    <add key="LogLevel" value="INFORMATION"/>
    <add key="LogLevel" value="GENERIC"/>
    <add key="LogLevel" value="SUCCESS"/>
    <add key="LogLevel" value="WARNING"/>
    <add key="LogLevel" value="ERROR"/>-->
    <add key="LogLevel" value="INFORMATION" />

    <!-- Logging Types
    <add key="LogType" value="CONSOLE"/>
    <add key="LogType" value="TXT"/>
    <add key="LogType" value="HTML"/>-->
    <add key="LogType" value="TXT"/>

    <!-- Log file path - Defaults to build location if no value is defined
    <add key="FileLoggerPath" value="C:\Frameworks\"/>-->

  </MagenicMaqs>
</configuration>

```
## appsettings.json
```json
{
  "WebServiceMaqs": {
    "WebServiceUri": "http://magenicautomation.azurewebsites.net",
    "WebServiceTimeout": "1000",
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