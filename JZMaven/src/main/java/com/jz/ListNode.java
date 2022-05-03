package com.jz;

public class ListNode {
    public static String SEPARATOR = ",";

    public int val;
    public ListNode next;

    public ListNode(int x) {
        val = x;
        next = null;
    }

    @Override
    public String toString() {
       if(this == null) {
         return "";
       }
        ListNode cur = this;
        if (cur == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        while (cur != null) {
            sb.append(this.val + SEPARATOR);
            cur = cur.next;
        }
        return sb.substring(0, sb.length() - 1);
    }

    public static ListNode of(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }

        String[] strs = str.split(SEPARATOR);
        ListNode dummy = new ListNode(Integer.MIN_VALUE);
        ListNode cur = dummy;
        for (int i = 0; i < strs.length; i++) {
            cur.next = new ListNode(Integer.parseInt(strs[i]));
            cur = cur.next;
        }

        return dummy.next;
    }

}