package com.pwan.pkg;

//https://leetcode-cn.com/problems/kth-smallest-element-in-a-bst/comments/
public class KthSmallestInBinaryTree {

    private static int count;

    private static int res;

    public static void main(String[] args) {
        System.out.println("This is the start of the program");




    }

    public int kthSmallest(TreeNode root, int k) {
        count=k;
        res=-1;
        inOrder(root);

        return res;

    }

    private void inOrder(TreeNode root){
        if(root==null){
            return;
        }
        inOrder(root.left);
        count--;
        if(count==0){
            res=root.value;
        }
        inOrder(root.right);
    }

}
