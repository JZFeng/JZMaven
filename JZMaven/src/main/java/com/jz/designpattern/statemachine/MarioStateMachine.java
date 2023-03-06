package com.jz.designpattern.statemachine;

//状态机需要传来传去;
public class MarioStateMachine {

  private int score;
  private IMario currentState;

  public MarioStateMachine() {
    this.score = 0;
    this.currentState = SmallMario.getInstance();
  }

  public int getScore() {
    return this.score;
  }

  public IMario getCurrentState() {
    return this.currentState;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public void setCurrentState(IMario currentState) {
    this.currentState = currentState;
  }
}
