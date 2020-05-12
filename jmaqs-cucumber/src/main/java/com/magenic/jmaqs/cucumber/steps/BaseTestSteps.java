/*
 * Copyright 2020 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.cucumber.steps;

import com.magenic.jmaqs.base.BaseTestObject;
import com.magenic.jmaqs.cucumber.ScenarioContext;

public class BaseTestSteps extends AbstractTestSteps<BaseTestObject> {

  protected BaseTestSteps(ScenarioContext context) {
    super(context);
  }

  @Override
  public BaseTestObject getTestObject() {
    return null;
  }

  @Override
  void setupBaseTest() {

  }

  @Override
  void teardownBaseTest() {

  }
}
