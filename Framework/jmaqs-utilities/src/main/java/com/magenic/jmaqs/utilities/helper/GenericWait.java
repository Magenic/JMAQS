/*
 * Copyright 2020 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.utilities.helper;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Generic Wait class.
 */
public final class GenericWait {

  /**
   * Hide the default public constructor.
   */
  private GenericWait() {
    throw new IllegalStateException("Utility class");
  }

  /**
   * Default retry time for the configuration file.
   */
  private static long retryTimeFromConfig = Long.parseLong(Config.getGeneralValue("WaitTime", "0"));

  /**
   * Default timeout time from the configuration file.
   */
  private static long timeoutFromConfig = Long.parseLong(Config.getGeneralValue("Timeout", "0"));

  /**
   * Wait until the wait for true function returns true or times out.
   *
   * @param waitForTrue The function we are waiting to return true
   * @param arg         Parameter to pass to the wait for true function
   * @return True if the waitForTrue function returned true before the timeout
   */
  public static <T> boolean waitUntil(Predicate<T> waitForTrue, T arg)
      throws InterruptedException, FunctionException {
    return wait(waitForTrue, retryTimeFromConfig, timeoutFromConfig, false, arg);
  }

  /**
   * Wait until the wait for true function returns true or times out.
   *
   * @param waitForTrue The function we are waiting to return true
   * @return True if the wait for true function returned true before timing out
   */
  public static boolean waitUntil(BooleanSupplier waitForTrue)
      throws InterruptedException, FunctionException {
    return wait(waitForTrue, retryTimeFromConfig, timeoutFromConfig, false);
  }

  /**
   * Wait until the wait for true function returns true, an exception will be thrown if the
   * wait times out.
   *
   * @param waitForTrue The function we are waiting to return true
   */
  public static void waitForTrue(BooleanSupplier waitForTrue)
      throws InterruptedException, FunctionException, TimeoutException {
    if (!wait(waitForTrue, retryTimeFromConfig, timeoutFromConfig, true)) {
      throw new TimeoutException("Timed out waiting for the function to return true");
    }
  }

  /**
   * Wait until the wait for true function returns true, an exception will be thrown if the
   * wait times out.
   *
   * @param waitForTrue The function we are waiting to return true
   * @param arg         Parameter to pass to the wait for true function
   */
  public static <T> void waitForTrue(Predicate<T> waitForTrue, T arg)
      throws InterruptedException, FunctionException, TimeoutException {
    if (!wait(waitForTrue, retryTimeFromConfig, timeoutFromConfig, true, arg)) {
      throw new TimeoutException("Timed out waiting for the function to return true");
    }
  }

  /**
   * Waits for a function with a return type T to return a value that is to an argument of the
   * same type. If it times out it returns the value of the function.
   *
   * @param waitForTrue      Function that returns type T
   * @param comparativeValue value of the same type as T
   * @return if it returned before the timeout occurred
   */
  public static <T> T waitUntilMatch(Supplier<T> waitForTrue, T comparativeValue)
      throws InterruptedException {
    // Set start time and exception holder
    LocalDateTime start = LocalDateTime.now();

    T value = waitForTrue.get();

    // Checks if the two values are equal
    boolean paramsAreEqual = paramsEqual(value, comparativeValue);

    // While the params are not equal & the timeout hasn't met, keep checking
    while (!paramsAreEqual
        && (ChronoUnit.MILLIS.between(start, LocalDateTime.now())) < timeoutFromConfig) {
      // If they aren't, wait
      Thread.sleep(retryTimeFromConfig);

      value = waitForTrue.get();

      // Check if they are equal
      // (running them through another function because we can't use an operator with T)
      if (paramsEqual(value, comparativeValue)) {
        return value;
      }
    }

    // return the value regardless
    return value;
  }

  /**
   * Waits for a function with a return type T to return a value that is to an argument
   * of the same type.
   * If it times out it returns the value of the function.
   *
   * @param waitForTrue      Function that returns type T
   * @param retryTime        time to wait between retries
   * @param timeout          how long before timing out
   * @param comparativeValue value of the same type as T
   * @return if it returned before the timeout occurred
   */
  public static <T> T waitUntilMatch(Supplier<T> waitForTrue, long retryTime, long timeout,
      T comparativeValue) throws InterruptedException {
    // Set start time and exception holder
    LocalDateTime start = LocalDateTime.now();

    T value = waitForTrue.get();

    // Checks if the two values are equal
    boolean paramsAreEqual = paramsEqual(value, comparativeValue);

    // While the params are not equal & the timeout hasn't met, keep checking
    while (!paramsAreEqual && (ChronoUnit.MILLIS.between(start, LocalDateTime.now())) < timeout) {
      // If they aren't, wait
      Thread.sleep(retryTime);

      value = waitForTrue.get();

      // Check if they are equal
      // (running them through another function because we can't use an operator with T
      paramsAreEqual = paramsEqual(value, comparativeValue);
    }

    // return the value regardless
    return value;
  }

  /**
   * Waits for a Function with a type T to return a value that is equal to a
   * comparative value of type T.
   *
   * @param waitForTrue      Method to wait for
   * @param comparativeValue value of the same type as T
   */
  public static <T> void waitForMatch(Supplier<T> waitForTrue, T comparativeValue)
      throws InterruptedException, TimeoutException {
    // Set start time and exception holder
    LocalDateTime start = LocalDateTime.now();

    // Checks if the two values are equal
    boolean paramsAreEqual = paramsEqual(waitForTrue.get(), comparativeValue);

    // While the params are not equal & the timeout hasn't met, keep checking
    while (!paramsAreEqual
        && (ChronoUnit.MILLIS.between(start, LocalDateTime.now())) < timeoutFromConfig) {
      // If they aren't, wait
      Thread.sleep(retryTimeFromConfig);

      // Check if they are equal
      // (running them through another function because we can't use an operator with T
      paramsAreEqual = paramsEqual(waitForTrue.get(), comparativeValue);
    }

    if (!paramsAreEqual) {
      throw new TimeoutException(
          "Timed out waiting for the supplier to return expected value of " + comparativeValue);
    }
  }

  /**
   * Waits for a Function with a type T to return a value that is equal to a comparative value of type T.
   *
   * @param waitForTrue      Method to wait for
   * @param retryTime        time to wait between retries
   * @param timeout          how long before timing out
   * @param comparativeValue The value to compare to what comes out of waitForTrue
   */
  public static <T> void waitForMatch(Supplier<T> waitForTrue, long retryTime, long timeout,
      T comparativeValue) throws InterruptedException, TimeoutException {
    // Set start time and exception holder
    LocalDateTime start = LocalDateTime.now();

    // Checks if the two values are equal
    boolean paramsAreEqual = paramsEqual(waitForTrue.get(), comparativeValue);

    // While the params are not equal & the timeout hasn't met, keep checking
    while (!paramsAreEqual && (ChronoUnit.MILLIS.between(start, LocalDateTime.now())) < timeout) {
      // Check if they are equal (running them through another function because we can't use an operator with T
      paramsAreEqual = paramsEqual(waitForTrue.get(), comparativeValue);

      // If they aren't, wait
      Thread.sleep(retryTime);
    }

    if (!paramsAreEqual) {
      throw new TimeoutException(
          "Timed out waiting for the supplier to return the expected value of " + comparativeValue);
    }
  }

  /**
   * Wait until the wait for function returns the expected type, an exception will be thrown if the wait times out.
   *
   * @param waitFor The wait for function
   * @return The wait for function return value
   */
  public static <T> T waitFor(Supplier<T> waitFor) throws InterruptedException, TimeoutException {
    return wait(waitFor, retryTimeFromConfig, timeoutFromConfig);
  }

  /**
   * Wait until the wait for function returns the expected type, an exception will be thrown if the wait times out.
   *
   * @param waitFor The wait for function
   * @param arg     The wait for function argument
   * @return The wait for function return value
   */
  public static <T, U> T waitFor(Function<U, T> waitFor, U arg)
      throws InterruptedException, TimeoutException {
    return wait(waitFor, retryTimeFromConfig, timeoutFromConfig, arg);
  }

  /**
   * Wait until the wait for true function returns true or times out.
   *
   * @param waitForTrue    The function we are waiting to return true
   * @param retryTime      How long do we wait before retrying the wait for true function
   * @param timeout        Max timeout for the check
   * @param throwException If the last check failed because of an exception should we throw the exception
   * @param arg            Parameter to pass to the wait for true function
   * @return True if the wait for true function returned true before timing out
   */
  public static <T> boolean wait(Predicate<T> waitForTrue, long retryTime, long timeout,
      boolean throwException, T arg) throws InterruptedException, FunctionException {
    // Set start time and exception holder
    LocalDateTime start = LocalDateTime.now();
    FunctionException exception = null;

    do {
      try {
        // Clear out old exception
        exception = null;

        // Check if the function returns true
        if (waitForTrue.test(arg)) {
          return true;
        }
      } catch (Exception e) {
        // Save of the exception if we want to throw exceptions
        if (throwException) {
          exception = new FunctionException("Predicate exception caught.", e);
        }
      }

      // Give the system a second before checking if the page is updating
      Thread.sleep(retryTime);
    } while ((ChronoUnit.MILLIS.between(start, LocalDateTime.now())) < timeout);

    // Check if we had an exceptions
    if (throwException && exception != null) {
      throw exception;
    }

    // We timed out waiting for the function to return true
    return false;
  }

  /**
   * Wait until the wait for true function returns true or times out.
   *
   * @param waitForTrue    The function we are waiting to return true
   * @param retryTime      How long do we wait before retrying the wait for true function
   * @param timeout        Max timeout for the check
   * @param throwException If the last check failed because of an exception should we throw the exception
   * @return True if the wait for true function returned true before timing out
   */
  public static boolean wait(BooleanSupplier waitForTrue, long retryTime, long timeout,
      boolean throwException) throws InterruptedException, FunctionException {
    // Set start time and exception holder
    LocalDateTime start = LocalDateTime.now();
    FunctionException exception = null;

    do {
      try {
        // Clear out old exception
        exception = null;

        // Check if the function returns true
        if (waitForTrue.getAsBoolean()) {
          return true;
        }
      } catch (Exception e) {
        // Save of the exception if we want to throw exceptions
        if (throwException) {
          exception = new FunctionException("BooleanSupplier exception caught.", e);
        }
      }

      // Give the system a second before checking if the page is updating
      Thread.sleep(retryTime);
    } while (ChronoUnit.MILLIS.between(start, LocalDateTime.now()) < timeout);

    // Check if we had an exceptions
    if (throwException && exception != null) {
      throw exception;
    }

    // We timed out waiting for the function to return true
    return false;
  }

  /**
   * Wait until the wait for function returns the expected type, an exception will be thrown if the wait times out.
   *
   * @param waitFor   The wait for function
   * @param retryTime How long do we wait before retrying the wait for true function
   * @param timeout   Max timeout for the check
   * @return Return value of the wait for function
   */
  public static <T> T wait(Supplier<T> waitFor, long retryTime, long timeout)
      throws InterruptedException, TimeoutException {
    // Set start time and exception holder
    LocalDateTime start = LocalDateTime.now();
    Exception exception = new Exception();

    do {
      try {
        T value = waitFor.get();

        if (value != null) {
          return value;
        }

      } catch (Exception e) {
        exception = e;
      }

      // Give the system a second before checking if the page is updating
      Thread.sleep(retryTime);
    } while ((ChronoUnit.MILLIS.between(start, LocalDateTime.now())) < timeout);

    throw new TimeoutException("Timed out waiting for the supplier to return", exception);
  }

  /**
   * Wait until the wait for function returns the expected type, an exception will be thrown if the wait times out.
   *
   * @param waitFor   The wait for function
   * @param retryTime How long do we wait before retrying the wait for true function
   * @param timeout   Max timeout for the check
   * @param arg       MArguments to pass into the wait for function
   * @return Return value of the wait for function
   */
  public static <T, U> T wait(Function<U, T> waitFor, long retryTime, long timeout, U arg)
      throws InterruptedException, TimeoutException {
    // Set start time and exception holder
    LocalDateTime start = LocalDateTime.now();
    Exception exception;

    do {
      try {
        return waitFor.apply(arg);
      } catch (Exception e) {
        exception = e;
      }

      // Give the system a second before checking if the page is updating
      Thread.sleep(retryTime);
    } while ((ChronoUnit.MILLIS.between(start, LocalDateTime.now())) < timeout);

    throw new TimeoutException("Timed out waiting for the function to return", exception);
  }

  /**
   * Checks that the objects all match.
   *
   * @param param objects passed in
   * @return parameters are all equal as a boolean
   */
  private static boolean paramsEqual(Object... param) {
    // For each item
    for (Object item : param) {
      // and each item
      for (Object item2 : param) {
        // Compare each item
        if (!item.equals(item2)) {
          // If any do not match, then they are not equal
          return false;
        }
      }
    }

    // If we get here, then we had no mismatches
    return true;
  }
}
