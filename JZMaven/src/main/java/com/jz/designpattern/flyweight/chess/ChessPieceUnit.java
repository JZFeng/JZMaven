package com.jz.designpattern.flyweight.chess;

public class ChessPieceUnit {
  private int id;
  private String text;
  private Color color;


  public ChessPieceUnit(int id, String text, Color color) {
    this.id = id;
    this.text = text;
    this.color = color;
  }
}
