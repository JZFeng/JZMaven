package com.jz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;


public class Solution {

  private static Log log = LogFactory.getLog(Solution.class);

  public static void main(String[] args) throws Exception {

    Solution solution = new Solution();

  }

  public String serialize(TreeNode root) {
    // write your code here
    if (root == null) {
      return "[]";
    }

    StringBuilder sb = new StringBuilder();
    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);
    while (!queue.isEmpty()) {
      TreeNode tmp = queue.poll();
      if (tmp == null) {
        sb.append(tmp.val + ",");
      } else {
        sb.append("#,");
      }

      if (tmp != null) {
        queue.offer(tmp.left);
        queue.offer(tmp.right);
      }
    }

    String res = sb.toString();
    int index = res.length() - 1;
    while (res.charAt(index) == '#' || res.charAt(index) == ',') {
      index--;
    }

    return "[" + res + "]";

  }


}