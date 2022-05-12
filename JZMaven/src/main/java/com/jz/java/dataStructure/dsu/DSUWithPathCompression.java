/**
 * @Author jzfeng
 * @Date 5/11/22-10:35 PM
 */

package com.jz.java.dataStructure.dsu;

public class DSUWithPathCompression {
    int[] parent;

    DSUWithPathCompression(int n) {
        this.parent = new int[n];
        for(int i = 0 ; i < n ; i++) {
            parent[i] = i;
        }
    }

    public int find(int x ) {
        if(parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }

    public void union(int x, int y) {
        parent[find(x)] = find(y);
    }
}
