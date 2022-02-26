package com.jz.jiuzhang;

import java.util.*;


public class Solution {
  public static void main(String[] args) {
    int[] nums = new int[]{-1};
    Solution solution = new Solution();
    double res = solution.findMaxAverage(nums, 1);
    System.out.println(res);
  }


  public int maxScore(int[] cardPoints, int k) {
    int len = cardPoints.length;
    int total = Arrays.stream(cardPoints).sum();
    int sum = 0 ;
    int min = Integer.MAX_VALUE;
    for(int i = 0 ; i < len; i++) {
      sum += cardPoints[i];
      if( i < k - 1) {
        continue;
      }
      if( i >= k) {
        sum -= cardPoints[i - k ];
      }

      min = Math.max(min, sum );
    }

    return total - min;
  }

  //滑动窗口的问题
  public double findMaxAverage(int[] nums, int k) {
    int len = nums.length;
    double sum = 0;
    double res = Double.NEGATIVE_INFINITY;
    for(int i = 0 ; i < len ; i++) {
      sum += nums[i];
      if( i < k - 1 ) {
        continue;
      }
      if( i >= k ) {
        sum -= nums[i - k ];
      }

      res = Double.max(res, sum / (double)k );
    }

    return res;
  }


}