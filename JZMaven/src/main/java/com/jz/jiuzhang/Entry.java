package com.jz.jiuzhang;

import java.util.*;

public class Entry {
  public static void main(String[] args) {
    Solution solution = new Solution();
    int[] nums = {1,2,3};
    List<List<Integer>> results = solution.subsets(nums);
    System.out.println(results);
  }

}
