/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.utilities.helper.functionalinterfaces;

/**
 * No param, no output functional interface.
 */
@FunctionalInterface
public interface Action {

  /**
   * invokes the functional interface when called.
   */
  void invoke();
}
