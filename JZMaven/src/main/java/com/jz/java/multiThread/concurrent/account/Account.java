/**
 * @Author jzfeng
 * @Date 9/27/20-2:16 PM
 */

package com.jz.java.multiThread.concurrent.account;

import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account {
    private String name;
    private double balance;

    private Lock lock = new ReentrantLock();

    public double getBalance() {
        return balance;
    }

/*    public void setBalance(double balance) {
        this.balance = balance;
    }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Account{" +
                "name='" + name + '\'' +
                ", balance=" + balance +
                '}';
    }

    public Account(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }

    public void deposit(double amount) {
        try {
            if (amount > 0) {
                lock.lock();
                balance += amount;
            } else {
                System.out.println("存入金额必须为正数！");
            }
        } finally {
            lock.unlock();
        }
    }

    public void withdraw(double amount) {

        if (amount > 0) {
            try {
                Thread.sleep(100);
                lock.lock();
                if (balance >= amount) {
                    balance -= amount;
                    System.out.println(Thread.currentThread().getName() + "取走" + amount + "，账户余额为" + balance);
                } else {
                    System.out.println(Thread.currentThread().getName() + "取钱，但余额不足！");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        } else {
            System.out.println("取款金额必须为正！");
        }
    }

    public Account() {
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, balance);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof Account) {
            return false;
        }

        Account account = (Account) obj;

        return name.equals(account.name) && balance == account.balance;
    }

}
