package com.jz;

import com.jz.algo.pojo.ListNode;
import com.jz.algo.pojo.TreeNode;

import java.util.*;

public class Utils {

  public static String serializeList(ListNode head) {
    if(head == null) {
      return "null";
    }

    StringBuilder sb = new StringBuilder();
    while(head != null) {
      sb.append(head.val + "->");
      head = head.next;
    }

    return sb.toString() + "null";

  }

  public static ListNode deserializeList(String str) {
    if(str == null || str.length() == 0 || str.equals("null")) {
      return null;
    }

    String[] strs = str.split("->");
    ListNode dummy = new ListNode(Integer.MIN_VALUE);
    ListNode cur = dummy;
    for (int i = 0; i < strs.length  -1 ; i++) {
      ListNode tmp = new ListNode(Integer.parseInt(strs[i]));
      cur.next = tmp;
      cur = cur.next;
    }

    return dummy.next;
  }


  public static String serializeTree(TreeNode root) {
    // write your code here
    if (root == null) {
      return "";
    }

    StringBuilder sb = new StringBuilder();
    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);
    while (!queue.isEmpty()) {
      TreeNode tmp = queue.poll();
      if (tmp != null) {
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

    return res.substring(0, index + 1);

  }

  public static TreeNode deserializeTree(String data) {
    // write your code here
    if (data == null || data.length() == 0) {
      return null;
    }

    String[] strs = data.split(",");

    int length = strs.length;
    int index = 0;

    TreeNode root = new TreeNode(Integer.parseInt(strs[0]));
    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);
    while (!queue.isEmpty()) {
      TreeNode tmp = queue.poll();
      tmp.left = (index * 2 + 1 < length && !strs[index * 2 + 1].equals("#")) ?
          new TreeNode(Integer.parseInt(strs[index * 2 + 1])) :
          null;
      tmp.right = (index * 2 + 2 < length && !strs[index * 2 + 2].equals("#")) ?
          new TreeNode(Integer.parseInt(strs[index * 2 + 2])) :
          null;
      if (tmp.left != null) {
        queue.offer(tmp.left);
      }
      if (tmp.right != null) {
        queue.offer(tmp.right);
      }

      index++;
    }

    return root;
  }

}
