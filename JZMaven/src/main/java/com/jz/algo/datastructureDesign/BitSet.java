/**
 * @Author jzfeng
 * @Date 2/9/23-3:06 PM
 */

package com.jz.algo.datastructureDesign;

import java.util.*;

class BitSet {

    public static void main(String[] args) {
        System.out.println(1L << 3);
    }


    /*
    [2166. Design Bitset](https://leetcode.com/problems/design-bitset/)
    反向掩膜法:
    我们每次维护一个常规数组存储01信息的同时，把以O(1)的时间复杂度维护一个反向的掩膜
    这个反向的掩膜在fix，unfix的变更过程中时间复杂度都是O(1)，而flip过程中就相当于把常规数组换成反向掩膜
    时间复杂度也是O(1)，要注意换成反向掩膜同时size也要变
    除了toString()时间复杂度为O(N)，其余方法时间复杂度均是:O(1)，空间复杂度:O(N)
        */
    int[] arr, neg; // arr为正向掩膜，也就是常规要用的，neg为反向掩膜
    int cnt = 0;    // 记录1的个数有多少
    int size;

    // 用 size 个位初始化 Bitset ，所有位都是 0
    public BitSet(int size) {
        this.size = size;
        arr = new int[size];
        neg = new int[size];
        Arrays.fill(neg, 1);    // neg全部初始化为1
    }

    // 将下标为 idx 的位上的值更新为 1 。如果值已经是 1 ，则不会发生任何改变
    public void fix(int idx) {
        if (arr[idx] == 0) {
            arr[idx] = 1;
            cnt++;  // 1的数目+1
            neg[idx] = 0;   // 注意负掩膜同步更新
        }
    }

    // 将下标为 idx 的位上的值更新为 0 。如果值已经是 0 ，则不会发生任何改变
    public void unfix(int idx) {
        if (arr[idx] == 1) {
            arr[idx] = 0;
            cnt--;  // 1的数目-1
            neg[idx] = 1;
        }
    }

    // 翻转 Bitset 中每一位上的值。换句话说，所有值为 0 的位将会变成 1 ，反之亦然
    public void flip() {
        // 互换引用
        int[] t = arr;
        arr = neg;
        neg = t;
        cnt = size - cnt;   // 反转后1的个数就变为了size-cnt
    }

    // 检查 Bitset 中 每一位 的值是否都是 1 。如果满足此条件，返回 true ；否则，返回 false
    public boolean all() {
        return cnt == size;
    }

    // 检查 Bitset 中 是否 至少一位 的值是 1 。如果满足此条件，返回 true ；否则，返回 false
    public boolean one() {
        return cnt >= 1;
    }

    // 返回 Bitset 中值为 1 的位的 总数
    public int count() {
        return cnt;
    }

    // 返回 Bitset 的当前组成情况。注意，在结果字符串中，第 i 个下标处的字符应该与 Bitset 中的第 i 位一致。
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int num : arr) {
            sb.append(num);
        }
        return sb.toString();
    }
}