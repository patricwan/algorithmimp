package com.pwan.pkg;

import java.util.*;

//import org.apache.commons.collections4.BinaryHeap;


public class BinaryHeapFindFrequency {

    public static void main(String[] args) {
        System.out.println("This is the start of main program");

        int[] nums = {1, 3, 4, 54, 3, 2, 2, 3, 5, 6, 8, 7, 4, 6, 43, 42, 3, 2};
        List<Integer> result = topKFrequent(nums, 3);

    }

    public static List<Integer> topKFrequent(int[] nums, int k) {
        //1. Build a HashMap like key: occurCount
        final Map numCountMap = new HashMap();

        for (int i = 0; i < nums.length; i++) {
            if (numCountMap.get(nums[i]) != null) {
                numCountMap.put(nums[i], (Integer) numCountMap.get(nums[i]) + 1);
            } else {
                numCountMap.put(nums[i], 1);
            }
        }


        PriorityQueue<Integer> pq = new PriorityQueue<>(new Comparator<Integer>() {
            public int compare(Integer a, Integer b) {
                return (Integer) numCountMap.get(a) - (Integer) numCountMap.get(b);
            }
        });

        //Maintain a priorityQueue which is actually a binary heap.
        //Iterate each lement in the key-count map.   (2,5), (3,6),(4,2),....(13,4)
        for (Object key : numCountMap.keySet()) {
            int keyV = ((Integer) key).intValue();
            //if size not reach k, simply adding the key to it..
            if (pq.size() < k) {
                pq.add(keyV);
            }
            //If already reach k, would need to find k largest, this would pop up the smallest one.
            else if ((Integer) numCountMap.get(key) > (Integer) numCountMap.get(pq.peek())) {
                pq.remove();
                pq.add(keyV);
            }
        }

        List<Integer> list = new ArrayList<Integer>();
        while (!pq.isEmpty()) {
            list.add(pq.remove());
        }

        return list;
    }
}
