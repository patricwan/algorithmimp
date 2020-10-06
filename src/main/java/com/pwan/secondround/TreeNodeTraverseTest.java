package com.pwan.secondround;



public class TreeNodeTraverseTest {


    public static void main(String[] args) {
        System.out.println("This is the start of TreeNode Traverse Test");

        TreeNodeTraverseTest test = new TreeNodeTraverseTest();

        TreeNode root = test.createTree();

        test.deepFirstSearch(root);

    }

    public TreeNode createTree() {
        TreeNode treeRoot = new TreeNode();
        treeRoot.val = 12;

        //Left and children
        TreeNode left1 = new TreeNode();
        left1.val = 8;
        treeRoot.leftNode  = left1 ;

        TreeNode left11 = new TreeNode();
        left11.val = 6;
        left1.leftNode = left11;

        TreeNode right11 = new TreeNode();
        right11.val = 4;
        left1.rightNode = right11;

        //Right and children
        TreeNode right1 = new TreeNode();
        right1.val = 7;
        treeRoot.rightNode = right1;

        TreeNode right21 = new TreeNode();
        right21.val = 5;
        right1.leftNode = right21;

        TreeNode right12 = new TreeNode();
        right12.val = 3;
        right1.rightNode = right12;


        return treeRoot;
    }

    public void deepFirstSearch(TreeNode tree) {
        if (tree==null) {
            return;
        }
        System.out.println("Value " + tree.val);

        deepFirstSearch(tree.leftNode);
        deepFirstSearch(tree.rightNode);
    }

}


