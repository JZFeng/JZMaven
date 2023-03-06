package com.jz.algo;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

//经典的LRUCache, 双向链表+ hashmap；
//node里要有key，value，pre，next；
//hashmap存放的是key和node，根据key，O(1)时间访问到node，然后移动node的位置（此处实现为，链表头部为最近访问过的节点）
public class LRUCache {
    Map<Integer, ListNode> map = null;
    int capacity = 0;
    DoubleLinkedList dll;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<Integer, ListNode>();
        dll = new DoubleLinkedList();
    }

    public int get(int key) {
        if (!map.containsKey(key)) {
            return -1;
        }

        ListNode node = map.get(key);
        //把当前节点删除；
        dll.remove(node);
        dll.addFirst(node);

        return node.val;
    }

    public void put(int key, int value) {
        //get函数本身就已经处理了移动到头部；
        if (get(key) != -1) {
            map.get(key).val = value;
            return;
        }

        ListNode node = new ListNode(key, value);
        //删除最后一个元素；
        // map.size() >= capacity 或是dll.size >= capacity 都可以；
        if (dll.size() >= capacity) {
            ListNode last = dll.removeLast();
            map.remove(last.key);
        }

        map.put(key, node);
        dll.addFirst(node);
    }

    class DoubleLinkedList {
        ListNode head;
        ListNode tail;
        int size;

        DoubleLinkedList() {
            head = new ListNode(Integer.MIN_VALUE, Integer.MAX_VALUE);
            tail = new ListNode(Integer.MIN_VALUE, Integer.MAX_VALUE);
            head.next = tail;
            tail.pre = head;
            size = 0;
        }

        private ListNode addFirst(ListNode node) {
            if (node != null) {
                ListNode oldFirst = head.next;
                oldFirst.pre = node;
                node.next = oldFirst;
                node.pre = head;
                head.next = node;
                size++;
            }

            return node;
        }

        public ListNode removeLast() {
            if (size > 0) {
                ListNode toDelete = tail.pre;
                tail.pre = toDelete.pre;
                tail.pre.next = tail;
                toDelete.pre = null;
                toDelete.next = null;
                size--;
                return toDelete;
            }
            return null;
        }

        public ListNode remove(ListNode node) {
            if (node != null) {
                node.pre.next = node.next;
                node.next.pre = node.pre;
                node.pre = null;
                node.next = null;
                size--;
                return node;
            }
            return null;
        }

        public int size() {
            return this.size;
        }

    }

    class ListNode {
        int key;
        int val;
        ListNode pre;
        ListNode next;

        ListNode(int key, int val) {
            this.key = key;
            this.val = val;
            pre = null;
            next = null;
        }
    }

    //使用LinkedHashMap可以轻松实现LRUCache
    class LRUCache1<K, V> extends LinkedHashMap<K, V> {
        private int cacheSize;

        public LRUCache1(int cacheSize) {
            //设置为true，则是按照accessOrder；
            super(cacheSize, 0.75f, true);
            this.cacheSize = cacheSize;
        }

        protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
            return size() >= cacheSize;
        }
    }
}