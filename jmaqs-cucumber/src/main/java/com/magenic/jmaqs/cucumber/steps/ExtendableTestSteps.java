/*
 * Copyright 2020 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.cucumber.steps;

import java.lang.reflect.Method;
import com.magenic.jmaqs.base.BaseExtendableTest;
import com.magenic.jmaqs.base.BaseTestInterface;
import com.magenic.jmaqs.base.BaseTestObject;
import com.magenic.jmaqs.cucumber.ScenarioContext;
import com.magenic.jmaqs.utilities.logging.Logger;
import com.magenic.jmaqs.utilities.performance.PerfTimerCollection;
import org.testng.ITestContext;

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
    Method method = (Method) ScenarioContext.get("JMAQSTESTMETHOD");
    ITestContext testContext = (ITestContext) ScenarioContext.get("JMAQSTESTCONTEXT");
    O baseTest = new O();
    baseTest.setup(method, testContext);
    ScenarioContext.put("JMAQSTESTOBJECT", baseTest.getTestObject());
    ScenarioContext.put("JMAQSTEST", baseTest);
  }
}
