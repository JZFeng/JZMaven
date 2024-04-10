package com.jz.algo.array;

/*
(528. Random Pick with Weight)[https://leetcode.com/problems/random-pick-with-weight/]
 */

import java.util.Random;

//[1,3,5]前缀和数组 [1,4,9] index = random.nextInt(9) ;
//在前缀和数组中二分查找第一个>=index的数，返回此下标;
class RandomWithWeight {

    int[] presum;
    Random random;

    public RandomWithWeight(int[] w) {
        int len = w.length;
        presum = new int[len];
        presum[0] = w[0];
        for (int i = 1; i < len; i++) {
            presum[i] = presum[i - 1] + w[i];
        }

        random = new Random();
    }

    public int pickIndex() {
        // plus one because index does not have 0 weight
        int index = random.nextInt(presum[presum.length - 1]) + 1;
        return search(presum, index);
    }

    //二分搜索,找到第一个>= index的数字；
    private int search(int[] presum, int index) {
        int left = 0, right = presum.length - 1;
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            if (presum[mid] >= index) {
                right = mid;
            } else {
                left = mid;
            }
        }

        if (presum[left] >= index) {
            return left;
        } else {
            return right;
        }
    }
}

/**
 * Your Solution object will be instantiated and called as such:
 * Solution obj = new Solution(w);
 * int param_1 = obj.pickIndex();
 */
