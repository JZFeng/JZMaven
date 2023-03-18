/**
 * @Author jzfeng
 * @Date 3/6/23-12:21 PM
 */

package com.jz.algo.sort;

public class IndexSorting {
    //[268. Missing Number](https://leetcode.com/problems/missing-number/)
    public int missingNumber(int[] nums) {
        int len = nums.length;

        for(int i = 0 ; i < len; i++) {
            while( nums[i] != i && nums[i] >= 0 && nums[i] < len && nums[nums[i]] != nums[i] ) {
                swap(nums, i, nums[i]);
            }
        }

        for(int i = 0 ; i < len; i++) {
            if( nums[i] != i ) {
                return i;
            }
        }
        //这里是个坑，要return nums.length;
        return nums.length;
    }

    private void swap(int[] nums, int i, int j ) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
}
