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
    Node head;
    Node tail;
    Map<String, Integer> key2count; // key:单词；value：对应的次数；
    Map<Integer, Node> count2node; //key：次数；value：对应的节点；

    /**
     * Initialize your data structure here.
     */
    public AllOne() {
        head = new Node(-1);
        tail = new Node(-1);
        head.next = tail;
        tail.pre = head;
        key2count = new HashMap<String, Integer>();
        count2node = new HashMap<Integer, Node>();
    }

    /**
     * Inserts a new key <Key> with value 1. Or increments an existing key by 1.
     */
    public void inc(String key) {
        if (key2count.containsKey(key)) {
            moveKey(key, 1);
        } else {
            key2count.put(key, 1);
            if (head.next.count != 1) {
                addBucketAfter(new Node(1), head);
            }
            head.next.keySet.add(key);
            count2node.put(1, head.next);
        }
    }

    /**
     * Decrements an existing key by 1. If Key's value is 1, remove it from the data structure.
     */
    public void dec(String key) {
        if (key2count.containsKey(key)) {
            int count = key2count.get(key);
            if (count == 1) {
                key2count.remove(key);
                removeKeyFromBucket(count2node.get(count), key);
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
        int count = key2count.get(key);
        key2count.put(key, count + offset);

        //找到对应的节点Bucket;
        Node cur = count2node.get(count);
        Node moveToNode;
        if (count2node.containsKey(count + offset)) {
            moveToNode = count2node.get(count + offset);
        } else {
            moveToNode = new Node(count + offset);
            count2node.put(count + offset, moveToNode);
            addBucketAfter(moveToNode, offset == 1 ? cur : cur.pre);
        }

        moveToNode.keySet.add(key);
        removeKeyFromBucket(cur, key);
    }

    private void addBucketAfter(Node newNode, Node preNode) {
        newNode.next = preNode.next;
        newNode.pre = preNode;
        preNode.next.pre = newNode;
        preNode.next = newNode;
    }

    private void removeKeyFromBucket(Node cur, String key) {
        cur.keySet.remove(key);
        if (cur.keySet.size() == 0) {
            count2node.remove(cur.count);
            cur.pre.next = cur.next;
            cur.next.pre = cur.pre;
        }
    }

    /**
     * 相当于一个Bucket
     */
    class Node {
        int count;
        Set<String> keySet;
        Node pre;
        Node next;

        public Node(int count) {
            this.count = count;
            keySet = new LinkedHashSet<String>();
        }
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