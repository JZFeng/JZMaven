package com.jz.java.designpattern.decorator.picFrame;

public class Entry {

    /**
     * @param args
     */
    public static void main(String[] args) {
        Picture picture = new Picture(1000);
        picture.setFrame(new TriangleFrame());
        picture.paint();
    }

}
