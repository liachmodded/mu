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
package net.kyori.lambda.reflect;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.Function;

/**
 * A collection of utilities for working with types.
 */
public interface Types {
  /**
   * Finds a class in the hierarchy matching the provided {@code type} and {@code function}.
   *
   * @param first the first class, used to start the search
   * @param function the function to find a result
   * @param <T> the class type
   * @param <R> the result type
   * @return the result of applying {@code function} to a class
   */
  // thanks, kenzie
  @SuppressWarnings("unchecked")
  static <T, R> @Nullable R find(final @NonNull Class<T> first, final @NonNull Function<Class<? super T>, R> function) {
    final Deque<Class<? super T>> classes = new ArrayDeque<>();
    classes.add(first);

    while(!classes.isEmpty()) {
      final /* @Nullable */ Class<? super T> next = classes.remove();

      final /* @Nullable */ R result = function.apply(next);
      if(result != null) {
        return result;
      }

      final /* @Nullable */ Class<?> parent = next.getSuperclass();
      if(parent != null && parent != Object.class) {
        classes.add((Class<? super T>) parent);
      }

      for(final Class<?> interfaceType : next.getInterfaces()) {
        classes.add((Class<? super T>) interfaceType);
      }
    }

    return null;
  }
}
