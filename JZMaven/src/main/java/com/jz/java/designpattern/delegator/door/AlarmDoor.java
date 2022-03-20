package com.jz.java.designpattern.delegator.door;

public class AlarmDoor extends Door implements IAlarm {

    IAlarm alarmAgent = null;

    AlarmDoor(IAlarm alarmAgent) {
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
