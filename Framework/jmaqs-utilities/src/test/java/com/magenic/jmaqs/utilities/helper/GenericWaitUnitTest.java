/*
 * Copyright 2019 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.utilities.helper;

import com.magenic.jmaqs.utilities.helper.GenericWait;
import com.magenic.jmaqs.utilities.helper.TestCategories;
import com.magenic.jmaqs.utilities.helper.TimeoutException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Unit test for the GenericWait class.
 */
public class GenericWaitUnitTest {
  /**
   * Constant test string.
   */
  private String teststring = "Test String";

  /**
   * Test override retry time.
   */
  private static long testretry = 200;

  /**
   * Test override time out time.
   */
  private static long testtimeout = 1000;

  /**
   * Counter for unit tests.
   */
  private static int number = 0;

  /**
   * Test wait until with no parameters works when the wait function returns true.
   */
  @Test(groups = TestCategories.Utilities)
  public void passNoParamUntilTest() {
    // have to use an array because the iterator needs to be mutable
    int[] loop = { 0 };
    try {
      Assert.assertTrue(GenericWait.waitUntil(() -> loop[0]++ > 3), "Failed no parameter test");
    } catch (Exception e) {
      Assert.fail("waitUntil no parameter test failed with exception", e);
    }
  }

  /**
   * Test wait until with an array of parameters works when the wait function returns true.
   */
  @Test(groups = TestCategories.Utilities)
  public void passObjectArrayUntilTest() {
    ArrayList<Object> objects = new ArrayList<>();
    objects.add("one");
    objects.add(new HashMap<Integer, UUID>());

    try {
      Assert.assertTrue(GenericWait.waitUntil(this::isTwoParameters, objects.toArray()),
          "Failed parameter array test");
    } catch (Exception e) {
      Assert.fail("waitUntil generic object test failed with exception", e);
    }
  }

  /**
   * Test wait for with an array of parameters works when the wait function returns true.
   */
  @Test(groups = TestCategories.Utilities)
  public void passObjectArrayForTest() {
    ArrayList<Object> objects = new ArrayList<>();
    objects.add("one");
    objects.add(new HashMap<Integer, UUID>());

    try {
      GenericWait.waitFor(this::isTwoParameters, objects.toArray());
    } catch (Exception e) {
      Assert.fail("waitFor generic object test failed with exception", e);
    }
  }

  /**
   * Test wait until with a single parameter works when the wait function returns false.
   */
  @Test(groups = TestCategories.Utilities)
  public void failStringUntilTest() {
    try {
      Assert.assertFalse(GenericWait.waitUntil(this::isParamTestString, "Bad"),
          "Failed single parameter test");
    } catch (Exception e) {
      Assert.fail("waitUntil failed with exception", e);
    }
  }

  /**
   * Test wait until with a parameter array works when the wait function returns false.
   */
  @Test(groups = TestCategories.Utilities)
  public void failObjectArrayUntilTest() {
    ArrayList<Object> objects = new ArrayList<>();
    try {
      Assert.assertFalse(GenericWait.waitUntil(this::isTwoParameters, objects.toArray()),
          "Failed parameter array test");
    } catch (Exception e) {
      Assert.fail("waitUntil failed with exception", e);
    }
  }

  /**
   * Test wait until with a parameter array works when the wait function returns false.
   */
  @Test(expectedExceptions = UnsupportedOperationException.class, groups = TestCategories.Utilities)
  public void throwExceptionWithoutParamTest() throws Throwable {
    try {
      GenericWait.waitForTrue(this::throwError);
    } catch (Exception e) {
      throw e.getCause();
    }
  }

  /**
   * Test waitForTrue passes.
   */
  @Test(groups = TestCategories.Utilities)
  public void waitForTruePasses() {
    try {
      GenericWait.waitForTrue(() -> 2 == 2);
    } catch (Exception e) {
      Assert.fail("waitForTrue threw unexpected exception", e);
    }
  }

  /**
   * Test waitForTrue passes.
   */
  @Test(groups = TestCategories.Utilities)
  public void waitForTruePassesWithParameters() {
    try {
      GenericWait.waitForTrue((c) -> 2 == c, 2);
    } catch (Exception e) {
      Assert.fail("waitForTrue threw unexpected exception", e);
    }
  }

  /**
   * Test wait for with no parameters returns the a timeout exception.
   */
  @Test(expectedExceptions = TimeoutException.class, groups = TestCategories.Utilities)
  public void throwTimeoutExceptionWithoutParamTest() throws Exception {
    GenericWait.waitForTrue(this::isParamTest);
  }

  /**
   * Test wait for with a parameter returns the function exception when the check times out.
   */
  @Test(expectedExceptions = RuntimeException.class, groups = TestCategories.Utilities)
  public void throwExceptionWithParamTest() throws Throwable {
    try {
      GenericWait.waitForTrue(this::throwError, teststring);
    } catch (Exception e) {
      throw e.getCause();
    }
  }

  /**
   * Test wait for with parameters returns the a timeout exception.
   */
  @Test(expectedExceptions = TimeoutException.class, groups = TestCategories.Utilities)
  public void throwTimeoutExceptionWithParamTest() throws Exception {
    GenericWait.waitForTrue(this::isTwoParameters, (new ArrayList<Object>()).toArray());
  }

  /**
   * Test wait without parameters returns function exception.
   */
  @Test(expectedExceptions = UnsupportedOperationException.class, groups = TestCategories.Utilities)
  public void throwExceptionWithoutParamWithCustomTimesTest() throws Throwable {
    try {
      GenericWait.wait(this::throwError, testretry, testtimeout, true);
    } catch (Exception e) {
      throw e.getCause();
    }
  }

  /**
   * Test wait with parameters returns function exception.
   */
  @Test(expectedExceptions = RuntimeException.class, groups = TestCategories.Utilities)
  public void throwExceptionWithParamWithCustomTimesTest() throws Throwable {
    try {
      GenericWait.wait(this::throwError, testretry, testtimeout, true, "Anything");
    } catch (Exception e) {
      throw e.getCause();
    }
  }

  /**
   * Verify custom timeout without parameters works.
   */
  @Test(groups = TestCategories.Utilities)
  public void customTimeoutWithoutParamTest() {
    try {
      long max = testtimeout + testretry + testretry;
      LocalDateTime start = LocalDateTime.now();
      GenericWait.wait(this::isParamTest, testretry, testtimeout, false);
      long duration = ChronoUnit.MILLIS.between(start, LocalDateTime.now());
      Assert.assertTrue(duration < max,
          "The max wait time should be no more than " + max + " but was " + duration);
    } catch (Exception e) {
      Assert.fail("wait threw unexpected exception", e);
    }
  }

  /**
   * Verify custom timeout with parameters works.
   */
  @Test(groups = TestCategories.Utilities)
  public void customTimeoutWithParamTest() {
    try {
      long max = testtimeout + testretry + testretry;
      LocalDateTime start = LocalDateTime.now();
      GenericWait.wait(this::isParamTestString, testretry, testtimeout, false, "bad");
      long duration = ChronoUnit.MILLIS.between(start, LocalDateTime.now());
      Assert.assertTrue(duration < max,
          "The max wait time should be no more than " + max + " but was " + duration);
    } catch (Exception e) {
      Assert.fail("wait threw unexpected exception", e);
    }
  }

  /**
   * Verify that Wait with input parameter throws expected exception.
   */
  @Test(expectedExceptions = TimeoutException.class, groups = TestCategories.Utilities)
  public void waitForFunctionWithInputExceptionThrown()
      throws InterruptedException, TimeoutException {
    GenericWait.wait(this::throwError, testretry, testtimeout, "input");
  }

  /**
   * Verify that WaitFor returns the correct value of its called function.
   */
  @Test(groups = TestCategories.Utilities)
  public void waitForTest() {
    try {
      Assert.assertFalse(GenericWait.waitFor(this::isParamTest));
    } catch (Exception e) {
      Assert.fail("waitFor threw unexpected exception", e);
    }
  }

  /**
   * Verify that Wait without input parameter throws expected exception.
   */
  @Test(expectedExceptions = TimeoutException.class, groups = TestCategories.Utilities)
  public void waitForFunctionWithoutInputExceptionThrown()
      throws InterruptedException, TimeoutException {
    GenericWait.wait(this::throwError, testretry, testtimeout);
  }

  /**
   * Verify waitUntilMatch returns as expected.
   */
  @Test(groups = TestCategories.Utilities)
  public void waitUntilMatchNeverMatch() {
    try {
      String[] loop = { "aa" };
      Assert.assertEquals(GenericWait.waitUntilMatch(() -> loop[0] += "", "bb"), "aa");
    } catch (InterruptedException e) {
      Assert.fail("waitUntil threw unexpected exception", e);
    }
  }

  /**
   * Verify waitUntilMatch returns as expected.
   */
  @Test(groups = TestCategories.Utilities)
  public void waitUntilMatch() {
    try {
      String[] loop = { "" };
      Assert.assertEquals(GenericWait.waitUntilMatch(() -> loop[0] += "a", "aaa"), "aaa");
    } catch (InterruptedException e) {
      Assert.fail("waitUntil threw unexpected exception", e);
    }
  }

  /**
   * Verify waitUntilMatch with timeout returns as expected.
   */
  @Test(groups = TestCategories.Utilities)
  public void waitUntilMatchTimeout() {
    try {
      String[] loop = { "aa" };
      Assert.assertEquals(
          GenericWait.waitUntilMatch(() -> loop[0] += "", testretry, testtimeout, "bb"), "aa");
    } catch (InterruptedException e) {
      Assert.fail("waitUntil threw unexpected exception", e);
    }
  }

  /**
   * Verify waitForMatch with passes as expected.
   */
  @Test(groups = TestCategories.Utilities)
  public void waitForMatchPass() {
    try {
      String[] loop = { "" };
      GenericWait.waitForMatch(() -> loop[0] += "a", "aaa");
    } catch (Exception e) {
      Assert.fail("waitFor threw unexpected exception", e);
    }
  }

  /**
   * Verify waitForMatch throws timeout exception.
   */
  @Test(expectedExceptions = TimeoutException.class, groups = TestCategories.Utilities)
  public void waitForMatchTimeoutException() throws InterruptedException, TimeoutException {
    String[] loop = { "a" };
    GenericWait.waitForMatch(() -> loop[0] += "a", "bb");
  }

  /**
   * Verify waitForMatch with time retry.
   */
  @Test(groups = TestCategories.Utilities)
  public void waitForMatchDefinedRetryPass() {
    try {
      String[] loop = { "" };
      GenericWait.waitForMatch(() -> loop[0] += "a", testretry, testtimeout, "aaa");
    } catch (Exception e) {
      Assert.fail("waitFor threw unexpected exception", e);
    }
  }

  /**
   * Verify waitForMatch with time retry and time overridden throws timeout exception.
   */
  @Test(expectedExceptions = TimeoutException.class, groups = TestCategories.Utilities)
  public void waitForMatchDefinedRetryTimeout() throws InterruptedException, TimeoutException {
    String[] loop = { "a" };
    GenericWait.waitForMatch(() -> loop[0] += "a", testretry, testtimeout, "bb");
  }

  /**
   * Test function that always returns false.
   *
   * @return Always returns false
   */
  private boolean isParamTest() {
    return false;
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
   * Test function that checks if the object array passed in is in the form expected.
   *
   * @param parameters Object array
   * @return True if the array is in the form expected
   */
  private boolean isTwoParameters(Object[] parameters) {
    return (parameters.length == 2 && (parameters[0] instanceof String)
        && (parameters[1] instanceof HashMap));
  }

  /**
   * Test function that always throws a not implemented exception.
   *
   * @return Always throws an exception
   */
  private boolean throwError() {
    throw new UnsupportedOperationException();
  }

  /**
   * Test function that always throws a runtime exception.
   *
   * @param testString Test string
   * @return Always throws an exception
   */
  private boolean throwError(String testString) {
    throw new RuntimeException();
  }
}
