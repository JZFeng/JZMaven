package com.jz.java.designpattern.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class ProxyBasedOnClass {
    public static void main(String[] args) {
        //被代理的对象
        final Producer producer = new Producer();

        //基于子类的代理对象，
        //强制转换为父类
        Producer cglibProducer  = (Producer) Enhancer.create(producer.getClass(), new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                Object returnValue = null;
                float money = (Float)args[0];
                if("sellProduct".equals(method.getName())) {
                    returnValue = method.invoke(producer, money  * 0.8f);
                }

                return returnValue;
            }
        });

        cglibProducer.sellProduct(10000f);
    }

}
