/**
 * @Author jzfeng
 * @Date 5/11/22-10:38 PM
 */

package com.jz.java.dataStructure.dsu;

import java.util.Arrays;

public class DSUWithWeight {
    int[] parent;
    int[] size; //size代表此节点作为集合包含了多少个元素；

    DSUWithWeight(int n) {
        this.parent = new int[n];
        this.size = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
        Arrays.fill(size, 1);
    }

    public int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }

    public void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        if(rootX == rootY) {
            return;
        }
        if (size[rootX] >= size[rootY]) {
            parent[rootY] = rootX;
            size[rootX] += size[rootY];
        } else {
            parent[rootX] = rootY;
            size[rootY] += size[rootX];
        }
    }

    public int findMax() {
        return size.length == 0 ? 0 : Arrays.stream(size).max().getAsInt();
    }
}
