/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

import com.magenic.jmaqs.cucumber.BaseSeleniumCucumber;

import io.cucumber.testng.CucumberOptions;


/**
 * Selenium glue.
 */
@CucumberOptions( glue = {"stepdefs.selenium"}, plugin = {"pretty"}, features = {"src/test/resources/selenium"})
public class TestRunnerSelenium extends BaseSeleniumCucumber {

}