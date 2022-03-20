package com.jz.java.designpattern.deligate.door;

public class AlarmDoor extends Door implements IAlarm {

    AlarmAgent alarmAgent = null;

    AlarmDoor(AlarmAgent alarmAgent) {
        this.alarmAgent = alarmAgent;
    }

    @Override
    public void open() {
        System.out.println("Alarm Door Open.");

    }

    @Override
    public void close() {
        // TODO Auto-generated method stub
        System.out.println("Alarm Door Close.");

    }

    @Override
    public void alarm() {
        alarmAgent.alarm();

    }

}
