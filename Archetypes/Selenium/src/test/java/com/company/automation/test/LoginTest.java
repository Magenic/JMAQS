package com.company.automation.test;

import com.magenic.jmaqs.selenium.BaseSeleniumTest;
import com.company.automation.pagemodels.HomePageModel;
import com.company.automation.pagemodels.LoginPageModel;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseSeleniumTest {

  @Test
  public void OpenPageTest() {
    LoginPageModel page = new LoginPageModel(this.getSeleniumTestObject());
    page.openLoginPage();
  }

  @Test
  public void enterValidCredentialsTest() {
    String username = "Ted";
    String password = "123";
    LoginPageModel page = new LoginPageModel(this.getSeleniumTestObject());
    page.openLoginPage();
    HomePageModel homepage = page.loginWithValidCredentials(username, password);
    Assert.assertTrue(homepage.isPageLoaded());
  }

  @Test
  public void EnterInvalidCredentials() {
    String username = "NOT";
    String password = "Valid";
    LoginPageModel page = new LoginPageModel(this.getSeleniumTestObject());
    page.openLoginPage();
    Assert.assertTrue(page.loginWithInvalidCredentials(username, password));
  }
}