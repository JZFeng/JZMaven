package com.jz.java.designpattern.observer.weather;

public class TV implements IWeatherSubscriber {

    public void updateWeather(int areaCode, long temperature) {
        System.out.println("TV Update:" + "[Area Code:" + areaCode + ", Temperature:" + temperature + "]");
    }

}
