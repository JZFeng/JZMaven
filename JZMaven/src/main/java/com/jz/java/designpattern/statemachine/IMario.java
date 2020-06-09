package com.jz.java.designpattern.statemachine;

public interface IMario {
  State getName();
  void obtainMushRoom(MarioStateMachine stateMachine) throws Exception;
  void obtainCape(MarioStateMachine stateMachine) throws Exception;
  void obtainFireFlower(MarioStateMachine stateMachine) throws Exception;
  void meetMonster(MarioStateMachine stateMachine) throws Exception;
}