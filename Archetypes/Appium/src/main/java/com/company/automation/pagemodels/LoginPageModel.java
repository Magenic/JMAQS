package com.company.automation.pagemodels;

import com.magenic.jmaqs.appium.AppiumTestObject;
import org.openqa.selenium.By;

/**
 * The type Login page model.
 */
public class LoginPageModel extends BasePageModel {

  /**
   * Field usernameField
   */
  private static By usernameField = By.id("email");

  /**
   * Field passwordField
   */
  private static By passwordField = By.id("password");

  /**
   * Field loginButton
   */
  private static By loginButton = By.id("email_sign_in_button");

  /**
   * Field errorMessage
   */
  private static By errorMessage = By.id("snackbar_text");

  /**
   * Instantiates a new Login page model.
   *
   * @param appiumTestObject the appium test object
   */
  public LoginPageModel(AppiumTestObject appiumTestObject) {
    super(appiumTestObject);
  }

  /**
   * Enter credentials.
   *
   * @param username the username
   * @param password the password
   */
  public void enterCredentials(String username, String password) {
    this.appiumTestObject.getAppiumWait().waitForVisibleElement(usernameField).sendKeys(username);
    this.appiumTestObject.getAppiumWait().waitForVisibleElement(passwordField).sendKeys(password);
  }

  /**
   * Login with invalid credentials.
   *
   * @param username the username
   * @param password the password
   */
  public void loginWithInvalidCredentials(String username, String password) {

    enterCredentials(username, password);
    this.appiumTestObject.getAppiumWait().waitForVisibleElement(loginButton).click();
    this.appiumTestObject.getAppiumWait().waitForVisibleElement(errorMessage);

  }

  /**
   * Login with valid credentials main page model.
   *
   * @param username the username
   * @param password the password
   * @return the main page model
   */
  public MainPageModel loginWithValidCredentials(String username, String password) {

    enterCredentials(username, password);
    this.appiumTestObject.getAppiumWait().waitForVisibleElement(loginButton).click();

    return new MainPageModel(this.appiumTestObject);
  }

  /**
   * Gets error message.
   *
   * @return the error message
   */
  public String getErrorMessage() {
    return this.appiumTestObject.getAppiumWait().waitForVisibleElement(errorMessage).getText();
  }
}
