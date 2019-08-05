package com.pwan.pkg;

import java.util.*;

//https://leetcode.com/problems/range-sum-query-immutable/
public class SumRange {

    private static Map<Pair, Integer> map = new HashMap<>();
    //We could trade in extra space for speed. By pre-computing all range
    // sum possibilities and store its results in a hash table, we can speed up the query to constant time.


    public static void main(String[] args) {
        System.out.println("This is the start of the program");
        int[] data = {3,6,8,7,9,64,5,3,3,2,7,6,5,4};

        System.out.println("sum " + sumRange(data, 4,7));

        int[] nums = data;

        for (int i = 0; i < nums.length; i++) {
            int sum = 0;
            for (int j = i; j < nums.length; j++) {
                sum += nums[j];
                map.put(new Pair(i, j), sum);
            }
        }



    }

    public static int sumRange(int[] data, int i, int j) {
        int sum = 0;
        for (int k = i; k <= j; k++) {
            sum += data[k];
        }
        return sum;
    }


}
