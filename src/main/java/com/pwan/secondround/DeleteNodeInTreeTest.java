package com.pwan.secondround;

public class DeleteNodeInTreeTest {

    public static void main(String[] args) {
        System.out.println("This is the start of " + DeleteNodeInTreeTest.class.getCanonicalName());

        DeleteNodeInTreeTest test = new DeleteNodeInTreeTest();




    }

    public TreeNode deleteNodeByKey(TreeNode treeNode, int key) {
        if (treeNode ==null) {
            return null;
        }

        if (key > treeNode.val) {
            deleteNodeByKey(treeNode.leftNode, key);
        } else if (key<treeNode.val) {
            deleteNodeByKey(treeNode.rightNode, key);
        } else {   //Found it.. Start operation.
            //Delete current node, TreeNode
            if (treeNode.leftNode ==null) {
                return treeNode.rightNode;
            } else if (treeNode.rightNode == null) {
                return treeNode.leftNode;
            } else {
                //Find the right tree. the left most node
                TreeNode node = treeNode.rightNode;
                while(node.leftNode!=null) {
                    node = node.leftNode;
                }
                node.leftNode = treeNode;

                return node;
            }
        }
        return null;
    }


}
