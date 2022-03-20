package com.jz.java.designpattern.deligate.door;

public class ColorDoor extends Door {

    int color;
    public int length;

    @Override
    public void open() {
        System.out.println("Color Door Open." + length);

    }

    @Override
    public void close() {
        System.out.println("Color Door Close.");
    }

}
