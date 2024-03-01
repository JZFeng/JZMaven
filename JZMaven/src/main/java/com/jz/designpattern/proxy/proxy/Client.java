/**
 * @Author jzfeng
 * @Date 3/19/22-9:31 PM
 */

package com.jz.designpattern.proxy.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Client {
    public static void main(String[] args) {
        IActor actor = new Actor();
        IActor proxyActor = (IActor)Proxy.newProxyInstance(actor.getClass().getClassLoader(), actor.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                String methodName = method.getName();
                Float money = (float) args[0];
                Object res = null;
                if (methodName.equals("basicAct")) {
                    if (money > 2000f) {
                        res = method.invoke(actor, money / 2);
                    }
                }

                if (methodName.equals("dangerAct")) {
                    if (money > 5000) {
                        res = method.invoke(actor, money / 2);
                    }
                }

                return res;
            }
        });

        proxyActor.basicAct(2500f);
        proxyActor.dangerAct(6000f);
    }
}
