
/*
 * Copyright 2019 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.base;

import java.lang.reflect.Method;

import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;

/**
 * The type Base extendable test.
 * @param <T> the type parameter
 */
public abstract class BaseExtendableTest<T extends BaseTestObject>
        extends BaseTest {

  /**
   * Instantiates a new Base extendable test.
   */
  protected BaseExtendableTest() {
  }

  /**
   * gets the test object.
   * @return the test object.
   */
  @Override
  @SuppressWarnings("unchecked")
  public T getTestObject() {
    return (T) super.getTestObject();
  }

  /**
   * sets the test object.
   * @param baseTestObject The Base Test Object to use
   */
  @Override
  public void setTestObject(final BaseTestObject baseTestObject) {

    if (this.baseTestObjects.putIfAbsent(this.getFullyQualifiedTestClassName(),
            baseTestObject)
        == null) {
      this.baseTestObjects.replace(this.getFullyQualifiedTestClassName(),
              baseTestObject);
    }
  }

  /**
   * sets up the Base extendable test.
   * @param method      The initial executing Method object
   * @param testContext The initial executing Test Context object
   * @throws Exception if set up fails.
   */

  @BeforeMethod
  @Override
  public void setup(final Method method, final ITestContext testContext)
          throws Exception {
    super.setup(method, testContext);
  }

  /**
   * creates the new test object.
   */
  @Override
  protected abstract void createNewTestObject();
}
