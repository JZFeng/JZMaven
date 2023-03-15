

package com.jz.algo.datastructureDesign;

class RangeSumQueryMutable {
    SegmentTree st;

    public RangeSumQueryMutable(int[] nums) {
        st = new SegmentTree(nums);
    }

    public void update(int index, int val) {
        st.update(st.root, index, val);
    }

    public int sumRange(int left, int right) {
        return st.sumRange(st.root, left, right);
    }
}

class SegmentTree {
    int[] nums;
    Node root;

    SegmentTree(int[] nums) {
        this.nums = nums;
        this.root = buildTree(nums, 0, nums.length - 1);
    }

    private Node buildTree(int[] nums, int start, int end) {
        if (start > end) {
            return null;
        }

        Node Node = new Node(start, end);
        if (start == end) {
            Node.sum = nums[start];
        } else {
            //mid在左边；
            int mid = start + (end - start) / 2;
            Node.left = buildTree(nums, start, mid);
            Node.right = buildTree(nums, mid + 1, end);
            Node.sum = Node.left.sum + Node.right.sum;
        }

        return Node;
    }

    public void update(Node Node, int i, int val) {
        if (Node.start == Node.end) {
            Node.sum = val;
            return;
        }

        int mid = Node.start + (Node.end - Node.start) / 2;
        if (i <= mid) { //mid在左边；
            update(Node.left, i, val);
        } else {
            update(Node.right, i, val);
        }

        Node.sum = Node.left.sum + Node.right.sum;
    }

    public int sumRange(Node Node, int start, int end) {
        if (start > end) return 0;
        if (Node.start == start && Node.end == end) return Node.sum;

        int mid = Node.start + (Node.end - Node.start) / 2;
        if (end <= mid) {
            return sumRange(Node.left, start, end);
        } else if (start > mid) {
            return sumRange(Node.right, start, end);
        } else {
            return sumRange(Node.left, start, mid) + sumRange(Node.right, mid + 1, end);
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

class ZKW {

    int n;
    int[] st;

    ZKW(int[] nums) {
        this.n = nums.length;
        this.st = new int[2 * n];

        for (int i = n; i < 2 * n; i++) {
            st[i] = nums[i - n];
        }

        for (int i = n - 1; i > 0; i--) {
            st[i] = st[i * 2] + st[i * 2 + 1];
        }
    }

    //单点更新;
    public void update(int i, int val) {
        int diff = val - st[i + n];
        for (i = i + n; i > 0; i = i / 2) {
            st[i] += diff;
        }
    }

    //区间查询；左偶 右奇
    public int sumRange(int i, int j) {
        int res = 0;
        for (i = i + n, j = j + n; i <= j; i /= 2, j /= 2) {
            if (i % 2 == 1) res += st[i++];
            if (j % 2 == 0) res += st[j--];
        }
        return res;
    }
}


/**
 * Your NumArray object will be instantiated and called as such:
 * NumArray obj = new NumArray(nums);
 * obj.update(index,val);
 * int param_2 = obj.sumRange(left,right);
 */