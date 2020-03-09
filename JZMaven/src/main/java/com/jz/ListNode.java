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

    sb.append(this.val + "->");
    while(this.next != null) {
      sb.append(this.next.val + "->");
      this.next = this.next.next;
    }

    sb.append("null");

    return sb.toString();
  }

}