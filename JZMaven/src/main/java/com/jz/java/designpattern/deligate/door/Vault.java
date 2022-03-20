package com.jz.java.designpattern.deligate.door;

public class Vault implements IAlarm {
    AlarmAgent alarmAgent = null;

    public void Valut(AlarmAgent alarmAgent) {
        this.alarmAgent = alarmAgent;
    }

    @Override
    public void alarm() {
        this.alarmAgent.alarm();

    }

}
