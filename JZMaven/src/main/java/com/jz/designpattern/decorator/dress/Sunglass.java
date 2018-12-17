package com.jz.designpattern.decorator.dress;

public class Sunglass  implements  Decorator{
    IPerson person;

    Sunglass(IPerson person) {
        this.person = person;
    }


    @Override
    public String getClothes() {
        return person.getClothes() + " Sunglass + ";
    }
}
