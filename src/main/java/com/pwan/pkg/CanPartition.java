package com.pwan.pkg;

//https://leetcode.com/problems/partition-equal-subset-sum/discuss/349936/Java-Solution
public class CanPartition {

    public static void main(String[] args) {
        System.out.println("This is the start of main program");

        int[] nums = {33,44,6,2,22,43,23,33,2,3,5,7,8,9,4,5,6,8,6,1};

        System.out.println("can partition " + canPartition(nums));

    }

    public static boolean canPartition(int[] nums) {
        int sum = 0;
        for (int num :
                nums) {
            sum += num;
        }
        System.out.println(" Total num " + sum);
        if (sum % 2 == 1) return false;
        int target = sum / 2;
        System.out.println(" target num " + target);

        int n = nums.length;
        boolean[][] dp = new boolean[n + 1][target + 1];
        for (int i = 0; i < n + 1; i++) {
            dp[i][0] = true;
        }

        for (int i = n - 1; i >= 0; i--) {
            for (int j = 1; j < target + 1; j++) {
                dp[i][j] = dp[i + 1][j];
                if (j >= nums[i]) {
                    dp[i][j] = (dp[i][j] || dp[i + 1][j - nums[i]]);
                }
            }
        }
        return dp[0][target];
    }
}
