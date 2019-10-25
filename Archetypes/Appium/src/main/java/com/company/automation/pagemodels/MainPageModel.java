package com.company.automation.pagemodels;

import com.magenic.jmaqs.appium.AppiumTestObject;
import org.openqa.selenium.By;

/**
 * The type Main page model.
 */
public class MainPageModel extends BasePageModel {

  /**
   * Username label value
   */
  private static By loginUsernameValue = By.id("usernameView");

  /**
   * Password label value
   */
  private static By loginPasswordValue = By.id("passView");

  /**
   * Instantiates a new Main page model.
   *
   * @param appiumTestObject the appium test object
   */
  public MainPageModel(AppiumTestObject appiumTestObject) {
    super(appiumTestObject);
  }

  /**
   * Gets username value text.
   *
   * @return the username value text
   */
  public String getUsernameValueText() {
    return this.appiumTestObject.getAppiumWait().waitForVisibleElement(loginUsernameValue).getText();
  }

  /**
   * Gets password value text.
   *
   * @return the password value text
   */
  public String getPasswordValueText() {
    return this.appiumTestObject.getAppiumWait().waitForVisibleElement(loginPasswordValue).getText();
  }
}
