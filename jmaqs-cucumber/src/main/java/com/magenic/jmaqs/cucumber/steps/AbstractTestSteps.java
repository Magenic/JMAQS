/*
 * Copyright 2020 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.cucumber.steps;

import com.magenic.jmaqs.base.BaseTestObject;
import com.magenic.jmaqs.cucumber.ScenarioContext;
import io.cucumber.java.BeforeStep;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;

public abstract class AbstractTestSteps<T extends BaseTestObject> {

  private ScenarioContext localScenarioContext;

  protected AbstractTestSteps(ScenarioContext context) {
    this.localScenarioContext = context;
  }

  public abstract T getTestObject();

  @BeforeStep
  void beforeStepSetup() {
    throw new UnsupportedOperationException("Not Implemented Yet");
  }

  @AfterMethod
  void teardownAfterScenario() {
    throw new UnsupportedOperationException("Not Implemented Yet");
  }

  abstract void setupBaseTest();

  abstract void teardownBaseTest();

}
