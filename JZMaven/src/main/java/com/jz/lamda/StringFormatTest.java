package com.jz.lamda;


import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class StringFormatTest {
  @Test
  public void testWorlds() {
    String[] input = new String[] { " HELLO ", "worlD  ", "java", "functional" };
    String[] expect = new String[] { "Functional", "Hello", "Java", "World" };
    assertArrayEquals(expect, StringFormat.format(input));
  }

  @Test
  public void testHasEmptyString() {
    String[] input = new String[] { "", " HELLO ", "worlD  ", "java", " \t ", "functional" };
    String[] expect = new String[] { "Functional", "Hello", "Java", "World" };
    assertArrayEquals(expect, StringFormat.format(input));
  }

  @Test
  public void testHasNull() {
    String[] input = new String[] { " ", " HELLO ", "worlD  ", "java", null, "functional" };
    String[] expect = new String[] { "Functional", "Hello", "Java", "World" };
    assertArrayEquals(expect, StringFormat.format(input));
  }

}
