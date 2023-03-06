package com.jz.designpattern.decorator.dress;

public class Tie implements  Decorator {
    IPerson person;

    Tie(IPerson person) {
        this.person = person;
    }

    @Override
    public String getClothes() {
        return person.getClothes() + " tie + ";
    }
}
