/**
 * @Author jzfeng
 * @Date 3/19/22-9:41 PM
 */

package com.jz.java.designpattern.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class Client {
    public static void main(String[] args) {
        Actor actor = new Actor();
        Actor cglibActor = (Actor) Enhancer.create(actor.getClass(), new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                String methodName = method.getName();
                Float money = (float)objects[0];
                Object res = null;
                if( methodName.equals("basicAct") ) {
                    if(money > 2000f) {
                        res = method.invoke(actor, money);
                    }
                }
                if(methodName.equals("dangerAct")) {
                    if(money > 5000f) {
                        res = method.invoke(actor, money);
                    }
                }
                return res;
            }
        });

        cglibActor.basicAct(4000f);
        cglibActor.dangerAct(8000f);
    }
}
