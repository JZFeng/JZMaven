/**
 * @Author jzfeng
 * @Date 3/19/22-9:40 PM
 */

package com.jz.designpattern.proxy.cglib;

public class Actor {
    public void basicAct(float money) {
        System.out.println("拿到钱，开始基本的表演："+money);
    }
    public void dangerAct(float money){
        System.out.println("拿到钱，开始危险的表演："+money);
    }
}
