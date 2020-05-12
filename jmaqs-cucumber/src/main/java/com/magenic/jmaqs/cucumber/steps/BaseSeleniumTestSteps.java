/*
 * Copyright 2020 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.cucumber.steps;

import com.magenic.jmaqs.cucumber.ScenarioContext;
import com.magenic.jmaqs.selenium.BaseSeleniumTest;
import com.magenic.jmaqs.selenium.SeleniumTestObject;

public class BaseSeleniumTestSteps extends ExtendableTestSteps<BaseSeleniumTest, SeleniumTestObject> {

  protected BaseSeleniumTestSteps(ScenarioContext context) {
    super(context);
  }

  @Override
  void teardownBaseTest() {

  }
}
