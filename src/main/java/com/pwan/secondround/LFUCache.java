package com.pwan.secondround;

import java.util.*;


public class LFUCache {

    Map<Integer, LFUNode> map  = null;

    //offer(E) : add a new element
    //remove(E): remove an element E
    //E peek(): find the element at top. the smallest one
    // poll: remove element at top. the smallest one.
    PriorityQueue<LFUNode> freqQue = null;

    int size;

    int capacity;

    int index = 0;


    public LFUCache(int size, int capacity) {
        this.map = new HashMap<Integer, LFUNode>();
        this.freqQue = new PriorityQueue<LFUNode>();
        this.size = size;
        this.capacity = capacity;
    }

    public int getFromCache(int key) {
        LFUNode lfuNode = (LFUNode)map.get(Integer.valueOf(key));
        if (lfuNode==null) {  //if not able to get it from cache.
            return -99;
        } else {
            lfuNode.freq++;
            lfuNode.index = index;
            //small heap ?
            //this.freqQue.remove(lfuNode);
            //this.freqQue.offer(lfuNode);
        }


        return -99;
    }

    public void putToCache(int key, int value) {
        LFUNode lfuNode = (LFUNode)map.get(Integer.valueOf(key));

        if (lfuNode ==null) {
            if (size > capacity) {
                //remove the one at top. the smallest one.
                this.freqQue.poll();
            }
            lfuNode = new LFUNode(key, value, index++);
            this.freqQue.offer(lfuNode);
            this.map.put(Integer.valueOf(key),lfuNode );
        } else {
            //Update value
            lfuNode.value = value;
            lfuNode.freq++;
            lfuNode.index = index++;
        }
    }

}
