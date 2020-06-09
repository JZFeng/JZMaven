package com.jz.java.designpattern.statemachine;

public class CapeMario extends AbstractState{

  private static final CapeMario instance = new CapeMario();

  private CapeMario(){}

  public static CapeMario getInstance() {
    return instance;
  }

  @Override
  public State getName() {
    return State.CAPE;
  }

  @Override
  public void obtainMushRoom(MarioStateMachine stateMachine) {
    //do nothing
  }

  @Override
  public void obtainCape(MarioStateMachine stateMachine) {
    // do nothing
  }

  @Override
  public void obtainFireFlower(MarioStateMachine stateMachine) {
  }

  @Override
  public void meetMonster(MarioStateMachine stateMachine) {

  }
}
