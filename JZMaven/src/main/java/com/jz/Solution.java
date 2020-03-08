package com.jz;

import java.util.*;


public class Solution {
  public static void main(String[] args) throws Exception {

    String list = "1->2->3->4->5->null";
    ListNode head = Utils.deserializeList(list);
    ListNode middle = findMiddle(head);
    System.out.println(middle);


  }




  private static ListNode findMiddle(ListNode head) {
    ListNode slow = head;
    ListNode fast = head;
    while(fast != null && fast.next != null) {
      slow = slow.next;
      fast = fast.next.next;
    }

    return slow;
  }


  private static ListNode reverse(ListNode head) {
    if( head == null || head.next == null) {
      return head;
    }

    ListNode pre = null;
    ListNode cur = head;
    while(cur != null) {
      ListNode tmp = cur.next;
      cur.next = pre;
      pre = cur;
      cur = tmp;
    }

    return pre;
  }

}