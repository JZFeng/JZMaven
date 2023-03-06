package com.jz.designpattern.flyweight.editor;

public class Charc {
  private char c;
  private CharcStyle charcStyle;

  public Charc(char c , CharcStyle charcStyle) {
    this.c = c;
    this.charcStyle = charcStyle;
  }
}
