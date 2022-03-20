package com.jz.java.dataStructure.binaryHeap;

public class BinaryMinHeap {
    private int[] data;

    private int heapSize;

    public BinaryMinHeap(int size) {
        data = new int[size];
        heapSize = 0;
    }

    /*
     * add some comments
     * ***/

    public BinaryMinHeap(int[] items) {
        if (items == null || items.length == 0) {
            throw new IllegalArgumentException();
        } else {
            heapSize = items.length;
            data = new int[heapSize];

            for (int i = 0; i < heapSize; i++) {
                data[i] = items[i];
            }
        }

        buildHeap();
    }

    public void buildHeap() {
        for (int i = heapSize / 2 - 1; i >= 0; i--) {
            siftDown(i);
        }

    }

    public int getMinimum() {
        if (isEmpty())
            throw new HeapException("Heap is empty");
        else
            return data[0];
    }

    public boolean isEmpty() {
        return (heapSize == 0);
    }

    private int getLeftChildIndex(int nodeIndex) {
        return 2 * nodeIndex + 1;
    }

    private int getRightChildIndex(int nodeIndex) {
        return 2 * nodeIndex + 2;
    }

    private int getParentIndex(int nodeIndex) {
        return (nodeIndex - 1) / 2;
    }

    public class HeapException extends RuntimeException {
        public HeapException(String message) {
            super(message);
        }
    }

    public void insert(int value) {
        if (heapSize == data.length)
            throw new HeapException("Heap's underlying storage is overflow");
        else {
            heapSize++;
            data[heapSize - 1] = value;
            siftUp(heapSize - 1);
        }
    }

    private void siftUp(int nodeIndex) {
        int parentIndex, tmp;
        if (nodeIndex != 0) {
            parentIndex = getParentIndex(nodeIndex);
            if (data[parentIndex] > data[nodeIndex]) {
                tmp = data[parentIndex];
                data[parentIndex] = data[nodeIndex];
                data[nodeIndex] = tmp;
                siftUp(parentIndex);
            }
        }
    }

    public void removeMin() {
        if (isEmpty())
            throw new HeapException("Heap is empty");
        else {
            data[0] = data[heapSize - 1];
            heapSize--;
            if (heapSize > 0)
                siftDown(0);
        }
    }


    /**
     * Sift down root's value. Sifting is done as following:
     * if current node has no children, sifting is over;
     * if current node has one child: check, if heap property is broken, then swap current node's value and child value; sift down the child;
     * if current node has two children: find the smallest of them. If heap property is broken, then swap current node's value and selected child value; sift down the child.
     * <p>
     * Find the smallest element of the two childrens.
     */
    private void siftDown(int nodeIndex) {
        int leftChildIndex, rightChildIndex, minIndex, tmp;

        leftChildIndex = getLeftChildIndex(nodeIndex);
        rightChildIndex = getRightChildIndex(nodeIndex);

        if (rightChildIndex >= heapSize) //no right child
        {
            if (leftChildIndex >= heapSize) // no left child
                return;
            else
                minIndex = leftChildIndex;
        } else {
            if (data[leftChildIndex] <= data[rightChildIndex])
                minIndex = leftChildIndex;
            else
                minIndex = rightChildIndex;
        }

        //find minIndex

        if (data[nodeIndex] > data[minIndex]) {
            tmp = data[minIndex];
            data[minIndex] = data[nodeIndex];
            data[nodeIndex] = tmp;
            siftDown(minIndex);
        }
    }

    /* non-recursion implement. Plus no swap.* */
    private void insert1(int value) {
        if (heapSize == data.length)
            throw new HeapException("Heap's underlying storage is overflow");
        else {

            heapSize++;
            int hole = heapSize - 1;

            while (hole > 0 && value < data[(hole - 1) / 2]) {
                data[hole] = data[(hole - 1) / 2];
                hole = (hole - 1) / 2;

            }

            data[hole] = value;
        }

    }

    public void printHeap() {
        for (int i : data) {
            System.out.print(" " + i);
        }
    }


    public static void main(String args[]) {
        int[] items =
                {4, 12, 5, 11, 78, 6, 17};
        // BinaryMinHeap bheap = new BinaryMinHeap(items);

        BinaryMinHeap bheap = new BinaryMinHeap(items.length);
        for (int i = 0; i < items.length; i++) {

            bheap.insert(items[i]);

        }

        bheap.printHeap();

    }
}