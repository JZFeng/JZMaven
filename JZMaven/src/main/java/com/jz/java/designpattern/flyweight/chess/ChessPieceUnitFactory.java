package com.jz.java.designpattern.flyweight.chess;

import java.util.HashMap;
import java.util.Map;

public class ChessPieceUnitFactory {
  private static final Map<Integer, ChessPieceUnit>  chessPieceUnits = new HashMap<>();

  static {
    chessPieceUnits.put(1, new ChessPieceUnit(1, "车", Color.BLACK));
    chessPieceUnits.put(2, new ChessPieceUnit(2, "帅", Color.RED));
  }

  public static ChessPieceUnit getChessPieceUnit(int id) {
    return chessPieceUnits.get(id);
  }
}
