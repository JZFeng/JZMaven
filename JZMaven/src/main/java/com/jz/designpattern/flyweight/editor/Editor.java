package com.jz.designpattern.flyweight.editor;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Editor {
  private List<Charc> chars = new ArrayList<>();

  public void appendCharacters(char c, Font font, int size , Color color) {
    chars.add(new Charc(c, CharcStyleFactory.getCharcStyle(font, size, color)));
  }

  public static void main(String[] args) {

    Integer i1 = 56;
    Integer i2 = 56;
    Integer i3 = 129;
    Integer i4 = 129;
    System.out.println(i1 == i2);
    System.out.println(i3 == i4);
  }
}
