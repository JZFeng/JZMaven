/**
 * @Author jzfeng
 * @Date 3/14/23-2:26 PM
 */

package com.jz.algo.datastructureDesign;

import java.util.*;

/*
[1570. Dot Product of Two Sparse Vectors](https://leetcode.com/problems/dot-product-of-two-sparse-vectors)
 */
class SparseVector {

    List<int[]> list; //优化1: 不用hashmap，hashset，避免hash collision;

    SparseVector(int[] nums) {
        this.list = new ArrayList<>();

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                list.add(new int[]{i, nums[i]}); // {index, nums[index}
            }
        }
    }

    // Return the dotProduct of two sparse vectors
    public int dotProduct(SparseVector vec) {
        int res = 0;
        if (vec.list.size() < this.list.size()) { //优化2:iterate比较小的集合；
            return vec.dotProduct(this);
        }

        for (int[] pair : this.list) {
            int i = pair[0];
            int val = pair[1];
            int index = binarySearch(vec.list, i);
            if (index != -1) {
                res += val * vec.list.get(index)[1];
            }
        }

        return res;
    }

    //还是老老实实用模版吧😂
    private int binarySearch(List<int[]> list, int index) {
        int left = 0, right = list.size() - 1;
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            if (list.get(mid)[0] > index) {
                right = mid;
            } else {
                left = mid;
            }
        }
        if (list.get(left)[0] == index) return left;
        if (list.get(right)[0] == index) return right;
        return -1;
    }
}

//这个比HashMap稍微好那么一丢丢,因为hashCode用了两个值；
//用treeset，按照index升序排列；
class Solution1 {
    TreeSet<Pair> treeSet;

    Solution1(int[] nums) {
        this.treeSet = new TreeSet<Pair>((a, b) -> a.index - b.index);
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                treeSet.add(new Pair(i, nums[i]));
            }
        }
    }

    // Return the dotProduct of two sparse vectors
    public int dotProduct(Solution1 vec) {
        if (this.treeSet.size() > vec.treeSet.size()) {
            return vec.dotProduct(this);
        }

        int res = 0;
        for (Pair p : vec.treeSet) {
            if (treeSet.contains(p)) {
                res += p.val * treeSet.floor(p).val;
            }
        }
        return res;
    }
}

class Pair {
    int index;
    int val;

    Pair(int index, int val) {
        this.index = index;
        this.val = val;
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, val);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Pair)) return false;
        Pair p = (Pair) o;
        return this.index == p.index && this.val == p.val;
    }
}

