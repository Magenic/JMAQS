/*
 * Copyright 2019 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.selenium;

import com.magenic.jmaqs.utilities.helper.StringProcessor;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Web element find helper class.
 * 
 * @deprecated {@link WebElementFind} is no longer supported. Use {@link com.magenic.jmaqs.selenium.factories.UIFindFactory UIFindFactory}
 *             to create {@link UIFind UIFind} objects instead.
 */
@Deprecated
public class WebElementFind {

  /**
   * General Find Element.
   * 
   * @param driver
   *          Web Driver
   * @param by
   *          Css Selector
   * @param throwException
   *          optional assert parameter - throws an assert exception if no element is found
   * @return Returns A Web Element
   * 
   */
  public static WebElement findElement(WebDriver driver, By by, boolean throwException) {
    // returns the 1st element in the collection if it is not null or empty
    List<WebElement> elementList = elemList(driver, by, throwException);
    if (elementList == null || elementList.size() == 0) {
      return null;
    }

    return elementList.get(0);
  }

  /**
   * General Find Element.
   * 
   * @param driver
   *          Web Driver
   * @param by
   *          Css Selector
   * @return Returns A Web Element
   */
  public static WebElement findElement(WebDriver driver, By by) {
    return findElement(driver, by, true);
  }

  /**
   * Find a specified Web Element by text.
   * 
   * @param driver
   *          Web Driver
   * @param by
   *          Css Selector
   * @param text
   *          Text to search the Web Element Collection
   * @param throwException
   *          optional assert parameter - throws an exception if no element is found
   * @return Returns a Web Element
   */
  public static WebElement findElementWithText(WebDriver driver, By by, String text,
      boolean throwException) {
    List<WebElement> elementList = elemList(driver, by, throwException);

    if (elementList == null || elementList.size() == 0) {
      return null;
    }

    for (WebElement element : elementList) {
      if (element.getText().equals(text)) {
        return element;
      }
    }

    return null;
  }

  /**
   * Find a specified Web Element by text.
   * 
   * @param driver
   *          Web Driver
   * @param by
   *          Css Selector
   * @param text
   *          Text to search the Web Element Collection
   * @return Returns a Web Element
   */
  public static WebElement findElementWithText(WebDriver driver, By by, String text) {
    return findElementWithText(driver, by, text, true);

  }

  /**
   * Finds the index of the specified Web Element.
   * 
   * @param driver
   *          Web Driver
   * @param by
   *          Css Selector
   * @param text
   *          Text to search the Web Element Collection
   * @param throwException
   *          optional assert parameter - throws an exception if no element is found
   * @return Returns the index of a Web Element
   */
  public static int findIndexOfElementWithText(WebDriver driver, By by, String text,
      boolean throwException) {
    // return -1 if index not found.. assert a fail if true
    List<WebElement> elementList = elemList(driver, by, throwException);
    int index = -1;

    if (elementList == null || (elementList.size() == 0)) {
      return index;
    }

    for (int i = 0; i < elementList.size(); i++) {
      if (elementList.get(i).getText().equals(text)) {
        return i;
      }
    }

    return index;
  }

  /**
   * Finds the index of the specified Web Element.
   * 
   * @param driver
   *          Web Driver
   * @param by
   *          Css Selector
   * @param text
   *          Text to search the Web Element Collection
   * @return Returns the index of a Web Element
   */
  public static int findIndexOfElementWithText(WebDriver driver, By by, String text) {
    return findIndexOfElementWithText(driver, by, text, true);
  }

  /**
   * Find the Index of the Specified Web Element Collection.
   * 
   * @param list
   *          ICollection of Web Elements
   * @param text
   *          Text to search the Web Element Collection
   * @param throwException
   *          optional assert parameter - throws an exception if no element is found
   * @return Returns the index of the Web Element in the inputted WebElement Collection
   */
  public static int findIndexOfElementWithText(List<WebElement> list, String text,
      boolean throwException) {
    int index = -1;

    if (list == null) {
      if (throwException) {
        throw new NotFoundException(StringProcessor
                  .safeFormatter("Element Collection is null"));
      } else {
        return index;
      }
    }

    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).toString().equals(text)) {
        return i;
      }
    }

    if (!throwException) {
      return index;
    } 
    
    throw new NotFoundException(StringProcessor
                .safeFormatter("Text did not match any element in collection %s", list.toString()));
  }

  /**
   * Find the Index of the Specified Web Element Collection.
   * 
   * @param list
   *          ICollection of Web Elements
   * @param text
   *          Text to search the Web Element Collection
   * @return Returns the index of the Web Element in the inputed WebElement Collection
   */
  public static int findIndexOfElementWithText(List<WebElement> list, String text) {
    return findIndexOfElementWithText(list, text, true);

  }

  /**
   * Function to get the Web Collection.
   * 
   * @param driver
   *          Web Driver
   * @param by
   *          Css Selector
   * @param throwException
   *          optional assert parameter - throws an exception if no element is found
   * @return Returns a Web Element Collection
   */
  private static List<WebElement> elemList(WebDriver driver, By by, boolean throwException) {
    List<WebElement> elems = driver.findElements(by);

    if (elems.size() > 0 || !throwException) {
      return elems;
    }

    throw new NotFoundException(
        StringProcessor.safeFormatter("No result found for By %s", by.toString()));
  }

}
