package com.jz.designpattern.proxy.pursuit;

public class Entry {
    public static  void main(String[] args) {
        RealPursuiter realPursuiter = new RealPursuiter("Xiaowang");
        Proxy proxy = new Proxy(realPursuiter);
        proxy.sendFlowers();
        proxy.sendChocolates();
        proxy.sendHandbags();
    }


}
