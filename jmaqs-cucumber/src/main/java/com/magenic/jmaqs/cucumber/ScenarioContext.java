/*
 * Copyright 2020 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.cucumber;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Scenario context.
 */
public class ScenarioContext {

  /**
   * Field context
   */
  private static ThreadLocal<Map<String, Object>> context = new ThreadLocal<>();

  /**
   * Get object.
   *
   * @param name the name
   * @return the object
   */
  public static Object get(final String name) {
    return context.get().get(name);
  }

  /**
   * Get t.
   *
   * @param <T>  the type parameter
   * @param type the type
   * @return the t
   */
  public static <T> T get(final Class<T> type) {
    final Object obj = get(type.toString());
    return (T) obj;
  }

  /**
   * Remove.
   *
   * @param <T>  the type parameter
   * @param type the type
   */
  public static <T> void remove(final Class<T> type) {
    context.get().remove(type.toString());
  }

  /**
   * Get t.
   *
   * @param <T>  the type parameter
   * @param name the name
   * @param type the type
   * @return the t
   */
  @SuppressWarnings("unchecked")
  public static <T> T get(final String name, final Class<T> type) {
    final Object obj = get(name);
    return (T) obj;
  }

  /**
   * Put.
   *
   * @param name the name
   * @param obj  the obj
   */
  public static void put(final String name, final Object obj) {
    context.get().put(name, obj);
  }

  /**
   * Put.
   *
   * @param <T>  the type parameter
   * @param type the type
   * @param obj  the obj
   */
  public static <T> void put(final Class<T> type, final Object obj) {
    context.get().put(type.toString(), obj);
  }

  /**
   * Put.
   *
   * @param <T>    the type parameter
   * @param object the object
   * @param type   the type
   */
  public static <T> void put(final Object object, final Class<T> type) {
    put(type, object);
  }

  /**
   * Init.
   */
  public static void init() {
    context.set(new HashMap<String, Object>());
  }
}
