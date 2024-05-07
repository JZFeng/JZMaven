package CapitalOne;

import java.util.*;

public class Bank {

    private static volatile Bank instance;

    Map<String, Account> accounts; // account table, (accountId, account)
    Map<String, List<Activity>> activities; //activities table, (accountId, Activity)
    PriorityQueue<Pair> pq; // (userId,transactionSum)
    static int DEFAULT_QUEUE_SIZE = Integer.MAX_VALUE / 2;

    private Bank() {
        this.accounts = new HashMap<>();
        this.activities = new HashMap<>();
        this.pq = new PriorityQueue<>(DEFAULT_QUEUE_SIZE, (a, b) -> (b.transactionSum - a.transactionSum == 0 ? a.accountId.compareTo(b.accountId) : (b.transactionSum - a.transactionSum > 0 ? 1 : -1)));
    }

    public static Bank getInstance() {
        if (instance == null) {
            synchronized (Bank.class) {
                if (instance == null) {
                    instance = new Bank();
                }
            }
        }
        return instance;
    }

    public boolean createAccount(String id, double balance) {
        if (!accounts.containsKey(id)) return false;
        accounts.put(id, new Account(id, balance));
        return true;
    }

    public double deposit(String id, double amount) {
        Account to = accounts.getOrDefault(id, null);
        return to.deposit(amount);
    }

    public double transfer(String fromId, String toId, double amount) {
        Account from = accounts.getOrDefault(fromId, null);
        if (from == null || from.balance < amount || amount <= 0) return -1;
        Account to = accounts.getOrDefault(toId, null);
        if (to == null || to == from) return -1;
        return from.transfer(to, amount);
    }

    public List<Pair> topActivities(int n) {
        List<Pair> res = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            res.add(pq.poll());
        }
        for (Pair p : res) pq.offer(p);

        return res;
    }
}


