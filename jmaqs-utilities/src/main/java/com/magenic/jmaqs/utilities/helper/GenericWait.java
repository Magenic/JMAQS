/*
 * Copyright 2020 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.utilities.helper;

import com.magenic.jmaqs.utilities.helper.exceptions.ExecutionFailedException;
import com.magenic.jmaqs.utilities.helper.exceptions.FunctionException;
import com.magenic.jmaqs.utilities.helper.exceptions.TimeoutException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * The type Generic wait.
 */
public final class GenericWait {

  private GenericWait() {
    throw new IllegalStateException("Utility class");
  }

  private static long retryTimeFromConfig = Long.parseLong(Config.getGeneralValue("WaitTime", "0"));

  private static long timeoutFromConfig = Long.parseLong(Config.getGeneralValue("Timeout", "0"));

  /**
   * Wait until boolean.
   *
   * @param <T>         the type parameter
   * @param waitForTrue the wait for true
   * @param arg         the arg
   * @return the boolean
   * @throws InterruptedException the interrupted exception
   * @throws FunctionException    the function exception
   */
  public static <T> boolean waitUntil(Predicate<T> waitForTrue, T arg) throws InterruptedException {
    return wait(waitForTrue, retryTimeFromConfig, timeoutFromConfig, false, arg);
  }

  /**
   * Wait until boolean.
   *
   * @param waitForTrue the wait for true
   * @return the boolean
   * @throws InterruptedException the interrupted exception
   * @throws FunctionException    the function exception
   */
  public static boolean waitUntil(BooleanSupplier waitForTrue) throws InterruptedException {
    return wait(waitForTrue, retryTimeFromConfig, timeoutFromConfig, false);
  }

  /**
   * Wait for true.
   *
   * @param waitForTrue the wait for true
   * @throws InterruptedException the interrupted exception
   * @throws FunctionException    the function exception
   * @throws TimeoutException     the timeout exception
   */
  public static void waitForTrue(BooleanSupplier waitForTrue) throws InterruptedException {
    if (!wait(waitForTrue, retryTimeFromConfig, timeoutFromConfig, true)) {
      throw new TimeoutException("Timed out waiting for the function to return true");
    }
  }

  /**
   * Wait for true.
   *
   * @param <T>         the type parameter
   * @param waitForTrue the wait for true
   * @param arg         the arg
   * @throws InterruptedException the interrupted exception
   * @throws FunctionException    the function exception
   * @throws TimeoutException     the timeout exception
   */
  public static <T> void waitForTrue(Predicate<T> waitForTrue, T arg) throws InterruptedException {
    if (!wait(waitForTrue, retryTimeFromConfig, timeoutFromConfig, true, arg)) {
      throw new TimeoutException("Timed out waiting for the function to return true");
    }
  }

  /**
   * Wait until match t.
   *
   * @param <T>              the type parameter
   * @param waitForTrue      the wait for true
   * @param comparativeValue the comparative value
   * @return the t
   * @throws InterruptedException the interrupted exception
   */
  public static <T> T waitUntilMatch(Supplier<T> waitForTrue, T comparativeValue) throws InterruptedException {
    // Set start time and exception holder
    LocalDateTime start = LocalDateTime.now();

    T value = waitForTrue.get();

    // Checks if the two values are equal
    boolean paramsAreEqual = paramsEqual(value, comparativeValue);

    // While the params are not equal & the timeout hasn't met, keep checking
    while (!paramsAreEqual && (ChronoUnit.MILLIS.between(start, LocalDateTime.now())) < timeoutFromConfig) {
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
   * Wait until match t.
   *
   * @param <T>              the type parameter
   * @param waitForTrue      the wait for true
   * @param retryTime        the retry time
   * @param timeout          the timeout
   * @param comparativeValue the comparative value
   * @return the t
   * @throws InterruptedException the interrupted exception
   */
  public static <T> T waitUntilMatch(Supplier<T> waitForTrue, long retryTime, long timeout, T comparativeValue)
      throws InterruptedException {
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
   * Wait for match.
   *
   * @param <T>              the type parameter
   * @param waitForTrue      the wait for true
   * @param comparativeValue the comparative value
   * @throws InterruptedException the interrupted exception
   * @throws TimeoutException     the timeout exception
   */
  public static <T> void waitForMatch(Supplier<T> waitForTrue, T comparativeValue) throws InterruptedException {
    // Set start time and exception holder
    LocalDateTime start = LocalDateTime.now();

    // Checks if the two values are equal
    boolean paramsAreEqual = paramsEqual(waitForTrue.get(), comparativeValue);

    // While the params are not equal & the timeout hasn't met, keep checking
    while (!paramsAreEqual && (ChronoUnit.MILLIS.between(start, LocalDateTime.now())) < timeoutFromConfig) {
      // If they aren't, wait
      Thread.sleep(retryTimeFromConfig);

      // Check if they are equal
      // (running them through another function because we can't use an operator with T
      paramsAreEqual = paramsEqual(waitForTrue.get(), comparativeValue);
    }

    if (!paramsAreEqual) {
      throw new TimeoutException("Timed out waiting for the supplier to return expected value of " + comparativeValue);
    }
  }

  /**
   * Wait for match.
   *
   * @param <T>              the type parameter
   * @param waitForTrue      the wait for true
   * @param retryTime        the retry time
   * @param timeout          the timeout
   * @param comparativeValue the comparative value
   * @throws InterruptedException the interrupted exception
   * @throws TimeoutException     the timeout exception
   */
  public static <T> void waitForMatch(Supplier<T> waitForTrue, long retryTime, long timeout, T comparativeValue) {
    // Set start time and exception holder
    LocalDateTime start = LocalDateTime.now();
    Exception exception = null;
    // Checks if the two values are equal
    boolean paramsAreEqual = paramsEqual(waitForTrue.get(), comparativeValue);

    // While the params are not equal & the timeout hasn't met, keep checking
    while (!paramsAreEqual && (ChronoUnit.MILLIS.between(start, LocalDateTime.now())) < timeout) {
      // Check if they are equal (running them through another function because we can't use an operator with T
      paramsAreEqual = paramsEqual(waitForTrue.get(), comparativeValue);

      // If they aren't, wait
      try {
        Thread.sleep(retryTime);
      } catch (InterruptedException e) {
        exception = e;
      }

    }

    if (exception != null) {
      throw new ExecutionFailedException("Thread Sleep interrupted: ", exception);
    } else if (!paramsAreEqual) {
      throw new TimeoutException(
          "Timed out waiting for the supplier to return the expected value of " + comparativeValue);
    }
  }

  /**
   * Wait for t.
   *
   * @param <T>     the type parameter
   * @param waitFor the wait for
   * @return the t
   * @throws InterruptedException the interrupted exception
   * @throws TimeoutException     the timeout exception
   */
  public static <T> T waitFor(Supplier<T> waitFor) throws InterruptedException {
    return wait(waitFor, retryTimeFromConfig, timeoutFromConfig);
  }

  /**
   * Wait for t.
   *
   * @param <T>     the type parameter
   * @param <U>     the type parameter
   * @param waitFor the wait for
   * @param arg     the arg
   * @return the t
   * @throws InterruptedException the interrupted exception
   * @throws TimeoutException     the timeout exception
   */
  public static <T, U> T waitFor(Function<U, T> waitFor, U arg) throws InterruptedException {
    return wait(waitFor, retryTimeFromConfig, timeoutFromConfig, arg);
  }

  /**
   * Wait boolean.
   *
   * @param <T>            the type parameter
   * @param waitForTrue    the wait for true
   * @param retryTime      the retry time
   * @param timeout        the timeout
   * @param throwException the throw exception
   * @param arg            the arg
   * @return the boolean
   * @throws InterruptedException the interrupted exception
   * @throws FunctionException    the function exception
   */
  public static <T> boolean wait(Predicate<T> waitForTrue, long retryTime, long timeout, boolean throwException, T arg)
      throws InterruptedException {
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
   * Wait boolean.
   *
   * @param waitForTrue    the wait for true
   * @param retryTime      the retry time
   * @param timeout        the timeout
   * @param throwException the throw exception
   * @return the boolean
   * @throws InterruptedException the interrupted exception
   * @throws FunctionException    the function exception
   */
  public static boolean wait(BooleanSupplier waitForTrue, long retryTime, long timeout, boolean throwException)
      throws InterruptedException {
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
   * Wait t.
   *
   * @param <T>       the type parameter
   * @param waitFor   the wait for
   * @param retryTime the retry time
   * @param timeout   the timeout
   * @return the t
   * @throws InterruptedException the interrupted exception
   */
  public static <T> T wait(Supplier<T> waitFor, long retryTime, long timeout) throws InterruptedException {
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
   * Wait t.
   *
   * @param <T>       the type parameter
   * @param <U>       the type parameter
   * @param waitFor   the wait for
   * @param retryTime the retry time
   * @param timeout   the timeout
   * @param arg       the arg
   * @return the t
   * @throws InterruptedException the interrupted exception
   * @throws TimeoutException     the timeout exception
   */
  public static <T, U> T wait(Function<U, T> waitFor, long retryTime, long timeout, U arg) throws InterruptedException {
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
