package com.pwan.secondround;

import java.util.*;

public class TopNFrequentElements {

    public static void main(String[] args) {
        System.out.println("This is the start of the program ");

        int[] intArr = {23, 34, 56, 43, 34, 23, 35, 56, 43, 23, 21, 5, 7,5,7,6,23, 35};


        int[] intRet = getTopNFrequentElements(intArr , 3);

        for (int i=0; i<intRet.length; i++) {
            System.out.println("top N " + intRet[i]);
        }
    }

    public static int[] getTopNFrequentElements(int[] intArr, int k) {
        int[] retInt = new int[k];

        Map map = new HashMap();

        for (int i =0 ; i<intArr.length; i++) {
            int key = intArr[i];
            if (map.containsKey(key)) {
                map.put(key, (Integer)map.get(key) + 1);
            } else {
                map.put(key, 1);
            }
        }

        //Store it into priority queue
        PriorityQueue pq = new PriorityQueue(new HashMapComparator(map));

        for (Object eachKey : map.keySet()) {
            Integer eachKeyInt = (Integer) eachKey;
            pq.offer(eachKeyInt);
        }

        for (int i=0; i<pq.size()-k; i++) {
            pq.poll();
        }

        for (int i=0; i<k; i++) {
            retInt[i] = (Integer) pq.poll();
        }
        return retInt;
    }


}
