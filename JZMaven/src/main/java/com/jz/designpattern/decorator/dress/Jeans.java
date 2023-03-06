package com.jz.designpattern.decorator.dress;

public class Jeans implements Decorator {

    IPerson person ;

    Jeans(IPerson person) {
        this.person = person;
    }


    @Override
    public String getClothes() {
        return person.getClothes() + "Jeans + ";
    }
}
