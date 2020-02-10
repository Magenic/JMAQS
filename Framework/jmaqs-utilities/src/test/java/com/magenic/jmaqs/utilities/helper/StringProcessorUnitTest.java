/*
 * Copyright 2020 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.utilities.helper;

import org.testng.Assert;
import org.testng.annotations.Test;

public class StringProcessorUnitTest {

  @Test
  public void testSafeFormatter() {
    final String successful = StringProcessor.safeFormatter("This is a %s message.", "successful");
    Assert.assertEquals(successful, "This is a successful message.");
  }

  @Test
  public void testSafeFormatterMessage() {
    final String successful = StringProcessor.safeFormatter("This is a message.");
    Assert.assertEquals(successful, "This is a message.");
  }

  @Test
  public void testSafeFormatterMessageNull() {
    final String s = StringProcessor.safeFormatter(null, "Message", "String", "Null");
    Assert.assertEquals(s, "Message: null Arguments: Message String Null ");
  }
}