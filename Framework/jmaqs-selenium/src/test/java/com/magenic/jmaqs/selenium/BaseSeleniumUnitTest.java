
/*
 * Copyright 2019 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.selenium;

import com.magenic.jmaqs.base.BaseTestObject;
import com.magenic.jmaqs.utilities.logging.*;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.Test;

import java.util.ArrayList;

/**
 * Tests for the Base Selenium Test page
 */
public class BaseSeleniumUnitTest extends BaseSeleniumTest {
    /**
     * checks the web driver.
     */
    @Test()
    public void checkWebDriver() {
        Assert.assertNotNull(getWebDriver());
    }

    /**
     * checks the selenium wait.
     */
    @Test()
    public void checkSeleniumWait() {
        Assert.assertNotNull(getSeleniumWait());
    }

    /**
     * checks the test object.
     */
    @Test()
    public void checkTestObject() {
        SeleniumTestObject testObject = this.getSeleniumTestObject();
        Assert.assertNotNull(testObject);
        this.unloadSeleniumTestObject();
    }

    /**
     * checks the browser
     * @throws Exception if error occurs.
     */
    @Test
    public void checkGetBrowser() throws Exception {
        WebDriver browser = this.getBrowser();
        Assert.assertNotNull(browser);
    }

    /**
     * Validate adding exceptions to the Logged Exception list adds the exceptions correctly.
     */
    @Test
    public void addLoggedExceptionsTest() {
        ArrayList<String> exceptions = new ArrayList<>();
        exceptions.add("First Exception.");
        exceptions.add("Second Exception.");
        exceptions.add("Third Exception.");
        this.setLoggedExceptions(exceptions);

        Assert.assertEquals(this.getLoggedExceptions().size(), 3,
                "Expect that 3 Logged exceptions are in this exception list.");
    }

    /**
     * Validate the Logging Enabled Setting is YES (set in Config).
     */
    @Test
    public void loggingEnabledSettingTest() {
        Assert.assertEquals(this.getLoggingEnabledSetting(),
                LoggingConfig.getLoggingEnabledSetting());
    }

    /**
     * Validate Setting the Test Object to a new Test Object (Console Logger instead of File Logger).
     */
    @Test
    public void setSeleniumTestObjectTest() {
        Logger logger = new ConsoleLogger();
        SeleniumTestObject seleniumTestObject = new SeleniumTestObject(this.getWebDriver(),
                getSeleniumWait(), logger,
                this.getFullyQualifiedTestClassName());

        Assert.assertTrue(seleniumTestObject.getLog() instanceof ConsoleLogger,
                "Expected Test Object to be set to have a Console Logger.");
    }

    /**
     * Validate Setting the Test Object to a new Test Object (Console Logger instead of File Logger).
     */
    @Test
    public void beforeLoggingTeardownCaptureScreenshot() {
            Logger logger = new ConsoleLogger();
        BaseTestObject baseTestObject = new BaseTestObject(logger,
                this.getFullyQualifiedTestClassName());

        //setTestResult(ITestResult.STARTED);


        ArrayList<String> loggedExceptions = new ArrayList<>();
        loggedExceptions.add(LoggingEnabled.YES.toString());
        this.setLoggedExceptions(loggedExceptions);

        this.setTestObject(baseTestObject);
    }

    /*
    @Test
    public void createNewTestObjectException() {
        WebDriverFactory.getDefaultBrowser();
    }
     */

    /*
     * (non-Javadoc)
     * @see com.magenic.jmaqs.utilities.BaseTest.BaseTest#postSetupLogging()
     */
    @Override
    protected void postSetupLogging() {
        BaseSeleniumTest baseSeleniumTest = new BaseSeleniumTest();
        baseSeleniumTest.postSetupLogging();
    }

    /*
     * (non-Javadoc)
     * @see com.magenic.jmaqs.utilities.BaseTest.BaseTest#beforeLoggingTeardown(org.testng.
     * ITestResult)
     */
    @Override
    protected void beforeLoggingTeardown(ITestResult resultType) {
        BaseSeleniumTest baseSeleniumTest = new BaseSeleniumTest();
        baseSeleniumTest.beforeLoggingTeardown(resultType);
    }
}