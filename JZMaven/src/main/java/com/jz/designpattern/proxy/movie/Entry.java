package com.jz.designpattern.proxy.movie;

public class Entry {
    public static void main(String[] args) {
        Movie movie = new Movie("Titanic");
        new Cinema(movie).play();
    }
}
