package com.pwan.secondround;

public class LFUNode implements Comparable<LFUNode>{

    int key;

    int value;

    int freq;

    int index;

    public LFUNode(int key, int value, int index) {
        this.key = key;
        this.value = value;
        this.freq = 1;
        this.index = index;
    }

    @Override
    public int compareTo(LFUNode node2) {
        int compRes = this.freq - node2.freq;
        if (compRes ==0) {
            return this.index - node2.index;
        } else {
           return compRes;
        }
    }

}
