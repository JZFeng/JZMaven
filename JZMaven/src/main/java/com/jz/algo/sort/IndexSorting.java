/**
 * @Author jzfeng
 * @Date 3/6/23-12:21 PM
 */

package com.jz.algo.sort;

public class IndexSorting {
    //[268. Missing Number](https://leetcode.com/problems/missing-number/)
    public int missingNumber(int[] nums) {
        int len = nums.length;
        for (int i = 0; i < len; i++) {
            while (i < len && nums[i] != i && nums[i] < len && nums[nums[i]] != nums[i] ) {
                swap(nums, i ,nums[i]);
                // int tmp = nums[nums[i]]; //这里也有坑，必须保存nums[nums[i]],最好的方法就是独立写一个swap函数；
                // nums[nums[i]] = nums[i];
                // nums[i] = tmp;
            }
        }
        for (int i = 0; i < len; i++) {
            if (nums[i] != i) {
                return i; //这也是个坑；
            }
        }
        return nums.length;  //这里是个坑，要return nums.length;
    }
    private void swap(int[] nums, int i, int j){
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
}
