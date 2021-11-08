/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.appium;

import com.magenic.jmaqs.base.BaseGenericTest;
import com.magenic.jmaqs.utilities.helper.TestCategories;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * The type Appium test object test.
 */
public class AppiumTestObjectUnitTest extends BaseGenericTest {

    /**
     * Test appium test object creation with driver.
     */
    @Test(groups = TestCategories.APPIUM)
    public void testAppiumTestObjectCreationWithDriver() {
        AppiumDriver<WebElement> defaultMobileDriver = AppiumDriverFactory.getDefaultMobileDriver();
        AppiumTestObject appiumTestObject = new AppiumTestObject(defaultMobileDriver, this.getLogger(),
                this.getFullyQualifiedTestClassName());
        Assert.assertNotNull(appiumTestObject, "Checking that appium test object via driver is not null");
    }

    /**
     * Test appium test object creation with supplier.
     */
    @Test(groups = TestCategories.APPIUM)
    public void testAppiumTestObjectCreationWithSupplier() {
        AppiumTestObject appiumTestObject = new AppiumTestObject(AppiumDriverFactory::getDefaultMobileDriver,
                this.getLogger(), this.getFullyQualifiedTestClassName());
        Assert.assertNotNull(appiumTestObject, "Checking that appium test object via supplier is not null");
    }

    /**
     * Test get appium driver.
     */
    @Test(groups = TestCategories.APPIUM)
    public void testGetAppiumDriver() {
        AppiumDriver<WebElement> defaultMobileDriver = AppiumDriverFactory.getDefaultMobileDriver();
        try (AppiumTestObject appiumTestObject = new AppiumTestObject(defaultMobileDriver, this.getLogger(),
                this.getFullyQualifiedTestClassName())) {
            AppiumDriver<WebElement> appiumDriver = appiumTestObject.getAppiumDriver();
            Assert.assertNotNull(appiumDriver, "Checking that appium driver can be retrieved from test object");
        }
    }

    /**
     * Test get appium manager.
     */
    @Test(groups = TestCategories.APPIUM)
    public void testGetAppiumManager() {
        AppiumDriver<WebElement> defaultMobileDriver = AppiumDriverFactory.getDefaultMobileDriver();

        try (AppiumTestObject appiumTestObject = new AppiumTestObject(defaultMobileDriver, this.getLogger(),
                this.getFullyQualifiedTestClassName())) {
            MobileDriverManager appiumManager = appiumTestObject.getAppiumManager();
            Assert.assertNotNull(appiumManager, "Checking that appium manager can be retrieved from test object");
        }
    }

    /**
     * Test set appium driver.
     */
    @Test(groups = TestCategories.APPIUM)
    public void testSetAppiumDriverWithDriver() {
        AppiumDriver<WebElement> defaultMobileDriver = AppiumDriverFactory.getDefaultMobileDriver();
        try (AppiumTestObject appiumTestObject = new AppiumTestObject(defaultMobileDriver, this.getLogger(),
                this.getFullyQualifiedTestClassName())) {
            int hashCode = appiumTestObject.getAppiumDriver().hashCode();
            appiumTestObject.getAppiumDriver().quit();
            appiumTestObject.setAppiumDriver(AppiumDriverFactory.getDefaultMobileDriver());
            int hashCode1 = appiumTestObject.getAppiumDriver().hashCode();
            Assert.assertNotEquals(hashCode, hashCode1, String.format(
                    "Checking that the appium driver is not the same as previous version.  First: %d, Second: %d",
                    hashCode, hashCode1));
        }
    }

    /**
     * Test setting the appium driver.
     */
    @Test(groups = TestCategories.APPIUM)
    public void testSetAppiumDriverWithSupplier() {
        AppiumDriver<WebElement> defaultMobileDriver = AppiumDriverFactory.getDefaultMobileDriver();
        try (AppiumTestObject appiumTestObject = new AppiumTestObject(defaultMobileDriver, this.getLogger(),
                this.getFullyQualifiedTestClassName())) {
            int hashCode = appiumTestObject.getAppiumDriver().hashCode();
            appiumTestObject.getAppiumDriver().quit();
            appiumTestObject.setAppiumDriver(AppiumDriverFactory::getDefaultMobileDriver);
            int hashCode1 = appiumTestObject.getAppiumDriver().hashCode();
            Assert.assertNotEquals(hashCode, hashCode1, String.format(
                    "Checking that the appium driver is not the same as previous version.  First: %d, Second: %d",
                    hashCode, hashCode1));
        }
    }
}