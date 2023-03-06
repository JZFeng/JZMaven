package com.jz.algo;

class DSU {

    private int[] parent;
    private int count;

    public DSU(int n) {
        // initialize your data structure here.
        parent = new int[n];
        for (int i = 0; i < n; ++i) {
            parent[i] = i;
        }
    }

    private int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }


    public void union(int x, int y) {
        int root_a = find(x);
        int root_b = find(y);
        if (root_a != root_b) {
            parent[root_a] = root_b;
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