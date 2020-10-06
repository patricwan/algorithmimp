package com.pwan.secondround;

public class LFUCacheMainTest {


    public static void main(String[] args) {
        System.out.println("This is the start of main test");

        LFUCache cache = new LFUCache(0, 10);
        cache.putToCache(2,5);
        cache.putToCache(3,5);
        cache.putToCache(4,5);

        cache.getFromCache(3);
        cache.getFromCache(4);

    }
}
