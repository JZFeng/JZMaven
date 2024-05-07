package CapitalOne;

interface IAccount {
    double deposit(double amount);
    double transfer(IAccount target, double amount);
}
