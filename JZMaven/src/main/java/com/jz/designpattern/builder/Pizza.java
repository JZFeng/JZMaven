package com.jz.designpattern.builder;

public class Pizza {
    private int size;
    private boolean hasMeat;
    private boolean hasPepperoni;
    private boolean hasCheese;

    private Pizza(Builder builder) {
        this.size = builder.size;
        this.hasCheese = builder.hasCheese;
        this.hasMeat = builder.hasMeat;
        this.hasPepperoni = builder.hasPepperoni;
    }

    public static class Builder {
        private static final int DEFAULT_SIZE = 6;

        private int size = DEFAULT_SIZE;
        private boolean hasMeat;
        private boolean hasPepperoni;
        private boolean hasCheese;

        public Builder size(int size) {
            this.size = size;
            return this;
        }


        public Builder hasMeat(boolean hasMeat) {
            this.hasMeat = hasMeat;
            return this;
        }

        public Builder hasCheese(boolean hasCheese) {
            this.hasCheese = hasCheese;
            return this;
        }

        public Builder hasPepperoni(boolean hasPepperoni) {
            this.hasPepperoni = hasPepperoni;
            return this;
        }

        public Pizza build() {
            //parameter check here
            //eg , size must be equal or greater than 6 inch;
            if (size < 6 || hasCheese == false) {
                throw new IllegalArgumentException("Minimum Pizza size : 6 inch; must have cheese");
            }

            return new Pizza(this);
        }
    }

}
