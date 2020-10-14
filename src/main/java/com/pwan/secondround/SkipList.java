package com.pwan.secondround;


import java.util.Random;



public class SkipList {

    private final int MAX_LEVEL = 32;
    /*
     * 当前跳表的有效层
     */
    private int levelCount = 1;
    /*
     * 跳表的头部节点
     */
    private final SkipNode head = new SkipNode(-1);
    /*
     * 随机数发生器
     */
    private final Random random = new Random();
    /*
     * 自然数e
     */
    private final double E = Math.E;


    public SkipNode find(int value) {
        SkipNode p = head;
        for (int i = levelCount - 1; i >= 0; --i) {
            while (p.next[i] != null && p.next[i].val < value) {
                p = p.next[i];
            }
        }

        if (p.next[0] != null && p.next[0].val == value) {
            return p.next[0];    // 找到，则返回原始链表中的结点
        } else {
            return null;
        }
    }


    // 插入操作
    public void insert(int value){
        int level = randomLevel();
        SkipNode newNode = new SkipNode(-1);
        newNode.val = value;
        newNode.maxLevel = level;   // 通过随机函数改变索引层的结点布置
        SkipNode update[] = new SkipNode[level];
        for(int i = 0; i < level; ++i){
            update[i] = head;
        }

        SkipNode p = head;
        for(int i = level - 1; i >= 0; --i){
            while(p.next[i] != null && p.next[i].val < value){
                p = p.next[i];
            }
            update[i] = p;
        }

        for(int i = 0; i < level; ++i){
            newNode.next[i] = update[i].next[i];
            update[i].next[i] = newNode;
        }
        if(levelCount < level){
            levelCount = level;
        }
    }


    // 随机函数
    private int randomLevel() {
        int level = 1;
        for (int i = 1; i < MAX_LEVEL; ++i) {
            if (random.nextInt() % 2 == 1) {
                level++;
            }
        }
        return level;
    }

}
