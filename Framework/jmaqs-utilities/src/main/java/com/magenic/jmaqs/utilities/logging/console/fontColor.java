package com.magenic.jmaqs.utilities.logging.console;

/**
 * Enumeration of each Ansi code for Foreground Color.
 */
public enum fontColor {
    BLACK("30"),
    RED("31"),
    GREEN("32"),
    YELLOW("33"),
    BLUE("34"),
    MAGENTA("35"),
    CYAN("36"),
    WHITE("37"),
    NONE(""),
    RESET("0");

    private final String code; // Ansi escape code

    /**
     * Enum's constructor. Associates a code to a Foreground Color.
     *
     * @param code to associate
     */
    fontColor(String code) {
        this.code = code;
    }

    /**
     * @return Ansi escape code for that Foreground Color.
     */
    public String getCode() {
        return this.code;
    }

    /**
     * @return The text representation of the enum (its code).
     */
    @Override
    public String toString() {
        return getCode();
    }
}