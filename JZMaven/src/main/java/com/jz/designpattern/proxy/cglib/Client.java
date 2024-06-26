/**
 * @Author jzfeng
 * @Date 3/19/22-9:41 PM
 */

package com.jz.designpattern.proxy.cglib;


import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

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
                        res = method.invoke(actor, money /2 ); //通过反射调用。
//                        res = methodProxy.invoke(actor, objects);
                        System.out.println(o.getClass().getName());
                    }
                }
                if(methodName.equals("dangerAct")) {
                    if(money > 5000f) {
                        //res = method.invoke(actor, money / 2);//通过反射调用。
                        res = methodProxy.invoke(actor, objects);
                    }
                }
                return res;
            }
        });

        cglibActor.basicAct(4000f);
        cglibActor.dangerAct(8000f);
    }
}
