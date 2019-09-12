/*
 * Copyright 2019 (C) Magenic, All rights Reserved
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

  public T getTestObject() {
    return (T) super.getTestObject();
  }

  @Override
  public void setTestObject(BaseTestObject baseTestObject) {

    if (this.baseTestObjects.putIfAbsent(this.getFullyQualifiedTestClassName(), baseTestObject)
        == null) {
      this.baseTestObjects.replace(this.getFullyQualifiedTestClassName(), baseTestObject);
    }
  }

  @BeforeMethod
  @Override
  public void setup(Method method, ITestContext testContext) throws Exception {
    super.setup(method, testContext);
  }

  protected abstract void createNewTestObject();
}