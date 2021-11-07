/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.selenium.constants;

import com.magenic.jmaqs.base.BaseGenericTest;
import com.magenic.jmaqs.utilities.helper.TestCategories;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * The type Operating system test.
 */
public class OperatingSystemUnitTest extends BaseGenericTest {

  /**
   * Test get operating system name.
   */
  @Test
  public void testGetOperatingSystemName() {
    OperatingSystem operatingSystem = OperatingSystem.WINDOWS;
    Assert.assertEquals(operatingSystem.getOperatingSystemName(), "Windows");
  }

  /**
   * Test get operating system with abbreviation linux.
   */
  @Test
  public void testGetOperatingSystemWithAbbreviationWindows() {
    Assert.assertEquals(OperatingSystem.getOperatingSystemWithAbbreviation("win"), OperatingSystem.WINDOWS);
  }

  /**
   * Test get operating system with abbreviation linux.
   */
  @Test
  public void testGetOperatingSystemWithAbbreviationWindowsServer() {
    Assert.assertEquals(OperatingSystem.getOperatingSystemWithAbbreviation("windows server 2016"),
        OperatingSystem.WINDOWS);
  }

  /**
   * Test get operating system with abbreviation windows.
   */
  @Test
  public void testGetOperatingSystemWithAbbreviationLinux() {
    Assert.assertEquals(OperatingSystem.getOperatingSystemWithAbbreviation("nix"), OperatingSystem.LINUX);
  }

  /**
   * Test get operating system with abbreviation mac.
   */
  @Test
  public void testGetOperatingSystemWithAbbreviationMac() {
    Assert.assertEquals(OperatingSystem.getOperatingSystemWithAbbreviation("mac os x"), OperatingSystem.MACOS);
  }

  /**
   * Test get operating system with abbreviation error.
   */
  @Test(expectedExceptions = IllegalArgumentException.class)
  public void testGetOperatingSystemWithAbbreviationError() {
    OperatingSystem.getOperatingSystemWithAbbreviation("operating system");
  }

  /**
   * Test get operating system - macOS.
   */
  @Test(singleThreaded = true, dependsOnGroups = TestCategories.SELENIUM)
  public void testGetOperatingSystemMacOS() {
    System.setProperty("os.name", "macOS");
    OperatingSystem operatingSystem = OperatingSystem.getOperatingSystem();
    Assert.assertEquals(operatingSystem, OperatingSystem.MACOS);
  }

  /**
   * Test get operating system - Linux.
   */
  @Test(singleThreaded = true,
        dependsOnMethods = "com.magenic.jmaqs.selenium.constants.OperatingSystemUnitTest.testGetOperatingSystemMacOS")
  public void testGetOperatingSystemLinux() {
    System.setProperty("os.name", "Linux");
    OperatingSystem operatingSystem = OperatingSystem.getOperatingSystem();
    Assert.assertEquals(operatingSystem, OperatingSystem.LINUX);
  }

  /**
   * Test get operating system - Windows.
   */
  @Test(singleThreaded = true,
        dependsOnMethods = "com.magenic.jmaqs.selenium.constants.OperatingSystemUnitTest.testGetOperatingSystemLinux")
  public void testGetOperatingSystemWindows() {
    System.setProperty("os.name", "Windows");
    OperatingSystem operatingSystem = OperatingSystem.getOperatingSystem();
    Assert.assertEquals(operatingSystem, OperatingSystem.WINDOWS);
  }
}