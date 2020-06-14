package com.jz.java.designpattern.statemachine;

public class MarioStateMachine {
  private int score;

  private IMario currentState;

  public MarioStateMachine() {
    this.score = 0;
    this.currentState = SmallMario.getInstance();
  }

  public void obtainMushRoom() throws Exception {
    currentState.obtainMushRoom(this);
  }

  public void obtainCape() throws Exception {
    currentState.obtainCape(this);
  }

  public void obtainFireFlower() throws Exception {
    currentState.obtainFireFlower(this);
  }

  public void meetMonster() throws Exception {
    currentState.meetMonster(this);
  }

  public int getScore() {
    return this.score;
  }

  public State getCurrentState() {
    return this.currentState.getName();
  }

  public void setScore(int score) {
    this.score = score;
  }

  public void setCurrentState(IMario currentState) {
    this.currentState = currentState;
  }
}
