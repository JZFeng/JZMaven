package com.jz.java.designpattern.chain;

public class FeeRequest {
  private String user;
  private int fee;

  public FeeRequest(String user, int fee) {
    this.user = user;
    this.fee = fee;
  }

  public int getFee() {
    return fee;
  }

  public String getUser() {
    return user;
  }

}
