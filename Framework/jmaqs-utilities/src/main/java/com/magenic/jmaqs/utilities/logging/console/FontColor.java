/*
 * Copyright 2018 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.utilities.logging.console;

public enum FontColor {
    /**
     * Code to Reset the Color
     */
    Reset("0"),
    /**
     * Code to clear the ANSI code
     */
    None(""),
    /**
     * Code for Black Font Color
     */
    Black("30"),
    /**
     * Code for Red Font Color
     */
    Red("31"),
    /**
     * Code for Green Font Color
     */
    Green("32"),
    /**
     * Code for Yellow Font Color
     */
    Yellow("33"),
    /**
     * Code for Blue Font Color
     */
    Blue("34"),
    /**
     * Code for Magenta Font Color
     */
    Magenta("35"),
    /**
     * Code for Cyan Font Color
     */
    Cyan("36"),
    /**
     * Code for White Font Color
     */
    White("37"),
    /**
     * Code for Bright Black Font Color
     */
    Bright_Black("90"),
    /**
     * Code for Bright Red Font Color
     */
    Bright_Red("91"),
    /**
     * Code for Bright Green Font Color
     */
    Bright_Green("92"),
    /**
     * Code for Bright Yellow Font Color
     */
    Bright_Yellow("93"),
    /**
     * Code for Bright Blue Font Color
     */
    Bright_Blue("94"),
    /**
     * Code for Bright Magenta Font Color
     */
    Bright_Magenta("95"),
    /**
     * Code for Bright Cyan Font Color
     */
    Bright_Cyan("96"),
    /**
     * Code for Bright White Font Color
     */
    Bright_White("97");

    /**
     * Font to be set as
     */
    private final String font; // Ansi escape code

    /**
     * Enum's constructor. Associates a code to a Foreground Color.
     * @param code to associate
     */
    FontColor(String code) {
        this.font = code;
    }

    /**
     * Gets the Font Color code.
     * @return Ansi escape code for that Foreground Color.
     */
    public String getCode() {
        return this.font;
    }

    /**
     * Gets the Font Color code in string format.
     * @return The text representation of the enum (its code).
     */
    @Override
    public String toString() {
        return getCode();
    }
}