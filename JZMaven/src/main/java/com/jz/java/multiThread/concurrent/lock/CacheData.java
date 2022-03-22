/**
 * @Author jzfeng
 * @Date 3/22/22-1:23 AM
 */

package com.jz.java.multiThread.concurrent.lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁内部维护了两个锁，一个用于读操作，一个用于写操作。
 * 所有 ReadWriteLock实现都必须保证 writeLock操作的内存同步效果也要保持与相关 readLock的联系。
 * 也就是说，成功获取读锁的线程会看到写入锁之前版本所做的所有更新。
 * <p>
 * 　　ReentrantReadWriteLock支持以下功能：
 * <p>
 * 　　　　1）支持公平和非公平的获取锁的方式；
 * 　　　　2）支持可重入。读线程在获取了读锁后还可以获取读锁；写线程在获取了写锁之后既可以再次获取写锁又可以获取读锁；
 * 　　　　3）还允许从写入锁降级为读取锁，其实现方式是：先获取写入锁，然后获取读取锁，最后释放写入锁。但是，从读取锁升级到写入锁是不允许的；
 * 　　　　4）读取锁和写入锁都支持锁获取期间的中断；
 * 　　　　5）Condition支持。仅写入锁提供了一个 Conditon 实现；读取锁不支持 Conditon ，readLock().newCondition() 会抛出 UnsupportedOperationException。
 */
//cachedata必须是线程安全的；
class CacheData {
    Object data;

    volatile boolean cacheValid;    //缓存是否有效

    ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    void processCachedData() {
        rwl.readLock().lock();    //获取读锁

        //如果缓存无效，更新cache;否则直接使用data
        if (!cacheValid) {
            // Must release read lock before acquiring write lock
            //获取写锁前须释放读锁
            rwl.readLock().unlock();
            rwl.writeLock().lock();

            // Recheck state because another thread might have acquired
            //   write lock and changed state before we did.
            if (!cacheValid) {
                data = new Object();
                cacheValid = true;
            }

            // Downgrade by acquiring read lock before releasing write lock
            //锁降级，在释放写锁前获取读锁
            rwl.readLock().lock();
            rwl.writeLock().unlock(); // Unlock write, still hold read
        }

        use(data);
        rwl.readLock().unlock();    //释放读锁
    }

    private void use(Object data) {
        System.out.println("Using data for specific task...");
    }
}
