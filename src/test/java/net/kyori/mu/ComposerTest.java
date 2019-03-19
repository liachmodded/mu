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
package net.kyori.mu;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ComposerTest {
  @Test
  void testGet() {
    final List<String> values = Composer.get(() -> Arrays.asList("abc", "def"));
    assertEquals(2, values.size());
    assertEquals("abc", values.get(0));
    assertEquals("def", values.get(1));
  }

  @Test
  void testAccept() {
    final List<String> values = Composer.accept(new ArrayList<>(), (list) -> {
      list.add("abc");
      list.add("def");
    });
    assertEquals(2, values.size());
    assertEquals("abc", values.get(0));
    assertEquals("def", values.get(1));
  }

  @Test
  void testApply() {
    final List<String> values = Composer.apply(
      new ArrayList<>(Arrays.asList("abc", "def")),
      Collections::unmodifiableList
    );
    assertEquals(2, values.size());
    assertEquals("abc", values.get(0));
    assertEquals("def", values.get(1));
    assertThrows(UnsupportedOperationException.class, () -> values.add("ghi"));
  }

  @Test
  void testMake() {
    final List<String> values = Composer.make(
      new ArrayList<String>(),
      (list) -> {
        list.add("abc");
        list.add("def");
      },
      Collections::unmodifiableList
    );
    assertEquals(2, values.size());
    assertEquals("abc", values.get(0));
    assertEquals("def", values.get(1));
    assertThrows(UnsupportedOperationException.class, () -> values.add("ghi"));
  }
}