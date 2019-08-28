/*
 * Copyright 2019 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.utilities.helper;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Generic wait unit test class.
 * Tests running in serial.
 */
@Test(singleThreaded = true)
public class GenericWaitUnitTestsNotParallel {
  /**
   * Constant test string.
   */
  private String teststring = "Test String";

  /**
   * Test override retry time.
   */
  private static long testretry = 100;

  /**
   * Test override time out time.
   */
  private static long testtimeout = 500;

  /**
   * Bool for unit tests.
   */
  private static boolean initialReturnValue = false;

  /**
   * Counter for unit tests.
   */
  private static int number = 0;

  /**
   * Test wait for with no parameters works when the wait function returns true.
   */
  @Test(groups = TestCategories.Utilities)
  public void passNoParamForTest() {
    initialReturnValue = false;
    try {
      GenericWait.waitFor(GenericWaitUnitTestsNotParallel::isNotParamTest);
    } catch (Exception e) {
      Assert.fail("waitFor no parameter test failed with exception", e);
    }
  }

  /**
   * Test wait until with one parameter works when the wait function returns true.
   */
  @Test(groups = TestCategories.Utilities)
  public void passStringUntilTest() {
    number = 0;
    try {
      Assert.assertTrue(GenericWait.waitUntil(this::isParamTestString, teststring + "3"),
          "Failed single parameter test");
    } catch (Exception e) {
      Assert.fail("waitUntil with parameter failed with exception", e);
    }
  }

  /**
   * Test wait until function returns expected value, then returns the value.
   */
  @Test(groups = TestCategories.Utilities)
  public void passStringsEqual() {
    number = 0;
    String matchedVal = "";
    try {
      matchedVal = GenericWait.waitUntilMatch(this::functionTestString, "Test String3");
    } catch (Exception e) {
      Assert.fail("waitUnitMatch parameter test failed with exception", e);
    }

    Assert.assertTrue(matchedVal.equals("Test String3"), "Failed expected parameter test.");
  }

  /**
   * Test wait until function returns expected value, then returns the value.
   */
  @Test(groups = TestCategories.Utilities)
  public void passStringsEqualOverride() {
    number = 0;
    String matchedVal = "";

    try {
      matchedVal = GenericWait
          .waitUntilMatch(this::functionTestString, testretry, testtimeout, "Test String3");
    } catch (Exception e) {
      Assert.fail("waitUnitMatch with parameter and retry/timeouts failed with exception", e);
    }

    Assert.assertTrue(matchedVal.equals("Test String3"), "Failed expected parameter test.");
  }

  /**
   * Test wait until function returns expected value, throws an exception if a timeout occurs.
   */
  @Test(groups = TestCategories.Utilities)
  public void passStringWaitFor() {
    int[] number = { 0 };
    try {
      GenericWait.waitForMatch(() -> teststring + ++number[0], teststring + "3");
    } catch (Exception e) {
      Assert.fail("waitForMatch parameter test failed with exception", e);
    }
  }

  /**
   * Tests waits checking that the function returns a value equal to the
   * input value until the input test retry and test timeout before throwing an exception.
   */
  @Test(groups = TestCategories.Utilities)
  public void passStringWaitForOverride() {
    number = 0;
    try {
      GenericWait.waitForMatch(this::functionTestString, testretry, testtimeout, teststring + "3");
    } catch (Exception e) {
      Assert.fail("waitForMatch parameter test and retry/timeouts failed with exception", e);
    }
  }

  /**
   * Test wait for with one parameter works when the wait function returns true.
   */
  @Test(groups = TestCategories.Utilities)
  public void passStringForTest() {
    int[] number = { 0 };
    try {
      GenericWait.waitFor((p) -> p.equals(teststring + number[0]++), teststring + "3");
    } catch (Exception e) {
      Assert.fail("waitFor parameter test failed with exception", e);
    }
  }

  /**
   * Test function that checks if the test string passed in is the same as the constant test string.
   *
   * @param testString The test string
   * @return True if the constant and passed in test strings match
   */
  private boolean isParamTestString(String testString) {
    return testString.equals(this.teststring + number++);
  }

  /**
   * Test function that always returns a specific string.
   *
   * @return Always returns a specific string
   */
  private String functionTestString() {
    return teststring + number++;
  }

  /**
   * Test function that always returns true.
   *
   * @return Always returns true
   */
  private static boolean isNotParamTest() {
    initialReturnValue = !initialReturnValue;
    return !initialReturnValue;
  }
}
