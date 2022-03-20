package com.jz.java.designpattern.observer.weather;

import java.util.Vector;

public class WeatherUpdater {
    Vector weatherListeners = null;

    public void addWeatherListener(IWeatherListener weather) {
        if (weatherListeners == null)
            weatherListeners = new Vector();
        weatherListeners.addElement(weather);
    }

    public void removeWeatherListener(IWeatherListener weather) {
        if (weatherListeners == null || weatherListeners.size() == 0)
            return;
        else if (weatherListeners.contains(weather)) {
            weatherListeners.removeElement(weather);
        }
    }

    public long getRegionTemp(int areaCode)
    // Usually get weather from sensor
    {
        return areaCode * 10;
    }

    public void update() {
        IWeatherListener listner = null;
        long areaTemp = 0;

        for (int areaCode = 0; areaCode <= 3; areaCode++) {
            areaTemp = getRegionTemp(areaCode);
            for (int index = 0; index < weatherListeners.size(); index++) {
                listner = (IWeatherListener) weatherListeners.elementAt(index);
                listner.updateWeather(areaCode, areaTemp);
            }

        }
    }

}
