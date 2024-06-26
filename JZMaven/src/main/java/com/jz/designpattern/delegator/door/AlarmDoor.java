package com.jz.designpattern.delegator.door;

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
        System.out.println("Alarm Door Close.");
    }

    @Override
    public void alarm() {
        this.alarmAgent.alarm();
    }
}
