package com.pwan.secondround;


public class TrieTree {

    private TrieNode root = new TrieNode();

    public TrieTree() {
    }

    public void insert(String word) {
        if (word ==null) {
            return;
        }

        TrieNode treePointer = root;
        for (int i =0 ;i <word.length(); i++) {
            Character currentChar = word.charAt(i);
            if (!treePointer.map.containsKey(currentChar)) {
                treePointer.map.put(currentChar, new TrieNode());
            }
            treePointer = treePointer.map.get(currentChar);
        }
    }
}
