package com.pwan.pkg;

import java.util.List;
import java.util.Stack;
import java.util.ArrayList;
//https://leetcode-cn.com/problems/binary-tree-preorder-traversal/solution/die-dai-yu-di-gui-shi-xian-xian-xu-bian-li-er-cha-/
public class PreorderTraverse {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(9);
        root.left = new TreeNode(8);
        root.right = new TreeNode(5);

        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(2);

        List<Integer> list = preorderTraversal(root);

    }

    //Use a stack to simulate recursive call
    public static List<Integer> preorderTraversal(TreeNode root) {
        if (root == null) {
            return new ArrayList<Integer>();
        }

        Stack<TreeNode> nodeStack = new Stack<TreeNode>();
        nodeStack.push(root);

        List<Integer> list = new ArrayList<Integer>();

        while (nodeStack.size() != 0) {
            TreeNode node = nodeStack.pop();
            list.add(node.value);

            if (node.right != null) {
                nodeStack.push(node.right);
            }
            if (node.left != null) {
                nodeStack.push(node.left);
            }
        }

        return list;
    }

}
