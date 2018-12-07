package com.jz.designPattern.decorator;

public class Jeans implements IDecorator {

    IComponent component ;

    Jeans(IComponent component) {
        super();
        this.component = component;
    }

    @Override
    public void decorate(IComponent component) {


    }
}
