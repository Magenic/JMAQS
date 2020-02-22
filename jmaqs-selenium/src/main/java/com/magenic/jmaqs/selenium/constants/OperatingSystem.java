/*
 * Copyright 2020 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.selenium.constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * The enum Operating system.
 */
public enum OperatingSystem {

  /**
   * None.  Can be used as a default state
   */
  NONE("None", new ArrayList<>(Collections.singletonList("None"))),
  /**
   * Linux Operating System.
   */
  LINUX("Linux", new ArrayList<>(Arrays.asList("nix", "nux", "aix", "ubuntu"))),
  /**
   * Mac Operating System.
   */
  MACOS("Mac OS", new ArrayList<>(Arrays.asList("mac os x", "mac"))),
  /**
   * Windows Operating System.
   */
  WINDOWS("Windows", new ArrayList<>(Arrays.asList("win", "windows")));

  /**
   * The Operating system name.
   */
  private String operatingSystemName;
  /**
   * The Operating system abbreviations.
   */
  private ArrayList<String> operatingSystemAbbreviations;

  /**
   * Instantiates a new Operating system.
   *
   * @param operatingSystemString the operating system string
   * @param osAbbr                the operating system abbreviations
   */
  OperatingSystem(String operatingSystemString, ArrayList<String> osAbbr) {
    this.operatingSystemName = operatingSystemString;
    this.operatingSystemAbbreviations = osAbbr;
  }

  /**
   * Getter for property 'operatingSystemName'.
   *
   * @return Value for property 'operatingSystemName'.
   */
  public String getOperatingSystemName() {
    return operatingSystemName;
  }

  /**
   * Gets operating system.
   *
   * @return the operating system
   */
  public static OperatingSystem getOperatingSystem() {
    String systemProperty = System.getProperty("os.name").toLowerCase();
    return getOperatingSystemWithAbbreviation(systemProperty);
  }

  /**
   * Gets operating system with abbreviation.
   *
   * @param operatingSystemAbbr the operating system abbr
   * @return the operating system with abbreviation
   */
  public static OperatingSystem getOperatingSystemWithAbbreviation(String operatingSystemAbbr) {

    if (LINUX.operatingSystemAbbreviations.stream().anyMatch(operatingSystemAbbr::contains)) {
      return OperatingSystem.LINUX;
    } else if (MACOS.operatingSystemAbbreviations.stream().anyMatch(operatingSystemAbbr::contains)) {
      return OperatingSystem.MACOS;
    } else if (WINDOWS.operatingSystemAbbreviations.stream().anyMatch(operatingSystemAbbr::contains)) {
      return OperatingSystem.WINDOWS;
    } else {
      throw new IllegalArgumentException(String.format("Unknown Operating System detected: %s", operatingSystemAbbr));
    }
  }
}