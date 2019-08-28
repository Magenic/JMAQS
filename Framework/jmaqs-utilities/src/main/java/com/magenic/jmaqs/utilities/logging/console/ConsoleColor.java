/*
 * Copyright 2018 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.utilities.logging.console;

/**
 * Enumeration of each Ansi code for Background Color.
 */
public enum ConsoleColor {
    /**
     * Code to Reset the Color
     */
    Reset("0"),
    /**
     * Code to clear the ANSI code
     */
    None(""),
    /**
     * Code for Black Background Color
     */
    Black("40"),
    /**
     * Code for Red Background Color
     */
    Red("41"),
    /**
     * Code for Green Background Color
     */
    Green("42"),
    /**
     * Code for Yellow Background Color
     */
    Yellow("43"),
    /**
     * Code for Blue Background Color
     */
    Blue("44"),
    /**
     * Code for Magenta Background Color
     */
    Magenta("45"),
    /**
     * Code for Cyan Background Color
     */
    Cyan("46"),
    /**
     * Code for White Background Color
     */
    White("47"),
    /**
     * Code for Bright Black Background Color
     */
    Bright_Black("100"),
    /**
     * Code for Bright Red Background Color
     */
    Bright_Red("101"),
    /**
     * Code for Bright Green Background Color
     */
    Bright_Green("102"),
    /**
     * Code for Bright Yellow Background Color
     */
    Bright_Yellow("103"),
    /**
     * Code for Bright Blue Background Color
     */
    Bright_Blue("104"),
    /**
     * Code for Bright Magenta Background Color
     */
    Bright_Magenta("105"),
    /**
     * Code for Bright Cyan Background Color
     */
    Bright_Cyan("106"),
    /**
     * Code for Bright White Background Color
     */
    Bright_White("107");

    /**
     * the cosole to set the color to
     */
    private final String console; // Ansi escape code

    /**
     * Enum's constructor. Associates a code to a Background Color.
     * @param code to associate
     */
    ConsoleColor(String code) {
        this.console = code;
    }

    /**
     * Gets the Console Color code.
     * @return Ansi escape code for that Foreground Color.
     */
    public String getCode() {
        return console;
    }

    /**
     * Gets the Console Color code in string format.
     * @return The text representation of the enum (its code).
     */
    @Override
    public String toString() {
        return getCode();
    }
}