package com.magenic.jmaqs.utilities.helper;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

public class PropertyManagerUnitTest {
    /**
     * An invalid key for the property manager to invoke failure
     */
    private static final String INVALID_KEY = "does-not-exist";

    /**
     * A valid key for the property manager
     */
    private static final String VALID_KEY = "does-exist";

    /**
     * The default value provided to the property manager
     */
    private static final String DEFAULT_VALUE = "default";

    /**
     * Valid value for the VALID_KEY property
     */
    private static final String VALID_VALUE = "TestLocation";

    @BeforeTest
    public void setValidSystemProperty() {
        System.setProperty(VALID_KEY, VALID_VALUE);
    }

    @Test
    public void testPropertySetIsMaintained() {
        assertEquals(PropertyManager.get(VALID_KEY), VALID_VALUE);
    }

    @Test
    public void testPropertyNotSetReturnsNull() {
        assertNull(
                PropertyManager.get(INVALID_KEY),
                String.format("key %s was found when it wasn't suppose to be.", INVALID_KEY));
    }

    @Test
    public void testPropertyNotSetReturnsDefault() {
        assertEquals(PropertyManager.get(INVALID_KEY, DEFAULT_VALUE), DEFAULT_VALUE);
    }
}
