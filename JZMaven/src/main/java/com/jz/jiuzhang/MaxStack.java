/**
 * @Author jzfeng
 * @Date 2/26/22-4:48 PM
 */

package com.jz.jiuzhang;

import java.util.*;

//我们做过min stack用的是双栈。但这里有个不同的是，多了一个popMax();
//从maxStack里pop一个，必须在stack里找到这个值，然后删掉，时间复杂度为O（N）了；
//如何都变成O（1）呢？
//参照LRU，自定义DoubleLinkedList<>可以模拟堆栈，存放主要数据;
//Map<>, key为数字，value为该key对应的节点列表；TreeMap 的keyset已排序，可以用于返回最大值；
class MaxStack {
    TreeMap<Integer, List<ListNode>> map; //key为值，value为结点列表；
    DoubleLinkedList dll;

    public MaxStack() {
        map = new TreeMap();
        dll = new DoubleLinkedList();
    }

    public void push(int x) {
        ListNode node = dll.add(x);
        if (!map.containsKey(x)) {
            map.put(x, new ArrayList<ListNode>());
        }
        map.get(x).add(node);
    }

    public int pop() {
        int val = dll.pop();
        List<ListNode> list = map.get(val);
        list.remove(list.size() - 1); //移除最后一个；
        if (list.isEmpty()) {
            map.remove(val);
        }

        return val;
    }

    public int top() {
        return dll.peek();
    }

    public int peekMax() {
        return map.lastKey();
    }

    public int popMax() {
        int max = peekMax();
        List<ListNode> list = map.get(max);
        ListNode node = list.remove(list.size() - 1);
        if (list.isEmpty()) {
            map.remove(max);
        }
        dll.remove(node);

        return max;
    }
}


class ListNode {
    int val;
    ListNode pre, next;

    public ListNode(int v) {
        val = v;
    }
}

class DoubleLinkedList {
    ListNode head, tail;

    public DoubleLinkedList() {
        head = new ListNode(0);
        tail = new ListNode(0);
        head.next = tail;
        tail.pre = head;
    }

    //插入节点放到列表尾部；
    public ListNode add(int val) {
        ListNode x = new ListNode(val);
        x.next = tail;
        x.pre = tail.pre;
        tail.pre = tail.pre.next = x;
        return x;
    }

    public ListNode push(int val) {
        return add(val);
    }

    public int pop() {
        return remove(tail.pre).val;
    }

    public int peek() {
        return tail.pre.val;
    }

    public ListNode remove(ListNode node) {
        node.pre.next = node.next;
        node.next.pre = node.pre;
        node.pre = null;
        node.next = null;
        return node;
    }


}

