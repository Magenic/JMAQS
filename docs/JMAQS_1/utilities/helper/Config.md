# <img src="resources/jmaqslogo.jpg" height="32" width="32"> Config

# Overview
Config is class file granting access to properties in the config.xml.

# Available methods
[GetGeneralValue](#GetGeneralValue)  
[GetValueForSection](#GetValueForSection)  
[GetValue](#GetValue)  
[DoesKeyExist](#DoesKeyExist)  
[AddGeneralTestSettingValues](#AddGeneralTestSettingValues)


## GetGeneralValue
This pulls configuration values from MagenicMaqs section of your app.config. It will return an emtpy string if the key is not found in the app.config.

```java
Config.getGeneralValue("Key");  
```

This pulls configuration values from MagenicMaqs section of your app.config. It will return the default value if the key is not found in the app.config.
```java
Config.getGeneralValue("Key", "DefaultValueToBeReturned");  
```

## GetValueForSection
This pulls configuration values from the provided section of your app.config. It will return an empty string if the key is not found in the app.config.

```java
Config.getValueForSection("SeleniumMaqs", "Key"))
```
This pulls configuration values from the provided section of your app.config. It will return the default value if the key is not found in the app.config.

```java
Config.getValueForSection("SeleniumMaqs", "Key", "DefaultValueToBeReturned");
```

## GetValue
Get the configuration value for a specific key. Does not assume a section.
```java
String getValue(String key, String defaultValue)
```

Does the key exist somewhere in the config
```java
Config.getValue("TestKey", "defaultValue")
```

## DoesKeyExist
Does a key exist in the MagenicMaqs section of the config file.

```java
Config.doesKeyExist("Key");
```
Does a key exist in the specified section of the config file.

```java
Config.doesKeyExist("Key", "SeleniumMaqs");    
```

## AddGeneralTestSettingValues
Ability to Add or Override settings for MagenicMaqs section of the config.

```java
HashMap<String, String> newValueMap = new HashMap<String, String>();
newValueMap.put("BrowserOverride", "CHROME");
newValueMap.put("TimeoutOverride", "13333333");

Config.addGeneralTestSettingValues(newValueMap, true);
```
Ability to Add or Override settings for the specified section of the config.

```java
HashMap<String, String> configValues = new HashMap<>();
    configValues.put("MobileCommandTimeout", "sixty thousand");
    Config.addTestSettingValues(configValues, ConfigSection.APPIUM_MAQS, true); 
```

