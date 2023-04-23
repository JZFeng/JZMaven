package com.jz.designpattern.observer.weather;

import java.util.*;

//相当于信息的发布者；
public class Weather {

    Set<IWeatherSubscriber> weatherSubscribers ;

    public void addSubscriber(IWeatherSubscriber weatherSubscriber) {
        if (weatherSubscribers == null) {
            weatherSubscribers = new HashSet<>();
        }
        weatherSubscribers.add(weatherSubscriber);
    }

    public boolean removeSubscriber(IWeatherSubscriber subscriber) {
            return weatherSubscribers.remove(subscriber);
    }

    // Usually get weather from sensor
    private long getRegionTemp(int areaCode){
        return areaCode * 10;
    }

    //异步更新；
    public void update() {
        long areaTemp = 0;

        for (int areaCode = 0; areaCode <= 3; areaCode++) {
            areaTemp = getRegionTemp(areaCode);

            for(IWeatherSubscriber subscriber: weatherSubscribers) {
                int finalAreaCode = areaCode;
                long finalAreaTemp = areaTemp;

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        subscriber.updateWeather(finalAreaCode, finalAreaTemp);
                    }
                }).start();
            }
        }
    }

}
