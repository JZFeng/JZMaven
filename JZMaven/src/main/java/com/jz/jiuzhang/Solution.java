package com.jz.jiuzhang;

import com.jz.TreeNode;

import java.util.*;


class Solution {

    public static void main(String[] args) {
        String s = "abcd";
        int[] indices = new int[]{0,2};
        String[] sources = new String[]{"a", "cd"};
        String[] targets = new String[]{"eee", "ffff"};
        Solution solution = new Solution();
        String res = solution.findReplaceString(s, indices, sources, targets);
        System.out.println(res);

    }
    public String findReplaceString(String s, int[] indices, String[] sources, String[] targets) {
        int len = s.length();
        int[] matches = new int[len];
        Arrays.fill(matches, -1);
        for (int i = 0; i < indices.length; i++) {
            if (s.substring(indices[i], indices[i] + sources[i].length()).equals(sources[i])) {
                matches[indices[i]] = i;
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; ) {
            if(matches[i] != -1) {
                sb.append(targets[matches[i]]);
                i += sources[matches[i]].length();
            } else {
                sb.append(s.charAt(i));
                i++;
            }
        }
        return sb.toString();
    }

}