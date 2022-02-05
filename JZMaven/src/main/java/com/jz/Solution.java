package com.jz;

import java.util.*;
import com.jz.Utils.*;


public class Solution {

    public static void main(String[] args) {
        Solution solution = new Solution();
        String tree = "3,9,20,#,#,15,7";
        TreeNode root = Utils.deserializeTree(tree);
    }



    public ListNode reverseBetween(ListNode head, int left, int right) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode pre = dummy;
        ListNode cur = head;
        for(int i = 1 ; i < left; i++) {
            pre = cur;
            cur = cur.next;
        }

        ListNode p = pre;
        ListNode c = cur;

        for(int i = left ; i <= right; i++) {
            ListNode tmp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = tmp;
        }

        p.next = pre;
        c.next = cur;

        return dummy.next;

    }


    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();

        int count = 0;
        Queue<TreeNode> queue = new LinkedList<>();

        queue.offer(root);
        while(!queue.isEmpty()) {
            int size = queue.size();
            count++;
            LinkedList<Integer> level = new LinkedList<>();
            for(int i = 0 ; i < size; i++) {
                TreeNode tmp = queue.poll();
                if( count % 2 == 1) {
                    level.add(tmp.val);
                } else {
                    level.addFirst(tmp.val);
                }
                if( tmp.left != null ) {
                    queue.offer(tmp.left);
                }
                if(tmp.right != null) {
                    queue.offer(tmp.right);
                }
            }
            res.add(level);
        }

        return res;
    }
}