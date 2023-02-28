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

    public int find(int id ) {
        if(parent[id] != id) {
            parent[id] = find(parent[id]);
        }
        return parent[id];
    }

    public void union(int a, int b) {
        parent[find(a)] = find(b);
    }
}
