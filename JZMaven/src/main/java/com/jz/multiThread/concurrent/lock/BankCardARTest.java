/**
 * @Author jzfeng
 * @Date 2/21/23-11:17 AM
 */

package com.jz.multiThread.concurrent.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class BankCardARTest {

    private static AtomicReference<BankCard> bankCardRef = new AtomicReference<>(new BankCard("cxuan", 100));

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                while (true) {
                    // 使用 AtomicReference.get 获取
                    final BankCard card = bankCardRef.get();
                    BankCard newCard = new BankCard(card.getAccountName(), card.getMoney() + 100);
                    // 使用 CAS 乐观锁进行非阻塞更新
                    if (bankCardRef.compareAndSet(card, newCard)) {
                        System.out.println(newCard);
                    }
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}


