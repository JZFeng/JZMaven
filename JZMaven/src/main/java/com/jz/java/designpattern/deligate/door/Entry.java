package com.jz.java.designpattern.deligate.door;

public class Entry {

    public static void main(String[] args) {
        Thread thread = new Thread(new LightDoor());
        thread.start();
        System.out.println("This is the main thread statement.");

    }

}
