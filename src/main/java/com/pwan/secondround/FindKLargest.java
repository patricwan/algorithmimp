package com.pwan.secondround;

import java.util.LinkedList;
import java.util.PriorityQueue;

public class FindKLargest {
    public static void main(String[] args) {
        System.out.println("This is the start of the program");

        int[] intArr = {3, 7, 6, 12, 67,32, 54,22, 24, 12, 15, 16, 18, 19};

        int kLargest = findKLargest(intArr, 5);

        System.out.println("K largest in array " + kLargest);
    }

    public static int findKLargest(int[] intArr, int k) {

        PriorityQueue pq = new PriorityQueue();
        for (int i=0; i<intArr.length ; i++) {
            pq.add(intArr[i]);
        }
        int ret = 0;
        Object obj = null;
        for (int i=0; i<intArr.length-k; i++) {
             obj =  pq.poll();
        }
        ret = Integer.valueOf(obj.toString());
        return ret;
    }
}
