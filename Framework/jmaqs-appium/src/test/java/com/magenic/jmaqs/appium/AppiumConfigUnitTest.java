/*
 * Copyright 2019 (C) Magenic, All rights Reserved
 */
package com.magenic.jmaqs.appium;

import com.magenic.jmaqs.utilities.helper.Config;
import com.magenic.jmaqs.utilities.helper.ConfigSection;
import java.util.HashMap;
import java.util.Map;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class AppiumConfigUnitTest {

  private String username = "username";
  private String browserName = "browserName";
  private String accessKey = "accessKey";
  private String deviceOrientation = "deviceOrientation";

  @Test
  @Ignore("Test ignored because method is deprecated.")
  public void testGetMobileDeviceUDID() throws Exception {
    String mobileDeviceUDID = AppiumConfig.getMobileDeviceUdid();
    Assert.assertTrue(mobileDeviceUDID.equalsIgnoreCase("1234567890ACDEF1234687890ABCDEF"));
  }

  @Test
  @Ignore("Test ignored because method is deprecated.")
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
  @Ignore("Test ignored because method is deprecated.")
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
        .assertEquals(capabilitiesAsStrings.get(accessKey), "0a4d7d84-f93b-43e6-9af1-1c89ac143355");
    softAssert.assertTrue(capabilitiesAsStrings.containsKey(browserName));
    softAssert.assertEquals(capabilitiesAsStrings.get(browserName), "Chrome");
    softAssert.assertTrue(capabilitiesAsStrings.containsKey(deviceOrientation));
    softAssert.assertEquals(capabilitiesAsStrings.get(deviceOrientation), "portrait");
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
        .assertEquals(capabilitiesAsObjects.get(accessKey), "0a4d7d84-f93b-43e6-9af1-1c89ac143355");
    softAssert.assertTrue(capabilitiesAsObjects.containsKey(browserName));
    softAssert.assertEquals(capabilitiesAsObjects.get(browserName), "Chrome");
    softAssert.assertTrue(capabilitiesAsObjects.containsKey(deviceOrientation));
    softAssert.assertEquals(capabilitiesAsObjects.get(deviceOrientation), "portrait");
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
  @Ignore("Impacting future tests since there is no way to reload config")
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
