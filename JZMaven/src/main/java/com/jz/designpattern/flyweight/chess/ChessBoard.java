package com.jz.designpattern.flyweight.chess;

import java.util.HashMap;
import java.util.Map;

public class ChessBoard {
  private Map<Integer, ChessPiece> chessPieces = new HashMap<>();

  public ChessBoard() {
    init();
  }

  private void init() {
    chessPieces.put(1, new ChessPiece(ChessPieceUnitFactory.getChessPieceUnit(1),0,0 ));
    chessPieces.put(2, new ChessPiece(ChessPieceUnitFactory.getChessPieceUnit(2), 0, 1));
    //初始化棋盘；
  }

  public void move(int chessPieceId, int toPositionX, int toPositionY) {

  }
}
