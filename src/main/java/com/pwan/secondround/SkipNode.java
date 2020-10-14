package com.pwan.secondround;

public class SkipNode {

    public int val;

    public int maxLevel = 0;

    //[i]    节点指向第i层的节点next[i]
    public SkipNode[] next;

    public SkipNode(int val) {
        this.next = new SkipNode[32];
        this.val = val;
    }
}
