package com.jz.designpattern.factory.factorymethod;

public class AddFactory implements Factory {

  @Override
  public Operation createOperation(double... nums) {
    return new Add(nums[0], nums[1]);
  }


}
