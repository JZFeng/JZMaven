package com.jz.jiuzhang;

import com.jz.TreeNode;

import javax.swing.text.html.ListView;
import java.util.*;


public class Solution {

    public static void main(String[] args) {
        Solution solution = new Solution();

    }


    public boolean canPartitionKSubsets(int[] nums, int k) {
        int sum = Arrays.stream(nums).sum();
        if(sum % k != 0){
            return false;
        }

        boolean[] visited = new boolean[nums.length];

        return helper(nums, visited, 0, k, 0, sum / k);

    }

    private boolean helper(int[] nums, boolean[] visited, int start, int remainingGroups, int curSum, int target) {
        if( remainingGroups == 0 ) {
            return true;
        }
        if(curSum == target) {
            return helper(nums, visited, 0, remainingGroups - 1, 0, target);
        }

        for(int i = start; i < nums.length; i++) {
            if( visited[i] || curSum + nums[i] > target ) {
                continue;
            }
            visited[i] = true;
            if( helper(nums, visited, i + 1, remainingGroups, curSum + nums[i], target) ) {
                return true;
            }
            visited[i] = false;
        }

        return false;
    }


}
