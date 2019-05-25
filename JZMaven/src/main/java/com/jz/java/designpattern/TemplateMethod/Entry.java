package com.jz.java.designpattern.TemplateMethod;

public class Entry {
  public static void  main(String[] args) {
    double money = 9999;
    BankBusiness deposit = new Deposit(money);
    deposit.process();

    money = 100000;
    BankBusiness withdraw = new Withdraw(money);
    withdraw.process();

  }
}
