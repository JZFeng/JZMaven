/**
 * @Author jzfeng
 * @Date 3/20/22-10:46 AM
 */

package com.jz.java.designpattern.statemachine;

public class SuperMario extends AbstractState{

    private static final SuperMario instance = new SuperMario();

    private SuperMario(){
    }

    public static SuperMario getInstance() {
        return instance;
    }

    @Override
    public State getName() {
        return State.SUPER;
    }

    public void obtainMushRoom(MarioStateMachine stateMachine) throws Exception {
        //do nothing
    }

    @Override
    public void obtainCape(MarioStateMachine stateMachine) throws Exception {
        stateMachine.setScore(stateMachine.getScore() + 200);
        stateMachine.setCurrentState(CapeMario.getInstance());
    }

    @Override
    public void obtainFireFlower(MarioStateMachine stateMachine) throws Exception {
    }

    @Override
    public void meetMonster(MarioStateMachine stateMachine) throws Exception {
    }
}
