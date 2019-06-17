package com.jz.jiuzhang;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.*;
import java.util.*;

import static com.google.common.net.HttpHeaders.USER_AGENT;

public class Solution {

  public static boolean isClassPresent(String name) {
    try {
      Class.forName(name);
      return true;
    } catch (ClassNotFoundException e) {
      return false;
    }
  }


  public int strStr(String source, String target) {
    if (source == null || target == null) {
      return -1;
    }
    if (target.length() == 0 && source.length() >= 0) {
      return -1;
    }
    if (target.length() > source.length()) {
      return -1;
    }


    for (int i = 0; i < source.length() - target.length() + 1; i++) {
      int j = 0;
      for (; j < target.length(); j++) {
        if (source.charAt(i + j) != target.charAt(j)) {
          break;
        }
      }

      if (j == target.length()) {
        return i;
      }
    }

    return -1;
  }

  public int strStrII(String source, String target) {
    if (source == null || target == null) {
      return -1;
    }
    if (target.length() == 0 && source.length() >= 0) {
      return -1;
    }
    if (target.length() > source.length()) {
      return -1;
    }

    int BASE = Integer.MAX_VALUE / 31;
    int length = target.length();

    long hash_target = 0;
    for (int i = 0; i < length; i++) {
      hash_target = (hash_target * 31 + target.charAt(i)) % BASE;
    }

    int power = 1;
    for (int i = 0; i < length; i++) {
      power = (power * 31) % BASE;
    }

    long hash_source = 0;
    for (int i = 0; i < source.length(); i++) {
      hash_source = (hash_source * 31 + source.charAt(i)) % BASE;
      if (i < length - 1) {
        continue;
      }

      if (i >= length) {
        hash_source = (hash_source - power * source.charAt(i - length)) % BASE;
        if (hash_source < 0) {
          hash_source = hash_source + BASE;
        }
      }

      if (hash_source == hash_target) {
        if (source.substring(i - length + 1, i + 1).equals(target)) {
          return i - length + 1;
        }
      }

    }

    return -1;
  }

  public List<List<Integer>> subsets(int[] nums) {
    List<List<Integer>> results = new ArrayList<>();
    if (nums == null) {
      return results;
    }
    if (nums.length == 0) {
      results.add(new ArrayList<Integer>());
      return results;
    }

    Arrays.sort(nums);
    List<Integer> subset = new ArrayList<>();
    subsetsHelper(nums, 0, subset, results);

    return results;
  }

  //递归定义：从nums中，寻找所有以subset开头的子集,并存入results中
  private void subsetsHelper(int[] nums,
                             int offset,
                             List<Integer> subset,
                             List<List<Integer>> results
  ) {
    results.add(new ArrayList<Integer>(subset)); // deep copy, AKS clone;

    for (int i = offset; i < nums.length; i++) {
      subset.add(nums[i]);
      subsetsHelper(nums, i + 1, subset, results);
      subset.remove(subset.size() - 1);
    }
  }

  public int lastPosition(int[] nums, int target) {
    // write your code here
    if (nums == null || nums.length == 0) {
      return -1;
    }

    int left = 0;
    int right = nums.length - 1;

    while (left + 1 < right) {
      int mid = right - (right - left) / 2;
      if (target < nums[mid]) {
        right = mid;
      } else if (target > nums[mid]) {
        left = mid;
      } else {
        left = mid;
      }
    }

    if (nums[right] == target) {
      return right;
    }
    if (nums[left] == target) {
      return left;
    }

    return -1;
  }

  public int firstPosition(int[] nums, int target) {
    if (nums == null || nums.length == 0) {
      return -1;
    }

    int left = 0;
    int right = nums.length - 1;

    while (left + 1 < right) {
      int mid = right - (right - left) / 2;
      if (target < nums[mid]) {
        right = mid;
      } else if (target > nums[mid]) {
        left = mid;
      } else {
        right = mid;
      }
    }

    if (nums[left] == target) {
      return left;
    }
    if (nums[right] == target) {
      return right;
    }

    return -1;

  }


}