package com.jz.java.collection;

import com.sun.istack.internal.NotNull;

import java.util.Iterator;

public class ReadOnlyList<E> implements Iterable<E> {

    private E[] array;

    ReadOnlyList(E... array) {
        this.array = array;
    }

    @Override @NotNull
    public Iterator<E> iterator() {
        return new ReadOnlyListIterator();
    }

    // inner class
    public class ReadOnlyListIterator implements Iterator<E> {

        int index;

        @Override
        public boolean hasNext() {
            return index < array.length;
        }

        @Override
        public E next() {
            return array[index++];
        }
    }
}

