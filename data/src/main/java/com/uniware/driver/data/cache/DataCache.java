package com.uniware.driver.data.cache;

import rx.Observable;

/**
 * An interface representing a user Cache.
 */
public interface DataCache {
  /**
   * Gets an {@link rx.Observable} which will emit a {@link Object}.
   *
   * @param userId The user id to retrieve data.
   */
  Observable<Object> get(final int userId);

  /**
   * Puts and element into the cache.
   *
   * @param userEntity Element to insert in the cache.
   */
  void put(Object userEntity);

  /**
   * Checks if an element (User) exists in the cache.
   *
   * @param userId The id used to look for inside the cache.
   * @return true if the element is cached, otherwise false.
   */
  boolean isCached(final int userId);

  /**
   * Checks if the cache is expired.
   *
   * @return true, the cache is expired, otherwise false.
   */
  boolean isExpired();

  /**
   * Evict all elements of the cache.
   */
  void evictAll();
}
