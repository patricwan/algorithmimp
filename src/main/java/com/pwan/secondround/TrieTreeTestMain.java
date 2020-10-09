package com.pwan.secondround;

public class TrieTreeTestMain {

    public static void main(String[] args) {
        System.out.println("This is the start of the test");

        TrieTree trieTree = new TrieTree();

        String tests = "good to see how can you fix this issue for that problem";
        String[] strArr = tests.split(" ");
        for (String str : strArr) {
            trieTree.insert(str);
        }


    }
}
