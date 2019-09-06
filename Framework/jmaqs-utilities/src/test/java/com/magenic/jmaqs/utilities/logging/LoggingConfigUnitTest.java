/*
 * Copyright 2019 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.utilities.logging;

import com.magenic.jmaqs.utilities.helper.Config;
import com.magenic.jmaqs.utilities.helper.StringProcessor;
import com.magenic.jmaqs.utilities.helper.TestCategories;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.util.HashMap;

/**
 * Logging Configuration unit test class.
 * Tests running in serial.
 */
@Test(singleThreaded = true)
public class LoggingConfigUnitTest {
    /**
     * Test getting Logging Enabled Setting.
     * Override Config to 'YES'
     */
    @Test(groups = TestCategories.Utilities)
    public void getLoggingEnabledSettingTest() {
        HashMap<String, String> newValueMap = new HashMap<>();
        newValueMap.put("Log", "YES");
        Config.addGeneralTestSettingValues(newValueMap, true);
        Assert.assertEquals(LoggingConfig.getLoggingEnabledSetting(), LoggingEnabled.YES,
                "Expected Logging Enabled Setting YES.");
    }

    /**
     * Test getting Logging Enabled Setting.
     * Override Config to 'ONFAIL'
     */
    @Test(groups = TestCategories.Utilities)
    public void getLoggingEnabledOnFailSettingTest() {
        HashMap<String, String> newValueMap = new HashMap<>();
        newValueMap.put("Log", "ONFAIL");
        Config.addGeneralTestSettingValues(newValueMap, true);
        Assert.assertEquals(LoggingConfig.getLoggingEnabledSetting(), LoggingEnabled.ONFAIL,
                "Expected Logging Enabled Setting ONFAIL.");
    }

    /**
     * Test getting Logging Enabled Setting.
     * Override Config to 'NO'
     */
    @Test(groups = TestCategories.Utilities)
    public void getLoggingDisabledSettingTest() {
        HashMap<String, String> newValueMap = new HashMap<>();
        newValueMap.put("Log", "NO");
        Config.addGeneralTestSettingValues(newValueMap, true);
        Assert.assertEquals(LoggingConfig.getLoggingEnabledSetting(), LoggingEnabled.NO,
                "Expected Logging Enabled Setting NO.");
    }

    /**
     * Test getting Logging Enabled Setting with an Illegal Argument
     * Override Config to 'INVALIDVALUE' - Expect IllegalArgumentException
     */
    @Test(expectedExceptions = IllegalArgumentException.class, groups = TestCategories.Utilities)
    public void getLoggingSettingIllegalArgumentTest() {
        HashMap<String, String> newValueMap = new HashMap<>();
        newValueMap.put("Log", "INVALIDVALUE");
        Config.addGeneralTestSettingValues(newValueMap, true);

        LoggingConfig.getLoggingEnabledSetting();
    }

    /**
     * Test getting Logging Level Setting.
     * Override Config to 'VERBOSE'
     */
    @Test(groups = TestCategories.Utilities)
    public void getLoggingLevelVerboseSettingTest() {
        HashMap<String, String> newValueMap = new HashMap<>();
        newValueMap.put("LogLevel", "VERBOSE");
        Config.addGeneralTestSettingValues(newValueMap, true);
        Assert.assertEquals(MessageType.VERBOSE, LoggingConfig.getLoggingLevelSetting(),
                "Expected Logging Level Setting VERBOSE.");
    }

    /**
     * Test getting Logging Level Setting.
     * Override Config to 'INFORMATION'
     */
    @Test(groups = TestCategories.Utilities)
    public void getLoggingLevelInformationSettingTest() {
        HashMap<String, String> newValueMap = new HashMap<>();
        newValueMap.put("LogLevel", "INFORMATION");
        Config.addGeneralTestSettingValues(newValueMap, true);
        Assert.assertEquals(MessageType.INFORMATION, LoggingConfig.getLoggingLevelSetting(),
                "Expected Logging Level Setting INFORMATION.");
    }

    /**
     * Test getting Logging Level Setting.
     * Override Config to 'GENERIC'
     */
    @Test(groups = TestCategories.Utilities)
    public void getLoggingLevelGenericSettingTest() {
        HashMap<String, String> newValueMap = new HashMap<>();
        newValueMap.put("LogLevel", "GENERIC");
        Config.addGeneralTestSettingValues(newValueMap, true);
        Assert.assertEquals(MessageType.GENERIC, LoggingConfig.getLoggingLevelSetting(),
                "Expected Logging Level Setting GENERIC.");
    }

    /**
     * Test getting Logging Level Setting.
     * Override Config to 'SUCCESS'
     */
    @Test(groups = TestCategories.Utilities)
    public void getLoggingLevelSuccessSettingTest() {
        HashMap<String, String> newValueMap = new HashMap<>();
        newValueMap.put("LogLevel", "SUCCESS");
        Config.addGeneralTestSettingValues(newValueMap, true);
        Assert.assertEquals(MessageType.SUCCESS, LoggingConfig.getLoggingLevelSetting(),
                "Expected Logging Level Setting SUCCESS.");
    }

    /**
     * Test getting Logging Level Setting.
     * Override Config to 'WARNING'
     */
    @Test(groups = TestCategories.Utilities)
    public void getLoggingLevelWarningSettingTest() {
        HashMap<String, String> newValueMap = new HashMap<>();
        newValueMap.put("LogLevel", "WARNING");
        Config.addGeneralTestSettingValues(newValueMap, true);
        Assert.assertEquals(MessageType.WARNING, LoggingConfig.getLoggingLevelSetting(),
                "Expected Logging Level Setting WARNING.");
    }

    /**
     * Test getting Logging Level Setting.
     * Override Config to 'ERROR'
     */
    @Test(groups = TestCategories.Utilities)
    public void getLoggingLevelErrorSettingTest() {
        HashMap<String, String> newValueMap = new HashMap<>();
        newValueMap.put("LogLevel", "ERROR");
        Config.addGeneralTestSettingValues(newValueMap, true);
        Assert.assertEquals(MessageType.ERROR, LoggingConfig.getLoggingLevelSetting(),
                "Expected Logging Level Setting ERROR.");
    }

    /**
     * Test getting Logging Level Setting.
     * Override Config to 'SUSPENDED'
     */
    @Test(groups = TestCategories.Utilities)
    public void getLoggingLevelSuspendedSettingTest() {
        HashMap<String, String> newValueMap = new HashMap<>();
        newValueMap.put("LogLevel", "SUSPENDED");
        Config.addGeneralTestSettingValues(newValueMap, true);
        Assert.assertEquals(MessageType.SUSPENDED, LoggingConfig.getLoggingLevelSetting(),
                "Expected Logging Level Setting SUSPENDED.");
    }

    /**
     * Test getting Logging Level Setting with Illegal Argument.
     * Override Config to 'INVALIDVALUE' - Expect IllegalArgumentException
     */
    @Test(expectedExceptions = IllegalArgumentException.class, groups = TestCategories.Utilities)
    public void getLoggingLevelIllegalArgumentTest() {
        HashMap<String, String> newValueMap = new HashMap<>();
        newValueMap.put("LogLevel", "INVALIDVALUE");
        Config.addGeneralTestSettingValues(newValueMap, true);

        LoggingConfig.getLoggingLevelSetting();
    }

    /**
     * Test getting File Logger.
     * Override Config LogType to 'TXT' which creates FileLogger.
     */
    @Test(groups = TestCategories.Utilities)
    public void getFileLoggerTest() {
        HashMap<String, String> newValueMap = new HashMap<>();
        newValueMap.put("LogType", "TXT");
        newValueMap.put("Log", "YES");
        Config.addGeneralTestSettingValues(newValueMap, true);
        String fileName = "TestLog.txt";
        Logger logger = LoggingConfig.getLogger(fileName);
        Assert.assertTrue(logger instanceof FileLogger, "Expected Logger to be of Type FileLogger.");
    }

    /**
     * Test getting File Logger.
     * Override Config LogType to 'CONSOLE' which creates ConsoleLogger.
     */
    @Test(groups = TestCategories.Utilities)
    public void getConsoleLoggerTest() {
        HashMap<String, String> newValueMap = new HashMap<>();
        newValueMap.put("LogType", "CONSOLE");
        newValueMap.put("Log", "YES");
        Config.addGeneralTestSettingValues(newValueMap, true);
        String fileName = "TestLog.txt";
        Logger logger = LoggingConfig.getLogger(fileName);
        Assert.assertTrue(logger instanceof ConsoleLogger, "Expected Logger to be of Type ConsoleLogger.");
    }

    /**
     * Test getting File Logger.
     * Override Config Log to 'NO' which creates ConsoleLogger by default.
     */
    @Test(groups = TestCategories.Utilities)
    public void getConsoleLoggerLoggingDisabledTest() {
        HashMap<String, String> newValueMap = new HashMap<>();
        newValueMap.put("Log", "NO");
        Config.addGeneralTestSettingValues(newValueMap, true);
        String fileName = "TestLog.txt";
        Logger logger = LoggingConfig.getLogger(fileName);
        Assert.assertTrue(logger instanceof ConsoleLogger, "Expected Logger to be of Type ConsoleLogger.");
    }

    /**
     * Test getting Log Directory.
     * Config value not defined - Compare to Default Path.
     */
    /*
    // FIXME: Commenting out test until repaired.  Expected result does not make sense.
    @Test(groups = TestCategories.Utilities)
    public void getLogDirectoryTest() {
        String log = LoggingConfig.getLogDirectory();
        String defaultPath = new File(LoggingConfig.getLogDirectory()).getAbsolutePath();

        Assert.assertEquals(defaultPath.contains(log),"jmaqs-utilities\\.\\target\logs",
                "Expected Default Path: " + defaultPath);
    }
    */
}
