package com.jz.algo.sort;

public class InsertSort {
    public void insertSort(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            int tmp = nums[i];
            int j = i;
            while (j > 0 && nums[j - 1] > tmp) {
                nums[j] = nums[j - 1]; //往后退，给更小的留位置；
                j--;
            }
            nums[j] = tmp;
        }
    }
}
