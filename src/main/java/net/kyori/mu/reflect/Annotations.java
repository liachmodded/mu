/*
 * This file is part of mu, licensed under the MIT License.
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
package net.kyori.mu.reflect;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

/**
 * A collection of utilities for working with annotations.
 */
public interface Annotations {
  /**
   * Finds an annotation by searching the class hierarchy.
   *
   * @param element the annotated element
   * @param annotationType the annotation type
   * @param <A> the annotation type
   * @return the annotation
   */
  static <A extends Annotation> @Nullable A find(final @NonNull AnnotatedElement element, final @NonNull Class<A> annotationType) {
    if(element instanceof Class<?>) {
      return find((Class<?>) element, annotationType);
    } else if(element instanceof Method) {
      return find((Method) element, annotationType);
    } else {
      final /* @Nullable */ A annotation = element.getDeclaredAnnotation(annotationType);
      if(annotation != null) {
        return annotation;
      }
    }
    return null;
  }

  /**
   * Finds an annotation by searching the hierarchy of {@code klass}.
   *
   * @param klass the klass
   * @param annotationType the annotation type
   * @param <A> the annotation type
   * @return the annotation
   */
  static <A extends Annotation> @Nullable A find(final @NonNull Class<?> klass, final @NonNull Class<A> annotationType) {
    final /* @Nullable */ A annotation = klass.getDeclaredAnnotation(annotationType);
    if(annotation != null) {
      return annotation;
    }
    return Types.find(klass, type -> type.getDeclaredAnnotation(annotationType));
  }

  /**
   * Finds an annotation by searching the class hierarchy of {@code method}.
   *
   * @param method the method
   * @param annotationType the annotation type
   * @param <A> the annotation type
   * @return the annotation
   */
  static <A extends Annotation> @Nullable A find(final @NonNull Method method, final @NonNull Class<A> annotationType) {
    final /* @Nullable */ A annotation = method.getDeclaredAnnotation(annotationType);
    if(annotation != null) {
      return annotation;
    }
    final Class<?> declaringClass = method.getDeclaringClass();
    return Types.find(declaringClass, type -> {
      // cannot search same class
      if(type == declaringClass) return null;
      final Method source = Methods.getDeclared(type, method);
      if(source == null) return null;
      // private and static methods cannot be an override
      if(Members.isPrivate(method) || Members.isStatic(method)) return null;
      if(Members.isPrivate(source) || Members.isStatic(source)) return null;
      // source cannot be overridden if final
      if(Members.isFinal(source)) return null;
      // package-private methods can only be overridden from the same package
      if(Members.isPackagePrivate(method) && !Types.inSamePackage(method.getDeclaringClass(), source.getDeclaringClass())) return null;
      return source.getDeclaredAnnotation(annotationType);
    });
  }
}
