package com.jz;

public class ListNode {
  int val;
  ListNode next;

  ListNode(int x) {
    val = x;
    next = null;
  }

  @Override
  public String toString() {
    if(this == null) {
      return "null";
    }

    StringBuilder sb = new StringBuilder();
    ListNode cur = this;
    while(cur != null) {
      sb.append(cur.val + "->");
      cur = cur.next;
    }

    return sb.toString() + "null";
  }

}