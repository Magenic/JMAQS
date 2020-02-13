/*
 * Copyright 2020 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.webservices.jdk8;

import com.magenic.jmaqs.base.BaseGenericTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MediaTypeUnitTest extends BaseGenericTest {

  @Test
  public void testTestToStringAppJson() {
    Assert.assertEquals(MediaType.APP_JSON.toString(), MediaType.APP_JSON.getMediaTypeString());
  }

  @Test
  public void testTestToStringAppXml() {
    Assert.assertEquals(MediaType.APP_XML.toString(), MediaType.APP_XML.getMediaTypeString());
  }

  @Test
  public void testTestToStringPlainText() {
    Assert.assertEquals(MediaType.PLAIN_TEXT.toString(), MediaType.PLAIN_TEXT.getMediaTypeString());
  }

  @Test
  public void testTestToStringImagePng() {
    Assert.assertEquals(MediaType.IMAGE_PNG.toString(), MediaType.IMAGE_PNG.getMediaTypeString());
  }

}