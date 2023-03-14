/**
 * @Author jzfeng
 * @Date 3/13/23-10:45 PM
 */

package com.jz.algo.datastructureDesign;

//[303. Range Sum Query - Immutable]https://leetcode.com/problems/range-sum-query-immutable/
public class RangeSumQueryArray {
    int[] presum;

    public RangeSumQueryArray(int[] nums) {
        this.presum = new int[nums.length + 1];
        for (int i = 0; i < nums.length; i++) {
            presum[i+1] = presum[i] + nums[i];
        }
    }

    public int sumRange(int left, int right) {
        return presum[right + 1] - presum[left];
    }
}
