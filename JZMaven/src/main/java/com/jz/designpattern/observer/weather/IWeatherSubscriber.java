package com.jz.designpattern.observer.weather;

//相当于信息的订阅者；
public interface IWeatherSubscriber {
    public void updateWeather(int areaCode, long temperature);
}
