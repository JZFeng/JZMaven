package com.jz.designpattern.builder;

public class Pizza {
    private int size;
    private boolean hasMeat;
    private boolean hasPepperoni;
    private boolean hasCheese;

    //构建一个复杂对象的时候，建议使用builder模式。
    //构造函数定义为private；
    private Pizza(Builder builder) {
        this.size = builder.size;
        this.hasCheese = builder.hasCheese;
        this.hasMeat = builder.hasMeat;
        this.hasPepperoni = builder.hasPepperoni;
    }

    //public的静态内部类；
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
            //检查所有的参数；
            //eg , size must be equal or greater than 6 inch;
            if (size < 6 || hasCheese == false) {
                throw new IllegalArgumentException("Minimum Pizza size : 6 inch; must have cheese");
            }

            return new Pizza(this);
        }
    }

}
