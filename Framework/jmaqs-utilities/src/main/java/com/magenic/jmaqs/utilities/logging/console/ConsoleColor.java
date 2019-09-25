/*
 * Copyright 2019 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.utilities.logging.console;

/**
 * Enumeration of each Ansi code for Background Color.
 */
public enum ConsoleColor {
    NONE(""),

    // Reset
    RESET("\033[0m"),

    BLACK("\033[40m"),  // BLACK
    RED("\033[41m"),    // RED
    GREEN("\033[42m"),  // GREEN
    YELLOW("\033[43m"), // YELLOW
    BLUE("\033[44m"),   // BLUE
    PURPLE("\033[45m"), // PURPLE
    CYAN("\033[46m"),   // CYAN
    WHITE("\033[47m");  // WHITE

    private final String code; // Ansi escape code

    /**
     * Enum's constructor. Associates a code to a Background Color.
     * @param code to associate
     */
    ConsoleColor(String code) {
        this.code = code;
    }

    /**
     * Gets the Color Code.
     * @return Ansi escape code for that Foreground Color.
     */
    public String getCode() {
        return this.code;
    }

    /**
     * puts the code to a string.
     * @return The text representation of the enum (its code).
     */
    @Override
    public String toString() {
        return getCode();
    }
}
