
/*
 * Copyright 2019 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.selenium;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Tests for the Base Selenium Test page
 */
public class BaseSeleniumPageModelTest extends BaseSeleniumTest {


    @Test
    public void checkTestObject() {
        SeleniumTestObject testObject = this.getSeleniumTestObject();
        Assert.assertNotNull(testObject);
        this.unloadSeleniumTestObject();
    }

    @Test
    public void checkGetBrowser() throws Exception {
        WebDriver browser = this.getBrowser();
        Assert.assertNotNull(browser);
    }
}
