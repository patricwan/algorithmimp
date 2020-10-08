package com.pwan.secondround;

import java.util.*;

public class TrieNode {

    public Map<Character, TrieNode> map = new HashMap<Character, TrieNode>();

    public char data;

    //public int freq;

    public TrieNode() {
    }
}
