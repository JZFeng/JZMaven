package com.jz.java.designpattern.observer.weather;

public interface IWeatherListener {
    public void updateWeather(int areaCode, long temperature);
}
