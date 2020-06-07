package com.jz.java.designpattern.flyweight.editor;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class CharcStyleFactory {
  private static final Set<CharcStyle> charcStyles = new HashSet<>();

  public static CharcStyle getCharcStyle(Font font, int size, Color color) {
     CharcStyle charcStyle = new CharcStyle(font, size, color);
     charcStyles.add(charcStyle);
     return charcStyle;
  }

}
