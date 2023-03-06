package com.jz.designpattern.strategy;

public class SaleFactory {

    public static ISale getSale(String saleType) {
        ISale sale = null;
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

        return sale;
    }

}
