package com.jz.designpattern.factory.factorymethod;

public interface Factory {
  Operation createOperation(double... nums);
}
