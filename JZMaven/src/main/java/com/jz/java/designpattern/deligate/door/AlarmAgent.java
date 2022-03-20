package com.jz.java.designpattern.deligate.door;

public class AlarmAgent implements IAlarm {

    @Override
    public void alarm() {
        System.out.println("Playing Alarm Audio.");
    }
}
