/*
 * Copyright 2020 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.cucumber;

import java.lang.reflect.Method;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import java.util.Arrays;
import org.testng.ITest;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;

public class BaseCucumberTestNG extends AbstractTestNGCucumberTests implements ITest {

  private ThreadLocal<String> testName = new ThreadLocal<>();

  /**
   * Method beforeMethod ...
   *
   * @param method  of type Method
   * @param params  of type Object[]
   * @throws Throwable when
   */
  @BeforeMethod
  public void beforeMethod(Method method, Object[] params, ITestContext context) throws Throwable {
    String testNameTest = params[1].toString().replace("\"", "") + " - " + params[0].toString().replace("\"", "");
    testName.set(testNameTest);
    String name = Arrays.asList(params).toString().replace(" ", "").replace(',', '_')
        .replace("[", "").replace("]", "").replace("\"", "");



    System.out.println("Before method setup done");

  }

  @Override
  public String getTestName() {
    return testName.get();
  }
}
