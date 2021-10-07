/**
 * @Author jzfeng
 * @Date 10/2/21-9:27 PM
 */

package com.jz;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

class Solution {
    int res = Integer.MAX_VALUE;

    public int minimumTimeRequired(int[] jobs, int k) {
        Arrays.sort(jobs);
        int[] sum = new int[k];
        dfs(jobs, sum, jobs.length - 1 );

        return res;
    }

    private void dfs(int[] jobs, int[] sum, int pos) {
        if(pos < 0 ) {
            int tmp = Arrays.stream(sum).max().getAsInt();
            res = Math.min(res, tmp);
            System.out.println(res);
            return;
        }

        if( Arrays.stream(sum).max().getAsInt() >= res ) {
            return;
        }

        for(int i = 0 ; i < sum.length; i++) {
            if( i > 0 && sum[i] == sum[i - 1]) {
                continue;
            }
            sum[i] += jobs[pos];
            dfs(jobs, sum, pos - 1);
            sum[i] -= jobs[pos];
        }
    }

    private int[] nextGreaterElement(int[] nums) {
        int[] res = new int[nums.length];
        Stack<Integer> stack = new Stack<>();
        for (int i = nums.length - 1; i >= 0; i--) {
            while(!stack.isEmpty() && nums[i] >= stack.peek()) {
                stack.pop();
            }
            res[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(nums[i]);
        }
        return res;
    }


    private String smallestSubsequence(String s) {
        Stack<Integer> stack = new Stack<>();
        int[] last = new int[128];
        Set<Integer> visited = new HashSet<>();
        for (int i = 0; i < s.length(); i++) {
            last[s.charAt(i)] = i;
        }

        for (int i = 0; i < s.length(); i++) {
            int c = s.charAt(i);
            if( !visited.add(c)) {
                continue;
            }
            while ( !stack.isEmpty() && c < stack.peek() && i < last[stack.peek()]) {
                visited.remove(stack.pop());
            }
            stack.push(c);
        }
        StringBuilder sb = new StringBuilder();
        for(int i : stack) {
            sb.append((char)i);
        }
        while(!stack.isEmpty()) {
            System.out.println( (char)stack.pop().intValue() );
        }

        return sb.toString();
    }


    public static void main(String[] args) {
        Solution solution = new Solution();
        String s = "cbacdcbc";
        String res = solution.smallestSubsequence(s);
        System.out.println(res);
    }
}