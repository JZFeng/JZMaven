/**
 * @Author jzfeng
 * @Date 3/12/23-7:31 PM
 */

package com.jz.algo.sort;

public class ShellSort {
    public void shellSort(int nums[]) {
        int n = nums.length;
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                int key = nums[i];
                int j = i;
                while (j >= gap && nums[j - gap] > key) {
                    nums[j] = nums[j - gap];
                    j -= gap;
                }
                nums[j] = key;
            }
        }
    }
}
