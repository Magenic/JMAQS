package com.magenic.jmaqs.cucumber;

import com.magenic.jmaqs.base.BaseGenericTest;
import com.magenic.jmaqs.base.BaseTest;
import com.magenic.jmaqs.selenium.BaseSeleniumTest;
import com.magenic.jmaqs.utilities.helper.TestCategories;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Unit test class for BaseGenericCucumber class.
 */
public class BaseGenericCucumberUnitTest {

    /**
     * Verifies the specific base test object is a BaseGenericTest Object
     */
    @Test(groups = TestCategories.CUCUMBER)
    public void testCreateSpecificBaseTest() {
        BaseGenericCucumber genericCucumber = new BaseGenericCucumber();
        BaseTest testObject = genericCucumber.createSpecificBaseTest();
        Assert.assertTrue(testObject instanceof BaseGenericTest);
    }
}