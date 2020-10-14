package com.pwan.secondround;

public class SkipListTestMain {
    public static void main(String[] args) {
        System.out.println("This is the start of main program");

        SkipList skipList = new SkipList();
        skipList.insert(4);
        skipList.insert(8);
        skipList.insert(12);
        skipList.insert(3);
        skipList.insert(7);
        skipList.insert(6);
        skipList.insert(17);
        skipList.insert(23);
        skipList.insert(41);

        SkipNode skipNode = skipList.find(12);
        System.out.println(" found node " + skipNode.val);

    }

}
