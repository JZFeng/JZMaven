package com.jz.java.dataStructure.tree.segmenttree;

public class ZKW {

    int n;
    int[] st;

    ZKW(int[] nums) {
        this.n = nums.length;
        this.st = new int[2 * n];

        for(int i = n ; i < 2 * n ; i++) {
            st[i] = nums[i - n];
        }

        for(int i = n - 1 ; i > 0 ; i--) {
            st[i] = st[i * 2] + st[i * 2 + 1];
        }
    }
    //单点更新;
    public void update(int i , int val) {
        int diff = val - st[ i + n];
        for( i = i + n; i > 0; i = i / 2 ) {
            st[i] += diff;
        }
    }
    //区间查询；
    public int rangeSum(int i, int j ) {
        int res = 0 ;
        for( i = i + n, j = j + n; i <= j; i /= 2, j /= 2 ) {
            if( i % 2 == 1) res += st[i++];
            if(j % 2 == 0) res += st[j--];
        }
        return res;
    }
}
