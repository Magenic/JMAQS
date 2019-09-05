/*
 * Copyright 2019 (C) Magenic, All rights Reserved
 */
package com.magenic.jmaqs.appium;

import com.magenic.jmaqs.appium.AppiumConfig;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AppiumConfigUnitTest {

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
}
