package com.company.automation.test;

import com.magenic.jmaqs.appium.AppiumConfig;
import com.magenic.jmaqs.appium.BaseAppiumTest;
import com.company.automation.pagemodels.LoginPageModel;
import com.company.automation.pagemodels.MainPageModel;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by jasonedstrom on 6/23/17.
 */
public class AppiumTest extends BaseAppiumTest {

  /**
   * Application installed test.
   */
  @Test
  public void applicationInstalledTest() {
    Assert.assertTrue(this.getAppiumDriver().isAppInstalled(AppiumConfig.getBundleId()),
        "Expected application to be installed");
  }

  /**
   * Invalid login test.
   */
  @Test
  public void invalidLoginTest()
  {
    String expectedError = "Wrong username or password";
    LoginPageModel page = new LoginPageModel(this.getAppiumTestObject());
    page.loginWithInvalidCredentials("Not", "Valid");
    String errorMessage = page.getErrorMessage();
    Assert.assertEquals(errorMessage, expectedError, "Expected error message to be equal");
  }

  /**
   * Valid login test.
   */
  @Test
  public void validLoginTest()
  {
    String username = "Magenic";
    String password = "MAQS";
    LoginPageModel page = new LoginPageModel(this.getAppiumTestObject());
    MainPageModel mainPageModel = page.loginWithValidCredentials(username, password);
    String loggedInPassword = mainPageModel.getPasswordValueText();
    String loggedInUsername = mainPageModel.getUsernameValueText();

    Assert.assertEquals(loggedInUsername, username, "Expected username value to be equal");
    Assert.assertEquals(loggedInPassword, password, "Expected password value to be equal");
  }
}
