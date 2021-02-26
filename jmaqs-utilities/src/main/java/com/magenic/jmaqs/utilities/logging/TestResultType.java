/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.utilities.logging;

/**
 * The type of result.
 */
public enum TestResultType {
  /**
   * The test passed.
   */
  PASS,

  /**
   * The test failed.
   */
  FAIL,

  /**
   * The test was inconclusive.
   */
  INCONCLUSIVE,

  /**
   * The test was skipped.
   */
  SKIP,

  /**
   * The test had an unexpected result.
   */
  OTHER,
}
