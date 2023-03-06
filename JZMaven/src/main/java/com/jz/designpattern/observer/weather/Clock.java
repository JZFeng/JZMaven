package com.jz.designpattern.observer.weather;

import java.util.HashSet;
import java.util.Set;

public class Clock {
    Set<IClockSubscriber> clockSubscribers = null;

    public void addClockSubscriber(IClockSubscriber subscriber) {
        if (clockSubscribers == null) {
            clockSubscribers = new HashSet<>();
        }
        clockSubscribers.add(subscriber);
    }

    public boolean removeClockSubscriber(IClockSubscriber subscriber) {
        return clockSubscribers.remove(subscriber);
    }

    public void update(int hour) {
        for( IClockSubscriber subscriber : clockSubscribers ) {
            subscriber.updateSharpHour(hour);
        }
    }

}
