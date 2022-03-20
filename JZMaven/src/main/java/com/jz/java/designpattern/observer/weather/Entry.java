package com.jz.java.designpattern.observer.weather;

public class Entry {
    public static void main(String[] args) {
        WeatherUpdater weatherUpdater = new WeatherUpdater();
        ClockUpdate clockUpdate = new ClockUpdate();
        Board board = new Board();
        weatherUpdater.addWeatherListener(new TV());
        weatherUpdater.addWeatherListener(board);
        clockUpdate.addClockListner(board);
        weatherUpdater.update();
        clockUpdate.update();
    }
}
