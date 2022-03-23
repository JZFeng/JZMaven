/**
 * @Author jzfeng
 * @Date 9/27/20-7:57 AM
 */

package com.jz.java.multiThread.concurrent.account;

public class Entry {
    public static void main(String[] args) {

        Account account = new Account("小明家的农行账户", 10000d);
        System.out.println(account);

        Ming ming = new Ming("小明");
        ming.setAccount(account);

        MingsWife mingsWife = new MingsWife("小明媳妇");
        mingsWife.setAccount(account);

        mingsWife.start();
        ming.start();
    }
}
