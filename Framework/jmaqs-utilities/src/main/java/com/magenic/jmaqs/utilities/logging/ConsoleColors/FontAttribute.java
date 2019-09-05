package com.magenic.jmaqs.utilities.logging.ConsoleColors;

/**
 * Enumeration of each Ansi code for Attribute.
 */
public enum FontAttribute {
    CLEAR("0"),
    BOLD("1"),
    LIGHT("1"),
    DARK("2"),
    UNDERLINE("4"),
    REVERSE("7"),
    HIDDEN("8"),
    NONE(""),
    RESET("0");

    private final String code; // Ansi escape code

    /**
     * Enum's constructor. Associates a code to a Attribute.
     *
     * @param code to associate
     */
    FontAttribute(String code) {
        this.code = code;
    }

    /**
     * @return Ansi escape code for that attribute.
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