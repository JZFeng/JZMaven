/**
 * @Author jzfeng
 * @Date 5/11/22-11:09 PM
 */

package com.jz.algo.dsu;

import java.util.Arrays;
//rank代表高度或者深度；高度低的树向高度高低树合并；
public class DSUWithRank {
    int[] parent;
    int[] rank;

    public DSUWithRank(int n ) {
        this.parent = new int[n];
        this.rank = new int[n];
        for(int i = 0 ; i < n; i++) {
            parent[i] = i;
        }
        Arrays.fill(rank, 1);
    }

    public int find(int x) {
        if(parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }

    public void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        if(rootX == rootY ) {
            return;
        }
        if(rank[rootX] < rank[rootY]) {
            parent[rootX] = rootY;
        } else if (rank[rootX] > rank[rootY]) {
            parent[rootY] = rootX;
        } else { //只有rank相等的情况下，才需要维护rank
            parent[rootX] = rootY;
            rank[rootY]++;
        }
    }
}
