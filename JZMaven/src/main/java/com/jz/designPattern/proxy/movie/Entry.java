package com.jz.designPattern.proxy.movie;

import com.jz.designPattern.proxy.movie.Cinema;
import com.jz.designPattern.proxy.movie.Movie;

public class Entry {
    public static void main(String[] args) {
        Movie movie = new Movie("Titanic");
        new Cinema(movie).play();
    }
}
