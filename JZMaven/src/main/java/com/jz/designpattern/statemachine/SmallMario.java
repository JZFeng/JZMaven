package com.jz.designpattern.statemachine;


//都是单例模式；因为一个游戏开始，只有那几种状态，设计成单例模式，很合理；
//状态机模式包括：状态，事件，动作；
//马里奥的不同形态就是状态机中的“状态”
// 游戏情节（比如吃了蘑菇）就是状态机中的“事件”，
// 加减积分就是状态机中的“动作”。
// 比如，吃蘑菇这个事件，会触发状态的转移：从小马里奥转移到超级马里奥，以及触发动作的执行（增加 100 积分）。

//状态模式通过将 [事件] 触发的[状态转移]和[动作执行]，拆分到不同的状态类中；

public class SmallMario extends AbstractState {
  private static final SmallMario instance = new SmallMario();

  private SmallMario() {}

  public static SmallMario getInstance() {
    return instance;
  }

  @Override
  public State getName() {
    return State.SMALL;
  }

  @Override
  public void obtainMushRoom(MarioStateMachine stateMachine) {
    stateMachine.setCurrentState(SuperMario.getInstance());
    stateMachine.setScore(stateMachine.getScore() + 100);
  }

  @Override
  public void obtainCape(MarioStateMachine stateMachine) {
    stateMachine.setCurrentState(CapeMario.getInstance());
    stateMachine.setScore(stateMachine.getScore() + 200);
  }


}