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
   * Initializes an instance of the ScenarioContext class.
   */
  public ScenarioContext() {
    // This constructor is intentionally left empty
  }

  /**
   * The default string for context of a JMAQS cucumber test.
   */
  public static final String JMAQS_HOLDER = "BASE_JMAQS_TEST";

  /**
   * Field context.
   */
  private static final ThreadLocal<Map<String, Object>> context = new ThreadLocal<>();

  /**
   * Gets the object by the given name.
   *
   * @param name the name maped to the desired object
   * @return the object
   */
  public static Object get(final String name) {
    return getMap().get(name);
  }

  /**
   * Gets the object of type T.
   *
   * @param <T>  the type parameter
   * @param type the class type
   * @return the typed object
   */
  public static <T> T get(final Class<T> type) {
    final Object obj = get(type.toString());
    return (T) obj;
  }

  /**
   * Gets the object from the name and cast to the type.
   *
   * @param <T>  the type parameter
   * @param name the name
   * @param type the class type
   * @return the object of the given type
   */
  @SuppressWarnings("unchecked")
  public static <T> T get(final String name, final Class<T> type) {
    final Object obj = get(name);
    return (T) obj;
  }

  /**
   * Removes the object of the given type.
   *
   * @param <T>  the type parameter
   * @param type the type
   */
  public static <T> void remove(final Class<T> type) {
    getMap().remove(type.toString());
  }


  /**
   * Remove an object by name.
   *
   * @param name The name of the object to remove
   */
  public static void remove(String name) {
    getMap().remove(name);
  }

  /**
   * Puts an object by name.
   *
   * @param name the name
   * @param obj  the obj
   */
  public static void put(final String name, final Object obj) {
    getMap().put(name, obj);
  }

  /**
   * Puts an object by type.
   *
   * @param <T>  the type parameter
   * @param type the type
   * @param obj  the obj
   */
  public static <T> void put(final Class<T> type, final Object obj) {
    getMap().put(type.toString(), obj);
  }

  /**
   * Removes the context.
   */
  public static void close() {
    context.remove();
  }

  /**
   * Get local thread map.
   *
   * @return the object
   */
  private static Map<String, Object> getMap() {
    if (context.get() == null) {
      context.set(new HashMap<>());
    }
    return context.get();
  }
}
