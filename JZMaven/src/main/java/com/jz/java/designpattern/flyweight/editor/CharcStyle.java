package com.jz.java.designpattern.flyweight.editor;

import java.awt.*;
import java.util.Objects;

public class CharcStyle {
  private Font font;
  private int size;
  private Color color;

  public CharcStyle(Font font , int size, Color color) {
    this.font = font;
    this.size = size;
    this.color = color;
  }

  @Override
  public boolean equals(Object o) {
    if(this == o) {
      return true;
    }
    if( !(o instanceof CharcStyle) ) {
      return false;
    }

    CharcStyle charcStyle = (CharcStyle)o;

    return charcStyle.font.equals(font)
        && charcStyle.color.equals(color)
        && charcStyle.size == size;
  }

  @Override
  public int hashCode(){
    return Objects.hash(font, size, color);
  }


}
