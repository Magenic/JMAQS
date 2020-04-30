/*
 * Copyright 2019 (C) Magenic, All rights Reserved
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
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Selenium waiter This is the tool-class used for waiting. It can be used to wait for many
 * circumstances (element exist, not exist, be visible, to equal a desired value, etc..
 */
public class UIWait {

	/** The default value of the Header size... Your header's size may be different. */
	private static final int HEADER_SIZE = 90;

	/** The default value of the page's body. Your page might have a different Header value. */
	private static final By BODY_BY = By.cssSelector("BODY");

	/** The Webdriver that the test is currently running on. */
	private WebDriver driver;

	/** The retry time. */
	private int fluentRetryTime;

	/** The timeout time. */
	private int timeout;

	/** The web driver wait that the test is currently running on. */
	private WebDriverWait waitDriver;

	/**
	 * Constructor for {@link UIWait} object.
	 *
	 * @param driver WebDriver
	 */
	public UIWait(WebDriver driver) {
		this(driver,
				Integer.parseInt(Config.getValueForSection(ConfigSection.SeleniumMaqs, "BrowserTimeout", "30000")),
				Integer.parseInt(Config.getValueForSection(ConfigSection.SeleniumMaqs, "BrowserWaitTime", "1000")),
				null);
	}

	public UIWait(WebDriver driver, WebDriverWait waitDriver) {
		this (
				driver,
				Integer.parseInt(Config.getValueForSection(ConfigSection.SeleniumMaqs, "BrowserTimeout", "30000")),
				Integer.parseInt(Config.getValueForSection(ConfigSection.SeleniumMaqs, "BrowserWaitTime", "1000")),
				waitDriver);
	}

	/**
	 * Constructor for SeleniumWait object.
	 *
	 * @param driver           The WebDriver
	 * @param timeOutInSeconds int value of the total time to wait until timing out
	 * @param fluentRetryTime  int value of seconds to use for fluent retry
	 */
	public UIWait(WebDriver driver, final int timeOutInSeconds, final int fluentRetryTime, WebDriverWait webDriverWait) {
		this.driver = driver;
		this.timeout = timeOutInSeconds;
		this.fluentRetryTime = fluentRetryTime;
		this.setWaitDriver(
				webDriverWait == null ?
						this.getNewWaitDriver() :
						webDriverWait);
	}

	/**
	 * Get the WebDriverWait for use outside of this instance class.
	 *
	 * @return The WebDriverWait
	 */
	public WebDriverWait getWaitDriver() {
		return this.waitDriver;
	}

	/**
	 * Sets the WebDriverWait.
	 *
	 * @param waiter The WebDriverWait
	 */
	public void setWaitDriver(WebDriverWait waiter) {
		this.waitDriver = waiter;
	}

	/**
	 * Resets wait default wait driver.
	 *
	 * @return The WebDriverWait
	 */
	public WebDriverWait resetWaitDriver() {
		WebDriverWait wait = this.getNewWaitDriver();
		this.setWaitDriver(wait);
		return wait;
	}

	/**
	 * Waits until the element is present.
	 *
	 * @param by The by selector
	 * @return Returns the element if present
	 */
	public WebElement waitForPresentElement(By by) {
		return this.waitForPresentElement(by, this.getWaitDriver());
	}

	/**
	 * Waits until the element is present.
	 *
	 * @param by  The by selector
	 * @param timeOutInMillis The timeout in milliseconds
	 * @param sleepInMillis the number of milliseconds to wait before a recheck
	 * @return Returns the element if present
	 */
	public WebElement waitForPresentElement(
			By by, 
			final int timeOutInMillis, 
			final int sleepInMillis) {
		WebDriverWait wait = this.getNewWaitDriver(timeOutInMillis, sleepInMillis);
		return wait.until(ExpectedConditions.presenceOfElementLocated(by));
	}

	/**
	 * Waits For the element to be present.
	 *
	 * @param by  The by selector
	 * @param wait The wait driver
	 * @return Returns the element if present
	 */
	public WebElement waitForPresentElement(By by, WebDriverWait wait) {
		return wait.until(ExpectedConditions.presenceOfElementLocated(by));
	}

	/**
	 * Wait for the specified element to be visible on the pages DOM. The first element located with
	 * the specified By value is returned.
	 *
	 * @param by Selector to wait for, and return
	 * @return WebElement - first one found with by
	 */
	public WebElement waitForVisibleElement(final By by) {
		return this.waitForVisibleElement(by, getWaitDriver());
	}

	/**
	 * Wait for the specified element to be visible on the pages DOM. The first element located with
	 * the specified By value is returned.
	 *
	 * @param by Selector to wait for, and return
	 * @param timeOutInMillis the number of milliseconds to wait before failing
	 * @param sleepInMillis the number of milliseconds to wait before a recheck
	 * @return WebElement - first one found with by. Null
	 */
	public WebElement waitForVisibleElement(
			final By by,
			final int timeOutInMillis,
			final int sleepInMillis) {
		WebDriverWait wait = this.getNewWaitDriver(timeOutInMillis, sleepInMillis);
		return this.waitForVisibleElement(by, wait);
	}

	/**
	 * Wait for the specified element to be visible on the pages DOM. The first element located with
	 * the specified By value is returned.
	 *
	 * @param by Selector to wait for, and return
	 * @param wait The wait driver
	 * @return WebElement - first one found with by. Null
	 */
	public WebElement waitForVisibleElement(
			final By by,
			WebDriverWait wait) {
		try {
			return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		} 
		catch (TimeoutException | NoSuchElementException e) {
			return null;
		}
	}

	/**
	 * Wait until the element exists and is visible.
	 *
	 * @param by Selector to wait for
	 * @return boolean true if element is found
	 */
	public boolean waitUntilVisibleElement(final By by) {
		return waitUntilVisibleElement(by, this.getWaitDriver());
	}

	/**
	 * Wait until the element exists and is visible.
	 *
	 * @param by Selector to wait for
	 * @param timeOutInMillis the number of milliseconds to wait before failing
	 * @param sleepInMillis the number of milliseconds to wait before a recheck
	 * @return boolean true if element is found
	 */
	public boolean waitUntilVisibleElement(final By by,
			final int timeOutInMillis,
			final int sleepInMillis) {
		WebDriverWait wait = this.getNewWaitDriver(this.driver, timeOutInMillis, sleepInMillis);
		return waitUntilVisibleElement(by, wait);
	}

	/**
	 * Wait until the element exists and is visible.
	 *
	 * @param by Selector to wait for
	 * @param wait The wait driver
	 * @return boolean true if element is found
	 */
	public boolean waitUntilVisibleElement(final By by, WebDriverWait wait) {
		try {
			return waitForVisibleElement(by, wait) != null;
		} catch (NoSuchElementException | StaleElementReferenceException | TimeoutException e) {
			return false;
		}
	}

	/**
	 * Wait for the specified element to be present and enabled. The first element located with
	 * the specified By value is returned.
	 *
	 * @param by Selector to wait for, and return
	 * @return WebElement - first one found with by. Null
	 */
	public WebElement waitForEnabledElement(final By by) {
		return waitForEnabledElement(by, this.timeout, this.fluentRetryTime);
	}

	/**
	 * Wait for the specified element to be present and enabled. The first element located with
	 * the specified By value is returned.
	 *
	 * @param by Selector to wait for, and return
	 * @param timeOutInMillis       - the number of milliseconds to wait before failing
	 * @param sleepInMillis         - the number of milliseconds to wait before a recheck
	 * @return WebElement - first one found with by. Null
	 */
	public WebElement waitForEnabledElement(final By by,
			final int timeOutInMillis,
			final int sleepInMillis) {
		if (waitUntilEnabledElement(by, timeOutInMillis, sleepInMillis)) {
			return this.driver.findElement(by);
		}

		throw new ElementNotInteractableException("Element is not enabled");
	}

	/**
	 * Wait until the specified element to be present and enabled.
	 *
	 * @param by - Selector to wait for, and return
	 * @return boolean true if element is found, else false
	 */
	public boolean waitUntilEnabledElement(final By by) {
		return waitUntilEnabledElement(by, this.timeout, this.fluentRetryTime);
	}

	/**
	 * Wait for the specified element to be present and enabled.
	 *
	 * @param by Selector to wait for, and return
	 * @param timeOutInMillis the number of milliseconds to wait before failing
	 * @param sleepInMillis the number of milliseconds to wait before a recheck
	 * @return boolean true if element is found, else false
	 */
	public boolean waitUntilEnabledElement(
			final By by,
			final int timeOutInMillis,
			final int sleepInMillis) {

		WebElement element = this.waitForVisibleElement(by);
		FluentWait<WebElement> fluentWait = FluentWaitFactory.getNewElementFluentWait(element, timeOutInMillis, sleepInMillis);

		Function<WebElement, Boolean> function = obj -> {
			try {
				return obj.isEnabled();
			} catch (NoSuchElementException | StaleElementReferenceException e) {
				// Do not throw these exceptions here.
				// Instead return false and let the fluentwait try again.
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
	 * Waits until the element is disabled.
	 *
	 * @param by the By selector
	 * @return returns true if the element is disabled, else false
	 */
	public boolean waitUntilDisabledElement(By by) {
		return waitUntilDisabledElement(by, this.timeout, this.fluentRetryTime);
	}  

	/**
	 * Waits until the element is disabled.
	 *
	 * @param by the web element
	 * @param timeOutInMillis he number of milliseconds to wait before failing
	 * @param sleepInMillis the number of milliseconds to wait before a recheck
	 * @return returns true if the element is disabled, else false
	 */
	public boolean waitUntilDisabledElement(By by,
			final int timeOutInMillis,
			final int sleepInMillis) {
		try {
			WebElement element = this.waitForVisibleElement(by, timeOutInMillis, sleepInMillis);
			FluentWait<WebElement> fluentWait = FluentWaitFactory.getNewElementFluentWait(element, timeOutInMillis, sleepInMillis);
			return fluentWait.until(obj -> !obj.isEnabled());
		} catch (NoSuchElementException | StaleElementReferenceException e) {
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

	/**
	 * Wait until the element is not displayed or visible.
	 *
	 * @param by Selector to not be displayed or visible
	 */
	public void waitForAbsentElement(final By by) {
		this.waitForAbsentElement(by, this.timeout, this.fluentRetryTime);
	}

	/**
	 * Wait until the element is not displayed or visible.
	 *
	 * @param by Selector to not be displayed or visible
	 */
	public void waitForAbsentElement(final By by,
			final int timeOutInMillis,
			final int sleepInMillis) {
		boolean isAbsent = this.waitUntilAbsentElement(by, timeOutInMillis, sleepInMillis);

		if (!isAbsent) {
			throw new TimeoutException(MessageFormat.format("Element with selector {0} is not absent", by));
		}
	}

	/**
	 * Wait until the element is not displayed or visible.
	 *
	 * @param by Selector to not be displayed or visible
	 */
	public boolean waitUntilAbsentElement(final By by) {
		return this.waitUntilAbsentElement(by, this.timeout, this.fluentRetryTime);
	}

	/**
	 * Wait until the element is not displayed or visible.
	 *
	 * @param by element to not be displayed or visible
	 * @param timeOutInMillis the number of milliseconds to wait before failing
	 * @param sleepInMillis the number of milliseconds to wait before a recheck
	 * @return boolean - true if not displayed
	 */
	public boolean waitUntilAbsentElement(By by, 
			final int timeOutInMillis, 
			final int sleepInMillis) {

		try {
			WebElement element = this.driver.findElement(by);
			FluentWait<WebElement> fluentWait = FluentWaitFactory.getNewElementFluentWait(element, timeOutInMillis, sleepInMillis);
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
	 * Wait for a selector to present, and then return a list of all WebElements that are located by
	 * that selector.
	 *
	 * @param by Selector value to wait for
	 * @return List of WebElements - all web elements found by the specified selector
	 */
	public List<WebElement> waitForElements(final By by) {
		return this.waitForElements(by, getWaitDriver());
	}

	/**
	 * Wait for a selector to present, and then return a list of all WebElements that are located by
	 * that selector.
	 *
	 * @param by Selector value to wait for
	 * @param timeOutInMillis the number of milliseconds to wait before failing
	 * @param sleepInMillis the number of milliseconds to wait before a recheck
	 * @return List of WebElements - all web elements found by the specified selector
	 * @throws Exception if encountered
	 */
	public List<WebElement> waitForElements(final By by, 
			final int timeOutInMillis, 
			final int sleepInMillis) {
		WebDriverWait wait = this.getNewWaitDriver(timeOutInMillis, sleepInMillis);
		return waitForElements(by, wait);
	}

	/**
	 * Wait for a selector to present, and then return a list of all WebElements that are located by
	 * that selector.
	 *
	 * @param by  Selector value to wait for
	 * @param wait The wait driver
	 * @return List of WebElements - all web elements found by the specified selector
	 * @throws Exception if encountered
	 */
	public List<WebElement> waitForElements(final By by, WebDriverWait wait) {
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
	}

	/**
	 * Wait for the specified element to have the Exact text.
	 *
	 * @param by By selector to examine
	 * @param text String to search for in the text
	 * @return WebElement - element, null if not found and assert == false
	 */
	public WebElement waitForExactText(final By by, final String text) {
		waitUntilExactText(by, text);
		return this.driver.findElement(by);
	}

	/**
	 * Wait until the exact text is present in the specified element.
	 *
	 * @param by  Selector to examine for the specified text
	 * @param text String value to verify the specified selector contains
	 * @return boolean - true if the text was found in the element - else false
	 */
	public boolean waitUntilExactText(final By by, final String text) {
		return this.waitUntilExactText(by, text, getWaitDriver());
	}

	/**
	 * Wait until the exact text is present in the specified element.
	 *
	 * @param by Selector to examine for the specified text
	 * @param text String value to verify the specified selector contains
	 * @param timeOutInMillis the number of milliseconds to wait before failing
	 * @param sleepInMillis the number of milliseconds to wait before a recheck
	 * @return boolean - true if the text was found in the element - else false
	 */
	public boolean waitUntilExactText(final By by, 
			final String text, 
			final int timeOutInMillis, 
			final int sleepInMillis) {
		WebDriverWait wait = this.getNewWaitDriver(timeOutInMillis, sleepInMillis);
		return waitUntilExactText(by, text, wait);
	}

	/**
	 * Wait until the exact text is present in the specified element.
	 *
	 * @param by Selector to examine for the specified text
	 * @param text String value to verify the specified selector contains
	 * @param wait The wait driver
	 * @return boolean - true if the text was found in the element - else false
	 */
	public boolean waitUntilExactText(final By by, 
			final String text, 
			WebDriverWait wait) {
		try {
			return wait.until((ExpectedCondition<Boolean>) function -> doesTextMatch(by, text));
		} catch (NoSuchElementException | StaleElementReferenceException | TimeoutException e) {
			return false;
		}
	}

	/**
	 * Wait for an element to contain a specified text.
	 *
	 * @param by  Selector to check the containing text
	 * @param text String that should be contained within the selector
	 * @return WebElement containing the text
	 */
	public WebElement waitForContainsText(final By by, final String text) {
		if (waitUntilContainsText(by, text, getWaitDriver())) {
			return this.driver.findElement(by);
		}
		String error = String.format("Selector [%s] couldn't be found.%n", by.toString());
		throw new NotFoundException(error);
	}

	/**
	 * Wait until an element contains the specified text.
	 *
	 * @param by  Selector to check the containing text
	 * @param text String that should be contained within the selector
	 * @return boolean - true if the text is contained in the selector, else false
	 */
	public boolean waitUntilContainsText(final By by, final String text) {
		return this.waitUntilContainsText(by, text, getWaitDriver());
	}

	/**
	 * Wait until an element contains the specified text.
	 *
	 * @param by Selector to check the containing text
	 * @param text  String that should be contained within the selector
	 * @param timeOutInMillis the number of milliseconds to wait before failing
	 * @param sleepInMillis the number of milliseconds to wait before a recheck
	 * @return boolean - true if the text is contained in the selector, else false
	 */
	public boolean waitUntilContainsText(final By by, 
			final String text, 
			final int timeOutInMillis, 
			final int sleepInMillis) {
		WebDriverWait wait = this.getNewWaitDriver(timeOutInMillis, sleepInMillis);
		return waitUntilContainsText(by, text, wait);
	}

	/**
	 * Wait until the element contains the specified text.
	 *
	 * @param by      Selector to check the containing text
	 * @param text    String that should be contained within the selector
	 * @param wait int value of seconds to wait before timing out
	 * @return boolean - true if the text is contained in the selector, else false
	 */
	public boolean waitUntilContainsText(final By by, 
			final String text, 
			WebDriverWait wait) {
		try {
			return wait.until((ExpectedCondition<Boolean>) function -> doesContainsText(by, text));
		} catch (NoSuchElementException | StaleElementReferenceException | TimeoutException e) {
			return false;
		}
	} 

	/**
	 * Wait until an attribute of the specified selector to be present and equal the desired value.
	 *
	 * @param by        Selector to look for
	 * @param attribute String value of the attribute to look at on the specified selector
	 * @param text      String value of the text to look for in the attribute
	 * @return true if the attribute with the specified text value is found, else false
	 */
	public boolean waitUntilAttributeTextEquals(final By by,
			final String attribute,
			final String text) {
		return this.waitUntilAttribute(by, attribute, text, getWaitDriver(), false);
	}

	/**
	 * Wait until an attribute of the specified selector to be present.
	 *
	 * @param by              Selector to look for
	 * @param attribute       String value of the attribute to look at on the specified selector
	 * @param text            String value of the text to look for in the attribute
	 * @param timeOutInMillis       - the number of milliseconds to wait before failing
	 * @param sleepInMillis         - the number of milliseconds to wait before a recheck
	 * @return true if the attribute with the specified text value is found, else false
	 */
	public boolean waitUntilAttributeTextEquals(final By by,
			final String attribute,
			final String text,
			final int timeOutInMillis,
			final int sleepInMillis) {
		WebDriverWait wait = this.getNewWaitDriver(timeOutInMillis, sleepInMillis);
		return waitUntilAttribute(by, attribute, text, wait, false);
	}

	/**
	 * Wait for an attribute of the specified selector to be present.
	 *
	 * @param by        Selector to look for
	 * @param attribute String value of the attribute to look at on the specified selector
	 * @param text      String value of the text to look for in the attribute
	 * @return Webelement of the selector that is found
	 */
	public WebElement waitForAttributeTextEquals(final By by,
			final String attribute,
			final String text) {
		return this.waitForAttributeTextEquals(by, attribute, text, getWaitDriver());
	}

	/**
	 * Wait for an attribute of the specified selector to be present and equal the desired value.
	 *
	 * @param by Selector to look for
	 * @param attribute String value of the attribute to look at on the specified selector
	 * @param text String value of the text to look for in the attribute
	 * @param timeOutInMillis the number of milliseconds to wait before failing
	 * @param sleepInMillis the number of milliseconds to wait before a recheck
	 * @return WebElement of the selector that is found
	 */
	public WebElement waitForAttributeTextEquals(final By by,
			final String attribute,
			final String text,
			final int timeOutInMillis,
			final int sleepInMillis) {
		WebDriverWait wait = this.getNewWaitDriver(timeOutInMillis, sleepInMillis);
		return waitForAttributeTextEquals(by, attribute, text, wait);
	}

	/**
	 * Wait for an attribute of the specified selector to be present and equal the desired value.
	 *
	 * @param by Selector to look for
	 * @param attribute String value of the attribute to look at on the specified selector
	 * @param text String value of the text to look for in the attribute
	 * @param wait int version of the timeout in seconds
	 * @return WebElement of the selector that is found
	 */
	public WebElement waitForAttributeTextEquals(final By by,
			final String attribute,
			final String text,
			WebDriverWait wait) {
		if (this.waitUntilAttribute(by, attribute, text, wait, false)) {
			return this.driver.findElement(by);
		}
		
		String error = String.format("Selector [%s] couldn't be found.%n", by.toString());
		throw new NotFoundException(error);    
	}

	/**
	 * Wait for an attribute of the specified selector to be present, and contain the specified text.
	 *
	 * @param by Selector to look for
	 * @param attribute String value of the attribute to look at on the specified selector
	 * @param text String value of the text to look for in the attribute
	 * @return true if the attribute with the specified text value is found, else false
	 */
	public boolean waitUntilAttributeTextContains(final By by,
			final String attribute,
			final String text) {
		return waitUntilAttribute(by, attribute, text, getWaitDriver(), true);
	}

	/**
	 * Wait for an attribute of the specified selector to be present, and contain the specified text.
	 *
	 * @param by Selector to look for
	 * @param attribute String value of the attribute to look at on the specified selector
	 * @param text String value of the text to look for in the attribute
	 * @param timeOutInMillis the number of milliseconds to wait before failing
	 * @param sleepInMillis the number of milliseconds to wait before a recheck
	 * @return true if the attribute with the specified text value is found, else false
	 */
	public boolean waitUntilAttributeTextContains(final By by,
			final String attribute,
			final String text,
			final int timeOutInMillis,
			final int sleepInMillis) {
		WebDriverWait wait = this.getNewWaitDriver(timeOutInMillis, sleepInMillis);
		return waitUntilAttribute(by, attribute, text, wait, true);
	} 

	/**
	 * Wait for an attribute of the specified selector to be present, and contain the specified text.
	 *
	 * @param by        Selector to look for
	 * @param attribute String value of the attribute to look at on the specified selector
	 * @param text      String value of the text to look for in the attribute
	 * @param wait      The wait driver
	 * @param contains  boolean true if checking if contains, false if exact match
	 * @return true if the attribute with the specified text value is found, else false
	 */
	public boolean waitUntilAttribute(final By by, 
			final String attribute, 
			final String text,
			WebDriverWait wait, 
			final boolean contains) {
		try {
			return wait.until((ExpectedCondition<Boolean>) f -> attributeMatches(f, by, attribute, text, contains));
		} catch (NoSuchElementException | StaleElementReferenceException | TimeoutException e) {
			return false;
		}
	}

	/**
	 * Scroll an element into view, and wait for it to be clickable.
	 *
	 * @param by Selector to wait for and focus on
	 * @return WebElement
	 */
	public WebElement waitForClickableElementAndScrollIntoView(final By by) {
		return this.waitForClickableElementAndScrollIntoView(by, getWaitDriver());
	}

	/**
	 * Scroll an element into view, and wait for it to be clickable.
	 *
	 * @param by Selector to wait for and focus on
	 * @param timeOutInMillis the number of milliseconds to wait before failing
	 * @param sleepInMillis the number of milliseconds to wait before a recheck
	 * @return WebElement
	 */
	public WebElement waitForClickableElementAndScrollIntoView(final By by,
			final int timeOutInMillis,
			final int sleepInMillis) {
		WebDriverWait wait = this.getNewWaitDriver(timeOutInMillis, sleepInMillis);
		return waitForClickableElementAndScrollIntoView(by, wait);
	}

	/**
	 * Scroll an element into view, and wait for it to be clickable.
	 *
	 * @param by Selector to wait for and focus on
	 * @param wait int value of seconds to wait for before timing out
	 * @return WebElement
	 */
	public WebElement waitForClickableElementAndScrollIntoView(final By by, WebDriverWait wait) {
		scrollIntoView(by);
		waitUntilPageLoad();
		return waitForClickableElement(by, wait);
	}

	/**
	 * Scroll an element into view, and wait for it to be clickable.
	 *
	 * @param by Selector to wait for and focus on
	 * @return boolean - true if the element is found and clickable
	 */
	public boolean waitUntilClickableElementAndScrollIntoView(final By by) {
		return this.waitUntilClickableElementAndScrollIntoView(by, getWaitDriver());
	}

	/**
	 * Scroll an element into view, and wait for it to be clickable.
	 *
	 * @param by Selector to wait for and focus on
	 * @param timeOutInMillis the number of milliseconds to wait before failing
	 * @param sleepInMillis the number of milliseconds to wait before a recheck
	 * @return boolean - true if the element is found and clickable
	 */
	public boolean waitUntilClickableElementAndScrollIntoView(final By by,
			final int timeOutInMillis,
			final int sleepInMillis) {
		WebDriverWait wait = this.getNewWaitDriver(timeOutInMillis, sleepInMillis);
		return waitUntilClickableElementAndScrollIntoView(by, wait);
	}

	/**
	 * Scroll an element into view, and wait for it to be clickable.
	 *
	 * @param by Selector to wait for and focus on
	 * @param wait The wait driver
	 * @return boolean - true if the element is found and clickable
	 */
	public boolean waitUntilClickableElementAndScrollIntoView(final By by, WebDriverWait wait) {
		scrollIntoView(by);
		waitUntilPageLoad();
		return waitUntilClickableElement(by, wait);
	}

	/**
	 * Wait for the specified selector to be clickable.
	 *
	 * @param by Selector to wait for
	 * @return boolean - true if found and clickable, else false
	 */
	public boolean waitUntilClickableElement(final By by) {
		return this.waitUntilClickableElement(by, getWaitDriver());
	}

	/**
	 * Wait for the specified selector to be clickable.
	 *
	 * @param by Selector to wait for
	 * @param timeOutInMillis the number of milliseconds to wait before failing
	 * @param sleepInMillis the number of milliseconds to wait before a recheck
	 * @return boolean - true if found and clickable, else false
	 */
	public boolean waitUntilClickableElement(final By by, final int timeOutInMillis, final int sleepInMillis) {
		WebDriverWait wait = this.getNewWaitDriver(timeOutInMillis, sleepInMillis);
		return waitUntilClickableElement(by, wait);
	}

	/**
	 * Wait for the specified selector to be clickable.
	 *
	 * @param by              Selector to wait for
	 * @param wait            The wait driver
	 * @return boolean - true if found and clickable, else false
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
	 * Wait for the element specified by the provided selector to be clickable.
	 *
	 * @param by Selector to wait for to be clickable
	 * @return WebElement that is located, or null if none is found
	 */
	public WebElement waitForClickableElement(final By by) {
		return this.waitForClickableElement(by, getWaitDriver());
	}

	/**
	 * Wait for the element specified by the provided selector to be clickable.
	 *
	 * @param by Selector to wait for to be clickable
	 * @param timeOutInMillis the number of milliseconds to wait before failing
	 * @param sleepInMillis the number of milliseconds to wait before a recheck
	 * @return WebElement that is located, or null if none is found
	 */
	public WebElement waitForClickableElement(final By by, 
			final int timeOutInMillis, 
			final int sleepInMillis) {

		WebDriverWait wait = this.getNewWaitDriver(timeOutInMillis, sleepInMillis);
		return waitForClickableElement(by, wait);
	}

	/**
	 * Wait for the element specified by the provided selector to be clickable.
	 *
	 * @param by Selector to wait for to be clickable
	 * @param wait The wait driver
	 * @return WebElement that is located, or null if none is found
	 */
	public WebElement waitForClickableElement(final By by, WebDriverWait wait) {
			return wait.until(ExpectedConditions.elementToBeClickable(by));
	}

	/**
	 * Wait for the page to load by waiting for the source to stop changing for at least a second.
	 * Asserts the page loads
	 */
	public void waitForPageLoad() {
		if (!this.waitUntilPageLoad()) {
			throw new TimeoutException("Page load took longer than timeout configuration.");
		}
	}

	/**
	 * Wait for the page to load by waiting for the source to stop changing for at least a second.
	 *
	 * @return true if it's successfully loaded, false if timed out and not finished loading
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
	 * Waits for the Frame to be available.
	 *
	 * @param by The frame locator
	 */
	public boolean waitUntilIframeToLoad(By by) {
		return waitUntilIframeToLoad(by, this.getWaitDriver());
	}

	/**
	 * Waits for the Frame to be available.
	 *
	 * @param by The frame locator
	 * @param timeOutInMillis he number of milliseconds to wait before failing
	 * @param sleepInMillis the number of milliseconds to wait before a recheck
	 */
	public boolean waitUntilIframeToLoad(By by,
			final int timeOutInMillis,
			final int sleepInMillis) {
		WebDriverWait wait = this.getNewWaitDriver(timeOutInMillis, sleepInMillis);
		return waitUntilIframeToLoad(by, wait);
	}

	/**
	 * Waits for the Frame to be available.
	 *
	 * @param by The frame locator
	 */
	public boolean waitUntilIframeToLoad(By by, WebDriverWait wait) {

		try {
			waitForIframeToLoad(by, wait);
			return true;
		} catch (NoSuchElementException
				| StaleElementReferenceException
				| TimeoutException e) {
			return false;
		}
	}

	/**
	 * Waits for the Frame to be available.
	 *
	 * @param by The frame locator
	 */
	public void waitForIframeToLoad(By by) {
		waitForIframeToLoad(by, this.getWaitDriver());
	}

	/**
	 * Waits for the Frame to be available.
	 *
	 * @param by The frame locator
	 * @param timeOutInMillis the number of milliseconds to wait before failing
	 * @param sleepInMillis  the number of milliseconds to wait before a recheck
	 */
	public void waitForIframeToLoad(By by,
			final int timeOutInMillis,
			final int sleepInMillis) {
		WebDriverWait wait = this.getNewWaitDriver(timeOutInMillis, sleepInMillis);
		waitForIframeToLoad(by, wait);
	}

	/**
	 * Waits for the Frame to be available.
	 *
	 * @param by The frame locator
	 */
	public void waitForIframeToLoad(By by, WebDriverWait wait) {
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(by));    
	}

	/**
	 * Returns the webDriver of this SeleniumWait object.
	 *
	 * @return the webDriver (type WebDriver) of this SeleniumWait object.
	 */
	protected WebDriver getWebDriver() {
		return driver;
	}

	/**
	 * Gets a new WaitDriver using the default timeout.
	 *
	 * @return new WaitDriver
	 */
	protected WebDriverWait getNewWaitDriver(WebDriver driver) {
		return getNewWaitDriver(driver, this.timeout, this.fluentRetryTime);
	}

	/**
	 * Gets a new WaitDriver using the default timeout.
	 *
	 * @return new WaitDriver
	 */
	protected WebDriverWait getNewWaitDriver() {
		return getNewWaitDriver(this.getWebDriver(), this.timeout, this.fluentRetryTime);
	}

	/**
	 * Gets a new WaitDriver using the specified timeout.
	 *
	 * @param timeOutInMillis the default timeout
	 * @return new WaitDriver
	 */
	protected WebDriverWait getNewWaitDriver(int timeOutInMillis) {
		return getNewWaitDriver(this.getWebDriver(), timeOutInMillis, this.fluentRetryTime);
	}

	/**
	 * Gets a new WaitDriver using the specified timeout.
	 *
	 * @param timeOutInMillis the time to wait before a timeout
	 * @param sleepInMillis   the time to wait before a recheck
	 * @return new WaitDriver
	 */
	protected WebDriverWait getNewWaitDriver(int timeOutInMillis, int sleepInMillis) {
		return getNewWaitDriver(this.getWebDriver(), timeOutInMillis, sleepInMillis);
	}

	/**
	 * Gets a new WaitDriver using the specified timeout.
	 *
	 * @param timeOutInMillis the time to wait before a timeout
	 * @param sleepInMillis   the time to wait before a recheck
	 * @return new WaitDriver
	 */
	protected WebDriverWait getNewWaitDriver(WebDriver driver, int timeOutInMillis, int sleepInMillis) {
		int timeoutInSeconds = timeOutInMillis / 1000;
		WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds, sleepInMillis);
		setWaitDriver(wait);
		return wait;
	}

	/**
	 * Scroll the web page so the selector is visible.
	 *
	 * @param by Selector to make visible
	 */
	private void scrollIntoView(By by) {
		WebElement element = waitForVisibleElement(by);
		int counter = 0;
		final int max = 5;

		try {
			((JavascriptExecutor) this.driver).executeScript(
					"arguments[0].scrollIntoView(true);",
					element);
		} 
		catch (Exception e) {
			String error = String.format("Failed JavaScript scroll into view...%s%n", (Object[])e.getStackTrace());
			System.err.print(error);
		}

		Coordinates coord = ((Locatable) element).getCoordinates();
		while (coord.inViewPort().getY() < HEADER_SIZE && counter < max) {
			waitForVisibleElement(BODY_BY).sendKeys(Keys.ARROW_UP);
			counter++;
		}
	}



	/**
	 * Check the text value of an attribute.
	 *
	 * @param driver   - web driver
	 * @param by        Selector to get the attribute from
	 * @param attribute String value of the attribute to examine
	 * @param text      String to check against the attribute
	 * @param contains  boolean - true to see if the string is contained, false if exact match
	 * @return boolean - true if they match, else false
	 */
	private static boolean attributeMatches(
			WebDriver driver, 
			By by, 
			String attribute, 
			String text,
			boolean contains) {
		try {
			if (contains) {
				return driver.findElement(by).getAttribute(attribute).contains(text);
			} else {
				return driver.findElement(by).getAttribute(attribute).equals(text);
			}
		} 
		catch (NullPointerException e) {
			return false;
		}
	}

	/**
	 * Checks if the text of the elements are equal
	 *
	 * @param by   Selector to examine
	 * @param text Text that is being compared to the selector
	 * @return boolean - true if equal
	 */
	private boolean doesTextMatch(By by, String text) {
		try {
			WebElement element = this.waitForVisibleElement(by);

			if (element != null && element.getText().equals(text)) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	/**
	 * Checks if the text of the elements are equal.
	 *
	 * @param by   Selector to examine
	 * @param text Text that is being compared to the selector
	 * @return boolean - true if equal
	 */
	private boolean doesContainsText(By by, String text) {
		try {
			WebElement element = this.waitForVisibleElement(by);

			if (element != null && element.getText().contains(text)) {
				return true;
			}
		} 
		catch (Exception e) {
			return false;
		}
		return false;
	} 
}