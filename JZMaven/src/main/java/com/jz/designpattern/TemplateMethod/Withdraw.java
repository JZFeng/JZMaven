package com.jz.designpattern.TemplateMethod;

public class Withdraw extends BankBusiness {

  Withdraw(double money) {
    super(money);
  }

  @Override
  void handleBusiness(double money) {
    System.out.println("Withdraw $" + money);
  }

  @Override
  boolean isVIP() {
    if((money - 99999) > 0.000001) {
      return true;
    }

    return false;
  }
}
