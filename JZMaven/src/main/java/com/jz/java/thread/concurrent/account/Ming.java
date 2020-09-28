/**
 * @Author jzfeng
 * @Date 9/27/20-2:48 PM
 */

package com.jz.java.thread.concurrent.account;

public class Ming extends Thread {
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

    public Ming(String name) {
        super(name);
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            account.withdraw(amount);
        }
    }
}
