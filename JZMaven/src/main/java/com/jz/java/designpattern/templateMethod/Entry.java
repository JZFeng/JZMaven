package com.jz.java.designpattern.templateMethod;

public class Entry {
  public static void  main(String[] args) {
    double money = 10000;
    BankBusiness deposit = new Deposit(money);
    deposit.process();

    money = 100000;
    BankBusiness withdraw = new Withdraw(money);
    withdraw.process();

  }
}
