package com.jz.designpattern.delegator.door;

//就像买房子的agent一样，专门做这个事情的。别人有需要Alarm这个事情，直接传进来让他做就行；
public class AlarmAgent implements IAlarm {

    @Override
    public void alarm() {
        System.out.println("Playing Alarm Audio.");
    }
}
