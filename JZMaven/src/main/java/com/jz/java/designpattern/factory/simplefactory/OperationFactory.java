package com.jz.java.designpattern.factory.simplefactory;

public class OperationFactory {
    private Operation operation;

    //varargs
    //case statement could have control logic.
    public static Operation getOperation(String str, double... nums) {
        Operation result = null;
        if(str == null || str.length() == 0) {
            return result;
        }

        switch(str) {
            case "+" :
                result = new Add(nums[0], nums[1]);
                break;
            case "-" :
//              result =  ((nums.length == 2 ) ? new Sub(nums[0], nums[1]) : new Neg(nums[0]) );
                if(nums.length == 1) {
                    result = new Neg(nums[0]);
                } else if (nums.length >= 2) {
                    result = new Sub(nums[0], nums[1]);
                }

                break;
            case "*" :
                result = new Mul(nums[0], nums[1]);
                break;
            case "/" :
                result = new Div(nums[0], nums[1]);
                break;
            case "%" :
                result = new Mod(nums[0], nums[1]);
                break;

            default:
                result = null;
                break;
        }


        return result;
    }
}
