package com.jz.designpattern.statemachine;

//我们可以将状态类设计成单例，毕竟状态类中不包含任何成员变量
//都是单例模式；因为一个游戏开始，只有那几种状态，设计成单例模式，很合理；

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
    stateMachine.setCurrentState(SmallMario.getInstance());
    stateMachine.setScore(0);
  }
}
