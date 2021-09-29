/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.utilities.helper;

import java.util.HashMap;
import java.util.Map;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Configuration unit test class.
 */
public class ConfigUnitTest {

  /**
   * Test getting an entire section from the config.
   */
  @Test(groups = TestCategories.UTILITIES)
  public void getSectionWithConfigSecEnumTest() {
    Map<String, String> testSection = Config.getSection(ConfigSection.SELENIUM_MAQS);
    Assert.assertEquals(testSection.get("TestKey"), "testValueTwo");
    Assert.assertEquals(testSection.get("Browser"), "Internet Explorer");
  }

  /**
   * Test adding a list of test settings to the config.
   */
  @Test(groups = TestCategories.UTILITIES)
  public void addTestSettingValuesNewSectionTest() {
    HashMap<String, String> newValueMap = new HashMap<>();
    newValueMap.put("BROWSER1", "CHROME1");
    newValueMap.put("DBString2", "DBString2222");

    Config.addTestSettingValues(newValueMap, "NewSection", false);
    Assert.assertEquals(Config.getSection("NewSection").get("BROWSER1"), "CHROME1");
    Assert.assertEquals(Config.getSection("NewSection").get("DBString2"), "DBString2222");
  }

  /**
   * Test overriding existing values in the config.
   */
  @Test(groups = TestCategories.UTILITIES)
  public void addGeneralTestSettingValuesOverrideValuesTest() {
    HashMap<String, String> newValueMap = new HashMap<>();
    newValueMap.put("BrowserOverride", "CHROME");
    newValueMap.put("TimeoutOverride", "13333333");

    Config.addGeneralTestSettingValues(newValueMap, true);
    Assert.assertEquals(Config.getGeneralValue("BrowserOverride"), "CHROME");
    Assert.assertEquals(Config.getGeneralValue("TimeoutOverride"), "13333333");
  }

  /**
   * Test not overriding existing values in the config.
   */
  @Test(groups = TestCategories.UTILITIES)
  public void addGeneralTestSettingValuesDoNotOverrideValuesTest() {
    HashMap<String, String> newValueMap = new HashMap<>();
    newValueMap.put("DoNotBrowserOverride", "CHROME");
    newValueMap.put("DoNotTimeoutOverride", "13333333");

    HashMap<String, String> newValueMapTwo = new HashMap<>();
    newValueMapTwo.put("DoNotBrowserOverride", "IE");
    newValueMapTwo.put("DoNotTimeoutOverride", "5555");

    // add values to the override config since the values don't exist in the
    // override config
    Config.addGeneralTestSettingValues(newValueMap, false);
    Assert.assertEquals(Config.getGeneralValue("DoNotBrowserOverride"), "CHROME");
    Assert.assertEquals(Config.getGeneralValue("DoNotTimeoutOverride"), "13333333");

    // don't add the values to the override config since the values do exist in the
    // override config
    Config.addGeneralTestSettingValues(newValueMapTwo, false);
    Assert.assertEquals(Config.getGeneralValue("DoNotBrowserOverride"), "CHROME");
    Assert.assertEquals(Config.getGeneralValue("DoNotTimeoutOverride"), "13333333");

    // do add the values because of the override flag
    Config.addGeneralTestSettingValues(newValueMapTwo, true);
    Assert.assertEquals(Config.getGeneralValue("DoNotBrowserOverride"), "IE");
    Assert.assertEquals(Config.getGeneralValue("DoNotTimeoutOverride"), "5555");
  }

  /**
   * Test getting a value out of the default section of the config.
   */
  @Test(groups = TestCategories.UTILITIES)
  public void getGeneralValueTest() {
    Assert.assertEquals(Config.getGeneralValue("TestKey"), "testValue");
    Assert.assertEquals(Config.getGeneralValue("nonExistentKey", "defaultValue"), "defaultValue");
  }

  /**
   * Test getting a value of a specified section of the config.
   */
  @Test(groups = TestCategories.UTILITIES)
  public void getValueForSectionTest() {
    Assert.assertEquals(Config.getValueForSection("SeleniumMaqs", "TestKey"), "testValueTwo");
    Assert.assertEquals(Config.getValueForSection(ConfigSection.SELENIUM_MAQS, "Browser"), "Internet Explorer");
    Assert.assertEquals(Config.getValueForSection("SeleniumMaqs", "nonExistentKey", "defaultValue"), "defaultValue");
  }

  /**
   * Test getting a value from the config using the full defined path.
   */
  @Test(groups = TestCategories.UTILITIES)
  public void getValueTest() {
    Assert.assertEquals(Config.getValue("TestKey", "defaultValue"), "defaultValue");
    Assert.assertEquals(Config.getValue("SeleniumMaqs.TestKey"), "testValueTwo");
  }

  /**
   * Test checking if the key exists.
   */
  @Test(groups = TestCategories.UTILITIES)
  public void doesKeyExistTest() {
    Assert.assertTrue(Config.doesKeyExist("SeleniumMaqs.TestKey"));
    Assert.assertTrue(Config.doesGeneralKeyExist("TimeoutOverride"));
    Assert.assertTrue(Config.doesKeyExist("HubAddress", ConfigSection.SELENIUM_MAQS));
    Assert.assertFalse(Config.doesKeyExist("HubAddress", ConfigSection.MAGENIC_MAQS));
  }
}
