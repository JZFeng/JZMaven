package com.jz.algo.array;

import java.util.Random;

public class Shuffle {
    public int[] shuffle(int[] nums) {
        Random random = new Random();
        for(int i = 0 ; i < nums.length; i++) {
            int j = i + random.nextInt(nums.length - i);
            int tmp = nums[i];
            nums[i] = nums[j];
            nums[j] = tmp;
        }
        return nums;
    }
}
