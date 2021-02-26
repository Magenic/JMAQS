/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.cucumber.steps;

import com.magenic.jmaqs.base.BaseGenericTest;
import com.magenic.jmaqs.cucumber.ScenarioContext;
import com.magenic.jmaqs.cucumber.unittestpagemodel.DummyBaseGenericStep;
import com.magenic.jmaqs.utilities.helper.TestCategories;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Unit test class for BaseGenericStep class.
 */
public class BaseGenericStepUnitTest extends BaseGenericTest {

    /**
     * Verifies the Logger is not null
     */
    @Test(groups = TestCategories.CUCUMBER)
    public void testGetLogger() {
        DummyBaseGenericStep stepClass = new DummyBaseGenericStep();
        ScenarioContext.put(ScenarioContext.JMAQS_HOLDER, this);
        Assert.assertNotNull(stepClass.getLogger(), "Checking that Logger is not null through BaseGenericStep");
    }

    /**
     * Verifies the Test Object is not null
     */
    @Test(groups = TestCategories.CUCUMBER)
    public void testGetTestObject() {
        DummyBaseGenericStep stepClass = new DummyBaseGenericStep();
        ScenarioContext.put(ScenarioContext.JMAQS_HOLDER, this);
        Assert.assertNotNull(stepClass.getTestObject(), "Checking that Test Object is not null through BaseGenericStep");
    }
}