package com.jz.designPattern.proxy.movie;

public class Movie implements IMovie{
    private String name;

    Movie(String name) {
        this.name = name;
    }


    @Override
    public void play() {
        System.out.println("Playing movie :" + name);
    }
}
