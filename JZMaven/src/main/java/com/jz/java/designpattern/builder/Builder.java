package com.jz.java.designpattern.builder;

public abstract class Builder {

    //第一步：装CPU
//声明为抽象方法，具体由子类实现
    public abstract void buildCPU();

    //第二步：装主板
//声明为抽象方法，具体由子类实现
    public abstract void buildMainboard();

    //第三步：装硬盘
//声明为抽象方法，具体由子类实现
    public abstract void builderHD();

    //返回产品的方法：获得组装好的电脑
    public abstract Computer getComputer();
}