package com.jz.designpattern.templateMethod;

public abstract class BankBusiness {

  public double money;

  BankBusiness(double money) {
    this.money = money;
  }

  //template method
  public final void process() {
    if(!isVIP()) {
      getNumber();
    }

    handleBusiness(money);
    review();
  }

  private void getNumber() {
    System.out.println("Get a number");
  }
  private void review() {
    System.out.println("Please give a review.");
  }

  //abstract method;
  abstract void  handleBusiness(double money);

  //hook
  abstract boolean isVIP();

}


