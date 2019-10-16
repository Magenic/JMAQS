package com.magenic.jmaqs.selenium.factories;

import java.util.concurrent.ConcurrentHashMap;

import com.magenic.jmaqs.selenium.UIWait;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.magenic.jmaqs.selenium.SeleniumUtilities;

/**
 * Factory class that is used for creating and maintaining a 
 * thread-safe collection of wait drivers. 
 */
public class UIWaitFactory {
	
	/** the collection of wait objects */
	private static ConcurrentHashMap<WebDriver, UIWait> waitCollection = new ConcurrentHashMap<>();
	
	/** private constructor so class can't be instantiated */
	private UIWaitFactory() { }
	
	/**
	 * Gets the {@link UIWait} object from the wait collection. If none exists,
	 * one is created and cached using the driver provided.
	 * 
	 * @param searchContext The web driver to associate with the wait
	 * @return the Wait object
	 */
	public static UIWait getWaitDriver(SearchContext searchContext) {
		WebDriver baseDriver = getLowLevelDriver(searchContext);
		
		UIWait waitDriver;
		if (waitCollection.containsKey(baseDriver)) {
			waitDriver = waitCollection.get(baseDriver);
		}
		else {
			waitDriver = new UIWait(baseDriver);
			setWaitDriver(baseDriver, waitDriver);
		}
		
		return waitDriver;
	}

	/**
	 * Gets the {@link UIWait} object from the wait collection. If none exists,
	 * one is created and cached using the driver provided.
	 *
	 * @param searchContext The web driver to associate with the wait
	 * @param timeOutInSeconds The timeout for the UIWait
	 * @param fluentRetryTime The fluent retry time for the UIWait
	 * @return the Wait object
	 */
	public static UIWait getWaitDriver(SearchContext searchContext, final int timeOutInSeconds, final int fluentRetryTime) {
		WebDriver baseDriver = getLowLevelDriver(searchContext);

		UIWait waitDriver;
		if (waitCollection.containsKey(baseDriver)) {
			waitDriver = waitCollection.get(baseDriver);
		}
		else {
			waitDriver = new UIWait(baseDriver, timeOutInSeconds, fluentRetryTime);
			setWaitDriver(baseDriver, waitDriver);
		}

		return waitDriver;
	}
	
	/**
	 * Adds the waitDriver to the collection
	 * 
	 * @param driver The web driver
	 * @param waitDriver the Wait object
	 */
	public static void setWaitDriver(SearchContext driver, UIWait waitDriver) {
		WebDriver baseDriver = getLowLevelDriver(driver);
		
		waitCollection.put(baseDriver, waitDriver);
	}
	
	/**
	 * Removes the waitDriver from the collection
	 * 
	 * @param driver the web driver
	 */
	public static void removeWaitDriver(SearchContext driver) {
		WebDriver baseDriver = getLowLevelDriver(driver);
		
		waitCollection.remove(baseDriver);
	}
	
	/**
	 * Get the underlying web driver.
	 *
	 * @param driver The web driver
	 * @return the underlying web driver
	 */
	private static WebDriver getLowLevelDriver(SearchContext searchContext) {
	    return (searchContext instanceof WebDriver) ? 
	    		(WebDriver)searchContext : SeleniumUtilities.webElementToWebDriver((WebElement)searchContext);		
	}
}
