package com.magenic.jmaqs.cucumber.steps;

import com.magenic.jmaqs.cucumber.ScenarioContext;
import com.magenic.jmaqs.cucumber.unittestpagemodel.DummyBaseSeleniumStep;
import com.magenic.jmaqs.selenium.BaseSeleniumTest;
import com.magenic.jmaqs.utilities.helper.TestCategories;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Unit test class for BaseSeleniumStep class.
 */
public class BaseSeleniumStepUnitTest extends BaseSeleniumTest {

    /**
     * Verifies the Driver is not null
     */
    @Test(groups = TestCategories.CUCUMBER)
    public void testGetDriver() {
        DummyBaseSeleniumStep stepClass = new DummyBaseSeleniumStep();
        ScenarioContext.put(ScenarioContext.JMAQS_HOLDER, this);
        Assert.assertNotNull(stepClass.getDriver(), "Checking that Selenium Driver is not null through BaseSeleniumStep");
    }

    /**
     * Verifies the Test Object is not null
     */
    @Test(groups = TestCategories.CUCUMBER)
    public void testGetTestObject() {
        DummyBaseSeleniumStep stepClass = new DummyBaseSeleniumStep();
        ScenarioContext.put(ScenarioContext.JMAQS_HOLDER, this);
        Assert.assertNotNull(stepClass.getTestObject(), "Checking that Test Object is not null through BaseSeleniumStep");
    }
}