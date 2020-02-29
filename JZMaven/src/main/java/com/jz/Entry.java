package com.jz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.*;

public class Entry {
  private static Log log = LogFactory.getLog(Entry.class);

  public static void main(String[] args) throws Exception {

    Entry entry = new Entry();

  }


  class TreeNode {
    public int val;

    public TreeNode left, right;

    public TreeNode(int val) {
      this.val = val;
      this.left = this.right = null;
    }
  }

  class ListNode {
    int val;

    ListNode next;

    ListNode(int x) {
      val = x;
      next = null;
    }
  }
}



