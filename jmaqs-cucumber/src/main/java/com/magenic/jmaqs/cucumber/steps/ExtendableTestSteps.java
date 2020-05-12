/*
 * Copyright 2020 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.cucumber.steps;

import java.lang.reflect.Method;
import com.magenic.jmaqs.base.BaseExtendableTest;
import com.magenic.jmaqs.base.BaseTestObject;
import com.magenic.jmaqs.cucumber.ScenarioContext;
import com.magenic.jmaqs.utilities.logging.Logger;
import com.magenic.jmaqs.utilities.performance.PerfTimerCollection;
import org.testng.ITestContext;
import org.testng.ITestResult;

public abstract class ExtendableTestSteps<O extends BaseExtendableTest<T>, T extends BaseTestObject>
    extends AbstractTestSteps<T> {

  public static final String JMAQS_TEST_METHOD = "JMAQSTESTMETHOD";
  public static final String JMAQS_TEST_CONTEXT = "JMAQSTESTCONTEXT";
  public static final String JMAQS_TEST_OBJECT = "JMAQSTESTOBJECT";
  public static final String JMAQS_TEST = "JMAQSTEST";

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
    Method method = (Method) ScenarioContext.get(JMAQS_TEST_METHOD);
    ITestContext testContext = (ITestContext) ScenarioContext.get(JMAQS_TEST_CONTEXT);
    O baseTest = new O();
    baseTest.setup(method, testContext);
    ScenarioContext.put(JMAQS_TEST_OBJECT, baseTest.getTestObject());
    ScenarioContext.put(JMAQS_TEST, baseTest);
  }

  @Override
  void teardownBaseTest(ITestResult result) {
    ((O)ScenarioContext.get(JMAQS_TEST)).setTestResult();
  }
}
