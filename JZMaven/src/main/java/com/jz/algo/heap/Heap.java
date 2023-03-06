/**
 * @Author jzfeng
 * @Date 5/12/22-9:26 AM
 */

package com.jz.algo.heap;

public class Heap {
    int[] queue;
    int size;
    int DEFAULT_CAPACITY = 100;

    Heap() {
        this.queue = new int[DEFAULT_CAPACITY];
    }

    Heap(int capacity) {
        this.queue = new int[capacity];
    }

    //递归方式实现，如果iterative，while(index != 0)
    private void siftUp(int index) {
        if (index > 0) {
            int parent = (index - 1) / 2;
            if (queue[parent] > queue[index]) {
                swap(parent, index);
                siftUp(parent);
            }
        }
    }

    //递归方式实现；如果iterative，while(true)
    private void siftDown(int index) {
        int left = index * 2 + 1;
        int right = index * 2 + 2;
        int smallest = index;
        if (left < size && queue[left] < queue[smallest]) {
            smallest = left;
        }
        if (right < size && queue[right] < queue[smallest]) {
            smallest = right;
        }
        if (smallest != index) {
            swap(smallest, index);
            siftDown(smallest);
        }
    }

    private void swap(int i, int j) {
        int tmp = queue[i];
        queue[i] = queue[j];
        queue[j] = tmp;
    }

    public boolean offer(int val) {
        if (size < queue.length) {
            queue[size] = val;
            siftUp(size);
            size++;
            return true;
        }
        return false;
    }

    public Integer poll() {
        Integer res = null;
        if (size > 0) {
            res = queue[0];
            queue[0] = queue[size - 1];
            size--;
            siftDown(0);
        }

        return res;
    }

    public Integer peek() {
        if (size > 0) {
            return queue[0];
        }
        return null;
    }


}
