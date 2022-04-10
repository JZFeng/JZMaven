package com.jz.jiuzhang;


import java.util.*;

//用一个变量存下个元素，即可搞定啦 可用iterator hasNext(), 和next()
class PeekingIterator implements Iterator<Integer> {
    Iterator<Integer> itr;
    Integer cur;

    public PeekingIterator(Iterator<Integer> iterator) {
        // initialize any member here.
        this.itr = iterator;
        cur = (itr.hasNext() ? itr.next() : null);
    }

    // Returns the next element in the iteration without advancing the iterator.
    public Integer peek() {
        return cur;
    }

    // hasNext() and next() should behave the same as in the Iterator interface.
    // Override them if needed.
    @Override
    public Integer next() {
        Integer next = null;
        if (hasNext()) {
            next = cur;
            cur = (itr.hasNext() ? itr.next() : null);
        }

        return next;
    }

    @Override
    public boolean hasNext() {
        return cur == null ? false : true;
    }
}

