package com.jz.java.designpattern.strategy;

public class Context {
    ISale sale = null;

    /*Context(ISale sale) {
        this.sale = sale;
    }*/

    Context(String saleType) {
        /*
        Class clazz = Class.forName(saleType);
        clazz.getConstructors();*/
        switch(saleType) {
            case "Normal" :
                sale = new Normal();
                break;
            case "20%Off" :
                sale = new Discount(0.8);
                break;
            case "100Off300" :
                sale = new Rebate(300,100);
                break;
            default :
                sale = new Normal();
                break;
        }


    }


    public double calculate(double originalPrice) {
        return sale.calculate(originalPrice);
    }

}
