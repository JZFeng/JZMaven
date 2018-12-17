package com.jz.designpattern.proxy.alcohol;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class Stand implements InvocationHandler {
    private Object agent;

    Stand(Object agent) {
        this.agent = agent;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Start selling:  " + this.getClass().getSimpleName());
        method.invoke(agent, args);
        System.out.println("Selling ends.");

        return null;
    }


}
