package com.jz;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
class Dummy{}

class Solution {
    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] nums = new int[]{1,2,3,4,3};
        int[] res = solution.nextGreaterElement(nums);
        Arrays.stream(res).forEach(System.out::println);
        List<Integer> list = new ArrayList<>();
    }
    public int[] nextGreaterElement(int[] nums) {
        if(nums == null || nums.length == 0) {
            return new int[]{};
        }

        int[] res = new int[nums.length];
        Deque<Integer> stack = new ArrayDeque<Integer>();

        for (int i = nums.length - 1; i >= 0; i--) {
            //维护单调性
            while(!stack.isEmpty() && nums[i] >= stack.peek()) {
                stack.pop();
            }
            //记录结果
            res[i] = ( stack.isEmpty() ? -1 : stack.peek() );

            stack.push(nums[i]);
        }

        return res;
    }
}