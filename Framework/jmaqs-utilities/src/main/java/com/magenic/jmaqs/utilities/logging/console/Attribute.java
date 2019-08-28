package com.magenic.jmaqs.utilities.logging.console;

public enum Attribute {
    /**
     * Code to clear the ANSI code
     */
    None(""),
    /**
     * Font is Clear
     */
    Clear("0"),
    /**
     * Font is Bold
     */
    Bold("1"),
    /**
     * Font is Light
     */
    Light("1"),
    /**
     * Font is Dark
     */
    Dark("2"),
    /**
     * Font is Italic
     */
    Italic("3"),
    /**
     * Font is Underlined
     */
    Underline("4"),
    /**
     * Font is Reversed
     */
    Reverse("7"),
    /**
     * Font is Hidden
     */
    Hidden("8");

    /**
     * attribute to be used
     */
    private final String att; // Ansi escape code

    /**
     * Enum's constructor. Associates a code to a Attribute.
     * @param code to associate
     */
    Attribute(String code) {
        att = code;
    }

    /**
     * Gets the Attribute code.
     * @return Ansi escape code for that attribute.
     */
    public String getCode() {
        return att;
    }

    /**
     * Gets the Attribute code in string format.
     * @return The text representation of the enum (its code).
     */
    @Override
    public String toString() {
        return getCode();
    }
}
