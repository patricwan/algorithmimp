package com.pwan.pkg;

public class ListNode {
    public int val;
    public ListNode nextNode;

    ListNode(int val) {
        this.val = val;
        this.nextNode = null;
    }

    public ListNode next() {
        return nextNode;
    }
}