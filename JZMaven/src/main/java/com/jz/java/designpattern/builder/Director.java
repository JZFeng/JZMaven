package com.jz.java.designpattern.builder;

public class Director {
    //指挥装机人员组装电脑
    public void construct(Builder builder) {
        builder.buildCPU();
        builder.buildMainboard();
        builder.builderHD();
    }
}
