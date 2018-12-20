package com.jz.designpattern.TemplateMethod;

public class Deposit extends BankBusiness {

  Deposit(double money) {
    super(money);
  }

  @Override
  void handleBusiness(double money) {
    System.out.println("Deposit $" + money);
  }

  @Override
  boolean isVIP() {
    if((money - 9999) > 0.0000001) {
      return true;
    }

    return false;
  }
}
