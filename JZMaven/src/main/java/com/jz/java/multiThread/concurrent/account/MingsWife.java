/**
 * @Author jzfeng
 * @Date 9/27/20-3:13 PM
 */

package com.jz.java.multiThread.concurrent.account;

public class MingsWife extends Thread {

    private Account account;
    private double amount = 1000d;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public MingsWife(String name) {
        super(name);
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            account.withdraw(amount);
        }
    }
}
