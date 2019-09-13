/*
 * Copyright 2019 (C) Magenic, All rights Reserved
 */
package com.magenic.jmaqs.appium;

import com.magenic.jmaqs.utilities.helper.Config;
import com.magenic.jmaqs.utilities.helper.ConfigSection;
import java.util.HashMap;
import java.util.Map;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class AppiumConfigUnitTest {

  private String username = "username";
  private String app = "app";
  private String appiumVersion = "appiumVersion";
  private String accessKey = "accessKey";

  @Test
  public void testGetMobileDeviceUDID() throws Exception {
    String mobileDeviceUDID = AppiumConfig.getMobileDeviceUdid();
    Assert.assertTrue(mobileDeviceUDID.equalsIgnoreCase("1234567890ACDEF1234687890ABCDEF"));
  }

  @Test
  public void testGetBundleID() throws Exception {
    String bundleID = AppiumConfig.getBundleId();
    Assert.assertTrue(bundleID.equalsIgnoreCase("com.magenic.maqs.appium.tester"));
  }

  @Test
  public void testGetOSVersion() throws Exception {
    String osVersion = AppiumConfig.getPlatformVersion();
    Assert.assertTrue(osVersion.equalsIgnoreCase("6.0"));
  }

  @Test
  public void testGetDeviceName() throws Exception {
    String deviceName = AppiumConfig.getDeviceName();
    Assert.assertTrue(deviceName.equalsIgnoreCase("Android GoogleAPI Emulator"));
  }

  @Test
  public void testGetMobileHubUrlString() throws Exception {
    String mobileHubUrl = AppiumConfig.getMobileHubUrlString();
    Assert.assertTrue(mobileHubUrl.equalsIgnoreCase("http://ondemand.saucelabs.com:80/wd/hub"));
  }

  @Test
  public void testSetTimeouts() throws Exception {
  }

  @Test
  public void testGetCapabilitiesAsStrings() {
    Map<String, String> capabilitiesAsStrings = AppiumConfig.getCapabilitiesAsStrings();

    SoftAssert softAssert = new SoftAssert();
    softAssert.assertTrue(capabilitiesAsStrings.containsKey(username));
    softAssert.assertEquals(capabilitiesAsStrings.get(username), "Partner_Magenic");
    softAssert.assertTrue(capabilitiesAsStrings.containsKey(accessKey));
    softAssert
        .assertEquals(capabilitiesAsStrings.get(accessKey), "7e0592a4-16de-4c6b-9b87-ee61aa43ceac");
    softAssert.assertTrue(capabilitiesAsStrings.containsKey(app));
    softAssert.assertEquals(capabilitiesAsStrings.get(app), "org.openintents.shopping");
    softAssert.assertTrue(capabilitiesAsStrings.containsKey(appiumVersion));
    softAssert.assertEquals(capabilitiesAsStrings.get(appiumVersion), "1.7.1");
    softAssert.assertAll();
  }

  @Test
  public void testGetCapabilitiesAsObjects() {
    Map<String, Object> capabilitiesAsObjects = AppiumConfig.getCapabilitiesAsObjects();

    SoftAssert softAssert = new SoftAssert();
    softAssert.assertTrue(capabilitiesAsObjects.containsKey(username));
    softAssert.assertEquals(capabilitiesAsObjects.get(username), "Partner_Magenic");
    softAssert.assertTrue(capabilitiesAsObjects.containsKey(accessKey));
    softAssert
        .assertEquals(capabilitiesAsObjects.get(accessKey), "7e0592a4-16de-4c6b-9b87-ee61aa43ceac");
    softAssert.assertTrue(capabilitiesAsObjects.containsKey(app));
    softAssert.assertEquals(capabilitiesAsObjects.get(app), "org.openintents.shopping");
    softAssert.assertTrue(capabilitiesAsObjects.containsKey(appiumVersion));
    softAssert.assertEquals(capabilitiesAsObjects.get(appiumVersion), "1.7.1");
    softAssert.assertAll();
  }

  @Test
  public void testGetSavePageSourceOnFail() {
    Assert.assertFalse(AppiumConfig.getSavePageSourceOnFail());
  }

  @Test
  public void testGetSoftAssertScreenShot() {
    Assert.assertFalse(AppiumConfig.getSoftAssertScreenShot());
  }

  @Test
  public void testGetCommandTimeout() {
    Assert.assertEquals(AppiumConfig.getCommandTimeout().toMillis(), 122000);
  }

  @Test(expectedExceptions = NumberFormatException.class)
  public void testGetCommandTimeoutError() {
    HashMap<String, String> configValues = new HashMap<>();
    configValues.put("MobileCommandTimeout", "sixty thousand");
    Config.addTestSettingValues(configValues, ConfigSection.AppiumMaqs, true);
    AppiumConfig.getCommandTimeout();
  }

  @Test
  public void testGetMobileTimeout() {
    Assert.assertEquals(AppiumConfig.getMobileTimeout().toMillis(), 10000);
  }

  @Test
  public void testGetDeviceType() {
    Assert.assertEquals(AppiumConfig.getDeviceType(), PlatformType.ANDROID);
  }

  @Test
  public void testGetDeviceTypeAndroid() {
    Assert.assertEquals(AppiumConfig.getDeviceType("android"), PlatformType.ANDROID);
  }

  @Test
  public void testGetDeviceTypeIOS() {
    Assert.assertEquals(AppiumConfig.getDeviceType("ios"), PlatformType.IOS);
  }

  @Test
  public void testGetDeviceTypeWindows() {
    Assert.assertEquals(AppiumConfig.getDeviceType("windows"), PlatformType.WINDOWS);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void testGetDeviceTypeError() {
    AppiumConfig.getDeviceType("linux");
  }
}
