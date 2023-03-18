package com.jz.algo.sort;

import java.util.Arrays;

public class PatienceSort {
    //[300. Longest Increasing Subsequence](https://leetcode.com/problems/longest-increasing-subsequence/)
    // LIS Solution 2: patience sorting,寻找最好的答案；
    // LIS为1的，以2结尾的，当然是比3结尾的更优，因为，2后面可以跟更小的数字以组成更长的LIS；
    //patience sorting,答案只是副产品

    //耐心排序：发出的第一张牌形成一张由单张牌组成的新牌。
    //每张后续卡片放置在一些现有的堆上，其顶部卡的值>=新卡的值,则放在该堆上;如果没有合适的堆,则放在所有现有堆的右侧，从而形成新的堆。
    //当没有剩余的牌可以交换时，游戏结束。
    // 桩的数量是最长子序列的长度
    //[10,9,2,5,3,7,101,18]， 按照耐心游戏，最后[2,9,10],[3,5],[7],[18,101];
    public int lengthOfLIS(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        if (nums.length == 1) {
            return 1;
        }

        int index = 0; //右边界，也是堆的个数；
        int[] res = new int[nums.length];
        res[0] = nums[0];

        for (int i = 1; i < nums.length; i++) {
            int insertPos = findInsertPosition(res, index, nums[i]);
            res[insertPos] = nums[i];
            if (insertPos > index) {
                index = insertPos; //相当于多了一个堆；
            }
        }

        return index + 1;
    }

    //二分法查找插入位置;第一个 >= target的数字；
    //等于也替换一下,这样逻辑好处理一点；
    private int findInsertPosition(int[] res, int index, int num) {
        int left = 0;
        int right = index;

        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            if (res[mid] >= num) {
                right = mid;
            } else {
                left = mid;
            }
        }

        if (res[left] >= num) {
            return left;
        }
        if (res[right] >= num) {
            return right;
        }

        return index + 1;
    }

    //LIS,单序列DP经典题; Solution 1: O(N2);
    public int lengthOfLIS1(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n + 1];
        Arrays.fill(dp, 1);
        dp[0] = 0;

        for (int i = 2; i <= n; i++) {
            for (int j = 1; j < i; j++) {
                if (nums[i - 1] > nums[j - 1]) {
                    //找中间那个可以接上的石头/跳板,然后取最大值；
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }

        //取结果；
        int res = 1;
        for (int i = 0; i <= n; i++) {
            res = Math.max(res, dp[i]);
        }

        return res;
    }
}
