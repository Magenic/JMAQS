/*
 * Copyright 2019 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.base;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Concurrent driver manager hash map.
 */
public class ConcurrentManagerHashMap extends ConcurrentHashMap<String, BaseTestObject> {

  /**
   * Removes the entry for a key only if currently mapped to a given value.
   *
   * @param key   key with which the specified value is associated
   * @param value value expected to be associated with the specified key
   * @return true if the value was removed
   */
  @Override
  public boolean remove(Object key, Object value) {
    this.closeForKey(key);
    return super.remove(key, value);
  }

  /**
   * Removes the key (and its corresponding value) from this map.
   *
   * @param key key with which the specified value is associated
   * @return true if the value was removed
   */
  @Override
  public BaseTestObject remove(Object key) {
    this.closeForKey(key);
    return super.remove(key);
  }

  /**
   * Removes all of the mappings from this map.
   */
  @Override
  public void clear() {

    super.forEach((k, v) -> v.close());

    super.clear();
  }

  /**
   * Replaces the entry for a key only if currently mapped to a given value.
   *
   * @param key      key with which the specified value is associated
   * @param oldValue value expected to be associated with the specified key
   * @param newValue value to be associated with the specified key
   * @return true if the value was replaced
   */
  @Override
  public boolean replace(String key, BaseTestObject oldValue, BaseTestObject newValue) {
    oldValue.close();
    return super.replace(key, oldValue, newValue);
  }

  /**
   * Replaces the entry for a key only if currently mapped to some value.
   *
   * @param key   key with which the specified value is associated
   * @param value value expected to be associated with the specified key
   * @return the previous value associated with the specified key, or null if there was no mapping for the key
   */
  @Override
  public BaseTestObject replace(String key, BaseTestObject value) {
    this.closeForKey(key);
    return super.replace(key, value);
  }

  /**
   * Close the object that matches the key.
   *
   * @param key Key of the item to close
   */
  private void closeForKey(Object key) {
    if (super.contains(key)) {
      super.get(key).close();
    }
  }
}
