package com.jz.java.designpattern.proxy.movie;

public class Cinema implements IMovie {
    IMovie movie;

    Cinema(IMovie movie) {
        this.movie = movie;
    }

    private void playAds() {
        System.out.println("Commercial ADs start.");
    }

    @Override
    public void play() {
        playAds();
        movie.play();
        playAds();
    }
}
