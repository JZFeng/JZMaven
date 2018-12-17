package com.jz.designpattern.builder;

public class Pizza {
    private int size;
    private boolean cheese;
    private boolean pepperoni;
    private boolean bacon;

    //user builder to construct a pizza
    private Pizza(Builder builder) {
        this.size = builder.size;
        this.cheese = builder.cheese;
        this.pepperoni = builder.pepperoni;
        this.bacon = builder.bacon;
    }

    public static class Builder {

        //        required
        private final int size;

        //        optional
        private boolean cheese;
        private boolean pepperoni;
        private boolean bacon;

        public Builder(int size) {
            this.size = size;
        }

        public Builder cheese(boolean cheese) {
            this.cheese = cheese;
            return this;
        }

        public Builder pepperoni(boolean pepperoni) {
            this.pepperoni = pepperoni;
            return this;
        }

        public Builder bacon(boolean bacon) {
            this.bacon = bacon;
            return this;
        }

        public Pizza build() {
            return new Pizza(this);
        }


    }

    public static void main(String[] args) {
        Pizza pizza = new Pizza.Builder(5).bacon(true).pepperoni(true).cheese(false).build();
    }
}

