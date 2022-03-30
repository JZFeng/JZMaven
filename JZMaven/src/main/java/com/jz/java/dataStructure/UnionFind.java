package com.jz.java.dataStructure;

class UnionFind {

    private int[] father = null;
    private int count;

    public UnionFind(int n) {
        // initialize your data structure here.
        father = new int[n];
        for (int i = 0; i < n; ++i) {
            father[i] = i;
        }
    }

    private int find(int x) {
        if (father[x] == x) {
            return x;
        }
        return father[x] = find(father[x]);
    }


    public void connect(int a, int b) {
        int root_a = find(a);
        int root_b = find(b);
        if (root_a != root_b) {
            father[root_a] = root_b;
            count--;
        }
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}