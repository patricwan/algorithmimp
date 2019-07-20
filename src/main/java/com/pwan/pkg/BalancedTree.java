package com.pwan.pkg;

//https://leetcode-cn.com/problems/balanced-binary-tree/solution/yi-bian-sao-miao-pan-duan-shi-fou-wei-ping-heng-er/
public class BalancedTree {

    static boolean ans = true;

    public static void main(String[] args) {
        TreeNode root = new TreeNode(9);
        root.left = new TreeNode(8);
        root.right = new TreeNode(5);

        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(2);

        boolean isBal = isBalanced(root);

        System.out.println("is Balanced? " + isBal);

    }

    public static boolean isBalanced(TreeNode root) {
        height(root);
        return ans;
    }

    //Recursive call
    public static int height(TreeNode root) {
        if (root == null)
            return 0;
        int left = height(root.left);

        int right = height(root.right);

        if (Math.abs(left - right) > 1)
            ans = false;
        return 1 + Math.max(left, right);
    }
}
