/**
 * @Author jzfeng
 * @Date 2/28/23-8:51 AM
 */

package com.jz.algo.pojo;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
class Dummy{}

class Solution {
    public boolean isPalindrome(ListNode head) {
        ListNode middle = findMiddle(head);
        ListNode node = reverse(middle.next);
        while (node != null) {
            if( head.val != node.val){
                return false;
            }
            node = node.next;
            head = head.next;
        }
        return true;
    }

    private ListNode  findMiddle(ListNode head) {
        ListNode fast = head.next , slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }

    private ListNode reverse(ListNode head) {
        ListNode pre  = null, cur = head;
        while (cur != null) {
            ListNode node =cur.next;
            cur.next = pre;
            pre = cur;
            cur = node;
        }
        return pre;
    }
}