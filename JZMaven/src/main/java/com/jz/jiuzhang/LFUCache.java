/**
 * @Author jzfeng
 * @Date 2/25/22-9:34 AM
 */

package com.jz.jiuzhang;

import java.util.*;

public class LFUCache {

    /**
     * key 就是题目中的 key
     * value 是结点类
     */
    private Map<Integer, ListNode> cache;

    /**
     * 访问次数哈希表，使用 ListNode[] 也可以，不过要占用很多空间
     */
    private Map<Integer, DoubleLinkedList> freqMap;

    /**
     * 外部传入的容量大小
     */
    private Integer capacity;

    /**
     * 全局最高访问次数，删除最少使用访问次数的结点时会用到
     */
    private Integer minFreq = 1;


    public LFUCache(int capacity) {

        // 显式设置哈希表的长度 = capacity 和加载因子 = 1 是为了防止哈希表扩容带来的性能消耗
        // 这一步操作在理论上的可行之处待讨论，实验下来效果是比较好的
        cache = new HashMap<>(capacity, 1);
        freqMap = new HashMap<>();
        this.capacity = capacity;
    }

    /**
     * get 一次操作，访问次数就增加 1；
     * 从原来的链表调整到访问次数更高的链表的表头
     *
     * @param key
     * @return
     */
    public int get(int key) {
        // 测试测出来的，capacity 可能传 0
        if (capacity == 0) {
            return -1;
        }

        if (cache.containsKey(key)) {
            // 获得结点类
            ListNode listNode = removeListNodeByKey(key);

            // 挂接到新的访问次数的双向链表的头部
            int freq = listNode.freq;
            addListNodeByFreq(freq, listNode);

            return listNode.value;
        } else {
            return -1;
        }
    }

    /**
     * @param key
     * @param value
     */
    public void put(int key, int value) {
        if (capacity == 0) {
            return;
        }

        // 如果 key 存在，就更新访问次数 + 1，更新值
        if (cache.containsKey(key)) {
            ListNode listNode = removeListNodeByKey(key);

            // 更新 value
            listNode.value = value;
            int frequent = listNode.freq;
            addListNodeByFreq(frequent, listNode);
            return;
        }

        // 如果 key 不存在
        // 1、如果满了，先删除访问次数最小的的末尾结点，再删除 map 里对应的 key
        if (cache.size() == capacity) {
            // 1、从双链表里删除结点
            DoubleLinkedList doubleLinkedList = freqMap.get(minFreq);
            ListNode removeNode = doubleLinkedList.removeLast();

            // 2、删除 map 里对应的 key
            cache.remove(removeNode.key);
        }

        // 2、再创建新结点放在访问次数为 1 的双向链表的前面
        ListNode newListNode = new ListNode(key, value);
        addListNodeByFreq(1, newListNode);
        cache.put(key, newListNode);

        // 【注意】因为这个结点是刚刚创建的，最少访问次数一定为 1
        this.minFreq = 1;
    }

    // 以下部分主要是结点类和双向链表的操作

    /**
     * 结点类，是双向链表的组成部分
     */
    private class ListNode {
        private int key;
        private int value;
        private int freq = 1;
        private ListNode pre;
        private ListNode next;

        public ListNode() {
        }

        public ListNode(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    /**
     * 双向链表
     */
    private class DoubleLinkedList {
        /**
         * 虚拟头结点，它无前驱结点
         */
        private ListNode first;
        /**
         * 虚拟尾结点，它无后继结点
         */
        private ListNode last;

        /**
         * 当前双向链表的有效结点数
         */
        private int size;

        public DoubleLinkedList() {
            // 虚拟头尾结点赋值多少无所谓
            this.first = new ListNode(-1, -1);
            this.last = new ListNode(-2, -2);

            first.next = last;
            last.pre = first;
            size = 0;
        }

        /**
         * 把一个结点类添加到双向链表的开头（头部是最新使用数据）
         *
         * @param node
         */
        public void addFirst(ListNode node) {
            ListNode oldFirst = first.next;
            // 两侧结点指向它
            first.next = node;
            oldFirst.pre = node;
            // 它的前驱和后继指向两侧结点
            node.pre = first;
            node.next = oldFirst;
            size++;
        }

        /**
         * 把双向链表的末尾结点删除（尾部是最旧的数据，在缓存满的时候淘汰）
         *
         * @return
         */
        public ListNode removeLast() {
            ListNode oldLast = last.pre;
            ListNode last = oldLast.pre;

            // 两侧结点建立连接
            last.next = this.last;
            this.last.pre = last;

            // 它的两个属性切断连接
            oldLast.pre = null;
            oldLast.next = null;

            // 重要：删除一个结点，当前双向链表的结点个数少 1
            size--;

            // 维护
            return oldLast;
        }
    }


    /**
     * 将原来访问次数的结点，从双向链表里脱离出来
     *
     * @param key
     * @return
     */
    private ListNode removeListNodeByKey(int key) {
        // 获得结点类
        ListNode deleteNode = cache.get(key);

        ListNode preNode = deleteNode.pre;
        ListNode nextNode = deleteNode.next;
        // 两侧结点建立连接
        preNode.next = nextNode;
        nextNode.pre = preNode;
        // 删除去原来两侧结点的连接
        deleteNode.pre = null;
        deleteNode.next = null;

        // 维护双链表结点数
        freqMap.get(deleteNode.freq).size--;

        // 【注意】维护 minFrequent
        // 如果当前结点正好在最小访问次数的链表上，并且移除以后结点数为 0，最小访问次数需要加 1
        if (deleteNode.freq == minFreq && freqMap.get(deleteNode.freq).size == 0) {
            // 这一步需要仔细想一下，经过测试是正确的
            minFreq++;
        }

        // 访问次数加 1
        deleteNode.freq++;

        return deleteNode;
    }


    /**
     * 把结点放在对应访问次数的双向链表的头部
     *
     * @param freq
     * @param addNode
     */
    private void addListNodeByFreq(int freq, ListNode addNode) {
        DoubleLinkedList doubleLinkedList;

        // 如果不存在，就初始化
        if (freqMap.containsKey(freq)) {
            doubleLinkedList = freqMap.get(freq);
        } else {
            doubleLinkedList = new DoubleLinkedList();
        }

        // 添加到 DoubleLinkedList 的表头
        doubleLinkedList.addFirst(addNode);
        freqMap.put(freq, doubleLinkedList);
    }

    public static void main(String[] args) {
        LFUCache cache = new LFUCache(2);

        cache.put(1, 1);
        cache.put(2, 2);

        System.out.println(cache.cache.keySet());

        int res1 = cache.get(1);
        System.out.println(res1);

        cache.put(3, 3);
        System.out.println(cache.cache.keySet());

        int res2 = cache.get(2);
        System.out.println(res2);

        int res3 = cache.get(3);
        System.out.println(res3);

        cache.put(4, 4);
        System.out.println(cache.cache.keySet());

        int res4 = cache.get(1);
        System.out.println(res4);

        int res5 = cache.get(3);
        System.out.println(res5);

        int res6 = cache.get(4);
        System.out.println(res6);
    }
}
