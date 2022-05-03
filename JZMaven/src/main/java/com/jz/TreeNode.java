package com.jz;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class TreeNode {
    public int val;

    public TreeNode left, right;

    public TreeNode(int val) {
        this.val = val;
        this.left = this.right = null;
    }

    @Override
    public String toString() {
        TreeNode root = this;
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

    public static TreeNode of(String data) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TreeNode treeNode = (TreeNode) o;
        return val == treeNode.val &&
                Objects.equals(left, treeNode.left) &&
                Objects.equals(right, treeNode.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(val, left, right);
    }
}