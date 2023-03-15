package com.jz.algo.tree.segmenttree;

class SegmentTree {
    private Node root;
    private int[] nums;

    SegmentTree(int[] nums) {
        this.nums = nums;
        this.root = buildTree(nums, 0, nums.length - 1);
    }

    private Node buildTree(int[] nums, int start, int end) {
        if (start > end) {
            return null;
        }

        Node root = new Node(start, end);
        if (start == end) {
            root.val = nums[start];
        } else {
            //mid在左边；
            int mid = start + (end - start) / 2;
            root.left = buildTree(nums, start, mid);
            root.right = buildTree(nums, mid + 1, end);
            root.val = root.left.val + root.right.val;
        }

        return root;
    }

    public void update(int i, int val){
        update(root, i, val);
    }
    //如果是区间修改（这里需要用到lazy propogation来优化到logn,可以使用差分数组和全局变量表示有没有修改；
    // if(isChanged) {restore from diff array}

    public int sumRange(int start, int end){
        return sumRange(root,start, end);
    }

    private void update(Node node, int index, int val) {
        if( index < node.start || index > node.end ) return; //不在范围内直接返回；
        if( node.start == node.end && node.start == index){
            node.val = val;
            return;
        }

        update(node.left, index, val);
        update(node.right, index, val);
        node.val = node.left.val + node.right.val;
        this.nums[index] = val;
        /*
        if (node.start == node.end) {
            node.sum = val;
            return;
        }

        int mid = node.start + (node.end - node.start) / 2;
        if (index <= mid) { //mid在左边；
            update(node.left, index, val);
        } else {
            update(node.right, index, val);
        }

        node.sum = node.left.sum + node.right.sum;*/
    }

    private int sumRange(Node node, int start, int end) {
        if(node.start > end || node.end < start) return 0;
        if(node.start >= start && node.end <= end) return node.val;
        return sumRange(node.left, start, end) + sumRange(node.right, start, end);
        /*容易理解但不推荐
        if (start > end) return 0;
        if (Node.start == start && Node.end == end) return Node.sum;

        int mid = Node.start + (Node.end - Node.start) / 2;
        if (end <= mid) {
            return sumRange(Node.left, start, end);
        } else if (start > mid) {
            return sumRange(Node.right, start, end);
        } else {
            return sumRange(Node.left, start, mid) + sumRange(Node.right, mid + 1, end);
        }*/
    }
}

class Node {
    int start, end, val; //这里的val可以是min，max，sum
    Node left, right;

    Node(int start, int end) {
        this.start = start;
        this.end = end;
    }
}