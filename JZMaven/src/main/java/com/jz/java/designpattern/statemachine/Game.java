/**
 * @Author jzfeng
 * @Date 3/20/22-10:41 AM
 */

package com.jz.java.designpattern.statemachine;

public class Game {
    private MarioStateMachine stateMachine;

    Game(MarioStateMachine marioStateMachine) {
        this.stateMachine = marioStateMachine;
    }

    public MarioStateMachine getStateMachine() {
        return stateMachine;
    }

    public void start() throws Exception {
        System.out.println("游戏加载中...");
        Thread.sleep(2000);
        System.out.println("开始游戏...");
        System.out.println("初始状态:" + stateMachine.getCurrentState().getName() + ";初始分数:" + stateMachine.getScore());
    }
}
