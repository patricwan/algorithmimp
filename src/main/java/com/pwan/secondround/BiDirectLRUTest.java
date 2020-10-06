package com.pwan.secondround;

import java.util.*;

public class BiDirectLRUTest {

    BiDirectLinkedNode head = new BiDirectLinkedNode();
    BiDirectLinkedNode tail = new BiDirectLinkedNode();

    private int size;
    private int capacity;

    Map<Integer,BiDirectLinkedNode> cacheMap = new HashMap<Integer,BiDirectLinkedNode>();
    {
        head.next = tail;
        tail.prev = head;
        this.size = 0;
        this.capacity = 10;
    }


    public static void main(String[] args) {
        System.out.println("This is the start of the test");

        BiDirectLRUTest biDirectTest = new BiDirectLRUTest();
        biDirectTest.biDirectLinkedNodeTest();

        biDirectTest.putToCache(3, 6);
        biDirectTest.putToCache(4, 6);
        biDirectTest.putToCache(5, 6);

        System.out.println("get key 4 " + biDirectTest.getFromCache(4));
        biDirectTest.putToCache(5, 12);

    }

    public void putToCache(int key, int value) {
        //First check if key exists.
        Object obj = cacheMap.get(key);
        if (obj==null) {
        //If not exist, add to head, put to cache.
            BiDirectLinkedNode newNode = new BiDirectLinkedNode(key, value);
            cacheMap.put(key, newNode);
            addToHead(head, tail , newNode);
            size++;
            //If size > capacity, remove the tail.
            if (size > capacity) {
                BiDirectLinkedNode rmTail = removeTail(head, tail);
                cacheMap.remove(rmTail.key);
                size--;
            }
        } else {
            //Update the value
            BiDirectLinkedNode existingNode = ( BiDirectLinkedNode )obj;
            existingNode.value = value;
            addToHead(head, tail, existingNode);
        }



    }

    public int getFromCache(int key) {
        Object obj = cacheMap.get(key);
        if (obj!=null) {
            BiDirectLinkedNode getNode = (BiDirectLinkedNode)obj;
            addToHead(head, tail,getNode);
            return getNode.value;
        }

        return -999;
    }

    public void biDirectLinkedNodeTest() {
        BiDirectLinkedNode head = new BiDirectLinkedNode();
        BiDirectLinkedNode tail = new BiDirectLinkedNode();

    }

    //Add node to be after head, because there is a virtual head.
    public void addToHead(BiDirectLinkedNode head,  BiDirectLinkedNode tail, BiDirectLinkedNode node) {
        //If empty list
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;


    }

    public void removeNode(BiDirectLinkedNode head,  BiDirectLinkedNode tail, BiDirectLinkedNode node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;

    }

    public BiDirectLinkedNode removeTail(BiDirectLinkedNode head,  BiDirectLinkedNode tail) {
        BiDirectLinkedNode tailPrev = tail.prev;
        if (tail.prev !=head) {
            removeNode(head, tail,tailPrev);
        }
        return tailPrev;
    }
}
