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
package net.kyori.mu.stream;

import com.google.common.collect.Iterators;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Stream;

import static com.google.common.truth.Truth8.assertThat;

class MuStreamsTest {
  @Test
  void testConcat() {
    final Stream<String> stream = MuStreams.concat(
      Stream.of("abc"),
      Stream.of("def", "ghi"),
      Stream.of("jkl")
    );
    assertThat(stream).containsExactly("abc", "def", "ghi", "jkl").inOrder();
  }

  @Test
  void testStream_collection() {
    final Iterable<String> iterable = Arrays.asList("abc", "def");
    final Stream<String> stream = MuStreams.of(iterable);
    assertThat(stream).containsExactly("abc", "def").inOrder();
  }

  @Test
  void testStream_iterable() {
    final Iterable<String> iterable = () -> Iterators.forArray("abc", "def");
    final Stream<String> stream = MuStreams.of(iterable);
    assertThat(stream).containsExactly("abc", "def").inOrder();
  }
}
