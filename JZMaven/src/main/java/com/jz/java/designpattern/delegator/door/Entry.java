package com.jz.java.designpattern.delegator.door;

public class Entry {

    public static void main(String[] args) {
        AlarmDoor alarmDoor = new AlarmDoor(new AlarmAgent());
        alarmDoor.alarm();

        Thread thread = new Thread(new LightDoor());
        thread.start();
        System.out.println("This is the main thread statement.");
    }

}
