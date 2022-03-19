package com.jz.jiuzhang;

import com.jz.TreeNode;

import javax.swing.text.html.ListView;
import java.util.*;


class Solution {
    //行数=height；列数= Power(2, height) - 1;
    //每一行的start,end, root肯定是在中间；

    public static void main(String[] args) {
        String url = "https://leetcode-cn.com/problems/longest-substring-without-repeating-characters/solution/wu-zhong-fu-zi-fu-de-zui-chang-zi-chuan-by-leetc-2/";
        System.out.println(url.length());
        ListNode node = new ListNode(1);
    }

    public TreeNode upsideDownBinaryTree(TreeNode root) {
        if( root == null || root.left == null) {
            return root;
        }


        TreeNode newRoot = upsideDownBinaryTree(root.left); //left变成新的根节点；
        newRoot.left = root.right;
        newRoot.right = root;
        root.left = null;
        root.right = null;

        return newRoot;

    }

    public List<List<String>> printTree(TreeNode root) {
        List<List<String>> res = new ArrayList<>();
        int height = height(root);
        int rows = height, cols = (int) (Math.pow(2, height) - 1);
        //初始化
        List<String> row = new ArrayList<>();
        for(int i = 0 ; i < cols; i++) {
            row.add("");
        }
        for(int i = 0 ; i < rows; i++) {
            res.add(new ArrayList<>(row));
        }

        //height,也是numOfRow；
        helper(root, res, 0, cols -1, 0);

        return res;


    }

    private void helper( TreeNode root, List<List<String>> res, int start, int end , int curRow ) {
        if(root == null ) {
            return;
        }

        int mid = start + (end - start) / 2;
        res.get(curRow).set(mid, root.val + "");
        helper( root.left, res, start, mid - 1, curRow + 1 );
        helper(root.right, res, mid + 1, end, curRow + 1);

    }


    private int height(TreeNode root) {
        if(root == null) {
            return 0;
        }

        return Math.max( height(root.left) , height(root.right )) + 1;
    }
}