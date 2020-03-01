package com.jz;

public class ListNode {
  int val;

  ListNode next;

  ListNode(int x) {
    val = x;
    next = null;
  }

  @Override
  public String toString(){
    StringBuilder sb = new StringBuilder();
    sb.append(val + "->");
    if(next == null) {
      sb.append("null");
    } else {
      sb.append(next.val);
    }

    return sb.toString();
  }

}