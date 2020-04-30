package com.magenic.jmaqs.selenium;

import java.util.ArrayList;
import java.util.List;

import com.magenic.jmaqs.utilities.helper.exceptions.TimeoutException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Driver for dynamically finding and interacting with elements
 */
public class LazyWebElement extends AbstractLazyElement {

	/**
	 * Initializes a new instance of the {@link #LazyWebElement} class
	 * @param testObject The selenium test object
	 * @param locator The by locator to search on
	 * @param userFriendlyName The user friendly name of the lazy element
	 */
	public LazyWebElement(SeleniumTestObject testObject, By locator, String userFriendlyName) {
		super(testObject, locator, userFriendlyName);
	}

	/**
	 * Initializes a new instance of the {@link #LazyWebElement} class
	 * @param parent The parent lazy element
	 * @param locator The by locator to search on
	 * @param userFriendlyName The user friendly name of the lazy element
	 */
	public LazyWebElement(LazyWebElement parent, By locator, String userFriendlyName) {
		super(parent, locator, userFriendlyName);
	}

	/**
	 * Initializes a new instance of the {@link #LazyWebElement} class
	 * @param parent The parent lazy element
	 * @param locator THe by locator to search on
	 * @param userFriendlyName The user friendly name of the lazy element
	 * @param index The index of the cached element if cached using the {@link #findElements(By)} method
	 * @param cachedElement The cached element
	 */
	public LazyWebElement(LazyWebElement parent, By locator, String userFriendlyName, Integer index, WebElement cachedElement) {
		super(parent, locator, userFriendlyName, index, cachedElement);
	}


	/**
	 * Find the WebElement using the by and then maps it to the LazyWebElement
	 * @param locator The by locator
	 * @return The LazyWebElement of the element found
	 * @throws TimeoutException If a timeout occurred while waiting for the element to be found
	 * @throws InterruptedException If the thread is interrupted while waiting for the element to be found
	 */
	public LazyWebElement findElement(By locator) throws TimeoutException, InterruptedException {
        WebElement elementFound = this.findRawElement(locator);
		return new LazyWebElement(this, locator, this.userFriendlyName, null, elementFound);
	}

	/**
	 * Find the collection of WebElements and then map them to LazyWebElements
	 * @param locator The by locator
	 * @return The collection of WebElements mapped to LazyWebElements
	 * @throws TimeoutException If a timeout occurred while waiting for the element to be found
	 * @throws InterruptedException If the thread is interrupted while waiting for the element to be found
	 */
    public List<LazyWebElement> findElements(By locator) throws TimeoutException, InterruptedException {
        int index = 0;
        List<LazyWebElement> elements = new ArrayList<>();

        for (WebElement element : this.findRawElements(locator)) {
            elements.add(new LazyWebElement(
                    this, locator, String.format("%s - %d", this.userFriendlyName, index), index, element));
            index++;
        }

        return elements;
    }
}
