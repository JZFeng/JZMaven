package com.jz;

import com.jz.algo.pojo.*;

import java.util.*;

class Solution {

    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length < k) {
            return new int[]{};
        }

        int len = nums.length, index = 0;
        int[] res = new int[len - k + 1];
        //单调递减队列；降序；
        //Queue里存放的是index；

        Deque<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < len; i++) {
            //维护window合法性；
            while( i < len && !q.isEmpty() && i - q.peekFirst() >= k ){
                q.pollFirst();
            }

            //维护Queue的单调性；右出；
            while (i < len && !q.isEmpty() && nums[i] >= nums[q.peekLast()]) {
                q.pollLast();
            }
            q.offerLast(i);

            if ( !q.isEmpty() && i - k + 1 >= 0) {
                res[index++] = nums[q.peekFirst()];
            }
        }

        return res;
    }

/*
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length < k) {
            return new int[]{};
        }

        int len = nums.length, left = 0, index = 0;
        int[] res = new int[len - k + 1];
        //单调递减队列；降序；
        //Queue里存放的是index；

        Deque<Integer> deque = new ArrayDeque<>();
        for (int i = 0; i < len; i++) {
            //维护window合法性；
            while (i < len && i - left + 1 > k) {
                if (deque.peekFirst() < i - k + 1) {
                    deque.pollFirst();
                }
                left++;
            }

            //维护Queue的单调性；右出；
            while (i < len && !deque.isEmpty() && nums[i] >= nums[deque.peekLast()]) {
                deque.pollLast();
            }
            deque.offerLast(i);

            if (i - left + 1 == k) {
                res[index++] = deque.peekFirst();
            }

        }

        return res;
    }*/

    //Treeset + Sliding window;
    public int[] maxSlidingWindow1(int[] nums, int k) {
        if (nums == null || nums.length == 0 || nums.length < k) {
            return new int[]{};
        }

        int len = nums.length, left = 0, index = 0;
        int[] res = new int[len - k + 1];
        TreeSet<Node> set = new TreeSet<>();
        for (int i = 0; i < len; i++) {
            set.add(new Node(nums[i], i));
            while (i < len && i - left + 1 > k) { //维护窗口合法性；
                set.remove(new Node(nums[left], left)); //这里不能用二元组，因为每次生成的new int[]{nums[left], left}都是新的内存地址；
                left++;
            }
            if (i - left + 1 == k) {
                res[index++] = set.last().val;
            }
        }
        return res;
    }
}

class Node implements Comparable<Node> {
    int val;
    int index;

    Node(int val, int index) {
        this.val = val;
        this.index = index;
    }

    @Override
    public int compareTo(Node o) {
        if (this.val == o.val) {
            return this.index - o.index;
        }

        return this.val - o.val;
    }

    //必须重写hashCode和equals;
    @Override
    public int hashCode() {
        return Objects.hash(val, index);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) {
            return false;
        }
        Node node = (Node) o;
        return this.val == node.val && this.index == node.index;
    }
}