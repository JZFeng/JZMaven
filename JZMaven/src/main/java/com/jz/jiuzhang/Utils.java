package com.jz.jiuzhang;

import static java.lang.Math.abs;

public class Utils {
  public static void main(String[] args) {
    double x = abs(-123.45);
    assert x >= 0 : "x must >= 0 but x = " + x;
    System.out.println(x);
  }
}
