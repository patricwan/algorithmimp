package com.pwan.pkg;

import java.util.HashMap;
import java.util.Map;


public class TwoSumValue {

    public static void main(String[] args) {

        int[] nums = {3, 6, 5, 7, 4, 3, 54, 7, 2};
        int[] res = twoSum(nums, 13);

        for (int i = 0; i < res.length; i++) {
            System.out.println(res[i]);
        }

    }

    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                return new int[]{map.get(target - nums[i]), i};
            }
            map.put(nums[i], i);
        }
        throw new IllegalArgumentException("No two sum solution");
    }
}
