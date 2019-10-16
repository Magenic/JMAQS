package com.company.automation.pagemodels;

import com.magenic.jmaqs.selenium.SeleniumConfig;
import com.magenic.jmaqs.selenium.SeleniumTestObject;
import org.openqa.selenium.By;

/**
 * The type Home page model.
 */
public class HomePageModel extends BasePageModel {

  /**
   * The URL for the page.
   */
  private static final String PAGE_URL = SeleniumConfig.getWebSiteBase()
      + "Static/Training3/HomePage.html";

  /**
   * Welcome Message Selector.
   */
  private static final By WELCOME_MESSAGE = By.cssSelector("#WelcomeMessage");

  /**
   * Instantiates a new Home page model.
   *
   * @param testObject
   *          the test object
   */
  public HomePageModel(SeleniumTestObject testObject) {
    super(testObject);
  }

  /**
   * Check if home page has been loaded
   * 
   * @return True if the page was loaded
   */
  public boolean isPageLoaded() {
    return this.testObject.getSeleniumWait().waitForVisibleElement(WELCOME_MESSAGE).isDisplayed();
  }
}
