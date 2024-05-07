package CapitalOne;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class Account implements IAccount {
    String id;
    double balance;
    double transactionSum;

    Account(String id, double balance) {
        this.id = id;
        this.balance = balance;
        this.transactionSum = 0;
    }

    @Override
    public double deposit(double amount) {
        if(amount <= 0d) return -1;
        this.balance += amount;
        this.transactionSum += amount;
        //send msg to msg queue;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bank.getInstance()
                        .activities
                        .computeIfAbsent(id, k ->new ArrayList<Activity>())
                        .add(Activity.builder().userId(id).date(new Date()).availableBalance(balance).amount(amount).transactionType(TransactionType.DEPOSIT).build());
                Bank.getInstance().pq.offer(new Pair(id, transactionSum));
            }
        }).start();

        return balance;
    }

    @Override
    public double transfer(IAccount target, double amount) {
        if(amount <= 0 || amount > balance) return -1;
        if(target == null || !(target instanceof Account)) return -1;
        Account t = (Account)target;
        if(! Bank.getInstance().activities.containsKey(t.id)  ) return -1;

        balance -= amount;
        transactionSum += amount;
        t.balance += amount;
        t.transactionSum +=amount;
        //send msg to msg queue;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bank.getInstance()
                        .activities
                        .computeIfAbsent(id, k ->new ArrayList<Activity>())
                        .add(Activity.builder().userId(id).date(new Date()).availableBalance(balance).amount(amount).transactionType(TransactionType.TRANSFER).build());
                Bank.getInstance().pq.offer(new Pair(id, transactionSum));
                Bank.getInstance()
                        .activities
                        .computeIfAbsent(t.id, k ->new ArrayList<Activity>())
                        .add(Activity.builder().userId(t.id).date(new Date()).availableBalance(t.balance).amount(amount).transactionType(TransactionType.DEPOSIT).build());
                Bank.getInstance().pq.offer(new Pair(id, transactionSum));
            }
        }).start();

        return balance;
    }

    public List<Activity> getActivities(){
        return Bank.getInstance().activities.getOrDefault(id, new ArrayList<>());
    }

}
