package com.jz.java.designpattern.observer.weather;

import java.util.Vector;

public class ClockUpdate {
    Vector clockListners = null;

    public void addClockListner(IClockListener listner) {
        if (clockListners == null)
            clockListners = new Vector();
        clockListners.addElement(listner);
    }

    public void update() {
        for (int index = 0; index < clockListners.size(); index++) {
            IClockListener clockListener = (IClockListener) clockListners.elementAt(index);
            clockListener.updateSharpHour(10);
        }
    }


}
