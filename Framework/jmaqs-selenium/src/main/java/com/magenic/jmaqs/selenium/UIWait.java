/*
 * Copyright 2020 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.selenium;

import com.magenic.jmaqs.selenium.factories.FluentWaitFactory;
import com.magenic.jmaqs.utilities.helper.Config;
import com.magenic.jmaqs.utilities.helper.ConfigSection;
import java.text.MessageFormat;
import java.util.List;
import java.util.function.Function;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Coordinates;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * The type Ui wait.
 */
public class UIWait {

  private static final int HEADER_SIZE = 90;

  private static final By BODY_BY = By.cssSelector("BODY");

  private WebDriver driver;

  private int fluentRetryTime;

  private int timeout;

  private WebDriverWait waitDriver;

  /**
   * Instantiates a new Ui wait.
   *
   * @param driver the driver
   */
  public UIWait(WebDriver driver) {
    this(driver, Integer
            .parseInt(Config.getValueForSection(ConfigSection.SeleniumMaqs, "BrowserTimeout", "30000")),
        Integer.parseInt(
            Config.getValueForSection(ConfigSection.SeleniumMaqs, "BrowserWaitTime", "1000")));
  }

  /**
   * Instantiates a new Ui wait.
   *
   * @param driver           the driver
   * @param timeOutInSeconds the time out in seconds
   * @param fluentRetryTime  the fluent retry time
   */
  public UIWait(WebDriver driver, final int timeOutInSeconds, final int fluentRetryTime) {
    this.driver = driver;
    this.timeout = timeOutInSeconds;
    this.fluentRetryTime = fluentRetryTime;
    this.setWaitDriver(this.getNewWaitDriver());
  }

  /**
   * Gets wait driver.
   *
   * @return the wait driver
   */
  public WebDriverWait getWaitDriver() {
    return this.waitDriver;
  }

  /**
   * Sets wait driver.
   *
   * @param waiter the waiter
   */
  public void setWaitDriver(WebDriverWait waiter) {
    this.waitDriver = waiter;
  }

  /**
   * Reset wait driver web driver wait.
   *
   * @return the web driver wait
   */
  public WebDriverWait resetWaitDriver() {
    WebDriverWait wait = this.getNewWaitDriver();
    this.setWaitDriver(wait);
    return wait;
  }

  /**
   * Wait for present element web element.
   *
   * @param by the by
   * @return the web element
   */
  public WebElement waitForPresentElement(By by) {
    return this.waitForPresentElement(by, this.getWaitDriver());
  }

  /**
   * Wait for present element web element.
   *
   * @param by              the by
   * @param timeOutInMillis the time out in millis
   * @param sleepInMillis   the sleep in millis
   * @return the web element
   */
  public WebElement waitForPresentElement(By by, final int timeOutInMillis,
      final int sleepInMillis) {
    WebDriverWait wait = this.getNewWaitDriver(timeOutInMillis, sleepInMillis);
    return wait.until(ExpectedConditions.presenceOfElementLocated(by));
  }

  /**
   * Wait for present element web element.
   *
   * @param by   the by
   * @param wait the wait
   * @return the web element
   */
  public WebElement waitForPresentElement(By by, WebDriverWait wait) {
    return wait.until(ExpectedConditions.presenceOfElementLocated(by));
  }

  /**
   * Wait for visible element web element.
   *
   * @param by the by
   * @return the web element
   */
  public WebElement waitForVisibleElement(final By by) {
    return this.waitForVisibleElement(by, getWaitDriver());
  }

  /**
   * Wait for visible element web element.
   *
   * @param by              the by
   * @param timeOutInMillis the time out in millis
   * @param sleepInMillis   the sleep in millis
   * @return the web element
   */
  public WebElement waitForVisibleElement(final By by, final int timeOutInMillis,
      final int sleepInMillis) {
    WebDriverWait wait = this.getNewWaitDriver(timeOutInMillis, sleepInMillis);
    return this.waitForVisibleElement(by, wait);
  }

  /**
   * Wait for visible element web element.
   *
   * @param by   the by
   * @param wait the wait
   * @return the web element
   */
  public WebElement waitForVisibleElement(final By by, WebDriverWait wait) {
    try {
      return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    } catch (TimeoutException | NoSuchElementException e) {
      return null;
    }
  }

  /**
   * Wait until visible element boolean.
   *
   * @param by the by
   * @return the boolean
   */
  public boolean waitUntilVisibleElement(final By by) {
    return waitUntilVisibleElement(by, this.getWaitDriver());
  }

  /**
   * Wait until visible element boolean.
   *
   * @param by              the by
   * @param timeOutInMillis the time out in millis
   * @param sleepInMillis   the sleep in millis
   * @return the boolean
   */
  public boolean waitUntilVisibleElement(final By by, final int timeOutInMillis,
      final int sleepInMillis) {
    WebDriverWait wait = this.getNewWaitDriver(this.driver, timeOutInMillis, sleepInMillis);
    return waitUntilVisibleElement(by, wait);
  }

  /**
   * Wait until visible element boolean.
   *
   * @param by   the by
   * @param wait the wait
   * @return the boolean
   */
  public boolean waitUntilVisibleElement(final By by, WebDriverWait wait) {
    try {
      return waitForVisibleElement(by, wait) != null;
    } catch (NoSuchElementException | StaleElementReferenceException | TimeoutException e) {
      return false;
    }
  }

  /**
   * Wait for enabled element web element.
   *
   * @param by the by
   * @return the web element
   */
  public WebElement waitForEnabledElement(final By by) {
    return waitForEnabledElement(by, this.timeout, this.fluentRetryTime);
  }

  /**
   * Wait for enabled element web element.
   *
   * @param by              the by
   * @param timeOutInMillis the time out in millis
   * @param sleepInMillis   the sleep in millis
   * @return the web element
   */
  public WebElement waitForEnabledElement(final By by, final int timeOutInMillis,
      final int sleepInMillis) {
    if (waitUntilEnabledElement(by, timeOutInMillis, sleepInMillis)) {
      return this.driver.findElement(by);
    }

    throw new ElementNotInteractableException("Element is not enabled");
  }

  /**
   * Wait until enabled element boolean.
   *
   * @param by the by
   * @return the boolean
   */
  public boolean waitUntilEnabledElement(final By by) {
    return waitUntilEnabledElement(by, this.timeout, this.fluentRetryTime);
  }

  /**
   * Wait until enabled element boolean.
   *
   * @param by              the by
   * @param timeOutInMillis the time out in millis
   * @param sleepInMillis   the sleep in millis
   * @return the boolean
   */
  public boolean waitUntilEnabledElement(final By by, final int timeOutInMillis,
      final int sleepInMillis) {

    WebElement element = this.waitForVisibleElement(by);
    FluentWait<WebElement> fluentWait = FluentWaitFactory
        .getNewElementFluentWait(element, timeOutInMillis, sleepInMillis);
    Function<WebElement, Boolean> function = obj -> {
      try {
        return obj.isEnabled();
      } catch (NoSuchElementException | StaleElementReferenceException e) {
        // Do not throw these exceptions here.
        // Instead return false and let the fluent wait try again.
        return false;
      }
    };

    try {
      return fluentWait.until(function);
    } catch (TimeoutException e) {
      return false;
    }
  }

  /**
   * Wait until disabled element boolean.
   *
   * @param by the by
   * @return the boolean
   */
  public boolean waitUntilDisabledElement(By by) {
    return waitUntilDisabledElement(by, this.timeout, this.fluentRetryTime);
  }

  /**
   * Wait until disabled element boolean.
   *
   * @param by              the by
   * @param timeOutInMillis the time out in millis
   * @param sleepInMillis   the sleep in millis
   * @return the boolean
   */
  public boolean waitUntilDisabledElement(By by, final int timeOutInMillis,
      final int sleepInMillis) {
    try {
      WebElement element = this.waitForVisibleElement(by, timeOutInMillis, sleepInMillis);
      FluentWait<WebElement> fluentWait = FluentWaitFactory
          .getNewElementFluentWait(element, timeOutInMillis, sleepInMillis);
      return fluentWait.until(obj -> !obj.isEnabled());
    } catch (NoSuchElementException | StaleElementReferenceException e) {
      return true;
    } catch (TimeoutException e) {
      return false;
    }
  }

  /**
   * Wait for absent element.
   *
   * @param by the by
   */
  public void waitForAbsentElement(final By by) {
    this.waitForAbsentElement(by, this.timeout, this.fluentRetryTime);
  }

  /**
   * Wait for absent element.
   *
   * @param by              the by
   * @param timeOutInMillis the time out in millis
   * @param sleepInMillis   the sleep in millis
   */
  public void waitForAbsentElement(final By by, final int timeOutInMillis,
      final int sleepInMillis) {
    boolean isAbsent = this.waitUntilAbsentElement(by, timeOutInMillis, sleepInMillis);

    if (!isAbsent) {
      throw new TimeoutException(
          MessageFormat.format("Element with selector {0} is not absent", by));
    }
  }

  /**
   * Wait until absent element boolean.
   *
   * @param by the by
   * @return the boolean
   */
  public boolean waitUntilAbsentElement(final By by) {
    return this.waitUntilAbsentElement(by, this.timeout, this.fluentRetryTime);
  }

  /**
   * Wait until absent element boolean.
   *
   * @param by              the by
   * @param timeOutInMillis the time out in millis
   * @param sleepInMillis   the sleep in millis
   * @return the boolean
   */
  public boolean waitUntilAbsentElement(By by, final int timeOutInMillis, final int sleepInMillis) {

    try {
      WebElement element = this.driver.findElement(by);
      FluentWait<WebElement> fluentWait = FluentWaitFactory
          .getNewElementFluentWait(element, timeOutInMillis, sleepInMillis);
      Function<WebElement, Boolean> function = obj -> {
        try {
          return !obj.isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
          // Do not throw these exceptions here. Instead return true as the element has disappeared.
          return true;
        }
      };
      return fluentWait.until(function);
    } catch (NoSuchElementException | StaleElementReferenceException e) {
      return true;
    } catch (TimeoutException e) {
      return false;
    }
  }

  /**
   * Wait for elements list.
   *
   * @param by the by
   * @return the list
   */
  public List<WebElement> waitForElements(final By by) {
    return this.waitForElements(by, getWaitDriver());
  }

  /**
   * Wait for elements list.
   *
   * @param by              the by
   * @param timeOutInMillis the time out in millis
   * @param sleepInMillis   the sleep in millis
   * @return the list
   */
  public List<WebElement> waitForElements(final By by, final int timeOutInMillis,
      final int sleepInMillis) {
    WebDriverWait wait = this.getNewWaitDriver(timeOutInMillis, sleepInMillis);
    return waitForElements(by, wait);
  }

  /**
   * Wait for elements list.
   *
   * @param by   the by
   * @param wait the wait
   * @return the list
   */
  public List<WebElement> waitForElements(final By by, WebDriverWait wait) {
    return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
  }

  /**
   * Wait for exact text web element.
   *
   * @param by   the by
   * @param text the text
   * @return the web element
   */
  public WebElement waitForExactText(final By by, final String text) {
    waitUntilExactText(by, text);
    return this.driver.findElement(by);
  }

  /**
   * Wait until exact text boolean.
   *
   * @param by   the by
   * @param text the text
   * @return the boolean
   */
  public boolean waitUntilExactText(final By by, final String text) {
    return this.waitUntilExactText(by, text, getWaitDriver());
  }

  /**
   * Wait until exact text boolean.
   *
   * @param by              the by
   * @param text            the text
   * @param timeOutInMillis the time out in millis
   * @param sleepInMillis   the sleep in millis
   * @return the boolean
   */
  public boolean waitUntilExactText(final By by, final String text, final int timeOutInMillis,
      final int sleepInMillis) {
    WebDriverWait wait = this.getNewWaitDriver(timeOutInMillis, sleepInMillis);
    return waitUntilExactText(by, text, wait);
  }

  /**
   * Wait until exact text boolean.
   *
   * @param by   the by
   * @param text the text
   * @param wait the wait
   * @return the boolean
   */
  public boolean waitUntilExactText(final By by, final String text, WebDriverWait wait) {
    try {
      return wait.until(function -> doesTextMatch(by, text));
    } catch (NoSuchElementException | StaleElementReferenceException | TimeoutException e) {
      return false;
    }
  }

  /**
   * Wait for contains text web element.
   *
   * @param by   the by
   * @param text the text
   * @return the web element
   */
  public WebElement waitForContainsText(final By by, final String text) {
    if (waitUntilContainsText(by, text, getWaitDriver())) {
      return this.driver.findElement(by);
    }
    String error = String.format("Selector [%s] couldn't be found.%n", by.toString());
    throw new NotFoundException(error);
  }

  /**
   * Wait until contains text boolean.
   *
   * @param by   the by
   * @param text the text
   * @return the boolean
   */
  public boolean waitUntilContainsText(final By by, final String text) {
    return this.waitUntilContainsText(by, text, getWaitDriver());
  }

  /**
   * Wait until contains text boolean.
   *
   * @param by              the by
   * @param text            the text
   * @param timeOutInMillis the time out in millis
   * @param sleepInMillis   the sleep in millis
   * @return the boolean
   */
  public boolean waitUntilContainsText(final By by, final String text, final int timeOutInMillis,
      final int sleepInMillis) {
    WebDriverWait wait = this.getNewWaitDriver(timeOutInMillis, sleepInMillis);
    return waitUntilContainsText(by, text, wait);
  }

  /**
   * Wait until contains text boolean.
   *
   * @param by   the by
   * @param text the text
   * @param wait the wait
   * @return the boolean
   */
  public boolean waitUntilContainsText(final By by, final String text, WebDriverWait wait) {
    try {
      return wait.until(function -> doesContainsText(by, text));
    } catch (NoSuchElementException | StaleElementReferenceException | TimeoutException e) {
      return false;
    }
  }

  /**
   * Wait until attribute text equals boolean.
   *
   * @param by        the by
   * @param attribute the attribute
   * @param text      the text
   * @return the boolean
   */
  public boolean waitUntilAttributeTextEquals(final By by, final String attribute,
      final String text) {
    return this.waitUntilAttribute(by, attribute, text, getWaitDriver(), false);
  }

  /**
   * Wait until attribute text equals boolean.
   *
   * @param by              the by
   * @param attribute       the attribute
   * @param text            the text
   * @param timeOutInMillis the time out in millis
   * @param sleepInMillis   the sleep in millis
   * @return the boolean
   */
  public boolean waitUntilAttributeTextEquals(final By by, final String attribute,
      final String text, final int timeOutInMillis, final int sleepInMillis) {
    WebDriverWait wait = this.getNewWaitDriver(timeOutInMillis, sleepInMillis);
    return waitUntilAttribute(by, attribute, text, wait, false);
  }

  /**
   * Wait for attribute text equals web element.
   *
   * @param by        the by
   * @param attribute the attribute
   * @param text      the text
   * @return the web element
   */
  public WebElement waitForAttributeTextEquals(final By by, final String attribute,
      final String text) {
    return this.waitForAttributeTextEquals(by, attribute, text, getWaitDriver());
  }

  /**
   * Wait for attribute text equals web element.
   *
   * @param by              the by
   * @param attribute       the attribute
   * @param text            the text
   * @param timeOutInMillis the time out in millis
   * @param sleepInMillis   the sleep in millis
   * @return the web element
   */
  public WebElement waitForAttributeTextEquals(final By by, final String attribute,
      final String text, final int timeOutInMillis, final int sleepInMillis) {
    WebDriverWait wait = this.getNewWaitDriver(timeOutInMillis, sleepInMillis);
    return waitForAttributeTextEquals(by, attribute, text, wait);
  }

  /**
   * Wait for attribute text equals web element.
   *
   * @param by        the by
   * @param attribute the attribute
   * @param text      the text
   * @param wait      the wait
   * @return the web element
   */
  public WebElement waitForAttributeTextEquals(final By by, final String attribute,
      final String text, WebDriverWait wait) {
    if (this.waitUntilAttribute(by, attribute, text, wait, false)) {
      return this.driver.findElement(by);
    }

    String error = String.format("Selector [%s] couldn't be found.%n", by.toString());
    throw new NotFoundException(error);
  }

  /**
   * Wait until attribute text contains boolean.
   *
   * @param by        the by
   * @param attribute the attribute
   * @param text      the text
   * @return the boolean
   */
  public boolean waitUntilAttributeTextContains(final By by, final String attribute,
      final String text) {
    return waitUntilAttribute(by, attribute, text, getWaitDriver(), true);
  }

  /**
   * Wait until attribute text contains boolean.
   *
   * @param by              the by
   * @param attribute       the attribute
   * @param text            the text
   * @param timeOutInMillis the time out in millis
   * @param sleepInMillis   the sleep in millis
   * @return the boolean
   */
  public boolean waitUntilAttributeTextContains(final By by, final String attribute,
      final String text, final int timeOutInMillis, final int sleepInMillis) {
    WebDriverWait wait = this.getNewWaitDriver(timeOutInMillis, sleepInMillis);
    return waitUntilAttribute(by, attribute, text, wait, true);
  }

  /**
   * Wait until attribute boolean.
   *
   * @param by        the by
   * @param attribute the attribute
   * @param text      the text
   * @param wait      the wait
   * @param contains  the contains
   * @return the boolean
   */
  public boolean waitUntilAttribute(final By by, final String attribute, final String text,
      WebDriverWait wait, final boolean contains) {
    try {
      return wait.until(f -> attributeMatches(f, by, attribute, text, contains));
    } catch (NoSuchElementException | StaleElementReferenceException | TimeoutException e) {
      return false;
    }
  }

  /**
   * Wait for clickable element and scroll into view web element.
   *
   * @param by the by
   * @return the web element
   */
  public WebElement waitForClickableElementAndScrollIntoView(final By by) {
    return this.waitForClickableElementAndScrollIntoView(by, getWaitDriver());
  }

  /**
   * Wait for clickable element and scroll into view web element.
   *
   * @param by              the by
   * @param timeOutInMillis the time out in millis
   * @param sleepInMillis   the sleep in millis
   * @return the web element
   */
  public WebElement waitForClickableElementAndScrollIntoView(final By by, final int timeOutInMillis,
      final int sleepInMillis) {
    WebDriverWait wait = this.getNewWaitDriver(timeOutInMillis, sleepInMillis);
    return waitForClickableElementAndScrollIntoView(by, wait);
  }

  /**
   * Wait for clickable element and scroll into view web element.
   *
   * @param by   the by
   * @param wait the wait
   * @return the web element
   */
  public WebElement waitForClickableElementAndScrollIntoView(final By by, WebDriverWait wait) {
    scrollIntoView(by);
    waitUntilPageLoad();
    return waitForClickableElement(by, wait);
  }

  /**
   * Wait until clickable element and scroll into view boolean.
   *
   * @param by the by
   * @return the boolean
   */
  public boolean waitUntilClickableElementAndScrollIntoView(final By by) {
    return this.waitUntilClickableElementAndScrollIntoView(by, getWaitDriver());
  }

  /**
   * Wait until clickable element and scroll into view boolean.
   *
   * @param by              the by
   * @param timeOutInMillis the time out in millis
   * @param sleepInMillis   the sleep in millis
   * @return the boolean
   */
  public boolean waitUntilClickableElementAndScrollIntoView(final By by, final int timeOutInMillis,
      final int sleepInMillis) {
    WebDriverWait wait = this.getNewWaitDriver(timeOutInMillis, sleepInMillis);
    return waitUntilClickableElementAndScrollIntoView(by, wait);
  }

  /**
   * Wait until clickable element and scroll into view boolean.
   *
   * @param by   the by
   * @param wait the wait
   * @return the boolean
   */
  public boolean waitUntilClickableElementAndScrollIntoView(final By by, WebDriverWait wait) {
    scrollIntoView(by);
    waitUntilPageLoad();
    return waitUntilClickableElement(by, wait);
  }

  /**
   * Wait until clickable element boolean.
   *
   * @param by the by
   * @return the boolean
   */
  public boolean waitUntilClickableElement(final By by) {
    return this.waitUntilClickableElement(by, getWaitDriver());
  }

  /**
   * Wait until clickable element boolean.
   *
   * @param by              the by
   * @param timeOutInMillis the time out in millis
   * @param sleepInMillis   the sleep in millis
   * @return the boolean
   */
  public boolean waitUntilClickableElement(final By by, final int timeOutInMillis,
      final int sleepInMillis) {
    WebDriverWait wait = this.getNewWaitDriver(timeOutInMillis, sleepInMillis);
    return waitUntilClickableElement(by, wait);
  }

  /**
   * Wait until clickable element boolean.
   *
   * @param by   the by
   * @param wait the wait
   * @return the boolean
   */
  public boolean waitUntilClickableElement(final By by, WebDriverWait wait) {
    try {
      if (waitForClickableElement(by, wait) == null) {
        return false;
      }
    } catch (Exception e) {
      return false;
    }
    return true;
  }

  /**
   * Wait for clickable element web element.
   *
   * @param by the by
   * @return the web element
   */
  public WebElement waitForClickableElement(final By by) {
    return this.waitForClickableElement(by, getWaitDriver());
  }

  /**
   * Wait for clickable element web element.
   *
   * @param by              the by
   * @param timeOutInMillis the time out in millis
   * @param sleepInMillis   the sleep in millis
   * @return the web element
   */
  public WebElement waitForClickableElement(final By by, final int timeOutInMillis,
      final int sleepInMillis) {

    WebDriverWait wait = this.getNewWaitDriver(timeOutInMillis, sleepInMillis);
    return waitForClickableElement(by, wait);
  }

  /**
   * Wait for clickable element web element.
   *
   * @param by   the by
   * @param wait the wait
   * @return the web element
   */
  public WebElement waitForClickableElement(final By by, WebDriverWait wait) {
    return wait.until(ExpectedConditions.elementToBeClickable(by));
  }

  /**
   * Wait for page load.
   */
  public void waitForPageLoad() {
    if (!this.waitUntilPageLoad()) {
      throw new TimeoutException("Page load took longer than timeout configuration.");
    }
  }

  /**
   * Wait until page load boolean.
   *
   * @return the boolean
   */
  public boolean waitUntilPageLoad() {
    String before = "";
    String after = "";
    int counter = this.timeout / this.fluentRetryTime;

    do {
      try {
        counter--;
        before = this.driver.getPageSource();
        Thread.sleep(this.fluentRetryTime);
        after = this.driver.getPageSource();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    } while (!before.equals(after) && counter > 0);

    return before.equals(after);
  }

  /**
   * Wait until iframe to load boolean.
   *
   * @param by the by
   * @return the boolean
   */
  public boolean waitUntilIframeToLoad(By by) {
    return waitUntilIframeToLoad(by, this.getWaitDriver());
  }

  /**
   * Wait until iframe to load boolean.
   *
   * @param by              the by
   * @param timeOutInMillis the time out in millis
   * @param sleepInMillis   the sleep in millis
   * @return the boolean
   */
  public boolean waitUntilIframeToLoad(By by, final int timeOutInMillis, final int sleepInMillis) {
    WebDriverWait wait = this.getNewWaitDriver(timeOutInMillis, sleepInMillis);
    return waitUntilIframeToLoad(by, wait);
  }

  /**
   * Wait until iframe to load boolean.
   *
   * @param by   the by
   * @param wait the wait
   * @return the boolean
   */
  public boolean waitUntilIframeToLoad(By by, WebDriverWait wait) {

    try {
      waitForIframeToLoad(by, wait);
      return true;
    } catch (NoSuchElementException | StaleElementReferenceException | TimeoutException e) {
      return false;
    }
  }

  /**
   * Wait for iframe to load.
   *
   * @param by the by
   */
  public void waitForIframeToLoad(By by) {
    waitForIframeToLoad(by, this.getWaitDriver());
  }

  /**
   * Wait for iframe to load.
   *
   * @param by              the by
   * @param timeOutInMillis the time out in millis
   * @param sleepInMillis   the sleep in millis
   */
  public void waitForIframeToLoad(By by, final int timeOutInMillis, final int sleepInMillis) {
    WebDriverWait wait = this.getNewWaitDriver(timeOutInMillis, sleepInMillis);
    waitForIframeToLoad(by, wait);
  }

  /**
   * Wait for iframe to load.
   *
   * @param by   the by
   * @param wait the wait
   */
  public void waitForIframeToLoad(By by, WebDriverWait wait) {
    wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(by));
  }

  /**
   * Gets web driver.
   *
   * @return the web driver
   */
  protected WebDriver getWebDriver() {
    return driver;
  }

  /**
   * Gets new wait driver.
   *
   * @param driver the driver
   * @return the new wait driver
   */
  protected WebDriverWait getNewWaitDriver(WebDriver driver) {
    return getNewWaitDriver(driver, this.timeout, this.fluentRetryTime);
  }

  /**
   * Gets new wait driver.
   *
   * @return the new wait driver
   */
  protected WebDriverWait getNewWaitDriver() {
    return getNewWaitDriver(this.getWebDriver(), this.timeout, this.fluentRetryTime);
  }

  /**
   * Gets new wait driver.
   *
   * @param timeOutInMillis the time out in millis
   * @return the new wait driver
   */
  protected WebDriverWait getNewWaitDriver(int timeOutInMillis) {
    return getNewWaitDriver(this.getWebDriver(), timeOutInMillis, this.fluentRetryTime);
  }

  /**
   * Gets new wait driver.
   *
   * @param timeOutInMillis the time out in millis
   * @param sleepInMillis   the sleep in millis
   * @return the new wait driver
   */
  protected WebDriverWait getNewWaitDriver(int timeOutInMillis, int sleepInMillis) {
    return getNewWaitDriver(this.getWebDriver(), timeOutInMillis, sleepInMillis);
  }

  /**
   * Gets new wait driver.
   *
   * @param driver          the driver
   * @param timeOutInMillis the time out in millis
   * @param sleepInMillis   the sleep in millis
   * @return the new wait driver
   */
  protected WebDriverWait getNewWaitDriver(WebDriver driver, int timeOutInMillis,
      int sleepInMillis) {
    int timeoutInSeconds = timeOutInMillis / 1000;
    WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds, sleepInMillis);
    setWaitDriver(wait);
    return wait;
  }

  private void scrollIntoView(By by) {
    WebElement element = waitForVisibleElement(by);
    int counter = 0;
    final int max = 5;

    try {
      ((JavascriptExecutor) this.driver)
          .executeScript("arguments[0].scrollIntoView(true);", element);
    } catch (Exception e) {
      String error = String.format("Failed JavaScript scroll into view...%s%n", e.getStackTrace());
      System.err.print(error);
    }

    Coordinates coordinates = ((Locatable) element).getCoordinates();
    while (coordinates.inViewPort().getY() < HEADER_SIZE && counter < max) {
      waitForVisibleElement(BODY_BY).sendKeys(Keys.ARROW_UP);
      counter++;
    }
  }

  private static boolean attributeMatches(WebDriver driver, By by, String attribute, String text,
      boolean contains) {
    try {
      if (contains) {
        return driver.findElement(by).getAttribute(attribute).contains(text);
      } else {
        return driver.findElement(by).getAttribute(attribute).equals(text);
      }
    } catch (NullPointerException e) {
      return false;
    }
  }

  // TODO: should have fuzzy validation incorporated.
  private boolean doesTextMatch(By by, String text) {
    try {
      WebElement element = this.waitForVisibleElement(by);

      // TODO: we probably should include an option to use fuzzy validation here, so it isn't a
      // hard-compare.
      if (element != null && element.getText().equals(text)) {
        return true;
      }
    } catch (Exception e) {
      return false;
    }
    return false;
  }

  private boolean doesContainsText(By by, String text) {
    try {
      WebElement element = this.waitForVisibleElement(by);

      if (element != null && element.getText().contains(text)) {
        return true;
      }
    } catch (Exception e) {
      return false;
    }
    return false;
  }
}