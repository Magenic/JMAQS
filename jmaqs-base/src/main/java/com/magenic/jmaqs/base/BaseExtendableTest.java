/*
 * Copyright 2020 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.base;

import java.lang.reflect.Method;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;

/**
 * The type Base extendable test.
 *
 * @param <T> the type parameter
 */
public abstract class BaseExtendableTest<T extends BaseTestObject> extends BaseTest {

  /**
   * Instantiates a new Base extendable test.
   */
  protected BaseExtendableTest() {
  }

  @Override
  @SuppressWarnings("unchecked")
  public T getTestObject() {
    return (T) super.getTestObject();
  }

  @BeforeMethod
  @Override
  public void setup(Method method, ITestContext testContext) {
    super.setup(method, testContext);
  }

  @Override
  protected abstract void createNewTestObject();
}
