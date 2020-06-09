package com.jz.java.designpattern.statemachine;

public abstract class AbstractState implements IMario {

  public abstract  State getName();

  public void obtainMushRoom(MarioStateMachine stateMachine) throws Exception {
    throw new Exception("Need Child Class implementation.");
  }

  @Override
  public void obtainCape(MarioStateMachine stateMachine) throws Exception {
    throw new Exception("Need Child Class implementation.");
  }

  @Override
  public void obtainFireFlower(MarioStateMachine stateMachine) throws Exception {
    throw new Exception("Need Child Class implementation.");
  }

  @Override
  public void meetMonster(MarioStateMachine stateMachine) throws Exception {
    throw new Exception("Need Child Class implementation.");
  }
}
