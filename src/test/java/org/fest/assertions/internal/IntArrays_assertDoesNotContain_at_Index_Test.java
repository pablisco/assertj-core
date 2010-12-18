/*
 * Created on Dec 14, 2010
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright @2010 the original author or authors.
 */
package org.fest.assertions.internal;

import static org.fest.assertions.data.Index.atIndex;
import static org.fest.assertions.error.ContainsAtIndex.containsAtIndex;
import static org.fest.assertions.test.Arrays.arrayOfInts;
import static org.fest.assertions.test.ExpectedException.none;
import static org.fest.assertions.test.FailureMessages.unexpectedNull;
import static org.fest.assertions.util.ArrayWrapperList.wrap;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

import org.fest.assertions.core.AssertionInfo;
import org.fest.assertions.core.WritableAssertionInfo;
import org.fest.assertions.data.Index;
import org.fest.assertions.test.ExpectedException;
import org.junit.*;

/**
 * Tests for <code>{@link IntArrays#assertDoesNotContain(AssertionInfo, int[], int, Index)}</code>.
 *
 * @author Alex Ruiz
 */
public class IntArrays_assertDoesNotContain_at_Index_Test {

  private static WritableAssertionInfo info;
  private static int[] actual;

  @Rule public ExpectedException thrown = none();

  private Failures failures;
  private IntArrays arrays;

  @BeforeClass public static void setUpOnce() {
    info = new WritableAssertionInfo();
    actual = arrayOfInts(6, 8, 10);
  }

  @Before public void setUp() {
    failures = spy(Failures.instance());
    arrays = new IntArrays(failures);
  }

  @Test public void should_fail_if_actual_is_null() {
    thrown.expectAssertionError(unexpectedNull());
    arrays.assertDoesNotContain(info, null, 8, atIndex(0));
  }

  @Test public void should_pass_if_actual_is_empty() {
    arrays.assertDoesNotContain(info, new int[0], 8, atIndex(0));
  }

  @Test public void should_throw_error_if_Index_is_null() {
    thrown.expectNullPointerException("Index should not be null");
    arrays.assertDoesNotContain(info, actual, 8, null);
  }

  @Test public void should_pass_if_Index_is_out_of_bounds() {
    arrays.assertDoesNotContain(info, actual, 8, atIndex(6));
  }

  @Test public void should_fail_if_actual_contains_value_at_index() {
    try {
      arrays.assertDoesNotContain(info, actual, 6, atIndex(0));
      fail();
    } catch (AssertionError e) {}
    verify(failures).failure(info, containsAtIndex(wrap(actual), 6, atIndex(0)));
  }
}