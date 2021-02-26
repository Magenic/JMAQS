/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.utilities;

import com.magenic.jmaqs.utilities.helper.Config;
import com.magenic.jmaqs.utilities.helper.ListProcessor;
import com.magenic.jmaqs.utilities.helper.StringProcessor;
import com.magenic.jmaqs.utilities.helper.TestCategories;

import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Unit tests for the ListProcessor class.
 */
public class ListProcessorUnitTest {
  /**
   * Unit Test for creating a comma delimited string.
   */

  @Test(groups = TestCategories.UTILITIES)
  public void createCommaDelimitedStringTest() {
    ArrayList<String> stringList = new ArrayList<String>();

    stringList.add("Maine");
    stringList.add("Massachusetts");
    stringList.add("New Hampshire");
    stringList.add("Connecticut");
    stringList.add("Rhode Island");
    stringList.add("Vermont");
    String expectedText = "Maine, Massachusetts, New Hampshire, Connecticut, Rhode Island, Vermont";

    String actualText = ListProcessor.createCommaDelimitedString(stringList, false);

    if (!expectedText.equals(actualText)) {
      Assert.fail(StringProcessor
          .safeFormatter("Expected string %s does not match Actual string %s", expectedText,
              actualText));
    }
  }

  /**
   * Unit Test for creating a sorted comma delimited string.
   */

  @Test(groups = TestCategories.UTILITIES)
  public void createSortedCommaDelimitedStringTest() {
    ArrayList<String> stringList = new ArrayList<String>();
    stringList.add("Maine");
    stringList.add("Massachusetts");
    stringList.add("New Hampshire");
    stringList.add("Connecticut");
    stringList.add("Rhode Island");
    stringList.add("Vermont");
    String expectedText = "Connecticut, Maine, Massachusetts, New Hampshire, Rhode Island, Vermont";

    String actualText = ListProcessor.createCommaDelimitedString(stringList, true);

    if (!expectedText.equals(actualText)) {
      Assert.fail(StringProcessor
          .safeFormatter("Expected string %s does not match Actual string %s", expectedText,
              actualText));
    }
  }

  /**
   * Unit Test for comparing two lists of strings.
   */

  @Test(groups = TestCategories.UTILITIES)
  public void listOfStringsComparerTest() {

    final StringBuilder results = new StringBuilder();
    ArrayList<String> expectedList = new ArrayList<String>();
    final ArrayList<String> actualList = new ArrayList<String>();
    expectedList.add("Maine");
    expectedList.add("Massachusetts");
    expectedList.add("New Hampshire");
    expectedList.add("Connecticut");
    expectedList.add("Rhode Island");
    expectedList.add("Vermont");

    actualList.add("Massachusetts");
    actualList.add("Connecticut");
    actualList.add("Rhode Island");
    actualList.add("Vermont");
    actualList.add("Maine");
    actualList.add("New Hampshire");

    ListProcessor.listOfStringsComparer(expectedList, actualList, results, false);

    if (results.length() > 0) {
      Assert.fail(StringProcessor.safeFormatter("%s%s", results.toString(), Config.NEW_LINE));
    }
  }

  /**
   * Unit Test for comparing two lists of strings by order.
   */
  @Test(groups = TestCategories.UTILITIES)
  public void listOfStringsComparerByOrderTest() {
    final StringBuilder results = new StringBuilder();
    ArrayList<String> expectedList = new ArrayList<String>();
    final ArrayList<String> actualList = new ArrayList<String>();
    expectedList.add("Maine");
    expectedList.add("Massachusetts");
    expectedList.add("New Hampshire");
    expectedList.add("Connecticut");
    expectedList.add("Rhode Island");
    expectedList.add("Vermont");

    actualList.add("Maine");
    actualList.add("Massachusetts");
    actualList.add("New Hampshire");
    actualList.add("Connecticut");
    actualList.add("Rhode Island");
    actualList.add("Vermont");

    ListProcessor.listOfStringsComparer(expectedList, actualList, results, true);

    if (results.length() > 0) {
      Assert.fail(StringProcessor.safeFormatter("%s%s", results.toString(), Config.NEW_LINE));
    }
  }

  /**
   * Verify that listOfStringsComparer handles lists of unequal length as expected.
   */
  @Test(groups = TestCategories.UTILITIES)
  public void listOfStringsComparerUnequalLengths() {
    final StringBuilder results = new StringBuilder();
    ArrayList<String> expectedList = new ArrayList<String>();
    ArrayList<String> actualList = new ArrayList<String>();
    expectedList.add("A");
    expectedList.add("B");

    actualList.add("A");
    boolean isEqual = ListProcessor.listOfStringsComparer(expectedList, actualList, results, true);
    Assert.assertTrue(results.toString().contains("The following lists are not the same size:"));
    Assert.assertFalse(isEqual);
  }

  /**
   * Verify that ListOfStringsComparer handles not finding an item in the expected list correctly.
   */
  @Test(groups = TestCategories.UTILITIES)
  public void listOfStringComparerItemNotFound() {
    final StringBuilder results = new StringBuilder();
    ArrayList<String> expectedList = new ArrayList<String>();
    ArrayList<String> actualList = new ArrayList<String>();
    expectedList.add("A");
    expectedList.add("B");

    actualList.add("A");
    actualList.add("C");
    boolean isEqual = ListProcessor.listOfStringsComparer(expectedList, actualList, results, false);
    Assert.assertTrue(
        results.toString().contains("[C] was found in the ArrayList but was not expected"));
    Assert.assertFalse(isEqual);
  }

  /**
   * Verify that listOfStringsComparer handles inequality between lists as expected.
   */
  @Test(groups = TestCategories.UTILITIES)
  public void listOfStringsComparerItemNotMatching() {
    final StringBuilder results = new StringBuilder();
    ArrayList<String> expectedList = new ArrayList<String>();
    ArrayList<String> actualList = new ArrayList<String>();
    expectedList.add("A");
    expectedList.add("B");

    actualList.add("A");
    actualList.add("C");
    boolean isEqual = ListProcessor.listOfStringsComparer(expectedList, actualList, results, true);
    Assert.assertTrue(results.toString().contains("Expected [B] but found [C]"));
    Assert.assertFalse(isEqual);
  }
}
