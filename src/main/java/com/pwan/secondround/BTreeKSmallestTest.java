package com.pwan.secondround;

import java.util.*;

public class BTreeKSmallestTest {

    public static void main(String[] args) {
        System.out.println("This is the start of ");


        TreeNode tree = new TreeNode();
        tree.val = 5;

        TreeNode treeLeftTemp = insertLeft(tree, 3);
        TreeNode treeRightTemp = insertRight(tree, 6);

        TreeNode treeLeftTemp2 = insertLeft(treeLeftTemp, 2);
        TreeNode treeRightTemp2 = insertRight(treeLeftTemp, 4);

        insertLeft(treeLeftTemp2, 1);


        Collection<Integer> resList2 = stackTraverseTree(tree);
        for (Integer each: resList2) {
            System.out.println("val2 " + each);
        }

        Collection<Integer> resList = new ArrayList<Integer>();
        resList = traverseTree(tree, resList);
        for (Integer each: resList) {
            System.out.println("val " + each);
        }

        Collection<Integer> resList3 = stackTraverseByFlag(tree);
        for (Integer each: resList3) {
            System.out.println("val3 " + each);
        }

    }

    /**
     * 1. First add root to stack
     * 2. Pop up a node from stack.
     *     a. If it is already flag = true, means it is already handled(left,right child), now output it to List.
     *     b. If it is flag = false, means it is not handled(left,right child), now add back to stack with below sequence:
     *        right child, node itself(but set the flag as True), left child.
     * 3. Continuously pop up from stack until it is empty
     *
     * @param tree : Input Binary Sorted Tree
     * @return traverse result - midOrder
     */
    private static Collection<Integer> stackTraverseByFlag(TreeNode tree) {
        Collection<Integer> resList = new ArrayList<Integer>();
        Stack<TreeNode> stack  = new Stack<TreeNode>();
        stack.push(tree);

        while(!stack.isEmpty()) {
            TreeNode pTreeNode = stack.pop();

            if (pTreeNode.flag == true) {
                resList.add(pTreeNode.val);
            } else {
                pTreeNode.flag = true;

                if (pTreeNode.rightNode!=null) {
                    stack.push(pTreeNode.rightNode);
                }
                stack.push(pTreeNode);
                if (pTreeNode.leftNode!=null) {
                    stack.push(pTreeNode.leftNode);
                }
            }
        }
        return resList;
    }


    private static Collection<Integer> stackTraverseTree(TreeNode tree) {

        Collection<Integer> resList = new ArrayList<Integer>();
        Stack<TreeNode> stack  = new Stack<TreeNode>();

        TreeNode root = tree;
        while(root!=null || !stack.isEmpty()) {
           if(root!=null) {
               stack.push(root);
               root = root.leftNode;
           } else  {
               TreeNode pTreeNode = stack.pop();
               resList.add(pTreeNode.val);

               if (pTreeNode.rightNode!=null) {
                   pTreeNode = pTreeNode.rightNode;
               }
           }
        }
        return resList;

    }


    private static Collection<Integer> traverseTree(TreeNode tree, Collection<Integer> resList) {
        if (tree == null) {
            return resList;
        }
        traverseTree(tree.leftNode, resList );

        resList.add(Integer.valueOf(tree.val));

        traverseTree(tree.rightNode, resList);

        return resList;
    }

    private static TreeNode insertLeft(TreeNode tree, int newVal) {
        TreeNode treeLeft = new TreeNode();
        treeLeft.val = newVal;

        tree.leftNode = treeLeft;

        return treeLeft;
    }

    private static TreeNode insertRight(TreeNode tree, int newVal) {
        TreeNode treeRight = new TreeNode();
        treeRight.val = newVal;

        tree.rightNode = treeRight;

        return treeRight;
    }

}
