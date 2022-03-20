package com.jz.java.designpattern.proxy.proxy;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyBasedOnInterface {
    public static void main(String[] args) {
        //被代理的对象
        final Producer producer = new Producer();

        //动态代理对象
        //强制转换为接口类型
        IProducer proxyProducer = (IProducer) Proxy.newProxyInstance(producer.getClass().getClassLoader(), producer.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //提供增强代码
                //定义返回值，这是必须的。
                Object returnValue = null;

                Float money = (Float)args[0];
                if("sellProduct".equals(method.getName())) {
                    returnValue = method.invoke(producer, money * 0.8f);
                }

                return returnValue;
            }
        });

        //由代理对象处理操作
        proxyProducer.sellProduct(10000f);
    }
}
