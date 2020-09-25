package stepdefs.selenium;

import com.magenic.jmaqs.cucumber.steps.BaseSeleniumStep;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

public class SeleniumSteps2  extends BaseSeleniumStep {

    @When("Selenium Step2")
    public void Step2() throws Throwable {
        this.getLogger().logMessage("Step2");
    }

}
