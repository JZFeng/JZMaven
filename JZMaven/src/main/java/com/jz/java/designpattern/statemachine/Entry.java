/**
 * @Author jzfeng
 * @Date 3/20/22-10:41 AM
 */

package com.jz.java.designpattern.statemachine;

public class Entry {
    public static void main(String[] args) throws Exception {
        MarioStateMachine stateMachine = new MarioStateMachine();

        System.out.println(stateMachine.getCurrentState().getName() + ":" + stateMachine.getScore());

        stateMachine.getCurrentState().obtainMushRoom(stateMachine);
        System.out.println(stateMachine.getCurrentState().getName() + ":" + stateMachine.getScore());

        stateMachine.getCurrentState().obtainCape(stateMachine);
        System.out.println(stateMachine.getCurrentState().getName() + ":" + stateMachine.getScore());

        stateMachine.getCurrentState().meetMonster(stateMachine);
        System.out.println(stateMachine.getCurrentState().getName() + ":" + stateMachine.getScore());

    }
}
