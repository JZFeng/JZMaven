/**
 * @Author jzfeng
 * @Date 3/19/22-9:30 PM
 */

package com.jz.java.designpattern.proxy.proxy;

public class Actor implements IActor {

    @Override
    public void basicAct(float money) {
        System.out.println("拿到钱，开始基本的表演: " + money);
    }

    @Override
    public void dangerAct(float money) {
        System.out.println("拿到钱，开始危险的表演: " + money);
    }
}
