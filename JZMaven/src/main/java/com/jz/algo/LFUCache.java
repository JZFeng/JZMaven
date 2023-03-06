/**
 * @Author jzfeng
 * @Date 2/25/22-9:34 AM
 */

package com.jz.algo;

import java.util.*;

public class LFUCache {

    //因为是(key，val) pair,底层肯定是用Map
    //要为每一个key记录freq；同时freq相同的时候，还需要考虑访问时间的先后；
    //访问时间的先后，我们在LRU做过，用的是Map和DoubleLinkedList
    //LFU实际上是在LRU基础了多了一个freq的维度；
    //Map<Integer, ListNode> 还是key对应结点，实现快速删除；
    // Map<Integer, DoubleLinkedList<ListNode>> frequency Map，key是freq，value是链表; 相当于一个个Bucket桶；

    private Map<Integer, ListNode> cache;
    private Map<Integer, DoubleLinkedList> freqMap;
    private Integer capacity;
    private Integer minFreq = 1;//存储最小的freq以便空间满的时候删除节点时使用；


    public LFUCache(int capacity) {
        // 显式设置哈希表的长度 = capacity 和加载因子 = 1 是为了防止哈希表扩容带来的性能消耗
        // 这一步操作在理论上的可行之处待讨论，实验下来效果是比较好的
        cache = new HashMap<>(capacity, 1);
        freqMap = new HashMap<>();
        this.capacity = capacity;
    }

    public int get(int key) {
        if (capacity == 0) {
            return -1;
        }

        if (!cache.containsKey(key)) {
            return -1;
        }

        ListNode node = cache.get(key);
        int freq = node.freq;

        //从freq这个桶里删掉，然后放到freq+1那个桶里；
        //此处记得更新minFreq；
        DoubleLinkedList dll = freqMap.get(freq);
        dll.remove(node);
        if (freq == minFreq && dll.size() == 0) {
            minFreq++;
        }

        node.freq = node.freq + 1;
        if (!freqMap.containsKey(node.freq)) {
            freqMap.put(node.freq, new DoubleLinkedList());
        }
        freqMap.get(node.freq).addFirst(node);

        return node.value;
    }

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
            DoubleLinkedList minFreqList = freqMap.get(minFreq);
            ListNode removeNode = minFreqList.removeLast();

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

    private class DoubleLinkedList {
        private ListNode head;
        private ListNode tail;
        private int size;

        public DoubleLinkedList() {
            // 虚拟头尾结点赋值多少无所谓
            this.head = new ListNode(Integer.MIN_VALUE, Integer.MAX_VALUE);
            this.tail = new ListNode(Integer.MAX_VALUE, Integer.MIN_VALUE);

            head.next = tail;
            tail.pre = head;
            size = 0;
        }

        //把一个结点类添加到双向链表的开头（头部是最新使用数据);
        public ListNode addFirst(ListNode node) {
            if (node != null) {
                ListNode oldFirst = head.next;
                head.next = node;
                oldFirst.pre = node;
                node.pre = head;
                node.next = oldFirst;
                size++;
            }
            return node;
        }

        //把双向链表的末尾结点删除（尾部是最旧的数据，在缓存满的时候淘汰）
        public ListNode removeLast() {
            if (size > 0) {
                ListNode oldLast = tail.pre;
                oldLast.pre.next = tail;
                tail.pre = oldLast.pre;
                oldLast.pre = null;
                oldLast.next = null;

                size--;
                return oldLast;
            }

            return null;
        }

        public ListNode remove(ListNode node) {
            if (node != null) {
                node.pre.next = node.next;
                node.next.pre = node.pre;
                node.next = null;
                node.pre = null;
                size--;
                return node;
            }

            return null;
        }

        public int size() {
            return size;
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
