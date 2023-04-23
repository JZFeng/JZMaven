package com.jz.designpattern.delegator.door;

public class ColorDoor extends Door {

    int color;
    public int length;

    ColorDoor(int color, int length){
        this.color = color;
        this.length = length;
    }

    @Override
    public void open() {
        System.out.println("Color Door Open." + length);
    }

    @Override
    public void close() {
        System.out.println("Color Door Close.");
    }

}
