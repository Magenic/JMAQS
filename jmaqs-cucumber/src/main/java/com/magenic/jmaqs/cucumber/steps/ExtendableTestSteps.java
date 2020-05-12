/*
 * Copyright 2020 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.cucumber.steps;

import com.magenic.jmaqs.base.BaseExtendableTest;
import com.magenic.jmaqs.base.BaseTestObject;
import com.magenic.jmaqs.cucumber.ScenarioContext;
import com.magenic.jmaqs.utilities.logging.Logger;
import com.magenic.jmaqs.utilities.performance.PerfTimerCollection;

public abstract class ExtendableTestSteps<O extends BaseExtendableTest<T>, T extends BaseTestObject>
    extends AbstractTestSteps<T> {

  protected ExtendableTestSteps(ScenarioContext context) {
    super(context);
  }

  @Override
  public T getTestObject() {
    return (T) ScenarioContext.get("JMAQSTESTOBJECT");
  }

  public Logger getLogger() {
    return getTestObject().getLog();
  }

  protected PerfTimerCollection getPerfTimerCollection() {
    return this.getTestObject().getPerfTimerCollection();
  }

  @Override
  void setupBaseTest() {
    O baseTest = new O();
    baseTest.setup();
  }
}
