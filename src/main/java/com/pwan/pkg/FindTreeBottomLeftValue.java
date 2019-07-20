package com.pwan.pkg;

//https://leetcode-cn.com/problems/find-bottom-left-tree-value/solution/java-liang-chong-fang-fa-di-gui-he-die-dai-by-zxy0/
public class FindTreeBottomLeftValue {

    static int maxDepth = -1, res = -1;

    public static void main(String[] args) {
        System.out.println("This is the start of the program");

        TreeNode root = new TreeNode(9);
        root.left = new TreeNode(8);
        root.right = new TreeNode(5);

        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(2);


        int retValue = findBottomLeftValue(root);

        System.out.println(retValue + "");

    }



    public static int findBottomLeftValue(TreeNode root) {
        traverse(root, 0);
        return res;
    }

    private static void traverse(TreeNode root, int depth) {
        if (root == null) return;
        traverse(root.left, depth + 1);
        //判断是否是最大深度
        if (depth > maxDepth) {
            maxDepth = depth;
            res = root.value;
        }
        traverse(root.right, depth + 1);
    }

}
