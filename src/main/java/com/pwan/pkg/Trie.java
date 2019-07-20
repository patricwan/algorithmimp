package com.pwan.pkg;
//https://leetcode-cn.com/problems/implement-trie-prefix-tree/description/?utm_source=LCUS&utm_medium=ip_redirect&utm_campaign=transfer2china
//https://blog.csdn.net/u013309870/article/details/71081393
public class Trie {

    public boolean isWord;
    public char word;
    public Trie[] tries = new Trie[26];


    /** Initialize your data structure here. */
    public Trie() {
        this.isWord =false;
        this.word=' ';

    }

    /** Inserts a word into the trie. */
    public void insert(String word) {
        char [] array = word.toCharArray();
        Trie node=this;
        for(int i=0;i<array.length;i++){
            if(node.tries[array[i]-'a']==null){
                node.tries[array[i]-'a']=new Trie();
            }
            node=node.tries[array[i]-'a'];
            node.word=array[i];
            if(i==array.length-1){
                node.isWord=true;
            }
        }
    }

    /** Returns if the word is in the trie. */
    public boolean search(String word) {
        char [] array =word.toCharArray();
        Trie node =this;
        for(int i=0;i<array.length;i++){
            if(node.tries[array[i]-'a']!=null){
                //Traverse to it's child node which the value is the char you are searching
                node =node.tries[array[i]-'a'];
                if(node.word ==array[i])
                    continue;
                else
                    return false;
            }else
                return false;

        }
        return node.isWord==true?true:false;
    }

    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {
        char [] array =prefix.toCharArray();
        Trie node =this;
        for(int i=0;i<array.length;i++){
            if(node.tries[array[i]-'a']!=null){
                node =node.tries[array[i]-'a'];
                if(node.word ==array[i])
                    continue;
                else
                    return false;
            }
            else
                return false;

        }
        return true;
    }
}
