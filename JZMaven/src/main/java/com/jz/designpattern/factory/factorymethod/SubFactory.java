package com.jz.designpattern.factory.factorymethod;

public class SubFactory implements Factory {
  @Override
  public Operation createOperation(double... nums) {
    return new Sub(nums[0], nums[1]);
  }
}
