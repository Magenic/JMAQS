/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package stepdefs.selenium;

import com.magenic.jmaqs.cucumber.steps.BaseSeleniumStep;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SeleniumSteps3 extends BaseSeleniumStep {

    @Then("Selenium Step3")
    public void Step3() throws Throwable {
        this.getLogger().logMessage("Step3");
    }
}
