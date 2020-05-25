/*
 * Copyright 2020 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.utilities.helper;

/**
 * Enumeration values for default configuration sections.
 */
public enum ConfigSection {

  /**
   * The default appium capabilities section.
   */
  APPIUM_CAPS_MAQS("AppiumCapsMaqs"),

  /**
   * The default appium maqs section.
   */
  APPIUM_MAQS("AppiumMaqs"),

  /**
   * Database Caps Section.
   */
  DATABASE_CAPS_MAQS("DatabaseCapsMaqs"),

  /**
   * The default database maqs section.
   */
  DATABASE_MAQS("DatabaseMaqs"),

  /**
   * The default email maqs section.
   */
  EMAIL_MAQS("EmailMaqs"),

  /**
   * The default magenic maqs section.
   */
  MAGENIC_MAQS("MagenicMaqs"),

  /**
   * The default remote selenium capabilities section.
   */
  REMOTE_SELENIUM_CAPS_MAQS("RemoteSeleniumCapsMaqs"),

  /**
   * The default selenium maqs section.
   */
  SELENIUM_MAQS("SeleniumMaqs"),

  /**
   * The default web service section.
   */
  WEB_SERVICE_MAQS("WebServiceMaqs");

  private final String sectionName;

  ConfigSection(String sectionName) {
    this.sectionName = sectionName;
  }

  @Override
  public String toString() {
    return sectionName;
  }
}
