package com.jz.java.designpattern.proxy.alcohol;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class Entry {
    public static void main(String[] args) {

        MaoTai maoTai = new MaoTai();
        InvocationHandler invocationHandler = new Stand(maoTai);

        ISellAlcohol dynamicProxy = (ISellAlcohol) Proxy.newProxyInstance(MaoTai.class.getClassLoader(), MaoTai.class.getInterfaces(), invocationHandler);

        dynamicProxy.sellAlcohol();

    }

}
