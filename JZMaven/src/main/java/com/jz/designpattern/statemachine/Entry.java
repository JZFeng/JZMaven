/**
 * @Author jzfeng
 * @Date 3/20/22-11:14 AM
 */

package com.jz.designpattern.statemachine;

public class Entry {
    public static void main(String[] args) throws Exception {
        Game game = new Game(new MarioStateMachine());
        game.start();

        MarioStateMachine stateMachine = game.getStateMachine();

        stateMachine.getCurrentState().obtainMushRoom(stateMachine);
        System.out.println(stateMachine.getCurrentState().getName() + ":" + stateMachine.getScore());

        stateMachine.getCurrentState().obtainCape(stateMachine);
        System.out.println(stateMachine.getCurrentState().getName() + ":" + stateMachine.getScore());

        stateMachine.getCurrentState().meetMonster(stateMachine);
        System.out.println(stateMachine.getCurrentState().getName() + ":" + stateMachine.getScore());
    }
}
