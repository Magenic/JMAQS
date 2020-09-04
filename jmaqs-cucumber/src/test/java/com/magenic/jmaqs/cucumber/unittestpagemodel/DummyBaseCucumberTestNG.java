package com.magenic.jmaqs.cucumber.unittestpagemodel;

import com.magenic.jmaqs.base.BaseGenericTest;
import com.magenic.jmaqs.base.BaseTest;
import com.magenic.jmaqs.cucumber.BaseCucumberTestNG;

/**
 * A Dummy BaseCucumberTestNG class for testing
 */
public class DummyBaseCucumberTestNG extends BaseCucumberTestNG {
    /**
     * Create a test object.
     *
     * @return A generic base test object
     */
    @Override
    public BaseTest createSpecificBaseTest() {
        return new BaseGenericTest();
    }
}
