/*
 * Copyright 2020 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.utilities.helper;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Contains methods for processing lists.
 */

public class ListProcessor {
  /**
   * Create a comma delimited string from a ArrayList of strings.
   *
   * @param stringList ArrayList of strings
   * @param sort       True to create an alphabetically sorted comma delimited string False to create comma
   *                   delimited string in the order of elements in the arraylist
   * @return a comma delimited string
   */

  public static String createCommaDelimitedString(ArrayList<String> stringList, boolean sort) {
    boolean firstElement = true;
    String commaDelimitedString = "";

    if (sort) {

      Collections.sort(stringList);
    }

    for (String text : stringList) {
      if (firstElement) {
        commaDelimitedString = text;
        firstElement = false;
      } else {
        commaDelimitedString += StringProcessor.safeFormatter(", %s", text);
      }
    }

    return commaDelimitedString;
  }

  /**
   * Compares two lists to see if they contain the same values.
   *
   * @param expectedList First ArrayList of strings to compare
   * @param actualList   Second ArrayList of strings to compare
   * @param results      StringBuilder to hold failed results
   * @param verifyOrder  If True, verify the two lists have values in the same order and If False, verify the
   *                     same elements are in both lists
   * @return True if the lists are the same
   */
  public static boolean listOfStringsComparer(ArrayList<String> expectedList,
      ArrayList<String> actualList, StringBuilder results, boolean verifyOrder) {
    if (expectedList.size() != actualList.size()) {
      results.append(StringProcessor.safeFormatter(
          "The following lists are not the same size: Expected %s [%s] %s and got %s [%s]",
          Config.NEW_LINE, createCommaDelimitedString(expectedList, false), Config.NEW_LINE,
          Config.NEW_LINE, createCommaDelimitedString(actualList, false)));
    }

    // Clone the first ArrayList
    ArrayList<String> clonedList = new ArrayList<String>(expectedList.size());

    for (String text : expectedList) {
      clonedList.add(text);
    }

    for (int i = 0; i < actualList.size(); i++) {
      String actualValue = actualList.get(i);
      if (!verifyOrder) {
        if (!clonedList.contains(actualValue)) {
          results.append(StringProcessor
              .safeFormatter("[%s] was found in the ArrayList but was not expected%s",
                  actualValue, Config.NEW_LINE));
        } else {
          // Remove these values from the ArrayList to make sure
          // duplicates are handled correctly
          clonedList.remove(clonedList.indexOf(actualValue));
        }
      } else if (clonedList.get(i) == null || !clonedList.get(i).equals(actualValue)) {
        results.append(StringProcessor
            .safeFormatter("Expected [%s] but found [%s]%s", clonedList.get(i), actualValue,
                Config.NEW_LINE));
      }
    }

    return results.length() == 0;
  }
}
