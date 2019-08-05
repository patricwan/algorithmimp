package com.pwan.pkg;

import java.util.Arrays;

//https://leetcode.com/problems/longest-increasing-subsequence/
public class LengthOfLIS {
    public static void main(String[] args) {
        System.out.println("This is the start of main program");

        int[] data = {3,534,225,3,6,78,2,4,4,2,79,3,33};

        System.out.println(" longest " + lengthOfLIS(data));
    }

    //This method relies on the fact that the longest increasing subsequence possible upto the i^{th}ith
    //  index in a given array is independent of the elements coming later on in the array.
    //  Thus, if we know the length of the LIS upto i^{th}ith
    //  index, we can figure out the length of the LIS possible by including the (i+1)^{th}(i+1)th
    //  element based on the elements with indices jj such that 0 \leq j \leq (i + 1)0≤j≤(i+1).



    public static int lengthOfLIS(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        int[] dp = new int[nums.length];
        dp[0] = 1;
        int maxans = 1;
        for (int i = 1; i < dp.length; i++) {
            int maxval = 0;
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    maxval = Math.max(maxval, dp[j]);
                }
            }
            dp[i] = maxval + 1;
            maxans = Math.max(maxans, dp[i]);
        }
        return maxans;
    }

}
