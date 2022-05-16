package com.jz.java.dataStructure.tree.segmenttree;

public class ZKW {

    public static void main(String[] args) {
        int[] nums = new int[]{4,6,7,3};
        ZKW zkw = new ZKW(nums);
        zkw.sumRange(1,3);
    }

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
    //区间查询；左偶 右奇
    public int sumRange(int i, int j ) {
        int res = 0 ;
        for( i = i + n, j = j + n; i <= j; i /= 2, j /= 2 ) {
            //left boundary 为右孩子；right boundary为左孩子；
            if( i % 2 == 1) res += st[i++];
            if( j % 2 == 0) res += st[j--];
        }
        return res;
    }
}
