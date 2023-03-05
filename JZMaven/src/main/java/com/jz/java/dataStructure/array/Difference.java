/**
 * @Author jzfeng
 * @Date 3/5/23-11:26 AM
 */

package com.jz.java.dataStructure.array;

class Difference {
    private int[] diff;

    Difference(int[] nums) {
        this.diff = new int[nums.length];
        diff[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            diff[i] = nums[i] - nums[i - 1];
        }
    }

    public void increment(int start, int end, int val) {
        diff[start] += val;
        if (end + 1 < diff.length) {
            diff[end + 1] -= val;
        }
    }

    public int[] restore() {
        int[] res = new int[diff.length];
        res[0] = diff[0];
        for (int i = 1; i < diff.length; i++) {
            res[i] = res[i - 1] + diff[i];
        }

        return res;
    }
}
