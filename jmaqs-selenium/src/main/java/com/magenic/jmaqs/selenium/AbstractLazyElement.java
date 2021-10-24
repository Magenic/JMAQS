/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.selenium;

import com.magenic.jmaqs.selenium.exceptions.ElementHandlerException;
import com.magenic.jmaqs.selenium.factories.FluentWaitFactory;
import com.magenic.jmaqs.selenium.factories.UIWaitFactory;
import com.magenic.jmaqs.utilities.helper.GenericWait;
import com.magenic.jmaqs.utilities.helper.exceptions.ExecutionFailedException;
import com.magenic.jmaqs.utilities.helper.exceptions.TimeoutException;
import com.magenic.jmaqs.utilities.helper.functionalinterfaces.Action;
import com.magenic.jmaqs.utilities.logging.MessageType;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;

/**
 * Abstract structure for dynamically finding and interacting with elements.
 */
public abstract class AbstractLazyElement {

  /**
   * The index in cases where the selector finds multiple elements.
   */
  private final Integer elementIndex;

  /**
   * A user-friendly name, for logging purposes.
   */
  protected final String userFriendlyName;

  /**
   * The parent lazy element.
   */
  protected LazyWebElement parent;

  /**
   * The 'by' selector for the element.
   */
  protected By by;

  /**
   * The test object for the element.
   */
  protected SeleniumTestObject testObject;

  /**
   * Cached copy of the element or null if we haven't already found the element.
   */
  private WebElement cachedElement;

  /**
   * Initializes a new instance of the {@link #AbstractLazyElement} class.
   *
   * @param testObject       The selenium test object
   * @param locator          The by locator to search on
   * @param userFriendlyName The user-friendly name of the lazy element
   */
  protected AbstractLazyElement(SeleniumTestObject testObject, By locator, String userFriendlyName) {
    this.testObject = testObject;
    this.by = locator;
    this.userFriendlyName = userFriendlyName;
    this.elementIndex = null;
  }

  /**
   * Initializes a new instance of the {@link #AbstractLazyElement} class.
   *
   * @param parent           The parent lazy element
   * @param locator          The by locator to search on
   * @param userFriendlyName The user-friendly name of the lazy element
   */
  protected AbstractLazyElement(LazyWebElement parent, By locator, String userFriendlyName) {
    this.testObject = parent.getTestObject();
    this.parent = parent;
    this.by = locator;
    this.userFriendlyName = userFriendlyName;
    this.elementIndex = null;
  }

  /**
   * Initializes a new instance of the {@link #AbstractLazyElement} class.
   *
   * @param parent           The parent lazy element
   * @param locator          THe by locator to search on
   * @param userFriendlyName The user-friendly name of the lazy element
   * @param elementIndex     The index of the cached element
   * @param cachedElement    The cached element
   */
  protected AbstractLazyElement(LazyWebElement parent, By locator, String userFriendlyName, Integer elementIndex,
      WebElement cachedElement) {
    this.testObject = parent.getTestObject();
    this.by = locator;
    this.userFriendlyName = userFriendlyName;
    this.parent = parent;
    this.elementIndex = elementIndex;
    this.cachedElement = cachedElement;
  }

  /**
   * Gets the by selector.
   *
   * @return the by
   */
  public By getBy() {
    return this.by;
  }

  /**
   * Gets the cached element.
   *
   * @return the cachedElement
   */
  public WebElement getCachedElement() {
    return this.cachedElement;
  }

  /**
   * Sets the cached Element.
   *
   * @param cachedElementFactory the cachedElement function to set
   *                             the cached element
   */
  private void setCachedElement(Supplier<WebElement> cachedElementFactory) {
    this.cachedElement = cachedElementFactory.get();
  }

  /**
   * Gets the user-friendly name.
   *
   * @return the userFriendlyName
   */
  public String getUserFriendlyName() {
    return this.userFriendlyName;
  }

  /**
   * Gets the parent of the lazy element.
   *
   * @return the parent
   */
  public LazyWebElement getParent() {
    return this.parent;
  }

  /**
   * Gets the test object.
   *
   * @return the test object
   */
  public SeleniumTestObject getTestObject() {
    return this.testObject;
  }

  /**
   * Gets the tag name of the lazy element.
   *
   * @return The tag name
   * @throws TimeoutException     If a timeout occurred while waiting for the element to be found
   * @throws InterruptedException If the thread is interrupted while waiting for the element to be found
   */
  public String getTagName() throws InterruptedException {
    this.getTestObject().getLogger().logMessage(MessageType.INFORMATION,
        String.format("Getting the tag name for lazy element '%s'", this.userFriendlyName));
    return GenericWait.waitFor(this.getElement(this::getRawExistingElement)::getTagName);
  }

  /**
   * Gets the lazy element's text.
   *
   * @return The element text
   * @throws TimeoutException     If a timeout occurred while waiting for the element to be found
   * @throws InterruptedException If the thread is interrupted while waiting for the element to be found
   */
  public String getText() throws InterruptedException {
    this.getTestObject().getLogger().logMessage(MessageType.INFORMATION,
        String.format("Getting the text for lazy element '%s'", this.userFriendlyName));
    return GenericWait.waitFor(this.getElement(this::getRawExistingElement)::getText);
  }

  /**
   * Gets the lazy element's location.
   *
   * @return the location as a Point
   * @throws TimeoutException     If a timeout occurred while waiting for the element to be found
   * @throws InterruptedException If the thread is interrupted while waiting for the element to be found
   */
  public Point getLocation() throws InterruptedException {
    this.getTestObject().getLogger().logMessage(MessageType.INFORMATION,
        String.format("Getting the location for lazy element '%s'", this.userFriendlyName));
    return GenericWait.waitFor(this.getElement(this::getRawExistingElement)::getLocation);
  }

  /**
   * Gets the lazy element's size.
   *
   * @return The lazy element's size
   * @throws TimeoutException     If a timeout occurred while waiting for the element to be found
   * @throws InterruptedException If the thread is interrupted while waiting for the element to be found
   */
  public Dimension getSize() throws InterruptedException {
    this.getTestObject().getLogger().logMessage(
        MessageType.INFORMATION, String.format("Getting the size of lazy element '%s'", this.userFriendlyName));
    return GenericWait.waitFor(this.getElement(this::getRawExistingElement)::getSize);
  }

  /**
   * Click the lazy element.
   *
   * @throws TimeoutException         If a timeout occurred while waiting for the element to be found
   * @throws InterruptedException     If the thread is interrupted while waiting for the element to be found
   * @throws ExecutionFailedException If error occurs while sending keys
   */
  public void click() throws InterruptedException {
    this.getTestObject().getLogger().logMessage(
        MessageType.INFORMATION, String.format("Click '%s'", this.userFriendlyName));
    WebElement element = GenericWait.waitFor(() -> this.getElement(this::getRawClickableElement));
    this.executeEvent(element::click, "Click");
  }

  /**
   * Send Secret keys with no logging.
   *
   * @param keys the secret keys
   * @throws ExecutionFailedException If error occurs while sending keys
   * @throws TimeoutException         If a timeout occurred while waiting for the element to be found
   * @throws InterruptedException     If the thread is interrupted while waiting for the element to be found
   */
  public void sendSecretKeys(String keys) throws InterruptedException {
    this.getTestObject().getLogger().logMessage(
        MessageType.VERBOSE, "Send secret keys to '%s'", this.getUserFriendlyName());
    WebElement element = GenericWait.waitFor(() -> this.getElement(this::getRawVisibleElement));

    try {
      this.getTestObject().getLogger().suspendLogging();
      this.executeEvent(() -> element.sendKeys(keys), "SendKeys");
      this.getTestObject().getLogger().continueLogging();
    } catch (ExecutionFailedException e) {
      this.getTestObject().getLogger().continueLogging();
      this.getTestObject().getLogger().logMessage(
          MessageType.ERROR, "Exception during sending secret keys: " + e + System.lineSeparator());

      throw e;
    }
  }

  /**
   * Clear the lazy element.
   *
   * @throws InterruptedException If the thread is interrupted while waiting for the element to be found
   */
  public void clear() throws InterruptedException {
    this.getTestObject().getLogger().logMessage(
        MessageType.INFORMATION, String.format("Clear '%s'", this.userFriendlyName));
    WebElement element = GenericWait.waitFor(() -> this.getElement(this::getRawVisibleElement));
    this.executeEvent(element::clear, "Clear");
  }

  /**
   * Submit the lazy element.
   *
   * @throws InterruptedException If the thread is interrupted while waiting for the element to be found
   */
  public void submit() throws InterruptedException {
    this.getTestObject().getLogger().logMessage(
        MessageType.INFORMATION, String.format("Submit '%s'", this.userFriendlyName));
    WebElement element = GenericWait.waitFor(() -> this.getElement(this::getRawExistingElement));
    this.executeEvent(element::submit, "Submit");
  }

  /**
   * Gets the value for the given attribute.
   *
   * @param attributeName The name of the attribute
   * @return The attribute
   * @throws TimeoutException     If a timeout occurred while waiting for the element to be found
   * @throws InterruptedException If the thread is interrupted while waiting for the element to be found
   */
  public String getAttribute(String attributeName) throws InterruptedException {
    this.getTestObject().getLogger().logMessage(MessageType.INFORMATION,
        String.format("Getting attribute '%s' for lazy element '%s'", attributeName, this.userFriendlyName));
    return GenericWait.waitFor(() -> this.getElement(this::getRawExistingElement).getAttribute(attributeName));
  }

  /**
   * Gets the current value of an element - Useful for get input box text.
   *
   * @return The value attribute
   * @throws TimeoutException     If a timeout occurred while waiting for the element to be found
   * @throws InterruptedException If the thread is interrupted while waiting for the element to be found
   */
  public String getValue() throws InterruptedException {
    this.getTestObject().getLogger().logMessage(MessageType.INFORMATION,
        String.format("Getting value for lazy element '%s'", this.userFriendlyName));
    return GenericWait.waitFor(() -> this.getElement(this::getRawVisibleElement).getAttribute("value"));
  }

  /**
   * Gets the CSS value for the given attribute.
   *
   * @param propertyName The property name
   * @return the css value for the property
   * @throws TimeoutException     If a timeout occurred while waiting for the element to be found
   * @throws InterruptedException If the thread is interrupted while waiting for the element to be found
   */
  public String getCssValue(String propertyName) throws InterruptedException {
    this.getTestObject().getLogger().logMessage(MessageType.INFORMATION,
        String.format("Getting '%s' css value for lazy element '%s'", propertyName, this.userFriendlyName));
    return GenericWait.waitFor(() -> this.getElement(this::getRawExistingElement).getCssValue(propertyName));
  }

  /**
   * Wait for and get the visible web element.
   *
   * @return The visible web element
   */
  public WebElement getRawVisibleElement() {
    Supplier<WebElement> elementSupplier;

    if (this.elementIndex == null) {
      if (this.parent == null) {
        elementSupplier = () -> {
          UIWait waitDriver = UIWaitFactory.getWaitDriver(this.getTestObject().getWebDriver());
          return waitDriver.waitForVisibleElement(this.getBy());
        };
      } else {
        elementSupplier = () -> {
          WebElement parentElement = this.parent.getRawExistingElement();
          FluentWait<WebElement> fluentWait = FluentWaitFactory.getNewElementFluentWait(parentElement);
          return fluentWait.until(e -> e.findElement(this.getBy()));
        };
      }

      this.setCachedElement(elementSupplier);
    } else {
      elementSupplier = () -> this.getRawIndexed(WebElement::isDisplayed, "be visible");
    }

    this.setCachedElement(elementSupplier);
    return this.getCachedElement();
  }

  /**
   * Wait for and get the click-able web element.
   *
   * @return The click-able web element
   */
  public WebElement getRawClickableElement() {
    Supplier<WebElement> elementSupplier;
    if (this.elementIndex == null) {
      if (this.parent == null) {
        elementSupplier = () -> {
          UIWait waitDriver = UIWaitFactory.getWaitDriver(this.getTestObject().getWebDriver());
          return waitDriver.waitForClickableElement(this.getBy());
        };
      } else {
        elementSupplier = () -> {
          WebElement parentElement = this.getParent().getRawExistingElement();
          FluentWait<WebElement> fluentWait = FluentWaitFactory.getNewElementFluentWait(parentElement);
          return fluentWait.until(e -> e.findElement(this.getBy()));
        };
      }
    } else {
      elementSupplier = () -> this.getRawIndexed(e -> e.isDisplayed() && e.isEnabled(), "be visible");
    }

    this.setCachedElement(elementSupplier);
    return this.getCachedElement();
  }

  /**
   * Waits for and gets the existing web element.
   *
   * @return The existing web element
   */
  public WebElement getRawExistingElement() {
    Supplier<WebElement> elementSupplier;

    if (this.elementIndex == null) {
      if (this.parent == null) {
        elementSupplier = () -> {
          UIWait waitDriver = UIWaitFactory.getWaitDriver(this.getTestObject().getWebDriver());
          return waitDriver.waitForPresentElement(this.getBy());
        };
      } else {
        elementSupplier = () -> {
          WebElement parentElement = this.getParent().getRawExistingElement();
          FluentWait<WebElement> fluentWait = FluentWaitFactory.getNewElementFluentWait(parentElement);
          return fluentWait.until(e -> e.findElement(this.getBy()));
        };
      }
    } else {
      elementSupplier = () -> this.getRawIndexed(Objects::nonNull, "exist");
    }

    this.setCachedElement(elementSupplier);
    return this.getCachedElement();
  }

  /**
   * Finds the first {@link WebElement WebElement} using the given method.
   *
   * @param by The locating mechanism to use
   * @return the {@link WebElement WebElement} being found
   * @throws TimeoutException     If a timeout occurred while waiting for the element to be found
   * @throws InterruptedException If the thread is interrupted while waiting for the element to be found
   */
  public WebElement findRawElement(By by) throws InterruptedException {
    return GenericWait.waitFor(() -> this.getElement(this::getRawExistingElement).findElement(by));
  }

  /**
   * Finds all {@link WebElement WebElement} within
   * the current context using the given mechanism.
   *
   * @param by the locating mechanism to use
   * @return the List of {@link WebElement WebElement} being found
   * @throws TimeoutException     If a timeout occurred while waiting for the element to be found
   * @throws InterruptedException If the thread is interrupted while waiting for the element to be found
   */
  public List<WebElement> findRawElements(By by) throws InterruptedException {
    return GenericWait.waitFor(() -> this.getElement(this::getRawExistingElement).findElements(by));
  }

  /**
   * Sends the keys to the element.
   *
   * @param keysToSend The keys being sent to the element
   * @throws TimeoutException         If a timeout occurred while waiting for the element to be found
   * @throws InterruptedException     If the thread is interrupted while waiting for the element to be found
   * @throws ExecutionFailedException If the send keys action fails to execute
   */
  public void sendKeys(CharSequence... keysToSend) throws InterruptedException {
    StringBuilder keyBuilder = new StringBuilder(keysToSend.length);

    // building char sequence to String so that it can be logged
    for (CharSequence cs : keysToSend) {
      keyBuilder.append(cs);
    }

    this.getTestObject().getLogger().logMessage(
        MessageType.VERBOSE, "Send keys '%s' to '%s'", keyBuilder.toString(), this.getUserFriendlyName());

    WebElement element = GenericWait.waitFor(() -> this.getElement(this::getRawVisibleElement));
    this.executeEvent(() -> element.sendKeys(keysToSend), "SendKeys");
  }

  /**
   * If the Element is selected.
   *
   * @return If the element is selected
   * @throws TimeoutException     If a timeout occurred while waiting for the element to be found
   * @throws InterruptedException If the thread is interrupted while waiting for the element to be found
   */
  public boolean isSelected() throws InterruptedException {
    this.getTestObject().getLogger().logMessage(MessageType.INFORMATION,
        String.format("Check to see if the lazy element %s is selected", this.userFriendlyName));
    return GenericWait.waitFor(this.getElement(this::getRawExistingElement)::isSelected);
  }

  /**
   * If the element is enabled.
   *
   * @return If the element is enabled
   * @throws TimeoutException     If a timeout occurred while waiting for the element to be found
   * @throws InterruptedException If the thread is interrupted while waiting for the element to be found
   */
  public boolean isEnabled() throws InterruptedException {
    this.getTestObject().getLogger().logMessage(MessageType.INFORMATION,
        String.format("Check to see if the lazy element %s is enabled", this.userFriendlyName));
    return GenericWait.waitFor(this.getElement(this::getRawExistingElement)::isEnabled);
  }

  /**
   * If the element is displayed.
   *
   * @return If the element is displayed
   * @throws TimeoutException     If a timeout occurred while waiting for the element to be found
   * @throws InterruptedException If the thread is interrupted while waiting for the element to be found
   */
  public boolean isDisplayed() throws InterruptedException {
    this.getTestObject().getLogger().logMessage(MessageType.INFORMATION,
        String.format("Check to see if the lazy element %s is displayed", this.userFriendlyName));
    return GenericWait.waitFor(this.getElement(this::getRawExistingElement)::isDisplayed);
  }

  /**
   * Gets the rectangle value that highlights the lazy element location.
   *
   * @return The location and size of the element
   * @throws TimeoutException     If a timeout occurred while waiting for the element to be found
   * @throws InterruptedException If the thread is interrupted while waiting for the element to be found
   */
  public Rectangle getRect() throws InterruptedException {
    return new Rectangle(this.getLocation(), this.getSize());
  }

  /**
   * Gets the screenshot as the target type.
   *
   * @param target The target output type
   * @return The type to get the screenshot as
   * @throws TimeoutException     If a timeout occurred while waiting for the element to be found
   * @throws InterruptedException If the thread is interrupted while waiting for the element to be found
   */
  public <X> X getScreenshotAs(OutputType<X> target) throws InterruptedException {
    return GenericWait.waitFor(() -> this.getElement(this::getRawExistingElement).getScreenshotAs(target));
  }

  /**
   * Returns a string that represents the current object.
   *
   * @return the lazy element string
   */
  @Override
  public String toString() {
    String temp = "";

    // Check if LazyElement has a parent
    // If so, prefix parent's toString()
    if (this.parent != null) {
      temp += this.parent.toString();
    }

    return temp + this.by.toString() + this.userFriendlyName;
  }

  /**
   * Gets a web element using the provided factory if the cached element is
   * null or if the cached element is stale.
   *
   * @param getElement The function that gets the element
   * @return The web element
   * @throws NoSuchElementException If the element can not be found
   */
  protected WebElement getElement(Supplier<WebElement> getElement) {
    if (this.getCachedElement() != null) {
      try {
        this.getCachedElement().isDisplayed();
        return this.getCachedElement();
      } catch (Exception e) {
        this.getTestObject().getLogger()
            .logMessage(MessageType.VERBOSE, "Finding element again because: " + e.getMessage());
      }
    }

    try {
      this.getTestObject().getLogger()
          .logMessage(MessageType.VERBOSE, "Performing lazy driver find on: " + this.getBy());
      this.setCachedElement(getElement);
      return this.getCachedElement();
    } catch (TimeoutException | NoSuchElementException noSuchElementException) {
      String messageBuilder =
          "Failed to find: " + this.userFriendlyName + System.lineSeparator()
              + "Locator: " + this.getBy() + System.lineSeparator()
              + "Because: " + noSuchElementException.getMessage() + System.lineSeparator();
      throw new ElementHandlerException(messageBuilder, noSuchElementException);
    }
  }

  /**
   * Execute an element action.
   *
   * @param elementAction the element action
   * @param caller        Text to identify the caller function
   * @throws ExecutionFailedException If the elementAction fails to execute
   */
  private void executeEvent(Action elementAction, String caller) {
    try {
      this.getTestObject().getLogger().logMessage(MessageType.VERBOSE, "Performing lazy driver action: " + caller);
      elementAction.invoke();
    } catch (Exception e) {
      String messageBuilder =
          "Failed to " + caller + ": " + this.userFriendlyName + System.lineSeparator()
              + "Locator: " + this.getBy() + System.lineSeparator()
              + "Because: " + e.getMessage() + System.lineSeparator();
      throw new ExecutionFailedException(messageBuilder, e);
    }
  }

  /**
   * Gets the element at the indexed value.
   *
   * @param matchesState  The predicate to see that the element found at the index matches the state
   *                      we expect
   * @param expectedState The expected state to be logged if the matchesState function returns false
   * @return The WebElement at the indexed value
   * @throws TimeoutException     If a timeout occurred while waiting for the element to be found
   */
  private WebElement getRawIndexed(Predicate<WebElement> matchesState, String expectedState) {
    List<WebElement> elements = (this.parent == null)
        ? this.getTestObject().getWebDriver().findElements(this.by)
        : this.parent.getRawExistingElement().findElements(by);
    WebElement indexedElement = elements.get(this.elementIndex == null ? 0 : this.elementIndex);

    if (!matchesState.test(indexedElement)) {
      throw new InvalidElementStateException(String.format("Expected element to %s", expectedState));
    }

    return indexedElement;
  }

  /**
   * Checks if the element exists.
   *
   * @return If the element exists in the current page state
   * @throws TimeoutException     If a timeout occurred while waiting for the element to be found
   * @throws InterruptedException If the thread is interrupted while waiting for the element to be found
   */
  public boolean doesExist() throws InterruptedException {
    return GenericWait.waitFor(() -> {
      this.getElement(this::getRawExistingElement);
      return true;
    });
  }
}
