/*
 * Copyright 2019 (C) Magenic, All rights Reserved
 */
package com.magenic.jmaqs.selenium;

import com.magenic.jmaqs.selenium.unittestpagemodel.IFramePageModel;
import com.magenic.jmaqs.selenium.unittestpagemodel.PageElementsPageModel;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
@Ignore
@Deprecated
public class SeleniumWaitTest extends BaseSeleniumTest {
  /**
   * Url for the site.
   */
  private static String siteUrl = SeleniumConfig.getWebSiteBase();

  /**
   * Automation site url.
   */
  private static String siteAutomationUrl = siteUrl + "Automation/";

  /**
   * IFrame Automation site url.
   */
  private static String siteIframeAutomationUrl = siteUrl + "Automation/iFramePage";

  @Test
  public void getSeleniumWaitTest() {
    Assert.assertNotNull(this.getSeleniumTestObject().getSeleniumWait());
  }

  @Test
  public void getWaitDriverTest() {
    Assert.assertNotNull(this.getSeleniumWait().getWaitDriver(this.getWebDriver()));
  }

  @Test
  public void setWaitDriverTest() {
    WebDriverWait oldWait = this.getSeleniumWait().getWaitDriver(this.getWebDriver());
    WebDriverWait wait = new WebDriverWait(this.getWebDriver(), 10, 1000);
    this.getSeleniumWait().setWaitDriver(this.getWebDriver(), wait);
    Assert.assertNotNull(this.getSeleniumWait().getWaitDriver(this.getWebDriver()));
    Assert.assertNotEquals(this.getSeleniumWait().getWaitDriver(this.getWebDriver()), oldWait);
  }

  @Test
  public void resetWaitDriverTest() {
    WebDriverWait oldWait = this.getSeleniumWait().getWaitDriver(this.getWebDriver());
    Assert.assertNotNull(this.getSeleniumWait().resetWaitDriver());
    Assert.assertNotEquals(this.getSeleniumWait().getWaitDriver(this.getWebDriver()), oldWait);
  }

  @Test
  public void waitForPageLoadTest() {
    PageElementsPageModel page = new PageElementsPageModel(this.getSeleniumTestObject());
    page.open(siteAutomationUrl);
    page.getSeleniumWait().waitForPageLoad();
  }

  @Test
  public void waitUntilPageLoadTest() {
    PageElementsPageModel page = new PageElementsPageModel(this.getSeleniumTestObject());
    page.open(siteAutomationUrl);
    Assert.assertTrue(page.getSeleniumWait().waitUntilPageLoad());
  }
  
  @Test
  public void waitForPresentElementTest() {
    PageElementsPageModel page = new PageElementsPageModel(this.getSeleniumTestObject());
    page.open(siteAutomationUrl);
    Assert.assertNotNull(page.getSeleniumWait().waitForPresentElement(page.showDialog1ButtonLocator));
  }

  @Test
  public void waitForPresentElementNewTimeoutTest() {
    PageElementsPageModel page = new PageElementsPageModel(this.getSeleniumTestObject());
    page.open(siteAutomationUrl);
    Assert.assertNotNull(page.getSeleniumWait().waitForPresentElement(page.showDialog1ButtonLocator, 3000, 1000));
  }
  
  @Test
  public void waitForVisibleElementTest() {
    PageElementsPageModel page = new PageElementsPageModel(this.getSeleniumTestObject());
    page.open(siteAutomationUrl);
    page.getSeleniumWait().waitForClickableElement(page.showDialog1ButtonLocator).click();

    // Method waitForVisibleElement Test    
    Assert.assertTrue(page.getSeleniumWait().waitForVisibleElement(page.closeButtonShowDialogButtonLocator).isDisplayed());
  }

  @Test
  public void waitForVisibleElementNewTimeoutTest() {
    PageElementsPageModel page = new PageElementsPageModel(this.getSeleniumTestObject());
    page.open(siteAutomationUrl);
    page.getSeleniumWait().waitForClickableElement(page.showDialog1ButtonLocator).click();

    // Method waitForVisibleElement Test   
    Assert.assertTrue(page.getSeleniumWait().waitForVisibleElement(page.closeButtonShowDialogButtonLocator, 10000, 1000).isDisplayed());
  }

  @Test
  public void waitForVisibleElementNewWaitDriverTest() {
    PageElementsPageModel page = new PageElementsPageModel(this.getSeleniumTestObject());
    page.open(siteAutomationUrl);
    page.getSeleniumWait().waitForClickableElement(page.showDialog1ButtonLocator).click();

    WebDriverWait wait = new WebDriverWait(this.getWebDriver(), 10, 1000);
    // Method waitForVisibleElement Test   
    Assert.assertTrue(page.getSeleniumWait().waitForVisibleElement(page.closeButtonShowDialogButtonLocator, wait).isDisplayed());
  }

  @Test
  public void waitUntilVisibleElementTest() {
    PageElementsPageModel page = new PageElementsPageModel(this.getSeleniumTestObject());
    page.open(siteAutomationUrl);
    page.getSeleniumWait().waitForClickableElement(page.showDialog1ButtonLocator).click();

    // Method Test   
    Assert.assertTrue(page.getSeleniumWait().waitUntilVisibleElement(page.closeButtonShowDialogButtonLocator));
  }

  @Test
  public void waitUntilVisibleElementFalseTest() {
    PageElementsPageModel page = new PageElementsPageModel(this.getSeleniumTestObject());
    page.open(siteAutomationUrl);

    // Method Test   
    Assert.assertFalse(page.getSeleniumWait().waitUntilVisibleElement(page.closeButtonShowDialogButtonLocator, 3000, 1000));
  }
  
  @Test
  public void waitForEnabledElementTest() {
    PageElementsPageModel page = new PageElementsPageModel(this.getSeleniumTestObject());
    page.open(siteAutomationUrl);
    page.getSeleniumWait().waitForClickableElement(page.showDialog1ButtonLocator).click();

    // Method Test   
    Assert.assertNotNull(page.getSeleniumWait().waitForEnabledElement(page.closeButtonShowDialogButtonLocator));
  }
  
  @Test
  public void waitForEnabledElementNegativeTest() {
    PageElementsPageModel page = new PageElementsPageModel(this.getSeleniumTestObject());
    page.open(siteAutomationUrl);

    // Method Test   
    try{
      page.getSeleniumWait().waitForEnabledElement(page.closeButtonShowDialogButtonLocator, 3000, 1000);
      Assert.fail("Element was enabled on page but was expected to be disabled");
    } catch (TimeoutException e) {
    }
  }

  @Test
  public void waitUntilEnabledElementTest() {
    PageElementsPageModel page = new PageElementsPageModel(this.getSeleniumTestObject());
    page.open(siteAutomationUrl);
    page.getSeleniumWait().waitForClickableElement(page.showDialog1ButtonLocator).click();

    // Method Test   
    Assert.assertTrue(page.getSeleniumWait().waitUntilEnabledElement(page.closeButtonShowDialogButtonLocator));
  }

  @Test
  public void waitUntilEnabledElementFalseTest() {
    PageElementsPageModel page = new PageElementsPageModel(this.getSeleniumTestObject());
    page.open(siteAutomationUrl);
    page.getSeleniumWait().waitForClickableElement(page.showDialog1ButtonLocator).click();

    // Method Test   
    Assert.assertFalse(page.getSeleniumWait().waitUntilEnabledElement(page.disabledFieldLocator, 3000, 1000));
  }
  
  @Test
  public void waitUntilDisabledElementTest() {
    PageElementsPageModel page = new PageElementsPageModel(this.getSeleniumTestObject());
    page.open(siteAutomationUrl);

    // Method Test 
    Assert.assertTrue(page.getSeleniumWait().waitUntilDisabledElement(page.disabledFieldLocator));    
  }

  @Test
  public void waitUntilDisabledElementNewTimoutTest() {
    PageElementsPageModel page = new PageElementsPageModel(this.getSeleniumTestObject());
    page.open(siteAutomationUrl);

    // Method Test 
    Assert.assertTrue(page.getSeleniumWait().waitUntilDisabledElement(page.disabledFieldLocator, 3000, 1000));
  }

  @Test
  public void waitUntilDisabledElementFalseTest() {
    PageElementsPageModel page = new PageElementsPageModel(this.getSeleniumTestObject());
    page.open(siteAutomationUrl);

    // Method Test 
    Assert.assertFalse(page.getSeleniumWait().waitUntilDisabledElement(page.showDialog1ButtonLocator, 3000, 1000));
  }

  @Test
  public void waitUntilAbsentElementTest() {
    PageElementsPageModel page = new PageElementsPageModel(this.getSeleniumTestObject());
    page.open(siteAutomationUrl);
    page.getSeleniumWait().waitForClickableElement(page.showDialog1ButtonLocator).click();
    page.getSeleniumWait().waitForClickableElement(page.closeButtonShowDialogButtonLocator).click();
    
    // Method Test 
    Assert.assertTrue(page.getSeleniumWait().waitUntilAbsentElement(page.closeButtonShowDialogButtonLocator));
  }

  @Test
  public void waitUntilAbsentElementNewTimeoutTest() {
    PageElementsPageModel page = new PageElementsPageModel(this.getSeleniumTestObject());
    page.open(siteAutomationUrl);
    page.getSeleniumWait().waitForClickableElement(page.showDialog1ButtonLocator).click();
    page.getSeleniumWait().waitForClickableElement(page.closeButtonShowDialogButtonLocator).click();

    // Method Test 
    Assert.assertTrue(page.getSeleniumWait().waitUntilAbsentElement(page.closeButtonShowDialogButtonLocator, 3000, 1000));
  }

  @Test
  public void waitUntilAbsentElementFalseTest() {
    PageElementsPageModel page = new PageElementsPageModel(this.getSeleniumTestObject());
    page.open(siteAutomationUrl);
    page.getSeleniumWait().waitForClickableElement(page.showDialog1ButtonLocator).click();

    // Method Test 
    Assert.assertFalse(page.getSeleniumWait().waitUntilAbsentElement(page.closeButtonShowDialogButtonLocator, 3000, 1000));
  }

  @Test
  public void waitForElementsTest() {
    PageElementsPageModel page = new PageElementsPageModel(this.getSeleniumTestObject());
    page.open(siteAutomationUrl);

    // Method Test 
    Assert.assertTrue(!page.getSeleniumWait().waitForElements(page.showDialog1ButtonLocator).isEmpty());
  }

  @Test
  public void waitForElementsNewTimeoutTest() {
    PageElementsPageModel page = new PageElementsPageModel(this.getSeleniumTestObject());
    page.open(siteAutomationUrl);

    // Method Test 
    Assert.assertTrue(!page.getSeleniumWait().waitForElements(page.showDialog1ButtonLocator, 3000, 100).isEmpty());
  }

  @Test
  public void waitForElementsNewWaitDriverTest() {
    PageElementsPageModel page = new PageElementsPageModel(this.getSeleniumTestObject());
    page.open(siteAutomationUrl);
    WebDriverWait wait = new WebDriverWait(this.getWebDriver(), 3000, 1000);
    // Method Test 
    Assert.assertTrue(!page.getSeleniumWait().waitForElements(page.showDialog1ButtonLocator, wait).isEmpty());
  }

  @Test
  public void waitForExactTextTest() {
    PageElementsPageModel page = new PageElementsPageModel(this.getSeleniumTestObject());
    page.open(siteAutomationUrl);
    // Method Test 
    Assert.assertNotNull(page.getSeleniumWait().waitForExactText(page.showDialog1ButtonLocator, "Show dialog"));
  }

  @Test
  public void waitUntilExactTextTest() {
    PageElementsPageModel page = new PageElementsPageModel(this.getSeleniumTestObject());
    page.open(siteAutomationUrl);
    // Method Test 
    Assert.assertTrue(page.getSeleniumWait().waitUntilExactText(page.showDialog1ButtonLocator, "Show dialog"));
  }

  @Test
  public void waitUntilExactTextNewTimeOutTest() {
    PageElementsPageModel page = new PageElementsPageModel(this.getSeleniumTestObject());
    page.open(siteAutomationUrl);
    // Method Test 
    Assert.assertTrue(page.getSeleniumWait().waitUntilExactText(page.showDialog1ButtonLocator, "Show dialog", 3000, 1000));
  }

  @Test
  public void waitUntilExactTextFalseTest() {
    PageElementsPageModel page = new PageElementsPageModel(this.getSeleniumTestObject());
    page.open(siteAutomationUrl);
    // Method Test 
    Assert.assertFalse(page.getSeleniumWait().waitUntilExactText(page.showDialog1ButtonLocator, "Show", 3000, 1000));
  }

  @Test
  public void waitForContainsTextTest() {
    PageElementsPageModel page = new PageElementsPageModel(this.getSeleniumTestObject());
    page.open(siteAutomationUrl);
    // Method Test 
    Assert.assertNotNull(page.getSeleniumWait().waitForContainsText(page.showDialog1ButtonLocator, "Show"));
  }

  @Test
  public void waitUntilContainsTextTest() {
    PageElementsPageModel page = new PageElementsPageModel(this.getSeleniumTestObject());
    page.open(siteAutomationUrl);
    // Method Test 
    Assert.assertTrue(page.getSeleniumWait().waitUntilContainsText(page.showDialog1ButtonLocator, "Show"));
  }

  @Test
  public void waitUntilContainsTextNewTimeoutTest() {
    PageElementsPageModel page = new PageElementsPageModel(this.getSeleniumTestObject());
    page.open(siteAutomationUrl);
    // Method Test 
    Assert.assertTrue(page.getSeleniumWait().waitUntilContainsText(page.showDialog1ButtonLocator, "Show", 3000, 1000));
  }

  @Test
  public void waitUntilContainsTextFalseTest() {
    PageElementsPageModel page = new PageElementsPageModel(this.getSeleniumTestObject());
    page.open(siteAutomationUrl);
    // Method Test 
    Assert.assertFalse(page.getSeleniumWait().waitUntilContainsText(page.showDialog1ButtonLocator, "p", 3000, 1000));
  }

  @Test
  public void waitForAttributeTextEqualsTest() {
    PageElementsPageModel page = new PageElementsPageModel(this.getSeleniumTestObject());
    page.open(siteAutomationUrl);
    // Method Test 
    Assert.assertNotNull(page.getSeleniumWait().waitForAttributeTextEquals(page.showDialog1ButtonLocator, "onclick", "ShowProgressAnimation();"));
  }

  @Test
  public void waitForAttributeTextNewTimeoutEqualsTest() {
    PageElementsPageModel page = new PageElementsPageModel(this.getSeleniumTestObject());
    page.open(siteAutomationUrl);
    // Method Test 
    Assert.assertNotNull(page.getSeleniumWait().waitForAttributeTextEquals(page.showDialog1ButtonLocator, "onclick", "ShowProgressAnimation();", 3000, 1000));
  }
  
  @Test
  public void waitUntilAttributeTextEqualsTest() {
    PageElementsPageModel page = new PageElementsPageModel(this.getSeleniumTestObject());
    page.open(siteAutomationUrl);
    // Method Test 
    Assert.assertTrue(page.getSeleniumWait().waitUntilAttributeTextEquals(page.showDialog1ButtonLocator, "onclick", "ShowProgressAnimation();"));
  }

  @Test
  public void waitUntilAttributeTextEqualsNewTimeoutTest() {
    PageElementsPageModel page = new PageElementsPageModel(this.getSeleniumTestObject());
    page.open(siteAutomationUrl);
    // Method Test 
    Assert.assertTrue(page.getSeleniumWait().waitUntilAttributeTextEquals(page.showDialog1ButtonLocator, "onclick", "ShowProgressAnimation();", 3000, 1000));
  }

  @Test
  public void waitUntilAttributeTextEqualsFalseTest() {
    PageElementsPageModel page = new PageElementsPageModel(this.getSeleniumTestObject());
    page.open(siteAutomationUrl);
    // Method Test 
    Assert.assertFalse(page.getSeleniumWait().waitUntilAttributeTextEquals(page.showDialog1ButtonLocator, "onclick", "ShowProgress", 3000, 1000));
  }

  @Test
  public void waitUntilAttributeTextContainsTest() {
    PageElementsPageModel page = new PageElementsPageModel(this.getSeleniumTestObject());
    page.open(siteAutomationUrl);
    // Method Test 
    Assert.assertTrue(page.getSeleniumWait().waitUntilAttributeTextContains(page.showDialog1ButtonLocator, "onclick", "ShowProgress"));
  }

  @Test
  public void waitUntilAttributeTextContainsNewTimeoutTest() {
    PageElementsPageModel page = new PageElementsPageModel(this.getSeleniumTestObject());
    page.open(siteAutomationUrl);
    // Method Test 
    Assert.assertTrue(page.getSeleniumWait().waitUntilAttributeTextContains(page.showDialog1ButtonLocator, "onclick", "ShowProgress", 3000, 1000));
  }

  @Test
  public void waitUntilAttributeTextContainsFalseTest() {
    PageElementsPageModel page = new PageElementsPageModel(this.getSeleniumTestObject());
    page.open(siteAutomationUrl);
    // Method Test 
    Assert.assertFalse(page.getSeleniumWait().waitUntilAttributeTextContains(page.showDialog1ButtonLocator, "onclick", "z", 3000, 1000));
  }

  @Test
  public void waitForClickableElementAndScrollIntoViewTest() {
    PageElementsPageModel page = new PageElementsPageModel(this.getSeleniumTestObject());
    page.open(siteAutomationUrl);
    // Method Test 
    Assert.assertNotNull(page.getSeleniumWait().waitForClickableElementAndScrollIntoView(page.uploadImageButtonLocator));
  }

  @Test
  public void waitForClickableElementAndScrollIntoViewNewTimeoutTest() {
    PageElementsPageModel page = new PageElementsPageModel(this.getSeleniumTestObject());
    page.open(siteAutomationUrl);
    // Method Test 
    Assert.assertNotNull(page.getSeleniumWait().waitForClickableElementAndScrollIntoView(page.uploadImageButtonLocator, 3000, 1000));
  }

  @Test
  public void waitUntilClickableElementAndScrollIntoViewTest() {
    PageElementsPageModel page = new PageElementsPageModel(this.getSeleniumTestObject());
    page.open(siteAutomationUrl);
    // Method Test 
    Assert.assertTrue(page.getSeleniumWait().waitUntilClickableElementAndScrollIntoView(page.uploadImageButtonLocator));
  }
  
  @Test
  public void waitUntilClickableElementAndScrollIntoViewNewTimeOutTest() {
    PageElementsPageModel page = new PageElementsPageModel(this.getSeleniumTestObject());
    page.open(siteAutomationUrl);
    // Method Test 
    Assert.assertTrue(page.getSeleniumWait().waitUntilClickableElementAndScrollIntoView(page.uploadImageButtonLocator, 3000, 1000));
  }

  @Test
  public void waitUntilClickableElementTest() {
    PageElementsPageModel page = new PageElementsPageModel(this.getSeleniumTestObject());
    page.open(siteAutomationUrl);
    page.getSeleniumWait().waitForClickableElement(page.showDialog1ButtonLocator).click();
    // Method Test 
    Assert.assertTrue(page.getSeleniumWait().waitUntilClickableElement(page.closeButtonShowDialogButtonLocator));
  }
  
  @Test
  public void waitUntilClickableElementNewTimeoutTest() {
    PageElementsPageModel page = new PageElementsPageModel(this.getSeleniumTestObject());
    page.open(siteAutomationUrl);
    page.getSeleniumWait().waitForClickableElement(page.showDialog1ButtonLocator).click();
    // Method Test 
    Assert.assertTrue(page.getSeleniumWait().waitUntilClickableElement(page.closeButtonShowDialogButtonLocator, 3000, 1000));
  }

  @Test
  public void waitUntilClickableElementFalseTest() {
    PageElementsPageModel page = new PageElementsPageModel(this.getSeleniumTestObject());
    page.open(siteAutomationUrl);
    // Method Test 
    Assert.assertFalse(page.getSeleniumWait().waitUntilClickableElement(page.closeButtonShowDialogButtonLocator, 3000, 1000));
  }

  @Test
  public void waitForClickableElementTest() {
    PageElementsPageModel page = new PageElementsPageModel(this.getSeleniumTestObject());
    page.open(siteAutomationUrl);
    page.getSeleniumWait().waitForClickableElement(page.showDialog1ButtonLocator).click();
    // Method Test 
    Assert.assertNotNull(page.getSeleniumWait().waitForClickableElement(page.closeButtonShowDialogButtonLocator));
  }
  
  @Test
  public void waitForClickableElementNewTimeoutTest() {
    PageElementsPageModel page = new PageElementsPageModel(this.getSeleniumTestObject());
    page.open(siteAutomationUrl);
    page.getSeleniumWait().waitForClickableElement(page.showDialog1ButtonLocator).click();
    // Method Test 
    Assert.assertNotNull(page.getSeleniumWait().waitForClickableElement(page.closeButtonShowDialogButtonLocator, 3000, 1000));
  }

  @Test
  public void waitUntilIframeToLoadTest() {
    IFramePageModel page = new IFramePageModel(this.getSeleniumTestObject());
    page.open(siteIframeAutomationUrl);
    
    // Method Test 
    Assert.assertTrue(page.getSeleniumWait().waitUntilIframeToLoad(page.iframeLocator));
  }

  @Test
  public void waitUntilIframeToLoadNewTimeoutTest() {
    IFramePageModel page = new IFramePageModel(this.getSeleniumTestObject());
    page.open(siteIframeAutomationUrl);

    // Method Test 
    Assert.assertTrue(page.getSeleniumWait().waitUntilIframeToLoad(page.iframeLocator, 3000, 1000));
  }

  @Test
  public void waitUntilIframeToLoadFalseTest() {
    IFramePageModel page = new IFramePageModel(this.getSeleniumTestObject());
    page.open(siteAutomationUrl);

    // Method Test 
    Assert.assertFalse(page.getSeleniumWait().waitUntilIframeToLoad(page.iframeLocator, 3000, 1000));
  }

  @Test
  public void waitForIframeToLoadTest() {
    IFramePageModel page = new IFramePageModel(this.getSeleniumTestObject());
    page.open(siteIframeAutomationUrl);

    // Method Test 
    page.getSeleniumWait().waitForIframeToLoad(page.iframeLocator);
  }

  @Test
  public void waitForIframeToLoadNewTimeoutTest() {
    IFramePageModel page = new IFramePageModel(this.getSeleniumTestObject());
    page.open(siteIframeAutomationUrl);

    // Method Test 
    page.getSeleniumWait().waitForIframeToLoad(page.iframeLocator, 3000, 1000);
  }

  @Test
  public void waitForAbsentElementTest() {
    PageElementsPageModel page = new PageElementsPageModel(this.getSeleniumTestObject());
    page.open(siteAutomationUrl);
    page.getSeleniumWait().waitForClickableElement(page.showDialog1ButtonLocator).click();
    page.getSeleniumWait().waitForClickableElement(page.closeButtonShowDialogButtonLocator).click();

    // Method Test 
    page.getSeleniumWait().waitForAbsentElement(page.closeButtonShowDialogButtonLocator);
  }

  @Test
  public void waitForAbsentElementNewTimeOutTest() {
    PageElementsPageModel page = new PageElementsPageModel(this.getSeleniumTestObject());
    page.open(siteAutomationUrl);
    page.getSeleniumWait().waitForClickableElement(page.showDialog1ButtonLocator).click();
    page.getSeleniumWait().waitForClickableElement(page.closeButtonShowDialogButtonLocator).click();

    // Method Test 
    page.getSeleniumWait().waitForAbsentElement(page.closeButtonShowDialogButtonLocator, 5000, 1000);
  }

  @Test
  public void waitForAbsentElementNegativeTest() {
    PageElementsPageModel page = new PageElementsPageModel(this.getSeleniumTestObject());
    page.open(siteAutomationUrl);
    page.getSeleniumWait().waitForClickableElement(page.showDialog1ButtonLocator).click();

    // Method Test 
    try{
      page.getSeleniumWait().waitForAbsentElement(page.closeButtonShowDialogButtonLocator, 2000, 1000);
      Assert.fail("Element was expected to be visible but was not.");
    } catch (TimeoutException e){}    
  }
}
