/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.selenium.factories;

import com.magenic.jmaqs.selenium.SeleniumConfig;
import java.time.Duration;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;

/**
 * Handles the creation of {@link org.openqa.selenium.support.ui.FluentWait FluentWait} objects.
 */
public class FluentWaitFactory {

  // private constructor so class can't be instantiated
  private FluentWaitFactory() {
  }

  /**
   * Returns a new {@link org.openqa.selenium.support.ui.FluentWait FluentWait} object.
   *
   * @param element         the element
   * @param timeOutInMillis the default milliseconds timeout
   * @param sleepInMillis   the polling milliseconds before retry
   * @return new fluent wait
   */
  public static FluentWait<WebElement> getNewElementFluentWait(WebElement element, int timeOutInMillis,
      int sleepInMillis) {
    return new FluentWait<>(element).withTimeout(Duration.ofMillis(timeOutInMillis))
        .pollingEvery(Duration.ofMillis(sleepInMillis)).ignoring(NotFoundException.class);
  }

  /**
   * Returns a new {@link org.openqa.selenium.support.ui.FluentWait FluentWait} object.
   *
   * @param element the element
   * @return new fluent wait
   */
  public static FluentWait<WebElement> getNewElementFluentWait(WebElement element) {
    return getNewElementFluentWait(element, (int) SeleniumConfig.getTimeoutTime().toMillis(),
        (int) SeleniumConfig.getWaitTime().toMillis());
  }
}
