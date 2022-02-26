/**
 * @Author jzfeng
 * @Date 2/26/22-1:12 PM
 */

package com.jz.jiuzhang;

import java.util.*;

/*
很像LFU的机制.

用Bucket组成一个double linked list. 按照Bucket的count从小到大排序.

inc 把key move到下一个Bucket.

dec把key move到前一个Bucket.

min key就在前面, head.next. //因为返回任意一个，所以返回第一个即可；

max key就出现在后面, tail.pre.//因为返回任意一个，所以返回第一个即可；

Time Complexity: inc, dec, getaMaxKey, getMinKey, O(1).

Space: O(n). n是现有key的个数.
 */


public class AllOne {
    Bucket head;
    Bucket tail;
    Map<String, Integer> keyCount; // key:单词；value：对应的次数；
    Map<Integer, Bucket> countBucket; //key：次数；value：对应的节点；

    /**
     * Initialize your data structure here.
     */
    public AllOne() {
        head = new Bucket(-1);
        tail = new Bucket(-1);
        head.next = tail;
        tail.pre = head;
        keyCount = new HashMap<String, Integer>();
        countBucket = new HashMap<Integer, Bucket>();
    }

    /**
     * Inserts a new key <Key> with value 1. Or increments an existing key by 1.
     */
    public void inc(String key) {
        if (keyCount.containsKey(key)) {
            moveKey(key, 1);
        } else {
            keyCount.put(key, 1);
            if (head.next.count != 1) {
                addBucketAfter(new Bucket(1), head);
            }
            head.next.keySet.add(key);
            countBucket.put(1, head.next);
        }
    }

    /**
     * Decrements an existing key by 1. If Key's value is 1, remove it from the data structure.
     */
    public void dec(String key) {
        if (keyCount.containsKey(key)) {
            int count = keyCount.get(key);
            if (count == 1) {
                keyCount.remove(key);
                removeKeyFromBucket(countBucket.get(count), key);
            } else {
                moveKey(key, -1);
            }
        }
    }

    /**
     * Returns one of the keys with maximal value.
     */
    public String getMaxKey() {
        return tail.pre == head ? "" : tail.pre.keySet.iterator().next();
    }

    /**
     * Returns one of the keys with Minimal value.
     */
    public String getMinKey() {
        return head.next == tail ? "" : head.next.keySet.iterator().next();
    }

    private void moveKey(String key, int offset) {
        //更新次数；
        int count = keyCount.get(key);
        keyCount.put(key, count + offset);

        //找到对应的节点Bucket;
        Bucket cur = countBucket.get(count);
        Bucket moveToBucket;
        if (countBucket.containsKey(count + offset)) {
            moveToBucket = countBucket.get(count + offset);
        } else {
            moveToBucket = new Bucket(count + offset);
            countBucket.put(count + offset, moveToBucket);
            addBucketAfter(moveToBucket, offset == 1 ? cur : cur.pre);
        }

        moveToBucket.keySet.add(key);
        removeKeyFromBucket(cur, key);
    }

    private void addBucketAfter(Bucket newBucket, Bucket preBucket) {
        newBucket.next = preBucket.next;
        newBucket.pre = preBucket;
        preBucket.next.pre = newBucket;
        preBucket.next = newBucket;
    }

    private void removeKeyFromBucket(Bucket cur, String key) {
        cur.keySet.remove(key);
        if (cur.keySet.size() == 0) {
            countBucket.remove(cur.count);
            cur.pre.next = cur.next;
            cur.next.pre = cur.pre;
        }
    }
}

/**
 * 相当于一个Bucket
 */
class Bucket {
    int count;
    Set<String> keySet;
    Bucket pre;
    Bucket next;

    public Bucket(int count) {
        this.count = count;
        keySet = new LinkedHashSet<String>();
    }
}


/**
 * Your AllOne object will be instantiated and called as such:
 * AllOne obj = new AllOne();
 * obj.inc(key);
 * obj.dec(key);
 * String param_3 = obj.getMaxKey();
 * String param_4 = obj.getMinKey();
 */