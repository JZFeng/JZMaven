/**
 * @Author jzfeng
 * @Date 3/12/23-7:27 PM
 */

package com.jz.algo.sort;

public class SelectionSort {
    public void selectionSort(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return;
        }
        for (int i = 0; i < nums.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[j] < nums[minIndex]) {
                    minIndex = j;
                }
            }
            swap(nums, i, minIndex);
        }
    }

    private void swap(int[] nums, int i, int minIndex) {
        int tmp = nums[i];
        nums[i] = nums[minIndex];
        nums[minIndex] = tmp;
    }
}
