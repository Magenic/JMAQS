/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.cucumber.steps;

import com.magenic.jmaqs.base.BaseTest;
import com.magenic.jmaqs.base.BaseTestObject;
import com.magenic.jmaqs.cucumber.ScenarioContext;
import com.magenic.jmaqs.utilities.logging.Logger;

/**
 * Base generic cucumber step.
 */
public abstract class BaseGenericStep {

  /**
   * Get the logger.
   *
   * @return The logger
   */
  public Logger getLogger() {
    return ScenarioContext.get(ScenarioContext.JMAQS_HOLDER, BaseTest.class).getLogger();
  }

  /**
   * Get the generic test object.
   *
   * @return The generic test object
   */
  public BaseTestObject getTestObject() {
    return ScenarioContext.get(ScenarioContext.JMAQS_HOLDER, BaseTest.class).getTestObject();
  }
}
