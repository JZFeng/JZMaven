package com.jz.java.multiThread.concurrent.future;

public class StockPrice {
    private final float price;
    private final String from;

    StockPrice(float price, String from) {
        this.price = price;
        this.from = from;
    }

    public float getPrice() {
        return price;
    }

    public String getFrom() {
        return from;
    }

    @Override
    public String toString() {
        return "Price : " + price + " from " + from;
    }
}
