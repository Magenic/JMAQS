package com.magenic.jmaqs.utilities.logging.ConsoleColors;

/**
 * Enumeration of each Ansi code for Background Color.
 */
public enum ConsoleColor {
    BLACK("40"),
    RED("41"),
    GREEN("42"),
    YELLOW("43"),
    BLUE("44"),
    MAGENTA("45"),
    CYAN("46"),
    WHITE("47"),
    NONE("");

    private final String code; // Ansi escape code

    /**
     * Enum's constructor. Associates a code to a Background Color.
     *
     * @param code to associate
     */
    ConsoleColor(String code) {
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