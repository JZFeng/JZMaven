package com.jz.algo.tree.segmenttree;

public class SegmentTree {
    int[] nums;
    Node root;

    SegmentTree(int[] nums) {
        this.nums = nums;
        this.root = buildTree(nums, 0, nums.length - 1);
    }

    private Node buildTree(int[] nums, int start, int end) {
        if(start > end) {
            return null;
        }

        Node node = new Node(start, end);
        if(start == end) {
            node.sum = nums[start];
        } else {
            //mid在左边；
            int mid = start + (end - start) / 2;
            node.left = buildTree(nums, start, mid);
            node.right = buildTree(nums, mid + 1 , end);
            node.sum = node.left.sum + node.right.sum;
        }

        return node;
    }

    public void update(Node node, int i , int val) {
        if(node.start == node.end) {
            node.sum = val;
            return;
        }

        int mid = node.start + (node.end - node.start ) / 2;
        if( i <= mid) { //mid在左边；
            update(node.left, i, val);
        } else {
            update(node.right, i, val);
        }

        node.sum = node.left.sum + node.right.sum;
    }

    public int sumRange(Node node, int start, int end) {
        if(start > end) return 0;
        if(node.start == start && node.end == end) return node.sum;

        int mid = node.start + (node.end - node.start) / 2;
        if(end <= mid) {
            return sumRange(node.left, start, end);
        } else if(start > mid) {
            return sumRange(node.right, start, end);
        } else {
            return sumRange(node.left, start, mid) + sumRange(node.right, mid + 1, end);
        }
    }
}

class Node {
    int start, end, sum;
    Node left, right;

    Node(int start, int end) {
        this.start = start;
        this.end = end;
    }
}
