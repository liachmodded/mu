/*
 * This file is part of lambda, licensed under the MIT License.
 *
 * Copyright (c) 2018-2019 KyoriPowered
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package net.kyori.lambda.collection;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * A loading map.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public interface LoadingMap<K, V> extends Map<K, V> {
  /**
   * Creates a loading map, backed by a {@link ConcurrentHashMap}.
   *
   * @param function the function
   * @param <K> the key type
   * @param <V> the value type
   * @return a loading map
   */
  static <K, V> @NonNull LoadingMap<K, V> concurrent(final @NonNull Function<K, V> function) {
    return of(new ConcurrentHashMap<>(), function);
  }

  /**
   * Creates a loading map.
   *
   * @param map the map
   * @param function the function
   * @param <K> the key type
   * @param <V> the value type
   * @return a loading map
   */
  static <K, V> @NonNull LoadingMap<K, V> of(final @NonNull Map<K, V> map, final @NonNull Function<K, V> function) {
    return new LoadingMapImpl<>(map, function);
  }

  /**
   * Gets, or computes if absent, the value for {@code key}.
   *
   * @param key the key
   * @return the value
   */
  @Override
  V get(final Object key);
}
